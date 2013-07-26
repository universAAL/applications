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
public final class PatientLinks extends Entity {

  private final Person doctor;
  private final Person patient;
  private final Person caregiver;

  public PatientLinks(int id, Person doctor, Person patient, Person caregiver) {
    super(id);

    validateParameter(doctor, "doctor");
    validateParameter(patient, "patient");
    validateParameter(caregiver, "caregiver");

    this.doctor = doctor;
    this.patient = patient;
    this.caregiver = caregiver;
  }

  public Person getDoctor() {
    return doctor;
  }

  public Person getPatient() {
    return patient;
  }

  public Person getCaregiver() {
    return caregiver;
  }

  @Override
  public String toString() {
    return "PatientLinks{" +
        "doctor=" + doctor +
        ", patient=" + patient +
        ", caregiver=" + caregiver +
        '}';
  }
}
