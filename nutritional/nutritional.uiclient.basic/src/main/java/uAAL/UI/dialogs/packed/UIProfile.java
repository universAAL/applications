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
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.UserIDs;

import uAAL.services.MedicationConsumer;

/**
 * The UI class that builds the Profile Form and handles its associated response
 * submits.
 * 
 * @author alfiva
 * 
 */
public class UIProfile {

    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
	    + "UIProfile"; //$NON-NLS-1$

    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
    static final String SUBMIT_MYPROFILE = PREFIX + "myprofile"; //$NON-NLS-1$
    static final String SUBMIT_QUESTIONNAIRES = PREFIX + "questionnaires"; //$NON-NLS-1$
    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
    static final String USER_INPUT_REF2 = PREFIX + "ref2"; //$NON-NLS-1$
    static final String USER_INPUT_REF3 = PREFIX + "ref3"; //$NON-NLS-1$
    static final PropertyPath PROP_PATH_REF1 = new PropertyPath(null,
	    false, new String[] { USER_INPUT_REF1 });
    static final PropertyPath PROP_PATH_REF2 = new PropertyPath(null,
	    false, new String[] { USER_INPUT_REF2 });
    static final PropertyPath PROP_PATH_REF3 = new PropertyPath(null,
	    false, new String[] { USER_INPUT_REF3 });

    /**
     * Compose the form for this dialog.
     * 
     * @return The form.
     */
    public static Form getForm() {
	Utils.println(Messages.getString("UIProfile.1")); //$NON-NLS-1$
	Form f;
	
	MedicationConsumer medConsumer = new MedicationConsumer(
		SharedResources.moduleContext);
	Precaution p = medConsumer.requestDetails(UserIDs.getSaiedUser());

	if (p != null) {
	    f = Form.newDialog(Messages.getString("UIProfile.2"), new Resource()); //$NON-NLS-1$
		f.setProperty("http://ontology.itaca.es/ClassicGUI.owl#layout", "constant,vertical,left"); //$NON-NLS-1$ //$NON-NLS-2$
//	    Group mealtimes = new Group(f.getIOControls(), new Label(
//		    "Meal times:", null), PROP_PATH_REF1, null, null);

//	    // Meal times // breakfast
//	    try {
//		String time = "08:00";
//		Utils.println("UIProfile: Time is: " + time);
//		String value = Messages.Profile_Prefer_Breakfast + " " + time;
//		new SimpleOutput(mealtimes, new Label("Time:", null), null,
//			value);
//	    } catch (Exception e) {
//		e.printStackTrace();
//	    }

	    Group medications = new Group(f.getIOControls(), new Label(
		    Messages.getString("UIProfile.3"), null), PROP_PATH_REF1, null, null); //$NON-NLS-1$

	    System.out.println("Tester: sideeffect: " + p.getSideEffect() //$NON-NLS-1$
		    + " incompliance: " + p.getIncompliance()); //$NON-NLS-1$
	    
	    Group sideeffects = new Group(medications, new Label(
		    Messages.getString("UIProfile.4"), null), PROP_PATH_REF2, null, null); //$NON-NLS-1$
	    new SimpleOutput(sideeffects, new Label("", null), null, //$NON-NLS-1$
		    p.getSideEffect());
	    
	    String incompliance = Messages.getString("UIProfile.5"); //$NON-NLS-1$
	    if (p.getIncompliance() != null)
		incompliance = p.getIncompliance();
	    
	    Group incompl = new Group(medications, new Label(
		    Messages.getString("UIProfile.6"), null), PROP_PATH_REF3, null, null); //$NON-NLS-1$
	    new SimpleOutput(incompl, new Label("", null), //$NON-NLS-1$
		    null, incompliance);
	    // add an exit button for quitting the dialog
	    new Submit(f.getSubmits(), new Label(Messages.getString("UIProfile.7"), null), //$NON-NLS-1$
		    SUBMIT_MYPROFILE);
	    new Submit(f.getSubmits(), new Label(Messages.getString("UIProfile.8"), null), //$NON-NLS-1$
		    SUBMIT_QUESTIONNAIRES);
	    new Submit(f.getSubmits(), new Label(Messages.getString("UIProfile.9"), null), //$NON-NLS-1$
		    SUBMIT_GOBACK);
	} else {
	    f = Form.newMessage(Messages.getString("UIProfile.10"), //$NON-NLS-1$
		    Messages.getString("UIProfile.11")); //$NON-NLS-1$
	}

	return f;
    }

    /**
     * The main InterfaceProvider delegates calls to the handleUIResponse of the
     * UICaller to this one if the prefix of the pressed submit ID matches this
     * class´ one (it´s one of its submits).
     * 
     * @param uir
     *            The UI Response to handle.
     */
    public static void handleUIResponse(UIResponse uir) {
	Utils.println("Received delegation of UI response in UIProfile"); //$NON-NLS-1$
	if (uir != null) {
	    String id = uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		Utils.println("UIProfile: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
//			UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
			UIMenus.getForm(true), LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_MYPROFILE.equals(id)) {
		Utils.println(" TODO UIProfile: submit myprofile"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			Form.newMessage(Messages.getString("UIProfile.12"), //$NON-NLS-1$
				Messages.getString("UIProfile.13")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_QUESTIONNAIRES.equals(id)) {
		Utils.println(" TODO UIProfile: submit quest"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			Form.newMessage(Messages.getString("UIProfile.12"), //$NON-NLS-1$
				Messages.getString("UIProfile.13")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	}
	Utils.println("Finished delegation of UI response in UIProfile"); //$NON-NLS-1$
    }

}
