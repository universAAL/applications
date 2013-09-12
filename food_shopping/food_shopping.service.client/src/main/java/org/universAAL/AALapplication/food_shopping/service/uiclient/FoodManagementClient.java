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

package org.universAAL.AALapplication.food_shopping.service.uiclient;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.universAAL.AALapplication.food_shopping.service.uiclient.dialogs.repository.Repository;
import org.universAAL.AALapplication.food_shopping.service.uiclient.dialogs.shopping.Shopping;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.Shopping.FoodManagement;
import org.universAAL.ontology.Shopping.Refrigerator;
import org.universAAL.ontology.Shopping.ShoppingList;
//import org.universAAL.ontology.nutrition.NutritionService;
import org.universAAL.ontology.phThing.Device;


/**
 * @author dimokas
 * 
 */
public class FoodManagementClient extends ContextSubscriber {

	public static Shopping shopping;
	public static Repository repository;
	public static String deviceURI = "http://ontology.universaal.org/FoodManagementServer.owl##controlledDevice0";
	
	private static ServiceCaller caller;
	private static final String FOODMANAGEMENT_CONSUMER_NAMESPACE = "http://ontology.universaal.org/FoodManagementClient.owl#";
	private static final String OUTPUT_LIST_OF_DEVICES = FOODMANAGEMENT_CONSUMER_NAMESPACE + "controlledDevices";
    private static final String OUTPUT_DEVICE_TEMPERATURE = FOODMANAGEMENT_CONSUMER_NAMESPACE + "temperature";
    private static final String OUTPUT_FOOD_ITEMS = FOODMANAGEMENT_CONSUMER_NAMESPACE + "foodItems";
    private static final String OUTPUT_SHOPPING_ITEMS = FOODMANAGEMENT_CONSUMER_NAMESPACE + "shoppingItems";
    private static final String OUTPUT_SHOPPING_LISTS = FOODMANAGEMENT_CONSUMER_NAMESPACE + "shoppingLists";
    private static final String OUTPUT_SHOPPINGLIST_ITEMS = FOODMANAGEMENT_CONSUMER_NAMESPACE + "shoppingListItems";
    private static final String OUTPUT_DEVICE_LOCATION = FOODMANAGEMENT_CONSUMER_NAMESPACE + "location";
    private static final String OUTPUT_DEVICE_STATUS = FOODMANAGEMENT_CONSUMER_NAMESPACE + "status";

    private static Vector values = new Vector(); 
    private static Vector items = new Vector();
	private static Vector quantities = new Vector();
	private static Vector sizes = new Vector();
	private static Vector companies = new Vector();
	private static Vector codes = new Vector();
	private static Vector tags = new Vector();
	private static Vector ins = new Vector();
	private static Vector exps = new Vector();

	private static Device[] devices;
    
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern cep1 = new ContextEventPattern();
		cep1.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, Refrigerator.MY_URI));
		ContextEventPattern cep2 = new ContextEventPattern();
		cep2.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, FoodItem.MY_URI));
		return new ContextEventPattern[] { cep1, cep2 };
	}

	public FoodManagementClient(ModuleContext context) {
		// the constructor register us to the bus
		super(context, getContextSubscriptionParams());
		caller = new DefaultServiceCaller(context);
		shopping = new Shopping(context);
		repository = new Repository(context);
		
		// Initiate available items for shopping list creation
		shopping.setCreateShoppingListItems(initShoppingListCreation());
		shopping.setShoppingLists(shoppingLists());
		shopping.setNutritionProducts(this.getShoppingItems());
	}

	public static Hashtable shoppingLists(){
		String uri = org.universAAL.ontology.Shopping.ShoppingList.MY_URI;
		ShoppingList[] sl = FoodManagementClient.getShoppingLists();

		Hashtable shoppinglists = new Hashtable();
        if (sl != null){
        	for (int i=0; i<sl.length; i++){
        		String item = sl[i].getName();
        		FoodItem[] itemList = FoodManagementClient.getShoppingListItems(uri, item);
        		if (itemList!=null)
        			shoppinglists.put(item, itemList);
        	}
        }
        return shoppinglists;
	}

	private String[] initShoppingListCreation(){
		FoodItem[] fi = FoodManagementClient.getShoppingItems();
		String[] items = {""};
        if (fi != null){
			items = new String[fi.length];
        	for (int i=fi.length-1,j=0; i>=0; i--,j++){
        		String item = fi[i].getCode()+", "+fi[i].getName()+", "+fi[i].getSize()+", "+fi[i].getCompany();
        		items[j]=item;
        	}
        }
        return items;
	}

	/*****************************************************************/
	/* 	Services Requests											 */
	/*****************************************************************/

	private static ServiceRequest turnOffRequest(String deviceURI) {
		ServiceRequest turnOff = new ServiceRequest(new FoodManagement(), null);
		turnOff.addValueFilter(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS }, new Refrigerator(deviceURI));
		turnOff.addChangeEffect(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS,
				Refrigerator.PROP_DEVICE_STATUS }, new Integer(0));
		return turnOff;
	}

	private static ServiceRequest turnOnRequest(String deviceURI) {
		ServiceRequest turnOn = new ServiceRequest(new FoodManagement(), null);
		turnOn.addValueFilter(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS }, new Refrigerator(deviceURI));
		turnOn.addChangeEffect(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS,Refrigerator.PROP_DEVICE_STATUS },
				new Integer(100));
		return turnOn;
	}

	private static ServiceRequest changeTempRequest(String deviceURI, int temp) {
		// Instantiate a ServiceRequest upon the Service Ontology we used
		ServiceRequest changeTemp = new ServiceRequest(new FoodManagement(), null);
		// We add an input. It�s a filtering input: The device we want to set the temperature for
		changeTemp.addValueFilter(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS }, new Refrigerator(deviceURI));
		changeTemp.addChangeEffect(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS,Refrigerator.PROP_TEMPERATURE },new Integer(temp));
		return changeTemp;
	}

	private static ServiceRequest addShoppingListRequest(String shoppingURI, String shoppingListName, String shoppingListDate, String[] shoppingItems) {
/*
		ServiceRequest service = new ServiceRequest(new NutritionService(), null);
		System.out.println("SHOPPING ITEMS = "+shoppingItems.length);

		service.addRequiredOutput(NutritionService.SERVICE_GET_TODAY_MENU_OUTPUT, new String[] { NutritionService.PROP_OBTAINS_MENU });
		return service;
*/		
		
		ServiceRequest addShoppingList = new ServiceRequest(new FoodManagement(), null);
		
		System.out.println("SHOPPING ITEMS = "+shoppingItems.length);
		ArrayList al = new ArrayList(shoppingItems.length);
		for (int i = 0; i < shoppingItems.length; i++){
			al.add(new FoodItem(FoodItem.MY_URI,shoppingItems[i],1));
		}
		
		addShoppingList.addValueFilter(new String[] { FoodManagement.PROP_SHOPPINGLIST_CONTROLS }, new org.universAAL.ontology.Shopping.ShoppingList(shoppingURI));
		addShoppingList.addAddEffect(new String[] { FoodManagement.PROP_SHOPPINGLIST_CONTROLS,org.universAAL.ontology.Shopping.ShoppingList.PROP_NAME }, shoppingListName);
		addShoppingList.addAddEffect(new String[] { FoodManagement.PROP_SHOPPINGLIST_CONTROLS,org.universAAL.ontology.Shopping.ShoppingList.PROP_DATE }, shoppingListDate);
		addShoppingList.addAddEffect(new String[] { FoodManagement.PROP_SHOPPINGLIST_CONTROLS,org.universAAL.ontology.Shopping.ShoppingList.PROP_HAS_FOODITEMS }, al);
		return addShoppingList;
	}

	private static ServiceRequest removeShoppingListRequest(String shoppingURI, String shoppingListName, int shoppingListID) {
		ServiceRequest removeShoppingList = new ServiceRequest(new FoodManagement(), null);
		MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(org.universAAL.ontology.Shopping.ShoppingList.PROP_NAME,shoppingListName);
		removeShoppingList.getRequestedService().addInstanceLevelRestriction(r1, new String[] { FoodManagement.PROP_SHOPPINGLIST_CONTROLS,org.universAAL.ontology.Shopping.ShoppingList.PROP_NAME });
		removeShoppingList.addRemoveEffect(new String[] { FoodManagement.PROP_SHOPPINGLIST_CONTROLS,org.universAAL.ontology.Shopping.ShoppingList.PROP_NAME });

		return removeShoppingList;
	}

	private static ServiceRequest removeFoodItemRequest(String deviceURI, String name) {
		ServiceRequest removeFoodItem = new ServiceRequest(new FoodManagement(), null);
		MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(org.universAAL.ontology.Shopping.FoodItem.PROP_NAME,name);
		removeFoodItem.getRequestedService().addInstanceLevelRestriction(r1, new String[] { FoodManagement.PROP_FRIDGE_CONTROLS,Refrigerator.PROP_HAS_FOODITEMS,org.universAAL.ontology.Shopping.FoodItem.PROP_NAME });
		removeFoodItem.addValueFilter(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS }, new Refrigerator(deviceURI));
		removeFoodItem.addRemoveEffect(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS, FoodItem.PROP_NAME });
		return removeFoodItem;
	}

	private static ServiceRequest getShoppingListItemsRequest(String URI, String shoppingListName) {
		ServiceRequest getShoppingListItems = new ServiceRequest(new FoodManagement(), null);
		getShoppingListItems.addValueFilter(new String[] { FoodManagement.PROP_SHOPPINGLIST_CONTROLS,org.universAAL.ontology.Shopping.ShoppingList.PROP_NAME }, shoppingListName);
		getShoppingListItems.addRequiredOutput(OUTPUT_SHOPPINGLIST_ITEMS, new String[] {
				FoodManagement.PROP_SHOPPINGLIST_CONTROLS, org.universAAL.ontology.Shopping.ShoppingList.PROP_HAS_FOODITEMS });
		return getShoppingListItems;
	}

	private static ServiceRequest addFoodItemRequest(String deviceURI, String name, double quantity, String size, String company, String tagID, String insDate, String expDate) {
		ServiceRequest addFoodItem = new ServiceRequest(new FoodManagement(), null);

		FoodItem anItem = new FoodItem(FoodItem.MY_URI,name,quantity,size,company,tagID,insDate,expDate);
		addFoodItem.addValueFilter(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS }, new Refrigerator(deviceURI));
		addFoodItem.addAddEffect(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS,Refrigerator.PROP_HAS_FOODITEMS }, anItem);
		return addFoodItem;
	}

	private static ServiceRequest updateFoodItemRequest(String deviceURI, String name, double quantity, String size, String company, String tagID, String insDate, String expDate) {
		ServiceRequest updateFoodItem = new ServiceRequest(new FoodManagement(), null);

		FoodItem anItem = new FoodItem(FoodItem.MY_URI,name,quantity,size,company,tagID,insDate,expDate);
		updateFoodItem.addValueFilter(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS }, new Refrigerator(deviceURI));
		updateFoodItem.addChangeEffect(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS,Refrigerator.PROP_HAS_FOODITEMS }, anItem);
		return updateFoodItem;
	}

	
	private static ServiceRequest getDeviceInfoRequest(String deviceURI) {

		ServiceRequest getInfo = new ServiceRequest(new FoodManagement(), null);
		getInfo.addValueFilter(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS },new Refrigerator(deviceURI));
		getInfo.addRequiredOutput(OUTPUT_DEVICE_TEMPERATURE, new String[] {
				FoodManagement.PROP_FRIDGE_CONTROLS, Refrigerator.PROP_TEMPERATURE });
		getInfo.addRequiredOutput(OUTPUT_DEVICE_STATUS, new String[] {
				FoodManagement.PROP_FRIDGE_CONTROLS, Refrigerator.PROP_DEVICE_STATUS });

		getInfo.addRequiredOutput(OUTPUT_DEVICE_LOCATION, new String[] {
				FoodManagement.PROP_FRIDGE_CONTROLS, Refrigerator.PROP_PHYSICAL_LOCATION });
	
		return getInfo;
    }

	private static ServiceRequest getFoodItemsRequest(String deviceURI) {
		ServiceRequest getFoodItems = new ServiceRequest(new FoodManagement(), null);
		getFoodItems.addValueFilter(new String[] { FoodManagement.PROP_FRIDGE_CONTROLS },new Refrigerator(deviceURI));
		getFoodItems.addRequiredOutput(OUTPUT_FOOD_ITEMS, new String[] {
				FoodManagement.PROP_FRIDGE_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS });
	
		return getFoodItems;
    }

	private static ServiceRequest getShoppingItemsRequest() {
		ServiceRequest getShoppingItems = new ServiceRequest(new FoodManagement(), null);
		getShoppingItems.addRequiredOutput(OUTPUT_SHOPPING_ITEMS, new String[] {FoodManagement.PROP_SHOPPINGLIST_CONTROLS,ShoppingList.PROP_HAS_FOODITEMS,FoodItem.PROP_NAME });
		return getShoppingItems;
    }

	private static ServiceRequest getShoppingListsRequest() {
		ServiceRequest getShoppingLists = new ServiceRequest(new FoodManagement(), null);
		getShoppingLists.addRequiredOutput(OUTPUT_SHOPPING_LISTS, new String[] {FoodManagement.PROP_SHOPPINGLIST_CONTROLS,ShoppingList.PROP_NAME });
		return getShoppingLists;
    }

	private static ServiceRequest getTodayMenuRequest() {
		ServiceRequest getShoppingLists = new ServiceRequest(new FoodManagement(), null);
		getShoppingLists.addRequiredOutput(OUTPUT_SHOPPING_LISTS, new String[] {FoodManagement.PROP_SHOPPINGLIST_CONTROLS,ShoppingList.PROP_NAME });
		return getShoppingLists;
    }

	private static ServiceRequest getWeeklyMenuRequest() {
		ServiceRequest getShoppingLists = new ServiceRequest(new FoodManagement(), null);
		getShoppingLists.addRequiredOutput(OUTPUT_SHOPPING_LISTS, new String[] {FoodManagement.PROP_SHOPPINGLIST_CONTROLS,ShoppingList.PROP_NAME });
		return getShoppingLists;
    }

	public static ServiceRequest getAllDevicesRequest() {
		ServiceRequest getAllDevices = new ServiceRequest(new FoodManagement(),	null);

		getAllDevices.addRequiredOutput(OUTPUT_LIST_OF_DEVICES,	new String[] { FoodManagement.PROP_FRIDGE_CONTROLS });

		return getAllDevices;
	}

	public static Device[] getControlledDevices() {

		ServiceResponse sr = caller.call(getAllDevicesRequest());

		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				List deviceList = sr.getOutput(OUTPUT_LIST_OF_DEVICES, true);

				if (deviceList == null || deviceList.size() == 0) {
					//LogUtils.logInfo(Activator.logger,
					//		"FoodManagementConsumer", "getControlledDevices",
					//		new Object[] { "there are no devices" }, null);
					return null;
				}

				devices = (Device[]) deviceList.toArray(new Device[deviceList.size()]);

				return devices;

			} catch (Exception e) {
				e.printStackTrace();
				//LogUtils.logError(Activator.logger, "FoodManagementConsumer",
				//		"getControlledDevices", new Object[] { "got exception",
				//				e.getMessage() }, e);
				return null;
			}
		} else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"getControlledDevices",
			//		new Object[] { "callstatus is not succeeded" }, null);
			return null;
		}
	}

	public static boolean addFoodItems(String deviceURI, String foodItem, double quantity, String size, String company, String tagID, String insDate, String expDate) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"addFoodItems", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		ServiceResponse sr = caller.call(addFoodItemRequest(deviceURI,foodItem,quantity,size,company,tagID,insDate,expDate));
		//LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
		//		"addFoodItems", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"addFoodItems", new Object[] { "The addition of food item "+ foodItem +" failed" }, null);
			return false;
		}    
    }

	public static boolean updateFoodItems(String deviceURI, String foodItem, double quantity, String size, String company, String tagID, String insDate, String expDate) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"updateFoodItems", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		//System.out.println("Client is going to update food item "+foodItem);
		ServiceResponse sr = caller.call(updateFoodItemRequest(deviceURI,foodItem,quantity,size,company,tagID,insDate,expDate));
		//LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
		//		"updateFoodItems", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"updateFoodItems", new Object[] { "The update of food item "+ foodItem +" failed" }, null);
			return false;
		}    
    }

	public static boolean removeFoodItems(String deviceURI, String foodItem) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"removeFoodItems", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		//System.out.println("Client is going to remove food item "+foodItem);
		ServiceResponse sr = caller.call(removeFoodItemRequest(deviceURI,foodItem));
		//LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
		//		"removeFoodItems", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"removeFoodItems", new Object[] { "The deletion of food item "+ foodItem +" failed" }, null);
			return false;
		}    
    }

	public static boolean addShoppingList(String shoppingURI, String shoppingListName, String shoppingListDate, String[] shoppinglist) {
		if ((shoppingURI == null) || !(shoppingURI instanceof String)) {
			return false;
		}
		ServiceResponse sr = caller.call(addShoppingListRequest(shoppingURI,shoppingListName,shoppingListDate,shoppinglist));

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else
			return false;
    }

	public static boolean updateShoppingList(String shoppingURI, String shoppingListName, String shoppingListDate, String[] shoppinglist) {
		if ((shoppingURI == null) || !(shoppingURI instanceof String)) {
			return false;
		}
		ServiceResponse sr = caller.call(addShoppingListRequest(shoppingURI,shoppingListName,shoppingListDate,shoppinglist));

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else
			return false;
    }

	public static boolean removeShoppingList(String shoppingURI, String shoppingListName, int shoppinglistID) {
		if ((shoppingURI == null) || !(shoppingURI instanceof String)) {
			return false;
		}
		ServiceResponse sr = caller.call(removeShoppingListRequest(shoppingURI,shoppingListName,shoppinglistID));

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else
			return false;
    }

	public static FoodItem[] getShoppingListItems(String shoppingURI, String shoppingListName) {
		if ((shoppingURI == null) || !(shoppingURI instanceof String)) {
			return null;
		}
		ServiceResponse sr = caller.call(getShoppingListItemsRequest(shoppingURI,shoppingListName));

    	FoodItem[] fi = null;
		if (sr.getCallStatus() == CallStatus.succeeded){
		    List shoppingItemsList = sr.getOutput(OUTPUT_SHOPPINGLIST_ITEMS, true);
			if (shoppingItemsList == null || shoppingItemsList.size() == 0)
			    return null;
			try{
				//System.out.println("shoppingItemsList="+shoppingItemsList);
				if (shoppingItemsList.get(0) instanceof FoodItem)
					fi = (FoodItem[])shoppingItemsList.toArray(new FoodItem[shoppingItemsList.size()]);
			}
			catch (java.lang.ArrayStoreException ase){
				ase.printStackTrace();
				return null;
			}
			return fi;
		}
		else
			return null;
    }
	
	// This method change the temperature of the device and returns 
	// true if the operation was successfully or false otherwise.
    public static boolean changeTemperature(String deviceURI, int temp) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"changeTemperature", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		if (temp<-18 || temp>20){
			return false;
		}
		
		ServiceResponse sr = caller.call(changeTempRequest(deviceURI,temp));
		//LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
		//		"changeTemperature", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"changeTemperature", new Object[] { "The modification of temperature failed" }, null);
			return false;
		}    
    }

    public static boolean getDeviceInfo(String deviceURI) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"getDeviceInfo", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(getDeviceInfoRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
		//		"getDeviceInfo", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		// check the call status and return true if succeeded
		if (sr.getCallStatus() == CallStatus.succeeded){
		    //System.out.println("GOT OUTPUTS " + sr.getOutputs().toString());
		    //System.out.println("GOT TEMPERATURE " + sr.getOutput(OUTPUT_DEVICE_TEMPERATURE, true).toString());
		    //System.out.println("GOT LOCATION " + sr.getOutput(OUTPUT_DEVICE_LOCATION, true).toString());
		    //System.out.println("GOT STATUS " + sr.getOutput(OUTPUT_DEVICE_STATUS, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_TEMPERATURE, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_LOCATION, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_STATUS, true).toString());
		    
			return true;
		}
		else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"getDeviceInfo", new Object[] { "The device information request failed" }, null);
			return false;
		}    
    }
    
    public static FoodItem[] getRefrigeratorItems(String deviceURI) {
    	FoodItem[] fi = null;

    	if ((deviceURI == null) || !(deviceURI instanceof String)) {
			return null;
		}
		
		ServiceResponse sr = caller.call(getFoodItemsRequest(deviceURI));
		if (sr.getCallStatus() == CallStatus.succeeded){
		    List foodItemsList = sr.getOutput(OUTPUT_FOOD_ITEMS, true);
			if (foodItemsList == null || foodItemsList.size() == 0) {
			    return null;
			}

			try{
				fi = (FoodItem[])foodItemsList.toArray(new FoodItem[foodItemsList.size()]);
			}
			catch (java.lang.ArrayStoreException ase){
				//ase.printStackTrace();
				return null;
			}
			return fi;
		}
		else
			return null;
    }

    public static FoodItem[] getShoppingItems() {
    	FoodItem[] fi = null;
    	
		ServiceResponse sr = caller.call(getShoppingItemsRequest());

		if (sr.getCallStatus() == CallStatus.succeeded){
		    List shoppingItemsList = sr.getOutput(OUTPUT_SHOPPING_ITEMS, true);
		    
			if (shoppingItemsList == null || shoppingItemsList.size() == 0)
			    return null;
			try{
			    fi = (FoodItem[])shoppingItemsList.toArray(new FoodItem[shoppingItemsList.size()]);
			}
			catch (java.lang.ArrayStoreException ase){
				ase.printStackTrace();
				return null;
			}
			return fi;
		}
		else
			return null;
    }

    public static ShoppingList[] getShoppingLists() {
    	ShoppingList[] sl = null;
    	
		ServiceResponse sr = caller.call(getShoppingListsRequest());

		if (sr.getCallStatus() == CallStatus.succeeded){
		    List shoppingItemsList = sr.getOutput(OUTPUT_SHOPPING_LISTS, true);
			if (shoppingItemsList == null || shoppingItemsList.size() == 0)
			    return null;
			try{
			    sl = (ShoppingList[])shoppingItemsList.toArray(new ShoppingList[shoppingItemsList.size()]);
			}
			catch (java.lang.ArrayStoreException ase){
				ase.printStackTrace();
				return null;
			}
			return sl;
		}
		else
			return null;
    }
    
    public static boolean getFoodItems(String deviceURI) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"getFoodItems", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(getFoodItemsRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
		//		"getFoodItems", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded){
		    List foodItemsList = sr.getOutput(OUTPUT_FOOD_ITEMS, true);
		    
			if (foodItemsList == null || foodItemsList.size() == 0) {
				//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
				//		"getFoodItems", new Object[] { "there are no food items" }, null);				
			    return false;
			}
		    
			try{
			    FoodItem[] fi = (FoodItem[])foodItemsList.toArray(new FoodItem[foodItemsList.size()]);
			    items.clear(); items=new Vector();
		    	quantities.clear(); quantities=new Vector();
		    	sizes.clear(); sizes=new Vector();
		    	companies.clear(); companies=new Vector();
		    	codes.clear(); codes=new Vector();
			    tags.clear(); tags=new Vector();
			    ins.clear(); ins=new Vector();
			    exps.clear(); exps=new Vector();
			    
			    for (int i=0; i<fi.length; i++){
			    	items.add(fi[i].getName());
			    	quantities.add(""+fi[i].getQuantity());
			    	sizes.add(fi[i].getSize());
			    	companies.add(fi[i].getCompany());
			    	codes.add(""+fi[i].getCode());
			    	tags.add(fi[i].getTagID());
			    	ins.add(fi[i].getInsDate());
			    	exps.add(fi[i].getExpDate());
			    	//System.out.println("Tag="+fi[i].getTagID());
			    	//System.out.println("Ins="+fi[i].getInsDate());
			    }
			}
			catch (java.lang.ArrayStoreException ase){
				return false;
			}
			return true;
		}
		else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"getDeviceInfo", new Object[] { "The device information request failed" }, null);
			return false;
		}    
    }
    
	public static boolean turnOff(String deviceURI) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"turnOff", new Object[] { "wrong deviceURI" }, null);
			return false;
		}

		// make a call with the appropriate request
		ServiceResponse sr = caller.call(turnOffRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
		//		"turnOff", new Object[] { "Call status: ",
		//				sr.getCallStatus().name() }, null);

		// check the call status and return true if succeeded
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"turnOff",
			//		new Object[] { "the device couldn't be turned off" }, null);
			return false;
		}
	}

	public static boolean turnOn(String deviceURI) {

		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer", 
			//		"turnOn", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(turnOnRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "FoodManagementConsumer", "turnOn",
		//		new Object[] { "Call device status: ", sr.getCallStatus().name() }, null);
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
			//		"turnOn", new Object[] { "the device couldn't be turned on" }, null);
			return false;
		}
	}

	public static boolean getTodayMenu() {
		ServiceResponse sr = caller.call(getTodayMenuRequest());

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else
			return false;
    }

	public static boolean getWeeklyMenu() {
		ServiceResponse sr = caller.call(getWeeklyMenuRequest());

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else
			return false;
    }
	
	public void handleContextEvent(ContextEvent event) {
/*
		LogUtils.logInfo(Activator.logger, "FoodManagementConsumer",
				"handleContextEvent", new Object[] {
						"Received context event:\n", "    Subject     = ",
						event.getSubjectURI(), "\n", "    Subject type= ",
						event.getSubjectTypeURI(), "\n", "    Predicate   = ",
						event.getRDFPredicate(), "\n", "    Object      = ",
						event.getRDFObject() }, null);
*/		
		System.out.println("------------ RECEIVED CONTEXT EVENT -------------");
			//this.getFoodItems(FoodManagementClient.devices[0].getURI());
			//FoodManagementUIClient.getItems(FoodManagementClient.devices[0]);
		if (event.getRDFObject() instanceof FoodItem[]){
			FoodItem[] lfi = (FoodItem[])event.getRDFObject();
			for (int i=0; i<lfi.length; i++){
				String name = lfi[i].getName();
				String size = lfi[i].getSize();
				String company = lfi[i].getCompany();
				System.out.println(name+", "+size+", "+company);
			}
			//FoodManagementUIClient.getItems(lfi);

			repository.startMainDialog(lfi);
		}
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	public static Vector getValues(){
		return values;
	}
    public static Vector getItems() {
		return items;
	}
	public static Vector getQuantities() {
		return quantities;
	}
	public static Vector getSizes() {
		return sizes;
	}
	public static Vector getCompanies() {
		return companies;
	}
	public static Vector getCodes() {
		return codes;
	}
	public static Vector getTags() {
		return tags;
	}
	public static Vector getIns() {
		return ins;
	}
	public static Vector getExps() {
		return exps;
	}

}
