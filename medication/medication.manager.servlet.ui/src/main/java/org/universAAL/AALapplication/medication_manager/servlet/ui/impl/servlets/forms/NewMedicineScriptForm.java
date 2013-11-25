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

package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MealRelationDTO;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.MedicineView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class NewMedicineScriptForm extends ScriptForm {

  public static final String PREFIX = "medicineObj.";
  public static final String SUFFIX = "\';";
  public static final String ID = "id";
  public static final String PRESCRIPTION_ID = "prescriptionId";
  public static final String IS_NEW = "isNew";
  public static final String MEDICINE_OBJ_NAME = "name";
  public static final String MEDICINE_OBJ_DESCRIPTION = "description";
  public static final String MEDICINE_OBJ_SIDE_EFFECTS = "side_effects";
  public static final String MEDICINE_OBJ_INCOMPLIANCES = "incompliances";
  public static final String MEDICINE_OBJ_DAYS = "days";
  public static final String MEDICINE_OBJ_DOSE = "dose";
  public static final String MEDICINE_OBJ_UNIT = "unit";
  public static final String MEDICINE_OBJ_MEAL_RELATION = "meal_relation";
  public static final String MEDICINE_OBJ_HOURS = "hours";
  private final PersistentService persistentService;
  private final MedicineView medicineView;
  private final int prescriptionId;

  private static final String NEW_PRESCRIPTION_FUNCTION_CALL_TEXT = "prescriptionObj.medicines.push";


  public NewMedicineScriptForm(PersistentService persistentService, MedicineView medicineView, int prescriptionId) {
    super(NEW_PRESCRIPTION_FUNCTION_CALL_TEXT);

    this.persistentService = persistentService;
    this.medicineView = medicineView;
    this.prescriptionId = prescriptionId;
    setSingleJavascriptObjects();
  }

  @Override
  public void setSingleJavascriptObjects() {

//    fillWithTempData();

    List<String> singleObjects = new ArrayList<String>();

    String medId = createSingleObjectText(ID, medicineView.getMedicineId());
    singleObjects.add(medId);

    String prescrId = createSingleObjectText(PRESCRIPTION_ID, prescriptionId);
    singleObjects.add(prescrId);

    String isNewText = PREFIX + IS_NEW + " = " + medicineView.isNew();
    singleObjects.add(isNewText);

    String name = medicineView.getName();
    if (name != null) {
      String value = createSingleObjectText(MEDICINE_OBJ_NAME, name);
      singleObjects.add(value);
    }

    String description = medicineView.getDescription();
    if (description != null) {
      String value = createSingleObjectText(MEDICINE_OBJ_DESCRIPTION, description);
      singleObjects.add(value);
    }

    String sideeffects = medicineView.getSideeffects();
    if (sideeffects != null) {
      String value = createSingleObjectText(MEDICINE_OBJ_SIDE_EFFECTS, sideeffects);
      singleObjects.add(value);
    }

    String incompliances = medicineView.getIncompliances();
    if (incompliances != null) {
      String value = createSingleObjectText(MEDICINE_OBJ_INCOMPLIANCES, incompliances);
      singleObjects.add(value);
    }

    int days = medicineView.getDays();
    if (days > 0) {
      String value = PREFIX + MEDICINE_OBJ_DAYS + " = " + days;
      singleObjects.add(value);
    }

    int dose = medicineView.getDose();
    if (dose > 0) {
      String value = PREFIX + MEDICINE_OBJ_DOSE + " = " + dose;
      singleObjects.add(value);
    }

    String unit = medicineView.getUnit();
    if (unit != null) {
      String value = createSingleObjectText(MEDICINE_OBJ_UNIT, unit);
      singleObjects.add(value);
    }

    MealRelationDTO mr = medicineView.getMealRelationDTO();
    if (mr != null) {
      String value = createSingleObjectText(MEDICINE_OBJ_MEAL_RELATION, mr.getValue());
      singleObjects.add(value);
    }

    String hours = medicineView.getHours();
    if (hours != null) {
      String value = PREFIX + MEDICINE_OBJ_HOURS + " = " + hours;
      singleObjects.add(value);
    }

    String[] objects = new String[singleObjects.size()];
    this.singleJavascriptObjects = singleObjects.toArray(objects);
  }

  private String createSingleObjectText(String field, Object value) {

    return PREFIX + field + " = \'" + value + SUFFIX;
  }

  @Override
  public void process() {

    //nothing we can do here
  }


}
