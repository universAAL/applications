package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.Activator;

public class Recipe extends ManagedIndividual {

	public static final String NAMESPACE = Activator.MY_NAMESPACE;
	public static final String MY_URI = Recipe.NAMESPACE + "Recipe";

	//property list
	public static final String P_ID;			// Integer
    public static final String P_NAME;			// String
    public static final String P_PROCEDURE;		// String
    public static final String P_IS_FAVOURITE;	// Boolean
    public static final String P_PICTURE;		// String, Picture Bytes?
    public static final String P_DISH_CATEGORY;	// DishCategory
    public static final String P_INGREDIENTS;	// Ingredient
    
    static {
		// property names
    	P_ID 			= Recipe.NAMESPACE + "id";
    	P_NAME 			= Recipe.NAMESPACE + "name";
		P_PROCEDURE 	= Recipe.NAMESPACE + "procedure";
		P_IS_FAVOURITE 	= Recipe.NAMESPACE + "isFavourite";
		P_PICTURE 		= Recipe.NAMESPACE + "hasPicture";
		P_DISH_CATEGORY	= Recipe.NAMESPACE + "dishCategory";
		P_INGREDIENTS	= Recipe.NAMESPACE + "ingredient";
		
		register(Recipe.class);
    }

    public Recipe() { }
    public Recipe(String uri) { super(uri); }

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI.equals(P_ID))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(Integer.class), 1, 1);
        if (propURI.equals(P_NAME))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(String.class), 1, 1);
        if (propURI.equals(P_PROCEDURE))
         return Restriction.getAllValuesRestrictionWithCardinality(propURI,
	         TypeMapper.getDatatypeURI(String.class), 0, 1);
        if (propURI.equals(P_IS_FAVOURITE))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(Boolean.class), 1, 1);
        if (propURI.equals(P_PICTURE))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         TypeMapper.getDatatypeURI(String.class), 0, 1);
        if (propURI.equals(P_DISH_CATEGORY))
            return Restriction.getAllValuesRestrictionWithCardinality(propURI,
   	         DishCategory.MY_URI, 1, 1);
        if (propURI.equals(P_INGREDIENTS))
        	return Restriction.getAllValuesRestriction(propURI,Ingredient.MY_URI);
	    return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] proper = {P_NAME, P_PROCEDURE, P_ID, P_IS_FAVOURITE, P_PICTURE,
						P_DISH_CATEGORY, P_INGREDIENTS};
		
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "comment of the recipe ontology";
	}

	public static String getRDFSLabel() {
		return "recipe";
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
	public String getProcedure() {
		String v = (String) props.get(P_PROCEDURE);
		return v;
	}
	
	public void setProcedure(String procedure) {
		if (procedure!=null)
			this.props.put(P_PROCEDURE, procedure);
	}
	
	// ID
	public int getID() {
		Integer v = (Integer) props.get(P_ID);
		return v.intValue();
	}
	
	public void setID(int id) {
		this.props.put(P_ID, new Integer(id));
	}
	
	// isFAVOURITE
	public boolean isFavourite() {
		Boolean v = (Boolean) props.get(P_IS_FAVOURITE);
		return v.booleanValue();
	}
	
	public void setFavourite(boolean value) {
		this.props.put(P_IS_FAVOURITE, new Boolean(value));
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
	
	// INGREDIENTS
	public Ingredient[] getIngredients() {
		Ingredient[] i = (Ingredient[]) this.props.get(P_INGREDIENTS);
		return i;
	}
	
	public void setIngredients(Ingredient[] ingredients) {
		this.props.put(P_INGREDIENTS, ingredients);
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
}