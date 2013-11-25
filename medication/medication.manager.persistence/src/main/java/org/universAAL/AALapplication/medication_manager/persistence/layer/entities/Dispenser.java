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

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Dispenser extends Entity {

  private final Person patient;
  private final String name;
  private final String dispenserUri;
  private final String instructionsFileName;
  private final boolean dueIntakeAlert;
  private final boolean successfulIntakeAlert;
  private final boolean missedIntakeAlert;
  private final boolean upsideDownAlert;

  public Dispenser(int id, Person patient, String name, String dispenserUri, String instructionsFileName,
                   boolean dueIntakeAlert, boolean successfulIntakeAlert,
                   boolean missedIntakeAlert, boolean upsideDownAlert) {
    super(id);

    validate(name, dispenserUri, instructionsFileName);

    this.patient = patient;
    this.name = name;
    this.dispenserUri = dispenserUri;
    this.instructionsFileName = instructionsFileName;
    this.dueIntakeAlert = dueIntakeAlert;
    this.successfulIntakeAlert = successfulIntakeAlert;
    this.missedIntakeAlert = missedIntakeAlert;
    this.upsideDownAlert = upsideDownAlert;
  }

  public Dispenser(Person patient, String name, String dispenserUri, String instructionsFileName,
                   boolean dueIntakeAlert, boolean successfulIntakeAlert,
                   boolean missedIntakeAlert, boolean upsideDownAlert) {

    this(0, patient, name, dispenserUri, instructionsFileName, dueIntakeAlert,
        successfulIntakeAlert, missedIntakeAlert, upsideDownAlert);
  }

  private void validate(String name, String dispenserUri, String instructionsFileName) {

    validateParameter(name, "name");
    validateParameter(dispenserUri, "dispenserUri");
    validateParameter(instructionsFileName, "instructionsFileName");
  }

  public Person getPatient() {
    return patient;
  }

  public String getDispenserUri() {
    return dispenserUri;
  }

  public String getInstructionsFileName() {
    return instructionsFileName;
  }

  public String getName() {
    return name;
  }

  public boolean isDueIntakeAlert() {
    return dueIntakeAlert;
  }

  public boolean isSuccessfulIntakeAlert() {
    return successfulIntakeAlert;
  }

  public boolean isMissedIntakeAlert() {
    return missedIntakeAlert;
  }

  public boolean isUpsideDownAlert() {
    return upsideDownAlert;
  }

  @Override
  public String toString() {
    return "Dispenser{" +
        "patient=" + patient +
        ", name='" + name + '\'' +
        ", dispenserUri='" + dispenserUri + '\'' +
        ", instructionsFileName='" + instructionsFileName + '\'' +
        ", dueIntakeAlert=" + dueIntakeAlert +
        ", successfulIntakeAlert=" + successfulIntakeAlert +
        ", missedIntakeAlert=" + missedIntakeAlert +
        ", upsideDownAlert=" + upsideDownAlert +
        '}';
  }
}


