/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Polit�cnica de Madrdid
	
	OCO Source Materials
	� Copyright IBM Corp. 2011
	
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
package org.universAAL.AALapplication.health.performedSession.manager;

import java.util.List;

import org.universAAL.AALapplication.health.performedSession.manager.impl.ProfileServerPerformedSessionManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.services.PerformedSessionManagementService;

/**
 * This class provides the performed sessions manager services.
 * 
 * @author roni
 */
public class PerformedSessionManagerProvider extends ServiceCallee {

	// the actual performed session manager 
	private PerformedSessionManager performedSessionManager = null;
	
	
    // prepare a standard error message for later use
    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    static {
    	invalidInput.addOutput(new ProcessOutput(
    			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }
	
    /**
     * Constructor.
     * 
     * @param context
     */
	public PerformedSessionManagerProvider(ModuleContext context) {
		
		// as a service providing component, we have to extend ServiceCallee
    	// this in turn requires that we introduce which services we would like
    	// to provide to the universAAL-based AAL Space
		super(context, ProvidedPerformedSessionManagementService.profiles);
		// the actual implementation of the performed session manager
		performedSessionManager = new ProfileServerPerformedSessionManager(context);
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
	}

	/**
	 * Since this class is a child of the ServiceCallee, it is registered to the
	 * service-bus. Every service call that passes the restrictions will take
     * affect here. Given by the URI of the request we know what specific
     * function we have to call.
     * 
	 * @see org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL
     * .middleware.service.ServiceCall)
	 */
	public ServiceResponse handleCall(ServiceCall call) {
		if(call == null)
		    return null;

		String operation = call.getProcessURI();
		if(operation == null)
		    return null;

		User userInput = (User) call.getInputValue(ProvidedPerformedSessionManagementService.INPUT_USER);
		if(userInput == null)
		    return null;

		if(operation.startsWith(ProvidedPerformedSessionManagementService.MY_URI)) {
			Object treatmentInput = call.getInputValue(ProvidedPerformedSessionManagementService.INPUT_TREATMENT);
			if(treatmentInput == null)
				return null;
			return getAllPerformedsessions(userInput, (Treatment) treatmentInput);
		}
		
		if(operation.startsWith(ProvidedPerformedSessionManagementService.MY_URI)) {
		Object timestampFromInput = call
			.getInputValue(ProvidedPerformedSessionManagementService.INPUT_TIMESTAMP_FROM);

		Object timestampToInput = call
			.getInputValue(ProvidedPerformedSessionManagementService.INPUT_TIMESTAMP_TO);
			
			if (timestampFromInput != null && timestampToInput != null) {

				Object treatmentInput = call.getInputValue(ProvidedPerformedSessionManagementService.INPUT_TREATMENT);
				return getPerformedSessionsBetweenTimestamps(
		    		userInput, (Treatment) treatmentInput, 
		    		((Long)timestampFromInput).longValue(), 
		    		((Long)timestampToInput).longValue());
			}
		}
		    



		

		if(operation.startsWith(ProvidedPerformedSessionManagementService.MY_URI)) {
			Object performedSessionInput = call
					.getInputValue(ProvidedPerformedSessionManagementService.INPUT_PERFORMED_SESSION);
			if (performedSessionInput != null)
				return sessionPerformed(userInput,
		    		(PerformedSession)performedSessionInput);
		}
		
		return null;
	}
	
	/**
	 * Creates a service response that including all the performed sessions that 
	 * are associated with the given user and treatment.
	 * 
	 * @param user The URI of the user
	 * @param treatment The URI of the associated treatment   
	 */
	private ServiceResponse getAllPerformedsessions(User user, Treatment treatment) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List<PerformedSession> performedSessionsList = 
			performedSessionManager.getAllPerformedSessions(user);
		sr.addOutput(new ProcessOutput(
				PerformedSessionManagementService.PROP_MANAGES_SESSION, performedSessionsList));
		
		return sr;		
	}

	/**
	 * Creates a service response that including all the performed sessions that 
	 * are associated with the given user and treatment and are between the 
	 * given timestamps.
	 * 
	 * @param user The URI of the user
	 * @param treatment The URI of the associated treatment   
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 */
	private ServiceResponse getPerformedSessionsBetweenTimestamps(
			User user, Treatment treatment, long timestampFrom, long timestampTo) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List<PerformedSession> performedSessionsList = performedSessionManager.
				getPerformedSessionsBetweenTimestamps(user, timestampFrom, timestampTo);
		sr.addOutput(new ProcessOutput(
				PerformedSessionManagementService.PROP_MANAGES_SESSION, performedSessionsList));
		
		return sr;
	}
	
	/**
	 * Creates a service response for storing a session that was performed by 
	 * the given user for the given treatment.
	 * 
	 * @param user The URI of the user 
	 * @param treatmentURI The URI of the associated treatment   
	 * @param session The session that was performed by the user
	 */
	private ServiceResponse sessionPerformed(User user, PerformedSession session) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		performedSessionManager.sessionPerformed(user, session);
		
		return sr;
	}
}
