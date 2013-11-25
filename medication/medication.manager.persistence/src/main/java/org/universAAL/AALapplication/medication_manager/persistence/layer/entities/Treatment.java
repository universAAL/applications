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

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Treatment extends Entity {

  private final Prescription prescription;
  private final Medicine medicine;
  private final Date startDate;
  private final Date endDate;
  private final TreatmentStatus status;
  private final boolean missedIntakeAlert;
  private final boolean newDoseAlert;
  private final boolean shortageAlert;

  public Treatment(int id, Prescription prescription, Medicine medicine, boolean missedIntakeAlert,
                   boolean newDoseAlert, boolean shortageAlert, Date startDate, Date endDate, TreatmentStatus status) {

    super(id);

    validate(prescription, medicine, startDate,endDate, status);

    this.prescription = prescription;
    this.medicine = medicine;
    this.missedIntakeAlert = missedIntakeAlert;
    this.newDoseAlert = newDoseAlert;
    this.shortageAlert = shortageAlert;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  public Treatment(Prescription prescription, Medicine medicine, boolean missedIntakeAlert,
                     boolean newDoseAlert, boolean shortageAlert, Date startDate, Date endDate, TreatmentStatus status) {

    this(0, prescription, medicine, missedIntakeAlert, newDoseAlert, shortageAlert, startDate, endDate, status);
  }

  private void validate(Prescription prescription, Medicine medicine,
                        Date startDate, Date endDate, TreatmentStatus status) {

    validateParameter(prescription, "prescription");
    validateParameter(medicine, "medicine");
    validateParameter(startDate, "startDate");
    validateParameter(endDate, "endDate");
    validateParameter(status, "status");

  }

  public Prescription getPrescription() {
    return prescription;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public TreatmentStatus getStatus() {
    return status;
  }

  public boolean isMissedIntakeAlert() {
    return missedIntakeAlert;
  }

  public boolean isNewDoseAlert() {
    return newDoseAlert;
  }

  public boolean isShortageAlert() {
    return shortageAlert;
  }

  @Override
  public String toString() {
    return "Treatment{" +
        "prescription=" + prescription +
        ", medicine=" + medicine +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", status=" + status +
        ", missedIntakeAlert=" + missedIntakeAlert +
        ", newDoseAlert=" + newDoseAlert +
        ", shortageAlert=" + shortageAlert +
        '}';
  }
}


