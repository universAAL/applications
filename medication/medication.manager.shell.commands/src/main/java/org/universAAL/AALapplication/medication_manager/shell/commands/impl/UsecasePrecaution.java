package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.AALapplication.medication_manager.simulation.MedicationConsumer;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class UsecasePrecaution extends Usecase {

  @Override
  public void execute(String... parameters) {
    User saiedUser = UserIDs.getSaiedUser();
    Log.info("Executing the UC12: Incompliancy identification. The mocked user is : " +
        saiedUser, getClass());


    Precaution precaution = MedicationConsumer.requestDetails(saiedUser);

    if (precaution == null) {
      throw new MedicationManagerShellException("There is no precaution in our database for that user");
    }

    printInfo(precaution);

  }

  private void printInfo(Precaution precaution) {
    String sideeffect = precaution.getSideEffect();
    Log.info("Side effects:\n %s", getClass(), sideeffect);
    String incompliance = precaution.getIncompliance();
    Log.info("Incompliance:\n %s", getClass(), incompliance);
  }
}
