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
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.Locale;

import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

public class ReminderDialog extends UICaller {

  private final ModuleContext moduleContext;
  private final Time time;

  private boolean userActed;

  private static final String CLOSE_BUTTON = "closeButton";
  private static final String INFO_BUTTON = "reminderButton";

  public ReminderDialog(ModuleContext context, Time time) {
    super(context);

    validateParameter(context, "context");
    validateParameter(time, "time");

    moduleContext = context;
    this.time = time;
    this.userActed = false;
  }

  public ReminderDialog(ModuleContext context) {
    this(context, null);
  }

  @Override
  public void communicationChannelBroken() {
  }

  @Override
  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    userActed = true;
    if (CLOSE_BUTTON.equals(input.getSubmissionID())) {
      System.out.println("close");
    } else if (INFO_BUTTON.equals(input.getSubmissionID())) {
      showRequestMedicationInfoDialog((User) input.getUser());
    } else {
      System.out.println("unknown");
    }

  }

  private void showRequestMedicationInfoDialog(User user) {

    RequestMedicationInfoDialog dialog = new RequestMedicationInfoDialog(moduleContext, time);
    dialog.showDialog(user);

  }

  public void showDialog(User inputUser) {

    Form f = Form.newDialog("Medication Manager UI", new Resource());

    //start of the form model

    String timeText;
    if (time != null) {
      timeText = '<' + time.getDailyTextFormat() + '>';
    } else {
      timeText = "";
    }
    String reminderMessage = getUserfriendlyName(inputUser) + ",\nit is time " + timeText + " to get your medicine.";

    new SimpleOutput(f.getIOControls(), null, null, reminderMessage);
    //...
    new Submit(f.getSubmits(), new Label("Close", null), CLOSE_BUTTON);
    new Submit(f.getSubmits(), new Label("Info", null), INFO_BUTTON);
    //stop of form model
    UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
    this.sendUIRequest(req);
  }

  public static String getUserfriendlyName(User inputUser) {
    String fullUserUriName = inputUser.toString();
    int index = fullUserUriName.lastIndexOf('#');
    if (index == -1) {
      throw new MedicationManagerUIException("Expected # symbol in the user.getUri() format like: \n" +
          "urn:org.universAAL.aal_space:test_env#saied");
    }
    String firstLetter = fullUserUriName.substring(index + 1).toUpperCase();
    return firstLetter.charAt(0) + fullUserUriName.substring(index + 2);
  }

  public boolean isUserActed() {
    return userActed;
  }
}
