package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class PrescribedMedicine extends Entity {

  private final Prescription prescription;
  private final Medicine medicine;
  private final Treatment treatment;

  public PrescribedMedicine(int id, Prescription prescription,
                            Medicine medicine, Treatment treatment) {

    super(id);

    validate(prescription, medicine, treatment);

    this.prescription = prescription;
    this.medicine = medicine;
    this.treatment = treatment;
  }

  public PrescribedMedicine(Prescription prescription, Medicine medicine, Treatment treatment) {

    this(0, prescription, medicine, treatment);
  }

  private void validate(Prescription prescription, Medicine medicine, Treatment treatment) {

    validateParameter(prescription, "prescription");
    validateParameter(medicine, "medicine");
    validateParameter(treatment, "treatment");

  }

  public Prescription getPrescription() {
    return prescription;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public Treatment getTreatment() {
    return treatment;
  }

  @Override
  public String toString() {
    return "PrescribedMedicine{" +
        "id=" + getId() +
        ", " + prescription +
        ", " + medicine +
        ", " + treatment +
        '}';
  }
}


