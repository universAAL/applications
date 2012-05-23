package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public abstract class Usecase {

  private static final Map<Integer, Usecase> USECASE_MAP = new HashMap<Integer, Usecase>();

  static {
    USECASE_MAP.put(1, new UsecasePrecaution());
    USECASE_MAP.put(2, new UsecaseMissedIntake());
  }

  public abstract void execute(String... parameters);

  public static Usecase getUsecase(Integer usecaseId) {
    Usecase usecase = USECASE_MAP.get(usecaseId);

    if (usecase == null) {
      throw new MedicationManagerShellException(
          "Not found usecase implementation with the following id <" + usecaseId + ">." +
              "\n\t\t You can execute the " + MedicationConsoleCommands.COMMAND_PREFIX + ':' + MedicationConsoleCommands.LISTIDS_COMMMAND +
              " command to see the implemented usecases with the corresponding ids");
    }

    return usecase;
  }


}
