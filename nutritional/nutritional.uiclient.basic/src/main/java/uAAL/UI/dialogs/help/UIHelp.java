/*
	Copyright 2011-2012 Itaca-TSB, http://www.tsb.upv.es
	Tecnologías para la Salud y el Bienestar
	
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
package uAAL.UI.dialogs.help;

import java.util.Locale;

import nna.SharedResources;
import nna.utils.Utils;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.ui.rdf.TextArea;

import uAAL.UI.dialogs.CustomUICaller;




/**
 * @author hecgamar
 * 
 */
public class UIHelp extends CustomUICaller {

	private final static String window = "UIHelp#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
			+ window;

	static final String SUBMIT_GOBACK = MY_UI_NAMESPACE + "back";
	
	static final String USER_INPUT_SELECTED_LAMP = MY_UI_NAMESPACE
			+ "selectedLamp";

	static final PropertyPath PROP_PATH_SCALE_VALUE = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP, SUBMIT_GOBACK });
	static final PropertyPath PROP_PATH_SELECTED_LAMP = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP });

	private Form mainDialog = null;
	private CustomUICaller parentUICaller = null;

	public UIHelp(ModuleContext context) {
		super(context);
	}

	public void handleUIResponse(UIResponse uir) {
		Utils.println(window + " Recibo ID: " + uir.getSubmissionID());
		if (uir != null) {
			if (SUBMIT_GOBACK.equals(uir.getSubmissionID())) {
				Utils.println(window+"  go back to previous screen");
				this.parentUICaller.callMainForm();
				return; // Cancel Dialog, go back to main dialog
			} else {
				Utils.println("Unknown uir!");
			}

			
		}
		Utils.println(window + " Continues");
	}

	public void startMainDialog(CustomUICaller parentForm) {
		Utils.println(window + " startMainDialog");
		this.parentUICaller = parentForm;
		if (mainDialog == null)
			mainDialog = initMainDialog();
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form initMainDialog() {
		Utils.println(window + " createMenusMainDialog");
		Form f = Form.newDialog("Help", new Resource());

		Group groupIntro = new Group(f.getIOControls(), new Label(
				"Introduction", null), PROP_PATH_SCALE_VALUE, null, null);
		new SimpleOutput(groupIntro, null, null, "What is Nutritional Advisor?");
		new SimpleOutput(groupIntro, null, null, "Nutritional Advisor is an application for your kitchen which will help you to lead a healthy and autonomous life.");

		Group groupHow = new Group(f.getIOControls(), new Label(
				"How can Nutritional Advisor help me?", null), PROP_PATH_SCALE_VALUE, null, null);
		new SimpleOutput(groupHow, null, null, "The Nutritional Advisor will provide you with Nutritional Menus and Nutritional Tips for the week. It will also create a Shopping List according to your menu and will give you assistance while cooking with easy instructions.\n\nAll the information we give to you (menus, tips, etc) is validated by a nutritionist and adapted to your nutritional profile.");

		Group groupWhat = new Group(f.getIOControls(), new Label(
				"What is the Nutritional Profile", null), PROP_PATH_SCALE_VALUE, null, null);
		new SimpleOutput(groupWhat, null, null, "The Nutritional Profile contains all the information related to your nutrition: Your eating habits, your diet, your likes and dislikes, your food intolerances, any illness you may have had that could affect your health, your activity, etc. This information is really important, since it will help the nutritionist to suggest you not only the best food and habits for your health, but also those recipes and foods you like the most.");

		Group groupHowNutri = new Group(f.getIOControls(), new Label(
				"How does my nutritionist know my Nutritional Profile?", null), PROP_PATH_SCALE_VALUE, null, null);
		new SimpleOutput(groupHowNutri, null, null, "He will ask you this information through two ways: either when you go to visit in his consultation or with easy questionnaires that you can complete through this application.");

		Group groupIdont = new Group(f.getIOControls(), new Label(
				"I do not understand the buttons? What do they mean?", null), PROP_PATH_SCALE_VALUE, null, null);
		new SimpleOutput(groupIdont, null, null, "Nutritional Menus: Gives you the menus for today, tomorrow, the week\n\nRecipes : Gives you the recipes of the meals with the ingredients and the instructions.\n\nShopping List: Creates a shopping list with the food you need to buy.\n\nMy Nutritional Profile: Here the information of your nutritiona profile is shown and gathered through questionnaires.");

		
		// add a go back button for quitting the dialog
		new Submit(f.getSubmits(), new Label("Go back!", null), SUBMIT_GOBACK);

		return f;
	}

	public void communicationChannelBroken() {
		System.out.println(window + " communicationChannelBroken");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.ui.UICaller#dialogAborted(java.lang.String)
	 */
	@Override
	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

	@Override
	public void callMainForm() {
		Utils.println(window+ " showing my MainForm!");
		 this.startMainDialog(this.parentUICaller);
	}
	
	@Override
	public Form getMainForm() {
		return this.mainDialog;
	}
}
