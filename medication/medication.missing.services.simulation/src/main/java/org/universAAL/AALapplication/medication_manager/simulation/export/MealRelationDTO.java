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


package org.universAAL.AALapplication.medication_manager.simulation.export;

import org.universAAL.ontology.medMgr.MedicationException;

/**
 * @author George Fournadjiev
 */
public enum MealRelationDTO {
  BEFORE("before"),
  AFTER("after"),
  ANY("any"),
  WITH_MEAL("with_meal");

  private final String value;

  private MealRelationDTO(String value) {
    this.value = value;
  }

  public static MealRelationDTO getEnumValueFor(String mealRelationText) {
    for (MealRelationDTO mealRelationDTO : values()) {
      if (mealRelationDTO.value.equals(mealRelationText)) {
        return mealRelationDTO;
      }
    }

    throw new MedicationException("Unknown MealRelationDTO enum value: " + mealRelationText);
  }

  public static String getStringValueFor(MealRelationDTO mealRelationDTO) {
    return mealRelationDTO.value;
  }

}
