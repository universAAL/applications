package org.universAAL.ontology.Shopping;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.Shopping.ShoppingOntology;

/**
 * @author dimokas
 *
 */
public class FoodItem extends ManagedIndividual {
	
	public static final String MY_URI; 
	public static final String PROP_NAME;
	public static final String PROP_QUANTITY;
	public static final String PROP_CODE;
	public static final String PROP_SIZE;
	public static final String PROP_COMPANY;
	public static final String PROP_TAGID;
	public static final String PROP_INSERTION_DATE;
	public static final String PROP_EXPIRATION_DATE;

	static{
		MY_URI = ShoppingOntology.NAMESPACE + "FoodItem";
		PROP_NAME = ShoppingOntology.NAMESPACE + "foodItemName";
		PROP_QUANTITY = ShoppingOntology.NAMESPACE + "foodItemQuantity";
		PROP_CODE = ShoppingOntology.NAMESPACE + "foodItemCode";
		PROP_SIZE = ShoppingOntology.NAMESPACE + "foodItemSize";
		PROP_COMPANY = ShoppingOntology.NAMESPACE + "foodItemCompany";
		PROP_TAGID = ShoppingOntology.NAMESPACE + "foodItemTag";
		PROP_INSERTION_DATE = ShoppingOntology.NAMESPACE + "foodItemInsertionDate";
		PROP_EXPIRATION_DATE = ShoppingOntology.NAMESPACE + "foodItemExpirationDate";
	}

	public static String[] getStandardPropertyURIs() {
		//return new String[] {PROP_DEVICE_LOCATION, PROP_DEVICE_STATUS};
		return new String[] {PROP_NAME, PROP_QUANTITY};
	}
/*	
	public static String getRDFSComment() {
		return "The class of all food items";
	}
	
	public static String getRDFSLabel() {
		return "FoodItem";
	}
*/	
    public String getClassURI() {
    	return MY_URI;
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

	public FoodItem(String uri, int code, String name, String size, String company) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_CODE, new Integer(code));
		props.put(PROP_NAME, new String(name));
		props.put(PROP_SIZE, new String(size));
		props.put(PROP_COMPANY, new String(company));
	}

	public FoodItem(String uri, String name, Double quantity, String size, String company, int code) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_NAME, new String(name));
		props.put(PROP_QUANTITY, new Double(""+quantity));
		props.put(PROP_SIZE, new String(size));
		props.put(PROP_COMPANY, new String(company));
		props.put(PROP_CODE, new Integer(code));
	}

	public FoodItem(String uri, String name, Double quantity, String size, String company, int code, String tagID) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_NAME, new String(name));
		props.put(PROP_QUANTITY, new Double(""+quantity));
		props.put(PROP_SIZE, new String(size));
		props.put(PROP_COMPANY, new String(company));
		props.put(PROP_CODE, new Integer(code));
		props.put(PROP_TAGID, new String(tagID));
	}
	
	public FoodItem(String uri, String name, Double quantity, String size, String company, String tagID, String insDate, String expDate) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_NAME, new String(name));
		props.put(PROP_QUANTITY, new Double(""+quantity));
		props.put(PROP_SIZE, new String(size));
		props.put(PROP_COMPANY, new String(company));
		props.put(PROP_TAGID, new String(tagID));
		props.put(PROP_INSERTION_DATE, new String(insDate));
		props.put(PROP_EXPIRATION_DATE, new String(expDate));
	}

	public FoodItem(String uri, String name, Double quantity, String size, String company, int code, String tagID, String insDate, String expDate) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_NAME, new String(name));
		props.put(PROP_QUANTITY, new Double(""+quantity));
		props.put(PROP_SIZE, new String(size));
		props.put(PROP_COMPANY, new String(company));
		props.put(PROP_CODE, new Integer(code));
		props.put(PROP_TAGID, new String(tagID));
		props.put(PROP_INSERTION_DATE, new String(insDate));
		props.put(PROP_EXPIRATION_DATE, new String(expDate));
	}
	
	public double getQuantity() {
		Double i = (Double) props.get(PROP_QUANTITY);
		return (i == null) ? -1.0 : i.doubleValue();
	}
	public void setQuantity(double quantity) {
		if (quantity > -1 && quantity < 101)
		    props.put(PROP_QUANTITY, new Double(quantity));
	}
	
	public String getName() {
		return (String) props.get(PROP_NAME);
	}
	public void setName(String name) {
		if (name != null)
		    props.put(PROP_NAME, name);
	}

	public String getSize() {
		return (String) props.get(PROP_SIZE);
	}
	public void setSize(String size) {
		if (size != null)
		    props.put(PROP_SIZE, size);
	}

	public String getCompany() {
		return (String) props.get(PROP_COMPANY);
	}
	public void setCompany(String company) {
		if (company != null)
		    props.put(PROP_COMPANY, company);
	}

	public Integer getCode() {
		return (Integer) props.get(PROP_CODE);
	}
	public void setCode(int code) {
	    props.put(PROP_CODE, new Integer(code));
	}

	public String getTagID() {
		return (String) props.get(PROP_TAGID);
	}
	public void setTagID(String tagID) {
		if (tagID != null)
		    props.put(PROP_TAGID, tagID);
	}

	public String getInsDate() {
		return (String) props.get(PROP_INSERTION_DATE);
	}
	public void setInsDate(String insDate) {
		if (insDate != null)
		    props.put(PROP_INSERTION_DATE, insDate);
	}

	public String getExpDate() {
		return (String) props.get(PROP_EXPIRATION_DATE);
	}
	public void setExpDate(String expDate) {
		if (expDate != null)
		    props.put(PROP_EXPIRATION_DATE, expDate);
	}

	
	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
		//return (PROP_NAME.equals(propURI)) ? PROP_SERIALIZATION_REDUCED
		//	: PROP_SERIALIZATION_FULL;
	}
		
	public boolean isWellFormed() {
		return true;
		//return props.containsKey(PROP_NAME)
		//&& props.containsKey(PROP_QUANTITY);
	}
	
	
}
