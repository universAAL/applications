/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Politï¿½cnica de Madrdid
	
	OCO Source Materials
	© Copyright IBM Corp. 2011
	
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
import org.universAAL.AALapplication.health.performedSession.manager.profiles.ListPerformedSessionBetweenTimeStampsService;
import org.universAAL.AALapplication.health.performedSession.manager.profiles.ListPerformedSessionService;
import org.universAAL.AALapplication.health.performedSession.manager.profiles.SessionPerformedService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.services.PerformedSessionManagementService;

/**
 * This class provides the performed sessions manager services.
 * 
 * @author roni
 */
public class PerformedSessionManagerProvider extends ServiceCallee {

	// the actual performed session manager 
	private PerformedSessionManager performedSessionManager = null;
	
	static final ServiceProfile[] profiles = new ServiceProfile[5];
	
	// define profiles
	static {
    	profiles[0] = new SessionPerformedService().getProfile();		
    	profiles[1] = new ListPerformedSessionService().getProfile();
    	profiles[2] = new ListPerformedSessionBetweenTimeStampsService().getProfile();
	}
	
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
     * @param realizedServices
     */
    protected PerformedSessionManagerProvider(ModuleContext context, ServiceProfile[] realizedServices) {
		super(context, realizedServices);
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
		super(context, profiles);

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

		Object userInput = call.getInputValue(PerformedSessionManagementService.INPUT_USER);
		if(userInput == null)
		    return null;

		Object treatmentInput = call.getInputValue(PerformedSessionManagementService.INPUT_TREATMENT);
		if(treatmentInput == null)
		    return null;

		if(operation.startsWith(ListPerformedSessionService.MY_URI))
			return getAllPerformedsessions(userInput.toString(), treatmentInput.toString());

		Object performedSessionInput = call
			.getInputValue(PerformedSessionManagementService.INPUT_PERFORMED_SESSION);

		Object timestampFromInput = call
			.getInputValue(ListPerformedSessionBetweenTimeStampsService.INPUT_TIMESTAMP_FROM);

		Object timestampToInput = call
			.getInputValue(ListPerformedSessionBetweenTimeStampsService.INPUT_TIMESTAMP_TO);

		if(timestampFromInput != null && timestampToInput != null &&
				operation.startsWith(ListPerformedSessionBetweenTimeStampsService.MY_URI))
		    return getPerformedSessionsBetweenTimestamps(
		    		userInput.toString(), treatmentInput.toString(), 
		    		((Long)timestampFromInput).longValue(), 
		    		((Long)timestampToInput).longValue());

		if(performedSessionInput != null &&
				operation.startsWith(SessionPerformedService.MY_URI))
		    return sessionPerformed(userInput.toString(), treatmentInput.toString(), 
		    		(PerformedSession)performedSessionInput);
		
		return null;
	}
	
	/**
	 * Creates a service response that including all the performed sessions that 
	 * are associated with the given user and treatment.
	 * 
	 * @param userURI The URI of the user
	 * @param treatmentURI The URI of the associated treatment   
	 */
	private ServiceResponse getAllPerformedsessions(String userURI, String treatmentURI) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List performedSessionsList = 
			performedSessionManager.getAllPerformedSessions(userURI, treatmentURI);
		sr.addOutput(new ProcessOutput(
				PerformedSessionManagementService.OUTPUT_PERFORMED_SESSIONS, performedSessionsList));
		
		return sr;		
	}

	/**
	 * Creates a service response that including all the performed sessions that 
	 * are associated with the given user and treatment and are between the 
	 * given timestamps.
	 * 
	 * @param userURI The URI of the user
	 * @param treatmentURI The URI of the associated treatment   
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 */
	private ServiceResponse getPerformedSessionsBetweenTimestamps(
			String userURI, String treatmentURI, long timestampFrom, long timestampTo) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List performedSessionsList = performedSessionManager.
				getPerformedSessionsBetweenTimestamps(userURI, treatmentURI, timestampFrom, timestampTo);
		sr.addOutput(new ProcessOutput(
				PerformedSessionManagementService.OUTPUT_PERFORMED_SESSIONS, performedSessionsList));
		
		return sr;
	}
	
	/**
	 * Creates a service response for storing a session that was performed by 
	 * the given user for the given treatment.
	 * 
	 * @param userURI The URI of the user 
	 * @param treatmentURI The URI of the associated treatment   
	 * @param session The session that was performed by the user
	 */
	private ServiceResponse sessionPerformed(String userURI, 
			String treatmentURI, PerformedSession session) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		performedSessionManager.sessionPerformed(userURI, treatmentURI, session);
		
		return sr;
	}
}
