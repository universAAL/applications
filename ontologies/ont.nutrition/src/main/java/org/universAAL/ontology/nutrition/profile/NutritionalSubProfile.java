package org.universAAL.ontology.nutrition.profile;

import org.universAAL.ontology.nutrition.NutritionOntology;
import org.universAAL.ontology.profile.SubProfile;


public class NutritionalSubProfile extends SubProfile {
  public static final String MY_URI = NutritionOntology.NAMESPACE
    + "NutritionalSubProfile";
  public static final String PROP_NUTRITIONAL_HABITS = NutritionOntology.NAMESPACE
    + "nutritionalHabits";
  public static final String PROP_NUTRITIONAL_PREFERENCES = NutritionOntology.NAMESPACE
    + "nutritionalPreferences";


  public NutritionalSubProfile () {
    super();
  }
  
  public NutritionalSubProfile (String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
      return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true 
      && hasProperty(PROP_NUTRITIONAL_HABITS)
      && hasProperty(PROP_NUTRITIONAL_PREFERENCES);
  }

  public NutritionalHabits getNutritionalHabits() {
    return (NutritionalHabits)getProperty(PROP_NUTRITIONAL_HABITS);
  }		

  public void setNutritionalHabits(NutritionalHabits newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP_NUTRITIONAL_HABITS, newPropValue);
  }		

  public NutritionalPreferences getNutritionalPreferences() {
    return (NutritionalPreferences)getProperty(PROP_NUTRITIONAL_PREFERENCES);
  }		

  public void setNutritionalPreferences(NutritionalPreferences newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP_NUTRITIONAL_PREFERENCES, newPropValue);
  }		
}
