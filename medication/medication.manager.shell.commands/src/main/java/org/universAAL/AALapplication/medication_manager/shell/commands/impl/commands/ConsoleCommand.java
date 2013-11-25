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


package org.universAAL.AALapplication.medication_manager.shell.commands.impl.commands;

/**
 * @author George Fournadjiev
 */
public abstract class ConsoleCommand {

  private final String name;
  private final String description;

  public static final String NO_PARAMETERS = "no";

  public ConsoleCommand(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public abstract String getParametersInfo();

  public abstract void execute(String... parameters);

  public String getCommandDetails(int number) {
    StringBuilder infoBuilder = new StringBuilder();
    infoBuilder.append('\n');
    infoBuilder.append((number));
    infoBuilder.append(". ");
    infoBuilder.append(getName());
    infoBuilder.append(" | Description: ");
    infoBuilder.append(getDescription());
    infoBuilder.append(" | Parameters: ");
    infoBuilder.append(getParametersInfo());

    return infoBuilder.toString();
  }

}
