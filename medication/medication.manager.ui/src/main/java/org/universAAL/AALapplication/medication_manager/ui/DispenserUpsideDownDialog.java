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
import org.universAAL.AALapplication.medication_manager.ui.impl.Activator;
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

import static org.universAAL.AALapplication.medication_manager.ui.IntakeReviewDialog.*;
import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

public class DispenserUpsideDownDialog extends UICaller {

  private boolean userActed;

  private static final String DISPENSER_UPSIDE_DOWN_FORM = "DispenserUpsideDownFor";

  public DispenserUpsideDownDialog(ModuleContext context) {
    super(context);
  }

  public boolean isUserActed() {
    return userActed;
  }

  @Override
  public void communicationChannelBroken() {
  }

  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    userActed = true;

  }

  public void showDialog(User inputUser) {

    try {
      validateParameter(inputUser, "inputUser");

      Log.info("Request a DispenserUpsideDownDialog for user %s", getClass(), inputUser);

      Form f = Form.newDialog(Activator.getMessage("medication.manager.ui.title"), new Resource());
      //start of the form model
      String message = getMessage(inputUser);
      new SimpleOutput(f.getIOControls(), null, null, message);
      //...
      new Submit(f.getSubmits(), new Label(Activator.getMessage("medication.manager.ui.done"), null), DISPENSER_UPSIDE_DOWN_FORM);
      //stop of form model
      //TODO to remove SAIED user and to return inputUser variable
      UIRequest req = new UIRequest(SAIED, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
      this.sendUIRequest(req);
    } catch (Exception e) {
      Log.error(e, "Error while trying to show dialog", getClass());
    }
  }

  private String getMessage(User user) {

    PersistentService persistentService = getPersistentService();

    String name = getName(persistentService, user);

    return Activator.getMessage("medication.manager.ui.upside.down", name);
  }

@Override
public void dialogAborted(String arg0, Resource arg1) {
	// TODO Auto-generated method stub
	
}

}
