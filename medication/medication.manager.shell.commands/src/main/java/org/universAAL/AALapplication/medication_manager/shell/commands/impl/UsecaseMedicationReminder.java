package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.ontology.medMgr.MyDeviceUserMappingDatabase;
import org.universAAL.AALapplication.medication_manager.simulation.MedicationReminderContextProvider;

/**
 * @author George Fournadjiev
 */
public final class UsecaseMedicationReminder extends Usecase {

  private static final String USECASE_TITLE = "UC01.1: Medication reminder for AP (pill dispenser) - ";
  private static final String USECASE = USECASE_TITLE +
      "The service handles reminder notifications, triggered by the pill dispenser and delivered via " +
      "Local Device Discovery and Integration (LDDI) service.";

  public UsecaseMedicationReminder(int usecaseId) {
    super(usecaseId);
  }

  @Override
  public void execute(String... parameters) {

    String deviceId = MyDeviceUserMappingDatabase.getDeviceIdForSaiedUser();

    Log.info("Executing the " + USECASE_TITLE + ". The deviceId is : " +
        deviceId, getClass());


    MedicationReminderContextProvider.dueIntakeReminderDeviceIdEvent(deviceId);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
