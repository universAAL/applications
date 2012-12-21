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
package org.universAAL.AALapplication.biomedicalsensors.uiclient;

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
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;

/**
 * @author joemoul, billyk
 * 
 */
public class AlertUI extends UICaller {

	private Form mainDialog = null;

	protected AlertUI(ModuleContext context) {
		super(context);
		
	}

	@Override
	public void communicationChannelBroken() {
		
		System.out.println("-----Channel Broken--------");

	}

	@Override
	public void dialogAborted(String dialogID) {
		
		System.out.println("----!-DIALOG ABORTED--------");

	}

	@Override
	public void handleUIResponse(UIResponse uir) {
		
		System.out.println("-----DIALOG RESPONSE RECEIVED--------");
		if (uir != null) {

			// button
			if ("STOP".equals(uir.getSubmissionID())) {
				// System.out.println("-----STOPPED PRESSED FOR--------"+SharedResources.uIProvider.bsURI);
				BiomedicalSensorsServiceCaller.orangeAlertActive = false;
				BiomedicalSensorsServiceCaller.redAlertActive = false;
				BiomedicalSensorsServiceCaller
						.stopWaitForAlerts(SharedResources.uIProvider.bsURI);

				SharedResources.uIProvider
						.startMainDialog(SharedResources.uIProvider
								.getbsIDFromURI(SharedResources.uIProvider.bsURI));
			} else if ("OK".equals(uir.getSubmissionID())) {
				BiomedicalSensorsServiceCaller.orangeAlertActive = false;
				BiomedicalSensorsServiceCaller.redAlertActive = false;
				SharedResources.uIProvider
						.startMainDialog(SharedResources.uIProvider
								.getbsIDFromURI(SharedResources.uIProvider.bsURI));
				// BiomedicalSensorsServiceCaller.getMeasurements(bsURI);
			}

		}

	}

	private Form initMainDialog(String alertType) {

		Form f = Form.newDialog(alertType + " ALERT!", new Resource());

		new MediaObject(f.getIOControls(), new Label("", null), "image/png",
				alertType + "alert.png");
		new MediaObject(f.getIOControls(), new Label("", null), "image/png",
				alertType + "posture.png");
		new SimpleOutput(f.getIOControls(), null, null, "");
		new MediaObject(f.getIOControls(), new Label("", null), "image/png",
				alertType + "heartrate.png");
		new SimpleOutput(f.getIOControls(), null, null, "");
		new MediaObject(f.getIOControls(), new Label("", null), "image/png",
				alertType + "activity.png");
		new Submit(f.getSubmits(), new Label("I'm OK", null), "OK");
		new Submit(f.getSubmits(), new Label("Stop Monitoring", null), "STOP");

		return f;
	}

	public Form startOrangeDialog(String alertType, LevelRating lr) {

		mainDialog = initMainDialog(alertType);

		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog, lr,
				Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);

		return mainDialog;
	}
}
