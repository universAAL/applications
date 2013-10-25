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
package org.universAAL.AALApplication.health.profile.manager.impl;

import java.util.Iterator;
import java.util.List;

import org.universAAL.AALApplication.health.profile.manager.IHealthProfileProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.service.ProfilingService;

/**
 * This class provides useful methods for accessing the health profile by using
 * the profiling service. The specific modules extend this class for managing
 * their data through the profiling service.
 * 
 * @author roni
 * @author amedrano
 * 
 */
public class ProfileServerHealthProfileProvider implements
	IHealthProfileProvider {

    private static final String ARG_OUT = HealthProfileOntology.NAMESPACE
	    + "argOut";

    /**
     * Needed for making service requests
     */
    protected ServiceCaller caller = null;

    protected ModuleContext moduleContext = null;

    /**
     * Constructor.
     * 
     * @param context
     */
    public ProfileServerHealthProfileProvider(ModuleContext context) {

	moduleContext = context;

	// the DefaultServiceCaller will be used to make ServiceRequest
	caller = new DefaultServiceCaller(context);
    }

    /** {@inheritDoc} */
    public HealthProfile getHealthProfile(Resource user) {

	ServiceRequest req = new ServiceRequest(new ProfilingService(null),
		null);
	req.addValueFilter(new String[] { ProfilingService.PROP_CONTROLS },
		user);
	req.addRequiredOutput(ARG_OUT, new String[] {
		ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE,
		Profile.PROP_HAS_SUB_PROFILE });

	ServiceResponse sr = caller.call(req);
	if (sr.getCallStatus().equals(CallStatus.succeeded)) {
	    try {
		List<?> subProfiles = sr.getOutput(ARG_OUT, true);
		if (subProfiles == null || subProfiles.size() == 0) {
		    LogUtils.logInfo(moduleContext, getClass(),
			    "getHealthProfile",
			    "there are no sub profiles, creating default health subprofile");
		    return newHealthProfile(user);
		}
		LogUtils.logDebug(moduleContext, getClass(),
			"getHealthProfile",
			"searching in " + subProfiles.size() + " suprofiles");
		Iterator<?> iter = subProfiles.iterator();
		while (iter.hasNext()) {
		    SubProfile subProfile = (SubProfile) iter.next();
		    if (subProfile.getClassURI().equals(HealthProfile.MY_URI)) {
			return (HealthProfile) subProfile;
		    }
		}
		LogUtils.logInfo(moduleContext, getClass(), "getHealthProfile",
			"there is no health profile, creating default one");
		return newHealthProfile(user);
	    } catch (Exception e) {
		LogUtils.logError(moduleContext, getClass(),
			"getHealthProfile", new String[] { "Got Exception" }, e);
		return newHealthProfile(user);
	    }
	} else {
	    LogUtils.logWarn(moduleContext, getClass(), "getHealthProfile",
		    "callstatus is not succeeded; creating new profile");
	    return newHealthProfile(user);
	}
    }

    private HealthProfile newHealthProfile(Resource ap) {
	HealthProfile hp = new HealthProfile(ap.getURI() + "HealthSubprofile");
	if (ap instanceof AssistedPerson) {
	    hp.setAssignedAssistedPerson((AssistedPerson) ap);
	    // Bug #378
	    // hp.setAssignedAssistedPerson(new AssistedPerson(ap.getURI()));
	}

	ServiceRequest req = new ServiceRequest(new ProfilingService(null),
		null);
	req.addValueFilter(new String[] { ProfilingService.PROP_CONTROLS }, ap);
	req.addAddEffect(new String[] { ProfilingService.PROP_CONTROLS,
		Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE }, hp);
	ServiceResponse resp = caller.call(req);
	if (resp.getCallStatus().equals(CallStatus.succeeded)) {
	    LogUtils.logDebug(moduleContext, getClass(), "newHealthProfile",
		    "added new HealthProfile");
	    return hp;
	} else {
	    LogUtils.logWarn(moduleContext, getClass(), "newHealthProfile",
		    "callstatus is not succeeded, subprofile not added");
	    return null;
	}
    }

    /** {@inheritDoc} */
    public void updateHealthProfile(HealthProfile healthProfile) {
	ServiceRequest req = new ServiceRequest(new ProfilingService(null),
		null);
	req.addAddEffect(new String[] { ProfilingService.PROP_CONTROLS,
		Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE },
		healthProfile);

	ServiceResponse sr = caller.call(req);
	if (sr.getCallStatus() != CallStatus.succeeded) {
	    LogUtils.logWarn(moduleContext, getClass(), "updateHealthProfile",
		    "callstatus is not suceeded, profile not updated.");
	}
    }

}
