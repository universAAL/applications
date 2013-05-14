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
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.ui.DispenserDisplayInstructionsDialog;
import org.universAAL.ontology.profile.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class UsecaseDisplayDispenserInstruction extends Usecase {

  private static final String PARAMETER_MESSAGE = "Expected one additional parameter (except usecase id), which is: DeviceId." +
        " Please check the dispenser table for the valid ids";

  private static final String USECASE_ID = "UC06";
  private static final String USECASE_TITLE = "UC06: Fill the medication in the pill dispenser - ";
  private static final String USECASE = USECASE_TITLE +
      "The service notifies the AP how to fill the dispenser (display instructions via dialog)" +
      "\n Parameters: " + PARAMETER_MESSAGE;
  public static final String DISPENSER_INSTRUCTIONS = "dispenser_instructions";

  public UsecaseDisplayDispenserInstruction() {
    super(USECASE_ID);
  }

  @Override
  public void execute(String... parameters) {

    try {
      if (parameters == null || parameters.length != 1) {
        throw new MedicationManagerShellException(PARAMETER_MESSAGE);
      }

      int id = Integer.parseInt(parameters[0]);

      PersistentService persistentService = getPersistentService();
      DispenserDao dispenserDao = persistentService.getDispenserDao();
      Dispenser dispenser = dispenserDao.getById(id);
      String instructionsFile = dispenser.getInstructionsFileName();

      Log.info("Executing the " + USECASE_TITLE + ". The instructionsFile is : " +
          instructionsFile, getClass());

      String message = getDispenserInstructions(instructionsFile);

      Person patient = dispenser.getPatient();

      User user = new User(patient.getPersonUri());

      DispenserDisplayInstructionsDialog dispenserDisplayInstructionsDialog = new DispenserDisplayInstructionsDialog(mc);
      dispenserDisplayInstructionsDialog.showDialog(user, message);


    } catch (Exception e) {
      Log.error(e, "Error while processing the the shell command for usecase id:  %s", getClass(), USECASE_ID);
    }
  }

  private String getDispenserInstructions(String instructionsFile) throws IOException {
    String dir = medicationManagerConfigurationDirectory + File.separator + DISPENSER_INSTRUCTIONS;

    File file = new File(dir, instructionsFile);

    FileReader fileReader = new FileReader(file);
    BufferedReader br = new BufferedReader(fileReader);
    StringBuffer sb = new StringBuffer();
    String line = br.readLine();
    while (line != null) {
      sb.append(line);
      line = br.readLine();
    }

    return sb.toString();
  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
