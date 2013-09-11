/**
 * This class simulates the change of position of the user by throwing GPS events to the context bus. For testing purposes.
 * 
 * @author Arturo Domingo
 * @author <a href="mailto:geibsan@upvnet.upv.es">Gema Ibanez&064;UPVLC</a> 
 * @version %I%
 * @since 1.0
 * 
 */


package org.universAAL.AALapplication.helpwhenoutdoor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;
import org.universAAL.ontology.profile.AssistedPerson;


public class Walker {
	
	private HelpWhenOutdoorDataService dataService;
	private List<Point> userpath;
	private int contador;
	Properties config;
	private ContextPublisher cp;
	
	
	/**
	 * Create Walker object and create fake coordinates
	 * 
	 * @param Properties
	 * @param HelpWhenOutdoorDataService
	 * @param ModuleContext
	 * 
	 */
	public Walker(Properties config, HelpWhenOutdoorDataService dataService,ModuleContext context) {
		this.config = config;
		this.dataService = dataService;
		userpath =  new ArrayList<Point>();
		userpath.add(new Point(39.4592018,-0.3845417,CoordinateSystem.WGS84));
		userpath.add(new Point(39.4587724,-0.3856196,CoordinateSystem.WGS84));
		userpath.add(new Point(39.4536763,-0.3922401,CoordinateSystem.WGS84));
		contador=0;
		
		// TODO: it must be moved to the right class when real location is taken from GPS 
		ContextProvider cpinfo = new ContextProvider("Walker"
				+ "TestMassContextProvider");
			cpinfo.setType(ContextProviderType.gauge);
			cpinfo.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
			
		cp = new DefaultContextPublisher(context, cpinfo);   
	}
	
		
	/**
	 * This method simulates the GPS
	 */
	public void changePosEvent(){ 
		AssistedPerson ap = new AssistedPerson(HwoNamespace.NAMESPACE + "HwoAP");
		Point punto = null;
		// Get a new position
		punto = userpath.get(contador); 
		System.out.println("Sending change location event");
	    String message = String.format("New location \n Longitude: %1$s \n Latitude: %2$s",punto.getX(),punto.getY());
	    System.out.println(message);
	    ap.setLocation(new Point(punto.getX(),punto.getY(),CoordinateSystem.WGS84));
	    cp.publish(new ContextEvent(ap,ap.PROP_PHYSICAL_LOCATION));
	   	if (contador<2) contador++;
	}

	/**
	 * Change position
	 * 
	 * @deprecated
	 * 
	 */
	public void changePos(){
		Point punto = null;
		punto = userpath.get(contador);
		dataService.setLatitude(punto.getX());
		dataService.setLongitude(punto.getY());
		System.out.println("New point:" + punto.getX()+punto.getY());
		dataService.setHistoryEntry(new Date().getTime());
		if (contador<2) contador++;	
	}

}

