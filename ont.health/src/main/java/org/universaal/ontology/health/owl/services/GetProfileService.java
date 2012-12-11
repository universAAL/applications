/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 * Universidad Politécnica de Madrid
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

package org.universaal.ontology.health.owl.services;

import org.universAAL.ontology.profile.AssistedPerson;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.HealthProfile;

/**
 * 
 * @author mdelafuente
 * 
 * @navassoc - "manages" - HealthProfile
 */
public class GetProfileService extends ProfileManagementService{

	//NAMESPACE & PROPERTIES
	public static final String MY_URI = HealthOntology.NAMESPACE
	+ "GetProfileService";

	private void buildProfile() {
		addFilteringInput(INPUT_USER, AssistedPerson.MY_URI, 1, 1, new String[] {PROP_ASSISTED_USER});
		addOutput(OUTPUT_PROFILE, HealthProfile.MY_URI, 0, 1, new String[] {PROP_MANAGES_PROFILE});
	}

    //CONSTRUCTORS
	public GetProfileService() {
		super();
		buildProfile();
	}

	public GetProfileService(String uri) {
		super(uri);
		buildProfile();
	}

	public String getClassURI() {
		return MY_URI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
	 * (java.lang.String)
	 */
	public int getPropSerializationType(String propURI) {
		return  PROP_MANAGES_PROFILE.equals(propURI) 
		 ? PROP_SERIALIZATION_FULL : super
				.getPropSerializationType(propURI);
	}

	public boolean isWellFormed() {
		return true;
	}
}
