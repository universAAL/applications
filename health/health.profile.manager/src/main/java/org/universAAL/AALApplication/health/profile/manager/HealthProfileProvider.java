/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 * Universidad Politécnica de Madrid
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
package org.universAAL.AALApplication.health.profile.manager;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.health.HealthProfile;
import org.universaal.ontology.health.owl.services.GetProfileService;
import org.universaal.ontology.health.owl.services.UpdateProfileService;

/**
 * @author amedrano
 *
 */
public class HealthProfileProvider extends ServiceCallee {

	static private ServiceProfile[] profiles
		= new ServiceProfile[] { new GetProfileService().getProfile(), new UpdateProfileService().getProfile()};
	
    // prepare a standard error message for later use
    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    static {
    	invalidInput.addOutput(new ProcessOutput(
    			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }

	private ProfileServerManager psm;
	
	/**
	 * @param context
	 * @param realizedServices
	 */
	protected HealthProfileProvider(ModuleContext context,
			ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		psm = new ProfileServerManager(context);
	}
	
	public HealthProfileProvider(ModuleContext context) {
		this(context,profiles);
	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken()
	 */
	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL.middleware.service.ServiceCall)
	 */
	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		if(call == null)
		    return invalidInput;

		String operation = call.getProcessURI();
		if(operation == null)
		    return invalidInput;

		Object userInput = call.getInputValue(GetProfileService.INPUT_USER);
		if(userInput == null)
		    return invalidInput;
		
		if(operation.startsWith(GetProfileService.MY_URI))
			return getProfile((String)userInput);
		
		HealthProfile profile = (HealthProfile) call.getInputValue(UpdateProfileService.INPUT_PROFILE);
		if(operation.startsWith(UpdateProfileService.MY_URI) && profile!= null)
			return updateProfile(profile);
		
		return invalidInput;
	}

	private ServiceResponse updateProfile(HealthProfile profile) {
		psm.updateHealthProfile(profile);
		return new ServiceResponse(CallStatus.succeeded);
	}

	private ServiceResponse getProfile(String userInput) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		sr.addOutput(new ProcessOutput(GetProfileService.OUTPUT_PROFILE, psm.getHealthProfile(userInput)));
		return sr;
	}

}
