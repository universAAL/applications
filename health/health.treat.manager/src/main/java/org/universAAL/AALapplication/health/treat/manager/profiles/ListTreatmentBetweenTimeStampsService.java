/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 * Universidad Politécnica de Madrid
 * 
 *	OCO Source Materials
 *	� Copyright IBM Corp. 2011
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

packaorg.universAAL.AALapplication.health.treat.manager.profilesces;

import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.services.TreatmentManagementService;

/**
 * @author amedrano
 * @author roni
 *
 * @navassoc - "lists" * Treatment
 */
public class ListTreatmentBetweenTimeStampsService extends TreatmentManagementService {

	//NAMESPACE & PROPERTIES
	public static final String MY_URI = HealthOntology.NAMESPACE
			+ "ListTreatmentBetweenTimeStampsService";

	//CONSTRUCTOR	
	public ListTreatmentBetweenTimeStampsService() {
		super();
		buildProfile();
	}		
	
	public ListTreatmentBetweenTimeStampsService(String instanceURI) {
		super(instanceURI);
		buildProfile();
	}

	private void buildProfile() {
		addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
    	addFilteringInput(INPUT_TIMESTAMP_FROM, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_FROM });
    	addFilteringInput(INPUT_TIMESTAMP_TO, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_TO });
    	addOutput(OUTPUT_TREATMENTS, Treatment.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES_TREATMENT });
	}
	
	public String getClassURI() {
		return MY_URI;
	}

	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true;
	}
}
