package org.universAAL.AALapplication.nutritional.dialogs;

import java.util.Locale;

import org.universAAL.AALapplication.nutritional.SharedResources;
import org.universAAL.AALapplication.nutritional.services.MedicationConsumer;
import org.universAAL.AALapplication.nutritional.utils.Utils;
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
import org.universAAL.ontology.recommendations.HorizontalAlignment;
import org.universAAL.ontology.recommendations.HorizontalLayout;
import org.universAAL.ontology.recommendations.VerticalLayout;

/**
 * The UI class that builds the Profile Form and handles its associated response
 * submits.
 * 
 * @author alfiva
 * 
 */
public class UIMedication {

    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
	    + "UIMedication"; //$NON-NLS-1$

    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
    static final String USER_INPUT_REF2 = PREFIX + "ref2"; //$NON-NLS-1$
    static final String USER_INPUT_REF3 = PREFIX + "ref3"; //$NON-NLS-1$

    static final PropertyPath PROP_PATH_REF1 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF1 });
    static final PropertyPath PROP_PATH_REF2 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF2 });
    static final PropertyPath PROP_PATH_REF3 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF3 });

    /**
     * Compose the form for this dialog.
     * 
     * @return The form.
     */
    public static Form getForm() {
	Utils.println(Messages.getString("UIProfile.1")); //$NON-NLS-1$
	Form f = null;
//
//	MedicationConsumer medConsumer = new MedicationConsumer(
//		SharedResources.getMContext());
//	Precaution p = medConsumer.requestDetails(UserIDs.getSaiedUser());
//
//	if (p != null) {
//	    f = Form.newDialog(
//		    Messages.getString("UIProfile.2"), new Resource()); //$NON-NLS-1$
//	    f.getIOControls().addAppearanceRecommendation(new VerticalLayout());
//	    f.getIOControls().addAppearanceRecommendation(HorizontalAlignment.left);
////	    f.setProperty(
////		    "http://ontology.itaca.es/ClassicGUI.owl#layout", "constant,vertical,left"); //$NON-NLS-1$ //$NON-NLS-2$
//
//	    Group medications = new Group(
//		    f.getIOControls(),
//		    new Label(Messages.getString("UIProfile.3"), null), PROP_PATH_REF1, null, null); //$NON-NLS-1$
//	    medications.addAppearanceRecommendation(new VerticalLayout());
//	    medications.addAppearanceRecommendation(HorizontalAlignment.left);
//
//	    Utils.println("Tester: sideeffect: " + p.getSideEffect() //$NON-NLS-1$
//		    + " incompliance: " + p.getIncompliance()); //$NON-NLS-1$
//
//	    Group sideeffects = new Group(
//		    medications,
//		    new Label(Messages.getString("UIProfile.4"), null), PROP_PATH_REF2, null, null); //$NON-NLS-1$
//	    sideeffects.addAppearanceRecommendation(new VerticalLayout());
//	    sideeffects.addAppearanceRecommendation(HorizontalAlignment.left);
//	    new SimpleOutput(sideeffects, new Label("", null), null, //$NON-NLS-1$
//		    p.getSideEffect());
//
//	    String incompliance = Messages.getString("UIProfile.5"); //$NON-NLS-1$
//	    if (p.getIncompliance() != null)
//		incompliance = p.getIncompliance();
//
//	    Group incompl = new Group(
//		    medications,
//		    new Label(Messages.getString("UIProfile.6"), null), PROP_PATH_REF3, null, null); //$NON-NLS-1$
//	    incompl.addAppearanceRecommendation(new VerticalLayout());
//	    incompl.addAppearanceRecommendation(HorizontalAlignment.left);
//	    new SimpleOutput(incompl, new Label("", null), //$NON-NLS-1$
//		    null, incompliance);
//	    // add an exit button for quitting the dialog
//	    new Submit(f.getSubmits(), new Label(
//		    Messages.getString("UIProfile.9"), null), //$NON-NLS-1$
//		    SUBMIT_GOBACK);
//	} else {
//	    f = Form.newMessage(Messages.getString("UIProfile.10"), //$NON-NLS-1$
//		    Messages.getString("UIProfile.11")); //$NON-NLS-1$
//	}

	return f;
    }

    /**
     * The main InterfaceProvider delegates calls to the handleUIResponse of the
     * UICaller to this one if the prefix of the pressed submit ID matches this
     * class� one (it�s one of its submits).
     * 
     * @param uir
     *            The UI Response to handle.
     */
    public static void handleUIResponse(UIResponse uir) {
	Utils.println("Received delegation of UI response in UIMedication"); //$NON-NLS-1$
	if (uir != null) {
	    String id = uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		Utils.println("UIMedication: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.user,
			UIProfile.getForm(), LevelRating.middle,
			Locale.getDefault(), PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	}
	Utils.println("Finished delegation of UI response in UIMedication"); //$NON-NLS-1$
    }

}
