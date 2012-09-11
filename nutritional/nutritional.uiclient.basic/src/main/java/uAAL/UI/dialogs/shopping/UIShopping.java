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
package uAAL.UI.dialogs.shopping;

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
public class UIShopping extends CustomUICaller {

	private final static String window = "UIShopping#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
			+ window;

	static final String SUBMIT_GOBACK = MY_UI_NAMESPACE + "back";
	static final String SUBMIT_TODAY = MY_UI_NAMESPACE + "today";
	static final String SUBMIT_TOMORROW = MY_UI_NAMESPACE + "tomorrow";
	static final String SUBMIT_FAVOURITES = MY_UI_NAMESPACE + "favourites";
	
	static final String USER_INPUT_SELECTED_LAMP = MY_UI_NAMESPACE
			+ "selectedLamp";

	static final PropertyPath PROP_PATH_SCALE_VALUE = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP, SUBMIT_GOBACK });
	static final PropertyPath PROP_PATH_SELECTED_LAMP = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP });

	private Form mainDialog = null;
	private CustomUICaller parentUICaller = null;

	public UIShopping(ModuleContext context) {
		super(context);
	}

	public void handleUIResponse(UIResponse uir) {
		Utils.println(window + " Recibo ID: " + uir.getSubmissionID());
		if (uir != null) {
			if (SUBMIT_GOBACK.equals(uir.getSubmissionID())) {
				Utils.println(window+"  go back to previous screen");
				this.parentUICaller.callMainForm();
				return; // Cancel Dialog, go back to main dialog
			}

			if (SUBMIT_TODAY.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana TODAY!!");
				// this.startMenuMainDialog();
				return;
			}
			if (SUBMIT_TOMORROW.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana TOMORROW!!");
				// this.startMenuMainDialog();
				return;
			}
			if (SUBMIT_FAVOURITES.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana FAVOURITES!!");
				// this.startMenuMainDialog();
				return;
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
		Form f = Form.newDialog("My shopping list", new Resource());

		InputField in = new InputField(f.getIOControls(), new Label(
				"Mi lista de la compra:", null), PROP_PATH_SCALE_VALUE, null, null);
		in.setAlertString("Expected: a number between 0 and 100!");

		TextArea ta = new TextArea(f.getIOControls(), new Label(
				"Recetitas buenas:", null), PROP_PATH_SCALE_VALUE, null, null);
		ta.setAlertString("Aqui falta algo");
		ta.setHelpString("Ayuda para el text");
		SimpleOutput so = new SimpleOutput(f.getIOControls(), null, null,
				"Comprar y comprar!");

		// add an exit button for quitting the dialog
//		new Submit(f.getSubmits(), new Label("Today", null), SUBMIT_TODAY);
//		new Submit(f.getSubmits(), new Label("Tomorrow", null), SUBMIT_TOMORROW);
//		new Submit(f.getSubmits(), new Label("Week", null), SUBMIT_FAVOURITES);
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
		//TODO
//		Utils.println(window+ " showing my MainForm!");
//		this.startMainDialog();
	}
	
	@Override
	public Form getMainForm() {
		return this.mainDialog;
	}
}
