package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.Activator;

public class Meal extends ManagedIndividual {

	public static final String NAMESPACE = Activator.MY_NAMESPACE;
	public static final String MY_URI = Meal.NAMESPACE + "Meal";

	//property list
	public static final String P_ID;					// Integer
    public static final String P_MEAL_CATEGORY;			// MealCategory
    public static final String P_DISHES;				// Dish
    
    static {
		// property names
    	P_ID 					= Meal.NAMESPACE + "id";
		P_MEAL_CATEGORY			= Meal.NAMESPACE + "mealCategory";
		P_DISHES 				= Meal.NAMESPACE + "hasDishes";
		
		register(Meal.class);
    }

    public Meal() { }
    public Meal(String uri) { super(uri); }

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI.equals(P_ID))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Integer.class), 1, 1);
        if (propURI.equals(P_MEAL_CATEGORY))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         DishCategory.MY_URI, 1, 1);
        if (propURI.equals(P_DISHES))
            return Restriction.getAllValuesRestriction(propURI, Dish.MY_URI);
	    return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] proper = {P_ID, P_MEAL_CATEGORY, P_DISHES};
		
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "comment of the meal ontology";
	}

	public static String getRDFSLabel() {
		return "meal";
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
	
	// DISHES
	public Dish[] getDishes() {
		Dish[] v = (Dish[]) props.get(P_DISHES);
		return v;
	}
	
	public void setDishes(Dish[] dishes) {
		if (dishes!=null)
			this.props.put(P_DISHES, dishes);
	}
	
	// MEAL CATEGORY
	public MealCategory getDishCategory() {
		MealCategory v = (MealCategory)props.get(P_MEAL_CATEGORY);
		return v;
	}
	
	public void setMealCategory(String value) {
		this.props.put(P_MEAL_CATEGORY, MealCategory.getMealCategoryByValue(value));
	}
	
	public void setMealCategory(int value) {
		this.props.put(P_MEAL_CATEGORY, MealCategory.getMealCategoryByOrder(value));
	}
	
	public void setMealCategory(MealCategory dc) {
		this.props.put(P_MEAL_CATEGORY, dc);
	}
	
}