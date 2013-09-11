package org.universAAL.AALapplication.hwo;

// Class that listen to the context bus for events linked to Hwo.
// This class has to be put in the servlet

import java.util.Date;

import org.universAAL.AALapplication.hwo.SCallee.GPSThread;
import org.universAAL.middleware.container.ModuleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
//import org.universAAL.middleware.service.DefaultServiceCaller; //At this time we don't need to call services from this class
//import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.owl.MergedRestriction; 
import org.universAAL.ontology.location.position.Point;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

import android.content.Context;
import android.location.LocationManager;
import android.os.Looper;


class HwoConsumer extends ContextSubscriber {
	

	private long prevtime;
	private Point prevpoint;
	private final static Logger log = LoggerFactory.getLogger(Activator.class);
	public boolean noresponsefromuser = false; // With this, when an alert has to be sent, we can decide whether ask the user if he's alright or just alert the caregivers directly.
	
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT,AssistedPerson.MY_URI)); 
		return new ContextEventPattern[] {cep};
	}  
	
	HwoConsumer(ModuleContext context){
		
		super(context,getContextSubscriptionParams()); //To be registered to the bus.
		
	}
	
	
	// *****************************************************************
	// Listening to the bus 
	// *****************************************************************
	

	
	

	public void handleContextEvent(ContextEvent event) {
		
		
		prevtime=new Date().getTime();
		User dummyuser;
	    LogUtils.logInfo(Activator.context, this.getClass(), "handleContextEvent",
		    new Object[] {
				"Received context event:\n" +
				"    Subject     ="+event.getSubjectURI()+"\n" +
				"    Subject type="+event.getSubjectTypeURI()+"\n" +
				"    Predicate   ="+event.getRDFPredicate()+"\n" +
				"    Object      ="+event.getRDFObject() },
				null);
	    
	    Point p = (Point)event.getRDFObject();
		System.out.println("Las coordenadas del evento son "+ p.getX()+", "+p.getY()+ " Y el tiempo: "+ new Date().getTime());
		log.info("enviando al Wandering detector");
	   prevpoint = p;
	   String wd = Activator.wanderingdetector.isWandering(p, new Date().getTime());
	   if (wd == "NOTYET") return;
	   if (Activator.scallee.user==null) dummyuser = new User ("DummyUser"); // To avoid exceptions
	   else dummyuser = Activator.scallee.user;
	   if (noresponsefromuser==false){
		   log.debug("debug We ask the user");
		   Activator.uimanager.showalertForm(dummyuser, wd); // TODO : this has to be sent through the context bus, instead of calling the method, because uimanager is in the mobile device
	   } 
	   else{ 
		   log.debug("debug We don't ask the user");
		   Activator.wanderingdetector.alert_yes();
		  
	   }
	    
	    
	} 
	
	public void startWanderingThread(){ //starting the thread that will check the GPS to make sure that every minute Wandering detector is called. This way, if the GPS 
										// doesn't send "change position" events (this can happen if the user stops completely or if its location is get by WIFI signal), we keep activating the WanderingDetector,
										// so it can check is everything is Ok.
		prevtime= new Date().getTime(); 
		prevpoint = null;
				
		CheckThread checklauncher = new CheckThread();
		log.debug("lanzando checklauncher");
		checklauncher.start();
				
	}

	public void communicationChannelBroken() {
		
	}
	
	public class CheckThread extends Thread
	{
		
		public void run()
		{	
			long thistime;
			
			while(true){
				try {
					log.debug("going into sleep");
					sleep(60000);
					log.debug("going out of sleep");
				} catch (InterruptedException e) {
					
				}
				thistime = new Date().getTime();
				if (((thistime - prevtime)>60000)&&(prevpoint!=null)) {
					log.debug("A minute has passed without GPS events, sending again last known position.");
					Activator.wanderingdetector.isWandering(prevpoint, thistime);
				}
				
			}
		  
		}
	}
}
