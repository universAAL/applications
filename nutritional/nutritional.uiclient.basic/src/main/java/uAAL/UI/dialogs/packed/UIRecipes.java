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
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;

/**
 * The UI class that builds the Recipes Form and handles its associated response
 * submits.
 * 
 * @author alfiva
 * 
 */
public class UIRecipes {
//
//    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
//	    + "UIRecipes"; //$NON-NLS-1$
//
//    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
//    static final String SUBMIT_TODAY = PREFIX + "today"; //$NON-NLS-1$
//    static final String SUBMIT_TOMORROW = PREFIX + "tomorrow"; //$NON-NLS-1$
//    static final String SUBMIT_FAVOURITES = PREFIX + "favourites"; //$NON-NLS-1$
//    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
//    static final PropertyPath PROP_PATH_REF1 = new PropertyPath(null,
//	    false, new String[] { USER_INPUT_REF1 });
//
//    /**
//     * Compose the form for this dialog.
//     * 
//     * @return The form.
//     */
//    public static Form getForm() {
//	Utils.println(Messages.getString("UIRecipes.0")); //$NON-NLS-1$
//	Form f = Form.newDialog(Messages.getString("UIRecipes.1"), new Resource()); //$NON-NLS-1$
//
//	InputField in = new InputField(f.getIOControls(), new Label(
//		Messages.getString("UIRecipes.2"), null), PROP_PATH_REF1, null, null); //$NON-NLS-1$
//	in.setAlertString(Messages.getString("UIRecipes.3")); //$NON-NLS-1$
//
//	new SimpleOutput(f.getIOControls(), null, null,
//		Messages.getString("UIRecipes.4")); //$NON-NLS-1$
//
//	// add an exit button for quitting the dialog
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIRecipes.5"), null), SUBMIT_TODAY); //$NON-NLS-1$
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIRecipes.6"), null), SUBMIT_TOMORROW); //$NON-NLS-1$
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIRecipes.7"), null), SUBMIT_FAVOURITES); //$NON-NLS-1$
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIRecipes.8"), null), SUBMIT_GOBACK); //$NON-NLS-1$
//
//	return f;
//    }
//
//    /**
//     * The main InterfaceProvider delegates calls to the handleUIResponse of the
//     * UICaller to this one if the prefix of the pressed submit ID matches this class´
//     * one (it´s one of its submits).
//     * 
//     * @param uir
//     *            The UI Response to handle.
//     */
//    public static void handleUIResponse(UIResponse uir) {
//	Utils.println("Received delegation of UI response in UIRecipes"); //$NON-NLS-1$
//	if (uir != null) {
//	    String id=uir.getSubmissionID();
//		if (SUBMIT_GOBACK.equals(id)) {
//			Utils.println("UIRecipes: submit go back"); //$NON-NLS-1$
//			UIRequest out = new UIRequest(SharedResources.testUser,
//				UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
//				PrivacyLevel.insensible);
//			SharedResources.uIProvider.sendUIRequest(out);
//			return;
//		}
//
//		if (SUBMIT_TODAY.equals(id)) {
//			Utils.println("UIRecipes: submit today"); //$NON-NLS-1$
//			UIRequest out = new UIRequest(SharedResources.testUser,
//				UIMenus.getForm(true), LevelRating.middle, Locale.ENGLISH,
//				PrivacyLevel.insensible);
//			SharedResources.uIProvider.sendUIRequest(out);
//			return;
//		}
//		if (SUBMIT_TOMORROW.equals(id)) {
//			Utils.println("UIRecipes: submit tomorrow"); //$NON-NLS-1$
//			UIRequest out = new UIRequest(SharedResources.testUser,
//				UIMenus.getForm(false), LevelRating.middle, Locale.ENGLISH,
//				PrivacyLevel.insensible);
//			SharedResources.uIProvider.sendUIRequest(out);
//			return;
//		}
//		if (SUBMIT_FAVOURITES.equals(id)) {
//			Utils.println("TODO  UIRecipes:  submit favs"); //$NON-NLS-1$
//			UIRequest out = new UIRequest(SharedResources.testUser,
//				Form.newMessage(Messages.getString("UIRecipes.9"), //$NON-NLS-1$
//					Messages.getString("UIRecipes.10")), //$NON-NLS-1$
//				LevelRating.middle, Locale.ENGLISH,
//				PrivacyLevel.insensible);
//			SharedResources.uIProvider.sendUIRequest(out);
//			return;
//			// TODO
//		}
//	}
//	Utils.println("Finished delegation of UI response in UIRecipes"); //$NON-NLS-1$
//    }
//
}
