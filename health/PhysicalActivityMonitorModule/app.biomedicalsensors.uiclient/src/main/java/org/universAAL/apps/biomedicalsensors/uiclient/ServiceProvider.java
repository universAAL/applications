package org.universAAL.apps.biomedicalsensors.uiclient;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.biomedicalsensors.BiomedicalSensorService;

/**
 * @author billyk, joemoul
 * 
 */
public class ServiceProvider extends ServiceCallee {

	public static final String UI_BIOMEDICALSENSORS_CLIENT_NAMESPACE = SharedResources.CLIENT_BIOMEDICALSENSORS_UI_NAMESPACE
			+ "ServiceProvider#";
	public static final String START_URI = UI_BIOMEDICALSENSORS_CLIENT_NAMESPACE
			+ "MainDialog";

	ServiceProvider(ModuleContext mc) {
		super(mc, new ServiceProfile[] { InitialServiceDialog
				.createInitialDialogProfile(BiomedicalSensorService.MY_URI,
						"http://www.certh.gr",
						"Physical Activity Monitor UI Client", START_URI) });
	}

	public void communicationChannelBroken() {

	}

	public ServiceResponse handleCall(ServiceCall call) {
		if (call != null) {
			String operation = call.getProcessURI();
			if (operation != null && operation.startsWith(START_URI)) {
				System.out
						.println("-- Physical Activity Monitor UI Client Main Menu --");
				SharedResources.uIProvider.startMainDialog();

				ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
				return sr;
			}
		}
		return null;
	}
}
