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
