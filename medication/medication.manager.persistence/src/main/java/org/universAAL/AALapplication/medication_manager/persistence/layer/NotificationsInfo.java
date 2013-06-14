package org.universAAL.AALapplication.medication_manager.persistence.layer;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public final class NotificationsInfo {

  public static final String EMPTY = "";
  private final NotificationInfoComplexId complexId;
  private final String patient;
  private final String medicine;
  private final boolean missed;
  private final int threshold;
  private final boolean shortage;
  private final boolean dose;

  public NotificationsInfo(NotificationInfoComplexId complexId, String patient, String medicine, boolean missed,
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

  public NotificationsInfo(NotificationInfoComplexId complexId, boolean missed,
                           int threshold, boolean shortage, boolean dose) {

    this(complexId, EMPTY, EMPTY, missed, threshold, shortage, dose);
  }

  public String getComplexIdAsText() {
    return encodeComplexId(complexId.getPatientId(), complexId.getTreatmentId(), complexId.getMedicineInventoryId());
  }


  public NotificationInfoComplexId getComplexId() {
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


