/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Politï¿½cnica de Madrdid
	
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
package org.universAAL.AALapplication.health.performedSession.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.AALapplication.health.manager.ProfileServerManager;
import org.universAAL.AALapplication.health.performedSession.manager.PerformedSessionManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.ontology.profile.health.HealthProfile;
import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;

/**
 * This class actually implements the 
 * {@link org.universAAL.AALapplication.health.performedSession.manager.PerformedSessionManager} 
 * by using the profiling service.
 * 
 * @author amedrano
 * @author roni
 */
public class ProfileServerPerformedSessionManager extends ProfileServerManager 
	implements PerformedSessionManager {

    /**
     * Constructor.
     * 
     * @param context
     */
    public ProfileServerPerformedSessionManager(ModuleContext context) {

    	super(context);
    }

	/**
	 * Returns a {@java.util.List} of all the performed sessions that are 
	 * associated to the given user and treatment.
	 * 
	 * @param userURI The URI of the user who performed the sessions
	 * @param treatmentURI The URI of the associated treatment   
	 * 
	 * @return All the sessions that were performed by the user for the given
	 * treatment
	 */
	public List getAllPerformedSessions(String userURI, String treatmentURI) {

		HealthProfile profile = getHealthProfile(userURI);
		if(null != profile) {
			Treatment treament = getTreatment(profile, treatmentURI);
			if(null == treament) {
				 LogUtils.logInfo(moduleContext, ProfileServerPerformedSessionManager.class,
			    			"getAllPerformedSessions",
			    			new Object[] { "treatment " + treatmentURI + " does not exist"}, null);
			} else {
				PerformedSession[] performedSessions = treament.getPerformedSessions();
				if(null != performedSessions) {
					List list = new ArrayList(performedSessions.length);
					for(int i=0; i<performedSessions.length; i++) {
						list.add(performedSessions[i]);
					}
					return list;
				}
			}
		}
		return null;
	}

	/**
	 * Returns a {@java.util.List} of all the performed sessions that are 
	 * associated to the given user and treatment and are between the given 
	 * timestamps.
	 * 
	 * @param userURI The URI of the user who performed the sessions
	 * @param treatmentURI The URI of the associated treatment   
     * @param timestampFrom The lower bound of the period, the value -1 means 
     * that there is not a lower bound 
     * @param timestampTo The upper bound of the period, the value -1 means 
     * that there is not an upper bound 
	 * 
	 * @return The sessions that were performed by the user for the given 
	 * treatment in a specific period of time  
	 */
	public List getPerformedSessionsBetweenTimestamps(String userURI, 
			String treatmentURI, long timestampFrom, long timestampTo) {
		
		HealthProfile profile = getHealthProfile(userURI);
		if(null != profile) {
			Treatment treament = getTreatment(profile, treatmentURI);
			if(null == treament) {
				 LogUtils.logInfo(moduleContext, ProfileServerPerformedSessionManager.class,
			    			"getAllPerformedSessions",
			    			new Object[] { "treatment " + treatmentURI + " does not exist"}, null);
			} else {
				PerformedSession[] performedSessions = treament.getPerformedSessions();
				if(null != performedSessions) {
					List list = new ArrayList();
					for(int i=0; i<performedSessions.length; i++) {
						if((timestampTo == -1 && timestampFrom == -1) || 
								((timestampFrom != -1 && 
										performedSessions[i].getDate().getMillisecond() >= timestampFrom) &&
								(timestampTo != -1 && 
										performedSessions[i].getDate().getMillisecond() <= timestampTo))) {
							list.add(performedSessions[i]);
						}
					}
					return list;
				}
			}
		}
		return null;
	}
	
	/**
	 * Stores the new session that was performed by the user for the given
	 * treatment.
	 * 
	 * @param userURI The URI of the user who performed this session
	 * @param treatmentURI The URI of the associated treatment 
	 * @param session The session that was performed by the user
	 */
	public void sessionPerformed(String userURI, String treatmentURI, PerformedSession session) {

		HealthProfile profile = getHealthProfile(userURI);
		if(null != profile) {
			Treatment treament = getTreatment(profile, treatmentURI);
			if(null == treament) {
				 LogUtils.logInfo(moduleContext, ProfileServerPerformedSessionManager.class,
			    			"sessionPerformed",
			    			new Object[] { "treatment " + treatmentURI + " does not exist"}, null);
			} else {
				treament.addPerformedSession(session);
				updateHealthProfile(profile);
			}
		}
	}

	/**
	 * Returns a {org.universaal.ontology.health.owl.Treatment} of the
	 * given user in its health profile.
	 * 
	 * @param userURI The URI of the user
	 * @param treatmentURI The URI of the associated treatment 
	 * 
	 * @return A treatment of the user 
	 */
	private Treatment getTreatment(HealthProfile healthProfile, String treatmentURI) {

		// just until we fix the mix of the Health Profiles....
		org.universaal.ontology.health.owl.HealthProfile newHealthProfile = new org.universaal.ontology.health.owl.HealthProfile();
		Treatment[] treatments = newHealthProfile.getTreatments();
		
		Treatment theTreatment = null;
		if(null != treatments) {
			for(int i=0; i<treatments.length; i++) {
				if(treatments[i].getURI().equals(treatmentURI)) {
					return treatments[i];
				}
			}
		}
		return null;
	}
}
