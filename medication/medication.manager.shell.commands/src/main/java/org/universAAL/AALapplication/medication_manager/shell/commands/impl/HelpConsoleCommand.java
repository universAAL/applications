package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

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

  }


}
