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


package org.universAAL.AALapplication.medication_manager.ui;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.ui.impl.Activator;
import org.universAAL.AALapplication.medication_manager.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.ui.impl.MedicationManagerUIException;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.profile.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

public class DispenserDisplayInstructionsDialog extends UICaller {

  private static final String DISPENSER_DISPLAY_INSTRUCTIONS_FORM = "DispenserDisplayInstructionsForm";
  private static final String DISPENSER_INSTRUCTIONS = "dispenser_instructions";

  public DispenserDisplayInstructionsDialog(ModuleContext context) {
    super(context);
  }

  @Override
  public void communicationChannelBroken() {
  }

  @Override
  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    // TODO Auto-generated method stub
  }

  public void showDialog(User inputUser) {

    try {
      validateParameter(inputUser, "inputUser");

      Log.info("Request a DispenserDisplayInstructionsDialog for user %s", getClass(), inputUser);

      String formTitle = Activator.getMessage("medication.manager.ui.title");
      Form f = Form.newDialog(formTitle, new Resource());
      //start of the form model
      String message = getInstructionText(inputUser);
      new SimpleOutput(f.getIOControls(), null, null, message);
      //...
      new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.done"), null), DISPENSER_DISPLAY_INSTRUCTIONS_FORM);
      //stop of form model
      //TODO to remove SAIED user and to return inputUser variable
      UIRequest req = new UIRequest(SAIED, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
      this.sendUIRequest(req);
    } catch (Exception e) {
      Log.error(e, "Error while trying to show dialog", getClass());
    }
  }

  private String getInstructionText(User inputUser) throws IOException {
    PersistentService persistentService = getPersistentService();
    PersonDao personDao = persistentService.getPersonDao();
    Person patient = personDao.findPersonByPersonUri(inputUser.getURI());
    DispenserDao dispenserDao = persistentService.getDispenserDao();
    Dispenser dispenser = dispenserDao.getDispenserByPerson(patient);

    if (dispenser == null) {
      return "This user does not have pill dispenser. The pill dispenser could be set via the configuration web application";
    }

    String instructionsFile = dispenser.getInstructionsFileName();

    return getDispenserInstructions(instructionsFile);
  }

  private String getDispenserInstructions(String instructionsFile) throws IOException {
    File medicationManagerConfigurationDirectory = Activator.getMedicationManagerConfigurationDirectory();
    File dispenserInstructionDir = new File(medicationManagerConfigurationDirectory, DISPENSER_INSTRUCTIONS);
    if (!dispenserInstructionDir.exists()) {
      throw new MedicationManagerUIException("Missing " + DISPENSER_INSTRUCTIONS + " directory");
    }

    if (!dispenserInstructionDir.isDirectory()) {
      throw new MedicationManagerUIException("The " + DISPENSER_INSTRUCTIONS + " is not valid directory");
    }

    File file = new File(dispenserInstructionDir, instructionsFile);

    FileReader fileReader = new FileReader(file);
    BufferedReader br = new BufferedReader(fileReader);
    StringBuffer sb = new StringBuffer();
    String line = br.readLine();
    while (line != null) {
      sb.append(line);
      sb.append('\n');
      line = br.readLine();
    }

    return sb.toString();
  }


}
