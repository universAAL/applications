/*******************************************************************************
 * Copyright 2013 Universidad Polit�cnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.AALapplication.health.manager;

import org.universAAL.AALapplication.health.manager.ui.MainMenu;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.Caregiver;
import org.universAAL.ontology.profile.User;

public class MainButtonProvider extends ServiceCallee {

	private static final String NAMESPACE = "http://ontologies.universAAL.com/MyService.owl#";
	private static final String MY_URI = NAMESPACE + "MainButtonService";
	private static final String START_UI = NAMESPACE + "startUI";

	private ModuleContext ctxt;
	
	public MainButtonProvider(ModuleContext context,
			ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		this.ctxt = context;
	}
	
	public MainButtonProvider(ModuleContext context){
		this(context,getProfiles());
	}

	private static ServiceProfile[] getProfiles() {
		ServiceProfile initDP = InitialServiceDialog
				.createInitialDialogProfile(
						MY_URI,
						"http://lst.tfo.upm.es",
						"Health Manager UI",
						START_UI);
		return new ServiceProfile[] {initDP};
	}

	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		Object inputUser = call.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
		if (inputUser instanceof Caregiver){
			new MainMenu(ctxt, (User) inputUser,null).show();
		}
		else if (inputUser instanceof AssistedPerson){
			new MainMenu(ctxt, (User) inputUser,(AssistedPerson) inputUser).show();
		}
		return new ServiceResponse(CallStatus.succeeded);
	}

}
