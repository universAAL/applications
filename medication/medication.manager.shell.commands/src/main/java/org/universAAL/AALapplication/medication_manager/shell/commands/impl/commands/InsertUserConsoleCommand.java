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

import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;

import java.io.File;

import static org.universAAL.AALapplication.medication_manager.shell.commands.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class InsertUserConsoleCommand extends ConsoleCommand {

  private final File vCardFilesDirectory;

  private static final String USER = "user";

  public static final String COMMAND_INFO =
      "The insert user from vcard command expects the following two parameters pairs, which are:" +
          "\n\t \n\t - " + USER + " " + "NAME_OF_THE_VCARD_PROPERTY_FILE" +
          " (it will insert a user into the CHE)";

  public InsertUserConsoleCommand(String name, String description) {
    super(name, description);

    vCardFilesDirectory = new File(medicationManagerConfigurationDirectory, "vcard");
  }

  @Override
  public String getParametersInfo() {
    return COMMAND_INFO;
  }


  @Override
  public void execute(String... parameters) {
    try {
      if (parameters == null || parameters.length != 2) {
        throw new MedicationManagerShellException(COMMAND_INFO);
      }

      String firstParam = parameters[0].trim();

      checkFirstParam(firstParam);

      String vcardFile = parameters[1].trim();

      insertUser(firstParam, vcardFile);
    } catch (Exception e) {
      Log.error(e, "Error while processing the the shell command", getClass());
    }

  }

  private void checkFirstParam(String firstParam) {
    Log.info("Checking the first param %s", getClass(), firstParam);


    boolean ok = USER.equalsIgnoreCase(firstParam);

    if (!ok) {
      throw new MedicationManagerShellException("The first parameter must be : " + USER);
    }

  }

  private void insertUser(String firstParam, String vcardFile) {

    UserManager userManager = getUserManager();

    Log.info("Inspecting the second param %s if it is vcard property file", getClass(), vcardFile);

    File vcard = getFile(vcardFile);

    userManager.insertUserFromVCard(vcard);

  }



  private File getFile(String vcard) {
    Log.info("The vCardFilesDirectory is: %s", getClass(), vCardFilesDirectory.getAbsolutePath());
    if (!vCardFilesDirectory.isDirectory()) {
      throw new MedicationManagerShellException("The required directory does not exists: vcard under the" +
          "***/runner/etc/medication_manager");
    }

    Log.info("The Medication Manager will look for the file " + vcard +
        " in following directory: " + vCardFilesDirectory, getClass());

    File file = new File(vCardFilesDirectory, vcard);

    if (!file.exists()) {
      throw new MedicationManagerShellException("The file : " + vcard + " does not exists");
    }

    return file;

  }

}
