package org.universAAL.AALapplication.medication_manager.persistence;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum Reference {

  INTAKE("INTAKE"),
  REFILL("REFILL");

  private String type;

  private Reference(String type) {
    this.type = type;
  }

  public static Reference getEnumValueFor(String referenceText) {

    for (Reference referenceEnum : values()) {
      if (referenceEnum.type.equalsIgnoreCase(referenceText)) {
        return referenceEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown Reference enum for value : " + referenceText);
  }
}
