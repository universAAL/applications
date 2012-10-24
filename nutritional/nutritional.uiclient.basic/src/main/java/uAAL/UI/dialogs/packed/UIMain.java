package uAAL.UI.dialogs.packed;

import java.util.Locale;

import nna.SharedResources;
import nna.utils.Utils;

import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;

/**
 * The UI class that builds the Main Form and handles its associated response
 * submits.
 * 
 * @author alfiva
 * 
 */
public class UIMain {
//
//    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
//	    + "UIMain"; //$NON-NLS-1$
//
//    static final String SUBMIT_EXIT = PREFIX + "exit"; //$NON-NLS-1$
//    static final String SUBMIT_MENUS = PREFIX + "menus"; //$NON-NLS-1$
//    static final String SUBMIT_RECIPES = PREFIX + "recipes"; //$NON-NLS-1$
//    static final String SUBMIT_SHOPPING = PREFIX + "shopping"; //$NON-NLS-1$
//    static final String SUBMIT_PROFILE = PREFIX + "profile"; //$NON-NLS-1$
//    static final String SUBMIT_HELP = PREFIX + "help"; //$NON-NLS-1$
//    static final String SUBMIT_POP = PREFIX + "pop"; //$NON-NLS-1$
//    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
//
//    /**
//     * Compose the form for this dialog.
//     * 
//     * @return The form.
//     */
//    public static Form getForm() {
//	Form f = Form.newDialog(Messages.getString("UIMain.0"), new Resource()); //$NON-NLS-1$
//
//	new SimpleOutput(f.getIOControls(), null, null,
//		Messages.getString("UIMain.1")); //$NON-NLS-1$
//	new SimpleOutput(
//		f.getIOControls(),
//		null,
//		null,
//		Messages.getString("UIMain.2")); //$NON-NLS-1$
//
//	new MediaObject(f.getIOControls(), new Label(Messages.getString("UIMain.3"), null), //$NON-NLS-1$
//		"image", InterfaceProvider.IMG_URL+"despacio.png"); // //$NON-NLS-1$ //$NON-NLS-2$
//
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.4"), null), SUBMIT_MENUS); //$NON-NLS-1$
////	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.5"), null), SUBMIT_RECIPES); //$NON-NLS-1$
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.6"), null), //$NON-NLS-1$
//		SUBMIT_SHOPPING);
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.7"), null), SUBMIT_PROFILE); //$NON-NLS-1$
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.8"), null), SUBMIT_HELP); //$NON-NLS-1$
//	new Submit(f.getSubmits(), new Label(Messages.getString("UIMain.9"), null), SUBMIT_EXIT); //$NON-NLS-1$
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
//	Utils.println("Received delegation of UI response in UIMain"); //$NON-NLS-1$
//	if (uir != null) {
//	    String id = uir.getSubmissionID();
//	    if (SUBMIT_EXIT.equals(id))
//		return; // Cancel Dialog, go back to main dialog
//
//	    if (SUBMIT_MENUS.equals(id)) {
//		Utils.println("UIMain submit menus"); //$NON-NLS-1$
//		UIRequest out = new UIRequest(SharedResources.testUser,
//			UIMenus.getForm(true), LevelRating.middle,
//			Locale.ENGLISH, PrivacyLevel.insensible);
//		SharedResources.uIProvider.sendUIRequest(out);
//		return;
//	    }
////	    if (SUBMIT_RECIPES.equals(id)) {
////		Utils.println("UIMain submit recipes"); //$NON-NLS-1$
////		UIRequest out = new UIRequest(SharedResources.testUser,
////			UIRecipes.getForm(), LevelRating.middle,
////			Locale.ENGLISH, PrivacyLevel.insensible);
////		SharedResources.uIProvider.sendUIRequest(out);
////		return;
////	    }
//	    if (SUBMIT_SHOPPING.equals(id)) {
//		Utils.println("UIMain submit shopping"); //$NON-NLS-1$
//		UIRequest out = new UIRequest(SharedResources.testUser,
//			UIShopping.getForm(), LevelRating.middle,
//			Locale.ENGLISH, PrivacyLevel.insensible);
//		SharedResources.uIProvider.sendUIRequest(out);
//		return;
//	    }
//	    if (SUBMIT_PROFILE.equals(id)) {
//		Utils.println("UIMain submit profile"); //$NON-NLS-1$
//		UIRequest out = new UIRequest(SharedResources.testUser,
//			UIProfile.getForm(), LevelRating.middle,
//			Locale.ENGLISH, PrivacyLevel.insensible);
//		SharedResources.uIProvider.sendUIRequest(out);
//		return;
//	    }
//	    if (SUBMIT_HELP.equals(id)) {
//		Utils.println("UIMain submit help"); //$NON-NLS-1$
//		UIRequest out = new UIRequest(SharedResources.testUser,
//			UIHelp.getForm(), LevelRating.middle, Locale.ENGLISH,
//			PrivacyLevel.insensible);
//		SharedResources.uIProvider.sendUIRequest(out);
//		return;
//	    }
//	    if (SUBMIT_POP.equals(id)) {
//		Utils.println("UIMain submit popup"); //$NON-NLS-1$
//		UIRequest out = new UIRequest(SharedResources.testUser,
//			UIProfile.getForm(), LevelRating.middle,
//			Locale.ENGLISH, PrivacyLevel.insensible);
//		SharedResources.uIProvider.sendUIRequest(out);
//		return;
//	    }
//	}
//	Utils.println("Finished delegation of UI response in UIMain"); //$NON-NLS-1$
//    }

}
