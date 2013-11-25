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

import static org.universAAL.AALapplication.medication_manager.configuration.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class PropertyInfo {

  private final int id;
  private final String name;
  private final String value;
  private final FormatEnum format;
  private final TypeEnum type;
  private final String description;

  public PropertyInfo(int id, String name, String value, FormatEnum format, TypeEnum type, String description) {

    this.id = id;

    validateParameter(name, "name");
    validateParameter(value, "value");
    validateParameter(format, "format");
    validateParameter(type, "type");
    validateParameter(description, "description");

    this.name = name;
    this.value = value;
    this.format = format;
    this.type = type;
    this.description = description;
  }

  public PropertyInfo(String name, String value, FormatEnum format, TypeEnum type, String description) {
    this(0, name, value, format, type, description);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public FormatEnum getFormat() {
    return format;
  }

  public TypeEnum getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "PropertyInfo{" +
        "id='" + id + '\'' +
        "name='" + name + '\'' +
        ", value=" + value +
        ", format='" + format + '\'' +
        ", type='" + type + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
