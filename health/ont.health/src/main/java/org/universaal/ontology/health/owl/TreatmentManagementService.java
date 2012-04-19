/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 Universidad Politécnica de Madrid
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

package org.universaal.ontology.health.owl;

import org.universAAL.middleware.service.owl.Service;

/**
 * This class describes the treatment management service, 
 * which consists of editing, creating and deleting a treatment.
 * Also, this service lists the treatments associated to the 
 * health profile of an assisted person.
 * @author mdelafuente
 */

public class TreatmentManagementService extends HealthService{

	//NAMESPACE & PROPERTIES
	public static final String MY_URI = HealthOntology.NAMESPACE
	+ "TreatmentManagementService";

	public static final String PROP_MANAGES_TREATMENT = HealthOntology.NAMESPACE
	+ "managesTreatments";
	public static final String PROP_LISTS_TREATMENTS =  HealthOntology.NAMESPACE
	+ "listsTreatments";
	
	//CONSTRUCTORS
	public TreatmentManagementService() {
		super();
	}

	public TreatmentManagementService(String uri) {
		super(uri);
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
		return  PROP_MANAGES_TREATMENT.equals(propURI) ||
		PROP_LISTS_TREATMENTS.equals(propURI) ? PROP_SERIALIZATION_FULL : super
				.getPropSerializationType(propURI);
	}

	public boolean isWellFormed() {
		return true;
	}
}
