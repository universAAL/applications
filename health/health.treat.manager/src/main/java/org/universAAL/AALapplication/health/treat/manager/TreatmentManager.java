/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Politécnica de Madrdid
	
	OCO Source Materials
	© Copyright IBM Corp. 2011
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.AALapplication.health.treat.manager;

import java.util.List;

import org.universaal.ontology.health.owl.Treatment;

/**
 * Interface for the treatment definition storage and retrieval.
 * 
 * Implementations of this interface go in 
 * {@link org.universAAL.AALapplication.health.treat.manager.impl} package; this 
 * way the actual storage of treatments can be expanded to other methods and 
 * service providers can select from these methods the one that fits best and 
 * has best performance, or they can implement their own storage method.
 * 
 * @author amedrano
 * @author roni
 */
public interface TreatmentManager {

	/**
	 * Adds a new treatment definition to the user profile.
	 * 
	 * @param userURI The URI of the user
	 * @param treatment The treatment to be added to the user profile
	 */
	public void newTreatment(String userURI, Treatment treatment);

	/**
	 * Deletes a treatment definition from the user profile.
	 * 
	 * @param userURI The URI of the user
	 * @param treatmentURI The treatment to be deleted
	 */
	public void deleteTreatment(String userURI, String treatmentURI);

	/**
	 * Edits a treatment in the user profile.
	 * 
	 * @param userURI The URI of the user
	 * @param oldTreatmentURI The URI of the treatment to be changed
	 * @param newTreatment The new treatment
	 */
	public void editTreatment(String userURI, String oldTreatmentURI, 
			Treatment newTreatment);

	/**
	 * Returns a {@java.util.List} of all the definition of the treatments that
	 * are associated with the given user.
	 * 
	 * @param userURI The URI of the user
	 * 
	 * @return All the treatment definitions that are associated with the user 
	 */
	public List getAllTreatments(String userURI);
	
	/**
	 * Returns a {@java.util.List} of all the definition of the treatments that
	 * are associated with the given user and are between the given timestamps.
	 * 
	 * @param userURI The URI of the user
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 * 
	 * @return All the treatment definitions that are associated with the user 
	 * in a specific period of time
	 */
	public List getTreatmentsBetweenTimestamps(String userURI, 
			long timestampFrom, long timestampTo);
}
