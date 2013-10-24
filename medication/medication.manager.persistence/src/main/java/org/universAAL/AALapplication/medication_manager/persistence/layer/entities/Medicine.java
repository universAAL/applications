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

package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class Medicine extends Entity {

  private final String medicineName;
  private final String medicineInfo;
  private final String medicineSideEffects;
  private final String incompliances;
  private final MealRelation mealRelation;
  private final UnitClass unitClass;

  public Medicine(int id, String medicineName, String medicineInfo, UnitClass unitClass,
                  String medicineSideEffects, String incompliances, MealRelation mealRelation) {

    super(id);

    validate(medicineName, mealRelation, unitClass);

    this.medicineName = medicineName;
    this.medicineInfo = medicineInfo != null ? medicineInfo : "";
    this.medicineSideEffects = medicineSideEffects != null ? medicineSideEffects : "";
    this.incompliances = incompliances != null ? incompliances : "";
    this.mealRelation = mealRelation;
    this.unitClass = unitClass;
  }

  public Medicine(String medicineName, String medicineInfo, UnitClass unitClass,
                  String medicineSideEffects, String incompliances, MealRelation mealRelation) {

    this(0, medicineName, medicineInfo, unitClass, medicineSideEffects, incompliances, mealRelation);
  }

  private void validate(String medicineName, MealRelation mealRelation, UnitClass unitClass) {

    validateParameter(medicineName, "medicineName");
    validateParameter(mealRelation, "mealRelation");
    validateParameter(unitClass, "unitClass");

  }

  public String getMedicineName() {
    return medicineName;
  }

  public String getMedicineInfo() {
    return medicineInfo;
  }

  public String getMedicineSideEffects() {
    return medicineSideEffects;
  }

  public String getIncompliances() {
    return incompliances;
  }

  public MealRelation getMealRelation() {
    return mealRelation;
  }

  public UnitClass getUnitClass() {
    return unitClass;
  }

  @Override
  public String toString() {
    return "Medicine{" +
        "medicineName='" + medicineName + '\'' +
        ", medicineInfo='" + medicineInfo + '\'' +
        ", medicineSideEffects='" + medicineSideEffects + '\'' +
        ", incompliances='" + incompliances + '\'' +
        ", mealRelation=" + mealRelation +
        ", unitClass=" + unitClass +
        '}';
  }
}
