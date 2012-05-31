package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.ontology.medMgr.MyDeviceUserMappingDatabase;
import org.universAAL.AALapplication.medication_manager.simulation.MedicationReminderContextProvider;
import org.universAAL.ontology.medMgr.MyIntakeInfosDatabase;
import org.universAAL.ontology.medMgr.Time;

/**
 * @author George Fournadjiev
 */
public final class UsecaseMedicationReminder extends Usecase {

  private static final String MORNING = "morning";
  private static final String LUNCH = "lunch";
  private static final String EVENING = "evening";
  private static final String ERROR_MESSAGE = "Expected only one parameter, which must have one of the following values: " +
      MORNING + ", " + LUNCH + ", " + EVENING;
  private static final String USECASE_TITLE = "UC01.1: Medication reminder for AP (pill dispenser) - ";
  private static final String USECASE = USECASE_TITLE +
      "The service handles reminder notifications, triggered by the pill dispenser and delivered via " +
      "Local Device Discovery and Integration (LDDI) service.\n" + " This usecase can trigger the following usecase, " +
      "if the user presses the info button: \n\t Usecase:\n\t\t" + "UC02: AP requests information about intake - " +
      "Upon request by the Assisted Person, the service provides information about medicine to be taken";

  public UsecaseMedicationReminder(int usecaseId) {
    super(usecaseId);
  }

  @Override
  public void execute(String... parameters) {
    if (parameters == null || parameters.length != 1) {
      throw new MedicationManagerShellException(ERROR_MESSAGE);
    }

    Time time = checkParameterAndGetTimeObject(parameters[0]);

    String deviceId = MyDeviceUserMappingDatabase.getDeviceIdForSaiedUser();

    Log.info("Executing the " + USECASE_TITLE + ". The deviceId is : " +
        deviceId, getClass());


    MedicationReminderContextProvider.dueIntakeReminderDeviceIdEvent(deviceId, time);

  }

  private Time checkParameterAndGetTimeObject(String parameter) {
    if (!MORNING.equalsIgnoreCase(parameter) &&
        !LUNCH.equalsIgnoreCase(parameter) &&
        !EVENING.equalsIgnoreCase(parameter)) {

       throw new MedicationManagerShellException(ERROR_MESSAGE);

    }

    Time time;

    if (MORNING.equalsIgnoreCase(parameter)) {
      time = MyIntakeInfosDatabase.MORNING_INTAKE;
    } else if (LUNCH.equalsIgnoreCase(parameter)) {
      time = MyIntakeInfosDatabase.LUNCH_INTAKE;
    } else { //EVENING.equalsIgnoreCase(parameter)
      time = MyIntakeInfosDatabase.EVENING_INTAKE;
    }

    return time;

  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
