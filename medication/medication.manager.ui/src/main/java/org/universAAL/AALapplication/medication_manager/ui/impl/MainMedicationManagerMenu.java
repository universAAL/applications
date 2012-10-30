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

import java.util.Locale;

public class MainMedicationManagerMenu extends UICaller {

  private static final String WELCOME_TO_THE_MEDICATION_MANAGER_SERVICE = "Welcome To the MedicationManager Service!";
  private static final String INTAKE_PLAN_FORM = "intakePlanForm";
  private static final String INTAKE_LOG_FORM = "intakeLogForm";
  private static final String INVENTORY_STATUS_FORM = "inventoryStatusForm";
  private static final String REFILL_OF_DISPENSER_FORM = "refillOfDispenserForm";
  private static final String CALL_A_CONTACT_FORM = "callAContactForm";
  private static final String FEEDBACK_FORM = "feedbackForm";
  private static final String NEW_DOSE_FORM = "NewDoseForm";
  private static final String SETTINGS_FORM = "settingsForm";
  private static final String CLOSE_FORM = "closeForm";

  protected MainMedicationManagerMenu(ModuleContext context) {
		super(context);
	}

	@Override
	public void communicationChannelBroken() {	}

	@Override
	public void dialogAborted(String dialogID) {	}

	@Override
	public void handleUIResponse(UIResponse input) {
		// TODO Auto-generated method stub
	}
	
	public void showDialog(Resource inputUser){
		Form f = Form.newDialog("simple UI", new Resource());
		//start of the form model
		new SimpleOutput(f.getIOControls(), null, null, WELCOME_TO_THE_MEDICATION_MANAGER_SERVICE);
		//...
		new Submit(f.getSubmits(), new Label("intake Plan", null), INTAKE_PLAN_FORM);
		new Submit(f.getSubmits(), new Label("intake Log", null), INTAKE_LOG_FORM);
		new Submit(f.getSubmits(), new Label("Inventory Status", null), INVENTORY_STATUS_FORM);
		new Submit(f.getSubmits(), new Label("Refill of Dispenser", null), REFILL_OF_DISPENSER_FORM);
		new Submit(f.getSubmits(), new Label("Call a Contact", null), CALL_A_CONTACT_FORM);
		new Submit(f.getSubmits(), new Label("Feedback", null), FEEDBACK_FORM);
		new Submit(f.getSubmits(), new Label("New Dose", null), NEW_DOSE_FORM);
		new Submit(f.getSubmits(), new Label("Settings", null), SETTINGS_FORM);
		new Submit(f.getSubmits(), new Label("Close", null), CLOSE_FORM);
		//stop of form model
		UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
		this.sendUIRequest(req);
	}

}
