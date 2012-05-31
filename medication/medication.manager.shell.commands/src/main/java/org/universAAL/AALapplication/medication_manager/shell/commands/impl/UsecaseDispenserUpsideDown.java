package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.AALapplication.medication_manager.simulation.DispenserUpsideDownContextProvider;
import org.universAAL.ontology.medMgr.MyDeviceUserMappingDatabase;

/**
 * @author George Fournadjiev
 */
public final class UsecaseDispenserUpsideDown extends Usecase {

  private static final String USECASE_TITLE = "UC03: Dispenser upside-down notification  - ";
  private static final String USECASE = USECASE_TITLE +
      "The service notifies the AP that the pill dispenser is upside-down and cannot be used";

  public UsecaseDispenserUpsideDown(int usecaseId) {
    super(usecaseId);
  }

  @Override
  public void execute(String... parameters) {

    if (parameters != null && parameters.length > 0) {
      throw new MedicationManagerShellException(NO_PARAMETERS_MESSAGE);
    }

    String deviceId = MyDeviceUserMappingDatabase.getDeviceIdForSaiedUser();

    Log.info("Executing the " + USECASE_TITLE + ". The deviceId is : " +
        deviceId, getClass());

    DispenserUpsideDownContextProvider.dispenserUpsideDownDeviceIdEvent(deviceId);
  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
