/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Polit�cnica de Madrdid
	
	OCO Source Materials
	� Copyright IBM Corp. 2011
	
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

import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.profile.User;

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
	 * Adds a new treatment definition to the user health profile.
	 * 
	 * @param user The URI of the user
	 * @param treatment The treatment to be added to the health profile
	 */
	public void newTreatment(User user, Treatment treatment);

	/**
	 * Adds a new treatment definition to the user health profile.
	 * 
	 * @param healthProfile the health profile to which to add the treatment
	 * @param treatment The treatment to be added to the health profile
	 */
	public void newTreatment(HealthProfile healthProfile, Treatment treatment);

	/**
	 * Deletes a treatment definition from the user health profile.
	 * @param user the user from which to remove the treatment
	 * @param treatment The treatment to be deleted
	 */
	public void deleteTreatment(User user, Treatment treatment);
	
	/**
	 * Deletes a treatment definition from the user health profile.
	 * @param healthProfile the health profile from which to remove the treatment
	 * @param treatment The treatment to be deleted
	 */
	public void deleteTreatment(HealthProfile healthProfile, Treatment treatment);

	/**
	 * Edits a treatment in the user health profile.
	 * @param newTreatment The updated treatment
	 */
	public void updateTreatment(Treatment newTreatment);

	/**
	 * Returns a {java.util.List} of all the treatments that are associated with
	 * the given user health profile.
	 * 
	 * @param user The user
	 * 
	 * @return All the treatments that are associated with the user 
	 */
	public List<Treatment> getAllTreatments(User user);
	
	/**
	 * Returns a {java.util.List} of all the treatments that are associated with
	 * the given user health profile.
	 * 
	 * @param profile The user's health profile.
	 * 
	 * @return All the treatments that are associated with the user 
	 */
	public List<Treatment> getAllTreatments(HealthProfile profile);
	
	/**
	 * Returns a {java.util.List} of all the treatments that are associated with 
	 * the given user health profile and are between the given timestamps.
	 * 
	 * @param user The user
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 * 
	 * @return All the treatments that are associated with the user in a 
	 * specific period of time
	 */
	public List<Treatment> getTreatmentsBetweenTimestamps(User user, 
			long timestampFrom, long timestampTo);
	
	/**
	 * Returns a {java.util.List} of all the treatments that are associated with 
	 * the given user health profile and are between the given timestamps.
	 * 
	 * @param profile The user's health profile
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 * 
	 * @return All the treatments that are associated with the user in a 
	 * specific period of time
	 */
	public List<Treatment> getTreatmentsBetweenTimestamps(HealthProfile profile, 
			long timestampFrom, long timestampTo);
}
