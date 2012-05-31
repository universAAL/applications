package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.apache.felix.service.command.Descriptor;

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

