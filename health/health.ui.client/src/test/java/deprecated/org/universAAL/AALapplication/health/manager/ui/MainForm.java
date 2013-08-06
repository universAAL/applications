/*******************************************************************************
 * Copyright 2011 Universidad Polit�cnica de Madrid
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
package deprecated.org.universAAL.AALapplication.health.manager.ui;

import org.universAAL.AALapplication.health.manager.HealthManager;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SubdialogTrigger;

/**
 * @author amedrano
 *
 */
public class MainForm extends InputListener {

	//TODO: internationalization!
	private static final String MESSAGE_LABEL = "Messages";
	private static final String MESSAGE_ICON = null;
	private static final String TREATMENT_LABEL = "Treatment";
	private static final String TREATMENT_ICON = null;
	private static final String PREFERENCES_LABEL = "Preferences";
	private static final String PREFERENCES_ICON = null;
	static final LevelRating PRIORITY = LevelRating.low;
	static final PrivacyLevel PRIVACY = PrivacyLevel.insensible;
	
	static String DIALOG_ID;
	
	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#getDialog()
	 */
	@Override
	public Form getDialog() {
		// Create Dialog
		Form f = Form.newDialog("Health Manager AAL Service", (Resource)null);
		new SubdialogTrigger(f.getSubmits(), 
				new Label(MESSAGE_LABEL, MESSAGE_ICON),
				MESSAGE_LABEL);
		new SubdialogTrigger(f.getSubmits(), 
				new Label(TREATMENT_LABEL, TREATMENT_ICON),
				TREATMENT_LABEL);
		new SubdialogTrigger(f.getSubmits(), 
				new Label(PREFERENCES_LABEL, PREFERENCES_ICON),
				PREFERENCES_LABEL);
		DIALOG_ID = f.getDialogID();
		listenTo(DIALOG_ID);
		// TODO add home submit
		// TODO Welcome Pane in IOControls
		return f;
	}

	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#handleEvent(org.universAAL.middleware.input.InputEvent)
	 */
	@Override
	public void handleEvent(UIResponse ie) {
		// listen to event for the Form and act Accordingly
		super.handleEvent(ie);
		UIRequest e = null;
		if (ie.getSubmissionID() == MESSAGE_LABEL) {
			e = new UIRequest(ie.getUser(),
					new MessagesForm().getDialog(),
					PRIORITY,
					HealthManager.getLanguage(),
					PRIVACY);
		}
		if (ie.getSubmissionID() == TREATMENT_LABEL) {
			e = new UIRequest(ie.getUser(),
					new TreatmentForm().getDialog(),
					PRIORITY,
					HealthManager.getLanguage(),
					PRIVACY);
		}
		if (ie.getSubmissionID() == PREFERENCES_LABEL) {
			e = new UIRequest(ie.getUser(),
					new PreferencesFrom().getDialog(),
					PRIORITY,
					HealthManager.getLanguage(),
					PRIVACY);
		}
		HealthManager.getInstance().getIsubcriber().sendUIRequest(e);
	}

}
