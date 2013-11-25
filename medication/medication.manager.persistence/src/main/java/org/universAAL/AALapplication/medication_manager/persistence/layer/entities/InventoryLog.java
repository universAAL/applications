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

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class InventoryLog extends Entity {

  private final Date timeOfCreation;
  private final Person patient;
  private final Medicine medicine;
  private final int changedQuantity;
  private final UnitClass unitClass;
  private final Reference reference;

  public InventoryLog(int id, Date timeOfCreation, Person patient, Medicine medicine,
                      int changedQuantity, UnitClass unitClass, Reference reference) {

    super(id);

    validate(timeOfCreation, patient, medicine, changedQuantity, unitClass, reference);

    this.timeOfCreation = timeOfCreation;
    this.patient = patient;
    this.medicine = medicine;
    this.changedQuantity = changedQuantity;
    this.unitClass = unitClass;
    this.reference = reference;
  }

  public InventoryLog(Date timeOfCreation, Person patient, Medicine medicine,
                      int changedQuantity, UnitClass unitClass, Reference reference) {

    this(0, timeOfCreation, patient, medicine, changedQuantity, unitClass, reference);
  }

  private void validate(Date timeOfCreation, Person patient, Medicine medicine,
                        int changedQuantity, UnitClass unitClass, Reference reference) {

    validateParameter(timeOfCreation, "timeOfCreation");
    validateParameter(patient, "patient");
    validateParameter(medicine, "medicine");
    validateParameter(changedQuantity, "changedQuantity");
    validateParameter(unitClass, "unitClass");
    validateParameter(reference, "reference");

  }

  public Date getTimeOfCreation() {
    return timeOfCreation;
  }

  public Person getPatient() {
    return patient;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public int getChangedQuantity() {
    return changedQuantity;
  }

  public UnitClass getUnitClass() {
    return unitClass;
  }

  public Reference getReference() {
    return reference;
  }

  @Override
  public String toString() {
    return "InventoryLog{" +
        "id=" + getId() +
        ", timeOfCreation=" + timeOfCreation +
        ", patient:" + patient +
        ", " + medicine +
        ", changedQuantity=" + changedQuantity +
        ", unitClass=" + unitClass +
        ", reference=" + reference +
        '}';
  }
}


