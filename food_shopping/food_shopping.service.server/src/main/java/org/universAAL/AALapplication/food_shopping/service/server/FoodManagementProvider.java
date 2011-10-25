package org.universAAL.AALapplication.food_shopping.service.server;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.util.LogUtils;
import org.universAAL.ontology.foodDevices.FoodItem;
import org.universAAL.ontology.foodDevices.Refrigerator;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.DeviceFoodItem;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.DeviceStateListener;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.MyDevices;

public class FoodManagementProvider extends ServiceCallee implements DeviceStateListener {
	
	static final String DEVICE_URI_PREFIX = ProvidedFoodManagementService.FOODMANAGEMENT_SERVER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";

	private static final ServiceResponse invalidInput = new ServiceResponse(CallStatus.serviceSpecificFailure);

	static {
		invalidInput.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
	}

	private MyDevices theServer;
	private ContextPublisher cp;

	FoodManagementProvider(BundleContext context) {
		// The parent need to know the profiles of the available functions to register them
		super(context, ProvidedFoodManagementService.profiles);
		// prepare for context publishing
		ContextProvider info = new ContextProvider(ProvidedFoodManagementService.FOODMANAGEMENT_SERVER_NAMESPACE
						+ "FoodManagementContextProvider");
		info.setType(ContextProviderType.controller);
		cp = new DefaultContextPublisher(context, info);

		// initialize the helper class that will save the available devices (their number is defined in MyDevices)
		theServer = new MyDevices();
		theServer.addListener(this);
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
	}

	private ServiceResponse getControlledDevices() {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);

		int[] devices = theServer.getDeviceIDs();
		
		ArrayList al = new ArrayList(devices.length);
		for (int i = 0; i < devices.length; i++){
			if(i==0){
				al.add(new Refrigerator(DEVICE_URI_PREFIX + devices[i]));
			}
		}

		sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_CONTROLLED_DEVICES, al));
		return sr;
	}

	private ServiceResponse getFoodItems(String deviceURI) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		int deviceID = Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()));
		Hashtable fooditems = theServer.getFoodItems(deviceID);
		ArrayList fi = new ArrayList(fooditems.size());
		
		Enumeration en = (Enumeration)fooditems.keys();
		while (en.hasMoreElements()){
			String key = (String)en.nextElement();
			DeviceFoodItem dfi = (DeviceFoodItem)fooditems.get(key);
			fi.add(new FoodItem(FoodItem.MY_URI,dfi.getName(),dfi.getQuantity()));
		}

		sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_FOOD_ITEMS, fi));
		return sr;
	}

	
	// create a service response with informations about the available devices
	private ServiceResponse getDeviceInfo(String deviceURI) {
		try {
			// collect the needed data
			int deviceID = Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()));
			String loc = theServer.getDeviceLocation(deviceID);
			int temp = theServer.getDeviceTemperature(deviceID);
			int state = theServer.isOn(deviceID) ? 100 : 0;
			// We assume that the Service-Call always succeeds because we only simulate the device
			ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
			// create and add a ProcessOutput-Event that binds the output URI to the state of the device
			sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_DEVICE_STATUS,
					new Integer(state)));
			// create and add a ProcessOutput-Event that binds the output URI to the location of the device
			sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_DEVICE_LOCATION,
					new Room(LOCATION_URI_PREFIX + loc)));
			sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_DEVICE_TEMPERATURE,
					new Integer(temp)));
			
			return sr;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return invalidInput;
		}
	}

	private ServiceResponse turnOff(String deviceURI) {
		try {
			int deviceID = Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()));
			theServer.turnOff(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())));
			return new ServiceResponse(CallStatus.succeeded);
		}
		catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse turnOn(String deviceURI) {
		try {
			theServer.turnOn(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())));
			return new ServiceResponse(CallStatus.succeeded);
		} 
		catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse changeTemperature(String deviceURI, Integer inputTemp) {
		try {
			theServer.changeTemperature(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), inputTemp.intValue());
			return new ServiceResponse(CallStatus.succeeded);
		} 
		catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse addFoodItem(String deviceURI, FoodItem inputFoodItem) {
		try {
			theServer.addFoodItem(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), inputFoodItem);
			return new ServiceResponse(CallStatus.succeeded);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return invalidInput;
		}
	}

	private ServiceResponse removeFoodItem(String deviceURI, String inputFoodItem) {
		try {
			theServer.removeFoodItem(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), inputFoodItem);
			return new ServiceResponse(CallStatus.succeeded);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return invalidInput;
		}
	}
	
	public ServiceResponse handleCall(ServiceCall call) {
		
		if (call == null)
			return null;

		String operation = call.getProcessURI();
		if (operation == null)
			return null;

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_GET_CONTROLLED_DEVICES))
			return getControlledDevices();

		Object input = call.getInputValue(ProvidedFoodManagementService.INPUT_DEVICE_URI);
		if (input == null)
			return null;

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_GET_DEVICE_INFO))
			return getDeviceInfo(input.toString());

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_TURN_OFF))
			return turnOff(input.toString());

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_TURN_ON))
			return turnOn(input.toString());

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_TEMPERATURE)){
		    Object inputDevice = call.getInputValue(ProvidedFoodManagementService.INPUT_DEVICE_URI);
		    Object inputTemp = call.getInputValue(ProvidedFoodManagementService.INPUT_TEMP_URI);
		    if (inputDevice == null || inputTemp == null || !(inputTemp instanceof Integer))
		    	return invalidInput;
		
			return changeTemperature(inputDevice.toString(),((Integer)inputTemp).intValue());
		}
		
		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_ADD_FOODITEM)){
		    Object inputDevice = call.getInputValue(ProvidedFoodManagementService.INPUT_DEVICE_URI);
		    Object inputFoodItem = call.getInputValue(ProvidedFoodManagementService.INPUT_FOODITEM_URI);
		    if (inputDevice == null || inputFoodItem == null || !(inputFoodItem instanceof FoodItem))
		    	return invalidInput;
		
			return addFoodItem(inputDevice.toString(),(FoodItem)inputFoodItem);
		}

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_REMOVE_FOODITEM)){
		    Object inputDevice = call.getInputValue(ProvidedFoodManagementService.INPUT_DEVICE_URI);
		    Object inputFoodItem = call.getInputValue(ProvidedFoodManagementService.INPUT_FOODITEM_URI);
		    if (inputDevice == null || inputFoodItem == null || !(inputFoodItem instanceof String))
		    	return invalidInput;
		
			return removeFoodItem(inputDevice.toString(),(String)inputFoodItem);
		}

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_GET_FOOD_ITEMS)){
		    Object inputDevice = call.getInputValue(ProvidedFoodManagementService.INPUT_DEVICE_URI);
		    if (inputDevice == null)
		    	return invalidInput;

			return getFoodItems(inputDevice.toString());
		}

		return null;
	}

	
	public void deviceStateChanged(int deviceID, String loc, boolean isOn) {
		
		Device device=null;
		if(deviceID==0){
			Refrigerator refrigerator = new Refrigerator(FoodManagementProvider.DEVICE_URI_PREFIX + deviceID);
			device=(Device)refrigerator;
			refrigerator.setDeviceLocation(new Room(FoodManagementProvider.LOCATION_URI_PREFIX
					+ loc));
			refrigerator.setStatus(isOn? 100 : 0);
			 LogUtils
				.logInfo(
						Activator.logger,
						"FoodManagementProvider",
						"DeviceStateChanged",
						new Object[] { "publishing a context event on the state of a Refrigerator!" },
						null);
			// finally create an context event and publish it with the refrigerator as
			// subject and the property that changed as predicate
			cp.publish(new ContextEvent(refrigerator, Refrigerator.PROP_DEVICE_STATUS));
		}	
	}
}
