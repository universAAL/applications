package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;

public class ShoppingList extends ManagedIndividual {

	public static final String NAMESPACE = NutritionOntology.NAMESPACE;
	public static final String MY_URI = ShoppingList.NAMESPACE + "ShoppingList";

	//property list
    public static final String PROP_ID;			// Integer
    public static final String PROP_DATEINTERVAL;			// String
    public static final String PROP_INGREDIENTS;	// Ingredient
    
    static {
		// property names
    	PROP_ID 			= ShoppingList.NAMESPACE + "id";
    	PROP_DATEINTERVAL 			= ShoppingList.NAMESPACE + "dateinterval";
		PROP_INGREDIENTS	= ShoppingList.NAMESPACE + "ingredient";
    }

    public ShoppingList() { }
    public ShoppingList(String uri) { super(uri); }

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
		String v = (String) props.get(PROP_DATEINTERVAL);
		return v;
	}
	
	public void setDateInterval(String date) {
		if (date!=null)
			this.props.put(PROP_DATEINTERVAL, date);
	}
	
	// ID
	public int getID() {
		Integer v = (Integer) props.get(PROP_ID);
		return v.intValue();
	}
	
	public void setID(int id) {
		this.props.put(PROP_ID, new Integer(id));
	}
	
	// INGREDIENTS
	public Ingredient[] getIngredients() {
		Ingredient[] i = (Ingredient[]) this.props.get(PROP_INGREDIENTS);
		return i;
	}
	
	public void setIngredients(Ingredient[] ingredients) {
		this.props.put(PROP_INGREDIENTS, ingredients);
	}
	

}