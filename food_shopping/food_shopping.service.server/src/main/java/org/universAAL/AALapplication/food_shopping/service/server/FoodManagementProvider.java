package org.universAAL.AALapplication.food_shopping.service.server;

import java.util.ArrayList;
import java.util.Hashtable;
//import java.util.Enumeration;

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
import org.universAAL.AALapplication.ont.foodDevices.FoodItem;
import org.universAAL.AALapplication.ont.foodDevices.Refrigerator;
import org.universAAL.AALapplication.ont.foodDevices.ShoppingList;
import org.universAAL.ontology.location.indoor.Room;
//import org.universAAL.ontology.phThing.Device;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.CodeFoodItem;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.DeviceFoodItem;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.DeviceStateListener;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.MyDevices;
import org.universAAL.AALapplication.food_shopping.service.RFidProvider.CPublisher;

public class FoodManagementProvider extends ServiceCallee implements DeviceStateListener {
	
	public static CPublisher rfidProvider = null;
	private static final ServiceResponse invalidInput = new ServiceResponse(CallStatus.serviceSpecificFailure);
	static {
		invalidInput.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
	}

	//static final String SHOPPINGITEM_URI_PREFIX = ProvidedFoodManagementService.FOODMANAGEMENT_SERVER_NAMESPACE + "shoppingItem";
	static final String DEVICE_URI_PREFIX = ProvidedFoodManagementService.FOODMANAGEMENT_SERVER_NAMESPACE + "controlledDevice";
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
	private ContextPublisher cp;
    
    /**
     * Helper method to construct the ontological declaration of context events
     * published by FoodManagementProvider.
	*/
    private static ContextEventPattern[] providedEvents(MyDevices theServer) {
	// the LightingProvioder publishes its context events only from within
	// "lampStateChanged()" below
	// Assumption 1: "theServer" below controls only a pre-determined set of
	// light sources without any dynamic changes
	// Assumption 2: light sources controlled by "theServer" below might
	// change dynamically; new light sources can always be added and
	// existing ones might disappear and even might come back again
	// however, the following is for both alternatives equal, namely

	// 1) that the event is always about the change of brightness
	MergedRestriction predicateRestriction = MergedRestriction
		.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE,
			Refrigerator.PROP_DEVICE_STATUS);

	// and 2) that the reported value will always be either 0 or 100
	MergedRestriction objectRestriction = MergedRestriction.getAllValuesRestrictionWithCardinality(
		ContextEvent.PROP_RDF_OBJECT, new Enumeration(new Integer[] { new Integer(0),new Integer(100) }), 1, 1);

	// now we demonstrate how each of the two alternatives discussed above
	// would work for describing the subjects of our context events

	// let's start with the variant 1 under the assumption 1
	// in this case, we can say that the subject of the context events is
	// always a member of a given set
	// in order to build this set, we must first fetch the set members from
	// a helper method
	Refrigerator[] myFoodManagement = getAllDevices(theServer);
	
	// the following is to say that the subject of my context events is
	// always one single member of the above array
	MergedRestriction subjectRestriction = MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_SUBJECT, new Enumeration(myFoodManagement), 1, 1);

	// now we can close the first variant by creating a ContextEventPattern
	// and adding the above restrictions to it
	ContextEventPattern cep1 = new ContextEventPattern();
	cep1.addRestriction(subjectRestriction);
	cep1.addRestriction(predicateRestriction);
	cep1.addRestriction(objectRestriction);

	// now, let's switch to the variant 2 under the assumption 2 for an
	// alternative way of describing the subject part

	// the subject of these context events is always an instance of
	// LightSource of type "light bulb" whose property srcLocation is always
	// an instance of Room
	Intersection xsection = new Intersection();
	xsection.addType(new TypeURI(Refrigerator.MY_URI, false));
	xsection.addType(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
				Refrigerator.PROP_PHYSICAL_LOCATION, Room.MY_URI, 1, 1));
	subjectRestriction = MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_SUBJECT, xsection, 1, 1);

	// now we can close the second variant as well, by creating a
	// ContextEventPattern and adding the above restrictions to it
	ContextEventPattern cep2 = new ContextEventPattern();
	cep2.addRestriction(subjectRestriction);
	cep2.addRestriction(predicateRestriction);
	cep2.addRestriction(objectRestriction);

	// we must actually make a decision and return only one of the above
	// alternatives, but here we return both in order to indicate that
	// context providers might provide different classes of context events
	// and hence might be forced to return several such descriptions of
	// their events
		return new ContextEventPattern[] { cep1, cep2 };
    }

    private static Refrigerator[] getAllDevices(MyDevices theServer) {
	int[] devs = theServer.getDeviceIDs();
	Refrigerator[] result = new Refrigerator[devs.length];
	for (int i = 0; i < devs.length; i++)
	    result[i] = new Refrigerator(
	    constructDeviceURIfromLocalID(devs[i]),
	    new Room(constructLocationURIfromLocalID(theServer.getDeviceLocation(devs[i]))));
	return result;
    }

    
	FoodManagementProvider(ModuleContext context) {
		// The parent need to know the profiles of the available functions to register them
		super(context, ProvidedFoodManagementService.profiles);
		// prepare for context publishing
		ContextProvider info = new ContextProvider(ProvidedFoodManagementService.FOODMANAGEMENT_SERVER_NAMESPACE
						+ "FoodManagementContextProvider");
		theServer = new MyDevices();
		info.setType(ContextProviderType.controller);
		info.setProvidedEvents(providedEvents(theServer));
		cp = new DefaultContextPublisher(context, info);
		//this.rfidReaderModule();
		// initialize the helper class that will save the available devices (their number is defined in MyDevices)
		theServer.addListener(this);
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
	}

	private ServiceResponse getControlledDevices() {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);

		int[] devices = theServer.getDeviceIDs();
		//System.out.println("num of devices = "+devices.length);
		ArrayList al = new ArrayList(devices.length);
		for (int i = 0; i < devices.length; i++){
			if(i==0){
				al.add(new Refrigerator(DEVICE_URI_PREFIX + devices[i]));
			}
		}

		sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_CONTROLLED_DEVICES, al));
		return sr;
	}

/* virtual
	private ServiceResponse getFoodItems(String deviceURI) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		int deviceID = Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()));
		Hashtable fooditems = theServer.getFoodItems(deviceID);
		ArrayList fi = new ArrayList(fooditems.size());
		
		java.util.Enumeration en = (java.util.Enumeration)fooditems.keys();
		while (en.hasMoreElements()){
			String key = (String)en.nextElement();
			DeviceFoodItem dfi = (DeviceFoodItem)fooditems.get(key);
			//fi.add(new FoodItem(FoodItem.MY_URI,dfi.getName(),dfi.getQuantity()));
		}

		sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_FOOD_ITEMS, fi));
		return sr;
	}
*/
	
	private ServiceResponse getFoodItems(String deviceURI) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		int deviceID = Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()));
		Hashtable fooditems = theServer.getDBFoodItems(deviceID);
		ArrayList fi = new ArrayList(fooditems.size());
		
		java.util.Enumeration en = (java.util.Enumeration)fooditems.keys();
		while (en.hasMoreElements()){
			String key = (String)en.nextElement();
			DeviceFoodItem dfi = (DeviceFoodItem)fooditems.get(key);
			//System.out.println("----------------------------------------------------DB Items------------------------------------------------------");
			//System.out.println(dfi.getName()+", "+dfi.getQuantity()+", "+dfi.getSize()+", "+dfi.getCompany()+", "+dfi.getCode()+", "+dfi.getTagID());
			fi.add(new FoodItem(FoodItem.MY_URI,dfi.getName(),dfi.getQuantity(),dfi.getSize(),dfi.getCompany(),dfi.getTagID(),dfi.getInsDate(),dfi.getExpDate()));
		}

		sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_FOOD_ITEMS, fi));
		return sr;
	}

	private ServiceResponse getShoppingItems() {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		Hashtable fooditems = theServer.getDBCodeItems();
		ArrayList foodList = new ArrayList(fooditems.size());
		
		java.util.Enumeration en = (java.util.Enumeration)fooditems.keys();
		while (en.hasMoreElements()){
			String key = (String)en.nextElement();
			FoodItem fi = (FoodItem)fooditems.get(key);
			foodList.add(fi);
		}
		sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_SHOPPING_ITEMS, foodList));
		return sr;
	}
	
	private ServiceResponse getShoppingListItems(String shoppingListName) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		Hashtable fooditems = theServer.getDBShoppingListItems(shoppingListName);
		ArrayList foodList = new ArrayList(fooditems.size());
		
		java.util.Enumeration en = (java.util.Enumeration)fooditems.keys();
		while (en.hasMoreElements()){
			String key = (String)en.nextElement();
			FoodItem fi = (FoodItem)fooditems.get(key);
			//System.out.println("Food Item = "+fi.getName());
			foodList.add(fi);
		}
		sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_SHOPPINGLIST_ITEMS, foodList));
		return sr;
	}

	private ServiceResponse getShoppingLists() {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		Hashtable shoppinglists = theServer.getDBShoppingLists();
		ArrayList shoppingList = new ArrayList(shoppinglists.size());
		
		java.util.Enumeration en = (java.util.Enumeration)shoppinglists.keys();
		while (en.hasMoreElements()){
			String key = (String)en.nextElement();
			ShoppingList fi = (ShoppingList)shoppinglists.get(key);
			shoppingList.add(fi);
		}
		sr.addOutput(new ProcessOutput(ProvidedFoodManagementService.OUTPUT_SHOPPING_LISTS, shoppingList));
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
	
	private ServiceResponse addShoppingList(String shoppingURI, String shoppingName, String shoppingDate, ArrayList shoppingItems) {
		try {
			theServer.addShoppingListToDB(shoppingURI,shoppingName,shoppingDate,shoppingItems);
			return new ServiceResponse(CallStatus.succeeded);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return invalidInput;
		}
	}

	private ServiceResponse removeShoppingList(String shoppingURI, String shoppingName) {
		try {
			boolean res = theServer.removeShoppingListFromDB(shoppingURI,shoppingName);
			if (res)
				return new ServiceResponse(CallStatus.succeeded);
			else
				return invalidInput;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return invalidInput;
		}
	}

/*	virtual
	private ServiceResponse addFoodItem(String deviceURI, FoodItem inputFoodItem) {
		try {
			System.out.println("Adding new item");
			System.out.println("Device URI = " + deviceURI);
			System.out.println("New food Item = " + inputFoodItem.getName() + ", Quantity = "+ inputFoodItem.getQuantity());
			theServer.addFoodItem(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), inputFoodItem);
			return new ServiceResponse(CallStatus.succeeded);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return invalidInput;
		}
	}
*/
	private ServiceResponse addFoodItem(String deviceURI, FoodItem inputFoodItem) {
		try {
			theServer.addFoodItemToDB(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), inputFoodItem);
			return new ServiceResponse(CallStatus.succeeded);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return invalidInput;
		}
	}

/*	virtual	
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
*/
	private ServiceResponse removeFoodItem(String deviceURI, String inputFoodItem) {
		try {
			theServer.removeFoodItemFromDB(Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length())), inputFoodItem);
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
		//System.out.println(operation);
		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_GET_CONTROLLED_DEVICES))
			return getControlledDevices();

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_GET_SHOPPING_ITEMS))
			return getShoppingItems();

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_GET_SHOPPING_LISTS))
			return getShoppingLists();
		
		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_ADD_SHOPPINGLIST)){
		    Object inputShoppingList = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGLIST_URI);
		    Object inputShoppingName = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGNAME_URI);
		    Object inputShoppingDate = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGDATE_URI);
		    Object inputItems = call.getInputValue(ProvidedFoodManagementService.INPUT_FOOD_ITEMS);
		    if (inputShoppingList == null || inputItems == null)
		    	return invalidInput;
		    if (inputShoppingName == null || inputShoppingDate == null)
		    	return invalidInput;
		    	
			return addShoppingList(inputShoppingList.toString(),inputShoppingName.toString(),inputShoppingDate.toString(),(ArrayList)inputItems);
		}

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_REMOVE_SHOPPINGLIST)){
		    Object inputShoppingList = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGLIST_URI);
		    Object inputShoppingName = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGNAME_URI);
		    if (inputShoppingList == null || inputShoppingName == null)
		    	return invalidInput;
		
			return removeShoppingList(inputShoppingList.toString(),inputShoppingName.toString());
		}
		
		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_GET_SHOPPINGLISTITEMS)){
		    Object inputShoppingList = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGLIST_URI);
		    Object inputShoppingName = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGNAME_URI);
		    if (inputShoppingList == null)
		    	return invalidInput;
		
			return getShoppingListItems(inputShoppingName.toString());
		}
		
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

	public void rfidReaderModule() {
		new Thread() {
			public void run() {
				rfidProvider = new CPublisher(cp);
			}
		}.start();
	}

	public void deviceStateChanged(int deviceID, String loc, boolean isOn) {
		
		//Device device=null;
		if(deviceID==0){
			Refrigerator refrigerator = new Refrigerator(FoodManagementProvider.DEVICE_URI_PREFIX + deviceID);
			//device=(Device)refrigerator;
			refrigerator.setDeviceLocation(new Room(FoodManagementProvider.LOCATION_URI_PREFIX
					+ loc));
			refrigerator.setStatus(isOn? 100 : 0);
			 LogUtils.logInfo(
						Activator.mc,
						FoodManagementProvider.class,
						"lampStateChanged",
						new Object[] { "publishing a context event on the state of a Refrigerator!" },
						null);
			// finally create an context event and publish it with the refrigerator as
			// subject and the property that changed as predicate
			cp.publish(new ContextEvent(refrigerator, Refrigerator.PROP_DEVICE_STATUS));
		}	
	}
}
