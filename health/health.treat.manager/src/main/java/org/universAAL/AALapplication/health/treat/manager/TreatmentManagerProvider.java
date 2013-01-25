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
package org.universAAL.AALapplication.health.treat.manager;

import java.util.List;

import org.universAAL.AALapplication.health.treat.manager.impl.ProfileServerTreatmentManager;
import org.universAAL.AALapplication.health.treat.manager.profiles.EditTreatmentService;
import org.universAAL.AALapplication.health.treat.manager.profiles.ListTreatmentBetweenTimeStampsService;
import org.universAAL.AALapplication.health.treat.manager.profiles.ListTreatmentService;
import org.universAAL.AALapplication.health.treat.manager.profiles.NewTreatmentService;
import org.universAAL.AALapplication.health.treat.manager.profiles.RemoveTreatmentService;
import org.universAAL.AALapplication.health.treat.manager.profiles.TreatmentManagerProfilesOnt;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.services.TreatmentManagementService;

/**
 * This class provides the treatment manager services.
 * 
 * @author roni
 */
public class TreatmentManagerProvider extends ServiceCallee {

	// the actual treatment manager 
	private TreatmentManager treatmentManager = null;
	
    static final ServiceProfile[] profiles = new ServiceProfile[5];
	
	// define profiles
	static {
		OntologyManagement.getInstance().register(new TreatmentManagerProfilesOnt());
    	profiles[0] = new NewTreatmentService().getProfile();		
    	profiles[1] = new RemoveTreatmentService().getProfile();
    	profiles[2] = new EditTreatmentService().getProfile();
    	profiles[3] = new ListTreatmentService().getProfile();
    	profiles[4] = new ListTreatmentBetweenTimeStampsService().getProfile();
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
    protected TreatmentManagerProvider(ModuleContext context, ServiceProfile[] realizedServices) {
		super(context, realizedServices);
    }
	
    /**
     * Constructor.
     * 
     * @param context
     */
	public TreatmentManagerProvider(ModuleContext context) {
		
		// as a service providing component, we have to extend ServiceCallee
    	// this in turn requires that we introduce which services we would like
    	// to provide to the universAAL-based AAL Space
		super(context, profiles);

		// the actual implementation of the treatment manager
		treatmentManager = new ProfileServerTreatmentManager(context);
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

		User userInput = (User) call.getInputValue(TreatmentManagementService.INPUT_USER);
		if(userInput == null)
		    return null;

		if(operation.startsWith(ListTreatmentService.MY_URI))
			return getAllTreatments(userInput);

		if(operation.startsWith(ListTreatmentBetweenTimeStampsService.MY_URI)) {
			Object timestampFromInput = call
					.getInputValue(ListTreatmentBetweenTimeStampsService.INPUT_TIMESTAMP_FROM);

			Object timestampToInput = call
					.getInputValue(ListTreatmentBetweenTimeStampsService.INPUT_TIMESTAMP_TO);	
			if (timestampFromInput != null && timestampToInput != null) {
				return getTreatmentsBetweenTimestamps(
						userInput, ((Long)timestampFromInput).longValue(), 
						((Long)timestampToInput).longValue());
			} else {
				return invalidInput;
			}
		}
		    

		if(operation.startsWith(NewTreatmentService.MY_URI)) {
			Object treatmentInput = call
			.getInputValue(NewTreatmentService.INPUT_TREATMENT);
			if (treatmentInput != null) {
				return newTreatment(userInput, (Treatment)treatmentInput);
			} else {
				return invalidInput;
			}
		}
		
		if(operation.startsWith(RemoveTreatmentService.MY_URI)) {
			Object treatmentInput = call.getInputValue(RemoveTreatmentService.INPUT_TREATMENT);
			if (treatmentInput != null) {
				return deleteTreatment(userInput, (Treatment) treatmentInput);
			} else {
				return invalidInput;
			}
		}
		
		if(	operation.startsWith(EditTreatmentService.MY_URI)) {
			Object treatmentInput = call
					.getInputValue(EditTreatmentService.INPUT_TREATMENT);

			if (treatmentInput != null) {
				return editTreatment(userInput, (Treatment)treatmentInput);
			} else {
				return invalidInput;
			}
		}

		return null;
	}

	/**
	 * Creates a service response that including all the treatments that are
	 * associated with the given user.
	 * 
	 * @param user The  user
	 */
	private ServiceResponse getAllTreatments(User user) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List treatmentsList = treatmentManager.getAllTreatments(user);
		sr.addOutput(new ProcessOutput(
				ListTreatmentService.OUTPUT_TREATMENTS, treatmentsList));
		
		return sr;		
	}

	/**
	 * Creates a service response that including all the treatments that are
	 * associated with the given user and are between the given timestamps.
	 * 
	 * @param user The user
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 */
	private ServiceResponse getTreatmentsBetweenTimestamps(
			User user, long timestampFrom, long timestampTo) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		List treatmentsList = treatmentManager.getTreatmentsBetweenTimestamps(
				user, timestampFrom, timestampTo);
		sr.addOutput(new ProcessOutput(
				ListTreatmentBetweenTimeStampsService.OUTPUT_TREATMENTS, treatmentsList));
		
		return sr;
	}
	
	/**
	 * Creates a service response for adding a new treatment for the given user.
	 * 
	 * @param user The user 
	 * @param treatment The treatment to be added
	 */
	private ServiceResponse newTreatment(User user, Treatment treatment) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		treatmentManager.newTreatment(user, treatment);
		
		return sr;
	}

	/**
	 * Creates a service response for deleting a treatment for the given user.
	 * 
	 * @param user The user 
	 * @param treatment The treatment to be deleted
	 */
	private ServiceResponse deleteTreatment(User user, Treatment treatment) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		treatmentManager.deleteTreatment(user, treatment);
		
		return sr;
	}

	/**
	 * Creates a service response for editing a treatment for the given user.
	 * 
	 * @param user The user 
	 * @param oldTreatmentURI The updated treatment
	 */
	private ServiceResponse editTreatment(User userInput,
			Treatment treatmentInput) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		treatmentManager.updateTreatment(treatmentInput);
		
		return sr;
	}
	}
