package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

/**
 * @author George Fournadjiev
 */
public final class ListidsConsoleCommand extends ConsoleCommand {

  public ListidsConsoleCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return NO_PARAMETERS;
  }


  @Override
  public void execute(String... parameters) {
    if (parameters != null && parameters.length > 0) {
      Log.info("The listids command doesn't expect any parameters", getClass());
    }
  }
}
