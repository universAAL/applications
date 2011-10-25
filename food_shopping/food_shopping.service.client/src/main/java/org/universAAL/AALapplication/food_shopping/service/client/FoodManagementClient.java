package org.universAAL.AALapplication.food_shopping.service.client;

import org.universAAL.ontology.phThing.Device;

import java.util.List;
import java.util.Vector;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.util.LogUtils;
import org.universAAL.ontology.foodDevices.FoodManagement;
import org.universAAL.ontology.foodDevices.FoodItem;
import org.universAAL.ontology.foodDevices.Refrigerator;


/**
 * @author dimokas
 * 
 */
class FoodManagementClient extends ContextSubscriber {

	private static ServiceCaller caller;

	private static final String FOODMANAGEMENT_CONSUMER_NAMESPACE = "http://ontology.universaal.org/FoodManagementClient.owl#";

	private static final String OUTPUT_LIST_OF_DEVICES = FOODMANAGEMENT_CONSUMER_NAMESPACE
			+ "controlledDevices";
	
    private static final String OUTPUT_DEVICE_TEMPERATURE = FOODMANAGEMENT_CONSUMER_NAMESPACE + "temperature";
    private static final String OUTPUT_FOOD_ITEMS = FOODMANAGEMENT_CONSUMER_NAMESPACE + "foodItems";
    private static final String OUTPUT_DEVICE_LOCATION = FOODMANAGEMENT_CONSUMER_NAMESPACE + "location";
    private static final String OUTPUT_DEVICE_STATUS = FOODMANAGEMENT_CONSUMER_NAMESPACE + "status";

    private static Vector values = new Vector(); 
    private static Vector items = new Vector();
	private static Vector quantities = new Vector();
    
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(Restriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, Refrigerator.MY_URI));
		return new ContextEventPattern[] { cep };
	}

	FoodManagementClient(BundleContext context) {
		// the constructor register us to the bus
		super(context, getContextSubscriptionParams());

		caller = new DefaultServiceCaller(context);
		new FoodManagementUIClient(getControlledDevices());
	}

	/*****************************************************************/
	/* 	Services Requests											 */
	/*****************************************************************/

	private static ServiceRequest turnOffRequest(String deviceURI) {
		ServiceRequest turnOff = new ServiceRequest(new FoodManagement(), null);
		turnOff.addValueFilter(new String[] { FoodManagement.PROP_CONTROLS }, new Refrigerator(deviceURI));
		turnOff.addChangeEffect(new String[] { FoodManagement.PROP_CONTROLS,
				Refrigerator.PROP_DEVICE_STATUS }, new Integer(0));
		return turnOff;
	}

	private static ServiceRequest turnOnRequest(String deviceURI) {
		ServiceRequest turnOn = new ServiceRequest(new FoodManagement(), null);
		turnOn.addValueFilter(new String[] { FoodManagement.PROP_CONTROLS }, new Refrigerator(deviceURI));
		turnOn.addChangeEffect(new String[] { FoodManagement.PROP_CONTROLS,Refrigerator.PROP_DEVICE_STATUS },
				new Integer(100));
		return turnOn;
	}

	private static ServiceRequest changeTempRequest(String deviceURI, int temp) {
		// Instantiate a ServiceRequest upon the Service Ontology we used
		ServiceRequest changeTemp = new ServiceRequest(new FoodManagement(), null);
		// We add an input. It´s a filtering input: The device we want to set the temperature for
		changeTemp.addValueFilter(new String[] { FoodManagement.PROP_CONTROLS }, new Refrigerator(deviceURI));
		changeTemp.addChangeEffect(new String[] { FoodManagement.PROP_CONTROLS,Refrigerator.PROP_TEMPERATURE },new Integer(temp));
		return changeTemp;
	}

	private static ServiceRequest addFoodItemRequest(String deviceURI, String name, int quantity) {
		ServiceRequest addFoodItem = new ServiceRequest(new FoodManagement(), null);

		FoodItem anItem = new FoodItem(FoodItem.MY_URI,name,quantity);
		addFoodItem.addValueFilter(new String[] { FoodManagement.PROP_CONTROLS }, new Refrigerator(deviceURI));
		addFoodItem.addChangeEffect(new String[] { FoodManagement.PROP_CONTROLS,Refrigerator.PROP_HAS_FOODITEMS }, anItem);
		return addFoodItem;
	}

	private static ServiceRequest updateFoodItemRequest(String deviceURI, String name, int quantity) {
		ServiceRequest updateFoodItem = new ServiceRequest(new FoodManagement(), null);

		FoodItem anItem = new FoodItem(FoodItem.MY_URI,name,quantity);
		updateFoodItem.addValueFilter(new String[] { FoodManagement.PROP_CONTROLS }, new Refrigerator(deviceURI));
		updateFoodItem.addChangeEffect(new String[] { FoodManagement.PROP_CONTROLS,Refrigerator.PROP_HAS_FOODITEMS }, anItem);
		return updateFoodItem;
	}

	private static ServiceRequest removeFoodItemRequest(String deviceURI, String name) {
		ServiceRequest removeFoodItem = new ServiceRequest(new FoodManagement(), null);

		removeFoodItem.addValueFilter(new String[] { FoodManagement.PROP_CONTROLS }, new Refrigerator(deviceURI));
		removeFoodItem.addChangeEffect(new String[] { FoodManagement.PROP_CONTROLS,Refrigerator.PROP_HAS_FOODITEMS }, name);
		return removeFoodItem;
	}

	private static ServiceRequest getDeviceInfoRequest(String deviceURI) {

		ServiceRequest getInfo = new ServiceRequest(new FoodManagement(), null);
		getInfo.addValueFilter(new String[] { FoodManagement.PROP_CONTROLS },new Refrigerator(deviceURI));
		getInfo.addRequiredOutput(OUTPUT_DEVICE_TEMPERATURE, new String[] {
				FoodManagement.PROP_CONTROLS, Refrigerator.PROP_TEMPERATURE });
		getInfo.addRequiredOutput(OUTPUT_DEVICE_STATUS, new String[] {
				FoodManagement.PROP_CONTROLS, Refrigerator.PROP_DEVICE_STATUS });

		getInfo.addRequiredOutput(OUTPUT_DEVICE_LOCATION, new String[] {
				FoodManagement.PROP_CONTROLS, Refrigerator.PROP_DEVICE_LOCATION });
	
		return getInfo;
    }

	private static ServiceRequest getFoodItemsRequest(String deviceURI) {
		ServiceRequest getFoodItems = new ServiceRequest(new FoodManagement(), null);
		getFoodItems.addValueFilter(new String[] { FoodManagement.PROP_CONTROLS },new Refrigerator(deviceURI));
		getFoodItems.addRequiredOutput(OUTPUT_FOOD_ITEMS, new String[] {
				FoodManagement.PROP_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS });
	
		return getFoodItems;
    }

	
	public static ServiceRequest getAllDevicesRequest() {
		ServiceRequest getAllDevices = new ServiceRequest(new FoodManagement(),	null);

		getAllDevices.addRequiredOutput(
				OUTPUT_LIST_OF_DEVICES,
				new String[] { FoodManagement.PROP_CONTROLS });

		return getAllDevices;
	}

	public static Device[] getControlledDevices() {

		ServiceResponse sr = caller.call(getAllDevicesRequest());

		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				List deviceList = sr.getOutput(OUTPUT_LIST_OF_DEVICES, true);

				if (deviceList == null || deviceList.size() == 0) {
					LogUtils.logInfo(Activator.logger,
							"FoodManagementConsumer", "getControlledDevices",
							new Object[] { "there are no devices" }, null);
					return null;
				}

				Device[] devices = (Device[]) deviceList.toArray(new Device[deviceList.size()]);

				return devices;

			} catch (Exception e) {
				e.printStackTrace();
				LogUtils.logError(Activator.logger, "FoodManagementConsumer",
						"getControlledDevices", new Object[] { "got exception",
								e.getMessage() }, e);
				return null;
			}
		} else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"getControlledDevices",
					new Object[] { "callstatus is not succeeded" }, null);
			return null;
		}
	}

	public static boolean addFoodItems(String deviceURI, String foodItem, int quantity) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"addFoodItems", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		ServiceResponse sr = caller.call(addFoodItemRequest(deviceURI,foodItem,quantity));
		LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
				"addFoodItems", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"addFoodItems", new Object[] { "The addition of food item "+ foodItem +" failed" }, null);
			return false;
		}    
    }

	public static boolean updateFoodItems(String deviceURI, String foodItem, int quantity) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"updateFoodItems", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		System.out.println("Client is going to update food item "+foodItem);
		ServiceResponse sr = caller.call(updateFoodItemRequest(deviceURI,foodItem,quantity));
		LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
				"updateFoodItems", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"updateFoodItems", new Object[] { "The update of food item "+ foodItem +" failed" }, null);
			return false;
		}    
    }

	public static boolean removeFoodItems(String deviceURI, String foodItem) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"removeFoodItems", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		System.out.println("Client is going to remove food item "+foodItem);
		ServiceResponse sr = caller.call(removeFoodItemRequest(deviceURI,foodItem));
		LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
				"removeFoodItems", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"removeFoodItems", new Object[] { "The deletion of food item "+ foodItem +" failed" }, null);
			return false;
		}    
    }

	// This method change the temperature of the device and returns 
	// true if the operation was successfully or false otherwise.
    public static boolean changeTemperature(String deviceURI, int temp) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"changeTemperature", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		if (temp<-18 || temp>20){
			return false;
		}
		
		ServiceResponse sr = caller.call(changeTempRequest(deviceURI,temp));
		LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
				"changeTemperature", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"changeTemperature", new Object[] { "The modification of temperature failed" }, null);
			return false;
		}    
    }

    public static boolean getDeviceInfo(String deviceURI) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"getDeviceInfo", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(getDeviceInfoRequest(deviceURI));
		LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
				"getDeviceInfo", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		// check the call status and return true if succeeded
		if (sr.getCallStatus() == CallStatus.succeeded){
		    System.out.println("GOT OUTPUTS " + sr.getOutputs().toString());
		    System.out.println("GOT TEMPERATURE " + sr.getOutput(OUTPUT_DEVICE_TEMPERATURE, true).toString());
		    System.out.println("GOT LOCATION " + sr.getOutput(OUTPUT_DEVICE_LOCATION, true).toString());
		    System.out.println("GOT STATUS " + sr.getOutput(OUTPUT_DEVICE_STATUS, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_TEMPERATURE, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_LOCATION, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_STATUS, true).toString());
		    
			return true;
		}
		else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"getDeviceInfo", new Object[] { "The device information request failed" }, null);
			return false;
		}    
    }
    
    public static boolean getFoodItems(String deviceURI) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"getFoodItems", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(getFoodItemsRequest(deviceURI));
		LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
				"getFoodItems", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded){
		    List foodItemsList = sr.getOutput(OUTPUT_FOOD_ITEMS, true);
		    
			if (foodItemsList == null || foodItemsList.size() == 0) {
				LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
						"getFoodItems", new Object[] { "there are no food items" }, null);				
			    return false;
			}
		    
			try{
			    FoodItem[] fi = (FoodItem[])foodItemsList.toArray(new FoodItem[foodItemsList.size()]);
			    for (int i=0; i<fi.length; i++){
			    	items.add(fi[i].getName());
			    	quantities.add(""+fi[i].getQuantity());
			    }
			}
			catch (java.lang.ArrayStoreException ase){
				return false;
			}
			return true;
		}
		else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"getDeviceInfo", new Object[] { "The device information request failed" }, null);
			return false;
		}    
    }
    
	public static boolean turnOff(String deviceURI) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"turnOff", new Object[] { "wrong deviceURI" }, null);
			return false;
		}

		// make a call with the appropriate request
		ServiceResponse sr = caller.call(turnOffRequest(deviceURI));
		LogUtils.logDebug(Activator.logger, "FoodManagementConsumer",
				"turnOff", new Object[] { "Call status: ",
						sr.getCallStatus().name() }, null);

		// check the call status and return true if succeeded
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"turnOff",
					new Object[] { "the device couldn't be turned off" }, null);
			return false;
		}
	}

	public static boolean turnOn(String deviceURI) {

		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer", 
					"turnOn", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(turnOnRequest(deviceURI));
		LogUtils.logDebug(Activator.logger, "FoodManagementConsumer", "turnOn",
				new Object[] { "Call device status: ", sr.getCallStatus().name() }, null);
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarning(Activator.logger, "FoodManagementConsumer",
					"turnOn",
					new Object[] { "the device couldn't be turned on" }, null);
			return false;
		}
	}

	public void handleContextEvent(ContextEvent event) {
		LogUtils.logInfo(Activator.logger, "FoodManagementConsumer",
				"handleContextEvent", new Object[] {
						"Received context event:\n", "    Subject     = ",
						event.getSubjectURI(), "\n", "    Subject type= ",
						event.getSubjectTypeURI(), "\n", "    Predicate   = ",
						event.getRDFPredicate(), "\n", "    Object      = ",
						event.getRDFObject() }, null);
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
}
