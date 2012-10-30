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


package org.universAAL.AALapplication.medication_manager.ui.prescription;

import java.util.Date;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class PrescriptionDTO {

  private final int id;
  private final String description;
  private final Date startDate;
  private final Set<MedicineDTO> medicineDTOSet;
  private final Doctor doctor;

  public PrescriptionDTO(int id, String description, Date startDate, Set<MedicineDTO> medicineDTOSet, Doctor doctor) {
    this.id = id;
    this.description = description;
    this.startDate = startDate;
    this.medicineDTOSet = medicineDTOSet;
    this.doctor = doctor;
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

  public Doctor getDoctor() {
    return doctor;
  }

  @Override
  public String toString() {
    return "Prescription{" +
        "id=" + id +
        ", description='" + description + '\'' +
        ", startDate=" + startDate +
        ", medicineSet=" + medicineDTOSet +
        ", doctor=" + doctor +
        '}';
  }
}
