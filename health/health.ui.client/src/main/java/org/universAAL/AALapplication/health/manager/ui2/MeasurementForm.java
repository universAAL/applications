

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

import java.util.Set;

import org.universAAL.AALapplication.health.manager.ui.InputListener;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SubdialogTrigger;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurement;

/**
 * @author amedrano
 *
 */
public class MeasurementForm extends InputListener {

	private static final String SELECTED_TREATMENT = HealthOntology.NAMESPACE + "uiMeasurementSelected";
	private static final String PREFERENCES_ICON = null;
	private static final String PREFERENCES_LABEL = null;

	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#getDialog()
	 */
	@Override
	public Form getDialog() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#handleEvent(org.universAAL.middleware.input.InputEvent)
	 */
	@Override
	public void handleEvent(UIResponse ie) {
		// TODO Auto-generated method stub

	}
	
	public void show(Resource inputUser) {
		// Create Dialog
		Form f = Form.newDialog("Treatment List", new Resource());
		
		Set<String> typesOfMeasurement = OntologyManagement
				.getInstance().getNamedSubClasses(HealthMeasurement.MY_URI, true, false);
		
		Select1 s = new Select1(f.getIOControls(),
				new Label("Select Measurement Type", null),
				new PropertyPath(SELECTED_TREATMENT, false),
				null, null);
		
		for (String type : typesOfMeasurement) {
			HealthMeasurement m = (HealthMeasurement) OntologyManagement
					.getInstance().getResource(type, Resource.generateAnonURI());
			String name = m.getName(); //TODO: find actual Measurement Type name.
			s.addChoiceItem(new ChoiceItem(name, null, m));
		}
		
		new SubdialogTrigger(f.getSubmits(), 
				new Label(PREFERENCES_LABEL, PREFERENCES_ICON),
				PREFERENCES_LABEL);
		
		new Submit(f.getSubmits(), new Label("Cancel", null), "Cancel" );
		// TODO Welcome Pane in IOControls

	}

}
