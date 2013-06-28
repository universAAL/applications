package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum TreatmentStatus {

  INACTIVE("N"),
  PENDING("P"),
  ACTIVE("Y");

  private String value;

  private TreatmentStatus(String value) {
    this.value = value;
  }

  public static TreatmentStatus getEnumValueFor(String statusText) {

    for (TreatmentStatus statusEnum : values()) {
      if (statusEnum.value.equalsIgnoreCase(statusText)) {
        return statusEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown TreatmentStatus enum for value : " + statusText);
  }

  public String getValue() {
    return value;
  }
}
