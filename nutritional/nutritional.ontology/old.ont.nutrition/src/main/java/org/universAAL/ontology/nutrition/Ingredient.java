package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.Activator;

public class Ingredient extends ManagedIndividual {

	public static final String NAMESPACE = Activator.MY_NAMESPACE;
	public static final String MY_URI = Ingredient.NAMESPACE + "Ingredient";

	//property list
	public static final String P_ID;			// Integer
    public static final String P_FOOD;			// Food
    public static final String P_QUANTITY;  	// String
    public static final String P_MEASURE_UNIT;  // MeasureUnit
    
    static {
		// property names
    	P_ID 			= Ingredient.NAMESPACE + "id";
    	P_FOOD 			= Ingredient.NAMESPACE + "has_food";
		P_QUANTITY		= Ingredient.NAMESPACE + "quantity";
		P_MEASURE_UNIT	= Ingredient.NAMESPACE + "measure_unit";
		
		register(Ingredient.class);
    }

    public Ingredient() { }
    public Ingredient(String uri) { super(uri); }

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI.equals(P_ID))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Integer.class), 1, 1);
        if (propURI.equals(P_FOOD))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Food.MY_URI), 1, 1);
        if (propURI.equals(P_QUANTITY))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(String.class), 1, 1);
        if (propURI.equals(P_MEASURE_UNIT))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(MeasureUnit.MY_URI), 1, 1);
	    return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] proper = {P_FOOD, P_ID, P_QUANTITY, P_MEASURE_UNIT};
		
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "comment of the ingredient ontology";
	}

	public static String getRDFSLabel() {
		return "ingredient";
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
	
	// FOOD
	public Food getFood() {
		Food v = (Food) props.get(P_FOOD);
		return v;
	}
	
	public void setFood(Food food) {
		if (food!=null)
		this.props.put(P_FOOD, food);
	}
	
	//QUANTITY
	public String getQuantity() {
		return (String) this.props.get(P_QUANTITY);
	}
	
	public void setQuantity(String quantity) {
		this.props.put(P_QUANTITY, quantity);
	}
	
	// MEASURE UNIT
	public MeasureUnit getMeasureUnit() {
		MeasureUnit v = (MeasureUnit) props.get(P_MEASURE_UNIT);
		return v;
	}
	
	public void setMeasureUnit(MeasureUnit measure) {
		this.props.put(P_MEASURE_UNIT, measure);
	}
}