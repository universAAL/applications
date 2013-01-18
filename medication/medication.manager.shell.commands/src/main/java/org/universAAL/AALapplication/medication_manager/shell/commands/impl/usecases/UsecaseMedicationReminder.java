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

import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.ontology.medMgr.MyDeviceUserMappingDatabase;
import org.universAAL.AALapplication.medication_manager.simulation.MedicationReminderContextProvider;
import org.universAAL.ontology.medMgr.MyIntakeInfosDatabase;
import org.universAAL.ontology.medMgr.Time;

/**
 * @author George Fournadjiev
 */
public final class UsecaseMedicationReminder extends Usecase {

  private static final String MORNING = "morning";
  private static final String LUNCH = "lunch";
  private static final String EVENING = "evening";
  private static final String ERROR_MESSAGE = "Expected only one parameter, which must have one of the following values: " +
      MORNING + ", " + LUNCH + ", " + EVENING;
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
    if (parameters == null || parameters.length != 1) {
      throw new MedicationManagerShellException(ERROR_MESSAGE);
    }

    Time time = checkParameterAndGetTimeObject(parameters[0]);

    String deviceId = MyDeviceUserMappingDatabase.getDeviceIdForSaiedUser();

    Log.info("Executing the " + USECASE_TITLE + ". The deviceId is : " +
        deviceId, getClass());


    MedicationReminderContextProvider.dueIntakeReminderDeviceIdEvent(deviceId, time);

  }

  private Time checkParameterAndGetTimeObject(String parameter) {
    if (!MORNING.equalsIgnoreCase(parameter) &&
        !LUNCH.equalsIgnoreCase(parameter) &&
        !EVENING.equalsIgnoreCase(parameter)) {

       throw new MedicationManagerShellException(ERROR_MESSAGE);

    }

    Time time;

    if (MORNING.equalsIgnoreCase(parameter)) {
      time = MyIntakeInfosDatabase.MORNING_INTAKE;
    } else if (LUNCH.equalsIgnoreCase(parameter)) {
      time = MyIntakeInfosDatabase.LUNCH_INTAKE;
    } else { //EVENING.equalsIgnoreCase(parameter)
      time = MyIntakeInfosDatabase.EVENING_INTAKE;
    }

    return time;

  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
