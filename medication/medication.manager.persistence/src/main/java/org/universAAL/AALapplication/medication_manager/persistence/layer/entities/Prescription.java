package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Prescription extends Entity {

  private final Date timeOfCreation;
  private final Person patient;
  private final Person physician;
  private final PrescriptionStatus prescriptionStatus;

  public Prescription(int id, Date timeOfCreation, Person patient,
                      Person physician, PrescriptionStatus prescriptionStatus) {

    super(id);

    validate(timeOfCreation, patient, physician, prescriptionStatus);

    this.timeOfCreation = timeOfCreation;
    this.patient = patient;
    this.physician = physician;
    this.prescriptionStatus = prescriptionStatus;
  }

  public Prescription(Date timeOfCreation, Person patient,
                      Person physician, PrescriptionStatus prescriptionStatus) {

    this(0, timeOfCreation, patient, physician, prescriptionStatus);

  }

  private void validate(Date timeOfCreation, Person patient,
                        Person physician, PrescriptionStatus prescriptionStatus) {

    validateParameter(timeOfCreation, "timeOfCreation");
    validateParameter(patient, "patient");
    validateParameter(physician, "physician");
    validateParameter(prescriptionStatus, "prescriptionStatus");

  }

  public Date getTimeOfCreation() {
    return timeOfCreation;
  }

  public Person getPatient() {
    return patient;
  }

  public Person getPhysician() {
    return physician;
  }

  public PrescriptionStatus getPrescriptionStatus() {
    return prescriptionStatus;
  }

  @Override
  public String toString() {
    return "Prescription{" +
        "id=" + getId() +
        ", timeOfCreation=" + timeOfCreation +
        ", patient:" + patient +
        ", physician:" + physician +
        ", prescriptionStatus=" + prescriptionStatus +
        '}';
  }
}


