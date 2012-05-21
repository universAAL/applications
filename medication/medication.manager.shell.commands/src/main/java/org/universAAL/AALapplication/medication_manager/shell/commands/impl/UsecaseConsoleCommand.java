package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.AALapplication.medication_manager.simulation.MedicationReminderContextProvider;
import org.universAAL.ontology.medMgr.UserIDs;

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
      Log.info(COMMAND_INFO, getClass());
    }

    MedicationReminderContextProvider.dueIntakeReminderDeviceIdEvent(UserIDs.getSaiedUser(), "device 1");


  }
}
