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

import java.util.Set;

import org.universAAL.AALapplication.health.manager.ui.measurements.BloodPreasureMeasurement;
import org.universAAL.AALapplication.health.manager.ui.measurements.HeartRateMeasurement;
import org.universAAL.AALapplication.health.manager.ui.measurements.Motivation;
import org.universAAL.AALapplication.health.manager.ui.measurements.WeigthMeasurement;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.healthmeasurement.owl.BloodPressure;
import org.universAAL.ontology.healthmeasurement.owl.HealthMeasurement;
import org.universAAL.ontology.healthmeasurement.owl.HeartRate;
import org.universAAL.ontology.healthmeasurement.owl.PersonWeight;

/**
 * @author amedrano
 *
 */
public class MeasurementTypeForm extends AbstractHealthForm {

	/**
	 * @param ahf
	 */
	public MeasurementTypeForm(AbstractHealthForm ahf) {
		super(ahf);
	}


	private static final String SELECTED_TREATMENT = HealthProfileOntology.NAMESPACE + "uiMeasurementSelected";
	private static final String OK_LABEL = "Select";
	private static final String OK_ICON = null;
	private static final String CANCEL_LABEL = "Cancel";
	private static final String CANCEL_ICON = null;
	

	public void show() {
		// Create Dialog
		Form f = Form.newDialog("Take A Measurement: Which Type?", new Resource());
		
		Set<String> typesOfMeasurement = OntologyManagement
				.getInstance().getNamedSubClasses(HealthMeasurement.MY_URI, true, false);
		
		Select1 s = new Select1(f.getIOControls(),
				new Label("Select Measurement Type", null),
				new PropertyPath(null, false, new String[]{SELECTED_TREATMENT}),
				null, null);
		
		for (String type : typesOfMeasurement) {
			HealthMeasurement m = (HealthMeasurement) OntologyManagement
					.getInstance().getResource(type, Resource.generateAnonURI());
			String name = m.getOrConstructLabel(null);
			s.addChoiceItem(new ChoiceItem(name, null, m));
		}
		
		new Submit(f.getSubmits(), 
				new Label(OK_LABEL, OK_ICON),
				OK_LABEL);
		
		Submit su = new Submit(f.getSubmits(), new Label(CANCEL_LABEL, CANCEL_ICON), CANCEL_LABEL );
		su.addMandatoryInput(s);
		
		sendForm(f);
	}


	@Override
	public void handleUIResponse(UIResponse arg0) {
		String cmd = arg0.getSubmissionID();
		if (cmd.equalsIgnoreCase(CANCEL_LABEL)){
			new MainMenu(this).show();
		}
		if (cmd.equalsIgnoreCase(OK_LABEL)){
			HealthMeasurement hm = (HealthMeasurement) arg0.getUserInput(new String[]{SELECTED_TREATMENT});
			// TODO: create a service in ont.health and call the service accordingly
			LogUtils.logDebug(owner, getClass(), "handleUIResponse", "handling Treatment of type:" + hm.getClassURI());
			if (hm instanceof PersonWeight){
				new WeigthMeasurement(this, (PersonWeight) hm).show();
			}
			else if (hm instanceof BloodPressure){
				new Motivation(this).show();
				new BloodPreasureMeasurement(this, (BloodPressure) hm).show();
			}
			else if (hm instanceof HeartRate){
				new HeartRateMeasurement(this, (HeartRate) hm).show();
			}
			else {
				new NotReady(this).show();
			}
			
		}
	}

}
