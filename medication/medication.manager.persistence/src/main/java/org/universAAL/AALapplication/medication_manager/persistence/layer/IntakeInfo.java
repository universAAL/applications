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

import org.universAAL.AALapplication.medication_manager.persistence.impl.Activator;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class IntakeInfo {

  private final String date;
  private final String time;
  private final String medication;
  private final String status;
  private final String internationalizationStatus;
  private final Date realDate;

  public static final String COMING = "coming";
  public static final String MISSED = "missed";
  public static final String TAKEN = "taken";

  public IntakeInfo(String date, String time, String medication, String status, Date realDate) {

    validateParameters(date, time, medication, status, realDate);

    this.date = date;
    this.time = time;
    this.medication = medication;
    this.status = status;
    this.realDate = realDate;
    internationalizationStatus = createInternationalizationStatus(status);
  }

  private String createInternationalizationStatus(String status) {
    if (status.equals(COMING)) {
      return Activator.getMessage("medication.manager.intake.review.coming");
    }

    if (status.equals(TAKEN)) {
      return Activator.getMessage("medication.manager.intake.review.taken");
    }

    if (status.equals(MISSED)) {
      return Activator.getMessage("medication.manager.intake.review.missed");
    }

    throw new MedicationManagerPersistenceException("Very strange unexpected exception");
  }

  private void validateParameters(String date, String time, String medication, String status, Date realDate) {
    validateParameter(date, "date");
    validateParameter(time, "time");
    validateParameter(medication, "medication");
    validateParameter(status, "status");
    validateParameter(realDate, "realDate");

    boolean statusOK = status.equals(COMING) || status.equals(MISSED) || status.equals(TAKEN);
    if (!statusOK) {
      throw new MedicationManagerPersistenceException("The status is incorrect : " + status +
          ". It can be one of the following: " + COMING + ", " + MISSED + ", " + TAKEN);
    }

  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }

  public String getMedication() {
    return medication;
  }

  public String getStatus() {
    return status;
  }

  public String getInternationalizationStatus() {
    return internationalizationStatus;
  }

  public Date getRealDate() {
    return realDate;
  }

  @Override
  public String toString() {
    return "IntakeInfo{" +
        "date='" + date + '\'' +
        ", time='" + time + '\'' +
        ", medication='" + medication + '\'' +
        ", status='" + status + '\'' +
        ", realDate='" + realDate + '\'' +
        '}';
  }
}
