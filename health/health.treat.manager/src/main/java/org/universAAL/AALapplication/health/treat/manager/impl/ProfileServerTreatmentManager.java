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
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.services.ProfileManagementService;
import org.universAAL.ontology.profile.User;

/**
 * This class actually implements the
 * {@link org.universAAL.AALapplication.health.treat.manager.TreatmentManager}
 * by using the profiling service.
 * 
 * @author amedrano
 * @author roni
 */
public class ProfileServerTreatmentManager // extends MapHealthProfileProvider
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
     * @param healthProfile
     *            The URI of the user
     * @param treatment
     *            The treatment to be added to the health profile
     */
    public void newTreatment(User user, Treatment treatment) {
	HealthProfile profile = getHealthProfile(user);
	newTreatment(profile, treatment);
    }

    /** {@inheritDoc} */
    public void newTreatment(HealthProfile profile, Treatment treatment) {
	profile.addTreatment(treatment);
	// TODO expand planned sessions...

	updateHealthProfile(profile);
    }

    /** {@inheritDoc} */
    public void deleteTreatment(User user, Treatment treatment) {
	deleteTreatment(getHealthProfile(user), treatment);
    }

    /** {@inheritDoc} */
    public void deleteTreatment(HealthProfile healthProfile, Treatment treatment) {
	if (null != healthProfile) {
	    if (healthProfile.deleteTreatment(treatment.getURI())) {
		updateHealthProfile(healthProfile);
	    } else {
		LogUtils.logInfo(mc, ProfileServerTreatmentManager.class,
			"deleteTreatment", new Object[] { "treatment "
				+ treatment.getURI() + " does not exist" },
			null);
	    }
	}
    }

    /**
     * Edits a treatment in the user health profile.
     * 
     * @param newTreatment
     *            The new treatment
     */
    public void updateTreatment(Treatment newTreatment) {
	// TODO SPARQL Query to update the treatment.

	// // check that the new treatment has the same URI
	// if(!treatmentURI.equals(newTreatment.getURI())) {
	// LogUtils.logError(mc, ProfileServerTreatmentManager.class,
	// "editTreatment",
	// new Object[] { "new treatment URI does not equal to " +
	// treatmentURI}, null);
	// return;
	// }
	//
	// HealthProfile profile = getHealthProfile(userURI);
	// if(null != profile) {
	// if(profile.editTreatment(newTreatment)) {
	// //TODO re expand planned sessions
	// updateHealthProfile(profile);
	// } else {
	// LogUtils.logInfo(mc, ProfileServerTreatmentManager.class,
	// "editTreatment",
	// new Object[] { "treatment " + treatmentURI + " does not exist"},
	// null);
	// }
	// }
    }

    /**
     * Returns a {java.util.List} of all the treatments that are associated with
     * the given user health profile.
     * 
     * @param user
     *            The URI of the user
     * 
     * @return All the treatments that are associated with the user
     */
    public List<Treatment> getAllTreatments(User user) {
	HealthProfile profile = getHealthProfile(user);
	return getAllTreatments(profile);
    }

    /** {@inheritDoc} */
    public List<Treatment> getAllTreatments(HealthProfile profile) {
	if (null != profile) {
	    Object propList = profile
		    .getProperty(HealthProfile.PROP_HAS_TREATMENT);
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
     * @param user
     *            The URI of the user
     * @param timestampFrom
     *            The lower bound of the period, the value -1 means that there
     *            is not a lower bound
     * @param timestampTo
     *            The upper bound of the period, the value -1 means that there
     *            is not an upper bound
     * 
     * @return All the treatments that are associated with the user in a
     *         specific period of time
     */
    public List<Treatment> getTreatmentsBetweenTimestamps(User user,
	    long timestampFrom, long timestampTo) {
	HealthProfile profile = getHealthProfile(user);
	return getTreatmentsBetweenTimestamps(profile, timestampFrom,
		timestampTo);
    }

    /** {@inheritDoc} */
    public List<Treatment> getTreatmentsBetweenTimestamps(
	    HealthProfile profile, long timestampFrom, long timestampTo) {
	if (null != profile) {
	    List<Treatment> treatments = getAllTreatments(profile);
	    List<Treatment> list = new ArrayList<Treatment>();
	    if (null != treatments) {
		for (int i = 0; i < treatments.size(); i++) {
		    TreatmentPlanning planning = treatments.get(i)
			    .getTreatmentPlanning();
		    if (null != planning) {
			if ((timestampTo == -1 && timestampFrom == -1)
				|| ((timestampFrom != -1 && planning
					.getStartDate().getMillisecond() >= timestampFrom) && (timestampTo != -1 && planning
					.getEndDate().getMillisecond() <= timestampTo))) {
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
	ServiceRequest req = new ServiceRequest(new ProfileManagementService(
		null), null);
	req.addChangeEffect(
		new String[] { ProfileManagementService.PROP_ASSISTED_USER_PROFILE },
		profile);

	new DefaultServiceCaller(mc).call(req);
    }

    /**
     * @param user
     * @return
     */
    private HealthProfile getHealthProfile(User user) {
	ServiceRequest req = new ServiceRequest(new ProfileManagementService(
		null), null);
	req.addValueFilter(
		new String[] { ProfileManagementService.PROP_ASSISTED_USER },
		user);
	req.addRequiredOutput(
		REQ_OUTPUT_PROFILE,
		new String[] { ProfileManagementService.PROP_ASSISTED_USER_PROFILE });

	ServiceResponse sr = new DefaultServiceCaller(mc).call(req);
	if (sr.getCallStatus() == CallStatus.succeeded) {
	    return (HealthProfile) sr.getOutput(REQ_OUTPUT_PROFILE, false).get(
		    0);
	}

	return null;
    }
}
