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
package org.universAAL.AALapplication.health.treat.logger.impl;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.health.ont.treatment.Treatment;
import org.universAAL.AALapplication.health.ont.treatment.UserHealthProfile;
import org.universAAL.AALapplication.health.treat.logger.TreatmentLogger;
import org.universAAL.AALapplication.health.treat.logger.TreatmentLoggerServices;
import org.universAAL.context.che.ontology.ContextHistoryService;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;

/**
 * This class actually implements the 
 * {@link org.universAAL.AALapplication.health.treat.logger.TreatmentLogger} by 
 * using the context history.
 * 
 * @author amedrano
 * @author roni
 */
public class ContextHistoryTreatmentLogger implements TreatmentLogger {

    private static final String CONTEXT_HISTORY_HTL_IMPL_NAMESPACE = 
    	"http://ontology.universAAL.org/ContextHistoryHTLImpl.owl#"; 

    private static final String OUTPUT_QUERY_RESULT = 
    	CONTEXT_HISTORY_HTL_IMPL_NAMESPACE + "queryResult";

    /**
     * Needed for publishing context events
     */
    private ContextPublisher cp = null;
    
    /**
     * Needed for making service requests
     */
    private static ServiceCaller caller = null;
    
    /**
     * Constructor.
     * 
     * @param context
     */
    public ContextHistoryTreatmentLogger(BundleContext context) {
    	
    	// prepare for context publishing
    	ContextProvider info = new ContextProvider(
    			TreatmentLoggerServices.TREATMENT_LOGGER_NAMESPACE + "TreatmentLoggerContextProvider");
    	info.setType(ContextProviderType.controller);
    	cp = new DefaultContextPublisher(context, info);

    	// the DefaultServiceCaller will be used to make ServiceRequest
    	caller = new DefaultServiceCaller(context);
    }

	/**
	 * Returns a {@java.util.List} of all the treatments in the treatment log 
	 * that are associated to the given user.
	 * 
	 * @param userURI The URI of the user who performed the treatments
	 * 
	 * @return All the treatments that were performed by the user 
	 */
	public List getAllTreatmentLog(String userURI) {

		ServiceResponse sr = caller.call(allTreatmentLogRequest(userURI));

		if (sr.getCallStatus() == CallStatus.succeeded) {

			try {
		    	List treatmentList = sr.getOutput(OUTPUT_QUERY_RESULT, true);

		    	if(treatmentList == null || treatmentList.size() == 0) {
//		    		LogUtils.logInfo(Activator.mc, ContextHistoryTreatmentLogger.class,
//		    				"getAllTreatmentLog",
//		    				new Object[] { "there are no treatments in the log" }, null);
		    		return null;
		    	}
		    	return treatmentList;

			} catch(Exception e) {
//				LogUtils.logError(Activator.mc, ContextHistoryTreatmentLogger.class,
//					"getAllTreatmentLog", new Object[] { "got exception",
//						e.getMessage() }, e);
				return null;
		    }
		} else {
//		    LogUtils.logWarn(Activator.mc, ContextHistoryTreatmentLogger.class,
//			    "getAllTreatmentLog",
//			    new Object[] { "callstatus is not succeeded" }, null);
		    return null;
		}
	}

	/**
	 * Returns a {@java.util.List} of all the treatments in the treatment log 
	 * that are associated to the given user and are between the given timestamps.
	 * 
	 * @param userURI The URI of the user who performed the treatments
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 * 
	 * @return The treatments that were performed by the user in a specific 
	 * period of time  
	 */
	public List getTreatmentLogBetweenTimestamps(String userURI, 
			long timestampFrom, long timestampTo) {
		
		ServiceResponse sr = caller.call(treatmentLogBetweenTimestampsRequest(
				userURI, timestampFrom, timestampTo));

		if (sr.getCallStatus() == CallStatus.succeeded) {
		    try {
		    	List treatmentList = sr.getOutput(OUTPUT_QUERY_RESULT, true);

		    	if(treatmentList == null || treatmentList.size() == 0) {
//		    		LogUtils.logInfo(Activator.mc, ContextHistoryTreatmentLogger.class,
//		    				"getTreatmentLogBetweenTimestamps",
//		    				new Object[] { "there are no treatments in the log" }, null);
		    		return null;
		    	}
		    	return treatmentList;

		    } catch(Exception e) {
//				LogUtils.logError(Activator.mc, ContextHistoryTreatmentLogger.class,
//						"getAllTreatmentLog", new Object[] { "got exception",
//							e.getMessage() }, e);
				return null;
		    }
		} else {
//		    LogUtils.logWarn(Activator.mc, ContextHistoryTreatmentLogger.class,
//		    		"getAllTreatmentLog",
//		    		new Object[] { "callstatus is not succeeded" }, null);
		    return null;
		}
	}
	
	/**
	 * Stores the new treatment that was performed by the user in the context 
	 * history.
	 * 
	 * @param userURI The URI of the user who performed this treatment
	 * @param treatment The treatment that was performed by the user
	 */
	public void treatmentDone(String userURI, Treatment treatment) {
		
		UserHealthProfile userHealthProfile = 
			new UserHealthProfile(CONTEXT_HISTORY_HTL_IMPL_NAMESPACE + "userHealthProfile"); // TBD - getHealthProfile(user);
		userHealthProfile.setProperty(UserHealthProfile.PROP_HAS_TREATMENT, treatment);
		cp.publish(new ContextEvent(userHealthProfile, UserHealthProfile.PROP_HAS_TREATMENT));
	}
	
    /**
     * Creates a ServiceRequest to retrieve all the treatments that were 
     * performed by the given user.
     *  
     * @param userURI The URI of the user that performed these treatments
     * 
     * @return The treatments that were performed
     */
    private ServiceRequest allTreatmentLogRequest(String userURI) {
    	
    	String query = null;
    	
    	ServiceRequest request = new ServiceRequest(new ContextHistoryService(null), null);
		
		Restriction r = Restriction.getFixedValueRestriction(
				ContextHistoryService.PROP_PROCESSES, query);
		
		request.getRequestedService().addInstanceLevelRestriction(
				r,
				new String[] {ContextHistoryService.PROP_PROCESSES});
		request.addSimpleOutputBinding(new ProcessOutput(OUTPUT_QUERY_RESULT),
				new PropertyPath(null, true, new String[] {
						ContextHistoryService.PROP_RETURNS}).getThePath());
    	
    	return request;
    }

    /**
     * Creates a ServiceRequest to retrieve all the treatments that were 
     * performed by the given user between the given timestamps.
     *  
     * @param userURI The URI of the user that perfomed these treatments
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
     * 
     * @return The treatments that were performed
     */
    private ServiceRequest treatmentLogBetweenTimestampsRequest(String userURI,
    		long timestampFrom, long timestampTo) {

    	String query = null;
    	
    	ServiceRequest request = new ServiceRequest(new ContextHistoryService(null), null);
		
		Restriction r = Restriction.getFixedValueRestriction(
				ContextHistoryService.PROP_PROCESSES, query);
		
		request.getRequestedService().addInstanceLevelRestriction(
				r,
				new String[] {ContextHistoryService.PROP_PROCESSES});
		
		request.addSimpleOutputBinding(new ProcessOutput(OUTPUT_QUERY_RESULT),
				new PropertyPath(null, true, new String[] {
						ContextHistoryService.PROP_RETURNS}).getThePath());
    	
    	return request;
    }
}
