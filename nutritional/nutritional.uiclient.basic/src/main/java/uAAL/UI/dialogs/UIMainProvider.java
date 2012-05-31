package uAAL.UI.dialogs;

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
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.ui.rdf.TextArea;

import uAAL.UI.dialogs.help.UIHelp;
import uAAL.UI.dialogs.menus.UIMenus;
import uAAL.UI.dialogs.profile.UIProfile;
import uAAL.UI.dialogs.recipes.UIRecipes;
import uAAL.UI.dialogs.shopping.UIShopping;

/**
 * @author hecgamar
 * 
 */
public class UIMainProvider extends CustomUICaller {

	private final static String window = "UIProvider#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
			+ window;

	static final String SUBMIT_EXIT = MY_UI_NAMESPACE + "exit";
	static final String SUBMIT_MENUS = MY_UI_NAMESPACE + "menus";
	static final String SUBMIT_RECIPES = MY_UI_NAMESPACE + "recipes";
	static final String SUBMIT_SHOPPING = MY_UI_NAMESPACE + "shopping";
	static final String SUBMIT_PROFILE = MY_UI_NAMESPACE + "profile";
	static final String SUBMIT_HELP = MY_UI_NAMESPACE + "help";
	static final String SUBMIT_POP = MY_UI_NAMESPACE + "pop";

	static final String SUBMIT_GOBACK = MY_UI_NAMESPACE + "back";
	static final String USER_INPUT_SELECTED_LAMP3 = MY_UI_NAMESPACE
			+ "selectedLamp3";
	static final PropertyPath PROP_PATH_SCALE_VALUE = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP3, SUBMIT_GOBACK });

	private ModuleContext myContext;
	private Form mainDialog = null;

	public UIMainProvider(ModuleContext context) {
		super(context);
		this.myContext = context;
		/*
		 * // initialize the list of devices devices =
		 * NutritionalConsumer.getControlledLamps();
		 * 
		 * // make sure that all of them have a label appropriate for creating
		 * form // controls later for (int i = 0; i < devices.length; i++) {
		 * String label = devices[i].getResourceLabel(); if (label == null) { //
		 * in this case create something like the following: Light Bulb //
		 * "lamp1" in Room "loc1" LightType lt = devices[i].getLightType();
		 * String type = StringUtils.deriveLabel((lt == null) ? devices[i]
		 * .getClassURI() : lt.getURI()); label =
		 * devices[i].getOrConstructLabel(type); Location l =
		 * devices[i].getLocation(); if (l != null) label += " in " +
		 * l.getOrConstructLabel(null); devices[i].setResourceLabel(label); } }
		 */
	}

	private Form initMainDialog() {
		Form f = Form.newDialog("Nutritional Advisor", new Resource());

		SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null,
				"Welcome to the Nutritional Advisor service.");
		SimpleOutput message = new SimpleOutput(
				f.getIOControls(),
				null,
				null,
				"Choose an option from the main menu to continue or press Exit to go back to the main screen");

		// new SubdialogTrigger(f.getIOControls(), new Label("Pop up", null),
		// SUBMISSION_POP);
//		URL picURL = this.getClass().getResource("file://C:\\Projects\\UniversAAL\\Tools\\workspaces_3.7.1\\base\\rundir\\confadmin\\ui.handler.gui.swing\\resources\\recipe91.jpg");
//		ImageIcon j = new ImageIcon(picURL);
//		new MediaObject(f.getIOControls(), new Label("Image", null), "image/jpeg", "file://C:\\Projects\\UniversAAL\\Tools\\workspaces_3.7.1\\base\\rundir\\confadmin\\ui.handler.gui.swing\\resources\\recipe91.jpg"); //
		new MediaObject(f.getIOControls(), new Label("Image", null), "image/jpeg", "recipe91.jpg"); //
//		new MediaObject(f.getIOControls(),new Label("","recepie91.jpg"),null);
		// add an exit button for quitting the dialog
		TextArea ta = new TextArea(f.getIOControls(), new Label( "Title of textarea", null),
				PROP_PATH_SCALE_VALUE	, null, "Some text inside the textarea");
		
		new Submit(f.getSubmits(), new Label("Menus", null), SUBMIT_MENUS);
		new Submit(f.getSubmits(), new Label("Recipes", null), SUBMIT_RECIPES);
		new Submit(f.getSubmits(), new Label("Shopping List", null),
				SUBMIT_SHOPPING);
		new Submit(f.getSubmits(), new Label("Profile", null), SUBMIT_PROFILE);
		new Submit(f.getSubmits(), new Label("Help", null), SUBMIT_HELP);
		new Submit(f.getSubmits(), new Label("Exit", null), SUBMIT_EXIT);

		return f;
	}

	public void handleUIResponse(UIResponse uir) {
		Utils.println(window + " Recibo ID: " + uir.getSubmissionID());
		if (uir != null) {
			if (SUBMIT_EXIT.equals(uir.getSubmissionID()))
				return; // Cancel Dialog, go back to main dialog

			if (SUBMIT_MENUS.equals(uir.getSubmissionID())) {
				Utils.println(window
						+ " Lanzo petición al bus. Quiero la ventana de menus!!");
				this.showMainDialog();
				// this.startRecipeDetailDialog(this);
			}
			if (SUBMIT_RECIPES.equals(uir.getSubmissionID())) {
				Utils.println(window
						+ " Lanzo petición al bus. Quiero la ventana de recetas!!");
				this.showRecipeMainDialog();
			}
			if (SUBMIT_SHOPPING.equals(uir.getSubmissionID())) {
				Utils.println(window
						+ " Lanzo petición al bus. Quiero la ventana de shopping!!");
				this.showShoppingMainDialog();
			}
			if (SUBMIT_PROFILE.equals(uir.getSubmissionID())) {
				Utils.println(window
						+ " Lanzo petición al bus. Quiero la ventana de profile!!");
				this.showProfileMainDialog();
			}
			if (SUBMIT_HELP.equals(uir.getSubmissionID())) {
				Utils.println(window
						+ " Lanzo petición al bus. Quiero la ventana de help!!");
				this.showHelpMainDialog();
			}
			if (SUBMIT_POP.equals(uir.getSubmissionID())) {
				Utils.println(window
						+ " Lanzo petición al bus. Quiero mostrar un popup!!");
				this.showProfileMainDialog();
			}
		}
		Utils.println(window + " Continues");
		// this.startMainDialog();
	}

	public void startMainDialog() {
		Utils.println(window + " startMainDialog");
		if (mainDialog == null)
			mainDialog = initMainDialog();
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	/*
	private void startRecipeDetailDialog(CustomUICaller parentForm) {
		Utils.println(window + " startRecipeDetailDialog");
		UIRecipe_Detail r = new UIRecipe_Detail(this.myContext);
		r.startMainDialog(this);
	}
	*/

	private void showMainDialog() {
		Utils.println(window + " startMenuMainDialog");
		UIMenus menus = new UIMenus(this.myContext);
		menus.startMainDialog(this);
	}

	private void showRecipeMainDialog() {
		Utils.println(window + " startRecipeMainDialog");
		UIRecipes recipes = new UIRecipes(this.myContext);
		recipes.startMainDialog(this);
	}

	private void showShoppingMainDialog() {
		Utils.println(window + " startShoppingMainDialog");
		UIShopping shopping = new UIShopping(this.myContext);
		shopping.startMainDialog(this);
	}

	private void showProfileMainDialog() {
		Utils.println(window + " startProfileMainDialog");
		UIProfile profile = new UIProfile(this.myContext);
		profile.startMainDialog(this);
	}
	
	private void showHelpMainDialog() {
		Utils.println(window + " startHelpMainDialog");
		UIHelp help = new UIHelp(this.myContext);
		help.startMainDialog(this);
	}
	
	public void communicationChannelBroken() {
		Utils.println(window + " Nutri: communicationChannelBroken");
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
		Utils.println(window + " dialogAborted: " + dialogID);
	}

	@Override
	public void callMainForm() {
		Utils.println(window + " showing my MainForm!");
		this.startMainDialog();
	}

	@Override
	public Form getMainForm() {
		return this.mainDialog;
	}

}
