/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnolog�as para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.ltba.ui.activator;

import java.util.Locale;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universaal.ltba.ui.impl.common.LTBACaller;
import org.universaal.ltba.ui.impl.common.SharedResources;
import org.universaal.ltba.ui.impl.reports.DayUI;
import org.universaal.ltba.ui.impl.reports.MonthUI;
import org.universaal.ltba.ui.impl.reports.WeekUI;
/**
 * User interface provider.
 * @author mllorente
 *
 */
public class MainLTBAUIProvider extends UICaller {

	static final String MY_UI_NAMESPACE = "http://www.tsbtecnolgias.es/LTBAUICaller.owl#";
	static final String SUBMISSION_ON = MY_UI_NAMESPACE + "submissionOn";
	static final String SUBMISSION_OFF = MY_UI_NAMESPACE + "submissionOff";
	static final String SUBMISSION_SHOW = MY_UI_NAMESPACE + "submissionDay";
	static final String SUBMISSION_WEEK = MY_UI_NAMESPACE + "submissionWeek";
	static final String SUBMISSION_MONTH = MY_UI_NAMESPACE + "submissionMonth";

	static final String USER_INPUT_SELECTED_RANGE = MY_UI_NAMESPACE
			+ "selectedRange";

	ModuleContext myModuleContext;
	Resource lastUser = new Resource();

	public MainLTBAUIProvider(ModuleContext context) {
		super(context);
		myModuleContext = context;
	}

	@Override
	public void communicationChannelBroken() {

	}

	@Override
	public void dialogAborted(String dialogID) {

	}

	public Form showDialog(Resource inputUser) {
		lastUser = inputUser;
		Form f = Form.newDialog("LTBA UI", new Resource());
		// start of the form model
		// new SimpleOutput(f.getIOControls(), null, null,
		// "Long Term Behaviour Analyzer User Interface");
		// new SimpleOutput(f.getIOControls(), null, null, "NANONANO");
		// new SimpleOutput(f.getIOControls(), null, null, "TETETETE");

		// Submit s = new Submit(f.getIOControls(), new Label("Activate LTBA",
		// null), "ltbaButton");
		if (SharedResources.ltbaIsOn) {
			new Submit(f.getIOControls(), new Label(">ltbaOn<", null),
					SUBMISSION_ON);
			new Submit(f.getIOControls(), new Label("ltbaOff", null),
					SUBMISSION_OFF);
		} else {
			new Submit(f.getIOControls(), new Label("ltbaOn", null),
					SUBMISSION_ON);
			new Submit(f.getIOControls(), new Label(">ltbaOff<", null),
					SUBMISSION_OFF);
		}
//		new Submit(f.getSubmits(), new Label("Day Report", null),
//				SUBMISSION_SHOW);
//		new Submit(f.getSubmits(), new Label("Week Report", null),
//				SUBMISSION_WEEK);
//		new Submit(f.getSubmits(), new Label("Month Report", null),
//				SUBMISSION_MONTH);
		if (inputUser == null) {
			System.out.println("NULL INPUT USER");
		}
		new Submit(f.getSubmits(), new Label("Done", null), "doneForm");

		/**
		 * TODO There is a bug. Despite when the service request is created, a
		 * user is specified, when this call is handled, the user extracted from
		 * the object of the call is null. This workaround will allow keep the
		 * service working, but the users must be correctly handled in the
		 * future.
		 */
		if (inputUser == null) {
			inputUser = new AssistedPerson(
					Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
		}
		UIRequest req = new UIRequest(inputUser, f, LevelRating.none,
				Locale.ENGLISH, PrivacyLevel.insensible);
		this.sendUIRequest(req);
		return f;
	}

	public void handleUIResponse(UIResponse uir) {
		if (lastUser == null) {
			lastUser = new AssistedPerson(
					Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
		}
		if (uir != null) {
			if (SUBMISSION_ON.equals(uir.getSubmissionID())) {
				SharedResources.ltbaIsOn = true;
				new LTBACaller(myModuleContext, lastUser);
				LTBACaller.switchOn();
			} else if (SUBMISSION_OFF.equals(uir.getSubmissionID())) {
				SharedResources.ltbaIsOn = false;
				new LTBACaller(myModuleContext, lastUser);
				LTBACaller.switchOff();
			} else if (SUBMISSION_SHOW.equals(uir.getSubmissionID())) {
				myModuleContext.logDebug("","To DroolsCaller in SUBMISSION_SHOW",
						null);
				new DayUI(myModuleContext).showDialog(lastUser);
				return;
			} else if (SUBMISSION_WEEK.equals(uir.getSubmissionID())) {
				myModuleContext.logDebug("","To DroolsCaller in SUBMISSION_WEEK",
						null);
				new WeekUI(myModuleContext).showDialog(lastUser);
				return;
			} else if (SUBMISSION_MONTH.equals(uir.getSubmissionID())) {
				myModuleContext.logDebug("","To DroolsCaller in SUBMISSION_MONTH",
						null);
				new MonthUI(myModuleContext).showDialog(lastUser);
				return;
			}
			// yoda conditions everywhere
			if ("doneForm".equals(uir.getSubmissionID())) {
				startMainDialog();
			} else {
				showDialog(lastUser);
			}
		}
	}

	void startMainDialog() {
	}

}
