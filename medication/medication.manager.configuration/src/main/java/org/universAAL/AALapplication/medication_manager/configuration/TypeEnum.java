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

package org.universAAL.AALapplication.medication_manager.configuration;

/**
 * @author George Fournadjiev
 */
public enum TypeEnum {

  NUMBER("number"),
  BOOLEAN("boolean"),
  STRING("string");

  private final String value;

  private TypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static TypeEnum getEnumFromValue(String textValue) {
    textValue = textValue.toLowerCase();
    for (TypeEnum typeEnum : values()) {
      if (typeEnum.value.equals(textValue)) {
        return typeEnum;
      }
    }

    throw new MedicationManagerConfigurationException("No TypeEnum for the following value : " + textValue);
  }
}
