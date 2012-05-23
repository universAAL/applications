package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

/**
 * @author George Fournadjiev
 */
public final class UsecaseConsoleCommand extends ConsoleCommand {

  private static final String COMMAND_INFO =
      "The usecase command expects exactly one parameter usecase id";

  public UsecaseConsoleCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return COMMAND_INFO;
  }


  @Override
  public void execute(String... parameters) {
    if (parameters == null || parameters.length != 1) {
      throw new MedicationManagerShellException(COMMAND_INFO);
    }

    int usecaseId = getUsecaseId(parameters[0]);
    Usecase usecase = Usecase.getUsecase(usecaseId);
    usecase.execute();
  }

  private int getUsecaseId(String parameter) {
    try {

      Integer usecaseId = Integer.valueOf(parameter);

      return usecaseId;

    } catch (NumberFormatException e) {

      throw new MedicationManagerShellException("The command parameter must be number", e);

    }
  }
}
