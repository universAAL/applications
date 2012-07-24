package org.universAAL.AALapplication.safety_home.service.server;

import java.util.ArrayList;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.Intersection;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.TypeURI;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.safetyDevices.Door;
import org.universAAL.ontology.safetyDevices.Window;
import org.universAAL.ontology.safetyDevices.LightSensor;
import org.universAAL.ontology.safetyDevices.TemperatureSensor;
import org.universAAL.ontology.safetyDevices.HumiditySensor;
import org.universAAL.ontology.safetyDevices.MotionSensor;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.AALapplication.safety_home.service.server.unit_impl.DeviceStateListener;
import org.universAAL.AALapplication.safety_home.service.server.unit_impl.MyDevices;

public class SafetyProvider extends ServiceCallee implements DeviceStateListener {
	
	private static final ServiceResponse invalidInput = new ServiceResponse(CallStatus.serviceSpecificFailure);
	static {
		invalidInput.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
	}

	static final String DEVICE_URI_PREFIX = SafetyService.SAFETY_SERVER_NAMESPACE + "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";

    private static String constructDeviceURIfromLocalID(int localID) {
    	return DEVICE_URI_PREFIX + localID;
    }

    private static String constructLocationURIfromLocalID(String localID) {
    	return LOCATION_URI_PREFIX + localID;
    }

    private static int extractLocalIDfromDeviceURI(String deviceURI) {
    	return Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()));
    }
    
	private MyDevices theServer;
	private ModuleContext context;
	private ContextPublisher cp;
	private ContextPublisher lightPublisher;
	private ContextProvider lightInfo;
	private ContextPublisher windowPublisher;
	private ContextProvider windowInfo;
	private ContextPublisher humidityPublisher;
	private ContextProvider humidityInfo;
	private ContextPublisher motionPublisher;
	private ContextProvider motionInfo;
	private ContextPublisher smartCardPublisher;
	private ContextProvider smartCardInfo;
	private ContextPublisher smokeDetectionPublisher;
	private ContextProvider smokeDetectionInfo;
	private ContextPublisher temperatureSensorPublisher;
	private ContextProvider temperatureSensorInfo;

    private static ContextEventPattern[] providedEvents(MyDevices theServer) {
    	MergedRestriction predicateRestriction = MergedRestriction
		.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE,
			Door.PROP_DEVICE_STATUS);

		MergedRestriction objectRestriction = MergedRestriction.getAllValuesRestrictionWithCardinality(
		ContextEvent.PROP_RDF_OBJECT, new Enumeration(new Integer[] { new Integer(0),new Integer(100) }), 1, 1);

		Door[] mySafetyManagement = getAllDevices(theServer);
	
		MergedRestriction subjectRestriction = MergedRestriction.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_SUBJECT, new Enumeration(mySafetyManagement), 1, 1);

		ContextEventPattern cep1 = new ContextEventPattern();
		cep1.addRestriction(subjectRestriction);
		cep1.addRestriction(predicateRestriction);
		cep1.addRestriction(objectRestriction);

		Intersection xsection = new Intersection();
		xsection.addType(new TypeURI(Door.MY_URI, false));
		xsection.addType(MergedRestriction.getAllValuesRestrictionWithCardinality(
				Door.PROP_PHYSICAL_LOCATION, Room.MY_URI, 1, 1));
		subjectRestriction = MergedRestriction.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_SUBJECT, xsection, 1, 1);

		ContextEventPattern cep2 = new ContextEventPattern();
		cep2.addRestriction(subjectRestriction);
		cep2.addRestriction(predicateRestriction);
		cep2.addRestriction(objectRestriction);

		return new ContextEventPattern[] { cep1, cep2 };
    }

    private static Door[] getAllDevices(MyDevices theServer) {
		int[] devs = theServer.getDeviceIDs();
		Door[] result = new Door[devs.length];
		for (int i = 0; i < devs.length; i++)
		    result[i] = new Door(constructDeviceURIfromLocalID(devs[i]),
		    new Room(constructLocationURIfromLocalID(theServer.getDeviceLocation(devs[i]))));
		return result;
    }
	
	SafetyProvider(ModuleContext context) {
		super(context, SafetyService.profiles);
		this.context = context;
		theServer = new MyDevices();
		theServer.addListener(this);
/*
		// start humidity context publisher 
		this.humidityInfo = new ContextProvider(SafetyService.SAFETY_SERVER_NAMESPACE + "HumidityContextProvider");
		this.humidityInfo.setType(ContextProviderType.controller);
		this.humidityInfo.setProvidedEvents(providedEvents(theServer));
		this.humidityPublisher = new DefaultContextPublisher(context, this.humidityInfo);
		this.humidityModule();

		// start light context publisher 
		this.lightInfo = new ContextProvider(SafetyService.SAFETY_SERVER_NAMESPACE + "LightContextProvider");
		this.lightInfo.setType(ContextProviderType.controller);
		this.lightInfo.setProvidedEvents(providedEvents(theServer));
		this.lightPublisher = new DefaultContextPublisher(context, this.lightInfo);
		this.lightModule();

		// start motion context publisher 
		this.motionInfo = new ContextProvider(SafetyService.SAFETY_SERVER_NAMESPACE + "MotionContextProvider");
		this.motionInfo.setType(ContextProviderType.controller);
		this.motionInfo.setProvidedEvents(providedEvents(theServer));
		this.motionPublisher = new DefaultContextPublisher(context, this.motionInfo);
		this.motionModule();

		// start smart card context publisher 
		this.smartCardInfo = new ContextProvider(SafetyService.SAFETY_SERVER_NAMESPACE + "SmartCardContextProvider");
		this.smartCardInfo.setType(ContextProviderType.controller);
		this.smartCardInfo.setProvidedEvents(providedEvents(theServer));
		this.smartCardPublisher = new DefaultContextPublisher(context, this.smartCardInfo);
		this.smartCardModule();

		// start smoke detection context publisher 
		this.smokeDetectionInfo = new ContextProvider(SafetyService.SAFETY_SERVER_NAMESPACE + "SmokeDetectionContextProvider");
		this.smokeDetectionInfo.setType(ContextProviderType.controller);
		this.smokeDetectionInfo.setProvidedEvents(providedEvents(theServer));
		this.smokeDetectionPublisher = new DefaultContextPublisher(context, this.smokeDetectionInfo);
		this.smokeModule();

		// start temperature context publisher 
		this.temperatureSensorInfo = new ContextProvider(SafetyService.SAFETY_SERVER_NAMESPACE + "TemperatureSensorContextProvider");
		this.temperatureSensorInfo.setType(ContextProviderType.controller);
		this.temperatureSensorInfo.setProvidedEvents(providedEvents(theServer));
		this.temperatureSensorPublisher = new DefaultContextPublisher(context, this.temperatureSensorInfo);
		this.temperatureModule();

		// start window context publisher 
		this.windowInfo = new ContextProvider(SafetyService.SAFETY_SERVER_NAMESPACE + "WindowContextProvider");
		this.windowInfo.setType(ContextProviderType.controller);
		this.windowInfo.setProvidedEvents(providedEvents(theServer));
		this.windowPublisher = new DefaultContextPublisher(context, this.windowInfo);
		this.windowModule();
*/
	}
/*
	public void humidityModule() {
		new Thread() {
			public void run() {
				System.out.println("Humidity Sensor Provider started ...");
				new org.universAAL.AALapplication.safety_home.service.humiditySensorProvider.CPublisher(context, humidityInfo, humidityPublisher);
			}
		}.start();
	}

	public void lightModule() {
		new Thread() {
			public void run() {
				System.out.println("Light Sensor Provider started ...");
				new org.universAAL.AALapplication.safety_home.service.lightSensorProvider.CPublisher(context, lightInfo, lightPublisher);
			}
		}.start();
	}

	public void motionModule() {
		new Thread() {
			public void run() {
				System.out.println("Motion Sensor Provider started ...");
				new org.universAAL.AALapplication.safety_home.service.motionSensorProvider.CPublisher(context, motionInfo, motionPublisher);
			}
		}.start();
	}

	public void smartCardModule() {
		new Thread() {
			public void run() {
				System.out.println("Smart Card Provider started ...");
				new org.universAAL.AALapplication.safety_home.service.smartCardProvider.CPublisher(context, smartCardInfo, smartCardPublisher);
			}
		}.start();
	}

	public void smokeModule() {
		new Thread() {
			public void run() {
				System.out.println("Smoke Detection Provider started ...");
				new org.universAAL.AALapplication.safety_home.service.smokeDetectionProvider.CPublisher(context, smokeDetectionInfo, smokeDetectionPublisher);
			}
		}.start();
	}

	public void temperatureModule() {
		new Thread() {
			public void run() {
				System.out.println("Temperature Sensor Provider started ...");
				new org.universAAL.AALapplication.safety_home.service.temperatureSensorProvider.CPublisher(context, temperatureSensorInfo, temperatureSensorPublisher);
			}
		}.start();
	}
	
	public void windowModule() {
		new Thread() {
			public void run() {
				System.out.println("Window Provider started ...");
				new org.universAAL.AALapplication.safety_home.service.windowProvider.CPublisher(context, windowInfo, windowPublisher);
			}
		}.start();
	}
*/	
	public ServiceResponse handleCall(ServiceCall call) {
		if (call == null)
			return null;

		String operation = call.getProcessURI();
		if (operation == null)
			return null;
		if (operation.startsWith(SafetyService.SERVICE_GET_CONTROLLED_DEVICES)){
			System.out.println("Server requested for: SERVICE_GET_CONTROLLED_DEVICES");
			return getControlledDevices();
		}
		Object input = call.getInputValue(SafetyService.INPUT_DEVICE_URI);
		if (input == null)
			return null;
		if (operation.startsWith(SafetyService.SERVICE_GET_DEVICE_INFO)){
			System.out.println("Server requested for: SERVICE_GET_DEVICE_INFO");
			return getDeviceInfo(input.toString());
		}
		if (operation.startsWith(SafetyService.SERVICE_LOCK)){
			System.out.println("Server requested for: SERVICE_LOCK");
			return lock(input.toString());
		}
		if (operation.startsWith(SafetyService.SERVICE_UNLOCK)){
			System.out.println("Server requested for: SERVICE_UNLOCK");
			return unlock(input.toString());
		}
		if (operation.startsWith(SafetyService.SERVICE_OPEN)){
			System.out.println("Server requested for: DOOR OPEN");
			return open(input.toString());
		}
		if (operation.startsWith(SafetyService.SERVICE_CLOSE)){
			System.out.println("Server requested for: DOOR CLOSE");
			return close(input.toString());
		}
		
		return null;
	}

	
	public void communicationChannelBroken() {
	}

	// create a service response that including all available devices
	private ServiceResponse getControlledDevices() {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		int[] devices = theServer.getDeviceIDs();
		
		ArrayList al = new ArrayList(devices.length);
		for (int i = 0; i < devices.length; i++){
			if(i==0){
				al.add(new Door(DEVICE_URI_PREFIX + devices[i]));
			}
			if(i==1){
				al.add(new Window(DEVICE_URI_PREFIX + devices[i]));
			}
			if(i==2){
				al.add(new LightSensor(DEVICE_URI_PREFIX + devices[i]));
			}
			if(i==3){
				al.add(new TemperatureSensor(DEVICE_URI_PREFIX + devices[i]));
			}
			if(i==4){
				al.add(new HumiditySensor(DEVICE_URI_PREFIX + devices[i]));
			}
			if(i==5){
				al.add(new MotionSensor(DEVICE_URI_PREFIX + devices[i]));
			}
		}
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of devices
		sr.addOutput(new ProcessOutput(SafetyService.OUTPUT_CONTROLLED_DEVICES, al));
		return sr;
	}

	// create a service response with informations about the available devices
	private ServiceResponse getDeviceInfo(String deviceURI) {
		try {
			// collect the needed data
			int deviceID = Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()));
			String loc = theServer.getDeviceLocation(deviceID);
			int state = theServer.isOn(deviceID) ? 100 : 0;
			// We assume that the Service-Call always succeeds because we only simulate the device
			ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
			// create and add a ProcessOutput-Event that binds the output URI to the state of the device
			sr.addOutput(new ProcessOutput(SafetyService.OUTPUT_DEVICE_STATUS,
					new Integer(state)));
			// create and add a ProcessOutput-Event that binds the output URI to the location of the device
			sr.addOutput(new ProcessOutput(SafetyService.OUTPUT_DEVICE_LOCATION,
					new Room(LOCATION_URI_PREFIX + loc)));
	
			return sr;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return invalidInput;
		}
	}

	// with Living Lab lock
	private ServiceResponse lock(String deviceURI) {
		try {
			// virtual lock
/*
			boolean res = true;
			if (theServer.lock(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				return new ServiceResponse(CallStatus.succeeded);
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
*/			
			// Living Lab lock
			boolean res = true;
			if (theServer.lock(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				res = theServer.lock();
			if (res)
				return new ServiceResponse(CallStatus.succeeded);
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
		}
		catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse unlock(String deviceURI) {
		try {
			// virtual unlock	
/*
			boolean res = true;
			if (theServer.unlock(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())))){
				//deviceStateChanged(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), ""+theServer.getDeviceLocation(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))), true);
				return new ServiceResponse(CallStatus.succeeded);
			}
			else{
				//deviceStateChanged(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), ""+theServer.getDeviceLocation(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))), false);
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
			}
*/
			// Living Lab unlock	
			boolean res = true;
			if (theServer.unlock(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				res = theServer.unlock();
			if (res)
				return new ServiceResponse(CallStatus.succeeded);
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
		} 
		catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse open(String deviceURI) {
		try {
			// virtual unlock
/*			
			boolean isOpen = true;
			if (theServer.open(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				return new ServiceResponse(CallStatus.succeeded);	
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
*/
			// Living Lab open
			boolean isOpen = true;
			if (theServer.open(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				isOpen = theServer.open();
			if (isOpen)
				return new ServiceResponse(CallStatus.succeeded);	
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
		} 
		catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse close(String deviceURI) {
		try {
			boolean isOpen = false;
			if (theServer.close(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				return new ServiceResponse(CallStatus.succeeded);	
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
		} 
		catch (Exception e) {
			return invalidInput;
		}
	}

	/*
	 * 
	 * Context Publisher functionality - Publishing Context events 
	 *	
	 * To demonstrate the functionality of the context bus we publish an event
	 * for every time the value of a door is changed
	 */
	public void deviceStateChanged(int deviceID, String loc, boolean value) {
		
		Device device=null;
		if(deviceID==0){
			Door door = new Door(SafetyProvider.DEVICE_URI_PREFIX + deviceID);
			device=(Device)door;
			door.setDeviceLocation(new Room(SafetyProvider.LOCATION_URI_PREFIX + loc));
			door.setStatus(value? 100: 0);
			//cp.publish(new ContextEvent(door, Door.PROP_DEVICE_STATUS));
			
/*
			LogUtils
				.logInfo(
						Activator.logger,
						"SafetyProvider",
						"DeviceStateChanged",
						new Object[] { "publishing a context event on the state of a Door!" },
						null);
*/
			// finally create an context event and publish it with the door as
			// subject and the property that changed as predicate
			//cp.publish(new ContextEvent(door, Door.PROP_DEVICE_STATUS));
		}	
	}
}
