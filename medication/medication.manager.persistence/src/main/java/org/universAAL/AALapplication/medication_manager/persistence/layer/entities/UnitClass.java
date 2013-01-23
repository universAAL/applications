package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum UnitClass {

  PILLS("PILLS"),
  DROPS("DROPS");

  private String type;

  private UnitClass(String type) {
    this.type = type;
  }

  public static UnitClass getEnumValueFor(String unitClassText) {

    for (UnitClass unitClassEnum : values()) {
      if (unitClassEnum.type.equalsIgnoreCase(unitClassText)) {
        return unitClassEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown UnitClass enum for value : " + unitClassText);
  }
}
