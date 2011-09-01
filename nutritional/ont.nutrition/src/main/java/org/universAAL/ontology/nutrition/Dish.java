package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.Activator;

public class Dish extends ManagedIndividual {

	public static final String NAMESPACE = Activator.MY_NAMESPACE;
	public static final String MY_URI = Dish.NAMESPACE + "Dish";

	//property list
	public static final String P_ID;					// Integer
    public static final String P_NAME;					// String
    public static final String P_CONTAINS_PROCEDURE;	// Boolean
    public static final String P_CONTAINS_RECIPE;		// Boolean
    public static final String P_PICTURE;				// String
    public static final String P_DISH_CATEGORY;			// DishCategory
    public static final String P_RECIPE;				// Recipe
    
    static {
		// property names
    	P_ID 					= Dish.NAMESPACE + "id";
    	P_NAME 					= Dish.NAMESPACE + "name";
    	P_PICTURE 				= Dish.NAMESPACE + "hasPicture";
		P_CONTAINS_PROCEDURE 	= Dish.NAMESPACE + "containsProcedure";
		P_CONTAINS_RECIPE 		= Dish.NAMESPACE + "containsRecipe";
		P_DISH_CATEGORY			= Dish.NAMESPACE + "dishCategory";
		P_RECIPE 				= Dish.NAMESPACE + "hasRecipe";
		
		register(Dish.class);
    }

    public Dish() { }
    public Dish(String uri) { super(uri); }

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI.equals(P_ID))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Integer.class), 1, 1);
        if (propURI.equals(P_NAME))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(String.class), 1, 1);
        if (propURI.equals(P_CONTAINS_PROCEDURE))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Boolean.class), 0, 1);
        if (propURI.equals(P_CONTAINS_RECIPE))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(Boolean.class), 1, 1);
        if (propURI.equals(P_PICTURE))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(String.class), 0, 1);
        if (propURI.equals(P_DISH_CATEGORY))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         DishCategory.MY_URI, 1, 1);
        if (propURI.equals(P_RECIPE))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         Recipe.MY_URI, 0, 1);
	    return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] proper = {P_NAME, P_CONTAINS_PROCEDURE, P_ID, P_CONTAINS_RECIPE, P_PICTURE,
						P_DISH_CATEGORY, P_RECIPE};
		
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "comment of the dish ontology";
	}

	public static String getRDFSLabel() {
		return "dish";
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
	
	// NAME
	public String getName() {
		String v = (String) props.get(P_NAME);
		return v;
	}
	
	public void setName(String name) {
		if (name!=null)
			this.props.put(P_NAME, name);
	}
	
	// PROCEDURE
	public boolean getContainsProcedure() {
		Boolean v = (Boolean) props.get(P_CONTAINS_PROCEDURE);
		return v.booleanValue();
	}
	
	public void setProcedure(boolean contains_procedure) {
		this.props.put(P_CONTAINS_PROCEDURE, new Boolean(contains_procedure));
	}
	
	// ID
	public int getID() {
		Integer v = (Integer) props.get(P_ID);
		return v.intValue();
	}
	
	public void setID(int id) {
		this.props.put(P_ID, new Integer(id));
	}
	
	// CONTAINS_RECIPE
	public boolean getContainsRecipe() {
		Boolean v = (Boolean) props.get(P_CONTAINS_RECIPE);
		return v.booleanValue();
	}
	
	public void setContainsRecipe(boolean value) {
		this.props.put(P_CONTAINS_RECIPE, new Boolean(value));
	}
	
	// DISH CATEGORY
	public DishCategory getDishCategory() {
		DishCategory v = (DishCategory)props.get(P_DISH_CATEGORY);
		return v;
	}
	
	public void setDishCategory(int value) {
		this.props.put(P_DISH_CATEGORY, DishCategory.getDishCategoriesByOrder(value));
	}
	
	public void setDishCategory(DishCategory dc) {
		this.props.put(P_DISH_CATEGORY, dc);
	}
	
	// PICTURE
	public String getPicture() {
		String v = (String) props.get(P_PICTURE);
		return v;
	}
	
	public void setPicture(String pictureUrl) {
		if (pictureUrl!=null)
			this.props.put(P_PICTURE, pictureUrl);
	}
	
	// RECIPE
	public Recipe getRecipe() {
		Recipe v = (Recipe) props.get(P_RECIPE);
		return v;
	}
	
	public void setRecipe(Recipe recipe) {
		if (recipe!=null)
			this.props.put(P_RECIPE, recipe);
	}
}