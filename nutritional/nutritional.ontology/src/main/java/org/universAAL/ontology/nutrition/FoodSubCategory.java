package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;

public class FoodSubCategory extends ManagedIndividual {

	public static final String NAMESPACE = NutritionOntology.NAMESPACE;
	public static final String MY_URI = FoodSubCategory.NAMESPACE + "FoodSubCategory";

	//property list
	public static final String PROP_ID;				// Integer
    public static final String PROP_NAME;				// String
    public static final String PROP_FOODCATEGORY;		// FoodCagegory
    
    
    static {
		// property names
    	PROP_ID 				= FoodSubCategory.NAMESPACE + "id";
    	PROP_NAME				= FoodSubCategory.NAMESPACE + "name";
    	PROP_FOODCATEGORY		= FoodSubCategory.NAMESPACE + "hasFoodCategory";
    }

    public FoodSubCategory() { }
    public FoodSubCategory(String uri) { super(uri); }

    /*
	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI.equals(PROP_ID))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Integer.class), 1, 1);
        if (propURI.equals(PROP_NAME))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(String.class), 0, 1);
        if (propURI.equals(PROP_FOODCATEGORY))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         FoodCategory.MY_URI, 0, 1);
	    return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}
	*/

	public int getPropSerializationType(String propURI) {
		return ManagedIndividual.PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true;
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	
	// ID
	public int getID() {
		Integer v = (Integer) props.get(PROP_ID);
		return v.intValue();
	}
	
	public void setID(int id) {
		this.props.put(PROP_ID, new Integer(id));
	}
	
	// NAME
	public String getName() {
		String v = (String) props.get(PROP_NAME);
		return v;
	}
	
	public void setName(String name) {
		this.props.put(PROP_NAME, new String(name));
	}
	
	// FOOD CATEGORY
	public FoodCategory getFoodCategory() {
		FoodCategory v = (FoodCategory) props.get(PROP_FOODCATEGORY);
		return v;
	}
	
	public void setFoodCategory(FoodCategory foodCategory) {
		this.props.put(PROP_FOODCATEGORY, foodCategory);
	}
}