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


package org.universAAL.AALapplication.medication_manager.persistence.layer.dto;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class MedicineDTO {

  private int medicineId;
  private final String name;
  private final int days;
  private final Date treatmentStartDate;
  private final Date treatmentEndDate;
  private final boolean missedIntakeAlert;
  private final boolean newDoseAlert;
  private final boolean shortageAlert;
  private final String description;
  private final String sideeffects;
  private final String incompliances;
  private final MealRelationDTO mealRelationDTO;
  private final Set<IntakeDTO> intakeDTOSet;

  public MedicineDTO(String name, Date startDate, int days, boolean missedIntakeAlert, boolean newDoseAlert,
                     boolean shortageAlert, String description, String sideeffects, String incompliances,
                     MealRelationDTO mealRelationDTO, Set<IntakeDTO> intakeDTOSet) {

    validateParameter(name, "name");
    validateParameter(startDate, "startDate");
    validateParameter(mealRelationDTO, "mealRelationDTO");
    validateParameter(intakeDTOSet, "intakeDTOSet");

    this.name = name;
    this.treatmentStartDate = startDate;
    this.treatmentEndDate = endDate(startDate, days);
    this.days = days;
    this.missedIntakeAlert = missedIntakeAlert;
    this.newDoseAlert = newDoseAlert;
    this.shortageAlert = shortageAlert;
    this.description = description;
    this.sideeffects = sideeffects;
    this.incompliances = incompliances;
    this.mealRelationDTO = mealRelationDTO;
    this.intakeDTOSet = intakeDTOSet;
  }

  public int getMedicineId() {
    if (medicineId == 0) {
      throw new MedicationManagerPersistenceException("The medicineId is not set");
    }
    return medicineId;
  }

  public void setMedicineIdId(int medicineId) {
    this.medicineId = medicineId;
  }

  private static Date endDate(Date startDate, int days) {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(startDate);

    cal.add(Calendar.DAY_OF_MONTH, days);

    return cal.getTime();
  }

  public String getName() {
    return name;
  }

  public Date getTreatmentStartDate() {
    return treatmentStartDate;
  }

  public Date getTreatmentEndDate() {
    return treatmentEndDate;
  }

  public int getDays() {
    return days;
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

  public String getDescription() {
    return description;
  }

  public String getSideeffects() {
    return sideeffects;
  }

  public String getIncompliances() {
    return incompliances;
  }

  public MealRelationDTO getMealRelationDTO() {
    return mealRelationDTO;
  }

  public Set<IntakeDTO> getIntakeDTOSet() {
    return intakeDTOSet;
  }

  @Override
  public String toString() {
    return "MedicineDTO{" +
        "medicineId=" + medicineId +
        ", name='" + name + '\'' +
        ", days=" + days +
        ", treatmentStartDate=" + treatmentStartDate +
        ", treatmentEndDate=" + treatmentEndDate +
        ", missedIntakeAlert=" + missedIntakeAlert +
        ", newDoseAlert=" + newDoseAlert +
        ", description='" + description + '\'' +
        ", sideeffects='" + sideeffects + '\'' +
        ", incompliances='" + incompliances + '\'' +
        ", mealRelationDTO=" + mealRelationDTO +
        ", intakeDTOSet=" + intakeDTOSet +
        '}';
  }
}
