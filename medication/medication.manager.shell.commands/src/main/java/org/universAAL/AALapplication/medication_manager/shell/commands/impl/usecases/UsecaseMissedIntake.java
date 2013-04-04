/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package org.universAAL.AALapplication.medication_manager.shell.commands.impl.usecases;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.providers.MissedIntakeContextProvider;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class UsecaseMissedIntake extends Usecase {

  private static final String PARAMETER_MESSAGE = "Expected one  additional parameter (except usecase id), which is: userId." +
      " Please check the person table for the valid ids";
  private static final String USECASE_ID = "UC04.1";
  private static final String USECASE_TITLE = "UC04.1: Medicine intake control (pill dispenser)";
  private static final String USECASE = USECASE_TITLE + " - The service " +
      "notifies a caregiver upon missed intake. Triggered by the proper event published by the dispenser" +
      "\n Parameters: " + PARAMETER_MESSAGE;

  public UsecaseMissedIntake() {
    super(USECASE_ID);
  }

  @Override
  public void execute(String... parameters) {

    if (parameters == null || parameters.length != 1) {
      throw new MedicationManagerShellException(PARAMETER_MESSAGE);
    }

    PersistentService persistentService = getPersistentService();
    PersonDao personDao = persistentService.getPersonDao();
    int id = Integer.parseInt(parameters[0]);
    Person person = personDao.getById(id);
    User user = new User(person.getPersonUri());
    Log.info("Executing the " + USECASE_TITLE + " . The user is : " + user, getClass());


    Time time = new Time(2012, 5, 12, 16, 52);

    MissedIntakeContextProvider provider = getMissedIntakeContextProvider();
    provider.missedIntakeTimeEvent(time, user);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
