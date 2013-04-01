package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Prescription extends Entity {

  private final Date timeOfCreation;
  private final Person patient;
  private final Person physician;
  private final String description;
  private final PrescriptionStatus prescriptionStatus;

  public Prescription(int id, Date timeOfCreation, Person patient, Person physician,
                      String description, PrescriptionStatus prescriptionStatus) {

    super(id);

    validate(timeOfCreation, patient, physician, prescriptionStatus, description);

    this.timeOfCreation = timeOfCreation;
    this.patient = patient;
    this.physician = physician;
    this.description = description;
    this.prescriptionStatus = prescriptionStatus;
  }

  public Prescription(Date timeOfCreation, Person patient, String description,
                      Person physician, PrescriptionStatus prescriptionStatus) {

    this(0, timeOfCreation, patient, physician, description, prescriptionStatus);

  }

  private void validate(Date timeOfCreation, Person patient,
                        Person physician, PrescriptionStatus prescriptionStatus, String description) {

    validateParameter(timeOfCreation, "timeOfCreation");
    validateParameter(patient, "patient");
    validateParameter(physician, "physician");
    validateParameter(prescriptionStatus, "prescriptionStatus");
    validateParameter(description, "description");

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

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "Prescription{" +
        "timeOfCreation=" + timeOfCreation +
        ", patient=" + patient +
        ", physician=" + physician +
        ", description='" + description + '\'' +
        ", prescriptionStatus=" + prescriptionStatus +
        '}';
  }
}


