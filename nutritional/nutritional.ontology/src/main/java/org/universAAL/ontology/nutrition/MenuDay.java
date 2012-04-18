package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;

public class MenuDay extends ManagedIndividual {

	public static final String NAMESPACE = NutritionOntology.NAMESPACE;
	public static final String MY_URI = MenuDay.NAMESPACE + "MenuDay";

	//property list
	public static final String PROP_ID;					// Integer
    public static final String PROP_DAYOFWEEK;			// DayOfWeek
    public static final String PROP_MEALS;				// Meal
    
    static {
		// property names
    	PROP_ID 					= MenuDay.NAMESPACE + "id";
		PROP_DAYOFWEEK				= MenuDay.NAMESPACE + "dayOfWeek";
		PROP_MEALS 				= MenuDay.NAMESPACE + "hasMeals";
    }

    public MenuDay() { }
    public MenuDay(String uri) { super(uri); }

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
	
	// MEALS
	public Meal[] getMeals() {
		Meal[] v = (Meal[]) props.get(PROP_MEALS);
		return v;
	}
	
	public void setMeals(Meal[] meals) {
		if (meals!=null)
			this.props.put(PROP_MEALS, meals);
	}
	
	// DAY OF WEEK
	public DayOfWeek getDayOfWeek() {
		DayOfWeek v = (DayOfWeek)props.get(PROP_DAYOFWEEK);
		return v;
	}
	
	public void setDayOfWeek(int value) {
		this.props.put(PROP_DAYOFWEEK, DayOfWeek.getDaysOfWeekByOrder(value));
	}
	
	public void setDayOfWeek(DayOfWeek dc) {
		this.props.put(PROP_DAYOFWEEK, dc);
	}
	
}