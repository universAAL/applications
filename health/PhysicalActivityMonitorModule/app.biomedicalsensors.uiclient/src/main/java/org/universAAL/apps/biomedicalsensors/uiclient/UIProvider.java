/*
	Copyright 2012 CERTH, http://www.certh.gr
	
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
package org.universAAL.apps.biomedicalsensors.uiclient;

import java.util.Locale;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.biomedicalsensors.CompositeBiomedicalSensor;
import org.universAAL.ontology.biomedicalsensors.SensorType;

/**
 * @author joemoul, billyk
 * 
 */
public class UIProvider extends UICaller {

	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_BIOMEDICALSENSORS_UI_NAMESPACE
			+ "UIProvider#";

	public static final String START_SVC = MY_UI_NAMESPACE + "Start";
	public static final String STOP_SVC = MY_UI_NAMESPACE + "Stop";
	public static final String INFO_SVC = MY_UI_NAMESPACE + "Info";
	public static final String SUBMISSION_EXIT = MY_UI_NAMESPACE + "Exit";
	public static final String USER_INPUT_SELECTED_BIOSENSOR = MY_UI_NAMESPACE
			+ "SelectedBiosensor";
	static final PropertyPath PROP_PATH_SELECTED_BIOSENSOR = new PropertyPath(
			null, false, new String[] { USER_INPUT_SELECTED_BIOSENSOR });
	public String bsURI = null;

	public static String dialogID;

	public static Form mainDialog = null;

	private CompositeBiomedicalSensor[] devices = null;

	protected UIProvider(ModuleContext context) {
		super(context);

		// initialize the list of devices
		devices = BiomedicalSensorsServiceCaller.getControlledSensors();

		for (int i = 0; i < devices.length; i++) {
			String label = devices[i].getResourceLabel();
			if (label == null) {

				SensorType st = devices[i].getSensorType();
				String type = StringUtils.deriveLabel((st == null) ? devices[i]
						.getClassURI() : st.getURI());
				label = devices[i].getOrConstructLabel(type);

				devices[i].setResourceLabel(label);
			}
		}
	}

	public int getbsIDFromURI(String bsURI) {
		// System.out.println("BS URI: "+bsURI);
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].getURI().equals(bsURI)) {
				return i;
			}
		}
		return 0;
	}

	public void communicationChannelBroken() {

	}

	private Form initMainDialog(int bsID) {
		Form f = Form.newDialog(
				"Physical Activity Monitor Module UI: Sensors Controllers",
				new Resource());

		new SimpleOutput(f.getIOControls(), null, null,
				"Biomedical Sensors in my Space");

		Select1 select = new Select1(
				f.getIOControls(),
				new Label(
						"Please select the sensor you will use in your exercise, and click 'Start Monitoring':",
						null), PROP_PATH_SELECTED_BIOSENSOR,
				MergedRestriction.getAllValuesRestrictionWithCardinality(
						USER_INPUT_SELECTED_BIOSENSOR,
						CompositeBiomedicalSensor.MY_URI, 1, 1), devices[bsID]);
		select.generateChoices(devices);

		// add the possible operations while specifying the mandatory input
		// needed form them
		new Submit(f.getSubmits(), new Label("Start Monitoring", null),
				START_SVC).addMandatoryInput(select);
		new Submit(f.getSubmits(), new Label("Stop Monitoring", null), STOP_SVC)
				.addMandatoryInput(select);
		new Submit(f.getSubmits(), new Label("Get Sensor info", null), INFO_SVC)
				.addMandatoryInput(select);
		// ...
		Submit s = new Submit(f.getSubmits(), new Label("Done", null),
				"doneForm");

		s.addMandatoryInput(select);

		// add an exit button for quitting the dialog
		new Submit(f.getSubmits(), new Label("Exit", null), SUBMISSION_EXIT);

		return f;
	}

	String getDeviceURI(int index) {
		if (index < devices.length)
			return devices[index].getURI();
		return null;
	}

	public void handleUIResponse(UIResponse uir) {
		if (uir != null) {
			if (SUBMISSION_EXIT.equals(uir.getSubmissionID())) {
				BiomedicalSensorsServiceCaller.stopWaitForAlerts(bsURI);
				return; // Cancel Dialog, go back to main dialog
			}

			Object o = uir.getUserInput(PROP_PATH_SELECTED_BIOSENSOR
					.getThePath());
			if (o instanceof CompositeBiomedicalSensor) {
				bsURI = ((CompositeBiomedicalSensor) o).getURI();
			}

			if (bsURI != null) {
				// button
				if (START_SVC.equals(uir.getSubmissionID())) {
					// System.out.println("TRYING TO START WAIT FOR ALERTS... ");
					BiomedicalSensorsServiceCaller.startWaitForAlerts(bsURI);
					SharedResources.uIProvider
							.startMainDialog(getbsIDFromURI(bsURI));
				} else if (STOP_SVC.equals(uir.getSubmissionID())) {
					BiomedicalSensorsServiceCaller.stopWaitForAlerts(bsURI);
					SharedResources.uIProvider
							.startMainDialog(getbsIDFromURI(bsURI));
					// BiomedicalSensorsServiceCaller.getMeasurements(bsURI);
				}
				if (INFO_SVC.equals(uir.getSubmissionID())) {
					BiomedicalSensorsServiceCaller.getInfo(bsURI);
					// BiomedicalSensorsServiceCaller.getMeasurements(bsURI);
					SharedResources.uIProvider
							.startMainDialog(getbsIDFromURI(bsURI));
				}
			}

		}

	}

	void startMainDialog() {
		int bsID = 0;
		mainDialog = initMainDialog(bsID);
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	void startMainDialog(int bsID) {

		mainDialog = initMainDialog(bsID);
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.ui.UICaller#dialogAborted(java.lang.String)
	 */
	@Override
	public void dialogAborted(String dialogID) {
		// TODO Auto-generated method stub

	}
}
