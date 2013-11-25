package org.universAAL.ontology.nutrition.profile;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.nutrition.NutritionOntology;

public class DietType extends ManagedIndividual {
  public static final String MY_URI = NutritionOntology.NAMESPACE
    + "DietType";

  public static final int _SAXON = 0;
  public static final int _MEDITERRANEAN = 1;
  public static final int _VEGETARIAN = 2;
  public static final int _VEGAN = 3;
  public static final int _OTHER = 4;

  private static final String[] names = {
    "Saxon","Mediterranean","Vegetarian","Vegan","Other" };

  public static final DietType Saxon = new DietType(_SAXON);
  public static final DietType Mediterranean = new DietType(_MEDITERRANEAN);
  public static final DietType Vegetarian = new DietType(_VEGETARIAN);
  public static final DietType Vegan = new DietType(_VEGAN);
  public static final DietType Other = new DietType(_OTHER);

  private int order;

  private DietType(int order) {
    super(NutritionOntology.NAMESPACE + names[order]);
    this.order = order;
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_OPTIONAL;
  }

  public boolean isWellFormed() {
    return true;
  }

  public String name() {
    return names[order];
  }

  public int ord() {
    return order;
  }

  public String getClassURI() {
    return MY_URI;
  }

  public static DietType getDietTypeByOrder(int order) {
    switch (order) {
      case _SAXON:
        return Saxon;
      case _MEDITERRANEAN:
        return Mediterranean;
      case _VEGETARIAN:
        return Vegetarian;
      case _VEGAN:
        return Vegan;
      case _OTHER:
        return Other;
    default:
      return null;    }
  }

  public static final DietType valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(NutritionOntology.NAMESPACE))
	    name = name.substring(NutritionOntology.NAMESPACE.length());

	for (int i = _SAXON; i <= _OTHER; i++)
	    if (names[i].equals(name))
		return getDietTypeByOrder(i);

	return null;
  }
}
