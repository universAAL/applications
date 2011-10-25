package org.universAAL.ontology.foodDevices;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.OrderingRestriction;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;

/**
 * @author dimokas
 *
 */
public class FoodItem extends ManagedIndividual {
	
	public static final String MY_URI; 
	public static final String PROP_NAME;
	public static final String PROP_QUANTITY;
	
	static{
		MY_URI = FoodManagement.FOODMANAGEMENT_NAMESPACE + "FoodItem";
		PROP_NAME = FoodManagement.FOODMANAGEMENT_NAMESPACE + "foodItemName";
		PROP_QUANTITY = FoodManagement.FOODMANAGEMENT_NAMESPACE + "foodItemQuantity";		
		register(FoodItem.class);		
	}
	
	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (PROP_NAME.equals(propURI))
			return Restriction.getCardinalityRestriction(propURI, 1, 1);
		if (PROP_QUANTITY.equals(propURI))
			return OrderingRestriction.newOrderingRestriction(new Integer(100),
				    new Integer(0), true, true, Restriction
					    .getAllValuesRestrictionWithCardinality(propURI,
						    TypeMapper.getDatatypeURI(Integer.class),
						    1, 1));
		return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		//return new String[] {PROP_DEVICE_LOCATION, PROP_DEVICE_STATUS};
		return new String[] {PROP_NAME, PROP_QUANTITY};
	}
	
	public static String getRDFSComment() {
		return "The class of all food items";
	}
	
	public static String getRDFSLabel() {
		return "FoodItem";
	}
		
	public FoodItem(){
		super();
	}
	
	public FoodItem(String uri) {
		super(uri);				
	}
		
	public FoodItem(String uri, String name, int quantity ) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_NAME, new String(name));
		props.put(PROP_QUANTITY, new Integer(quantity));
	}

	
	public int getQuantity() {
		Integer i = (Integer) props.get(PROP_QUANTITY);
		return (i == null) ? -1 : i.intValue();
	}
	public void setQuantity(int quantity) {
		if (quantity > -1 && quantity < 101)
		    props.put(PROP_QUANTITY, new Integer(quantity));
	}
	
	public String getName() {
		return (String) props.get(PROP_NAME);
	}
	public void setName(String name) {
		if (name != null)
		    props.put(PROP_NAME, name);
	}

	
	public int getPropSerializationType(String propURI) {
		return (PROP_NAME.equals(propURI)) ? PROP_SERIALIZATION_REDUCED
			: PROP_SERIALIZATION_FULL;
	}
		
	public boolean isWellFormed() {
		return props.containsKey(PROP_NAME)
		&& props.containsKey(PROP_QUANTITY);
	}
	
	
}
