package org.universAAL.AALapplication.food_shopping.service.server;

import java.util.Hashtable;

import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.Shopping.FoodManagement;
import org.universAAL.ontology.Shopping.Refrigerator;
import org.universAAL.ontology.Shopping.ShoppingList;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.phThing.PhysicalThing;
//import org.universAAL.AALapplication.food_shopping.service.server.unit_impl.DeviceFoodItem;

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
	static final String SERVICE_GET_SHOPPING_ITEMS = FOODMANAGEMENT_SERVER_NAMESPACE + "getShoppingItems";
	static final String SERVICE_GET_SHOPPING_LISTS = FOODMANAGEMENT_SERVER_NAMESPACE + "getShoppingLists";
	static final String SERVICE_GET_SHOPPINGLISTITEMS = FOODMANAGEMENT_SERVER_NAMESPACE + "getShoppingListItems";
	static final String SERVICE_GET_DEVICE_INFO = FOODMANAGEMENT_SERVER_NAMESPACE + "getDeviceInfo";
	static final String SERVICE_TURN_OFF = FOODMANAGEMENT_SERVER_NAMESPACE + "turnOff";
	static final String SERVICE_TURN_ON = FOODMANAGEMENT_SERVER_NAMESPACE + "turnOn";
	static final String SERVICE_TEMPERATURE = FOODMANAGEMENT_SERVER_NAMESPACE + "changeTemperature";
	static final String SERVICE_ADD_FOODITEM = FOODMANAGEMENT_SERVER_NAMESPACE + "addFoodItem";
	static final String SERVICE_UPDATE_FOODITEM = FOODMANAGEMENT_SERVER_NAMESPACE + "updateFoodItem";
	static final String SERVICE_REMOVE_FOODITEM = FOODMANAGEMENT_SERVER_NAMESPACE + "removeFoodItem";
	static final String SERVICE_ADD_SHOPPINGLIST = FOODMANAGEMENT_SERVER_NAMESPACE + "addShoppingList";
	static final String SERVICE_REMOVE_SHOPPINGLIST = FOODMANAGEMENT_SERVER_NAMESPACE + "removeShoppingList";
	
	static final String INPUT_DEVICE_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "DeviceURI";
	static final String INPUT_TEMP_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "inputTemp";
	static final String INPUT_FOODITEM_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "inputFoodItem";
	static final String INPUT_SHOPPINGLIST_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "inputShoppingList";
	static final String INPUT_SHOPPINGNAME_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "inputShoppingName";
	static final String INPUT_SHOPPINGLISTID_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "inputShoppingListID";
	static final String INPUT_SHOPPINGDATE_URI = FOODMANAGEMENT_SERVER_NAMESPACE + "inputShoppingDate";
	static final String INPUT_FOOD_ITEMS = FOODMANAGEMENT_SERVER_NAMESPACE + "foodItems";
	
	static final String OUTPUT_CONTROLLED_DEVICES = FOODMANAGEMENT_SERVER_NAMESPACE + "controlledDevices";	
	static final String OUTPUT_DEVICE_STATUS = FOODMANAGEMENT_SERVER_NAMESPACE + "status";
	static final String OUTPUT_DEVICE_LOCATION = FOODMANAGEMENT_SERVER_NAMESPACE + "location";
	static final String OUTPUT_DEVICE_TEMPERATURE = FOODMANAGEMENT_SERVER_NAMESPACE + "temperature";
	static final String OUTPUT_FOOD_ITEMS = FOODMANAGEMENT_SERVER_NAMESPACE + "foodItems";
	static final String OUTPUT_SHOPPING_ITEMS = FOODMANAGEMENT_SERVER_NAMESPACE + "shoppingItems";
	static final String OUTPUT_SHOPPINGLIST_ITEMS = FOODMANAGEMENT_SERVER_NAMESPACE + "shoppingListItems";
	static final String OUTPUT_SHOPPING_LISTS = FOODMANAGEMENT_SERVER_NAMESPACE + "shoppingLists";
	
	static final ServiceProfile[] profiles = new ServiceProfile[13];
	private static Hashtable serverFoodManagementRestrictions = new Hashtable();
	

    static {
	// we need to register all classes in the ontology for the serialization of the object
	// OntologyManagement.getInstance().register(new SimpleOntology(MY_URI,	FoodManagement.MY_URI));
	OntologyManagement.getInstance().register(
		new SimpleOntology(MY_URI, FoodManagement.MY_URI,
			new ResourceFactoryImpl() {
			    @Override
			    public Resource createInstance(String classURI,
				    String instanceURI, int factoryIndex) {
				return new ProvidedFoodManagementService(instanceURI);
			    }
			}));

	String[] ppControls = new String[] {FoodManagement.PROP_CONTROLS};
	String[] ppStatus = new String[] {FoodManagement.PROP_CONTROLS, Refrigerator.PROP_DEVICE_STATUS};
	String[] ppTemperature = new String[] {FoodManagement.PROP_CONTROLS, Refrigerator.PROP_TEMPERATURE};
	String[] ppFoodItem = new String[] {FoodManagement.PROP_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS};

	addRestriction((MergedRestriction) FoodManagement
		.getClassRestrictionsOnProperty(FoodManagement.MY_URI,FoodManagement.PROP_CONTROLS).copy(), ppControls,serverFoodManagementRestrictions);

	addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(Refrigerator.PROP_DEVICE_STATUS,
					new Enumeration(new Integer[] {new Integer(0), new Integer(100)}), 1, 1),
			ppStatus,serverFoodManagementRestrictions);
	
	addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(Refrigerator.PROP_TEMPERATURE,
					new Enumeration(new Integer[] {new Integer(-18), new Integer(20)}), 1, 1),
			ppStatus,serverFoodManagementRestrictions);

		ProvidedFoodManagementService getControlledDevices = new ProvidedFoodManagementService(SERVICE_GET_CONTROLLED_DEVICES);
		getControlledDevices.addOutput(OUTPUT_CONTROLLED_DEVICES, Refrigerator.MY_URI, 0, 0, ppControls);
		profiles[0] = getControlledDevices.myProfile;
		
		ProvidedFoodManagementService getDeviceInfo = new ProvidedFoodManagementService(SERVICE_GET_DEVICE_INFO);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		getDeviceInfo.addOutput(OUTPUT_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1,ppStatus);
		getDeviceInfo.addOutput(OUTPUT_DEVICE_LOCATION,	Location.MY_URI, 1, 1,
				new String[] {FoodManagement.PROP_CONTROLS, PhysicalThing.PROP_PHYSICAL_LOCATION});
		getDeviceInfo.addOutput(OUTPUT_DEVICE_TEMPERATURE, TypeMapper.getDatatypeURI(Integer.class),1,1,ppTemperature);
		profiles[1] = getDeviceInfo.myProfile;
		
		ProvidedFoodManagementService turnOff = new ProvidedFoodManagementService(SERVICE_TURN_OFF);
		turnOff.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		turnOff.myProfile.addChangeEffect(ppStatus, new Integer(0));
		profiles[2] = turnOff.myProfile;
		
		ProvidedFoodManagementService turnOn = new ProvidedFoodManagementService(SERVICE_TURN_ON);
		turnOn.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		turnOn.myProfile.addChangeEffect(ppStatus, new Integer(100));
		profiles[3] = turnOn.myProfile;
		
		ProvidedFoodManagementService changeTemperature = new ProvidedFoodManagementService(SERVICE_TEMPERATURE);
		changeTemperature.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		changeTemperature.addInputWithChangeEffect(INPUT_TEMP_URI, TypeMapper
			.getDatatypeURI(Integer.class), 1, 1, new String[] {
			FoodManagement.PROP_CONTROLS, Refrigerator.PROP_TEMPERATURE });
		profiles[4] = changeTemperature.myProfile;

		ProvidedFoodManagementService addFoodItem = new ProvidedFoodManagementService(SERVICE_ADD_FOODITEM);
		addFoodItem.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		addFoodItem.addInputWithAddEffect(INPUT_FOODITEM_URI, FoodItem.MY_URI, 1, 1, new String[] {
			FoodManagement.PROP_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS });
		profiles[5] = addFoodItem.myProfile;

		ProvidedFoodManagementService removeFoodItem = new ProvidedFoodManagementService(SERVICE_REMOVE_FOODITEM);
		removeFoodItem.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		removeFoodItem.addInputWithRemoveEffect(INPUT_FOODITEM_URI, TypeMapper
				.getDatatypeURI(String.class), 1, 1, new String[] {	FoodManagement.PROP_CONTROLS, FoodItem.PROP_NAME });
		profiles[6] = removeFoodItem.myProfile;
		
		ProvidedFoodManagementService getFoodItems = new ProvidedFoodManagementService(SERVICE_GET_FOOD_ITEMS);
		getFoodItems.addFilteringInput(INPUT_DEVICE_URI, Refrigerator.MY_URI, 1, 1, ppControls);
		getFoodItems.addOutput(OUTPUT_FOOD_ITEMS, FoodItem.MY_URI, 0, 0, new String[] {
				FoodManagement.PROP_CONTROLS, Refrigerator.PROP_HAS_FOODITEMS });
		profiles[7] = getFoodItems.myProfile;

		ProvidedFoodManagementService addShoppingList = new ProvidedFoodManagementService(SERVICE_ADD_SHOPPINGLIST);
		addShoppingList.addFilteringInput(INPUT_SHOPPINGLIST_URI, ShoppingList.MY_URI, 1, 1, ppControls);
		addShoppingList.addInputWithAddEffect(INPUT_SHOPPINGNAME_URI, TypeMapper
				.getDatatypeURI(String.class), 1, 1, new String[] {
				FoodManagement.PROP_CONTROLS, ShoppingList.PROP_NAME });
		addShoppingList.addInputWithAddEffect(INPUT_SHOPPINGDATE_URI, TypeMapper
				.getDatatypeURI(String.class), 1, 1, new String[] {
				FoodManagement.PROP_CONTROLS, ShoppingList.PROP_DATE });
		addShoppingList.addInputWithAddEffect(INPUT_FOOD_ITEMS, FoodItem.MY_URI, 0, 0, new String[] {
				FoodManagement.PROP_CONTROLS, ShoppingList.PROP_HAS_FOODITEMS });
		profiles[8] = addShoppingList.myProfile;

		ProvidedFoodManagementService getShoppingItems = new ProvidedFoodManagementService(SERVICE_GET_SHOPPING_ITEMS);
		getShoppingItems.addOutput(OUTPUT_SHOPPING_ITEMS, FoodItem.MY_URI, 0, 0, new String[] 
				{	FoodManagement.PROP_CONTROLS,FoodItem.PROP_NAME });
		profiles[9] = getShoppingItems.myProfile;

		ProvidedFoodManagementService getShoppingLists = new ProvidedFoodManagementService(SERVICE_GET_SHOPPING_LISTS);
		getShoppingLists.addOutput(OUTPUT_SHOPPING_LISTS, ShoppingList.MY_URI, 0, 0, new String[] 
				{	FoodManagement.PROP_CONTROLS,ShoppingList.PROP_NAME });
		profiles[10] = getShoppingLists.myProfile;

		ProvidedFoodManagementService getShoppingListItems = new ProvidedFoodManagementService(SERVICE_GET_SHOPPINGLISTITEMS);
		getShoppingListItems.addFilteringInput(INPUT_SHOPPINGNAME_URI, TypeMapper
				.getDatatypeURI(String.class), 1, 1, new String[] {
				FoodManagement.PROP_CONTROLS, ShoppingList.PROP_NAME });
		getShoppingListItems.addOutput(OUTPUT_SHOPPINGLIST_ITEMS, FoodItem.MY_URI, 0, 0, new String[] {
				FoodManagement.PROP_CONTROLS, org.universAAL.ontology.Shopping.ShoppingList.PROP_HAS_FOODITEMS });
		profiles[11] = getShoppingListItems.myProfile;

		ProvidedFoodManagementService removeShoppingList = new ProvidedFoodManagementService(SERVICE_REMOVE_SHOPPINGLIST);
		removeShoppingList.addInputWithRemoveEffect(INPUT_SHOPPINGNAME_URI, TypeMapper
				.getDatatypeURI(String.class), 1, 1, new String[] {	FoodManagement.PROP_CONTROLS, ShoppingList.PROP_NAME });
		profiles[12] = removeShoppingList.myProfile;
		
    }


    private ProvidedFoodManagementService(String uri) {
    	super(uri);
    }

    public String getClassURI() {
    	return MY_URI;
    }
	
}
