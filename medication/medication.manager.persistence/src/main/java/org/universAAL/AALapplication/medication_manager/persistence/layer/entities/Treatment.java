package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Treatment extends Entity {

  private final Prescription prescription;
  private final Medicine medicine;
  private final TreatmentStatus status;

  public Treatment(int id, Prescription prescription, Medicine medicine, TreatmentStatus status) {

    super(id);

    validate(prescription, medicine, status);

    this.prescription = prescription;
    this.medicine = medicine;
    this.status = status;
  }

  public Treatment(Prescription prescription, Medicine medicine, TreatmentStatus status) {

    this(0, prescription, medicine, status);
  }

  private void validate(Prescription prescription, Medicine medicine, TreatmentStatus status) {

    validateParameter(prescription, "prescription");
    validateParameter(medicine, "medicine");
    validateParameter(status, "status");

  }

  public Prescription getPrescription() {
    return prescription;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public TreatmentStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "Treatment{" +
        "prescription=" + prescription +
        ", medicine=" + medicine +
        ", status=" + status +
        '}';
  }
}


