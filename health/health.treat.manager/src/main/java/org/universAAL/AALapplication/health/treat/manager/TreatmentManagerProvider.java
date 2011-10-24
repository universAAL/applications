/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Politécnica de Madrdid
	
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
package org.universAAL.AALapplication.health.treat.manager;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.health.ont.treatment.Treatment;
import org.universAAL.AALapplication.health.treat.manager.impl.JenaConverterTreatmentManager;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

/**
 * This class provides the treatment manager services.
 * 
 * @author roni
 */
public class TreatmentManagerProvider extends ServiceCallee {

	// the actual treatment manager 
	private TreatmentManager treatmentManager = null;
	
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
    protected TreatmentManagerProvider(BundleContext context, ServiceProfile[] realizedServices) {
		super(context, realizedServices);
    }
	
    /**
     * Constructor.
     * 
     * @param context
     */
	public TreatmentManagerProvider(BundleContext context) {
		// as a service providing component, we have to extend ServiceCallee
    	// this in turn requires that we introduce which services we would like
    	// to provide to the universAAL-based AAL Space
		super(context, TreatmentManagerServices.profiles);

		// the actual implementation of the treatment manager
		treatmentManager = new JenaConverterTreatmentManager(context);
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

		Object userInput = call.getInputValue(TreatmentManagerServices.INPUT_USER);
		if(userInput == null)
		    return null;

		if(operation.startsWith(TreatmentManagerServices.SERVICE_LIST_ALL_TREATMENTS))
			return getAllTreatments(userInput.toString());

		Object treatmentInput = call
			.getInputValue(TreatmentManagerServices.INPUT_TREATMENT);

		Object oldTreatmentInput = call
			.getInputValue(TreatmentManagerServices.INPUT_OLD_TREATMENT);

		Object newTreatmentInput = call
			.getInputValue(TreatmentManagerServices.INPUT_NEW_TREATMENT);

		Object timestampFromInput = call
			.getInputValue(TreatmentManagerServices.INPUT_TIMESTAMP_FROM);

		Object timestampToInput = call
			.getInputValue(TreatmentManagerServices.INPUT_TIMESTAMP_TO);

		if(timestampFromInput != null && timestampToInput != null &&
				operation.startsWith(TreatmentManagerServices.SERVICE_LIST_TREATMENTS_BETWEEN_TIMESTAMPS))
		    return getTreatmentsBetweenTimestamps(
		    		userInput.toString(), ((Long)timestampFromInput).longValue(), 
		    		((Long)timestampToInput).longValue());

		if(treatmentInput != null &&
				operation.startsWith(TreatmentManagerServices.SERVICE_NEW_TREATMENT))
		    return newTreatment(userInput.toString(), (Treatment)treatmentInput);
		
		if(treatmentInput != null &&
				operation.startsWith(TreatmentManagerServices.SERVICE_DELETE_TREATMENT))
		    return deleteTreatment(userInput.toString(), treatmentInput.toString());

		if(oldTreatmentInput != null && newTreatmentInput != null &&
				operation.startsWith(TreatmentManagerServices.SERVICE_EDIT_TREATMENT))
		    return editTreatment(userInput.toString(), oldTreatmentInput.toString(),
		    		(Treatment)newTreatmentInput);

		return null;
	}
	
	/**
	 * Creates a service response that including all the treatments that are
	 * associated with the given user.
	 * 
	 * @param userURI The URI of the user
	 */
	private ServiceResponse getAllTreatments(String userURI) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List treatmentsList = treatmentManager.getAllTreatments(userURI);
		sr.addOutput(new ProcessOutput(
				TreatmentManagerServices.OUTPUT_TREATMENTS, treatmentsList));
		
		return sr;		
	}

	/**
	 * Creates a service response that including all the treatments that are
	 * associated with the given user and are between the given timestamps.
	 * 
	 * @param userURI The URI of the user
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 */
	private ServiceResponse getTreatmentsBetweenTimestamps(
			String userURI, long timestampFrom, long timestampTo) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List treatmentsList = treatmentManager.getTreatmentsBetweenTimestamps(
				userURI, timestampFrom, timestampTo);
		sr.addOutput(new ProcessOutput(
				TreatmentManagerServices.OUTPUT_TREATMENTS, treatmentsList));
		
		return sr;
	}
	
	/**
	 * Creates a service response for adding a new treatment for the given user.
	 * 
	 * @param userURI The URI of the user 
	 * @param treatment The treatment to be added
	 */
	private ServiceResponse newTreatment(String userURI, Treatment treatment) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		treatmentManager.newTreatment(userURI, treatment);
		
		return sr;
	}

	/**
	 * Creates a service response for deleting a treatment for the given user.
	 * 
	 * @param userURI The URI of the user 
	 * @param treatmentURI The treatment to be deleted
	 */
	private ServiceResponse deleteTreatment(String userURI, String treatmentURI) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		treatmentManager.deleteTreatment(userURI, treatmentURI);
		
		return sr;
	}

	/**
	 * Creates a service response for editing a treatment for the given user.
	 * 
	 * @param userURI The URI of the user 
	 * @param oldTreatmentURI The treatment to be editted
	 * @param newTreatment The new treatment
	 */
	private ServiceResponse editTreatment(
			String userURI, String oldTreatmentURI, Treatment newTreatment) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		treatmentManager.editTreatment(userURI, oldTreatmentURI, newTreatment);
		
		return sr;
	}
}
