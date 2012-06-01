package uAAL.UI.dialogs.profile;

import java.util.Locale;

import na.oasisUtils.profile.ProfileConnector;
import na.utils.lang.Messages;
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
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.UserIDs;

import uAAL.UI.dialogs.CustomUICaller;
import uAAL.services.MedicationConsumer;




/**
 * @author hecgamar
 * 
 */
public class UIProfile extends CustomUICaller {

	private final static String window = "UIProfile#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
			+ window;

	static final String SUBMIT_GOBACK = MY_UI_NAMESPACE + "back";
	static final String SUBMIT_MYPROFILE = MY_UI_NAMESPACE + "myprofile";
	static final String SUBMIT_QUESTIONNAIRES = MY_UI_NAMESPACE + "questionnaires";
	
	static final String USER_INPUT_SELECTED_LAMP = MY_UI_NAMESPACE
			+ "selectedLamp";

	static final PropertyPath PROP_PATH_SCALE_VALUE = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP, SUBMIT_GOBACK });
	static final PropertyPath PROP_PATH_SELECTED_LAMP = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP });

	private Form mainDialog = null;
	private CustomUICaller parentUICaller = null;

	public UIProfile(ModuleContext context) {
		super(context);
	}

	public void handleUIResponse(UIResponse uir) {
		Utils.println(window + " Recibo ID: " + uir.getSubmissionID());
		if (uir != null) {
			if (SUBMIT_GOBACK.equals(uir.getSubmissionID())) {
				Utils.println(window+"  go back to previous screen");
//				this.parentUICaller.callMainForm();
				this.backToMainMenusDialog(this.getContext());
				return; // Cancel Dialog, go back to main dialog
			}

			if (SUBMIT_MYPROFILE.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana Profile!!");
				// this.startMenuMainDialog();
				return;
			}
			if (SUBMIT_QUESTIONNAIRES.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana Questionnaires!!");
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
//		ProfileConnector.getInstance().downloadProfileFromServer();
		Form f = Form.newDialog("My nutritional Profile", new Resource());

		
		Group mealtimes = new Group(f.getIOControls(), new Label(
				"Meal times:", null), PROP_PATH_SCALE_VALUE, null, null);
		
		// Meal times // breakfast
		try {
//			String time = ProfileConnector.getInstance().getBreakfastTime();
			String time = "08:00";
			Utils.println("Time is: "+time);
			String value = Messages.Profile_Prefer_Breakfast +" "+ time;
			SimpleOutput label_description = new SimpleOutput(
					mealtimes, new Label("Time:", null), null,
					value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Group medications = new Group(f.getIOControls(), new Label(
				"Medications:", null), PROP_PATH_SCALE_VALUE, null, null);
		
		
		MedicationConsumer medConsumer = new MedicationConsumer(this.getContext());
		Precaution p = medConsumer.requestDetails(UserIDs.getSaiedUser());
		System.out.println("Tester: sideeffect: "+ p.getSideEffect()+ " incompliance: "+p.getIncompliance());
		SimpleOutput label_sideEffect = new SimpleOutput(
				medications, new Label("SideEffect:", null), null,
				p.getSideEffect());
		String incompliance = "none";
		if (p.getIncompliance()!=null)
			incompliance = p.getIncompliance();
		SimpleOutput label_Incompliance = new SimpleOutput(
				medications, new Label("Incompliance:", null), null,
				incompliance);
		
		/*
		
		// Meal times // lunch
		try {
			String time = ProfileConnector.getInstance().getLunchTime();
			Utils.println("Time is: "+time);
			String value = Messages.Profile_Prefer_Lunch +" "+ time;
			SimpleOutput label_description = new SimpleOutput(
					mealtimes, new Label("Dish:", null), null,
					value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Meal times // dinner
		try {
			String time = ProfileConnector.getInstance().getDinnerTime();
			Utils.println("Time is: "+time);
			String value = Messages.Profile_Prefer_Dinner +" "+ time;
			SimpleOutput label_description = new SimpleOutput(
					mealtimes, new Label("Dish:", null), null,
					value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		// add an exit button for quitting the dialog
		new Submit(f.getSubmits(), new Label("My profile", null), SUBMIT_MYPROFILE);
		new Submit(f.getSubmits(), new Label("Questionnaires", null), SUBMIT_QUESTIONNAIRES);
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
