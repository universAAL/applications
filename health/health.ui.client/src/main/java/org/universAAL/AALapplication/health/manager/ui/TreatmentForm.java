

/*******************************************************************************
 * Copyright 2011 Universidad Polit�cnica de Madrid
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
package org.universAAL.AALapplication.health.manager.ui;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Repeat;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.SubdialogTrigger;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.services.DisplayTreatmentService;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public class TreatmentForm extends AbstractHealthForm {

	/**
	 * @param ahf
	 */
	protected TreatmentForm(AbstractHealthForm ahf) {
		super(ahf);
	}

	/**
	 * @param owner
	 * @param inputUser
	 * @param targetUser
	 */
	public TreatmentForm(ModuleContext owner, User inputUser,
			AssistedPerson targetUser) {
		super(owner,inputUser,targetUser);
	}

	private static final String TREATMENT_EXAPAND = HealthProfileOntology.NAMESPACE + "subdialogTreatmentExpand";
	private static final String NEW_TREATMENT = HealthProfileOntology.NAMESPACE + "newTreatmentUI";
	private static final String BACK = HealthProfileOntology.NAMESPACE + "back";
	private HealthProfile hp;


	public void handleUIResponse(UIResponse input) {
		// TODO Manage Submit Detail
		String cmd = input.getSubmissionID();
		
		if (cmd.startsWith(TREATMENT_EXAPAND)){
			// user has selected a detail Treatment
			int index = Integer.parseInt(cmd.substring(TREATMENT_EXAPAND.length()));
			// service call the most specific DisplayTreatmentService available.
			ServiceCaller sc = new DefaultServiceCaller(owner);
			ServiceRequest sr = new ServiceRequest(new DisplayTreatmentService(null), inputUser);
			sr.addChangeEffect(new String[]{DisplayTreatmentService.PROP_TREATMENT}, 
					hp.getTreatments()[index]);
			sr.addValueFilter(new String[]{DisplayTreatmentService.PROP_AFFECTED_USER}, targetUser);
			sc.call(sr);
		}
		
		if (cmd.startsWith(NEW_TREATMENT)){
			// New Treatment type Select form
			new TreatmentTypeForm(this).show();
		}
		if (cmd.startsWith(BACK)){
			new MainMenu(this).show();
		}
	}
	
	public void show() {
		hp = getHealthProfile();
		if (hp == null){
			//WARN
			LogUtils.logError(owner, getClass(), "show", "No Health Profile Found!!");
			return;
		}
		// Create Dialog
		Form f = Form.newDialog("Treatment List", hp);
		
		Repeat r = new Repeat(f.getIOControls(), new Label("Treatments", null),
				new PropertyPath(null, false, new String[] {HealthProfile.PROP_HAS_TREATMENT}), null, null);
		
		Group row = new Group(r,
				new Label("", null), null, null, null);
		new SimpleOutput(row, new Label("Name", null), 
				new PropertyPath(null, false, new String[] {Treatment.PROP_NAME}), null);
		new SimpleOutput(row, new Label("Description", null), 
				new PropertyPath(null, false, new String[] {Treatment.PROP_DESCRIPTION}), null);
		SubdialogTrigger sdt = new SubdialogTrigger(row, new Label("Detail", null), TREATMENT_EXAPAND);
		sdt.setRepeatableIDPrefix(TREATMENT_EXAPAND);
		
		// TODO add completiviness
		// TODO add more info. according to user (if Careguiver, then more info...)
		// Add remove Treatment button?
		
		new Submit(f.getSubmits(), new Label("Add New", null), NEW_TREATMENT);
	
		new Submit(f.getSubmits(), new Label("Back", null), BACK);
		
		sendForm(f);
	}
	
}
