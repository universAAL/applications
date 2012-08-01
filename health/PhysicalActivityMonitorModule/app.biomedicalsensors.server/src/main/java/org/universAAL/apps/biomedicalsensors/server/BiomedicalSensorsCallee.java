package org.universAAL.apps.biomedicalsensors.server;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.universAAL.apps.biomedicalsensors.server.bluetooth.DEMOBluetoothDeviceDiscovery;
import org.universAAL.apps.biomedicalsensors.server.unit_impl.BiomedicalSensorStateListener;
import org.universAAL.apps.biomedicalsensors.server.unit_impl.MyBiomedicalSensors;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.Intersection;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.TypeURI;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.biomedicalsensors.CompositeBiomedicalSensor;
import org.universAAL.ontology.biomedicalsensors.MeasuredEntity;
import org.universAAL.ontology.biomedicalsensors.SensorType;
import org.universAAL.ontology.biomedicalsensors.Zephyr;

/**
 * @author joemoul,billyk 
 * 
 */
public class BiomedicalSensorsCallee extends ServiceCallee implements
	BiomedicalSensorStateListener {
	
	//Thread Specific vars
	Thread echo;
	CallStatus cs;
	  
	private static Boolean cronJob=true;
    
    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    static {
	invalidInput.addOutput(new ProcessOutput(
		ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }

    
    static final String SENSOR_URI_PREFIX = BiomedicalSensorsServiceProfiles.BMS_SERVER_NAMESPACE
	    + "controlledSensor";
    static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";

    private static String constructSensorURIfromLocalID(int localID,SensorType stype) {
	return stype.getClassURI() +"-"+ localID;
    }

    private static String constructLocationURIfromLocalID(String localID) {
	return LOCATION_URI_PREFIX + localID;
    }

    //Get the local ID
    private static int extractLocalIDfromSensorURI(String bsURI) {
    	return  Integer.parseInt(bsURI.substring(bsURI.lastIndexOf("-")+1));
    }


    //Get all the registered sensors
    private static CompositeBiomedicalSensor[] getAllControlledSensors(MyBiomedicalSensors theServer) {
	int[] sensors = theServer.getBioSensorIDs();
	CompositeBiomedicalSensor[] result = new CompositeBiomedicalSensor[sensors.length];
	for (int i = 0; i < sensors.length; i++)
	    result[i] = new CompositeBiomedicalSensor(
	    	// first param: instance URI
		    constructSensorURIfromLocalID(sensors[i],theServer.getBioSensorType(i)),
		    // second param:connection type
		   theServer.getBioSensorConnectionType(i),
		    // third param: Sensor Type
		    theServer.getBioSensorType(i),
		    // 4rth param: bluetooth service url
		    "",
		    // 5th param: is connected
		    true,
		    // 6th get the last mesurements
		    theServer.getLastMeasurements(i));
	return result;
    }

    /**
     * Method to construct the ontological declaration of context events
     * published by BiomedicalSensorsCallee.
     */
    private static ContextEventPattern[] providedEvents(MyBiomedicalSensors theServer) {

	MergedRestriction predicateRestriction = MergedRestriction
		.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE,
			CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS);
	
	MergedRestriction objectRestriction = MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_OBJECT,MeasuredEntity.MY_URI, 1, 1);


/*	CompositeBiomedicalSensor[] myBioSensors = getAllControlledSensors(theServer);

	// The subject of the context events is
	// one single member of the above array
	MergedRestriction subjectRestriction = MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_SUBJECT,
			new Enumeration(myBioSensors), 1, 1);

	//creating a ContextEventPattern and adding the above restrictions to it
	ContextEventPattern cep1 = new ContextEventPattern();
	cep1.addRestriction(subjectRestriction);
	cep1.addRestriction(predicateRestriction);
	cep1.addRestriction(objectRestriction);*/

	Intersection xsection = new Intersection();
	xsection.addType(new TypeURI(CompositeBiomedicalSensor.MY_URI, false));
	xsection.addType(MergedRestriction.getFixedValueRestriction(
		CompositeBiomedicalSensor.PROP_SENSOR_TYPE, Zephyr.zephyr));
	MergedRestriction subjectRestriction = MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_SUBJECT, xsection, 1, 1);

	//creating a ContextEventPattern and adding the above restrictions to it
	ContextEventPattern cep2 = new ContextEventPattern();
	cep2.addRestriction(subjectRestriction);
	cep2.addRestriction(predicateRestriction);
	cep2.addRestriction(objectRestriction);

	 
	//return all the descriptions of the context providers events
	return new ContextEventPattern[] { cep2 };
    }

    // the server being wrapped and bound to universAAL
    private MyBiomedicalSensors theServer;

    // needed for publishing context events
    private ContextPublisher cp;

    BiomedicalSensorsCallee(ModuleContext context) {
	
    // provided services to the universAAL-based AAL Space
	super(context, BiomedicalSensorsServiceProfiles.profiles);

	theServer = new MyBiomedicalSensors();

	// preparation for publishing context events
	ContextProvider info = new ContextProvider(
		BiomedicalSensorsServiceProfiles.BMS_SERVER_NAMESPACE
			+ "BiomedicalSensorsContextProvider");
	info.setType(ContextProviderType.controller);
	info.setProvidedEvents(providedEvents(theServer));
	cp = new DefaultContextPublisher(context, info);

	//listen to the changes on the server side
	theServer.addListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken
     * ()
     */
    public void communicationChannelBroken() {
    }

    // Service response including all available biomedical sensors
    private ServiceResponse getControlledBioSensors() {
	
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	
	// list with all the available sensors
	int[] bs = theServer.getBioSensorIDs();
	ArrayList al = new ArrayList(bs.length);
	for (int i = 0; i < bs.length; i++) {
	    CompositeBiomedicalSensor cbs = new CompositeBiomedicalSensor(
	    	    	// first param: instance URI
	    		    constructSensorURIfromLocalID(bs[i],theServer.getBioSensorType(i)),
	    		    // second param:connection type
	    		    theServer.getBioSensorConnectionType(i),
	    		    // third param: Sensor Type
	    		    theServer.getBioSensorType(i),
	    		    // 4rth param: bluetooth service url
	    		    "",
	    		    // 5th param: is connected
	    		    true,
	    		    // 6th param: get the last measurements
	    		    theServer.getLastMeasurements(i));
	    al.add(cbs);
	}
	// ProcessOutput-Event that binds the output URI to the created list of sensors
	sr.addOutput(new ProcessOutput(
		BiomedicalSensorsServiceProfiles.OUTPUT_CONTROLLED_SENSORS, al));
	return sr;
    }

    // Service response with info about the selected sensor
    private ServiceResponse getSensorInfo(String bsURI) {
	try {
	    // collect the needed data	    
	    int bsID = extractLocalIDfromSensorURI(bsURI);
	    System.out.println("local ID: "+bsID);
	    SensorType sensorType = theServer.getBioSensorType(bsID);
	   
	    //bluetooth service URL	    	
		String btURL = theServer.getserviceURL(bsID);
	    if (btURL.equals("")){
	    	DEMOBluetoothDeviceDiscovery zeph=new DEMOBluetoothDeviceDiscovery();
	    	
	    	System.out.println("FETCHING URL for: "+theServer.getBioSensorName(bsID));
	    			zeph.startBT(theServer.getBioSensorName(bsID));
	    			//fetch service from bluetooth
	    			if (DEMOBluetoothDeviceDiscovery.url!=null){
	    	System.out.println("URL FOUND: "+DEMOBluetoothDeviceDiscovery.url);
	    	btURL=DEMOBluetoothDeviceDiscovery.url;
	    	
	    	//set bluetooth service URL
	    	theServer.setserviceURL(btURL, bsID);
	    	}else{
	    		 ServiceResponse sr = new ServiceResponse(CallStatus.serviceSpecificFailure);
		    	 sr.addOutput(new ProcessOutput(
		    				ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Requested bluetooth device not found!"));
	    		 return sr;
	    	}
	    			
	    }
	    
	    ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	    // ProcessOutput-Event that binds the info about sensors
	    sr.addOutput(new ProcessOutput(
			    BiomedicalSensorsServiceProfiles.OUTPUT_SENSOR_INFO,
			    sensorType));
	    // ProcessOutput-Event that binds the discovered service
	    sr.addOutput(new ProcessOutput(
		    BiomedicalSensorsServiceProfiles.OUTPUT_DISCOVERED_SERVICE,
		    btURL));
	    return sr;
	    
    
	    
	} catch (Exception e) {
	    return invalidInput;
	}
    }

    private ServiceResponse startWaitForAlerts(final String bsURI) {
    	try {
    	    // collect the needed data
    		ServiceResponse sr = null;
    		ServiceResponse infoSR=new ServiceResponse();
    	   final  int bsID = extractLocalIDfromSensorURI(bsURI);
    	    String btURL = theServer.getserviceURL(bsID);
    	    if (btURL.equals("")){
    	    	infoSR=getSensorInfo(bsURI);
    	    
    	    if (infoSR.getCallStatus() != CallStatus.succeeded){
    	    	sr =  new ServiceResponse(CallStatus.serviceSpecificFailure);
    	    return sr;
    	    }
    	    }
    	   
    	cronJob=true;
    	    Thread echo = new Thread() {
    			public void run() {
    			int j=0;
    			boolean postureAlert=false;
    			
				//MeasuredEntity[][] last10 = new MeasuredEntity[10][5];
				ArrayList<MeasuredEntity[]> last10 = new ArrayList<MeasuredEntity[]>();
    				while (cronJob){
    					j++;
    					
    				ServiceResponse getMeasSR=getMeasurements(bsURI);
    				try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    				
    				
    				if (getMeasSR.getCallStatus() == CallStatus.succeeded){
    					MeasuredEntity[] me=theServer.getLastMeasurements(bsID);
    					
    					last10.add(me);
    					
    					
    					for (int i=0;i<me.length;i++){
    					System.out.println("SERVER MEASUREMENT: "+me[i].getMeasurementName()
    															 +":"+me[i].getMeasurementValue()
    															 +me[i].getMeasurementUnit());
    				
    				if (me[i].getMeasurementName().equals("Posture"))
    						if (Math.abs(Integer.valueOf(me[i].getMeasurementValue()))>80){
    				
    						 Format formatter;
    						  Date date = new Date();

    					
    						  formatter = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
    						  String formattedTimeNow = formatter.format(date);

    						  /*AlertUI al=new AlertUI(Activator.mc);
        					al.startMainDialog();*/
    						  postureAlert=true;
    						  int measHistory=6;
    						  theServer.setLastMeasurements(last10.get(Math.max(0, last10.size()-measHistory)), bsID);
    						  
    						  for (int  k=Math.max(0, last10.size()-measHistory-1);k<last10.size();k++){
    							  for (int l=0;l<5;l++){
    							  theServer.addLastMeasurement(last10.get(k)[l], bsID);
    							  }
    						  }
    						  MeasuredEntity alertME=new MeasuredEntity(MeasuredEntity.MY_URI, "Posture Alert",
    									"true", "","" ,formattedTimeNow, "","");
    						  theServer.addLastMeasurement(alertME, bsID);
    						  
        				CompositeBiomedicalSensor cbs=new CompositeBiomedicalSensor(bsURI, theServer.getBioSensorConnectionType(bsID),
        						theServer.getBioSensorType(bsID),theServer.getserviceURL(bsID), true,theServer.getLastMeasurements(bsID));
        				if (cronJob)
    						cp.publish(new ContextEvent(cbs, CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS));
    						
        				
    					}else{
    						
    					if (postureAlert){
    						Format formatter;
  						  	Date date = new Date();

  					
  						  formatter = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
  						  String formattedTimeNow = formatter.format(date);

    						MeasuredEntity alertME=new MeasuredEntity(MeasuredEntity.MY_URI, "Posture Alert",
									"false", "","" ,formattedTimeNow, "","");
						  theServer.addLastMeasurement(alertME, bsID);
    				CompositeBiomedicalSensor cbs=new CompositeBiomedicalSensor(bsURI, theServer.getBioSensorConnectionType(bsID),
    						theServer.getBioSensorType(bsID),theServer.getserviceURL(bsID), true,theServer.getLastMeasurements(bsID));
    				if (cronJob)
						cp.publish(new ContextEvent(cbs, CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS));
    					}
    					postureAlert=false;	
    					}
    						
    					
    					}
    						
    					
    					cs=CallStatus.succeeded;
    					
    					System.out.println("WAITING FOR ALERTS("+j+" for local ID: "+bsID);	
    				}else{
    					cs=CallStatus.serviceSpecificFailure;
    					System.out.println("FAILED TO START WAITING FOR ALERTS for local ID: "+bsID);
    				}
    			}
    			}
    			};
    			echo.start();
    			
    	    sr = new ServiceResponse(cs);
    	    
    	    return sr;    	       	    
    	} catch (Exception e) {
    	    return invalidInput;
    	}
        }
    
    private ServiceResponse stopWaitForAlerts(String bsURI) {
    	try {
    	    // collect the needed data
    	    
    	    int bsID = extractLocalIDfromSensorURI(bsURI);
    	    cronJob=false;
    	    System.out.println("STOPPED WAITING FOR ALERTS for local ID: "+bsID);
 
    	    ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
    	    
    	    return sr;
    
    	} catch (Exception e) {
    	    return invalidInput;
    	}
        }
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL
     * .middleware.service.ServiceCall)
     * 
     * Since this class is a child of ServiceCallee it is registered to the
     * service-bus Every service call that passes the restrictions will take
     * affect here Given by the URI of the request we know what specific
     * function we have to call
     */
    public ServiceResponse handleCall(ServiceCall call) {
	if (call == null)
	    return null;

	String operation = call.getProcessURI();
	if (operation == null)
	    return null;

	if (operation
		.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_GET_CONTROLLED_SENSORS))
	    return getControlledBioSensors();

	Object input = call
		.getInputValue(BiomedicalSensorsServiceProfiles.INPUT_SENSOR_URI);
	if (input == null)
	    return null;

	if (operation.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_GET_SENSOR_INFO))
	    return getSensorInfo(input.toString());

	if (operation.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_GET_MEASUREMENTS))
	    return getMeasurements(input.toString());
	if (operation.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_START_WAIT_FOR_ALERTS))
	    return startWaitForAlerts(input.toString());
	if (operation.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_STOP_WAIT_FOR_ALERTS))
	    return stopWaitForAlerts(input.toString());

	return null;
    }

    
    // Publishing events every time the state of a biomedical sensor is changed
    public void BioSensorStateChanged(int lampID, boolean isOn) {
	
    }

    	//ServiceResponse method for getting the measurements
        private ServiceResponse getMeasurements(String bsURI) {
    	int bsID = extractLocalIDfromSensorURI(bsURI);
	    System.out.println("local ID: "+bsID);
	    SensorType sensorType = theServer.getBioSensorType(bsID);
    	if (sensorType.equals(Zephyr.zephyr)){
    	try {
	    //fetch measurements for Bluetooth URL
		
		DEMOBluetoothDeviceDiscovery zeph=new DEMOBluetoothDeviceDiscovery();
		if (!theServer.getserviceURL(extractLocalIDfromSensorURI(bsURI)).equals("")){
			
		boolean gotres=zeph.handleConnection(theServer.getserviceURL(extractLocalIDfromSensorURI(bsURI)));
if (gotres){
	    ArrayList<MeasuredEntity> al = new ArrayList(2);
	    System.out.println("Activity level: "+zeph.activityLevel);
	    System.out.println("Heart Rate: "+zeph.heartRate);
	    System.out.println("Posture: "+zeph.posture);
	    System.out.println("Breathing Rate: "+zeph.breathingRate);
	    System.out.println("Date - Time: "+zeph.formattedTimeNow);
	    ServiceResponse sr=new ServiceResponse(CallStatus.succeeded);
	    
	    MeasuredEntity me=new MeasuredEntity(MeasuredEntity.MY_URI, "Heart Rate",
				String.valueOf(zeph.heartRate), "+/-1", "pulse/min",zeph.formattedTimeNow, "LP72677-5","http://purl.bioontology.org/ontology/LOINC");
	    MeasuredEntity me1=new MeasuredEntity(MeasuredEntity.MY_URI, "Activity Level",
				String.valueOf(zeph.activityLevel), "N/A", "",zeph.formattedTimeNow, "LP115576-3","http://purl.bioontology.org/ontology/LOINC");
	    MeasuredEntity me2=new MeasuredEntity(MeasuredEntity.MY_URI, "Breathing Rate",
				String.valueOf(zeph.breathingRate), "N/A", "min^-1",zeph.formattedTimeNow, "9279-1","http://purl.bioontology.org/ontology/LOINC");
	    MeasuredEntity me3=new MeasuredEntity(MeasuredEntity.MY_URI, "Posture",
				String.valueOf(zeph.posture), "+/-1", "degrees",zeph.formattedTimeNow, "LP120681-4","http://purl.bioontology.org/ontology/LOINC");
	    MeasuredEntity me4=new MeasuredEntity(MeasuredEntity.MY_URI, "Skin Temp",
				String.valueOf(zeph.skinTemperature), "+/-1", "degrees C",zeph.formattedTimeNow, "39106-0","http://purl.bioontology.org/ontology/LOINC");
	    
	    al.add(me);
	    al.add(me2);
	    al.add(me3);
	    al.add(me4);
	    al.add(me1);
	    sr.addOutput(new ProcessOutput(
			    BiomedicalSensorsServiceProfiles.OUTPUT_SENSOR_MEASUREMENTS,
			    al));  
	    theServer.setLastMeasurements(new MeasuredEntity[]{me,me1,me2,me3,me4}, bsID);
	    return sr;
}else{
	 ServiceResponse sr = new ServiceResponse(CallStatus.serviceSpecificFailure);
   	 sr.addOutput(new ProcessOutput(
   				ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "No measurements fetched. Check connection quality."));
   	 System.out.println("No measurements fetched.Got response " + gotres + ". Check connection quality.");
   	 return sr;
}
		}else{
			ServiceResponse sr = new ServiceResponse(CallStatus.serviceSpecificFailure);
		   	 sr.addOutput(new ProcessOutput(
		   				ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Try get info (of the selected sensor) first."));
		   	 return sr;
}
	} catch (Exception e) {
	    return invalidInput;
	}
    }else{
   	 ServiceResponse sr = new ServiceResponse(CallStatus.serviceSpecificFailure);
   	 sr.addOutput(new ProcessOutput(
   				ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Requested service is only for BioHarness Zephyr sensors"));
   	 return sr;
   }
    }
	    
}
