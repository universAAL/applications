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

package org.universAAL.AALapplication.health.manager.service.genericUIs;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.services.DisplayTreatmentService;
import org.universAAL.ontology.health.owl.services.TreatmentManagementService;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public class RemoveConfirmation extends AbstractHealthForm {

	/**
	 * @param context
	 * @param inputUser
	 * @param targetUser
	 */
	public RemoveConfirmation(ModuleContext context, User inputUser,
			AssistedPerson targetUser) {
		super(context, inputUser, targetUser);
	}

	/**
	 * @param ahf
	 */
	protected RemoveConfirmation(AbstractHealthForm ahf) {
		super(ahf);
	}

	/**
	 * 
	 */
	private static final String BACK_CMD = "ups"; 
	/**
	 * 
	 */
	private static final String DELETE_CMD = "deleteNow"; 


	/** {@ inheritDoc}	 */
	@Override
	public void handleUIResponse(UIResponse uiResponse) {
	    close();
		String cmd = uiResponse.getSubmissionID();
		if (cmd.equals(DELETE_CMD)){
			// call remove treatment
			TreatmentManagementService tms = new TreatmentManagementService(null);
			tms.setProperty(TreatmentManagementService.PROP_MANAGES_TREATMENT, uiResponse.getSubmittedData());
			ServiceRequest sr = new ServiceRequest(tms, inputUser);
			sr.addRemoveEffect(new String[]{TreatmentManagementService.PROP_MANAGES_TREATMENT});
			sr.addValueFilter(new String[]{TreatmentManagementService.PROP_ASSISTED_USER}, targetUser);
			new DefaultServiceCaller(owner).call(sr);
		}
		//Call list treatment Service
		ServiceRequest sr = new ServiceRequest(new DisplayTreatmentService(null), inputUser);
		sr.addValueFilter(new String[]{DisplayTreatmentService.PROP_AFFECTED_USER}, targetUser);
		new DefaultServiceCaller(owner).call(sr);
	}
	
	public void show(Treatment t){
		Form f = Form.newDialog(getString("generic.remove.title"), t); 
		new SimpleOutput(f.getIOControls(), null, null, getString("generic.remove.text")); 
		new SimpleOutput(f.getIOControls(), null, new PropertyPath(null, false, new String[]{Treatment.PROP_NAME}), null);
		
		new Submit(f.getSubmits(), new Label(getString("generic.remove.yes"), getString("generic.remove.yes.icon")), DELETE_CMD); 
		new Submit(f.getSubmits(), new Label(getString("generic.remove.no"),getString("generic.remove.no.icon")), BACK_CMD); 
		
		sendForm(f);
	}

	@Override
	public void dialogAborted(String dialogID, Resource data) {
		// TODO Auto-generated method stub
		
	}

}
