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

package org.universAAL.AALapplication.health.treat.manager.profiles;

import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.services.TreatmentManagementService;

public class NewTreatmentService extends TreatmentManagementService {

	//NAMESPACE & PROPERTIES
	public static final String MY_URI = TreatmentManagerProfilesOnt.NAMESPACE
		+ "AddTreatmentService";
	
	//INPUT PARAMETERS URI
	public static final String INPUT_USER      = HealthOntology.NAMESPACE + "user";
	public static final String INPUT_TREATMENT      = HealthOntology.NAMESPACE + "treatment";

	//CONSTRUCTOR
	public NewTreatmentService(String uri) {
		super(uri);
		buildProfile();
	}

	public NewTreatmentService() {
		super();
		buildProfile();
	}		
	
	private void buildProfile() {
		addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
    	addInputWithAddEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, 
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
