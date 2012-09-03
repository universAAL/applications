package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.Activator;

public class MenuDay extends ManagedIndividual {

	public static final String NAMESPACE = Activator.MY_NAMESPACE;
	public static final String MY_URI = MenuDay.NAMESPACE + "MenuDay";

	//property list
	public static final String P_ID;					// Integer
    public static final String P_DAYOFWEEK;			// DayOfWeek
    public static final String P_MEALS;				// Meal
    
    static {
		// property names
    	P_ID 					= MenuDay.NAMESPACE + "id";
		P_DAYOFWEEK				= MenuDay.NAMESPACE + "dayOfWeek";
		P_MEALS 				= MenuDay.NAMESPACE + "hasMeals";
		
		register(MenuDay.class);
    }

    public MenuDay() { }
    public MenuDay(String uri) { super(uri); }

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI.equals(P_ID))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Integer.class), 1, 1);
        if (propURI.equals(P_DAYOFWEEK))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         DayOfWeek.MY_URI, 1, 1);
        if (propURI.equals(P_MEALS))
            return Restriction.getAllValuesRestriction(propURI, Meal.MY_URI);
	    return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] proper = {P_ID, P_DAYOFWEEK, P_MEALS};
		
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "comment of the menuday ontology";
	}

	public static String getRDFSLabel() {
		return "Menuday";
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
	
	// MEALS
	public Meal[] getMeals() {
		Meal[] v = (Meal[]) props.get(P_MEALS);
		return v;
	}
	
	public void setMeals(Meal[] meals) {
		if (meals!=null)
			this.props.put(P_MEALS, meals);
	}
	
	// DAY OF WEEK
	public DayOfWeek getDayOfWeek() {
		DayOfWeek v = (DayOfWeek)props.get(P_DAYOFWEEK);
		return v;
	}
	
	public void setDayOfWeek(int value) {
		this.props.put(P_DAYOFWEEK, DayOfWeek.getDaysOfWeekByOrder(value));
	}
	
	public void setDayOfWeek(DayOfWeek dc) {
		this.props.put(P_DAYOFWEEK, dc);
	}
	
}