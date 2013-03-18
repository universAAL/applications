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

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;

import java.util.Date;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class PrescriptionDTO {

  private final int id;
  private final String description;
  private final Date startDate;
  private final Set<MedicineDTO> medicineDTOSet;
  private final Person physician;
  private final Person patient;

  public PrescriptionDTO(int id, String description, Date startDate,
                         Set<MedicineDTO> medicineDTOSet, Person physician, Person patient) {

    validateParameter(id, "id");
    validateParameter(description, "description");
    validateParameter(startDate, "startDate");
    validateParameter(medicineDTOSet, "medicineDTOSet");
    validateParameter(medicineDTOSet, "medicineDTOSet");
    validateParameter(physician, "physician");
    validateParameter(patient, "patient");

    this.id = id;
    this.description = description.toUpperCase();
    this.startDate = startDate;
    this.medicineDTOSet = medicineDTOSet;
    this.physician = physician;
    this.patient = patient;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Set<MedicineDTO> getMedicineDTOSet() {
    return medicineDTOSet;
  }

  public Person getPhysician() {
    return physician;
  }

  public Person getPatient() {
    return patient;
  }

  @Override
  public String toString() {
    return "PrescriptionDTO{" +
        "id=" + id +
        ", description='" + description + '\'' +
        ", startDate=" + startDate +
        ", medicineDTOSet=" + medicineDTOSet +
        ", physician=" + physician +
        ", patient=" + patient +
        '}';
  }
}
