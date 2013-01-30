/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.owl.ManagedIndividual;

/**
 * @author George Fournadjiev
 */
public class MealRelation extends ManagedIndividual {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "MealRelation";

  public static final int BEFORE = 0;
  public static final int WITH_MEAL = 1;
  public static final int AFTER = 2;
  public static final int ANY = 3;

  private static final String[] names = {"before", "with_meal", "after", "any"};

  public static final MealRelation MEAL_RELATION_BEFORE = new MealRelation(BEFORE);
  public static final MealRelation MEAL_RELATION_WITH_MEAL = new MealRelation(WITH_MEAL);
  public static final MealRelation MEAL_RELATION_AFTER = new MealRelation(AFTER);
  public static final MealRelation MEAL_RELATION_ANY = new MealRelation(ANY);


  public String getClassURI() {
    return MY_URI;
  }

  public static MealRelation getIntakeUnitByOrder(int order) {
    switch (order) {
      case BEFORE:
        return MEAL_RELATION_BEFORE;
      case WITH_MEAL:
        return MEAL_RELATION_WITH_MEAL;
      case AFTER:
        return MEAL_RELATION_AFTER;
      case ANY:
        return MEAL_RELATION_ANY;
      default:
        return null;
    }
  }

  public static final MealRelation valueOf(String name) {
    if (name == null)
      return null;

    if (name.startsWith(MedicationOntology.NAMESPACE))
      name = name.substring(MedicationOntology.NAMESPACE.length());

    for (int i = 0; i <= names.length; i++)
      if (names[i].equals(name))
        return getIntakeUnitByOrder(i);

    return null;
  }

  private int order = 0;

  private MealRelation(int order) {
    super(MedicationOntology.NAMESPACE + names[order]);
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


}
