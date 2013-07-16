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
import org.universAAL.AALapplication.medication_manager.persistence.layer.Week;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.ui.IntakeLogDialog;
import org.universAAL.ontology.profile.User;

import java.util.Calendar;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class UsecaseIntakeLogReview extends Usecase {

  private static final String PARAMETER_MESSAGE = "Expected one additional parameters (except usecase id), which is: " +
      "1. UserId and" +
      " Please check the person table for the valid ids";
  private static final String USECASE_ID = "UC14";
  private static final String USECASE_TITLE = "UC14: Medication intake log/plan review for AP - ";
  private static final String USECASE = USECASE_TITLE +
      "The service handles intake log/plan review with the two buttons to move to next/previous week" +
      "\n Parameters: " + PARAMETER_MESSAGE;

  public UsecaseIntakeLogReview() {
    super(USECASE_ID);
  }

  @Override
  public void execute(String... parameters) {
    try {
      if (parameters == null || parameters.length != 1) {
        throw new MedicationManagerShellException(PARAMETER_MESSAGE);
      }

      PersistentService persistentService = getPersistentService();

      Log.info("Persistent service found", getClass());

      PersonDao personDao = persistentService.getPersonDao();
      int personId = getIdFromString(parameters[0]);
      Person person = personDao.getById(personId);

      Log.info("Person = %s", getClass(), person);

      Calendar currentWeekStartDate = Week.getMondayOfTheCurrentWeek();
      Week currentWeek = Week.createWeek(currentWeekStartDate);

      Log.info("Executing the " + USECASE_TITLE + ". The current week is : " +
               currentWeek + " for user with id=" + personId, getClass());

      IntakeLogDialog dialog = new IntakeLogDialog(mc, persistentService, currentWeek, person);
      User user = new User(person.getPersonUri());
      dialog.showDialog(user);

    } catch (Exception e) {
      Log.error(e, "Error while processing the the shell command for usecase id: %s", getClass(), USECASE_ID);
    }

  }

  private int getIdFromString(String parameter) {
    try {
      return Integer.parseInt(parameter);
    } catch (NumberFormatException e) {
      throw new MedicationManagerShellException("Parameter is not a valid number", e);
    }
  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
