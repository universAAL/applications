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
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.ui.rdf.TextArea;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.services.DisplayTreatmentService;
import org.universAAL.ontology.health.owl.services.TreatmentManagementService;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.recommendations.VerticalLayout;

public class NewTreatmentForm extends AbstractHealthForm{

	private static final String NAME_LABEL = "Name";
	private static final String DESCR_LABEL = "Description";
	private static final String NEW_LABEL = "Add";
	private static final String BACK_LABEL = "Back";
	private static final String NEW_CMD = "addNewTreatment";
	private static final String BACK_CMD = "goBack";

	/**
	 * @param context
	 * @param inputUser
	 */
	public NewTreatmentForm(ModuleContext context, User inputUser) {
		super(context, inputUser);
	}

	/** {@ inheritDoc}	 */
	@Override
	public void handleUIResponse(UIResponse uiResponse) {
		String cmd = uiResponse.getSubmissionID();
		if (cmd.startsWith(NEW_CMD)){
			// Call add Treatment
			ServiceRequest sr = new ServiceRequest(new TreatmentManagementService(null), inputUser);
			sr.addAddEffect(new String[]{TreatmentManagementService.PROP_MANAGES_TREATMENT}, uiResponse.getSubmittedData());
			sr.addValueFilter(new String[]{TreatmentManagementService.PROP_ASSISTED_USER}, inputUser);
			new DefaultServiceCaller(owner).call(sr);
		}
		//Call list treatment Service
		ServiceRequest sr = new ServiceRequest(new DisplayTreatmentService(null), inputUser);
		new DefaultServiceCaller(owner).call(sr);
		
	}
	
	public void show(Treatment t){
		Form f = getGenericTreatmentForm(t);
		new Submit(f.getSubmits(), new Label(NEW_LABEL, null), NEW_CMD);
		new Submit(f.getSubmits(), new Label(BACK_LABEL, null), BACK_CMD);
		sendForm(f);
	}
	
	static Form getGenericTreatmentForm(Treatment t){
		Form f = Form.newDialog("New Treatment", t);
		
		InputField nf = new InputField(f.getIOControls(), 
				new Label(NAME_LABEL, null),
				new PropertyPath(Resource.generateAnonURI(), true, new String[]{Treatment.PROP_NAME}),
				null,
				"");
		nf.setAlertString("Name must be a valid Aphanumeric Value");
		nf.setHelpString("set a distinctive name for this treatment");
		nf.setHintString("take weight measurement");
		
		TextArea df = new TextArea(f.getIOControls(), 
				new Label(DESCR_LABEL, null),
				new PropertyPath(Resource.generateAnonURI(), true, new String[]{Treatment.PROP_DESCRIPTION}),
				null,
				"");
		df.setAlertString("Description must be a valid Aphanumeric Value");
		df.setHelpString("set a description for this treatment");
		df.setHintString("every day, in the morning climb on the scale and tell the system your weight.");
		
		f.getIOControls().addAppearanceRecommendation(new VerticalLayout());
		return f;
	}
	
}