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
package org.universAAL.AALapplication.health.performedSession.manager;

import java.util.List;

import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;

/**
 * Interface for the actual performed sessions storage and retrieval.
 * 
 * Implementations of this interface go in 
 * {@link org.universAAL.AALapplication.health.performedSession.manager.impl}
 * package; this way the actual storage of performed sessions can be expanded to 
 * other methods and service providers can select from these methods the one 
 * that fits best and has best performance, or they can implement their own 
 * storage method.
 * 
 * @author amedrano
 * @author roni
 */
public interface PerformedSessionManager {

	/**
	 * Returns a {@java.util.List} of all the performed sessions that are 
	 * associated to the given user.
	 * 
	 * @param user The user who performed the sessions
	 * @return All the sessions that were performed by the user for the given
	 * treatment
	 */
	public List<PerformedSession> getAllPerformedSessions(User user);

	/**
	 * Returns a {@java.util.List} of all the performed sessions that are 
	 * associated to the given user and are between the given 
	 * timestamps.
	 * 
	 * @param user The user who performed the sessions
	 * @param timestampFrom The lower bound of the period
	 * @param timestampTo The upper bound of the period
	 * @return The sessions that were performed by the user for the given 
	 * treatment in a specific period of time  
	 */
	public List<PerformedSession> getPerformedSessionsBetweenTimestamps(User user, 
			long timestampFrom, long timestampTo);
	
	/**
	 * Returns a {@java.util.List} of all the performed sessions that are 
	 * associated to the given user and treatment.
	 * 
	 * @param user The user who performed the sessions
	 * @param treatment The associated treatment   
	 * 
	 * @return All the sessions that were performed by the user for the given
	 * treatment
	 */
	public List<PerformedSession> getTreatmentPerformedSessions(User user, Treatment treatment);

	/**
	 * Returns a {@java.util.List} of all the performed sessions that are 
	 * associated to the given user and treatment and are between the given 
	 * timestamps.
	 * 
	 * @param user The user who performed the sessions
	 * @param treatment The associated treatment   
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 * 
	 * @return The sessions that were performed by the user for the given 
	 * treatment in a specific period of time  
	 */
	public List<PerformedSession> getTreatmentPerformedSessionsBetweenTimestamps(User user, 
			Treatment treatment, long timestampFrom, long timestampTo);
	/**
	 * Stores the new session that was performed by the user for the given
	 * treatment.
	 * 
	 * @param user The URI of the user who performed this session
	 * @param session The session that was performed by the user
	 */
	public void sessionPerformed(User user, PerformedSession session);
}
