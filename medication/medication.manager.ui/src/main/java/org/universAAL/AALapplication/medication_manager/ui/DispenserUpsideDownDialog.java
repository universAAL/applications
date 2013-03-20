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
import org.universAAL.ontology.profile.User;

import java.util.Locale;

import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

public class DispenserUpsideDownDialog extends UICaller {

  private static final String DISPENSER_UPSIDE_DOWN_FORM = "DispenserUpsideDownFor";

  public DispenserUpsideDownDialog(ModuleContext context) {
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

    validateParameter(inputUser, "inputUser");

    Log.info("Request a DispenserUpsideDownDialog for user %s", getClass(), inputUser);

    Form f = Form.newDialog("Medication Manager UI", new Resource());
    //start of the form model
    String message = getMessage(inputUser);
    new SimpleOutput(f.getIOControls(), null, null, message);
    //...
    new Submit(f.getSubmits(), new Label("Done", null), DISPENSER_UPSIDE_DOWN_FORM);
    //stop of form model
    UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
    this.sendUIRequest(req);
  }

  private String getMessage(User user) {

    StringBuilder sb = new StringBuilder();
    String userFriendlyname = ReminderDialog.getUserfriendlyName(user);
    sb.append(userFriendlyname);
    sb.append(',');
    sb.append("the dispenser is placed upside down.");
    sb.append(" Please put it in the right position");
    sb.append('\n');
    sb.append('\n');

    return sb.toString();
  }

}
