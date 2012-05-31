package nna;

import nna.utils.Utils;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.nutrition.NutritionService;

/**
 * @author hecgamar
 * 
 */
public class UIServiceProvider extends ServiceCallee {

	public static final String UI_NUTRITIONAL_CLIENT_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
			+ "ServiceProvider#";
	public static final String START_URI = UI_NUTRITIONAL_CLIENT_NAMESPACE
			+ "MainDialog";

	UIServiceProvider(ModuleContext mc) {
		super(mc, new ServiceProfile[] { InitialServiceDialog
				.createInitialDialogProfile(NutritionService.MY_URI,
						"http://www.tsb.upv.es/", "Nutrition super UI",
						START_URI) });
		Utils.println("Registro: "+NutritionService.MY_URI + " web: "+"http://www.tsb.upv.es/");
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
				SharedResources.uIProvider.startMainDialog();

				ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
				return sr;
			}
		}
		return null;
	}
}
