package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum Status {

  PLANNED("PLANNED"),
  ACTIVED("ACTIVED"),
  FINISHED("FINISHED"),
  CANCELLED("CANCELLED"),
  PROLONGED("PROLONGED");

  private String type;

  private Status(String type) {
    this.type = type;
  }

  public static Status getEnumValueFor(String statusText) {

    for (Status statusEnum : values()) {
      if (statusEnum.type.equalsIgnoreCase(statusText)) {
        return statusEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown Status enum for value : " + statusText);
  }
}