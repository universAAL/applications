package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public abstract class Usecase {

  private static ListidsConsoleCommand listidsConsoleCommand;
  private static final Map<Integer, Usecase> USECASE_MAP = new HashMap<Integer, Usecase>();

  static {
    USECASE_MAP.put(1, new UsecasePrecaution());
    USECASE_MAP.put(2, new UsecaseMissedIntake());
  }

  public static void setListidsConsoleCommand(ListidsConsoleCommand listidsConsoleCommand) {
    Usecase.listidsConsoleCommand = listidsConsoleCommand;
  }

  public abstract void execute(String... parameters);

  public abstract String getDescription();

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

  public static String[] getUsecaseDescriptions() {
    String[] descriptions = new String[USECASE_MAP.size()];
    Iterator<Usecase> iter = USECASE_MAP.values().iterator();
    int i = 0;
    while (iter.hasNext()) {
      Usecase next = iter.next();
      descriptions[i] = next.getDescription();
      i++;
    }

    return descriptions;
  }


}
