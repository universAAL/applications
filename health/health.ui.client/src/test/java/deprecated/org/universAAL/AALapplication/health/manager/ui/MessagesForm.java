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
public class MessagesForm extends InputListener {

	
	private static final String LIST_LABEL = "List Messages";
	private static final String LIST_ICON = null;
	private static final String READ_LABEL = "Read Message";
	private static final String READ_ICON = null;
	private static final String SEND_LABEL = "Write Message";
	private static final String SEND_ICON = null;

	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#getDialog()
	 */
	@Override
	public Form getDialog() {
		return new MessagesListForm().getDialog();
	}
	
	void addSubdialogs(Group g) {
		new SubdialogTrigger(g, 
				new Label(LIST_LABEL,LIST_ICON),
				LIST_LABEL);
		new SubdialogTrigger(g, 
				new Label(READ_LABEL,READ_ICON),
				READ_LABEL);
		new SubdialogTrigger(g, 
				new Label(SEND_LABEL,SEND_ICON),
				SEND_LABEL);
	}

	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#handleEvent(org.universAAL.middleware.input.InputEvent)
	 */
	@Override
	public void handleEvent(UIResponse ie) {
		// listen to event for the Form and act Accordingly
				super.handleEvent(ie);
				UIRequest e = null;
				if (ie.getSubmissionID() == LIST_LABEL) {
					e = new UIRequest(ie.getUser(),
							new MessagesListForm().getDialog(),
							MainForm.PRIORITY,
							HealthManager.getLanguage(),
							MainForm.PRIVACY);
				}
				if (ie.getSubmissionID() == READ_LABEL) {
					e = new UIRequest(ie.getUser(),
							new MessagesReadForm().getDialog(),
							MainForm.PRIORITY,
							HealthManager.getLanguage(),
							MainForm.PRIVACY);
				}
				if (ie.getSubmissionID() == SEND_LABEL) {
					e = new UIRequest(ie.getUser(),
							new MessagesWriteForm().getDialog(),
							MainForm.PRIORITY,
							HealthManager.getLanguage(),
							MainForm.PRIVACY);
				}
				HealthManager.getInstance().getIsubcriber().sendUIRequest(e);
	}

}
