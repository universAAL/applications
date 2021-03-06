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


package org.universAAL.AALapplication.medication_manager.persistence.layer.dto;

import org.universAAL.ontology.medMgr.MedicationException;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class IntakeDTO {

  private final TimeDTO time;
  private final Unit unit;
  private final int dose;

  public IntakeDTO(TimeDTO time, Unit unit, int dose) {

    validateParameter(time, "time");
    validateParameter(unit, "unit");
    validateParameter(dose, "dose");

    this.time = time;
    this.unit = unit;
    this.dose = dose;
  }

  public TimeDTO getTime() {
    return time;
  }

  public Unit getUnit() {
    return unit;
  }

  public int getDose() {
    return dose;
  }

  @Override
  public String toString() {
    return "Intake{" +
        "time=" + time +
        ", unit=" + unit +
        ", dose=" + dose +
        '}';
  }

  public enum Unit {
    PILL("PILL"), DROPS("DROPS");

    private String value;

    Unit(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static Unit getEnumValueFor(String unitText) {
      for (Unit unit : values()) {
        if (unit.value.equalsIgnoreCase(unitText)) {
          return unit;
        }
      }

      //hack to be fixed
      if ("pills".equalsIgnoreCase(unitText)) {
        return PILL;
      }

      throw new MedicationException("Unknown Unit enum value: " + unitText);
    }

    public static String getStringValueFromEnum(Unit un) {
      return un.value;
    }

  }


}
