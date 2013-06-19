package org.universAAL.AALapplication.nutritional.dialogs;

import java.util.Locale;

import org.universAAL.AALapplication.nutritional.SharedResources;
import org.universAAL.AALapplication.nutritional.utils.Utils;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
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
    
   // private static String absolutePath = new BundleConfigHome("nutritional.client").getAbsolutePath();
    
    public static final String IMG_URL = "http://127.0.0.1:8080/resources/nutritional.client/";

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
	// This delivers the UI response to the right class. If you add a new UI
	// class, add it here!
    	
	Utils.println("Received UI Submit ID: " + uir.getSubmissionID());
	String id = uir.getSubmissionID();
	if (uir != null) {
	    if (id.startsWith(UIHelp.PREFIX)) {
		UIHelp.handleUIResponse(uir);
		return;
	    }
	    if (id.startsWith(UIMenus.PREFIX)) {
		UIMenus.handleUIResponse(uir);
		return;
	    }
	    if (id.startsWith(UIProfile.PREFIX)) {
		UIProfile.handleUIResponse(uir);
		return;
	    }
	    if (id.startsWith(UIRecipeDetail.PREFIX)) {
		UIRecipeDetail.handleUIResponse(uir);
		return;
	    }
	    if (id.startsWith(UIShopping.PREFIX)) {
		UIShopping.handleUIResponse(uir);
		return;
	    }
	    if (id.startsWith(UIMedication.PREFIX)) {
		UIMedication.handleUIResponse(uir);
		return;
	    }
	    if (id.startsWith(UIItemList.PREFIX)) {
		UIItemList.handleUIResponse(uir);
		return;
	    }
	}
	Utils.println("End of handling UI response");
    }

    public void startMainDialog() {
	Utils.println("Start Main Dialog invoked");
	UIRequest out = new UIRequest(SharedResources.user,
		// UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
		UIMenus.getForm(true), LevelRating.middle, Locale.getDefault(),
		PrivacyLevel.insensible);
	sendUIRequest(out);
    }

}
