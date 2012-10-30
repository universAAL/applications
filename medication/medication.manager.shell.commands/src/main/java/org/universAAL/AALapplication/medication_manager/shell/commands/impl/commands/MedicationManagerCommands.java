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

import org.apache.felix.service.command.Descriptor;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;

/**
 * @author George Fournadjiev
 */
public class MedicationManagerCommands {

  @Descriptor("Trigger specific Medication Manager Use Case")
  public void usecase(@Descriptor("parameters") String... parameters) {

    UsecaseConsoleCommand consoleCommand = (UsecaseConsoleCommand) MedicationConsoleCommands.getUsecaseConsoleCommand();

    if (parameters == null || parameters.length == 0) {
      throw new MedicationManagerShellException(consoleCommand.getParametersInfo());
    }

    Log.info("Executing medication:usecase command with the following useCaseId: %s", getClass(), parameters[0]);

    consoleCommand.execute(parameters);
  }

  @Descriptor("List all implemented Medication Manager Use Cases")
  public void listids() {

    Log.info("Executing medication:listids command", getClass());

    ConsoleCommand consoleCommand = MedicationConsoleCommands.getListidsConsoleCommand();
    consoleCommand.execute();
  }

  @Descriptor("Displays Medication Manager commands")
  public void help() {

    Log.info("Executing medication:help command", getClass());

    ConsoleCommand consoleCommand = MedicationConsoleCommands.getHelpConsoleCommand();
    consoleCommand.execute();

  }


}

