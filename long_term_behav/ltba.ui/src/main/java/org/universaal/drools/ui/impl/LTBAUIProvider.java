/**
 * 
 */
package org.universaal.drools.ui.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

/**
 * @author mllorente
 * 
 */
public class LTBAUIProvider extends ServiceCallee {

	// public static final String START_URI =
	// SharedResources.DROOLS_UI_NAMESPACE
	// + "MainDialog";

	private static final String NAMESPACE = "http://ontologies.universAAL.com/LTBAUI.owl#";
	private static final String MY_URI = NAMESPACE + "LTBAUIProvider";
	private static final String START_UI = NAMESPACE + "startUI";

	ModuleContext ctx;
	
	public LTBAUIProvider(ModuleContext mc) {
//		super(mc, new ServiceProfile[] { InitialServiceDialog
//				.createInitialDialogProfile(DroolsReasoning.MY_URI,
//						"http://www.tsbtecnologias.es", "LTBA user interface",
//						START_UI) });
		this(mc, getProfiles());
	}
	
	/**
	 * @param context
	 * @param realizedServices
	 */
	public LTBAUIProvider(ModuleContext context,
			ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		this.ctx = context;
		// TODO Auto-generated constructor stub
	}
	
	private static ServiceProfile[] getProfiles() {
		ServiceProfile initDP = InitialServiceDialog
				.createInitialDialogProfile(
						MY_URI,
						"http://www.tsbtecnologias.es",
						"LTBA User Interface",
						START_UI);
		return new ServiceProfile[] {initDP};
	}


	public ServiceResponse handleCall(ServiceCall call) {
		
		Object inputUser = call.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
		new UIProvider(this.ctx).showDialog((Resource) inputUser);
		return new ServiceResponse(CallStatus.succeeded);
		
		
//		if (call != null) {
//			String operation = call.getProcessURI();
//			if (operation != null && operation.startsWith(START_URI)) {
//				System.out.println("-- LTBA UI Main Menu --");
//				SharedResources.uIProvider.startMainDialog();
//
//				ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
//				return sr;
//			}
//		}
//		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken
	 * ()
	 */
	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

}
