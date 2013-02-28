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

import org.universAAL.AALapplication.health.performedSession.manager.PerformedSessionManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.PerformedMeasurementSession;
import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.services.ProfileManagementService;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurement;

/**
 * This class actually implements the 
 * {@link org.universAAL.AALapplication.health.performedSession.manager.PerformedSessionManager} 
 * by using the profiling service.
 * 
 * @author amedrano
 * @author roni
 */
public class ProfileServerPerformedSessionManager  
	implements PerformedSessionManager {

    private static final String REQ_OUTPUT_PROFILE = "http://ontologies.universaal.org/PerformedSessionManager#profile";
	private ModuleContext mc;

	/**
     * Constructor.
     * 
     * @param context
     */
    public ProfileServerPerformedSessionManager(ModuleContext context) {
    	mc = context;
    	//super(context);
    }

	/** {@inheritDoc} */
	public List<PerformedSession> getAllPerformedSessions(User user) {

		HealthProfile profile = getHealthProfile(user);
		if(null != profile) {
			Treatment[] allTreatments = profile.getTreatments();
			List<PerformedSession> sessions = new ArrayList<PerformedSession>();
			for (int i = 0; i < allTreatments.length; i++) {
				Treatment treatment = allTreatments[i];
				PerformedSession[] performedSessions = treatment.getPerformedSessions();
				if(null != performedSessions) {
					for(int j=0; i<performedSessions.length; j++) {
						sessions.add(performedSessions[j]);
					}
				}

			}
			return sessions;
		}
		return null;
	}

	/** {@inheritDoc} */
	public List<PerformedSession> getPerformedSessionsBetweenTimestamps(
			User user, long timestampFrom, long timestampTo) {

		HealthProfile profile = getHealthProfile(user);
		if(null != profile) {
			Treatment[] allTreatments = profile.getTreatments();
			List<PerformedSession> sessions = new ArrayList<PerformedSession>();
			for (int i = 0; i < allTreatments.length; i++) {
				Treatment treatment = allTreatments[i];
				PerformedSession[] performedSessions = treatment.getPerformedSessions();
				if(null != performedSessions) {
					for(int j=0; i<performedSessions.length; j++) {
						if((timestampTo == -1 && timestampFrom == -1) || 
								((timestampFrom != -1 && 
								performedSessions[i].getSessionStartTime().getMillisecond() >= timestampFrom) &&
								(timestampTo != -1 && 
								performedSessions[i].getSessionStartTime().getMillisecond() <= timestampTo))) {
							sessions.add(performedSessions[j]);
						}
					}
				}

			}
			return sessions;
		}
		return null;
	}

	/** {@inheritDoc} */
	public List<PerformedSession> getTreatmentPerformedSessions(User user, Treatment treatment) {

		HealthProfile profile = getHealthProfile(user);
		if(null != profile) {
			if(null == treatment) {
				 LogUtils.logInfo(mc, ProfileServerPerformedSessionManager.class,
			    			"getAllPerformedSessions",
			    			new Object[] { "treatment " + treatment + " does not exist"}, null);
			} else {
				PerformedSession[] performedSessions = treatment.getPerformedSessions();
				if(null != performedSessions) {
					List<PerformedSession> list = new ArrayList<PerformedSession>(performedSessions.length);
					for(int i=0; i<performedSessions.length; i++) {
						list.add(performedSessions[i]);
					}
					return list;
				}
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	public List<PerformedSession> getTreatmentPerformedSessionsBetweenTimestamps(User user,
			Treatment treatment, long timestampFrom, long timestampTo) {
		
		HealthProfile profile = getHealthProfile(user);
		if(null != profile) {
			//Treatment treament = getTreatment(profile, treatment);
			if(null == treatment) {
				 LogUtils.logInfo(mc, ProfileServerPerformedSessionManager.class,
			    			"getAllPerformedSessions",
			    			new Object[] { "treatment " + treatment + " does not exist"}, null);
			} else {
				PerformedSession[] performedSessions = treatment.getPerformedSessions();
				if(null != performedSessions) {
					List<PerformedSession> list = new ArrayList<PerformedSession>();
					for(int i=0; i<performedSessions.length; i++) {
						if((timestampTo == -1 && timestampFrom == -1) || 
								((timestampFrom != -1 && 
										performedSessions[i].getSessionStartTime().getMillisecond() >= timestampFrom) &&
								(timestampTo != -1 && 
										performedSessions[i].getSessionStartTime().getMillisecond() <= timestampTo))) {
							list.add(performedSessions[i]);
						}
					}
					return list;
				}
			}
		}
		return null;
	}
	
	/** {@inheritDoc} */
	public void sessionPerformed(User user, PerformedSession session) {

		HealthProfile profile = getHealthProfile(user);
		if(null != profile) {
			boolean needsUpdate = false;
			Treatment treatment;
			if (session instanceof PerformedMeasurementSession) {
				HealthMeasurement hm = ((PerformedMeasurementSession) session)
						.getHealthMeasurement();
				profile.updateHealthMeasurement(hm);
				needsUpdate = true;
			}
			treatment = session.getAssociatedTreatment();
			//TODO find the associated Treatment. if treatment == null or not in the list of treatments.
			
			if(null == treatment) {
				 LogUtils.logInfo(mc, ProfileServerPerformedSessionManager.class,
			    			"sessionPerformed",
			    			new Object[] { "treatment: " + treatment.getURI() + " does not exist"}, null);
			} else {
				treatment.addPerformedSession(session);
				needsUpdate = true;
			}
			if (needsUpdate) {
				updateHealthProfile(profile);
			}
		}
	}	
	
	/**
	 * @param profile
	 */
	private void updateHealthProfile(HealthProfile profile) {
		ServiceRequest req = new ServiceRequest(new ProfileManagementService(null), null);
		req.addChangeEffect(new String[] {ProfileManagementService.PROP_ASSISTED_USER_PROFILE}, profile);
		
		new DefaultServiceCaller(mc).call(req);
	}

	/**
	 * @param user
	 * @return
	 */
	private HealthProfile getHealthProfile(User user) {
		ServiceRequest req = new ServiceRequest(new ProfileManagementService(null), null);
		req.addValueFilter(new String[] {ProfileManagementService.PROP_ASSISTED_USER}, user);
		req.addRequiredOutput(REQ_OUTPUT_PROFILE, new String[] {ProfileManagementService.PROP_ASSISTED_USER_PROFILE});
		
		ServiceResponse sr = new DefaultServiceCaller(mc).call(req);
		if (sr.getCallStatus() == CallStatus.succeeded) {
			return (HealthProfile) sr.getOutput(REQ_OUTPUT_PROFILE, false).get(0);
		}
		
		return null;
	}
}
