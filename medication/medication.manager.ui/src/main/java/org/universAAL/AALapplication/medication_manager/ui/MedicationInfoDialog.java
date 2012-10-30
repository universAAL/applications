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
import org.universAAL.ontology.medMgr.MedicationException;
import org.universAAL.ontology.medMgr.MedicinesInfo;
import org.universAAL.ontology.medMgr.MyIntakeInfosDatabase;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.Locale;

public class MedicationInfoDialog extends UICaller {

  private final ModuleContext moduleContext;
  private final Time time;

  private static final String CLOSE_BUTTON = "closeButton";
  private static final String INFO_BUTTON = "reminderButton";

  public MedicationInfoDialog(ModuleContext context, Time time) {
    super(context);
    moduleContext = context;
    this.time = time;
  }

  @Override
  public void communicationChannelBroken() {
  }

  @Override
  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    //nothing to do here

  }

  public void showDialog(User inputUser) {
    Form f = Form.newDialog("Medication Manager UI", new Resource());


    //start of the form model

    MedicinesInfo intakeInfoForUser = MyIntakeInfosDatabase.getIntakeInfoForUser(inputUser, time);
    if (intakeInfoForUser == null) {
      throw new MedicationException("No information for the user: " + inputUser);
    }
    String medicationInfoMessage = getTitle() + intakeInfoForUser.getDetailsInfo();

    new SimpleOutput(f.getIOControls(), null, null, medicationInfoMessage);
    //...
    new Submit(f.getSubmits(), new Label("Close", null), CLOSE_BUTTON);
    //stop of form model
    UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
    this.sendUIRequest(req);
  }

  private String getTitle() {

    StringBuilder sb = new StringBuilder();

    sb.append("         Intake info           ");
    sb.append("\n\n");


    return sb.toString();

  }

}
