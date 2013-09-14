package org.universAAL.AALapplication.nutritional.dialogs;

import java.util.Locale;

import org.universAAL.AALapplication.nutritional.SharedResources;
import org.universAAL.AALapplication.nutritional.utils.Utils;
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
import org.universAAL.ontology.recommendations.HorizontalAlignment;
import org.universAAL.ontology.recommendations.VerticalLayout;

/**
 * The UI class that builds the Shopping Form and handles its associated
 * response submits.
 * 
 * @author alfiva
 * 
 */
public class UIShopping {

    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
	    + "UIShopping"; //$NON-NLS-1$

    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
    static final String SUBMIT_TODAY = PREFIX + "today"; //$NON-NLS-1$
    static final String SUBMIT_TOMORROW = PREFIX + "tomorrow"; //$NON-NLS-1$
    static final String SUBMIT_FAVOURITES = PREFIX + "favourites"; //$NON-NLS-1$
    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
    static final String USER_INPUT_REF2 = PREFIX + "ref2"; //$NON-NLS-1$
    static final PropertyPath PROP_PATH_REF1 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF1 });
    static final PropertyPath PROP_PATH_REF2 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF2 });

    /**
     * Compose the form for this dialog.
     * 
     * @return The form.
     */
    public static Form getForm() {
	Utils.println("UI Shopping Form"); //$NON-NLS-1$
	Form f = Form.newDialog(
		Messages.getString("UIShopping.0"), new Resource()); //$NON-NLS-1$
	f.addAppearanceRecommendation(new VerticalLayout());
	f.addAppearanceRecommendation(HorizontalAlignment.left);
//	f.setProperty("http://ontology.itaca.es/ClassicGUI.owl#layout",
//		"vertical,left");
	InputField in = new InputField(
		f.getIOControls(),
		new Label(Messages.getString("UIShopping.1"), null), PROP_PATH_REF1, null, //$NON-NLS-1$
		null);
	// in.setAlertString("Expected: a number between 0 and 100!");

	TextArea ta = new TextArea(
		f.getIOControls(),
		new Label(Messages.getString("UIShopping.2"), null), PROP_PATH_REF2, null, null); //$NON-NLS-1$
	// ta.setAlertString("Aqui falta algo");
	// ta.setHelpString("Ayuda para el text");
	new SimpleOutput(f.getIOControls(), null, null,
		Messages.getString("UIShopping.3")); //$NON-NLS-1$

	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIShopping.4"), null), SUBMIT_GOBACK); //$NON-NLS-1$

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
	Utils.println("Received delegation of UI response in UIShopping"); //$NON-NLS-1$
	if (uir != null) {
	    String id = uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		Utils.println("UIShopping: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.user,
			// UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
			UIMenus.getForm(true), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }

	    if (SUBMIT_TODAY.equals(id)) {
		Utils.println("TODO  UIShopping: submit today"); //$NON-NLS-1$
		UIRequest out = new UIRequest(
			SharedResources.user,
			Form.newMessage(Messages.getString("UIShopping.5"), //$NON-NLS-1$
				Messages.getString("UIShopping.6")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_TOMORROW.equals(id)) {
		Utils.println("TODO  UIShopping: submit tomorrrrow"); //$NON-NLS-1$
		UIRequest out = new UIRequest(
			SharedResources.user,
			Form.newMessage(Messages.getString("UIShopping.5"), //$NON-NLS-1$
				Messages.getString("UIShopping.6")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_FAVOURITES.equals(id)) {
		Utils.println("TODO UIShopping: submit favs"); //$NON-NLS-1$
		UIRequest out = new UIRequest(
			SharedResources.user,
			Form.newMessage(Messages.getString("UIShopping.5"), //$NON-NLS-1$
				Messages.getString("UIShopping.6")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	}
	Utils.println("Finished delegation of UI response in UIShopping"); //$NON-NLS-1$
    }

}
