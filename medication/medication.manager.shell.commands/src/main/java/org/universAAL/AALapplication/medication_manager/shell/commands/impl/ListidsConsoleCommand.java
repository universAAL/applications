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
      throw new MedicationManagerShellException(
          "The listids command doesn't expect any parameters");
    }

    String[] descriptions = Usecase.getUsecaseDescriptions();
    for (int i = 0; i < descriptions.length; i++) {
      String desc = descriptions[i];
      int usecaseId = i + 1;
      printImplementedUsecaseId(usecaseId, desc);
    }

  }

  private void printImplementedUsecaseId(int id, String usecase) {
    StringBuilder usecaseBuilder = new StringBuilder();
    usecaseBuilder.append("The usecase with the id=");
    usecaseBuilder.append(id);
    usecaseBuilder.append(" is equivalent to the the following usecase described in the Medication Manager documentation:");
    usecaseBuilder.append("\n\t\t");
    usecaseBuilder.append(usecase);
    usecaseBuilder.append('\n');

    Log.info("Printing info for the following usecase id: %s\n" + usecaseBuilder.toString(), getClass(), id);
  }
}
