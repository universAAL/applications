package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;

import java.util.Date;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionView {

  private final int prescriptionId;
  private String description;
  private Date startDate;
  private Set<MedicineDTO> medicineDTOSet;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Person getPhysician() {
    return physician;
  }

  public Person getPatient() {
    return patient;
  }

  public Set<MedicineDTO> getMedicineDTOSet() {
    return medicineDTOSet;
  }

  public void setMedicineDTOSet(Set<MedicineDTO> medicineDTOSet) {
    this.medicineDTOSet = medicineDTOSet;
  }

}
