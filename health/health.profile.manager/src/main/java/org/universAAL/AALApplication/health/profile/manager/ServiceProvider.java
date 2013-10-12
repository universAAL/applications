/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 * Universidad Polit�cnica de Madrid
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

import org.universAAL.AALApplication.health.profile.manager.impl.ProfileServerHealthProfileProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.profile.AssistedPerson;

/**
 * @author amedrano
 *
 */
public class ServiceProvider extends ServiceCallee {

	
    // prepare a standard error message for later use
    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    static {
    	invalidInput.addOutput(new ProcessOutput(
    			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }

	private IHealthProfileProvider psm;
	
	/**
	 * @param context
	 * @param realizedServices
	 */
	protected ServiceProvider(ModuleContext context,
			ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		psm = new ProfileServerHealthProfileProvider(context);
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

		Object userInput = call.getInputValue(HealthProfileService.INPUT_USER);
		LogUtils.logDebug(owner,
				getClass(),
				"handleCall",
				new Object[]{"user Received ", userInput},
				null);
		
		if(userInput == null)
		    return invalidInput;
		
		if(operation.startsWith(HealthProfileService.GET_HEALTH_PROFILE))
			return getProfile(((AssistedPerson)userInput));
		
		HealthProfile profile = (HealthProfile) call.getInputValue(HealthProfileService.INPUT_PROFILE);
		if(operation.startsWith(HealthProfileService.UPDATE_HEALTH_PROFILE) && profile!= null)
			return updateProfile(profile);
		
		return invalidInput;
	}

	private ServiceResponse updateProfile(HealthProfile profile) {
		psm.updateHealthProfile(profile);
		//TODO check it wass udpated
		return new ServiceResponse(CallStatus.succeeded);
	}

	private ServiceResponse getProfile(Resource userInput) {
		LogUtils.logDebug(owner, getClass(), "getProfile", "getting profile");
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		sr.addOutput(new ProcessOutput(HealthProfileService.OUTPUT_PROFILE, psm.getHealthProfile(userInput)));

		LogUtils.logDebug(owner, getClass(), "getProfile", "profile gotten");
		// TODO check get is not null
		return sr;
	}


}
