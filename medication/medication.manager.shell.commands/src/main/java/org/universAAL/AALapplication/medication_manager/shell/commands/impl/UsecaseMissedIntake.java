package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.AALapplication.medication_manager.simulation.MissedIntakeContextProvider;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class UsecaseMissedIntake extends Usecase {

  private static final String USECASE_TITLE = "UC12: Incompliancy identification";
  private static final String USECASE = USECASE_TITLE + " - The service provides warnings " +
      "about side effects and possible incompliancy with some food and drinks, " +
      "so that the Nutrition Adviser Service compose a health menu";

  public UsecaseMissedIntake(int usecaseId) {
    super(usecaseId);
  }

  @Override
  public void execute(String... parameters) {
    User saiedUser = UserIDs.getSaiedUser();
    Log.info("Executing the " + USECASE_TITLE + " . The mocked user is : " +
        saiedUser, getClass());


    Time time = new Time(2012, 5, 12, 16, 52);
    MissedIntakeContextProvider.missedIntakeTimeEvent(time, saiedUser);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
