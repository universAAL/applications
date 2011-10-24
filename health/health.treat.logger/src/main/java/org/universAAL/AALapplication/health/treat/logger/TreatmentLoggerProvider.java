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
package org.universAAL.AALapplication.health.treat.logger;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.health.ont.treatment.Treatment;
import org.universAAL.AALapplication.health.treat.logger.impl.ContextHistoryTreatmentLogger;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

/**
 * This class provides the treatment logger services.
 * 
 * @author roni
 */
public class TreatmentLoggerProvider extends ServiceCallee {

	// the actual treatment logger 
	private TreatmentLogger treatmentLogger = null;
	
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
    protected TreatmentLoggerProvider(BundleContext context, ServiceProfile[] realizedServices) {
		super(context, realizedServices);
    }
	
    /**
     * Constructor.
     * 
     * @param context
     */
	public TreatmentLoggerProvider(BundleContext context) {
		// as a service providing component, we have to extend ServiceCallee
    	// this in turn requires that we introduce which services we would like
    	// to provide to the universAAL-based AAL Space
		super(context, TreatmentLoggerServices.profiles);

		// the actual implementation of the treatment logger
		treatmentLogger = new ContextHistoryTreatmentLogger(context);
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

		Object userInput = call.getInputValue(TreatmentLoggerServices.INPUT_USER);
		if(userInput == null)
		    return null;

		if(operation.startsWith(TreatmentLoggerServices.SERVICE_LIST_ALL_TREATMENT_LOG))
			return getAllTreatmentLog(userInput.toString());

		Object treatmentInput = call
			.getInputValue(TreatmentLoggerServices.INPUT_TREATMENT);

		Object timestampFromInput = call
			.getInputValue(TreatmentLoggerServices.INPUT_TIMESTAMP_FROM);

		Object timestampToInput = call
			.getInputValue(TreatmentLoggerServices.INPUT_TIMESTAMP_TO);

		if(timestampFromInput != null && timestampToInput != null &&
				operation.startsWith(TreatmentLoggerServices.SERVICE_LIST_TREATMENT_LOG_BETWEEN_TIMESTAMPS))
		    return getTreatmentLogBetweenTimestamps(
		    		userInput.toString(), ((Long)timestampFromInput).longValue(), 
		    		((Long)timestampToInput).longValue());

		if(treatmentInput != null &&
				operation.startsWith(TreatmentLoggerServices.SERVICE_TREATMENT_DONE))
		    return treatmentDone(userInput.toString(), (Treatment)treatmentInput);
		
		return null;
	}
	
	/**
	 * Creates a service response that including all the treatments that are
	 * associated with the given user.
	 * 
	 * @param userURI The URI of the user
	 */
	private ServiceResponse getAllTreatmentLog(String userURI) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List treatmentsList = treatmentLogger.getAllTreatmentLog(userURI);
		sr.addOutput(new ProcessOutput(
				TreatmentLoggerServices.OUTPUT_TREATMENTS, treatmentsList));
		
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
	private ServiceResponse getTreatmentLogBetweenTimestamps(
			String userURI, long timestampFrom, long timestampTo) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List treatmentsList = treatmentLogger.getTreatmentLogBetweenTimestamps(
				userURI, timestampFrom, timestampTo);
		sr.addOutput(new ProcessOutput(
				TreatmentLoggerServices.OUTPUT_TREATMENTS, treatmentsList));
		
		return sr;
	}
	
	/**
	 * Creates a service response for storing a treatment that was performed by 
	 * the given user.
	 * 
	 * @param userURI The URI of the user 
	 * @param treatment The treatment that was performed by the user
	 */
	private ServiceResponse treatmentDone(String userURI, Treatment treatment) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		treatmentLogger.treatmentDone(userURI, treatment);
		
		return sr;
	}
}
