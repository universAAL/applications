package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.AALapplication.medication_manager.ui.RequestMedicationInfoDialog;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class UsecaseRequestMedicationInfo extends Usecase {

  private static final String USECASE_TITLE = "UC02: AP requests information about intake – ";
  private static final String USECASE = USECASE_TITLE +
      "Upon request by the Assisted Person, the service provides information about medicine to be taken";

  public UsecaseRequestMedicationInfo(int usecaseId) {
    super(usecaseId);
  }

  @Override
  public void execute(String... parameters) {
    User saiedUser = UserIDs.getSaiedUser();
    Log.info("Executing the " + USECASE_TITLE + ". The mocked user is : " +
        saiedUser, getClass());


    RequestMedicationInfoDialog requestMedicationInfoDialog =
        new RequestMedicationInfoDialog(moduleContext);

    requestMedicationInfoDialog.showDialog(saiedUser);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
