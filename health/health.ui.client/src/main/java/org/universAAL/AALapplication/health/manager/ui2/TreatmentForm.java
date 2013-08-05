

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
package org.universAAL.AALapplication.health.manager.ui2;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Repeat;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.SubdialogTrigger;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.Treatment;

/**
 * @author amedrano
 *
 */
public class TreatmentForm extends AbstractHealthForm {

	private static final String TREATMENT_EXAPAND = HealthOntology.NAMESPACE + "subdialogTreatmentExpand";
	private HealthProfile hp;

	public TreatmentForm(ModuleContext context, User inputUser) {
		super(context, inputUser);
	}


	public void handleUIResponse(UIResponse input) {
		// TODO Manage Submit Detail
		String cmd = input.getSubmissionID();
		
		if (cmd.startsWith(TREATMENT_EXAPAND)){
			// user has selected a detail Treatment
			int index = Integer.parseInt(cmd.substring(TREATMENT_EXAPAND.length()));
			new TreatmentViewForm(context, inputUser).show(hp.getTreatments()[index]);
		}
		
	}
	
	public void show() {
		hp = getHealthProfile();
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
	
		sendForm(f);
	}
	
}
