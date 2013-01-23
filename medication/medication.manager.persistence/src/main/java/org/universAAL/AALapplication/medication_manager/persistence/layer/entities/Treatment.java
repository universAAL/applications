package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Treatment {

  private final int treatmentId;
  private final String name;
  private final Person patient;
  private final Medicine medicine;
  private final Person physician;
  private final Status status;

  public Treatment(int treatmentId, String name, Person patient, Medicine medicine,
                   Person physician, Status status) {

    validate(treatmentId, name, patient, medicine, physician, status);

    this.treatmentId = treatmentId;
    this.name = name;
    this.patient = patient;
    this.medicine = medicine;
    this.physician = physician;
    this.status = status;
  }

  private void validate(int treatmentId, String name, Person patient, Medicine medicine,
                        Person physician, Status status) {

    validateParameter(treatmentId, "treatmentId");
    validateParameter(name, "name");
    validateParameter(patient, "patient");
    validateParameter(medicine, "medicine");
    validateParameter(physician, "physician");
    validateParameter(status, "status");

  }

  public int getTreatmentId() {
    return treatmentId;
  }

  public String getName() {
    return name;
  }

  public Person getPatient() {
    return patient;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public Person getPhysician() {
    return physician;
  }

  public Status getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "Treatment{" +
        "treatmentId=" + treatmentId +
        ", name='" + name + '\'' +
        ", patient:" + patient +
        ", " + medicine +
        ", physician:" + physician +
        ", status=" + status +
        '}';
  }
}


