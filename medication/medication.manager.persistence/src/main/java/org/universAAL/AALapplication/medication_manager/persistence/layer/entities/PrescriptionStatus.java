package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum PrescriptionStatus {

  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE");

  private String type;

  private PrescriptionStatus(String type) {
    this.type = type;
  }

  public static PrescriptionStatus getEnumValueFor(String statusText) {

    for (PrescriptionStatus statusEnum : values()) {
      if (statusEnum.type.equalsIgnoreCase(statusText)) {
        return statusEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown PrescriptionStatus enum for value : " + statusText);
  }

  public String getType() {
    return type;
  }
}