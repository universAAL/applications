package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.Activator;

public class ShoppingList extends ManagedIndividual {

	public static final String NAMESPACE = Activator.MY_NAMESPACE;
	public static final String MY_URI = ShoppingList.NAMESPACE + "ShoppingList";

	//property list
    public static final String P_ID;			// Integer
    public static final String P_DATEINTERVAL;			// String
    public static final String P_INGREDIENTS;	// Ingredient
    
    static {
		// property names
    	P_ID 			= ShoppingList.NAMESPACE + "id";
    	P_DATEINTERVAL 			= ShoppingList.NAMESPACE + "dateinterval";
		P_INGREDIENTS	= ShoppingList.NAMESPACE + "ingredient";
		
		register(ShoppingList.class);
    }

    public ShoppingList() { }
    public ShoppingList(String uri) { super(uri); }

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI.equals(P_ID))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Integer.class), 1, 1);
        if (propURI.equals(P_DATEINTERVAL))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(String.class), 1, 1);
        if (propURI.equals(P_INGREDIENTS))
        	return Restriction.getAllValuesRestriction(propURI,Ingredient.MY_URI);
	    return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] proper = {P_DATEINTERVAL, P_ID, P_INGREDIENTS};
		
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "comment of the shopping list ontology";
	}

	public static String getRDFSLabel() {
		return "shoppinglist";
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
	
	// DATE INTERVAL
	public String getDateInterval() {
		String v = (String) props.get(P_DATEINTERVAL);
		return v;
	}
	
	public void setDateInterval(String date) {
		if (date!=null)
			this.props.put(P_DATEINTERVAL, date);
	}
	
	// ID
	public int getID() {
		Integer v = (Integer) props.get(P_ID);
		return v.intValue();
	}
	
	public void setID(int id) {
		this.props.put(P_ID, new Integer(id));
	}
	
	// INGREDIENTS
	public Ingredient[] getIngredients() {
		Ingredient[] i = (Ingredient[]) this.props.get(P_INGREDIENTS);
		return i;
	}
	
	public void setIngredients(Ingredient[] ingredients) {
		this.props.put(P_INGREDIENTS, ingredients);
	}
	

}