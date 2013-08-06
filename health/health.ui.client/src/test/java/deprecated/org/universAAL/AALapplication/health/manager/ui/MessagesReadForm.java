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

import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;

/**
 * @author amedrano
 *
 */
public class MessagesReadForm extends MessagesForm {


	private static final String LISTFORM_TITLE = "Reading a Message";
	private static final String FROM_LABEL = "From: ";
	private static final String FROM_ICON = "";
	private static final String DATE_LABEL = "Date: ";
	private static final String DATE_ICON = null;
	private static final String SUBJECT_LABEL = "Subject: ";
	private static final String SUBJECT_ICON = null;

	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#getDialog()
	 */
	@Override
	public Form getDialog() {
		Form f = Form.newSubdialog(LISTFORM_TITLE, MainForm.DIALOG_ID);
		addSubdialogs(f.getSubmits());
		/*
		 *  TODO add Message Info
		 *  (add in groups?)
		 *  (add next and previous submits?)
		 */
		new SimpleOutput(f.getIOControls(),
				new Label(FROM_LABEL, FROM_ICON),
				null,
				null); // todo: add the From value
		new SimpleOutput(f.getIOControls(),
				new Label(DATE_LABEL, DATE_ICON),
				null,
				null); // todo: add the Date value
		new SimpleOutput(f.getIOControls(),
				new Label(SUBJECT_LABEL, SUBJECT_ICON),
				null,
				null); // todo: add the Subject value
		new SimpleOutput(f.getIOControls(),
				//new Label(FROM_LABEL, FROM_ICON),
				null,
				null,
				null); // todo: add the Message value
		return f;
	}

	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#handleEvent(org.universAAL.middleware.input.InputEvent)
	 */
	@Override
	public void handleEvent(UIResponse ie) {
		super.handleEvent(ie);

	}

}
