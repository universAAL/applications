package uAAL.UI.dialogs.packed;

import java.util.Locale;

import nna.SharedResources;
import nna.utils.Utils;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;

/**
 * The UICaller that takes care of handling all UI responses and delivering them
 * to the right UI***** class. It also starts the main screen called from the
 * callee (from the Main Menu).
 * 
 * @author alfiva
 * 
 */
public class InterfaceProvider extends UICaller {

    public static final String MY_UI_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
	    + "UIProvider#";
    public static final String IMG_URL = "http://127.0.0.1:8080/resources/nutritional.uiclient.basic/";

    public InterfaceProvider(ModuleContext context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    @Override
    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    @Override
    public void dialogAborted(String arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void handleUIResponse(UIResponse uir) {
	Utils.println("Received UI Submit ID: " + uir.getSubmissionID());
	String id = uir.getSubmissionID();
	if (uir != null) {
//	    if (id.startsWith(UIMain.PREFIX)) {
//		UIMain.handleUIResponse(uir);
//		return;
//	    }
	    if (id.startsWith(UIHelp.PREFIX)) {
		UIHelp.handleUIResponse(uir);
		return;
	    }
	    if (id.startsWith(UIMenus.PREFIX)) {
		UIMenus.handleUIResponse(uir);
		return;
	    }
//	    if (id.startsWith(UIMenusTomorrow.PREFIX)) {
//		UIMenusTomorrow.handleUIResponse(uir);
//		return;
//	    }
	    if (id.startsWith(UIProfile.PREFIX)) {
		UIProfile.handleUIResponse(uir);
		return;
	    }
	    if (id.startsWith(UIRecipeDetail.PREFIX)) {
		UIRecipeDetail.handleUIResponse(uir);
		return;
	    }
//	    if (id.startsWith(UIRecipes.PREFIX)) {
//		UIRecipes.handleUIResponse(uir);
//		return;
//	    }
//	    if (id.startsWith(UIRecipesToday.PREFIX)) {
//		UIRecipesToday.handleUIResponse(uir);
//		return;
//	    }
//	    if (id.startsWith(UIRecipesTomorrow.PREFIX)) {
//		UIRecipesTomorrow.handleUIResponse(uir);
//		return;
//	    }
	    if (id.startsWith(UIShopping.PREFIX)) {
		UIShopping.handleUIResponse(uir);
		return;
	    }
	}
	Utils.println("End of handling UI response");
    }

    public void startMainDialog() {
	Utils.println("Start Main Dialog invoked");
	UIRequest out = new UIRequest(SharedResources.testUser,
//		UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
		UIMenus.getForm(true), LevelRating.middle, Locale.ENGLISH,
		PrivacyLevel.insensible);
	sendUIRequest(out);
    }

}
