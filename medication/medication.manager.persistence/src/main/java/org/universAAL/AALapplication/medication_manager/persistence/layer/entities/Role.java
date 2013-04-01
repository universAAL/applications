package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum Role {

  PATIENT("PATIENT"),
  CAREGIVER("CAREGIVER"),
  PHYSICIAN("PHYSICIAN");

  private String role;

  private Role(String role) {
    this.role = role;
  }

  public static Role getEnumValueFor(String roleText) {

    for (Role roleEnum : values()) {
      if (roleEnum.role.equalsIgnoreCase(roleText)) {
        return roleEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown Role enum for value : " + roleText);
  }

  public String getValue() {
    return role;
  }
}
