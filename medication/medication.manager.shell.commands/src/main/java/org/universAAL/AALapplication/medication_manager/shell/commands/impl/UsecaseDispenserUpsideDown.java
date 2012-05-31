package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.AALapplication.medication_manager.ui.DispenserUpsideDownDialog;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

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

    User saiedUser = UserIDs.getSaiedUser();
    Log.info("Executing the " + USECASE_TITLE + " .The mocked user is : " +
        saiedUser, getClass());

    DispenserUpsideDownDialog dispenserUpsideDownDialog =
        new DispenserUpsideDownDialog(moduleContext);

    dispenserUpsideDownDialog.showDialog(saiedUser);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
