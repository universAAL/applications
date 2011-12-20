/*******************************************************************************
 * Copyright 2011 Universidad Politécnica de Madrid
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
package org.universAAL.Application.personal_safety.sw.panic;

import org.osgi.framework.BundleContext;
import org.universAAL.Application.personal_safety.sw.panic.osgi.Activator;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.User;

public class SCallee extends ServiceCallee{
	
	private static final String MY_URI = "http://ontology.universAAL.org/panic.owl#";
	private static final String SERVICE_URI = MY_URI+"SW_BUTTON";
	private static final String SERVICE_START_URI = MY_URI+"SW_BUTTON_PRESSED";
	
	
	private static final ServiceResponse failure = new ServiceResponse(
			CallStatus.serviceSpecificFailure);
	
	public SCallee(BundleContext context){
		super(context, getProfiles());
	}
	
	protected SCallee(BundleContext context, ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public ServiceResponse handleCall(ServiceCall call) {
		if (call == null){
			failure.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,"Null call!?!"));
			return failure;
		}
		String operation = call.getProcessURI();
		if (operation == null){
			failure.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,"Null operation!?!"));
			return failure;
		}
		if (operation.startsWith(SERVICE_START_URI)){
			return panicButton(call);
		}
		return null;
	}

	static ServiceProfile[] getProfiles() {
		ServiceProfile prof = InitialServiceDialog.createInitialDialogProfile(
				SERVICE_URI,
				"http://www.upm.es", "Software Mapped Panic Button",
				SERVICE_START_URI);
		return new ServiceProfile[]{prof};
	}
	
	private ServiceResponse panicButton(ServiceCall call) {
		User user=null;
		Object inputUser = call.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
		if ((inputUser == null)||!(inputUser instanceof User)){
			failure.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,"Invalid User Input!"));
			return failure;
		}else{
			user=(User)inputUser;
			Activator.opublisher.confirmPanic(user);
		}
		
		return new ServiceResponse(CallStatus.succeeded);
	}
}

