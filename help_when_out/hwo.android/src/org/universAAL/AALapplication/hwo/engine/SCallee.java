package org.universAAL.AALapplication.hwo.engine;

/* Server. Initiates all services UI and contains their code */

// Point(double x, double y, double z, CoordinateSystem system)

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.universAAL.AALapplication.hwo.BackgroundService;
import org.universAAL.AALapplication.hwo.R;
import org.universAAL.AALapplication.hwo.model.AssistedPerson;
import org.universAAL.AALapplication.hwo.model.CoordinateSystem;
import org.universAAL.AALapplication.hwo.model.Point;
import org.universAAL.AALapplication.hwo.model.User;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;

public class SCallee {
	private static final String TAG = "SCallee";
	User user = null;
	User dummyuser = null;

	private double[] homeLocation;
	private boolean sent; // SMS
	private boolean delivered; // SMS

	private DataStorage dataStorage;

	public SCallee() {
		Log.d(TAG, "SCallee construct 0");
		dataStorage = DataStorage.getInstance();
		homeLocation = dataStorage.getHomePosition();
	}

	// Handle Calls.

//	public ServiceResponse handleCall(ServiceCall call) {
//		Log.d(TAG, "Received call");
//
//		if (call == null) {
//
//			failure.addOutput(new ProcessOutput(
//					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Null call!?!"));
//			log.error("Null call");
//			return failure;
//		}
//
//		String operation = call.getProcessURI();
//
//		if (operation == null) {
//			failure.addOutput(new ProcessOutput(
//					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
//					"Null operation!?!"));
//			log.error("Null op");
//			return failure;
//		}
//
//		if (operation.startsWith(HwoNamespace.NAMESPACE + "startUI")) {
//			Log.d(TAG, "Start UI op");
//			Object inputUser = call.getInvolvedUser();
//			if ((inputUser == null) || !(inputUser instanceof User)) {
//				failure.addOutput(new ProcessOutput(
//						ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
//						"Invalid User Input!"));
//				Log.d(TAG, "No user");
//				return failure;
//			} else {
//				user = (User) inputUser;
//			}
//			Log.d(TAG, "Show dialog from call");
//			ServiceResponse response = failure;
//			if (operation.startsWith(HwoNamespace.NAMESPACE + "startUIpanic")) { // Start
//																					// Panic
//																					// Button
//																					// UI
//				BackgroundService.uimanager.showButtonManualForm(user);
//				response = new ServiceResponse(CallStatus.succeeded);
//			} else if (operation.startsWith(HwoNamespace.NAMESPACE
//					+ "startUIguide")) { // Start Guide Me UI
//				BackgroundService.uimanager.showGuideMeForm(user);
//				response = new ServiceResponse(CallStatus.succeeded);
//			}
//			return response;
//		}
//		Log.d(TAG, "finished");
//		return null;
//	}

	// Servicios.

	// Take Me Home

	public boolean TakeHome() {

		String POIselectedLocation = Double.toString(homeLocation[0]) + ","
				+ Double.toString(homeLocation[1]);
		GuideTo(POIselectedLocation);
		return true;
	}

	// Guide Me

	public boolean GuideMe(User user) { // This will show to the user the
										// available list of POIs
		List<POI> POIs = GetPOIs();
		BackgroundService.uimanager.showAvailablePOIForm(user, POIs);
		return true;
	}

	public List<POI> GetPOIs() {
		List<POI> POIs = new ArrayList<POI>();
		Log.d(TAG, "Accediendo a la lista de POIs");
		Vector poilist = dataStorage.getPOIList();
		POI_UAAL auxpoi = null;
		int index = 0;
		POIs.add(new POI("Home", Double.toString(homeLocation[0]) + ","
				+ Double.toString(homeLocation[1]))); // el hogar

		for (index = 0; index < poilist.size(); index++) { // Conversion between
															// POI_UAAL class
															// and our own.
			auxpoi = (POI_UAAL) poilist.get(index);
			POIs.add(new POI(auxpoi.getName(), auxpoi.getCoordinates()));

		}

		return POIs;

	}

	public boolean GuideTo(String destiny) { // Launches Google Navigation

		Intent i = new Intent(Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q=" + destiny.replace(" ", "+")
						+ "&mode=w"));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		BackgroundService.activityHandle.startActivity(i);
		return true;
	}

	// Panic Button

	public boolean Panic(User user) {

		String txt = BackgroundService.activityHandle.getString(R.string.PANIC_BUTTON_PRESSED);//PANIC_BUTTON_PRESSED
		boolean sent = AlertCareGivers(user, txt);
		return sent;
	}

	// Alert Caregivers

	public boolean AlertCareGivers(User user, String txt) {

		List<Caregiver> Caregivers = GetCareGivers(); //
		boolean alerted = false;
		int index = 0;
		do {

			alerted = SendSMS(txt, Caregivers.get(index).getNumber());
			index++;

		} while ((alerted != true) & (index < Caregivers.size()));
		BackgroundService.uimanager.showSMSForm(user, alerted);
		return alerted;
	}

	public List<Caregiver> GetCareGivers() { // TODO: Get the CareGivers list
												// from proper location
		List<Caregiver> Caregivers = new ArrayList<Caregiver>();
		Caregivers.add(new Caregiver("Arturo", "34620486483"));
		return Caregivers;

	}

	// SMS
	public boolean SendSMS(String txt, String number) {

		Log.d(TAG, "Llamando a funcion sendSMS");
//		ServiceResponse sr = BackgroundService.caller
//				.call(sendSMS(txt, number));
		// TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return true;
	}

	// GPS

	public boolean Location(User user) {
		Log.d(TAG, "Entering Location");
		GPSThread gpslauncher = new GPSThread();
		Log.d(TAG, "Launching gpslauncher");
		gpslauncher.start();
		return true;
	}

	private class MyLocationListener implements LocationListener {
		AssistedPerson ap = new AssistedPerson(HwoNamespace.NAMESPACE + "HwoAP");

		public void onLocationChanged(Location location) {
			Log.i(TAG, "Change in Location");
			String message = String.format(
					"New Location \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude());
			Log.d(TAG, message);
			Point p = new Point(location.getLatitude(),
					location.getLongitude(), location.getAltitude(),
					CoordinateSystem.WGS84);
			BackgroundService.hwoconsumer.handleContextEvent(ap,
					"hasLocation", p);
			publish(ap,p);
		}

		private void publish(AssistedPerson ap2, Point p) {
			String[] parts=ap2.uri.split("#");
			Intent intent = new Intent("org.universAAL.AALapplication.hwo.ACTION_LOCATION");
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.putExtra("subjectSuffix", parts[1]);
			intent.putExtra("objectLocationX", p.getX());
			intent.putExtra("objectLocationY", p.getY());
			intent.putExtra("objectLocationZ", p.getZ());
			BackgroundService.activityHandle.sendBroadcast(intent);
		}

		public void onStatusChanged(String s, int i, Bundle b) {
		}

		public void onProviderDisabled(String s) {
		}

		public void onProviderEnabled(String s) {
		}
	}

	public class GPSThread extends Thread {
		public void run() {
			LocationManager mLocationManager;
			MyLocationListener mLocationListener;
			mLocationManager = (LocationManager) BackgroundService.activityHandle
					.getSystemService(Context.LOCATION_SERVICE);
			if (mLocationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Looper.prepare();
				mLocationListener = new MyLocationListener();
				mLocationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
				Looper.loop();
				Looper.myLooper().quit();
			} else {
				Log.e(TAG, "Location function in SCallee failed");
			}

		}
	}

}
