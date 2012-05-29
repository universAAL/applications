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
    User saiedUser = UserIDs.getSaiedUser();
    Log.info("Executing the " + USECASE_TITLE + ". The mocked user is : " +
        saiedUser, getClass());


    Precaution precaution = MedicationConsumer.requestDetails(saiedUser);

    if (precaution == null) {
      throw new MedicationManagerShellException("There is no precaution in our database for that user");
    }

    printInfo(precaution);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }

  private void printInfo(Precaution precaution) {
    String sideeffect = precaution.getSideEffect();
    Log.info("Side effects:\n %s", getClass(), sideeffect);
    String incompliance = precaution.getIncompliance();
    Log.info("Incompliance:\n %s", getClass(), incompliance);
  }
}
