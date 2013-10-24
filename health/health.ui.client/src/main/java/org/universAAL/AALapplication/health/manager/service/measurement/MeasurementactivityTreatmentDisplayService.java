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

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.health.owl.TakeMeasurementActivity;
import org.universAAL.ontology.health.owl.services.DisplayTreatmentService;
import org.universAAL.ontology.profile.AssistedPerson;

/**
 * @author amedrano
 *
 */
public class MeasurementactivityTreatmentDisplayService extends DisplayTreatmentService {
	
	public static final ServiceProfile[] profs = new ServiceProfile[2];
	static final String NAMESPACE = "http://lst.tfo.upm.es/Health.owl#";
	static final String MY_URI = NAMESPACE + "GenericTreatmentDisplay";
	
	/**
	 * Inputs.
	 */
	public static final String INPUT_TREATMENT      = NAMESPACE + "treatment";
	public static final String NEW_MEASURE_ACTIVITY_TREATMENT = NAMESPACE + "showNewMeasurementActivity";
	public static final String EDIT_MEASURE_ACTIVITY_TREATMENT = NAMESPACE + "editMeasurementActivity";
	private static final String INPUT_TARGET_USER = NAMESPACE + "targetUser";

	/**
	 * @param instanceURI
	 */
	public MeasurementactivityTreatmentDisplayService(String instanceURI) {
		super(instanceURI);
	}

	public static void initialize(ModuleContext mc){
		OntologyManagement.getInstance().register(
				mc,
				new SimpleOntology(MY_URI, DisplayTreatmentService.MY_URI,
						new ResourceFactory() {

							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new MeasurementactivityTreatmentDisplayService(instanceURI);
							}
						}));

		// NEW generic TReatment
		MeasurementactivityTreatmentDisplayService gNewTreatmentDisplay = new MeasurementactivityTreatmentDisplayService(NEW_MEASURE_ACTIVITY_TREATMENT);
		gNewTreatmentDisplay.addFilteringInput(INPUT_TARGET_USER, AssistedPerson.MY_URI, 1, 1, new String[]{DisplayTreatmentService.PROP_AFFECTED_USER});
		gNewTreatmentDisplay.addInputWithAddEffect(INPUT_TREATMENT, TakeMeasurementActivity.MY_URI, 1, 1, new String[]{DisplayTreatmentService.PROP_TREATMENT});
		profs[0] = gNewTreatmentDisplay.myProfile;
		
		// EDIT generic Treatment
		MeasurementactivityTreatmentDisplayService gEditTreatmentDisplay = new MeasurementactivityTreatmentDisplayService(EDIT_MEASURE_ACTIVITY_TREATMENT);
		gEditTreatmentDisplay.addFilteringInput(INPUT_TARGET_USER, AssistedPerson.MY_URI, 1, 1, new String[]{DisplayTreatmentService.PROP_AFFECTED_USER});
		gEditTreatmentDisplay.addInputWithChangeEffect(INPUT_TREATMENT, TakeMeasurementActivity.MY_URI, 1, 1, new String[]{DisplayTreatmentService.PROP_TREATMENT});
		profs[1] = gEditTreatmentDisplay.myProfile;
	}

}
