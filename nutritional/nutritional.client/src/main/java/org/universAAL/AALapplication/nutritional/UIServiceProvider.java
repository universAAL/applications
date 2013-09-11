package org.universAAL.AALapplication.nutritional;

import na.oasisUtils.trustedSecurityNetwork.Login;
import na.utils.InitialSetup;

import org.universAAL.AALapplication.nutritional.utils.Utils;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.nutrition.NutritionService;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

/**
 * @author hecgamar
 * 
 */
public class UIServiceProvider extends ServiceCallee {
	
    private boolean done = false;
    public static final String UI_NUTRITIONAL_CLIENT_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
	    + "ServiceProvider#";
    public static final String START_URI = UI_NUTRITIONAL_CLIENT_NAMESPACE
	    + "MainDialog";

    UIServiceProvider(ModuleContext mc) {
		super(mc, new ServiceProfile[] { InitialServiceDialog
			.createInitialDialogProfile(NutritionService.MY_URI,
				"http://www.tsb.upv.es/", "Nutrition UI",
				START_URI) });
		Utils.println("Registro: " + NutritionService.MY_URI + " web: "
			+ "http://www.tsb.upv.es/");
    }

    /**
     * @see org.persona.middleware.service.ServiceCallee#communicationChannelBroken()
     */
    public void communicationChannelBroken() {
	Utils.println("communicationChannelBroken en UIServiceProvider!");
    }

    /**
     * @see org.persona.middleware.service.ServiceCallee#handleCall(org.persona.middleware.service.ServiceCall)
     */
    public ServiceResponse handleCall(ServiceCall call) {
	if (call != null) {
		
	    String operation = call.getProcessURI();
	    if (operation != null && operation.startsWith(START_URI)) {
		Utils.println("-- New Nutritional Main Menu --");
		if (SharedResources.user==null) {
			SharedResources.user = (User)call.getInvolvedUser();
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------   user " + SharedResources.user);
			if (!done){
				InitialSetup.initNutriAdvisorFolder();
				Login login = new Login();
				login.logMeIn();
				done = true;
			} 
		}
		else Utils.println("[ " + SharedResources.user + " ] user is login in the system.......");
		
		SharedResources.uIProvider.startMainDialog();
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		return sr;
	    }
	}
	return null;
    }
}
