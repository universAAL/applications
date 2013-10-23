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

package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionView {

  private final int prescriptionId;
  private String description;
  private String startDate;
  private Map<Integer, MedicineView> medicineViews = new HashMap<Integer, MedicineView>();
  private Person physician;
  private Person patient;

  public NewPrescriptionView(int prescriptionId, Person physician, Person patient) {
    this.prescriptionId = prescriptionId;
    this.physician = physician;
    this.patient = patient;
  }

  public int getPrescriptionId() {
    return prescriptionId;
  }

  public String getNotes() {
    return description;
  }

  public void setNotes(String description) {
    if (description != null && !description.trim().isEmpty()) {
      this.description = description;
    }
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public Person getPhysician() {
    return physician;
  }

  public Person getPatient() {
    return patient;
  }

  public Set<MedicineView> getMedicineViewSet() {
    return new HashSet<MedicineView>(medicineViews.values());
  }

  public void addMedicineView(MedicineView medicineView) {
    medicineViews.put(medicineView.getMedicineId(), medicineView);
  }

  public void removeMedicineView(String medicineViewId) {
    int id = getIntFromString(medicineViewId, "medicineViewId");
    MedicineView medicineView = medicineViews.remove(id);
    if (medicineView == null) {
      throw new MedicationManagerServletUIException("Missing medicineView with id: " + medicineViewId);
    }
  }

  public MedicineView getMedicineView(String medicineId) {
    int medViewId = getIntFromString(medicineId, "medicineId");
    MedicineView medicineView = medicineViews.get(medViewId);
    if (medicineView == null) {
      throw new MedicationManagerServletUIException("Unexpected error. Missing medicineView with id: " + medViewId);
    }

    return medicineView;
  }

  @Override
  public String toString() {
    return "NewPrescriptionView{" +
        "prescriptionId=" + prescriptionId +
        ", description='" + description + '\'' +
        ", startDate='" + startDate + '\'' +
        ", medicineViews.size=" + medicineViews.size() +
        ", medicineViews.ids=" + getMVIds() +
        ", medicineViews=" + medicineViews +
        ", physician=" + physician +
        ", patient=" + patient +
        '}';
  }

  private String getMVIds() {
    StringBuffer sb = new StringBuffer();
    sb.append('[');
    int i = 0;
    int size = medicineViews.size();
    for (MedicineView med : medicineViews.values()) {
      sb.append(med.getMedicineId());

      i++;
      if (i < size) {
        sb.append(", ");

      }
    }

    sb.append(']');

    return sb.toString();
  }
}
