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


package org.universAAL.AALapplication.medication_manager.ui.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.Week;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.ui.DispenserDisplayInstructionsDialog;
import org.universAAL.AALapplication.medication_manager.ui.IntakeReviewDialog;
import org.universAAL.AALapplication.medication_manager.ui.InventoryStatusDialog;
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

import java.util.Calendar;
import java.util.Locale;

import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

public class MainMedicationManagerMenu extends UICaller {

  private static final String INTAKE_REVIEW_BUTTON = "intakeReview";
  private static final String INVENTORY_STATUS_BUTTON = "inventoryStatus";
  private static final String DISPENSER_INSTRUCTIONS_BUTTON = "dispenserInstructions";
  private static final String CLOSE_BUTTON = "close";

  protected MainMedicationManagerMenu(ModuleContext context) {
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
    try {
      PersistentService persistentService = getPersistentService();
      User user = getUser(input, persistentService);
      if (CLOSE_BUTTON.equals(input.getSubmissionID())) {
      } else if (INTAKE_REVIEW_BUTTON.equals(input.getSubmissionID())) {
        showIntakeReviewDialog(user, persistentService);
      } else if (INVENTORY_STATUS_BUTTON.equals(input.getSubmissionID())) {
        showInventoryStatusDialog(user);
      } else if (DISPENSER_INSTRUCTIONS_BUTTON.equals(input.getSubmissionID())) {
        showDispenserInstructionDialog(user);
      } else {
        throw new MedicationManagerUIException("Unknown button clicked");
      }
    } catch (Exception e) {
      Log.error(e, "Error while handling UI response", getClass());
    }

  }

  private User getUser(UIResponse input, PersistentService persistentService) {
    User user = (User) input.getUser();

    String uri = user.getURI();
    Log.info("User object uri is : %s", getClass(), uri);

    //TODO hack to be removed (replace the test user Saied with our mock user saied

    if ("urn:org.universAAL.aal_space:test_environment#saied".equalsIgnoreCase(uri)) {
      PersonDao personDao = persistentService.getPersonDao();
      Person patient = personDao.getById(1);
      return new User(patient.getPersonUri());
    }

    return user;
  }

  private void showDispenserInstructionDialog(User user) {
    DispenserDisplayInstructionsDialog dialog = new DispenserDisplayInstructionsDialog(mc);
    dialog.showDialog(user);
  }

  private void showInventoryStatusDialog(User user) {
    InventoryStatusDialog dialog = new InventoryStatusDialog(mc);
    dialog.showDialog(user);
  }

  private void showIntakeReviewDialog(User user, PersistentService persistentService) {
    PersonDao personDao = persistentService.getPersonDao();
    Person patient = personDao.findPersonByPersonUri(user.getURI());
    Calendar startOfTheCurrentWeek = Week.getMondayOfTheCurrentWeek();
    Week currentWeek = Week.createWeek(startOfTheCurrentWeek);
    IntakeReviewDialog dialog = new IntakeReviewDialog(mc, persistentService, currentWeek, patient);
    dialog.showDialog(user);
  }

  public void showDialog(Resource inputUser) {
    Form f = Form.newDialog(getMessage("medication.manager.ui.main.menu"), new Resource());
    //start of the form model
    new SimpleOutput(f.getIOControls(), null, null, getMessage("medication.manager.ui.welcome"));
    //...
    new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.intake.review"), null), INTAKE_REVIEW_BUTTON);
    new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.intake.inventory.status"), null), INVENTORY_STATUS_BUTTON);
    new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.intake.dispenser.instructions"), null), DISPENSER_INSTRUCTIONS_BUTTON);
    new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.close"), null), CLOSE_BUTTON);
    //stop of form model
    UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
    this.sendUIRequest(req);
  }

}
