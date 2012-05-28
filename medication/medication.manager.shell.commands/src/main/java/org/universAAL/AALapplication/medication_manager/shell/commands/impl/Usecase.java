package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.middleware.container.ModuleContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public abstract class Usecase {

  private final int usecaseId;

  public static ModuleContext moduleContext;
  private static ListidsConsoleCommand listidsConsoleCommand;
  private static final Map<Integer, Usecase> USECASE_MAP = new HashMap<Integer, Usecase>();

  static {
    int id = 1;
    USECASE_MAP.put(id, new UsecasePrecaution(id));
    id++;
    USECASE_MAP.put(id, new UsecaseMissedIntake(id));
    id++;
    USECASE_MAP.put(id, new UsecaseMedicationReminder(id));
    id++;
    USECASE_MAP.put(id, new UsecaseRequestMedicationInfo(id));
    id++;
    USECASE_MAP.put(id, new UsecaseDispenserUpsideDown(id));
  }

  protected Usecase(int usecaseId) {
    this.usecaseId = usecaseId;
  }

  public int getUsecaseId() {
    return usecaseId;
  }

  public static void setModuleContext(ModuleContext moduleContext) {
    Usecase.moduleContext = moduleContext;
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

  public static Usecase[] getUsecaseDescriptions() {
    Usecase[] usecases = new Usecase[USECASE_MAP.size()];
    Iterator<Usecase> iter = USECASE_MAP.values().iterator();
    int i = 0;
    while (iter.hasNext()) {
      Usecase next = iter.next();
      usecases[i] = next;
      i++;
    }

    return usecases;
  }


}
