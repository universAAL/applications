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

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public class Medicine extends ManagedIndividual {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "Medicine";

  public static final String PROP_MEDICINE_ID = MedicationOntology.NAMESPACE + "medicineId";

  public static final String PROP_NAME = MedicationOntology.NAMESPACE + "name";

  public static final String PROP_DAYS = MedicationOntology.NAMESPACE + "days";

  public static final String PROP_DESCRIPTION = MedicationOntology.NAMESPACE + "description";

  public static final String PROP_MEAL_RELATION = MedicationOntology.NAMESPACE + "mealRelation";

  public static final String PROP_INTAKES = MedicationOntology.NAMESPACE + "intakes";

  public Medicine() {
    super();
  }

  public Medicine(String uri) {
    super(uri);
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_FULL;
  }

  public String getClassURI() {
    return MY_URI;
  }

  public boolean isWellFormed() {
    return true;
  }

  public int getMedicineId() {
    return (Integer) props.get(PROP_MEDICINE_ID);
  }

  public void setMedicineId(int medicineId) {
    props.put(PROP_MEDICINE_ID, medicineId);
  }

  public String getName() {
    return (String) props.get(PROP_NAME);
  }

  public void setName(String medicineId) {
    props.put(PROP_NAME, medicineId);
  }

  public int getDays() {
    return (Integer) props.get(PROP_DAYS);
  }

  public void setDays(int days) {
    props.put(PROP_DAYS, days);
  }

  public String getDescription() {
    return (String) props.get(PROP_DESCRIPTION);
  }

  public void setDescription(String description) {
    props.put(PROP_DESCRIPTION, description);
  }

  public MealRelation getMealRelation() {
    return (MealRelation) props.get(PROP_MEAL_RELATION);
  }

  public void setMealRelation(MealRelation mealRelation) {
    props.put(PROP_MEAL_RELATION, mealRelation);
  }

  public List<Intake> getIntakes() {
    Object value = getProperty(PROP_INTAKES);
    if (value instanceof Intake) {
      ArrayList<Intake> medicines = new ArrayList<Intake>();
      medicines.add((Intake) value);
      return medicines;
    }
    return (List<Intake>) value;
  }

  public void setIntakes(List<Intake> intakeList) {
    if (intakeList == null || intakeList.isEmpty()) {
      return;
    }
    if (intakeList.size() > 1) {
      setProperty(PROP_INTAKES, intakeList);
    } else {
      Intake intake = intakeList.get(0);
      setProperty(PROP_INTAKES, intake);
    }
  }

}
