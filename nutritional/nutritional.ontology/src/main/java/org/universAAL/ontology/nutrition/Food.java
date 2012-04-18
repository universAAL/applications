package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;

public class Food extends ManagedIndividual {

	public static final String NAMESPACE = NutritionOntology.NAMESPACE;
	public static final String MY_URI = Food.NAMESPACE + "Food";

	//property list
	public static final String PROP_ID;				// Integer
    public static final String PROP_CARBOHIDRATES;		// Double
    public static final String PROP_FAT;				// Double
    public static final String PROP_KILOCALORIES;		// Double
    public static final String PROP_NAME;				// String
    public static final String PROP_FOODSUBCATEGORY;	// FoodSubCategory
    
    
    static {
		// property names
    	PROP_ID 				= Food.NAMESPACE + "id";
    	PROP_CARBOHIDRATES		= Food.NAMESPACE + "carbohydrates";
    	PROP_FAT	 			= Food.NAMESPACE + "fat";
    	PROP_KILOCALORIES		= Food.NAMESPACE + "kilocalories";
    	PROP_NAME				= Food.NAMESPACE + "name";
    	PROP_FOODSUBCATEGORY	= Food.NAMESPACE + "has_FoodSubCategory";
    }

    public Food() { }
    public Food(String uri) { super(uri); }

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
	
	// CARBOHYDRATES
	public double getCarbohydrates() {
		Double v = (Double) props.get(PROP_CARBOHIDRATES);
		return v.doubleValue();
	}
	
	public void setCarbohydrates(double carbo) {
		this.props.put(PROP_CARBOHIDRATES, new Double(carbo));
	}
	
	// FAT
	public double getFat() {
		Double v = (Double) props.get(PROP_FAT);
		return v.doubleValue();
	}
	
	public void setFat(double carbo) {
		this.props.put(PROP_FAT, new Double(carbo));
	}
	
	// KILOCALORIES
	public double getKilocalories() {
		Double v = (Double) props.get(PROP_KILOCALORIES);
		return v.doubleValue();
	}
	
	public void setKilocalories(double kcal) {
		this.props.put(PROP_KILOCALORIES, new Double(kcal));
	}
	
	// NAME
	public String getName() {
		String v = (String) props.get(PROP_NAME);
		return v;
	}
	
	public void setName(String name) {
		this.props.put(PROP_NAME, new String(name));
	}
	
	// FOOD SUBCATEGORY
	public FoodSubCategory getFoodSubCategory() {
		FoodSubCategory v = (FoodSubCategory) props.get(PROP_FOODSUBCATEGORY);
		return v;
	}
	
	public void setFoodSubCategory(FoodSubCategory foodSubCategory) {
		this.props.put(PROP_FOODSUBCATEGORY, foodSubCategory);
	}
}