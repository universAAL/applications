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


package org.universAAL.AALapplication.medication_manager.shell.commands.impl.commands;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.SqlUtility;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.InventoryLogDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PrescribedMedicineDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PrescriptionDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.TreatmentDao;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class SqlConsoleCommand extends ConsoleCommand {

  private final File sqlFilesDirectory;

  private static final String PRINT = "print";
  private static final String FILE = "file";
  private static final String EXECUTE = "execute";
  private static final String ALL_TABLES = "allTables";
  private static final String SQL = "sql";

  public static final String COMMAND_INFO =
      "The sql command expects the following two parameters pairs, which are:" +
          "\n\t \n\t - " + PRINT + " " + ALL_TABLES + " (will print all tables records sorted by table)" +
          "\n\t \n\t - " + PRINT + " tableName" + " (will print all the records for a particular table)" +
          "\n\t \n\t - " + FILE + " fileName" + " (will execute sql statements from file)" +
          "\n\t \n\t - " + EXECUTE + " sqlStatement (will execute any sql statement)";

  public SqlConsoleCommand(String name, String description) {
    super(name, description);

    sqlFilesDirectory = new File(medicationManagerConfigurationDirectory, "sql");
  }

  @Override
  public String getParametersInfo() {
    return COMMAND_INFO;
  }


  @Override
  public void execute(String... parameters) {
    if (parameters == null || parameters.length != 2) {
      throw new MedicationManagerShellException(COMMAND_INFO);
    }

    String firstParam = parameters[0].trim();

    checkFirstParam(firstParam);

    String secondParam = parameters[1].trim();

    callSqlUtility(firstParam, secondParam);

    testPersistentService();
  }

  private void testPersistentService() {
    PersistentService persistentService = getPersistentService();

    System.out.println("////////////////////////////BEGIN/////////////////////////////////////");

    DispenserDao dispenserDao = persistentService.getDispenserDao();
    dispenserDao.getById(1);
    IntakeDao intakeDao = persistentService.getIntakeDao();
    intakeDao.getById(1);
    InventoryLogDao inventoryLogDao = persistentService.getInventoryLogDao();
    inventoryLogDao.getById(1);
    MedicineDao medicineDao = persistentService.getMedicineDao();
    medicineDao.getById(1);
    MedicineInventoryDao medicineInventoryDao = persistentService.getMedicineInventoryDao();
    medicineInventoryDao.getById(1);
    PersonDao personDao = persistentService.getPersonDao();
    personDao.getById(1);
    PrescribedMedicineDao prescribedMedicineDao = persistentService.getPrescribedMedicineDao();
    prescribedMedicineDao.getById(1);
    PrescriptionDao prescriptionDao = persistentService.getPrescriptionDao();
    prescriptionDao.getById(1);
    TreatmentDao treatmentDao = persistentService.getTreatmentDao();
    treatmentDao.getById(1);

    System.out.println("////////////////////////////END/////////////////////////////////////");
  }

  private void checkFirstParam(String firstParam) {
    Log.info("Checking the first param %s", getClass(), firstParam);


    boolean ok = PRINT.equalsIgnoreCase(firstParam) ||
        FILE.equalsIgnoreCase(firstParam) || EXECUTE.equalsIgnoreCase(firstParam);

    if (!ok) {
      throw new MedicationManagerShellException("The first parameter must be one of the following : " +
          PRINT + ", " + EXECUTE + ", " + FILE);
    }

  }

  private void callSqlUtility(String firstParam, String secondParam) {

    PersistentService persistentService = getPersistentService();

    SqlUtility sqlUtility = persistentService.getSqlUtility();

    Log.info("Inspecting the second param %s", getClass(), secondParam);

    if (PRINT.equalsIgnoreCase(firstParam) && ALL_TABLES.equalsIgnoreCase(secondParam)) {
      sqlUtility.printTablesData();
    } else if (PRINT.equalsIgnoreCase(firstParam)) {
      sqlUtility.printTableData(secondParam);
    } else if (FILE.equalsIgnoreCase(firstParam)) {
      BufferedReader bufferedReader = getFile(secondParam);
      sqlUtility.executeSqlFile(bufferedReader);
    } else if (EXECUTE.equalsIgnoreCase(firstParam)) {
      String selectStatement = getSelectStatement(secondParam);
      sqlUtility.executeSqlStatement(selectStatement);
    } else {
      throw new MedicationManagerShellException("Unexpected pair of parameters");
    }


  }

  private String getSelectStatement(String statement) {
    statement = statement.toUpperCase();
    if (!statement.startsWith("SELECT")) {
      throw new MedicationManagerShellException("This command supports only SELECT statement");
    }

    return statement;
  }

  private BufferedReader getFile(String secondParam) {

    if (!sqlFilesDirectory.isDirectory()) {
      throw new MedicationManagerShellException("The required directory does not exists:" + SQL + " under the" +
          "***/runner/configurations/services/medication_manager");
    }

    Log.info("The Medication Manager will look for the file " + secondParam +
        " in following directory: " + sqlFilesDirectory, getClass());

    File file = new File(sqlFilesDirectory, secondParam);

    if (!file.exists()) {
      throw new MedicationManagerShellException("The file : " + secondParam + " does not exists");
    }

    try {
      FileInputStream fis = new FileInputStream(file);
      InputStreamReader inputStreamReader = new InputStreamReader(fis);
      return new BufferedReader(inputStreamReader);
    } catch (FileNotFoundException e) {
      throw new MedicationManagerShellException(e);
    }

  }

}
