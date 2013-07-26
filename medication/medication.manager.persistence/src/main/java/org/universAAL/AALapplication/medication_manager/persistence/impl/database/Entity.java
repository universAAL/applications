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

package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.configuration.MedicationManagerConfigurationException;

/**
 * @author George Fournadjiev
 */
public abstract class Entity {

  private final int id;

  protected Entity(int id) {
    validateId(id, "id");

    this.id = id;
  }

  protected Entity() {
    this(0);
  }

  private void validateId(int parameter, String parameterName) {

    if (parameter < 0) {
      throw new MedicationManagerConfigurationException("The parameter : " +
          parameterName + " cannot be negative number");
    }

  }

  public int getId() {
    return id;
  }
}
