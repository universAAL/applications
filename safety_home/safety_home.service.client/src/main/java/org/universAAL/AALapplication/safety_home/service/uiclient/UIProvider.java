/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
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
 *****************************************************************************************/

package org.universAAL.AALapplication.safety_home.service.uiclient;

import java.util.Locale;


import org.universAAL.AALapplication.safety_home.service.uiclient.dialogs.door.FrontDoorControl;
import org.universAAL.AALapplication.safety_home.service.uiclient.dialogs.environmental.EnvironmentalControl;
import org.universAAL.AALapplication.safety_home.service.uiclient.utils.Utils;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.phThing.Device;

/**
 * @author dimokas
 * 
 */
public class UIProvider extends UICaller {

	private final static String window = "UIProvider#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SAFETY_UI_NAMESPACE + window;
    static final String SUBMIT_DOOR = MY_UI_NAMESPACE + "door";
    static final String SUBMIT_ENVIRONMENTAL = MY_UI_NAMESPACE + "environmental";
    static final String SUBMIT_HELP = MY_UI_NAMESPACE + "help";
    static final String SUBMIT_EXIT = MY_UI_NAMESPACE + "exit";

    public static Form mainDialog = null;
	public static String dialogID;
	private Device[] devices = null;
	
	protected UIProvider(ModuleContext context) {
		super(context);
		devices = SafetyClient.getControlledDevices();
		for (int i = 0; i < devices.length; i++) {
			String label = devices[i].getResourceLabel();
			if (label == null) {
				String st = devices[i].getURI();
				String type = StringUtils.deriveLabel((st == null) ? devices[i]
						.getClassURI() : st);
				label = devices[i].getOrConstructLabel(type);
				devices[i].setResourceLabel(label);
			}
		}
	}

    public void communicationChannelBroken() { }

	private Form initMainDialog() {
		Form f = Form.newDialog("Safety and Security",	new Resource());
		//SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null, "Welcome to the Safety and Security service.");
		new SimpleOutput(f.getIOControls(), null, null,"Safety and Secutiry in my Space");

		new Submit(f.getSubmits(), new Label("Front Door Control", null), SUBMIT_DOOR);
		new Submit(f.getSubmits(), new Label("Environmental Control", null), SUBMIT_ENVIRONMENTAL);
		new Submit(f.getSubmits(), new Label("Help", null), SUBMIT_HELP);
		new Submit(f.getSubmits(), new Label("Exit", null), SUBMIT_EXIT);

		return f;
	}

    String getDeviceURI(int index) {
		if (index < devices.length)
		    return devices[index].getURI();
		return null;
    }

	public int getbsIDFromURI(String deviceURI) {
		System.out.println(">> DEVICE URI: "+deviceURI);
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].getURI().equals(deviceURI)) {
				return i;
			}
		}
		return 0;
	}
    
	public void handleUIResponse(UIResponse uir) {
		if (uir != null) {
		    if (SUBMIT_EXIT.equals(uir.getSubmissionID()))
				return; // Cancel Dialog, go back to main dialog

		    if (SUBMIT_DOOR.equals(uir.getSubmissionID())) {
		    	this.showDoorMainDialog();
			} 
		    if (SUBMIT_ENVIRONMENTAL.equals(uir.getSubmissionID())) {
		    	this.showEnvironmentalMainDialog();
			} 
		}
		//SharedResources.uIProvider.startMainDialog();
	}

    public void startMainDialog() {
    	int deviceID=0;
		if (mainDialog == null)
		    mainDialog = initMainDialog();
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
			LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
    }

	private void showDoorMainDialog() {
		Utils.println(window + " starts Front Door Dialog");
		//SharedResources.fdc.startMainDialog();
		SafetyClient.fdc.startMainDialog();
	}

	private void showEnvironmentalMainDialog() {
		Utils.println(window + " starts Front Door Dialog");
		//SharedResources.ec.startMainDialog();
		SafetyClient.ec.startMainDialog();
	}

	@Override
	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

}
