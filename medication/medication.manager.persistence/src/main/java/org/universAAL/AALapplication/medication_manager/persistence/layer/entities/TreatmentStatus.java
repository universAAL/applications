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
public enum TreatmentStatus {

  INACTIVE("N"),
  PENDING("P"),
  ACTIVE("Y");

  private String value;

  private TreatmentStatus(String value) {
    this.value = value;
  }

  public static TreatmentStatus getEnumValueFor(String statusText) {

    for (TreatmentStatus statusEnum : values()) {
      if (statusEnum.value.equalsIgnoreCase(statusText)) {
        return statusEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown TreatmentStatus enum for value : " + statusText);
  }

  public String getValue() {
    return value;
  }
}
