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
import org.universAAL.ontology.medMgr.MedicinesInfo;
import org.universAAL.ontology.medMgr.MyIntakeInfosDatabase;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.Locale;

public class RequestMedicationInfoDialog extends UICaller {

  private static final String EMPTY_STRING = "";
  private static final Time ZERO_TIME_DUMMY = new Time(0, 0, 0, 0, 0);
  private final ModuleContext moduleContext;
  private final Time time;

  private static final String CLOSE_BUTTON = "closeButton";
  private static final String INFO_BUTTON = "infoButton";

  public RequestMedicationInfoDialog(ModuleContext context, Time time) {
    super(context);
    this.moduleContext = context;
    this.time = time;
  }

  public RequestMedicationInfoDialog(ModuleContext context) {
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
    User user = (User) input.getUser();
    if (CLOSE_BUTTON.equals(input.getSubmissionID())) {
      ReminderDialog reminderDialog = new ReminderDialog(moduleContext, time);
      reminderDialog.showDialog(user);
    } else if (INFO_BUTTON.equals(input.getSubmissionID())) {
      MedicationInfoDialog medicationInfoDialog = new MedicationInfoDialog(moduleContext, time);
      medicationInfoDialog.showDialog(user);
    } else {
      System.out.println("unknown");
    }
  }

  public void showDialog(User inputUser) {
    Form f = Form.newDialog("Medication Manager UI", new Resource());
    //start of the form model

    MedicinesInfo intakeInfoForUser;
    if (time != null) {
      intakeInfoForUser = MyIntakeInfosDatabase.getIntakeInfoForUser(inputUser, time);
    } else {
      intakeInfoForUser = new MedicinesInfo(EMPTY_STRING, EMPTY_STRING, ZERO_TIME_DUMMY);
    }

    new SimpleOutput(f.getIOControls(), null, null, intakeInfoForUser.getGeneralInfo());
    //...
    new Submit(f.getSubmits(), new Label("close", null), CLOSE_BUTTON);
    new Submit(f.getSubmits(), new Label("info", null), INFO_BUTTON);
    //stop of form model
    UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
    this.sendUIRequest(req);

  }

}
