package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Prescription {

  private final int prescriptionId;
  private final Date timeOfCreation;
  private final Person patient;
  private final Person physician;
  private final PrescriptionStatus prescriptionStatus;

  public Prescription(int prescriptionId, Date timeOfCreation, Person patient,
                      Person physician, PrescriptionStatus prescriptionStatus) {

    validate(prescriptionId, timeOfCreation, patient, physician, prescriptionStatus);

    this.prescriptionId = prescriptionId;
    this.timeOfCreation = timeOfCreation;
    this.patient = patient;
    this.physician = physician;
    this.prescriptionStatus = prescriptionStatus;
  }

  private void validate(int prescriptionId, Date timeOfCreation, Person patient,
                        Person physician, PrescriptionStatus prescriptionStatus) {

    validateParameter(prescriptionId, "prescriptionId");
    validateParameter(timeOfCreation, "timeOfCreation");
    validateParameter(patient, "patient");
    validateParameter(physician, "physician");
    validateParameter(prescriptionStatus, "prescriptionStatus");

  }

  public int getPrescriptionId() {
    return prescriptionId;
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
        "prescriptionId=" + prescriptionId +
        ", timeOfCreation=" + timeOfCreation +
        ", patient:" + patient +
        ", physician:" + physician +
        ", prescriptionStatus=" + prescriptionStatus +
        '}';
  }
}


