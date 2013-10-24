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
package org.universAAL.AALapplication.health.performedSession.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.AALapplication.health.performedSession.manager.PerformedSessionManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.PerformedMeasurementSession;
import org.universAAL.ontology.health.owl.PerformedSession;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.services.ProfileManagementService;
import org.universAAL.ontology.healthmeasurement.owl.HealthMeasurement;
import org.universAAL.ontology.profile.User;

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
			// find the associated Treatment. if treatment == null or not in the list of treatments.
			if (treatment == null){
				treatment = findTreatmentFor(profile,session);
			}
			
			if(null == treatment) {
				 LogUtils.logInfo(mc, ProfileServerPerformedSessionManager.class,
			    			"sessionPerformed",
			    			new Object[] { "Cannot find Treatment for: " + session.getURI()}, null);
			} else {
				 LogUtils.logInfo(mc, ProfileServerPerformedSessionManager.class,
			    			"sessionPerformed",
			    			new Object[] { "Treatment for: " + session.getURI(), "\n is: " + treatment.getURI()}, null);
				treatment.addPerformedSession(session);
				needsUpdate = true;
				
//				if (session instanceof PerformedMeasurementSession
//						&& treatment.getMeasurementRequirements() != null){
//					//TODO: check for possible irregularities in the measurements and warn in that case.
//				}
			}
			if (needsUpdate) {
				updateHealthProfile(profile);
			}
		}
	}	
	
	/**
	 * will try to locate the treatment most fit that handles the performedsession.
	 * @param profile
	 * @param session
	 * @return
	 */
	private Treatment findTreatmentFor(HealthProfile profile,
			PerformedSession session) {
		Treatment[] ts = profile.getTreatments();
		Treatment selected = null;
		int selectedCalification = 0;
		for (int i = 0; i < ts.length; i++) {
			Treatment test = (Treatment) ts[i].deepCopy();
			PerformedSession[] sessions = test.getPerformedSessions();
			if (test.addPerformedSession(session)){
				//it was semantically allowed.
				int tsiCalification = 0;
				
				/*
				 * Check the session and treatments dates are compatible.
				 */
				TreatmentPlanning tp = test.getTreatmentPlanning();
				if (tp != null){
					XMLGregorianCalendar sD, teD;
					teD = tp.getEndDate();
					sD = session.getSessionEndTime();
					if (teD != null && sD != null
							&& teD.compare(sD) <= 0){
						tsiCalification += 2;
					}
				}
				
				/*
				 * check that the set of performed sessions has the same type.
				 */
				boolean tsiFitsPerfomedSessions = false;
				for (int j = 0; j < sessions.length; j++) {
					tsiFitsPerfomedSessions |= PerformedSession.checkCompatibility(sessions[i].getClassURI(), session.getClassURI());
				}
				if (tsiFitsPerfomedSessions){
					tsiCalification += 1;
				}
				if (selected == null
						&& tsiCalification >0 ){
					// set Selected
					selected = ts[i];
					selectedCalification = tsiCalification;
				} else if (tsiCalification >= selectedCalification){
					// check if ts[i] is more specific than selected
					if (ManagedIndividual.checkCompatibility(selected.getClassURI(),ts[i].getClassURI())){
						//selected is a super class of ts[i] or is the same class
						// set Selected
						selected = ts[i];
						selectedCalification = tsiCalification;
					}
				}
			}
		}
		return selected;
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
