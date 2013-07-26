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

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum MealRelation {

  BEFORE("BEFORE"),
  WITH_MEAL("WITH_MEAL"),
  AFTER("AFTER"),
  ANY("ANY");

  private String mealRelation;

  private MealRelation(String mealRelation) {
    this.mealRelation = mealRelation;
  }

  public String getValue() {
    return mealRelation;
  }

  public static MealRelation getEnumValueFor(String mealRelationText) {

    for (MealRelation mealRelationEnum : values()) {
      if (mealRelationEnum.mealRelation.equalsIgnoreCase(mealRelationText)) {
        return mealRelationEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown MealRelation enum for value : " + mealRelationText);
  }
}
