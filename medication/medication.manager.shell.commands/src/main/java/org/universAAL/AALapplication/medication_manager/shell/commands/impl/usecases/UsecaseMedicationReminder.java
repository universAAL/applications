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
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.simulation.MedicationReminderContextProvider;
import org.universAAL.ontology.medMgr.Time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class UsecaseMedicationReminder extends Usecase {

  private static final String ERROR_MESSAGE = "Expected two parameters, which are: " +
      "1. UserId \n" +
      "2. IntakeId \n" +
      "Please check the person and intake tables for the valid ids";
  private static final String USECASE_ID = "UC01.1";
  private static final String USECASE_TITLE = "UC01.1: Medication reminder for AP (pill dispenser) - ";
  private static final String USECASE = USECASE_TITLE +
      "The service handles reminder notifications, triggered by the pill dispenser and delivered via " +
      "Local Device Discovery and Integration (LDDI) service.\n" + " This usecase can trigger the following usecase, " +
      "if the user presses the info button: \n\t Usecase:\n\t\t" + "UC02: AP requests information about intake - " +
      "Upon request by the Assisted Person, the service provides information about medicine to be taken";

  public UsecaseMedicationReminder() {
    super(USECASE_ID);
  }

  @Override
  public void execute(String... parameters) {
    if (parameters == null || parameters.length != 2) {
      throw new MedicationManagerShellException(ERROR_MESSAGE);
    }

    PersistentService persistentService = getPersistentService();

    Log.info("Persistent service found", getClass());

    PersonDao personDao = persistentService.getPersonDao();
    int personId = getIdFromString(parameters[0]);
    Person person = personDao.getById(personId);

    DispenserDao dispenserDao = persistentService.getDispenserDao();
    Dispenser dispenser = dispenserDao.findByPerson(person);
    String deviceId = String.valueOf(dispenser.getId());

    Log.info("DeviceId=%s", getClass(), deviceId);

    IntakeDao intakeDao = persistentService.getIntakeDao();
    Intake intake = intakeDao.getById(getIdFromString(parameters[1]));

    int intakePersonId = intake.getPatient().getId();

    if (intakePersonId != personId) {
      throw new MedicationManagerShellException("The intake with id=" + intake.getId() +
          " is not associated with the patient with id=" + personId);
    }

    Time time = getTimeObject(intake.getTimePlan());

    Log.info("Executing the " + USECASE_TITLE + ". The deviceId is : " +
        deviceId + " for user with id=" + personId, getClass());


    MedicationReminderContextProvider.dueIntakeReminderDeviceIdEvent(deviceId, time);

  }

  private int getIdFromString(String parameter) {
    try {
      return Integer.parseInt(parameter);
    } catch (NumberFormatException e) {
      throw new MedicationManagerShellException("Parameter is not a valid number", e);
    }
  }

  private Time getTimeObject(Date date) {

    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(date);

    int year = gregorianCalendar.get(Calendar.YEAR);
    int month = gregorianCalendar.get(Calendar.MONTH);
    int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    int hour = gregorianCalendar.get(Calendar.HOUR);
    int minutes = gregorianCalendar.get(Calendar.MINUTE);

    return new Time(year, month, day, hour, minutes);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
