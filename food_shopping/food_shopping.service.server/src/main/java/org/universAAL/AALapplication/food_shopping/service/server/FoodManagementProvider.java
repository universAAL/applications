/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

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
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.Shopping.Refrigerator;
import org.universAAL.ontology.Shopping.ShoppingList;
import org.universAAL.ontology.location.indoor.Room;
//import org.universAAL.ontology.phThing.Device;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.CodeFoodItem;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.DeviceFoodItem;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.DeviceStateListener;
import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.MyDevices;
import org.universAAL.AALapplication.food_shopping.service.RFidProvider.CPublisher;

/**
 * @author dimokas
 * 
 */

public class FoodManagementProvider extends ServiceCallee implements DeviceStateListener {
	
	public static CPublisher rfidProvider = null;
	private static final ServiceResponse invalidInput = new ServiceResponse(CallStatus.serviceSpecificFailure);
	static {
		invalidInput.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
	}

	//static final String SHOPPINGITEM_URI_PREFIX = ProvidedFoodManagementService.FOODMANAGEMENT_SERVER_NAMESPACE + "shoppingItem";
	static final String DEVICE_URI_PREFIX = ProvidedFoodManagementService.FOODMANAGEMENT_SERVER_NAMESPACE + "controlledDevice0";
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
	MergedRestriction predicateRestriction = MergedRestriction
		.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE,
			Refrigerator.PROP_DEVICE_STATUS);

	MergedRestriction objectRestriction = MergedRestriction.getAllValuesRestrictionWithCardinality(
		ContextEvent.PROP_RDF_OBJECT, new Enumeration(new Integer[] { new Integer(0),new Integer(100) }), 1, 1);

	Refrigerator[] myFoodManagement = getAllDevices(theServer);
	
	MergedRestriction subjectRestriction = MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_SUBJECT, new Enumeration(myFoodManagement), 1, 1);

	ContextEventPattern cep1 = new ContextEventPattern();
	cep1.addRestriction(subjectRestriction);
	cep1.addRestriction(predicateRestriction);
	cep1.addRestriction(objectRestriction);

	Intersection xsection = new Intersection();
	xsection.addType(new TypeURI(Refrigerator.MY_URI, false));
	xsection.addType(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
				Refrigerator.PROP_PHYSICAL_LOCATION, Room.MY_URI, 1, 1));
	subjectRestriction = MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			ContextEvent.PROP_RDF_SUBJECT, xsection, 1, 1);

	ContextEventPattern cep2 = new ContextEventPattern();
	cep2.addRestriction(subjectRestriction);
	cep2.addRestriction(predicateRestriction);
	cep2.addRestriction(objectRestriction);

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
		super(context, ProvidedFoodManagementService.profiles);
		ContextProvider info = new ContextProvider(ProvidedFoodManagementService.FOODMANAGEMENT_SERVER_NAMESPACE
						+ "FoodManagementContextProvider");
		theServer = new MyDevices();
		info.setType(ContextProviderType.controller);
		info.setProvidedEvents(providedEvents(theServer));
		cp = new DefaultContextPublisher(context, info);
		this.rfidReaderModule();
		theServer.addListener(this);
	}

	public void communicationChannelBroken() {
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
		System.out.println(">>> deviceURI="+deviceURI);
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

	private ServiceResponse getDeviceInfo(String deviceURI) {
		try {
			// collect the needed data
			int deviceID = Integer.parseInt(deviceURI.substring(DEVICE_URI_PREFIX.length()));
			String loc = theServer.getDeviceLocation(deviceID);
			int temp = theServer.getDeviceTemperature(deviceID);
			int state = theServer.isOn(deviceID) ? 100 : 0;
			ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
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
			LogUtils.logInfo(Activator.mc,	FoodManagementProvider.class,	"addShoppingList",
					new Object[] { "Food Shopping Service added/updated shopping list" + shoppingName }, null);

			return new ServiceResponse(CallStatus.succeeded);
		} 
		catch (Exception e) {
			LogUtils.logError(Activator.mc,	FoodManagementProvider.class,	"addShoppingList",
					new Object[] { "Food Shopping Service did not add/updat shopping list" + shoppingName }, null);
			e.printStackTrace();
			return invalidInput;
		}
	}

	private ServiceResponse removeShoppingList(String shoppingName) {
		try {
			boolean res = theServer.removeShoppingListFromDB(shoppingName);
			if (res){
				LogUtils.logInfo(Activator.mc,	FoodManagementProvider.class,	"removeShoppingList",
						new Object[] { "Food Shopping Service removed shopping list" + shoppingName }, null);
				return new ServiceResponse(CallStatus.succeeded);
			}
			else{
				LogUtils.logInfo(Activator.mc,	FoodManagementProvider.class,	"removeShoppingList",
						new Object[] { "Food Shopping Service did not remove shopping list" + shoppingName }, null);
				return invalidInput;
			}
		} 
		catch (Exception e) {
			LogUtils.logError(Activator.mc,	FoodManagementProvider.class,	"removeShoppingList",
					new Object[] { "Food Shopping Service removed shopping list" + shoppingName }, null);
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
		    
		    if (inputItems instanceof FoodItem){ // Only one food item in shopping list
		    	ArrayList al = new ArrayList();
		    	FoodItem fi = (FoodItem)inputItems;
		    	al.add(fi);
		    	return addShoppingList(inputShoppingList.toString(),inputShoppingName.toString(),inputShoppingDate.toString(),al);
		    }
		    else if (inputItems instanceof ArrayList) // Many food items in shopping list
		    	return addShoppingList(inputShoppingList.toString(),inputShoppingName.toString(),inputShoppingDate.toString(),(ArrayList)inputItems);
		}

		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_REMOVE_SHOPPINGLIST)){
		    Object inputShoppingName = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGNAME_URI);
		    if (inputShoppingName == null)
		    	return invalidInput;

			return removeShoppingList(inputShoppingName.toString());
		}
		
		if (operation.startsWith(ProvidedFoodManagementService.SERVICE_GET_SHOPPINGLISTITEMS)){
		    Object inputShoppingName = call.getInputValue(ProvidedFoodManagementService.INPUT_SHOPPINGNAME_URI);
		    //System.out.println(inputShoppingName.toString());
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
