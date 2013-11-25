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
public final class Intake extends Entity {

  private final Treatment treatment;
  private final int quantity;
  private final UnitClass unitClass;
  private final Date timePlan;
  private final Date timeTaken;
  private final Dispenser dispenser;

  public Intake(int id, Treatment treatment, int quantity, UnitClass unitClass,
                Date timePlan, Date timeTaken, Dispenser dispenser) {

    super(id);

    validate(treatment, quantity, unitClass, timePlan);

    this.treatment = treatment;
    this.quantity = quantity;
    this.unitClass = unitClass;
    this.timePlan = timePlan;
    this.timeTaken = timeTaken;
    this.dispenser = dispenser;
  }

  public Intake(Treatment treatment, int quantity, UnitClass unitClass,
                Date timePlan, Date timeTaken, Dispenser dispenser) {

    this(0, treatment, quantity, unitClass, timePlan, timeTaken, dispenser);
  }


  private void validate(Treatment treatment, int quantity,
                        UnitClass unitClass, Date timePlan) {

    validateParameter(treatment, "treatment");
    validateParameter(quantity, "quantity");
    validateParameter(unitClass, "unitClass");
    validateParameter(timePlan, "timePlan");

  }

  public Treatment getTreatment() {
    return treatment;
  }

  public int getQuantity() {
    return quantity;
  }

  public UnitClass getUnitClass() {
    return unitClass;
  }

  public Date getTimePlan() {
    return timePlan;
  }

  public Date getTimeTaken() {
    return timeTaken;
  }

  public Dispenser getDispenser() {
    return dispenser;
  }

  @Override
  public String toString() {
    return "Intake{" +
        "treatment=" + treatment +
        ", quantity=" + quantity +
        ", unitClass=" + unitClass +
        ", timePlan=" + timePlan +
        ", timeTaken=" + timeTaken +
        ", dispenser=" + dispenser +
        '}';
  }
}


