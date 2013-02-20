package org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands;

import org.universAAL.AALapplication.contact_manager.shell.commands.impl.ContactManagerShellException;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.Log;

import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactConsoleCommands.*;

/**
 * @author George Fournadjiev
 */
public final class ContactManagerHelpCommand extends ConsoleCommand {

  private static final String COMMAND = COMMAND_PREFIX + ':' + HELP_COMMAND + " command";

  public ContactManagerHelpCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return NO_PARAMETERS;
  }

  @Override
  public void execute(String... parameters) {

    Log.info("Executing " + COMMAND, getClass());

    if (parameters != null && parameters.length > 0) {
      throw new ContactManagerShellException(
          "The help command doesn't expect any parameters");
    }

    StringBuilder infoBuilder = new StringBuilder();
    infoBuilder.append('\n');
    infoBuilder.append('\n');
    infoBuilder.append("************************* Printing available Contact Manager console commands:************************\n");

    ConsoleCommand[] consoleCommands = ContactConsoleCommands.getConsoleCommands();

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

  @Override
  public String getCommandText() {
    return COMMAND;
  }


}
