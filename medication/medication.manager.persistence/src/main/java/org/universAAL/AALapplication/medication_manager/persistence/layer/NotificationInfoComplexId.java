package org.universAAL.AALapplication.medication_manager.persistence.layer;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class NotificationInfoComplexId {

  private final int patientId;
  private final int treatmentId;
  private final int medicineInventoryId;

  public NotificationInfoComplexId(int patientId, int treatmentId, int medicineInventoryId) {

    validateParameter(patientId, "patientId");
    validateParameter(treatmentId, "treatmentId");
    validateParameter(medicineInventoryId, "medicineInventoryId");

    this.patientId = patientId;
    this.treatmentId = treatmentId;
    this.medicineInventoryId = medicineInventoryId;
  }

  public int getPatientId() {
    return patientId;
  }

  public int getTreatmentId() {
    return treatmentId;
  }

  public int getMedicineInventoryId() {
    return medicineInventoryId;
  }

  @Override
  public String toString() {
    return "NotificationInfoComplexIdUtility{" +
        "patientId=" + patientId +
        ", treatmentId=" + treatmentId +
        ", medicineInventoryId=" + medicineInventoryId +
        '}';
  }
}
