/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.ltba.ui.impl.common;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universaal.ltba.ui.activator.MainLTBAUIProvider;

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
		// super(mc, new ServiceProfile[] { InitialServiceDialog
		// .createInitialDialogProfile(DroolsReasoning.MY_URI,
		// "http://www.tsbtecnologias.es", "LTBA user interface",
		// START_UI) });
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
				.createInitialDialogProfile(MY_URI,
						"http://www.tsbtecnologias.es", "LTBA User Interface",
						START_UI);
		return new ServiceProfile[] { initDP };
	}

	public ServiceResponse handleCall(ServiceCall call) {
		if (call != null) {
			String operation = call.getProcessURI();
			if (operation != null && operation.startsWith(START_UI)) {
				Object inputUser = call
						.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
				new MainLTBAUIProvider(this.ctx)
						.showDialog((Resource) inputUser);
				return new ServiceResponse(CallStatus.succeeded);
			}
		}
		return null;

		// if (call != null) {
		// String operation = call.getProcessURI();
		// if (operation != null && operation.startsWith(START_URI)) {
		// System.out.println("-- LTBA UI Main Menu --");
		// SharedResources.uIProvider.startMainDialog();
		//
		// ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		// return sr;
		// }
		// }
		// return null;
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
