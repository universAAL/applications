package org.universAAL.AALapplication.food_shopping.service.client;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.AALapplication.ont.foodDevices.*;

public class ServiceProvider extends ServiceCallee {

    public static final String UI_SHOPPING_CLIENT_NAMESPACE = SharedResources.CLIENT_SHOPPING_UI_NAMESPACE + "ServiceProvider#";
    public static final String START_URI = UI_SHOPPING_CLIENT_NAMESPACE + "MainDialog";

    ServiceProvider(ModuleContext mc) {
	super(mc, new ServiceProfile[] { InitialServiceDialog.createInitialDialogProfile(FoodManagement.MY_URI,
			"http://www.igd.fraunhofer.de",
			"Sample Refrigerator UI Client", START_URI) });
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    public ServiceResponse handleCall(ServiceCall call) {
    	System.out.println("1. EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
	if (call != null) {
	    String operation = call.getProcessURI();
	    if (operation != null && operation.startsWith(START_URI)) {
		System.out.println("-- Lighting UI Client Main Menu --");
		SharedResources.uIProvider.startMainDialog();

		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		return sr;
	    }
	}
	return null;
    }
}
