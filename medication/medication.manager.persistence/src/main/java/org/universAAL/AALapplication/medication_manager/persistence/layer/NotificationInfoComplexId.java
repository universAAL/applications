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

package org.universAAL.AALapplication.medication_manager.persistence.layer;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class NotificationInfoComplexId {

  private final int patientId;
  private final int treatmentId;
  private final int medicineInventoryId;

  public NotificationInfoComplexId(int patientId, int treatmentId, int medicineInventoryId) {

    validateParameter(patientId, "patientId");
    validateParameter(treatmentId, "treatmentId");
    validateParameter(medicineInventoryId, "medicineInventoryId");

    this.patientId = patientId;
    this.treatmentId = treatmentId;
    this.medicineInventoryId = medicineInventoryId;
  }

  public int getPatientId() {
    return patientId;
  }

  public int getTreatmentId() {
    return treatmentId;
  }

  public int getMedicineInventoryId() {
    return medicineInventoryId;
  }

  @Override
  public String toString() {
    return "NotificationInfoComplexIdUtility{" +
        "patientId=" + patientId +
        ", treatmentId=" + treatmentId +
        ", medicineInventoryId=" + medicineInventoryId +
        '}';
  }
}
