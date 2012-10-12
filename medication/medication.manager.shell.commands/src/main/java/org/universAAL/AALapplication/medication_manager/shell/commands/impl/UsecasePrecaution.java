package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.AALapplication.medication_manager.simulation.MedicationConsumer;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class UsecasePrecaution extends Usecase {


  private static final String USECASE_TITLE = "UC12: Incompliancy identification";
  private static final String USECASE = USECASE_TITLE + " - The service provides warnings " +
      "about side effects and possible incompliancy with some food and drinks, " +
      "so that the Nutrition Adviser Service compose a health menu";

  public UsecasePrecaution(int usecaseId) {
    super(usecaseId);
  }

  @Override
  public void execute(String... parameters) {

    if (parameters != null && parameters.length > 0) {
      throw new MedicationManagerShellException(NO_PARAMETERS_MESSAGE);
    }

    User saiedUser = UserIDs.getSaiedUser();
    Log.info("Executing the " + USECASE_TITLE + ". The mocked user is : " +
        saiedUser, getClass());


    Precaution[] precautions = MedicationConsumer.requestDetails(saiedUser);

    if (precautions == null || precautions.length != 2) {
      throw new MedicationManagerShellException("There is no precaution in our database for that user " +
          "or the returned Precaution array contains not 2 elements");
    }

    printInfo(precautions);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }

  private void printInfo(Precaution[] precautions) {
    String sideeffect = precautions[0].getSideEffect();
    Log.info("Side effects:\n %s", getClass(), sideeffect);
    String incompliance = precautions[1].getIncompliance();
    Log.info("Incompliance:\n %s", getClass(), incompliance);
  }
}
