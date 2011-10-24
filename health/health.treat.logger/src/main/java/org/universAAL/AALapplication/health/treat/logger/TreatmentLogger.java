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
package org.universAAL.AALapplication.health.treat.logger;

import java.util.List;

import org.universAAL.AALapplication.health.ont.treatment.Treatment;

/**
 * Interface for the actual treatments storage and retrieval.
 * 
 * Implementations of this interface go in 
 * {@link org.universAAL.AALapplication.health.treat.logger.impl}
 * package; this way the actual storage of treatments can be expanded to other 
 * methods and service providers can select from these methods the one that fits 
 * best and has best performance, or they can implement their own storage 
 * method.
 * 
 * @author amedrano
 * @author roni
 */
public interface TreatmentLogger {

	/**
	 * Returns a {@java.util.List} of all the treatments in the treatment log 
	 * that are associated to the given user.
	 * 
	 * @param userURI The URI of the user who performed the treatments
	 * 
	 * @return All the treatments that were performed by the user 
	 */
	public List getAllTreatmentLog(String userURI);

	/**
	 * Returns a {@java.util.List} of all the treatments in the treatment log 
	 * that are associated to the given user and are between the given timestamps.
	 * 
	 * @param userURI The URI of the user who performed the treatments
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 * 
	 * @return The treatments that were performed by the user in a specific 
	 * period of time  
	 */
	public List getTreatmentLogBetweenTimestamps(String userURI, 
			long timestampFrom, long timestampTo);

	/**
	 * Stores the new treatment that was performed by the user.
	 * 
	 * @param userURI The URI of the user who performed this treatment
	 * @param treatment The treatment that was performed by the user
	 */
	public void treatmentDone(String userURI, Treatment treatment);
}
