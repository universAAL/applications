package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.universAAL.AALapplication.medication_manager.simulation.MissedIntakeContextProvider;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class UsecaseMissedIntake extends Usecase {

  @Override
  public void execute(String... parameters) {
    User saiedUser = UserIDs.getSaiedUser();
    Log.info("Executing the UC04.1: Medicine intake control (pill dispenser) . The mocked user is : " +
        saiedUser, getClass());


    Time time = new Time(2012, 5, 12, 16, 52);
    MissedIntakeContextProvider.missedIntakeTimeEvent(time, saiedUser);

  }

}
