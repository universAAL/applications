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
import org.universAAL.AALapplication.medication_manager.ui.NewPrescriptionHandler;
import org.universAAL.AALapplication.medication_manager.ui.prescription.PrescriptionDTO;

import java.io.File;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class UsecaseNewPrescription extends Usecase {


  private static final String PRESCRIPTIONS = "prescriptions";
  private final File prescriptionDirectory;

  public static final String COMMAND_INFO =
      "This command expects two parameters, which are: usecase id and the file name";

  private static final String USECASE_ID = "UC08";
  private static final String USECASE_TITLE = "UC08: New prescription created  - ";
  private static final String USECASE = USECASE_TITLE +
      "Simulates the creation of the new prescription by the doctor(formal caregiver) and " +
      "triggers Medication Manager action to handle it";

  public UsecaseNewPrescription() {
    super(USECASE_ID);
    prescriptionDirectory = new File(medicationManagerConfigurationDirectory, PRESCRIPTIONS);
  }

  @Override
  public void execute(String... parameters) {

    if (!prescriptionDirectory.isDirectory()) {
      throw new MedicationManagerShellException("The required directory does not exists:" + PRESCRIPTIONS + " under the" +
          "***/runner/configurations/services/medication_manager");
    }

    if (parameters == null || parameters.length == 0) {
      throw new MedicationManagerShellException(COMMAND_INFO);
    }

    String fileName = parameters[0];
    Log.info("Executing the " + USECASE_TITLE + ". The file name is : " + fileName, getClass());
    Log.info("The Medication Manager will look for the file in following directory: " + prescriptionDirectory, getClass());

    File prescriptionFile = new File(prescriptionDirectory, fileName);

    if (!prescriptionFile.exists()) {
      throw new MedicationManagerShellException("The following file does not exists: " + prescriptionFile);
    }

    if (!prescriptionFile.isFile()) {
      throw new MedicationManagerShellException("The " + prescriptionFile + " is not a valid file");
    }

    PrescriptionParser prescriptionParser = new PrescriptionParser();
    PrescriptionDTO prescriptionDTO = prescriptionParser.parse(prescriptionFile);

    NewPrescriptionHandler newPrescriptionHandler = getNewPrescriptionHandler();
    newPrescriptionHandler.callHealthServiceWithNewPrescription(prescriptionDTO);

  }


  @Override
  public String getDescription() {
    return USECASE;
  }

}
