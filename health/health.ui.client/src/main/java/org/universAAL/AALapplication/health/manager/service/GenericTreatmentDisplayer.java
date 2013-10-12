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

package org.universAAL.AALapplication.health.manager.service;

import org.universAAL.AALapplication.health.manager.service.genericUIs.EditTreatmentForm;
import org.universAAL.AALapplication.health.manager.service.genericUIs.NewTreatmentForm;
import org.universAAL.AALapplication.health.manager.service.genericUIs.RemoveConfirmation;
import org.universAAL.AALapplication.health.manager.ui.TreatmentForm;
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
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public class GenericTreatmentDisplayer extends ServiceCallee {

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
	public GenericTreatmentDisplayer(ModuleContext context,
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
		
		// DO NOT COPY THIS IF IF EXTENDING THE SERVICE
		// ONLY ONE GENERIC "show Treatment List".
		if (operation.startsWith(GenericTreatmentDisplayService.SHOW_TREATMENT_LIST)) {
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing list of Treatments");
			new TreatmentForm(owner, (User) user).show();
		}
			
		Treatment t = (Treatment) call.getInputValue(GenericTreatmentDisplayService.INPUT_TREATMENT);
		if (t == null) {
			LogUtils.logError(owner, getClass(), "handleCall", "Treatment MUST BE SET!");
			return invalidInput;
		}
		
		if (operation.startsWith(GenericTreatmentDisplayService.NEW_GENERIC_TREATMENT)){
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing Form for new Treatment");
			// Form to add Treatment
			new NewTreatmentForm(owner,(User) user).show(t);
		}
		

		if (operation.startsWith(GenericTreatmentDisplayService.EDIT_GENERIC_TREATMENT)){
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing Form for Editing Treatment");
			// Form that updates treatment
			new EditTreatmentForm(owner, (User) user);
		}

		if (operation.startsWith(GenericTreatmentDisplayService.REMOVE_GENERIC_TREATMENT)){
			LogUtils.logDebug(owner, getClass(), "handleCall", "Showing confirmation for removing Treatment");
			// Form that confirms Treatment Remove.
			new RemoveConfirmation(owner, (User) user);
		}
		
		return invalidInput;
	}
	
}
