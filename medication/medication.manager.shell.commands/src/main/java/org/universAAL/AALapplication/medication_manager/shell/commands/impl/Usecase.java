package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.middleware.container.ModuleContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public abstract class Usecase {

  private final int usecaseId;

  public static ModuleContext moduleContext;
  public static final String NO_PARAMETERS_MESSAGE = "This usecase doesn't expect parameters";
  private static final Map<Integer, Usecase> USECASE_MAP = new HashMap<Integer, Usecase>();

  static {
    int id = 1;
    UsecasePrecaution usecasePrecaution = new UsecasePrecaution(id);
    USECASE_MAP.put(usecasePrecaution.getUsecaseId(), usecasePrecaution);
    id++;
    UsecaseMissedIntake usecaseMissedIntake = new UsecaseMissedIntake(id);
    USECASE_MAP.put(usecaseMissedIntake.getUsecaseId(), usecaseMissedIntake);
    id++;
    UsecaseMedicationReminder usecaseMedicationReminder = new UsecaseMedicationReminder(id);
    USECASE_MAP.put(usecaseMedicationReminder.getUsecaseId(), usecaseMedicationReminder);
    id++;
    UsecaseDispenserUpsideDown usecaseDispenserUpsideDown = new UsecaseDispenserUpsideDown(id);
    USECASE_MAP.put(usecaseDispenserUpsideDown.getUsecaseId(), usecaseDispenserUpsideDown);
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

  public abstract void execute(String... parameters);

  public abstract String getDescription();

  public static Usecase getUsecase(Integer usecaseId) {
    Usecase usecase = USECASE_MAP.get(usecaseId);

    if (usecase == null) {
      throw new MedicationManagerShellException(
          "Not found usecase implementation with the following id <" + usecaseId + ">." +
              "\n\t\t You can execute the " + MedicationConsoleCommands.COMMAND_PREFIX + ':' +
              MedicationConsoleCommands.LISTIDS_COMMMAND +
              " command to see the implemented usecases with the corresponding ids");
    }

    return usecase;
  }

  public static Collection<Usecase> getUsecaseMap() {
    return USECASE_MAP.values();
  }
}
