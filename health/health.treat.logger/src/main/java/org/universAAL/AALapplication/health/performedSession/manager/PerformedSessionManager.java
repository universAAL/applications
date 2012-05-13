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

import org.universaal.ontology.health.owl.PerformedSession;

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
	 * @param userURI The URI of the user who performed the sessions
	 * 
	 * @return All the sessions that were performed by the user 
	 */
	public List getAllPerformedSessions(String userURI);

	/**
	 * Returns a {@java.util.List} of all the performed sessions that are 
	 * associated to the given user and are between the given timestamps.
	 * 
	 * @param userURI The URI of the user who performed the sessions
     * @param timestampFrom The lower bound of the period
     * @param timestampTo The upper bound of the period
	 * 
	 * @return The sessions that were performed by the user in a specific 
	 * period of time  
	 */
	public List getPerformedSessionsBetweenTimestamps(String userURI, 
			long timestampFrom, long timestampTo);

	/**
	 * Stores the new session that was performed by the user.
	 * 
	 * @param userURI The URI of the user who performed this session
	 * @param session The session that was performed by the user
	 */
	public void sessionPerformed(String userURI, PerformedSession session);
}
