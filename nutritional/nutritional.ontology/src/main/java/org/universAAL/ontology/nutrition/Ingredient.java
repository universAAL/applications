package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;

public class Ingredient extends ManagedIndividual {

	public static final String NAMESPACE = NutritionOntology.NAMESPACE;
	public static final String MY_URI = Ingredient.NAMESPACE + "Ingredient";

	//property list
	public static final String PROP_ID;			// Integer
    public static final String PROP_FOOD;			// Food
    public static final String PROP_QUANTITY;  	// String
    public static final String PROP_MEASURE_UNIT;  // MeasureUnit
    
    static {
		// property names
    	PROP_ID 			= Ingredient.NAMESPACE + "id";
    	PROP_FOOD 			= Ingredient.NAMESPACE + "has_food";
		PROP_QUANTITY		= Ingredient.NAMESPACE + "quantity";
		PROP_MEASURE_UNIT	= Ingredient.NAMESPACE + "measure_unit";
    }

    public Ingredient() { }
    public Ingredient(String uri) { super(uri); }

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
	
	// FOOD
	public Food getFood() {
		Food v = (Food) props.get(PROP_FOOD);
		return v;
	}
	
	public void setFood(Food food) {
		if (food!=null)
		this.props.put(PROP_FOOD, food);
	}
	
	//QUANTITY
	public String getQuantity() {
		return (String) this.props.get(PROP_QUANTITY);
	}
	
	public void setQuantity(String quantity) {
		this.props.put(PROP_QUANTITY, quantity);
	}
	
	// MEASURE UNIT
	public MeasureUnit getMeasureUnit() {
		MeasureUnit v = (MeasureUnit) props.get(PROP_MEASURE_UNIT);
		return v;
	}
	
	public void setMeasureUnit(MeasureUnit measure) {
		this.props.put(PROP_MEASURE_UNIT, measure);
	}
}