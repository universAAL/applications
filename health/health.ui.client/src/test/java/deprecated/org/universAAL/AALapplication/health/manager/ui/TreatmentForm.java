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
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SubdialogTrigger;

/**
 * @author amedrano
 *
 */
public class TreatmentForm extends InputListener {

	private static final String FOLLOW_LABEL = "Follow Treatment";
	private static final String FOLLOW_ICON = null;
	private static final String NEW_LABEL = "Add New Treatment";
	private static final String NEW_ICON = null;
	private static final String VIEW_LABEL = "View Treatment Record";
	private static final String VIEW_ICON = null;
	private static final String EDIT_LABEL = "Edit Treatment";
	private static final String EDIT_ICON = null;
	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#getDialog()
	 */
	@Override
	public Form getDialog() {
		return new TreatmentFollowForm().getDialog();
	}

	void addSubdialogs(Group g) {
		//TODO CHECK the User has permission to access each
		new SubdialogTrigger(g, 
				new Label(FOLLOW_LABEL,FOLLOW_ICON),
				FOLLOW_LABEL);
		new SubdialogTrigger(g, 
				new Label(VIEW_LABEL,VIEW_ICON),
				VIEW_LABEL);
		new SubdialogTrigger(g, 
				new Label(NEW_LABEL,NEW_ICON),
				NEW_LABEL);		
		new SubdialogTrigger(g, 
				new Label(EDIT_LABEL,EDIT_ICON),
				EDIT_LABEL);
	}
	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#handleEvent(org.universAAL.middleware.input.InputEvent)
	 */
	@Override
	public void handleEvent(UIResponse ie) {
		// listen to event for the Form and act Accordingly
		super.handleEvent(ie);
		UIRequest e = null;
		if (ie.getSubmissionID() == FOLLOW_LABEL) {
			e = new UIRequest(ie.getUser(),
					new TreatmentFollowForm().getDialog(),
					MainForm.PRIORITY,
					HealthManager.getLanguage(),
					MainForm.PRIVACY);
		}
		if (ie.getSubmissionID() == VIEW_LABEL) {
			e = new UIRequest(ie.getUser(),
					new TreatmentViewForm().getDialog(),
					MainForm.PRIORITY,
					HealthManager.getLanguage(),
					MainForm.PRIVACY);
		}
		if (ie.getSubmissionID() == NEW_LABEL) {
			e = new UIRequest(ie.getUser(),
					new TreatmentNewForm().getDialog(),
					MainForm.PRIORITY,
					HealthManager.getLanguage(),
					MainForm.PRIVACY);
		}
		if (ie.getSubmissionID() == EDIT_LABEL) {
			e = new UIRequest(ie.getUser(),
					new TreatmentEditForm().getDialog(),
					MainForm.PRIORITY,
					HealthManager.getLanguage(),
					MainForm.PRIVACY);
		}
		HealthManager.getInstance().getIsubcriber().sendUIRequest(e);
	}

}
