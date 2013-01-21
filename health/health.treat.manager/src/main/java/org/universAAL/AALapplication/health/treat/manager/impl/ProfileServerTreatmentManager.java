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
package org.universAAL.AALapplication.health.treat.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.AALapplication.health.treat.manager.TreatmentManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.TreatmentPlanning;
import org.universaal.ontology.health.owl.services.ProfileManagementService;

/**
 * This class actually implements the 
 * {@link org.universAAL.AALapplication.health.treat.manager.TreatmentManager} 
 * by using the profiling service.
 * 
 * @author amedrano
 * @author roni
 */
public class ProfileServerTreatmentManager //extends MapHealthProfileProvider 
	implements TreatmentManager {

    private static final String REQ_OUTPUT_PROFILE = "http://ontologies.universaal.org/TreatmentManager#profile";
	private ModuleContext mc;

	/**
     * Constructor.
     * 
     * @param context
     */
	 public ProfileServerTreatmentManager(ModuleContext context) {
		 mc = context;
	 }
	 	
	 /**
	  * Adds a new treatment definition to the user health profile.
	  * 
	  * @param userURI The URI of the user
	  * @param treatment The treatment to be added to the health profile
	  */
	 public void newTreatment(String userURI, Treatment treatment) {
		 
		 HealthProfile profile = getHealthProfile(userURI);
		 	profile.addTreatment(treatment);
		 	// TODO expand planned sessions...
		 	
			updateHealthProfile(profile);
	 }

	/**
	 * Deletes a treatment definition from the user health profile.
	 * 
	 * @param userURI The URI of the user
	 * @param treatmentURI The treatment to be deleted
	 */
	public void deleteTreatment(String userURI, String treatmentURI) {

		 HealthProfile profile = getHealthProfile(userURI);
		 if(null != profile) {
			 if(profile.deleteTreatment(treatmentURI)) {
				  updateHealthProfile(profile);
			 } else {
				 LogUtils.logInfo(mc, ProfileServerTreatmentManager.class,
		    			"deleteTreatment",
		    			new Object[] { "treatment " + treatmentURI + " does not exist"}, null);
			 }
		 }
	}

	/**
	 * Edits a treatment in the user health profile.
	 * 
	 * @param userURI The URI of the user
	 * @param treatmentURI The URI of the treatment to be changed
	 * @param newTreatment The new treatment 
	 */
	public void editTreatment(String userURI, String treatmentURI, 
			Treatment newTreatment) {

		// check that the new treatment has the same URI
		if(!treatmentURI.equals(newTreatment.getURI())) {
			 LogUtils.logError(mc, ProfileServerTreatmentManager.class,
					 "editTreatment",
					 new Object[] { "new treatment URI does not equal to " + treatmentURI}, null);
			return;
		}
		
		 HealthProfile profile = getHealthProfile(userURI);
		 if(null != profile) {
			 if(profile.editTreatment(newTreatment)) {
				 //TODO re expand planned sessions
				  updateHealthProfile(profile);
			 } else {
				 LogUtils.logInfo(mc, ProfileServerTreatmentManager.class,
		    			"editTreatment",
		    			new Object[] { "treatment " + treatmentURI + " does not exist"}, null);
			 }
		 }
	}

	/**
	 * Returns a {java.util.List} of all the treatments that are associated with
	 * the given user health profile.
	 * 
	 * @param userURI The URI of the user
	 * 
	 * @return All the treatments that are associated with the user 
	 */
	public List<Treatment> getAllTreatments(String userURI) {
		
		HealthProfile profile = getHealthProfile(userURI);
		if(null != profile) {
			Object propList = profile.getProperty(HealthProfile.PROP_HAS_TREATMENT);
			if (propList instanceof List) {
				return ((List<Treatment>) propList);
			} else {
				List<Treatment> al = new ArrayList<Treatment>();
				if (propList != null && propList instanceof Treatment) {
					al.add((Treatment) propList);
				}
				return al;
			}
		}
		return null;
	}
		
	/**
	 * Returns a {java.util.List} of all the treatments that are associated with 
	 * the given user health profile and are between the given timestamps.
	 * 
	 * @param userURI The URI of the user
     * @param timestampFrom The lower bound of the period, the value -1 means 
     * that there is not a lower bound 
     * @param timestampTo The upper bound of the period, the value -1 means 
     * that there is not an upper bound 
	 * 
	 * @return All the treatments that are associated with the user in a
	 * specific period of time
	 */
	public List<Treatment> getTreatmentsBetweenTimestamps(String userURI, 
			long timestampFrom, long timestampTo) {
		
		HealthProfile profile = getHealthProfile(userURI);
		if(null != profile) {
			List<Treatment> treatments = getAllTreatments(userURI);
			List<Treatment> list = new ArrayList<Treatment>();
			if(null != treatments) {
				for(int i=0; i<treatments.size(); i++) {
					TreatmentPlanning planning = treatments.get(i).getTreatmentPlanning(); 
					if(null != planning) {
						if((timestampTo == -1 && timestampFrom == -1) || 
								((timestampFrom != -1 && 
								planning.getStartDate().getMillisecond() >= timestampFrom) &&
								(timestampTo != -1 && 
								planning.getEndDate().getMillisecond() <= timestampTo))) {
							list.add(treatments.get(i));
						}
					}
				}
				return list;
			}
		}
		return null;
	}
	
	/**
	 * @param profile
	 */
	private void updateHealthProfile(HealthProfile profile) {
		ServiceRequest req = new ServiceRequest(new ProfileManagementService(null), null);
		req.addChangeEffect(new String[] {ProfileManagementService.PROP_MANAGES_PROFILE}, profile);
		
		new DefaultServiceCaller(mc).call(req);
	}

	/**
	 * @param userURI
	 * @return
	 */
	private HealthProfile getHealthProfile(String userURI) {
		ServiceRequest req = new ServiceRequest(new ProfileManagementService(null), null);
		req.addValueFilter(new String[] {ProfileManagementService.PROP_ASSISTED_USER}, new AssistedPerson(userURI));
		req.addRequiredOutput(REQ_OUTPUT_PROFILE, new String[] {ProfileManagementService.PROP_MANAGES_PROFILE});
		
		ServiceResponse sr = new DefaultServiceCaller(mc).call(req);
		if (sr.getCallStatus() == CallStatus.succeeded) {
			return (HealthProfile) sr.getOutput(REQ_OUTPUT_PROFILE, false).get(0);
		}
		
		return null;
	}
}
