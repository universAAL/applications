package uAAL.UI.dialogs.packed;

import java.util.Locale;

import nna.SharedResources;
import nna.utils.Utils;

import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;

/**
 * The UI class that builds the Help Form and hadnles its associated response
 * submits.
 * 
 * @author alfiva
 * 
 */
public class UIHelp {

    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
	    + "UIHelp"; //$NON-NLS-1$

    static final String SUBMIT_EXIT = PREFIX + "exit"; //$NON-NLS-1$
    static final String SUBMIT_SHOPPING = PREFIX + "shopping"; //$NON-NLS-1$
    static final String SUBMIT_PROFILE = PREFIX + "profile"; //$NON-NLS-1$
    static final String SUBMIT_TODAY = PREFIX + "today"; //$NON-NLS-1$
    static final String SUBMIT_WEEK = PREFIX + "week"; //$NON-NLS-1$
    static final String SUBMIT_TIPS = PREFIX + "tips"; //$NON-NLS-1$
    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
    static final PropertyPath PROP_PATH_REF1 = new PropertyPath(null,
	    false, new String[] { USER_INPUT_REF1 });

    /**
     * Compose the form for this dialog.
     * 
     * @return The form.
     */
    public static Form getForm() {
	Utils.println(Messages.getString("UIHelp.0")); //$NON-NLS-1$
	Form f = Form.newDialog(Messages.getString("UIHelp.1"), new Resource()); //$NON-NLS-1$
	f.setProperty("http://ontology.itaca.es/ClassicGUI.owl#layout", "vertical,left");// For Classic LaF
	
	Group groupIntro = new Group(f.getIOControls(), new Label(
		Messages.getString("UIHelp.2"), null), PROP_PATH_REF1, null, null); //$NON-NLS-1$
//	new SimpleOutput(groupIntro, null, null, Messages.getString("UIHelp.3")); //$NON-NLS-1$
	new SimpleOutput(
		groupIntro,
		null,
		null,
		Messages.getString("UIHelp.4")); //$NON-NLS-1$

	Group groupHow = new Group(f.getIOControls(), new Label(
		Messages.getString("UIHelp.5"), null), //$NON-NLS-1$
		PROP_PATH_REF1, null, null);
	new SimpleOutput(
		groupHow,
		null,
		null,
		Messages.getString("UIHelp.6")); //$NON-NLS-1$

	Group groupWhat = new Group(f.getIOControls(), new Label(
		Messages.getString("UIHelp.7"), null), //$NON-NLS-1$
		PROP_PATH_REF1, null, null);
	new SimpleOutput(
		groupWhat,
		null,
		null,
		Messages.getString("UIHelp.8")); //$NON-NLS-1$

	Group groupHowNutri = new Group(f.getIOControls(), new Label(
		Messages.getString("UIHelp.9"), null), //$NON-NLS-1$
		PROP_PATH_REF1, null, null);
	new SimpleOutput(
		groupHowNutri,
		null,
		null,
		Messages.getString("UIHelp.10")); //$NON-NLS-1$

	Group groupIdont = new Group(f.getIOControls(), new Label(
		Messages.getString("UIHelp.11"), null), //$NON-NLS-1$
		PROP_PATH_REF1, null, null);
	new SimpleOutput(
		groupIdont,
		null,
		null,
		Messages.getString("UIHelp.12")); //$NON-NLS-1$

	// add a go back button for quitting the dialog

	new Submit(f.getSubmits(), new Label(Messages.getString("UIMenus.8"), null), SUBMIT_TODAY); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(Messages.getString("UIMenus.10"), null), SUBMIT_WEEK); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(Messages.getString("UIMenus.11"), null), SUBMIT_TIPS); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.6"), null), //$NON-NLS-1$
		SUBMIT_SHOPPING);
	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.7"), null), SUBMIT_PROFILE); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(Messages.getString("UIHelp.13"), null), SUBMIT_GOBACK); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.9"), null), SUBMIT_EXIT); //$NON-NLS-1$
	return f;
    }

    /**
     * The main InterfaceProvider delegates calls to the handleUIResponse of the
     * UICaller to this one if the prefix of the pressed submit ID matches this class´
     * one (it´s one of its submits).
     * 
     * @param uir
     *            The UI Response to handle.
     */
    public static void handleUIResponse(UIResponse uir) {
	Utils.println("Received delegation of UI response in UIHelp"); //$NON-NLS-1$
	if (uir != null) {
	    String id = uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		Utils.println("UIHelp: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			// UIMain.getForm(), LevelRating.middle,
			UIMenus.getForm(true), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_TODAY.equals(id)) {
		Utils.println("UIMEnus: submit today"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIMenus.getForm(true), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_WEEK.equals(id)) {
		Utils.println(" TODO UIMenus: submit week"); //$NON-NLS-1$
		UIRequest out = new UIRequest(
			SharedResources.testUser,
			Form.newMessage(Messages.getString("UIMenus.13"), //$NON-NLS-1$
				Messages.getString("UIMenus.14")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_TIPS.equals(id)) {
		Utils.println(" TODO UImenus: submit tips"); //$NON-NLS-1$
		UIRequest out = new UIRequest(
			SharedResources.testUser,
			Form.newMessage(Messages.getString("UIMenus.13"), //$NON-NLS-1$
				Messages.getString("UIMenus.14")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_SHOPPING.equals(id)) {
		Utils.println("UIMain submit shopping"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIShopping.getForm(), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_PROFILE.equals(id)) {
		Utils.println("UIMain submit profile"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIProfile.getForm(), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_EXIT.equals(id))
		return; // Cancel Dialog, go back to main dialog
	}
	Utils.println("Finished delegation of UI response in UIHelp"); //$NON-NLS-1$
    }

}
