package org.universAAL.AALapplication.hwo.engine;

// TODO: replace the Wandering Detector Class in the servlet with this one
import java.util.Date;
import java.util.Vector;

import org.universAAL.AALapplication.hwo.BackgroundService;
import org.universAAL.AALapplication.hwo.R;
import org.universAAL.AALapplication.hwo.model.CoordinateSystem;
import org.universAAL.AALapplication.hwo.model.Point;
import org.universAAL.AALapplication.hwo.model.User;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WanderingDetector {  // despite its name, this class check all the
									// possible risk situations.

	

	
	protected class RoutePoint {
		Point RP;
		long timestamp;

		protected RoutePoint(double x, double y) {
			RP = new Point(x, y, 0.0, CoordinateSystem.WGS84);
			timestamp = -1;
		}
	}

	private static final String TAG = "WanderingDetector";

	private Vector routeHistory;
	private Vector safearea;
	private int lastSegmentIntersections;
	private int routeIntersections;
	private int lastSegmentIntersectionThreshold;
	private static int distance;
	private int routeIntersectionsThreshold;
	private final long POSITION_SAMPLING_MSECS = 60000; // time between checks
														// on the user condition
														// (in miliseconds)
	private final long ALERTS_OFF = 1800000; // if an alert is sent, we
												// deactivate it for this long

	private boolean[] flags = { false, false, false }; // This flags are used to
														// check which alarm was
														// sent when we received
														// a message of
														// "Send/don't send alarm"
	private boolean[] mask = { false, false, false }; // This flags are used to
														// deactivate alarms
														// when they have been
														// send
	private long[] timeoff = { 0, 0, 0 }; // To control how long the mask has to
											// be active
	private boolean alerthasbeensent = false; // We activate this when an alarm
												// is sent, and deactivate it
												// when we recive response from
												// the user. If we send an
												// alarm,
	// a minute has passed and this is still "true" we can assume the user
	// doesn't have access to the device and won't answer if he is ok, so we
	// alert the caregivers directly.

	private long oldTimestamp = -1;
	private DataStorage dataStorage;


	public WanderingDetector(int lastSegmentIntersectionThreshold,
			int routeIntersectionsThreshold) {
		//this.distance = 0;
		//Log.e("WanderingDetector - Constructor", "1");
		routeHistory = new Vector();
		routeIntersections = 0;
		lastSegmentIntersections = 0;

		this.lastSegmentIntersectionThreshold = lastSegmentIntersectionThreshold;
		this.routeIntersectionsThreshold = routeIntersectionsThreshold;
		this.dataStorage = DataStorage.getInstance();
		Log.d(TAG, "buscando Safe Area");
		safearea = dataStorage.getArea("safeArea");
		//Log.e("WanderingDetector - Constructor", "2 despu�s de get area");
		//Log.d(TAG, "La Safe Area es");
		//Log.d(TAG, safearea.toString());
	}
	
	public static String getDistance(){
		String aux = Integer.toString(distance);
		return aux;
	}

	/**
	 * Add a new position to the monitored route
	 * 
	 * @param position
	 *            A double array with the latitude in the first element and the
	 *            longitude in the second one. Null values are not admitted.
	 * @param timestamp
	 *            . The timestamp of the detected position
	 */

	public String isWandering(Point p, long timestamp) {
		// despite its name, this method check all the possible risk situations.
		Log.d("WanderingDetector - isWandering" , " wandering ");
		
		User dummyuser;

		if (BackgroundService.scallee.user == null)
			dummyuser = new User("DummyUser");
		else
			dummyuser = BackgroundService.scallee.user;

		// Checking if the new event is new enough
		if (oldTimestamp == -1) {
			oldTimestamp = timestamp;
			return "NOTYET";
		}

		if ((timestamp - oldTimestamp < POSITION_SAMPLING_MSECS)) {
			return "NOTYET";
		}

		oldTimestamp = timestamp;

		// Mask check
		if (timestamp - timeoff[0] > ALERTS_OFF) {
			mask[0] = false;
		}
		if (timestamp - timeoff[1] > ALERTS_OFF) {
			mask[1] = false;
		}
		if (timestamp - timeoff[2] > ALERTS_OFF) {
			mask[2] = false;
		}

		if (p == null)
			throw new IllegalArgumentException();

		// Adding the point to the Route
		RoutePoint currentRoute = new RoutePoint(p.getX(),p.getY());
//		currentRoute.RP.set2DCoordinates(p.getX(), p.getY());
		Log.d(TAG, "debug, las coordenadas son: " + Double.toString(p.getX())
				+ ", " + Double.toString(p.getY()));
		currentRoute.timestamp = timestamp;
		routeHistory.add(currentRoute);

		if (alerthasbeensent == true)
			BackgroundService.hwoconsumer.noresponsefromuser = true;
		else
			BackgroundService.hwoconsumer.noresponsefromuser = false;

		// �Is the user out of Safe Area?
		boolean resultSafeArea = checkSafeArea(p);
		if (resultSafeArea == true && mask[0] == false) {
			clearRouteHistory();
			flags[0] = true;
			flags[1] = false;
			flags[2] = false;
			alerthasbeensent = true;
			return "OUT";
		}

		// �Is the user stopped?
		Log.d(TAG, "Checking if the user is stopped");
		boolean resultStopped = false;
		int routeSizeS = routeHistory.size();
		if (routeSizeS < 5) {
			return "NOTYET";
		}
		Vector last5points = new Vector();
		int j = 0;
		for (int i = routeSizeS - 1; i >= routeSizeS - 5; i--) {
			last5points.add(j, (RoutePoint) routeHistory.get(i));
			j++;
		}
		resultStopped = stopped(last5points);
		if (resultStopped == true && mask[1] == false) {
			Log.i(TAG, "The user has stopped (Checkpoint beta)");

			clearRouteHistory();
			flags[0] = false;
			flags[1] = true;
			flags[2] = false;
			alerthasbeensent = true;
			return "STOPPED";
		}

		// �Is the user wandering?
		Log.d(TAG, "Checking if the user is wandering");
		if (routeSizeS < 4) {
			return "NOTYET";
		}
		updateSegmentIntersections();
		boolean resultIntersections = lastSegmentIntersections > lastSegmentIntersectionThreshold
				|| routeIntersections > routeIntersectionsThreshold;
		if (resultIntersections == true && mask[2] == false) {
			Log.i(TAG, "The user is wandering (Checkpoint alfa)");
			clearRouteHistory();
			flags[0] = false;
			flags[1] = false;
			flags[2] = true;
			alerthasbeensent = true;
			return "WANDERING";
		}

		return "NOTYET"; // NOTYET will result in doing nothing, because
							// everything is ok or because we don't have enough
							// info yet.
	}

	public void clearRouteHistory() {
		routeHistory.clear();
		lastSegmentIntersections = 0;
		routeIntersections = 0;

	}

	/**
	 * Clear the user position table. To be called when the user reaches the
	 * destination
	 */

	public boolean checkSafeArea(Point p) {
		Log.w(TAG, "checkSafeArea-> Comprobar distancia, ver si causa error");
		System.out.println("***Testeando la funcion checkSafeArea***");
		
		DataStorageJson aux = new DataStorageJson();
		Points[] points = aux.getArrayPoi();
		int safeAreaRad = points[0].getBigRadius();
		Double[] homeCoords = points[0].getCoordinates();
		//Point home = new Point(homeCoords[0], homeCoords[1], CoordinateSystem.WGS84);
		
		Location home = new Location("");
		home.setAltitude(homeCoords[1]);
		home.setLatitude(homeCoords[0]);
		
		Location actualLocation = new Location("");
		actualLocation.setLatitude(p.getY());
		actualLocation.setAltitude(p.getX());
		
		distance = (int) home.distanceTo(actualLocation);
		/*
		 * Solo para debug
		 * 
		 */
		
		/*
		 * End debug, eliminar import button
		 */
		
		System.out.println("********************************************");
		System.out.println("El usuario est� a "+distance+" m del centro");
		System.out.println("********************************************");
		
		if(distance > safeAreaRad)	
			return true;
		else return false;

		
		/*Point A;
		Point B;
		double xA, xB, yA, yB; // Segment of Safe Area that we are checking
		double xPos, yPos; // User Position
		double R = 6371000.0; // Earth Radius in meters
		double PI = 3.14159265;

		double[] VectorXsafearea = new double[safearea.size()];

		xPos = p.getX();
		yPos = p.getY();

		double xxPos = R * Math.cos(xPos * PI / 180) * Math.cos(yPos * PI / 180); // Conversion
																			// to
																			// Cartesian
		double yyPos = R * Math.cos(xPos * PI / 180) * Math.sin(yPos * PI / 180);

		// *** Step 1: Order Safe Area points to get the max and min values. If
		// user is further than the min or max value, its out of the safe Area

		for (int i = 0; i < safearea.size() - 1; i++) {
			A = (Point) safearea.get(i);
			xA = A.getX();
			yA = A.getY();
			xA = R * Math.cos(xA * PI / 180) * Math.cos(yA * PI / 180);
			yA = R * Math.cos(xA * PI / 180) * Math.sin(yA * PI / 180);
			VectorXsafearea[i] = xA;

		}
		Arrays.sort(VectorXsafearea);
		if (xxPos < VectorXsafearea[1]
				|| xxPos > VectorXsafearea[safearea.size() - 1]) {
			System.out.println("User out of safe area");
			return true;
		}

		// *** Step 2 : Trace a Line that represents every segment of the
		// safeArea, count intersections with the one-direction horizontal line
		// from the user position

		int contador = 0;
		for (int i = 0; i < safearea.size() - 1; i++) {
			A = (Point) safearea.get(i);
			B = (Point) safearea.get(i + 1); // A y B son dos puntos del �rea
												// segura.
			xA = A.getX();
			yA = A.getY();
			xB = B.getX();
			yB = B.getY();
			double xxA = R * Math.cos(xA * PI / 180) * Math.cos(yA * PI / 180);
			double yyA = R * Math.cos(xA * PI / 180) * Math.sin(yA * PI / 180);
			double xxB = R * Math.cos(xB * PI / 180) * Math.cos(yB * PI / 180);
			double yyB = R * Math.cos(xB * PI / 180) * Math.sin(yB * PI / 180);
			Line2DDouble AB = new Line2DDouble(xxA, yyA, xxB, yyB);
			Line2DDouble LineaPos = new Line2DDouble(xxPos, yyPos,
					VectorXsafearea[safearea.size() - 1] + 100, yyPos); // linea
																		// paralela
																		// al
																		// eje X
																		// que
																		// va de
																		// Xpos
																		// a un
																		// extremo
																		// (y un
																		// poco
																		// mas
																		// lejos
																		// para
																		// comprobar
																		// la
																		// interseccion)
			boolean intersectan = LineaPos.intersectsLine(AB);
			if (intersectan)
				contador++;
		}

		// Step 3: If the number of intersections is even, the user is out of
		// the safe area
		if ((contador % 2) == 0)
			return true;
		// Algorithm (in Spanish)
		// http://jsbsan.blogspot.com.es/2011/01/saber-si-un-punto-esta-dentro-o-fuera.html

		return false;*/
	}

	private boolean stopped(Vector last5points) {
		double latAB, longAB, xAB, yAB;

		RoutePoint pointA = (RoutePoint) last5points.get(0);
		RoutePoint pointB = (RoutePoint) last5points.get(4); // 5 min has passed
																// between this
																// 2 points
		latAB = pointA.RP.getX() - pointB.RP.getX();
		longAB = pointA.RP.getY() - pointB.RP.getY();
		xAB = roundtodec(latAB, 4);
		yAB = roundtodec(longAB, 4); // rounding to the first 4 decimals is
										// approx. equal to 20 meters.
		if ((xAB == 0.0000) && (yAB == 0.0000))
			return true;

		return false;
	}

	/**
	 * Updates the number of intersections of the current path and the
	 * intersections of the last segment.
	 */
	private void updateSegmentIntersections() { // Vector calculus
		Log.d(TAG, "Comprobando intersecciones * * * * * * ");
		int intersections = 0;
		int routeSize = routeHistory.size();

		RoutePoint pointA = (RoutePoint) routeHistory.lastElement();
		RoutePoint pointB = (RoutePoint) routeHistory.get(routeSize - 2);
		for (int i = routeSize - 1; i >= 3; i--) {
			RoutePoint pointC = (RoutePoint) routeHistory.get(i - 2);
			RoutePoint pointD = (RoutePoint) routeHistory.get(i - 3);
			intersections += intersecates(pointA, pointB, pointC, pointD);
		}
		lastSegmentIntersections = intersections;
		routeIntersections += lastSegmentIntersections;
		System.out.println("Route intersections: " + routeIntersections);
		System.out.println("Last segment intersections: "
				+ lastSegmentIntersections);

	}

	/**
	 * @param A
	 *            End point of the first segment
	 * @param B
	 *            End point of the first segment
	 * @param C
	 *            End point of the second segment
	 * @param D
	 *            End point of the second segment
	 * @return 1 if there is at least 1 intersection between the input segments,
	 *         0 in any other case
	 */
	private int intersecates(RoutePoint A, RoutePoint B, RoutePoint C,
			RoutePoint D) {

		int ordC = order(A, B, C);
		int ordD = order(A, B, D);

		if (ordC * ordD >= 0)
			return 0;

		int ordA = order(C, D, A);
		int ordB = order(C, D, B);

		if (ordA * ordB >= 0)
			return 0;
		return 1;

	}

	/**
	 * Order function between points A, B, and C
	 */
	private int order(RoutePoint A, RoutePoint B, RoutePoint C) {

		// vector that represents the AB segment
		double ux = A.RP.getX() - B.RP.getX();
		double uy = A.RP.getY() - B.RP.getY();

		// vector CB
		double vx = C.RP.getX() - B.RP.getX();
		double vy = C.RP.getY() - B.RP.getY();

		// vector product between AB and CB
		double uxv = ux * vy - vx * uy;

		if (uxv > 0)
			return 1;

		if (uxv == 0)
			return 0;

		return -1;
	}

	private double roundtodec(double decimal, int numberofdecs) {

		decimal = decimal * (java.lang.Math.pow(10, numberofdecs));
		decimal = java.lang.Math.round(decimal);
		decimal = decimal / java.lang.Math.pow(10, numberofdecs);

		return decimal;

	}

	// Methods once the user has answered to the alert screen that asks if he's
	// ok.

	public boolean alert_yes() {

		Log.d(TAG, " debug We decide to Alert the Caregivers");
		alerthasbeensent = false;
		BackgroundService.hwoconsumer.noresponsefromuser = false;

		User dummyuser;
		if (BackgroundService.scallee.user == null)
			dummyuser = new User("DummyUser");
		else
			dummyuser = BackgroundService.scallee.user;
		if (flags[0] == true) {
			BackgroundService.scallee.AlertCareGivers(dummyuser,
					BackgroundService.activityHandle.getString(R.string.ALERT_CG_OUT));//ALERT_CG_OUT
			mask[0] = true;
			timeoff[0] = new Date().getTime();
			return true;
		} else if (flags[1] == true) {
			BackgroundService.scallee.AlertCareGivers(dummyuser,
					BackgroundService.activityHandle.getString(R.string.ALERT_CG_STOPPED));//ALERT_CG_STOPPED
			mask[1] = true;
			timeoff[1] = new Date().getTime();
			return true;
		} else if (flags[2] == true) {
			BackgroundService.scallee.AlertCareGivers(dummyuser,
					BackgroundService.activityHandle.getString(R.string.ALERT_CG_WANDERING));//ALERT_CG_WANDERING
			mask[2] = true;
			timeoff[2] = new Date().getTime();
			return true;
		}

		return false;
	}

	public boolean alert_no() {

		Log.d(TAG, " debug We decide not to warn caregivers");
		alerthasbeensent = false;
		BackgroundService.hwoconsumer.noresponsefromuser = false;

		if (flags[0] == true) {
			mask[0] = true;
			timeoff[0] = new Date().getTime();
			return true;
		} else if (flags[1] == true) {
			mask[1] = true;
			timeoff[1] = new Date().getTime();
			return true;
		} else if (flags[2] == true) {
			mask[2] = true;
			timeoff[2] = new Date().getTime();
			return true;
		}
		return false;
	}

}
