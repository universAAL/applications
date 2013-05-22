package org.universAAL.ontology.nutrition.profile;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.nutrition.Food;
import org.universAAL.ontology.nutrition.NutritionOntology;
import org.universAAL.ontology.nutrition.Recipe;


public class NutritionalPreferences extends ManagedIndividual {
  public static final String MY_URI = NutritionOntology.NAMESPACE
    + "NutritionalPreferences";
  public static final String PROP__PREF_CONVENIENCE = NutritionOntology.NAMESPACE
    + "PrefConvenience";
  public static final String PROP__PREFERRED_CUISINES = NutritionOntology.NAMESPACE
    + "PreferredCuisines";
  public static final String PROP__MEDICINE_FOOD_INTERACTIONS = NutritionOntology.NAMESPACE
    + "MedicineFoodInteractions";
  public static final String PROP__PREFERRED_COOKING_TECHNIQUES = NutritionOntology.NAMESPACE
    + "PreferredCookingTechniques";
  public static final String PROP_FOOD_DISLIKES = NutritionOntology.NAMESPACE
    + "foodDislikes";
  public static final String PROP_FAVOURITE_RECIPES = NutritionOntology.NAMESPACE
    + "favouriteRecipes";


  public NutritionalPreferences () {
    super();
  }
  
  public NutritionalPreferences (String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	// TODO Implement or if for Device subclasses: remove 
	return 0;
  }

  public boolean isWellFormed() {
	return true 
      && hasProperty(PROP__PREF_CONVENIENCE)
      && hasProperty(PROP__PREFERRED_CUISINES)
      && hasProperty(PROP__MEDICINE_FOOD_INTERACTIONS)
      && hasProperty(PROP__PREFERRED_COOKING_TECHNIQUES)
      && hasProperty(PROP_FOOD_DISLIKES)
      && hasProperty(PROP_FAVOURITE_RECIPES);
  }

  public Recipe[] getFavRecipes() {
    Object propList = getProperty(PROP_FAVOURITE_RECIPES);
    if (propList instanceof List)
      return (Recipe[]) ((List) propList).toArray(new Recipe[0]);
    else if (propList != null)
      return new Recipe[] {(Recipe)propList}; // Handle special case of a single item not contained in a list
    return new Recipe[0];
  }

  public void addFavRecipes(Recipe newValue) {
    Object propList = getProperty(PROP_FAVOURITE_RECIPES);
    List newList;
    if (propList instanceof List)
      newList = (List)propList;
    else {
      newList = new ArrayList();
      if (propList != null)
        newList.add(propList); // Handle special case of a single previous item not contained in a list
    }
    newList.add(newValue);
    changeProperty(PROP_FAVOURITE_RECIPES, newList);
  }
  

  public void setFavRecipes(Recipe[] propertyValue) {
    List propList = new ArrayList(propertyValue.length);
    for (int i = 0; i < propertyValue.length; i++) {
      propList.add(propertyValue[i]);
    }
    changeProperty(PROP_FAVOURITE_RECIPES, propList);
  }
  
  public Food[] getFoodDislike() {
    Object propList = getProperty(PROP_FOOD_DISLIKES);
    if (propList instanceof List)
      return (Food[]) ((List) propList).toArray(new Food[0]);
    else if (propList != null)
      return new Food[] {(Food)propList}; // Handle special case of a single item not contained in a list
    return new Food[0];
  }

  public void addFoodDislike(Food newValue) {
    Object propList = getProperty(PROP_FOOD_DISLIKES);
    List newList;
    if (propList instanceof List)
      newList = (List)propList;
    else {
      newList = new ArrayList();
      if (propList != null)
        newList.add(propList); // Handle special case of a single previous item not contained in a list
    }
    newList.add(newValue);
    changeProperty(PROP_FOOD_DISLIKES, newList);
  }
  

  public void setFoodDislike(Food[] propertyValue) {
    List propList = new ArrayList(propertyValue.length);
    for (int i = 0; i < propertyValue.length; i++) {
      propList.add(propertyValue[i]);
    }
    changeProperty(PROP_FOOD_DISLIKES, propList);
  }

  public String getPrefConvenience() {
    return (String)getProperty(PROP__PREF_CONVENIENCE);
  }		

  public void setPrefConvenience(String newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP__PREF_CONVENIENCE, newPropValue);
  }		

  public String getMedicineFoodInteractions() {
    return (String)getProperty(PROP__MEDICINE_FOOD_INTERACTIONS);
  }		

  public void setMedicineFoodInteractions(String newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP__MEDICINE_FOOD_INTERACTIONS, newPropValue);
  }		

  public String getPreferredCookingTechniques() {
    return (String)getProperty(PROP__PREFERRED_COOKING_TECHNIQUES);
  }		

  public void setPreferredCookingTechniques(String newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP__PREFERRED_COOKING_TECHNIQUES, newPropValue);
  }		

  public String getPreferredCuisines() {
    return (String)getProperty(PROP__PREFERRED_CUISINES);
  }		

  public void setPreferredCuisines(String newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP__PREFERRED_CUISINES, newPropValue);
  }		
}
