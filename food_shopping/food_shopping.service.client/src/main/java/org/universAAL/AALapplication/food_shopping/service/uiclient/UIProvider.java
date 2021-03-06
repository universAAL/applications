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

package org.universAAL.AALapplication.food_shopping.service.uiclient;

import java.util.Locale;

import org.universAAL.AALapplication.food_shopping.service.uiclient.utils.Utils;
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

    public static String IMG_URL = "http://127.0.0.1:8181/resources/shopping/images/";
	private final static String window = "UIProvider#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SHOPPING_UI_NAMESPACE + window;
    static final String SUBMIT_SHOPPING = MY_UI_NAMESPACE + "shopping";
    static final String SUBMIT_REPOSITORY = MY_UI_NAMESPACE + "repository";
    static final String SUBMIT_HELP = MY_UI_NAMESPACE + "help";
    static final String SUBMIT_EXIT = MY_UI_NAMESPACE + "exit";

    public static Form mainDialog = null;
	public static String dialogID;
	private Device[] devices = null;
	
	protected UIProvider(ModuleContext context) {
		super(context);
	    String port = System.getProperty("org.osgi.service.http.port");  
		String ip = System.getProperty("resource.server.ip");
	 	if ((port!=null) && (ip!=null)) IMG_URL = "http://"+ ip +":" + port + "/resources/shopping/images/";
	 	else if (port!=null) IMG_URL = "http://127.0.0.1:" + port + "/resources/shopping/images/";
	 	else if (ip!=null) IMG_URL = "http://"+ ip +":8181/resources/shopping/images/";
	}

    public void communicationChannelBroken() { }

	private Form initMainDialog() {
		Form f = Form.newDialog("Food and Shopping",	new Resource());
		new SimpleOutput(f.getIOControls(), null, null,"Welcome to Food and Shopping service");
/*
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_shopList_small.png"), SUBMIT_SHOPPING);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodRepos_small.png"), SUBMIT_REPOSITORY);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_help_small.png"), SUBMIT_HELP);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_exit_small.png"), SUBMIT_EXIT);
*/
		new Submit(f.getSubmits(), new Label("Shopping List", null), SUBMIT_SHOPPING);
		new Submit(f.getSubmits(), new Label("Food Repository", null), SUBMIT_REPOSITORY);
		new Submit(f.getSubmits(), new Label("Exit", null), SUBMIT_EXIT);
		return f;
	}

	public void handleUIResponse(UIResponse uir) {
		if (uir != null) {
		    if (SUBMIT_EXIT.equals(uir.getSubmissionID()))
				return; 

		    if (SUBMIT_SHOPPING.equals(uir.getSubmissionID())) {
		    	this.showShoppingMainDialog();
			} 
		    if (SUBMIT_REPOSITORY.equals(uir.getSubmissionID())) {
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

	private void showShoppingMainDialog() {
		Utils.println(window + " starts Shopping Dialog");
		FoodManagementClient.shopping.startMainDialog();
	}

	private void showEnvironmentalMainDialog() {
		Utils.println(window + " starts Front Door Dialog");
		FoodManagementClient.repository.startMainDialog();
	}

	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

	@Override
	public void dialogAborted(String arg0, Resource arg1) {
		// TODO Auto-generated method stub
		
	}

}
