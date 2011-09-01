package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.Activator;

public class Food extends ManagedIndividual {

	public static final String NAMESPACE = Activator.MY_NAMESPACE;
	public static final String MY_URI = Food.NAMESPACE + "Food";

	//property list
	public static final String P_ID;				// Integer
    public static final String P_CARBOHIDRATES;		// Double
    public static final String P_FAT;				// Double
    public static final String P_KILOCALORIES;		// Double
    public static final String P_NAME;				// String
    public static final String P_FOODSUBCATEGORY;	// FoodSubCategory
    
    
    static {
		// property names
    	P_ID 				= Food.NAMESPACE + "id";
    	P_CARBOHIDRATES		= Food.NAMESPACE + "carbohydrates";
    	P_FAT	 			= Food.NAMESPACE + "fat";
    	P_KILOCALORIES		= Food.NAMESPACE + "kilocalories";
    	P_NAME				= Food.NAMESPACE + "name";
    	P_FOODSUBCATEGORY	= Food.NAMESPACE + "has_FoodSubCategory";
		
		register(Food.class);
    }

    public Food() { }
    public Food(String uri) { super(uri); }

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI.equals(P_ID))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Integer.class), 1, 1);
        if (propURI.equals(P_CARBOHIDRATES))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Double.class), 0, 1);
        if (propURI.equals(P_FAT))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(Double.class), 0, 1);
        if (propURI.equals(P_KILOCALORIES))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(Double.class), 0, 1);
        if (propURI.equals(P_NAME))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(String.class), 0, 1);
        if (propURI.equals(P_FOODSUBCATEGORY))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         FoodSubCategory.MY_URI, 0, 1);
	    return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] proper = {P_ID, P_CARBOHIDRATES, P_FAT, P_KILOCALORIES, P_NAME};
		
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "comment of the food ontology";
	}

	public static String getRDFSLabel() {
		return "food";
	}

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
		Integer v = (Integer) props.get(P_ID);
		return v.intValue();
	}
	
	public void setID(int id) {
		this.props.put(P_ID, new Integer(id));
	}
	
	// CARBOHYDRATES
	public double getCarbohydrates() {
		Double v = (Double) props.get(P_CARBOHIDRATES);
		return v.doubleValue();
	}
	
	public void setCarbohydrates(double carbo) {
		this.props.put(P_CARBOHIDRATES, new Double(carbo));
	}
	
	// FAT
	public double getFat() {
		Double v = (Double) props.get(P_FAT);
		return v.doubleValue();
	}
	
	public void setFat(double carbo) {
		this.props.put(P_FAT, new Double(carbo));
	}
	
	// KILOCALORIES
	public double getKilocalories() {
		Double v = (Double) props.get(P_KILOCALORIES);
		return v.doubleValue();
	}
	
	public void setKilocalories(double kcal) {
		this.props.put(P_KILOCALORIES, new Double(kcal));
	}
	
	// NAME
	public String getName() {
		String v = (String) props.get(P_NAME);
		return v;
	}
	
	public void setName(String name) {
		this.props.put(P_NAME, new String(name));
	}
	
	// FOOD SUBCATEGORY
	public FoodSubCategory getFoodCategory() {
		FoodSubCategory v = (FoodSubCategory) props.get(P_FOODSUBCATEGORY);
		return v;
	}
	
	public void setFoodCategory(FoodCategory foodSubCategory) {
		this.props.put(P_FOODSUBCATEGORY, foodSubCategory);
	}
}