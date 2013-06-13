package org.universAAL.AALapplication.medication_manager.persistence.layer;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class NotificationsInfo {

  private final String complexId;
  private final String patient;
  private final String medicine;
  private final boolean missed;
  private final int threshold;
  private final boolean shortage;
  private final boolean dose;

  public NotificationsInfo(String complexId, String patient, String medicine, boolean missed,
                           int threshold, boolean shortage, boolean dose) {

    validateParameter(complexId, "complexId");
    validateParameter(patient, "patient");
    validateParameter(medicine, "medicine");
    validateParameter(threshold, "threshold");

    this.complexId = complexId;
    this.patient = patient;
    this.medicine = medicine;
    this.missed = missed;
    this.threshold = threshold;
    this.shortage = shortage;
    this.dose = dose;
  }

  public String getComplexId() {
    return complexId;
  }

  public String getPatient() {
    return patient;
  }

  public String getMedicine() {
    return medicine;
  }

  public boolean isMissed() {
    return missed;
  }

  public int getThreshold() {
    return threshold;
  }

  public boolean isShortage() {
    return shortage;
  }

  public boolean isDose() {
    return dose;
  }

  @Override
  public String toString() {
    return "NotificationsInfo{" +
        "complexId=" + complexId +
        ", patient='" + patient + '\'' +
        ", medicine='" + medicine + '\'' +
        ", missed=" + missed +
        ", threshold=" + threshold +
        ", shortage=" + shortage +
        ", dose=" + dose +
        '}';
  }
}


