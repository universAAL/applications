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

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.services.DisplayTreatmentService;

/**
 * @author amedrano
 *
 */
public class GenericTreatmentDisplayService extends DisplayTreatmentService {
	
	public static final ServiceProfile[] profs = new ServiceProfile[4];
	static final String NAMESPACE = "http://lst.tfo.upm.es/Health.owl#";
	static final String MY_URI = NAMESPACE + "GenericTreatmentDisplay";
	
	/**
	 * Inputs.
	 */
	public static final String INPUT_TREATMENT      = NAMESPACE + "treatment";
	public static final String NEW_GENERIC_TREATMENT = NAMESPACE + "showNewGenericTreatment";
	public static final String EDIT_GENERIC_TREATMENT = NAMESPACE + "editGenericTreatment";
	public static final String REMOVE_GENERIC_TREATMENT = NAMESPACE + "removeGenericTreatmentDisplay";
	public static final String SHOW_TREATMENT_LIST = NAMESPACE + "showTreatmentList";

	/**
	 * @param instanceURI
	 */
	public GenericTreatmentDisplayService(String instanceURI) {
		super(instanceURI);
	}

	public static void initialize(ModuleContext mc){
		OntologyManagement.getInstance().register(
				mc,
				new SimpleOntology(MY_URI, DisplayTreatmentService.MY_URI,
						new ResourceFactory() {

							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new GenericTreatmentDisplayService(instanceURI);
							}
						}));

		// NEW generic TReatment
		GenericTreatmentDisplayService gNewTreatmentDisplay = new GenericTreatmentDisplayService(NEW_GENERIC_TREATMENT);
		gNewTreatmentDisplay.addInputWithAddEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, new String[]{DisplayTreatmentService.PROP_TREATMENT});
		profs[0] = gNewTreatmentDisplay.myProfile;
		
		// EDIT generic Treatment
		GenericTreatmentDisplayService gEditTreatmentDisplay = new GenericTreatmentDisplayService(EDIT_GENERIC_TREATMENT);
		gEditTreatmentDisplay.addInputWithChangeEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, new String[]{DisplayTreatmentService.PROP_TREATMENT});
		profs[1] = gEditTreatmentDisplay.myProfile;
		
		// REMOVE generic Treatment
		GenericTreatmentDisplayService gRemoveTreatmentDisplay = new GenericTreatmentDisplayService(REMOVE_GENERIC_TREATMENT);
		gRemoveTreatmentDisplay.addInputWithRemoveEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, new String[]{DisplayTreatmentService.PROP_TREATMENT});
		profs[2] = gRemoveTreatmentDisplay.myProfile;
		
		// Treatment List Display
		GenericTreatmentDisplayService treatmentListDisplay = new GenericTreatmentDisplayService(SHOW_TREATMENT_LIST);
		profs[3] = treatmentListDisplay.myProfile;
	}

}
