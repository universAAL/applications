/*******************************************************************************
 * Copyright 2013 Universidad Politécnica de Madrid
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

package org.universAAL.AALApplication.health.profile.manager;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.services.ProfileManagementService;
import org.universAAL.ontology.profile.AssistedPerson;

/**
 * @author amedrano
 * 
 */
public class HealthProfileService extends ProfileManagementService {

	/**
	 * @param instanceURI
	 */
	public HealthProfileService(String instanceURI) {
		super(instanceURI);
	}

	static final ServiceProfile[] profs = new ServiceProfile[2];
	static final String NAMESPACE = "http://lst.tfo.upm.es/Health.owl#";
	static final String MY_URI = NAMESPACE + "HealthProfileManager";
	
	
	// GET HEALTH PROFILE
	public static final String GET_HEALTH_PROFILE = NAMESPACE + "getHealthProfile";
	//INPUT PARAMETERS
	public static final String INPUT_PROFILE      = NAMESPACE + "healthProfile";
	public static final String INPUT_USER      = NAMESPACE + "user";
    //OUTPUT PARAMETERS URI    
	public static final String OUTPUT_PROFILE = NAMESPACE + "matchingHealthProfile";
	
	// UPDATE
	public static final String UPDATE_HEALTH_PROFILE = NAMESPACE + "updateHealthProfile";


	public static void initialize(ModuleContext mc){
		OntologyManagement.getInstance().register(
				mc,
				new SimpleOntology(MY_URI, ProfileManagementService.MY_URI,
						new ResourceFactory() {

							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new HealthProfileService(instanceURI);
							}
						}));

		// SProfile for Getting HealthProfile
		HealthProfileService getHP = new HealthProfileService(
				GET_HEALTH_PROFILE);
		getHP.addFilteringInput(INPUT_USER, AssistedPerson.MY_URI, 1, 1, new String[] {PROP_ASSISTED_USER});
		getHP.addOutput(OUTPUT_PROFILE, HealthProfile.MY_URI, 0, 1, new String[] {PROP_ASSISTED_USER_PROFILE});
		profs[0] = getHP.myProfile;
		
		// SProfile for updating HealthProfile
		HealthProfileService upHP = new HealthProfileService(UPDATE_HEALTH_PROFILE);
		upHP.addInputWithChangeEffect(INPUT_PROFILE, HealthProfile.MY_URI, 0, 1, new String[] {PROP_ASSISTED_USER_PROFILE});
		//upHP.addFilteringInput(INPUT_USER, AssistedPerson.MY_URI, 1, 1, new String[] {PROP_ASSISTED_USER});
		//upHP.addOutput(OUTPUT_PROFILE, HealthProfile.MY_URI, 0, 1, new String[] {PROP_ASSISTED_PERSON_PROFILE});
		profs[1] = upHP.myProfile;
	}

}
