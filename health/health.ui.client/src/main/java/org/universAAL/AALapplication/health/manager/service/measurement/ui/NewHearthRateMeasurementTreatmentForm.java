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

package org.universAAL.AALapplication.health.manager.service.measurement.ui;

import org.universAAL.AALapplication.health.manager.service.genericUIs.NewTreatmentForm;
import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.HeartRateRequirement;
import org.universAAL.ontology.health.owl.MeasurementRequirements;
import org.universAAL.ontology.health.owl.TakeMeasurementActivity;
import org.universAAL.ontology.healthmeasurement.owl.HeartRate;
import org.universAAL.ontology.measurement.Measurement;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

public class NewHearthRateMeasurementTreatmentForm extends NewTreatmentForm{

	/**
	 * @param ahf
	 */
	public NewHearthRateMeasurementTreatmentForm(AbstractHealthForm ahf) {
		super(ahf);
	}

	/**
	 * @param context
	 * @param inputUser
	 * @param targetUser
	 */
	public NewHearthRateMeasurementTreatmentForm(ModuleContext context, User inputUser,
			AssistedPerson targetUser) {
		super(context, inputUser, targetUser);
	}

	/** {@ inheritDoc}	 */
	@Override
	public void handleUIResponse(UIResponse uiResponse) {
		super.handleUIResponse(uiResponse);
		    close();
	}
	
	public void show(TakeMeasurementActivity t){
		Form f = getMeasurementActivityForm(t);
		new Submit(f.getSubmits(), new Label(getString("generic.newTreatmentadd"), null), NEW_CMD);  
		new Submit(f.getSubmits(), new Label(getString("generic.newTreatmentback"), null), BACK_CMD);  
		sendForm(f);
	}
	
	Form getMeasurementActivityForm(TakeMeasurementActivity t){
		Form f = NewTreatmentForm.getGenericTreatmentForm(t, this);
		
		//TODO conditional limits group.
		
		Group limits = new Group(f.getIOControls(), new Label(getString("pulse.newTreatment.requierements"), null), null, null, null); 
		
		
		MeasurementRequirements mr = t.getMeasurementRequirements();
		
		if (mr == null){
			mr = new HeartRateRequirement();
			t.setMeasurementRequirements(mr);
			    
		}
		
		if (mr.getMaxValueAllowed() == null){
			mr.setMaxValueAllowed(new HeartRate());
		}
		if (mr.getMinValueAllowed() == null){
			mr.setMinValueAllowed(new HeartRate());
		}
		
		InputField minVal = new InputField(limits, new Label(getString("pulse.newTreatment.min"), null),  
				new PropertyPath(null, false, new String[] {
					TakeMeasurementActivity.PROP_HAS_MEASUREMENT_REQUIREMENTS,
					MeasurementRequirements.PROP_MIN_VALUE_ALLOWED,
					Measurement.PROP_VALUE
				}), null, null);
		//XXX add hints helps and alerts
		

		InputField maxVal = new InputField(limits, new Label(getString("pulse.newTreatment.max"), null),  
			new PropertyPath(null, false, new String[] {
				TakeMeasurementActivity.PROP_HAS_MEASUREMENT_REQUIREMENTS,
				MeasurementRequirements.PROP_MAX_VALUE_ALLOWED,
				Measurement.PROP_VALUE
			}), null, null);
		//XXX add hints helps and alerts

		
		return f;
	}
	
}