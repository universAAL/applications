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
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.simulation.export.MedicationConsumer;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.profile.User;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class UsecasePrecaution extends Usecase {

  private static final String PARAMETER_MESSAGE = "Expected one  additional parameter (except usecase id), which is: UserId." +
      " Please check the person table for the valid ids";
  private static final String USECASE_ID = "UC12";
  private static final String USECASE_TITLE = "UC12: Incompliancy identification";
  private static final String USECASE = USECASE_TITLE + " - The service provides warnings " +
      "about side effects and possible incompliancy with some food and drinks, " +
      "so that the Nutrition Adviser Service compose a health menu" +
      "\n Parameters: " + PARAMETER_MESSAGE;

  public UsecasePrecaution() {
    super(USECASE_ID);
  }

  @Override
  public void execute(String... parameters) {

    try {
      if (parameters == null || parameters.length != 1) {
        throw new MedicationManagerShellException(PARAMETER_MESSAGE);
      }

      PersistentService persistentService = getPersistentService();
      PersonDao personDao = persistentService.getPersonDao();
      int personId = Integer.parseInt(parameters[0]);

      Person person = personDao.getById(personId);

      Log.info("Executing the " + USECASE_TITLE + ". The user is : " + person, getClass());

      User user = new User(person.getPersonUri());
      Precaution[] precautions = MedicationConsumer.requestDetails(user);

      if (precautions == null || precautions.length != 2) {
        throw new MedicationManagerShellException("There is no precaution in our database for that user " +
            "or the returned Precaution array contains not 2 elements");
      }

      printInfo(precautions);
    } catch (Exception e) {
      Log.error(e, "Error while processing the the shell command for usecase id:  %s", getClass(), USECASE_ID);
    }

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
