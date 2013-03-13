package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Treatment extends Entity {

  private final String name;
  private final Person patient;
  private final Medicine medicine;
  private final Person physician;
  private final Status status;

  public Treatment(int id, String name, Person patient, Medicine medicine,
                   Person physician, Status status) {

    super(id);

    validate(name, patient, medicine, physician, status);

    this.name = name;
    this.patient = patient;
    this.medicine = medicine;
    this.physician = physician;
    this.status = status;
  }

  public Treatment(String name, Person patient, Medicine medicine,
                   Person physician, Status status) {

    this(0, name, patient, medicine, physician, status);
  }

  private void validate(String name, Person patient, Medicine medicine,
                        Person physician, Status status) {

    validateParameter(name, "name");
    validateParameter(patient, "patient");
    validateParameter(medicine, "medicine");
    validateParameter(physician, "physician");
    validateParameter(status, "status");

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
        "id=" + getId() +
        ", name='" + name + '\'' +
        ", patient:" + patient +
        ", " + medicine +
        ", physician:" + physician +
        ", status=" + status +
        '}';
  }
}


