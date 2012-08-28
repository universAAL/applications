package org.universAAL.AALapplication.hwo;

/* Server. Initiates all services UI and contains their code */


// Point(double x, double y, double z, CoordinateSystem system)

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

//import org.universAAL.AALapplication.personal_safety.sms.owl.EnvioSMSService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;



public class SCallee extends ServiceCallee {
	
	private final static Logger log = LoggerFactory.getLogger(SCallee.class);
		private static final ServiceResponse failure = new ServiceResponse(CallStatus.serviceSpecificFailure); //default Service error response. Will be completed with specific outputs
		private ContextPublisher cp;
		User user = null; 
		User dummyuser = null;
		
		private double[] homeLocation;
		private boolean sent; // SMS
		private boolean delivered; //SMS
		
		private DataStorage dataStorage;
		
	
protected SCallee(ModuleContext context) {
	
	super(context, SCalleeProvidedService.profiles);
	log.debug("SCallee construct 0");
	ContextProvider cpinfo = new ContextProvider(SCalleeProvidedService.HWO_SERVER_NAMESPACE + "HwoContextProvider"
			+ "TestMassContextProvider");
		cpinfo.setType(ContextProviderType.gauge);
		cpinfo.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
	cp = new DefaultContextPublisher(context, cpinfo);        
	dataStorage = DataStorage.getInstance();
	homeLocation = dataStorage.getHomePosition();
	
 	}       

protected SCallee(ModuleContext context, ServiceProfile[] realizedServices) {
	super(context, realizedServices);
    }


	public void communicationChannelBroken() {
		
	}

	//Handle Calls.
	
	public ServiceResponse handleCall(ServiceCall call) {
		log.debug("Received call");

		if (call == null) {

		    failure
			    .addOutput(new ProcessOutput(
				    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
				    "Null call!?!"));
		    log.error("Null call");
		    return failure;
		}

		String operation = call.getProcessURI();
		
		if (operation == null) {
		    failure.addOutput(new ProcessOutput(
			    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			    "Null operation!?!"));
		    log.error("Null op");
		    return failure;
		}

		if (operation
				.startsWith(HwoNamespace.NAMESPACE+"startUI")) {
			    log.debug("Start UI op");
			    Object inputUser = call.getInvolvedUser();
			    if ((inputUser == null) || !(inputUser instanceof User)) {
				failure.addOutput(new ProcessOutput(
					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
					"Invalid User Input!"));
				log.debug("No user");
				return failure;
			    } else {
				user = (User) inputUser;
			    }
			    log.debug("Show dialog from call");
			    ServiceResponse response = failure;			
			    if (operation.startsWith(HwoNamespace.NAMESPACE+"startUIpanic")) { //Start Panic Button UI
			    	Activator.uimanager.showButtonManualForm(user);	
			    	response = new ServiceResponse(CallStatus.succeeded);
				}else if (operation.startsWith(HwoNamespace.NAMESPACE+"startUIguide")) {  //Start Guide Me UI
					Activator.uimanager.showGuideMeForm(user);
					response = new ServiceResponse(CallStatus.succeeded);
					    }
			    return response;
			}
		log.debug("finished");
		return null;
	  }    
	
	
// Servicios.
	
		// Take Me Home
	
	public boolean TakeHome(){
		
		String POIselectedLocation = Double.toString(homeLocation[0])+","+Double.toString(homeLocation[1]);
		GuideTo(POIselectedLocation);
		return true;
	}
	
		// Guide Me
	
	public boolean GuideMe(User user){ // This will show to the user the available list of POIs
		List<POI> POIs = GetPOIs(); 
		Activator.uimanager.showAvailablePOIForm(user, POIs);
		return true;
	}
	
	public List<POI> GetPOIs(){ 
		List<POI> POIs = new ArrayList<POI>();
		log.debug("Accediendo a la lista de POIs");
		Vector poilist = dataStorage.getPOIList();
		POI_UAAL auxpoi = null;
		int index = 0;
		POIs.add(new POI("Home",Double.toString(homeLocation[0])+","+Double.toString(homeLocation[1]))); // el hogar

		for (index=0;index<poilist.size();index++){ // Conversion between POI_UAAL class and our own.
			auxpoi = (POI_UAAL) poilist.get(index);
			POIs.add(new POI(auxpoi.getName(),auxpoi.getCoordinates())); 
			
		}
		
		return POIs;
		
	}
	
	public boolean GuideTo(String destiny){ //Launches Google Navigation
		
		Intent i = new Intent(
				Intent.ACTION_VIEW,
				Uri
					.parse("google.navigation:q="
						+ destiny
							.replace(" ", "+") + "&mode=w"));
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Activator.activityHandle.startActivity(i);
		return true;
	}
	
	
	
		// Panic Button
	
	public boolean Panic(User user){

		String txt = Activator.uimanager.PANIC_BUTTON_PRESSED; 
		boolean sent = AlertCareGivers(user,txt);
		return sent;
	}
	
	
		// Alert Caregivers
	
	public boolean AlertCareGivers(User user, String txt){

		List<Caregiver> Caregivers = GetCareGivers(); //
		boolean alerted = false;
		int index = 0; 
		do {
			
			alerted = SendSMS(txt,Caregivers.get(index).getNumber());
			index++;
			
		}while ( (alerted != true) & (index<Caregivers.size()) );
		Activator.uimanager.showSMSForm(user, alerted); 
		return alerted;		
	}
	
	
	
	
	public List<Caregiver> GetCareGivers(){ // TODO: Get the CareGivers list from proper location
		List<Caregiver> Caregivers = new ArrayList<Caregiver>();
		Caregivers.add(new Caregiver("Arturo","34620486483"));
		return Caregivers;
		
	}
	
	
		// SMS
	
	 private ServiceRequest sendSMS(String txt,String num){
/*			ServiceRequest sendSMS = new ServiceRequest(new EnvioSMSService(null), null);
			sendSMS.getRequestedService().addInstanceLevelRestriction(MergedRestriction.getFixedValueRestriction(EnvioSMSService.PROP_MANDA_TEXTO,new String(txt)),new String[] { EnvioSMSService.PROP_MANDA_TEXTO });
			sendSMS.getRequestedService().addInstanceLevelRestriction(MergedRestriction.getFixedValueRestriction(EnvioSMSService.PROP_TIENE_NUMERO, new String(num)),new String[] { EnvioSMSService.PROP_TIENE_NUMERO });
			return sendSMS;*/
		 return null;
	 }
	 
	public boolean SendSMS(String txt, String number){
		
		log.debug("Llamando a funcion sendSMS");
		ServiceResponse sr = Activator.caller.call(sendSMS(txt , number));
		return true; 
		}
	


	
	//  GPS 
	
	
public boolean Location(User user){
	log.debug("Entering Location");
	GPSThread gpslauncher = new GPSThread();
	log.debug("Launching gpslauncher");
	gpslauncher.start();
	return true;
	} 




		
private class MyLocationListener implements LocationListener {

	AssistedPerson ap = new AssistedPerson(HwoNamespace.NAMESPACE + "HwoAP");
	
    public void onLocationChanged(Location location) {
    	log.info("Change in Location");
        String message = String.format("New Location \n Longitude: %1$s \n Latitude: %2$s",location.getLongitude(), location.getLatitude());
        log.debug(message);
        ap.setLocation(new Point(location.getLatitude(),location.getLongitude(),CoordinateSystem.WGS84));
    	cp.publish(new ContextEvent(ap,ap.PROP_PHYSICAL_LOCATION));
          }

    public void onStatusChanged(String s, int i, Bundle b) {      
    }

    public void onProviderDisabled(String s) {      
    }

    public void onProviderEnabled(String s) {      
    }
}



public class GPSThread extends Thread
{
public void run()
{	

   LocationManager mLocationManager;
	MyLocationListener mLocationListener;
	mLocationManager =	(LocationManager)Activator.activityHandle.getSystemService(Context.LOCATION_SERVICE);
   if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		Looper.prepare();
		mLocationListener = new MyLocationListener();
		mLocationManager.requestLocationUpdates(
		LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
		Looper.loop();
		Looper.myLooper().quit();
		} else {
		log.error("Location function in SCallee failed");
		}
  
}
}



}


