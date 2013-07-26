/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum Role {

  PATIENT("PATIENT"),
  CAREGIVER("CAREGIVER"),
  ADMIN("ADMIN"),
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
