package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class PrescribedMedicine {

  private final int prescribedMedicineId;
  private final Prescription prescription;
  private final Medicine medicine;
  private final Treatment treatment;

  public PrescribedMedicine(int prescribedMedicineId, Prescription prescription,
                            Medicine medicine, Treatment treatment) {

    validate(prescribedMedicineId, prescription, medicine, treatment);


    this.prescribedMedicineId = prescribedMedicineId;
    this.prescription = prescription;
    this.medicine = medicine;
    this.treatment = treatment;
  }

  private void validate(int prescribedMedicineId, Prescription prescription,
                        Medicine medicine, Treatment treatment) {

    validateParameter(prescribedMedicineId, "prescribedMedicineId");
    validateParameter(prescription, "prescription");
    validateParameter(medicine, "medicine");
    validateParameter(treatment, "treatment");

  }

  public int getPrescribedMedicineId() {
    return prescribedMedicineId;
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
        "prescribedMedicineId=" + prescribedMedicineId +
        ", " + prescription +
        ", " + medicine +
        ", " + treatment +
        '}';
  }
}


