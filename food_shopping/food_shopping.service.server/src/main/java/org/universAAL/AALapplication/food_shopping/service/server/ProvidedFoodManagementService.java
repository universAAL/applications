package org.universAAL.AALapplication.food_shopping.service.server;

import java.util.Hashtable;

import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.DeviceFoodItem;
import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.foodDevices.FoodManagement;
import org.universAAL.ontology.foodDevices.FoodItem;
import org.universAAL.ontology.foodDevices.Refrigerator;
import org.universAAL.ontology.location.Location;

/**
 * @author dimokas
 *
 */

public class ProvidedFoodManagementService extends FoodManagement {
	
	// All the static Strings are used to unique identify special functions and objects
	public static final String FOODMANAGEMENT_SERVER_NAMESPACE = "http://ontology.universaal.org/FoodManagementServer.owl#";
	public static final String MY_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "FoodManagementService";
	
	static final String SERVICE_GET_CONTROLLED_DEVICES = FOODMANAGEMENT_SERVER_NAMESPACE + "getControlledDevices";
	static final String SERVICE_GET_FOOD_ITEMS = FOODMANAGEMENT_SERVER_NAMESPACE + "getFoodItems";
	static final String SERVICE_GET_DEVICE_INFO = FOODMANAGEMENT_SERVER_NAMESPACE + "getDeviceInfo";
	static final String SERVICE_TURN_OFF = FOODMANAGEMENT_SERVER_NAMESPACE + "turnOff";
	static final String SERVICE_TURN_ON = FOODMANAGEMENT_SERVER_NAMESPACE + "turnOn";
	static final String SERVICE_TEMPERATURE = FOODMANAGEMENT_SERVER_NAMESPACE + "changeTemperature";
	static final String SERVICE_ADD_FOODITEM = FOODMANAGEMENT_SERVER_NAMESPACE + "addFoodItem";
	static final String SERVICE_UPDATE_FOODITEM = FOODMANAGEMENT_SERVER_NAMESPACE + "updateFoodItem";
	static final String SERVICE_REMOVE_FOODITEM = FOODMANAGEMENT_SERVER_NAMESPACE + "removeFoodItem";
	
	static final String INPUT_DEVICE_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "DeviceURI";
	static final String INPUT_TEMP_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "inputTemp";
	static final String INPUT_FOODITEM_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "inputFoodItem";
	
	static final String OUTPUT_CONTROLLED_DEVICES = FOODMANAGEMENT_SERVER_NAMESPACE + "controlledDevices";	
	static final String OUTPUT_DEVICE_STATUS = FOODMANAGEMENT_SERVER_NAMESPACE + "status";
	static final String OUTPUT_DEVICE_LOCATION = FOODMANAGEMENT_SERVER_NAMESPACE + "location";
	static final String OUTPUT_DEVICE_TEMPERATURE = FOODMANAGEMENT_SERVER_NAMESPACE + "temperature";
	static final String OUTPUT_FOOD_ITEMS = FOODMANAGEMENT_SERVER_NAMESPACE + "foodItems";
	
	static final ServiceProfile[] profiles = new ServiceProfile[8];
	private static Hashtable serverFoodManagementRestrictions = new Hashtable();
	
	static {
		// we need to register all classes in the ontology for the serialization of the object
		register(ProvidedFoodManagementService.class);
		
		// Help structures to define property paths used more than once below
		String[] ppControls = new String[] {FoodManagement.PROP_CONTROLS};
		String[] ppStatus = new String[] {FoodManagement.PROP_CONTROLS, Refrigerator.PROP_DEVICE_STATUS};
		String[] ppTemperature = new String[] {FoodManagement.PROP_CONTROLS, Refrigerator.PROP_TEMPERATURE};
		String[] ppFoodItem = new String[] {FoodManagement.PROP_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS};
		
		addRestriction((Restriction)
				FoodManagement.getClassRestrictionsOnProperty(FoodManagement.PROP_CONTROLS).copy(),
				ppControls, serverFoodManagementRestrictions);
		addRestriction(
				Restriction.getAllValuesRestrictionWithCardinality(Refrigerator.PROP_DEVICE_STATUS,
						new Enumeration(new Integer[] {new Integer(0), new Integer(100)}), 1, 1),
				ppStatus,serverFoodManagementRestrictions);
		
		addRestriction(
				Restriction.getAllValuesRestrictionWithCardinality(Refrigerator.PROP_TEMPERATURE,
						new Enumeration(new Integer[] {new Integer(-18), new Integer(20)}), 1, 1),
				ppStatus,serverFoodManagementRestrictions);
		
		/*
		 * create the service description #1 to be registered with the service bus
		 */
		
		ProvidedFoodManagementService getControlledDevices = new ProvidedFoodManagementService(SERVICE_GET_CONTROLLED_DEVICES);
		getControlledDevices.addOutput(OUTPUT_CONTROLLED_DEVICES, Refrigerator.MY_URI, 0, 0, ppControls);
		profiles[0] = getControlledDevices.myProfile;
		
		/*
		 * create the service description #2 to be registered with the service bus
		 */
		
		ProvidedFoodManagementService getDeviceInfo = new ProvidedFoodManagementService(SERVICE_GET_DEVICE_INFO);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		
		getDeviceInfo.addOutput(OUTPUT_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1,
				ppStatus);
		getDeviceInfo.addOutput(OUTPUT_DEVICE_LOCATION,	Location.MY_URI, 1, 1,
				new String[] {FoodManagement.PROP_CONTROLS, Refrigerator.PROP_DEVICE_LOCATION});
		getDeviceInfo.addOutput(OUTPUT_DEVICE_TEMPERATURE, TypeMapper.getDatatypeURI(Integer.class), 1, 1,
				ppTemperature);
		profiles[1] = getDeviceInfo.myProfile;
		
		
		/*
		 * create the service description #3 to be registered with the service bus
		 */
		ProvidedFoodManagementService turnOff = new ProvidedFoodManagementService(SERVICE_TURN_OFF);
		turnOff.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		turnOff.myProfile.addChangeEffect(ppStatus, new Integer(0));
		profiles[2] = turnOff.myProfile;
		
		/*
		 * create the service description #4 to be registered with the service bus
		 */
		ProvidedFoodManagementService turnOn = new ProvidedFoodManagementService(SERVICE_TURN_ON);
		turnOn.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		turnOn.myProfile.addChangeEffect(ppStatus, new Integer(100));
		profiles[3] = turnOn.myProfile;
		
		/*
		 * create the service description #5 to be registered with the service bus
		 */
		ProvidedFoodManagementService changeTemperature = new ProvidedFoodManagementService(SERVICE_TEMPERATURE);
		changeTemperature.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		// Input+Effect - Temperature to set
		changeTemperature.addInputWithChangeEffect(INPUT_TEMP_URI, TypeMapper
			.getDatatypeURI(Integer.class), 1, 1, new String[] {
			FoodManagement.PROP_CONTROLS, Refrigerator.PROP_TEMPERATURE });
		profiles[4] = changeTemperature.myProfile;

		/*
		 * create the service description #6 to be registered with the service bus
		 */
		ProvidedFoodManagementService addFoodItem = new ProvidedFoodManagementService(SERVICE_ADD_FOODITEM);
		addFoodItem.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		addFoodItem.addInputWithChangeEffect(INPUT_FOODITEM_URI, FoodItem.MY_URI, 1, 1, new String[] {
			FoodManagement.PROP_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS });
		profiles[5] = addFoodItem.myProfile;

		/*
		 * create the service description #7 to be registered with the service bus
		 */
		ProvidedFoodManagementService removeFoodItem = new ProvidedFoodManagementService(SERVICE_REMOVE_FOODITEM);
		removeFoodItem.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		removeFoodItem.addInputWithChangeEffect(INPUT_FOODITEM_URI, TypeMapper
				.getDatatypeURI(String.class), 1, 1, new String[] {
			FoodManagement.PROP_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS });
		profiles[6] = removeFoodItem.myProfile;
		
		ProvidedFoodManagementService getFoodItems = new ProvidedFoodManagementService(SERVICE_GET_FOOD_ITEMS);
		getFoodItems.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		getFoodItems.addOutput(OUTPUT_FOOD_ITEMS, FoodItem.MY_URI, 0, 0, new String[] {
				FoodManagement.PROP_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS });
		profiles[7] = getFoodItems.myProfile;

		
		//System.out.println("Restrictions = " + serverFoodManagementRestrictions);
}


	private ProvidedFoodManagementService(String uri) {
		super(uri);
	}
	
}
