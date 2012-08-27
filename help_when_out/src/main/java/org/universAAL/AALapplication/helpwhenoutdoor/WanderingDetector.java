/**
 * This class checks if the user is following an erratic route, is out of the safe area or if is wandering
 * 
 * @author Arturo Domingo
 * @author <a href="mailto:geibsan@upvnet.upv.es">Gema Ibanez&064;UPVLC</a> 
 * @version %I%
 * @since 1.0
 * 
 */

package org.universAAL.AALapplication.helpwhenoutdoor;

//TODO: replace this class with the WanderingDetector of hwo.mobile project.

//Latitud, posicion 0, corresponde a X

import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.AALapplication.helpwhenoutdoor.common.DataStorage;
import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;

public class WanderingDetector {
private static final Logger log = LoggerFactory.getLogger(WanderingDetector.class);
protected class RoutePoint {
	Point RP;
	long timestamp;
	
	
	
	protected RoutePoint(){
		RP = new Point(200,200,CoordinateSystem.WGS84);
		timestamp=-1;
	}
}

	private Vector routeHistory;
	private int lastSegmentIntersections;
	private int routeIntersections;
	private int lastSegmentIntersectionThreshold; 
	private int  routeIntersectionsThreshold;
	private final long POSITION_SAMPLING_MSECS=60000;
	private DataStorage dataStorage;

public WanderingDetector(int lastSegmentIntersectionThreshold, 
		int  routeIntersectionsThreshold){
	routeHistory= new Vector();
	routeIntersections=0;
	lastSegmentIntersections=0;
	this.lastSegmentIntersectionThreshold=lastSegmentIntersectionThreshold;
	this.routeIntersectionsThreshold=routeIntersectionsThreshold;
	this.dataStorage = DataStorage.getInstance();
	}

/**
 * Add a new position to the monitored route
 * @param position A double array with the latitude in the first element and the
 * longitude in the second one. Null values are not admitted.
 * @param timestamp. The timestamp of the detected position
 */





public String isWandering (Point p, long timestamp){
	log.info("Checking if the user is wandering using intersection counter");
	
	
	long oldTimestamp=-1;
	if (routeHistory.size()>0){
		RoutePoint oldPoint = (RoutePoint)routeHistory.lastElement();
		oldTimestamp=oldPoint.timestamp;
	}
	
	/*if (timestamp-oldTimestamp>POSITION_SAMPLING_MSECS)
		addPosition(position, timestamp);*/
	if (timestamp-oldTimestamp<POSITION_SAMPLING_MSECS) //si la nueva posición no está lo bastante separada temporalmente de la anterior, no hacemos nada.
		return "OK";
	
	if (p==null)
		throw new IllegalArgumentException();
	
	RoutePoint currentRoute= new RoutePoint();
	currentRoute.RP.set2DCoordinates(p.getX(), p.getY());
	currentRoute.timestamp=timestamp;
	routeHistory.add(currentRoute);
	updateSegmentIntersections(); // Comprobamos si tenemos que añadir intersecciones.
	
		
	
	boolean resultW =lastSegmentIntersections>lastSegmentIntersectionThreshold
	 				|| routeIntersections>routeIntersectionsThreshold; //si hemos superado el limite, salimos.
	 if (resultW)
	 {
		 log.info("The user is wandering");
		 clearRouteHistory();
		 return "WANDERING";
	 }
	 //añadido. Bloque para comprobar si el usuario está fuera del área segura.
	 
	boolean resultC =  checkSafeArea(p);
	
	 if (resultC)
	 {
		 log.info("The user is out of safe Area");
		 clearRouteHistory();
		 return "OUT";
	 } 
	 
	 
	 //añadido. Bloque para comprobar si el usuario se ha caído.
	 log.info("Checking if the user is stopped");
	 boolean resultS = false;
	 int routeSizeS=routeHistory.size();
	
	 	 
	 if (routeSizeS<5) // Necesitamos 5 elementos
		 {
			return "OK";
		}
	 
	 Vector last5points = new Vector();
	 int j=0;
	 
	 for (int i=routeSizeS-1; i>=routeSizeS-5; i--)
		{
			last5points.add(j, (RoutePoint) routeHistory.get(i));
			j++;
		}
	 resultS=stopped(last5points);
	 if (resultS)
	 {
		 log.info("The user has stopped");
		 clearRouteHistory();
		 return "STOPPED";
	 }
	 //fin añadido
	 
	 
	 
	 return "OK";
}

public void clearRouteHistory(){
	routeHistory.clear();
	lastSegmentIntersections=0;
	routeIntersections=0;
	
}
/**
 * Clear the user position table. To be called when the user reaches the destination
 */

public boolean checkSafeArea(Point p) {
	//Explicacion del algoritmo. Nos basaremos en http://jsbsan.blogspot.com.es/2011/01/saber-si-un-punto-esta-dentro-o-fuera.html
	//Es decir, trazando una linea paralela al eje X que pase por nuestra posición, vamos a comprobar si el numero de intersecciones con las rectas
	//que conforman la safeArea es par o impar.
	
	System.out.println("***Testeando la funcion checkSafeArea***");
	Vector safearea = dataStorage.getArea("safeArea"); //ahora es un vector de puntos.
	if (safearea==null) return false;
	System.out.println("*****DATOS DE LA SAFEAREA******" + safearea.toString()); //eliminar esto
	safearea.add(safearea.get(0)); //acaba y empieza en el mismo elemento, para el bucle
	Point A;
	Point B;
	double xA,xB,yA,yB; //coordenadas metricas de los 2 puntos del area segura entre los que trazamos el vector
	double xPos,yPos;	// coordenadas metricas de la posicion del usuario
	double difx,dify; //incrementos para construir el vector
	double xaux,yaux; //punto actual del vector
	double result; // comparacion
	double R = 6371000.0;  //Earth Radius in meters
	double PI = 3.14159265;
	int numberpoints = 10; //para cambiar la precision
	double[] VectorpointsX = new double[numberpoints];
	double[] VectorpointsY = new double[numberpoints]; //para crear el vector que une dos puntos del safeArea
	
	double[] VectorXsafearea = new double[safearea.size()]; 
			
	xPos=p.getX();
	yPos=p.getY();
	
	System.out.println("***Nuestra posición en grados es "+xPos+", "+ yPos);
	
	xPos=R*Math.cos(xPos*PI/180)*Math.cos(yPos*PI/180);
	yPos=R*Math.cos(xPos*PI/180)*Math.sin(yPos*PI/180);
	
	System.out.println("***Nuestra posición en metros es "+xPos+", "+ yPos);
	
	//*** Paso 1: Ordenar las coordenadas de la SafeArea y averiguar los extremos.
	
	 for (int i=0; i<safearea.size()-1; i++)
		{
		 System.out.println("***Iteración numero "+i);
			A =(Point) safearea.get(i);
			B =(Point) safearea.get(i+1); //A y B son dos puntos del área segura.
			xA = A.getX();
			yA = A.getY();
			xB = B.getX();
			yB = B.getY(); // de momento son latitud y longitud.
			
			xA= R*Math.cos(xA*PI/180)*Math.cos(yA*PI/180);
			yA= R*Math.cos(xA*PI/180)*Math.sin(yA*PI/180);
			System.out.println("***Posicion en metros del punto A: "+xA+", "+ yA);
			VectorXsafearea[i]=xA; //iremos almacenando las coordenadas X de los puntos de la safearea
			
			xB= R*Math.cos(xB*PI/180)*Math.cos(yB*PI/180);
			yB= R*Math.cos(xB*PI/180)*Math.sin(yB*PI/180); //en metros.
			System.out.println("***Posicion en metros del punto B: "+xB+", "+ yB);
			
			/*difx=Math.abs((xA-xB))/numberpoints;
			dify=Math.abs((yA-yB))/numberpoints;
			if (xA>xB)difx = difx*-1;
			if (yA>yB)difx = dify*-1;
			
			for( int j=0; j<numberpoints;j++){
				xaux=xA+difx*j;
				yaux=yA+dify*j;
				VectorpointsX[j]=xaux;
				VectorpointsY[j]=yaux;	
				//System.out.println("***Posicion en metros del punto " +j+": "+xaux+", "+ yaux);
			}*/ //aqui ya tenemos el vector creado entre A y B. Ahora queda trazar una linea paralela al eje X desde xPos (ver notas sobre extremos en apuntes).
			
			// Innecesario de momento. En vez de comprobar interseccion punto a punto vamos a usar la clase "recta" de Java.

		}
	 Arrays.sort(VectorXsafearea);
	 for (int k=0; k<safearea.size(); k++){
	 System.out.println("***Coordenadas X de la safearea  "+ VectorXsafearea[k]);
			//la más pequeña está en el indice 1 y la máxima está en safearea.size(), sort aumenta el tamaño del vector en 1 xD
		}
	
	//Ahora, si x es menor que la menor coordenada X de los puntos de la safearea o mayor que la máxima coordenada X, es seguro que estamos fuera. Si está entre ellas, hay que comprobarlo.
	
	 //*** Paso 2: Trazar rectas entre todos los puntos contiguos de la safearea y contar cuántas veces intersectan con la paralela al eje x desde xpos a un extremo.
	
	 if (xPos<VectorXsafearea[1]||xPos>VectorXsafearea[safearea.size()-1]) { System.out.println("Usuario fuera de la safe area");return true;} //usuario se ha salido. Creo que es -1, porque si no, da error.
	 int contador = 0;
	 for (int i=0; i<safearea.size()-1; i++)
		{
		 A =(Point) safearea.get(i);
		 B =(Point) safearea.get(i+1); //A y B son dos puntos del área segura.
		 xA = A.getX();
		 yA = A.getY();
		 xB = B.getX();
		 yB = B.getY();
		 xA= R*Math.cos(xA*PI/180)*Math.cos(yA*PI/180);
		 yA= R*Math.cos(xA*PI/180)*Math.sin(yA*PI/180);
		 xB= R*Math.cos(xB*PI/180)*Math.cos(yB*PI/180);
		 yB= R*Math.cos(xB*PI/180)*Math.sin(yB*PI/180);
		 Line2D.Double AB = new Line2D.Double(xA, yA, xB, yB);
		 Line2D.Double LineaPos = new Line2D.Double(xPos, yPos, VectorXsafearea[safearea.size()-1]+100, yPos); //linea paralela al eje X que va de Xpos a un extremo (y un poco mas lejos para comprobar la interseccion)
		 boolean intersectan = LineaPos.intersectsLine(AB);
		 if(intersectan) contador++;
		}
	
	 System.out.println("La cuenta de intersecciones es: "+contador);
	 if ((contador % 2) == 0) return true; //si el numero de intersecciones es par, estamos fuera.
	 //http://jsbsan.blogspot.com.es/2011/01/saber-si-un-punto-esta-dentro-o-fuera.html
	 
	return false;
}

private boolean stopped(Vector last5points) {
	double latAB,longAB,xAB,yAB; 
	//double PI=3.14159265;
	//int EarthR=6370000;
	// 2 bucles for. Tengo que comprobar todas las combinaciones de puntos.
	/*for (int i=0;i<4;i++){
			RoutePoint pointA= (RoutePoint)last5points.get(i);
			
		for (int j=i+1;j<5;j++){
			RoutePoint pointB= (RoutePoint)last5points.get(j);
				
			
			latAB=pointA.latitude-pointB.latitude;
			longAB=pointA.longitude-pointB.longitude;
			xAB=(latAB*2*PI*EarthR)/360;
			yAB=(longAB*2*PI*EarthR)/360;
			xAB=truncate(xAB);
			yAB=truncate(yAB);
		}
	
	}*/
	
	RoutePoint pointA= (RoutePoint)last5points.get(0);
	RoutePoint pointB= (RoutePoint)last5points.get(4); //primer y último punto de una serie de 5. Por tanto, desplazamiento en 5 min.
	latAB=pointA.RP.getX()-pointB.RP.getX();
	longAB=pointA.RP.getY()-pointB.RP.getY(); //hacemos resta de latitud y longitud y redondeamos el resultado. Si es 0, es que son iguales.
	xAB=roundtodec(latAB,4);
	yAB=roundtodec(longAB,4);
	if ((xAB==0.0000)&&(yAB==0.0000)) return true;
	
	
	return false;
}



/**
 * Updates the number of intersections of the current path and the intersections of
 * the last segment.
 */
private void updateSegmentIntersections(){
	
	int intersections=0;
	int routeSize=routeHistory.size();
	if (routeSize<4)
	{
		lastSegmentIntersections=0;
		routeIntersections=0;
		return;
	}
	
	RoutePoint pointA= (RoutePoint)routeHistory.lastElement();
	RoutePoint pointB=(RoutePoint)routeHistory.get(routeSize-2);
	for (int i=routeSize-1; i>=3; i--)
	{
		RoutePoint pointC=(RoutePoint)routeHistory.get(i-2);
		RoutePoint pointD=(RoutePoint)routeHistory.get(i-3);
		intersections+=intersecates(pointA, pointB, pointC, pointD);
	}
	lastSegmentIntersections=intersections;
	routeIntersections+=lastSegmentIntersections;
	System.out.println("Route intersections: " + routeIntersections);
	System.out.println("Last segment intersections: " + lastSegmentIntersections);
	
	
}

/**
 * @param A End point of the first segment
 * @param B End point of the first segment
 * @param C End point of the second segment
 * @param D End point of the second segment
 * @return 1 if there is at least 1 intersection between the input segments, 0
 *  in any other case
 */
private int intersecates (RoutePoint A,RoutePoint B,RoutePoint C,RoutePoint D){
	
	int ordC= order (A,B,C);
	int ordD=order(A,B,D);
	
	if (ordC*ordD>=0)
		return 0;
	
	int ordA= order(C,D,A);
	int ordB= order (C,D,B);
	
	if (ordA*ordB>=0)
		return 0;
	return 1;
	
	
}
/**
 * Order function between points A, B, and C
 */
private int order (RoutePoint A,RoutePoint B,RoutePoint C){

	//vector that represents the AB segment
	double ux= A.RP.getX()-B.RP.getX();
	double uy=A.RP.getY()-B.RP.getY();
	
	//vector CB
	double vx=C.RP.getX()-B.RP.getX();
	double vy=C.RP.getY()-B.RP.getY();
	
	//vector product between AB and CB
	double uxv=ux*vy-vx*uy;
	
	if (uxv>0)
		return 1;

	if (uxv==0)
		return 0;

	return -1;
}

private double roundtodec(double decimal,int numberofdecs) { //función que redondea a X decimales. Cruzar una calle hace que cambien X decimales de la posición. En 5 minutos debería
	//haber recorrido esa distancia. Por tanto, redondeando las coordenadas a X decimales, si son idénticas, significa que la posición ha variado muy poco
	//y el usuario puede haberse detenido o caído. Nota: X= 3 ó 4, comprobar con un GPS real.
	
	decimal = decimal*(java.lang.Math.pow(10,numberofdecs));
	decimal = java.lang.Math.round(decimal);
	decimal = decimal/java.lang.Math.pow(10,numberofdecs);

	return decimal;
	
}

}
