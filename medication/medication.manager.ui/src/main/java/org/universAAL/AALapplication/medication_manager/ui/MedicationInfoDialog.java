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

import org.universAAL.AALapplication.medication_manager.ui.impl.Log;
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
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.Locale;

import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

public class MedicationInfoDialog extends UICaller {

  private final ModuleContext moduleContext;
  private final Time time;
  private final MedicinesInfo medicinesInfo;

  private static final String CLOSE_BUTTON = "closeButton";
  private static final String INFO_BUTTON = "reminderButton";

  public MedicationInfoDialog(ModuleContext context, Time time, MedicinesInfo medicinesInfo) {
    super(context);
    moduleContext = context;
    this.time = time;
    this.medicinesInfo = medicinesInfo;
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

    try {
      validateParameter(inputUser, "inputUser");

      Form f = Form.newDialog("Medication Manager", new Resource());

      //start of the form model

      String medicationInfoMessage = getTitle() + medicinesInfo.getDetailsInfo();

      new SimpleOutput(f.getIOControls(), null, null, medicationInfoMessage);
      //...
      new Submit(f.getSubmits(), new Label("Close", null), CLOSE_BUTTON);
      //stop of form model
      //TODO to remove SAIED user and to return inputUser variable
      UIRequest req = new UIRequest(SAIED, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
      this.sendUIRequest(req);
    } catch (Exception e) {
      Log.error(e, "Error while trying to show dialog", getClass());
    }
  }

  private String getTitle() {

    StringBuilder sb = new StringBuilder();

    sb.append("         Intake info           ");
    sb.append("\n\n");


    return sb.toString();

  }

}
