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
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PrescriptionDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.simulation.export.NewPrescriptionHandler;

import java.io.File;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log.*;

/**
 * @author George Fournadjiev
 */
public final class UsecaseNewPrescription extends Usecase {


  private static final String PRESCRIPTIONS = "prescriptions";
  private final File prescriptionDirectory;

  public static final String PARAMETER_MESSAGE =
      "This command expects 3  additional parameters (except usecase id), which are: " +
          "1. usecase id and" +
          "2. the file name." +
          " Please check the file name in the prescriptions configuration directory";

  private static final String USECASE_ID = "UC08";
  private static final String USECASE_TITLE = "UC08: New prescription created  - ";
  private static final String USECASE = USECASE_TITLE +
      "Simulates the creation of the new prescription by the doctor(formal caregiver) and " +
      "triggers Medication Manager action to handle it" + "\n Parameters: " + PARAMETER_MESSAGE;

  public UsecaseNewPrescription() {
    super(USECASE_ID);
    prescriptionDirectory = new File(medicationManagerConfigurationDirectory, PRESCRIPTIONS);
  }

  @Override
  public void execute(String... parameters) {

    if (!prescriptionDirectory.isDirectory()) {
      throw new MedicationManagerShellException("The required directory does not exists:" + PRESCRIPTIONS + " under the" +
          "***/runner/configurations/medication_manager");
    }

    if (parameters == null || parameters.length != 1) {
      throw new MedicationManagerShellException(PARAMETER_MESSAGE);
    }

    String fileName = parameters[0];
    info("Executing the %s . The file name is : %s", getClass(), USECASE_TITLE, fileName);
    info("The Medication Manager will look for the file in following directory: " + prescriptionDirectory, getClass());

    PersistentService persistentService = getPersistentService();

    File prescriptionFile = new File(prescriptionDirectory, fileName);

    if (!prescriptionFile.exists()) {
      throw new MedicationManagerShellException("The following file does not exists: " + prescriptionFile);
    }

    if (!prescriptionFile.isFile()) {
      throw new MedicationManagerShellException("The " + prescriptionFile + " is not a valid file");
    }

    PrescriptionParser prescriptionParser = new PrescriptionParser();
    PrescriptionDTO prescriptionDTO = prescriptionParser.parse(prescriptionFile, persistentService);

    info("The Medication Manager will try to save prescriptionDTO in the database: ", getClass());

    PrescriptionDao prescriptionDao = persistentService.getPrescriptionDao();

    prescriptionDao.save(prescriptionDTO);

    info("The Medication Manager successfully saved prescriptionDTO in the database: ", getClass());

    info("The Medication Manager will try to send the prescription the Health Service ", getClass());

    NewPrescriptionHandler newPrescriptionHandler = getNewPrescriptionHandler();
    newPrescriptionHandler.callHealthServiceWithNewPrescription(persistentService, prescriptionDTO);

    info("The Medication Manager successfully sent the prescription the Health Service ", getClass());

  }


  @Override
  public String getDescription() {
    return USECASE;
  }

}
