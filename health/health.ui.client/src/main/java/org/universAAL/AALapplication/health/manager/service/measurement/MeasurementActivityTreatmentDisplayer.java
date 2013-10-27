/*******************************************************************************
 * Copyright 2013 Universidad Politécnica de Madrid
 * Copyright 2013 Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
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

package org.universAAL.AALapplication.health.manager.service.measurement;

import org.universAAL.AALapplication.health.manager.service.GenericTreatmentDisplayService;
import org.universAAL.AALapplication.health.manager.service.measurement.ui.EditMeasurementActivityForm;
import org.universAAL.AALapplication.health.manager.service.measurement.ui.NewBloodPressureMeasurementTreatmentForm;
import org.universAAL.AALapplication.health.manager.service.measurement.ui.NewHearthRateMeasurementTreatmentForm;
import org.universAAL.AALapplication.health.manager.service.measurement.ui.NewMeasurementActivityForm;
import org.universAAL.AALapplication.health.manager.service.measurement.ui.NewWeightMeasurementTreatmentForm;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public class MeasurementActivityTreatmentDisplayer extends ServiceCallee {

    // prepare a standard error message for later use
    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    static {
    	invalidInput.addOutput(new ProcessOutput(
    			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }
	/**
	 * @param context
	 * @param realizedServices
	 */
	public MeasurementActivityTreatmentDisplayer(ModuleContext context,
			ServiceProfile[] realizedServices) {
		super(context, realizedServices);
	}

	/** {@ inheritDoc}	 */
	@Override
	public void communicationChannelBroken() {

	}

	/** {@ inheritDoc}	 */
	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		if(call == null)
		    return invalidInput;

		String operation = call.getProcessURI();
		if(operation == null)
		    return invalidInput;
		
		Resource user = call.getInvolvedUser();
		
		if (user == null || ! (user instanceof User)){
			LogUtils.logError(owner, getClass(), "handleCall", "InvolvedUser MUST BE SET!");
			return invalidInput;
		}
		
		Resource targetUser = (Resource) call.getInputValue(GenericTreatmentDisplayService.INPUT_TARGET_USER);
		if (targetUser == null ||! (targetUser instanceof AssistedPerson) ){
			LogUtils.logError(owner, getClass(), "handleCall", "Affected User MUST BE SET! and it must be an AssistedPerson");
			return invalidInput;
		}
			
		Treatment t = (Treatment) call.getInputValue(MeasurementactivityTreatmentDisplayService.INPUT_TREATMENT);
		if (t == null) {
			LogUtils.logError(owner, getClass(), "handleCall", "Treatment MUST BE SET!");
			return invalidInput;
		}
		
		if (operation.startsWith(MeasurementactivityTreatmentDisplayService.NEW_MEASURE_ACTIVITY_TREATMENT)){
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing Form for new Treatment");
			// Form to add Treatment
			new NewMeasurementActivityForm(owner,(User) user, (AssistedPerson) targetUser).show(t);
			return new ServiceResponse(CallStatus.succeeded);
		}
		

		if (operation.startsWith(MeasurementactivityTreatmentDisplayService.EDIT_MEASURE_ACTIVITY_TREATMENT)){
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing Form for Editing Treatment");
			// Form that updates treatment
			new EditMeasurementActivityForm(owner, (User) user, (AssistedPerson) targetUser);
			return new ServiceResponse(CallStatus.succeeded);
		}
		
		if (operation.startsWith(MeasurementactivityTreatmentDisplayService.NEW__BP_MEASURE_ACTIVITY_TREATMENT)){
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing Form for new BloodPressure Treat.");
			// Form that updates treatment
			new NewBloodPressureMeasurementTreatmentForm(owner, (User) user, (AssistedPerson) targetUser);
			return new ServiceResponse(CallStatus.succeeded);
		}
		if (operation.startsWith(MeasurementactivityTreatmentDisplayService.NEW__HR_MEASURE_ACTIVITY_TREATMENT)){
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing Form for new HeartRate Treat.");
			// Form that updates treatment
			new NewHearthRateMeasurementTreatmentForm(owner, (User) user, (AssistedPerson) targetUser);
			return new ServiceResponse(CallStatus.succeeded);
		}
		if (operation.startsWith(MeasurementactivityTreatmentDisplayService.NEW__WEIGHT_MEASURE_ACTIVITY_TREATMENT)){
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing Form for new Weight Treat.");
			// Form that updates treatment
			new NewWeightMeasurementTreatmentForm(owner, (User) user, (AssistedPerson) targetUser);
			return new ServiceResponse(CallStatus.succeeded);
		}
		return invalidInput;
	}
	
}
