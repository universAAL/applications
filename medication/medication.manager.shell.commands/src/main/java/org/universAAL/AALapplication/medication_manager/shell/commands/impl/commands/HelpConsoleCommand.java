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

import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;

/**
 * @author George Fournadjiev
 */
public final class HelpConsoleCommand extends ConsoleCommand {

  public HelpConsoleCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return NO_PARAMETERS;
  }

  @Override
  public void execute(String... parameters) {
    try {
      if (parameters != null && parameters.length > 0) {
        throw new MedicationManagerShellException(
            "The help command doesn't expect any parameters");
      }

      StringBuilder infoBuilder = new StringBuilder();
      infoBuilder.append('\n');
      infoBuilder.append('\n');
      infoBuilder.append("************************* Printing available Medication console commands:************************\n");

      ConsoleCommand[] consoleCommands = MedicationConsoleCommands.getConsoleCommands();

      for (int i = 0; i < consoleCommands.length; i++) {
        ConsoleCommand command = consoleCommands[i];
        infoBuilder.append(command.getCommandDetails(i + 1));
      }


      infoBuilder.append('\n');
      infoBuilder.append('\n');
      infoBuilder.append("*************************************** END *****************************************************\n");
      infoBuilder.append('\n');

      Log.info(infoBuilder.toString(), getClass());
    } catch (Exception e) {
      Log.error(e, "Error while processing the the shell command", getClass());
    }

  }


}
