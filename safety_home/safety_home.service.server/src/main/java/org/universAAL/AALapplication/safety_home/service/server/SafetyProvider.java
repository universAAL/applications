package org.universAAL.AALapplication.safety_home.service.server;

import java.util.ArrayList;

import org.osgi.framework.BundleContext;
//import org.universAAL.middleware.context.ContextEvent;
//import org.universAAL.middleware.context.ContextPublisher;
//import org.universAAL.middleware.context.DefaultContextPublisher;
//import org.universAAL.middleware.context.owl.ContextProvider;
//import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.util.LogUtils;
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
	
	static final String DEVICE_URI_PREFIX = SafetyService.SAFETY_SERVER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";

	private static final ServiceResponse invalidInput = new ServiceResponse(CallStatus.serviceSpecificFailure);

	static {
		invalidInput.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
	}

	private MyDevices theServer;
	//private ContextPublisher cp;

	SafetyProvider(BundleContext context) {
		super(context, SafetyService.profiles);
/*
		ContextProvider info = new ContextProvider(SafetyService.SAFETY_SERVER_NAMESPACE
						+ "SafetyContextProvider");
		info.setType(ContextProviderType.controller);
		cp = new DefaultContextPublisher(context, info);
*/		
		theServer = new MyDevices();
		theServer.addListener(this);
	}

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
			boolean res = true;
			if (theServer.lock(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				return new ServiceResponse(CallStatus.succeeded);
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
			
			// Living Lab lock
/*			boolean res = true;
			if (theServer.lock(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				res = theServer.lock();
			if (res)
				return new ServiceResponse(CallStatus.succeeded);
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
*/
		}
		catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse unlock(String deviceURI) {
		try {
			// virtual unlock	
			boolean res = true;
			if (theServer.unlock(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())))){
				//deviceStateChanged(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), ""+theServer.getDeviceLocation(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))), true);
				return new ServiceResponse(CallStatus.succeeded);
			}
			else{
				//deviceStateChanged(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), ""+theServer.getDeviceLocation(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))), false);
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
			}

			// Living Lab unlock	
/*			boolean res = true;
			if (theServer.unlock(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				res = theServer.unlock();
			if (res)
				return new ServiceResponse(CallStatus.succeeded);
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
*/
		} 
		catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse open(String deviceURI) {
		try {
			boolean isOpen = true;
			if (theServer.open(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				return new ServiceResponse(CallStatus.succeeded);	
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);

			// Living Lab open
/*			boolean isOpen = true;
			if (theServer.open(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()))))
				isOpen = theServer.open();
			if (isOpen)
				return new ServiceResponse(CallStatus.succeeded);	
			else
				return new ServiceResponse(CallStatus.serviceSpecificFailure);
*/				
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
