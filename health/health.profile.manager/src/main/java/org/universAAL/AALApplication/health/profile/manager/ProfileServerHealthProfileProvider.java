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
package org.universAAL.AALApplication.health.profile.manager;

import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.service.ProfilingService;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.HealthProfile;

/**
 * This class provides useful methods for accessing the health profile by using 
 * the profiling service.
 * The specific modules extend this class for managing their data through the
 * profiling service.
 * 
 * @author roni
 * @author amedrano
 *
 */
public class ProfileServerHealthProfileProvider implements HealthProfileProvider {

	private static final String ARG_OUT = HealthOntology.NAMESPACE + "argOut";

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
	public HealthProfile getHealthProfile(String userURI) {

		ServiceRequest req = new ServiceRequest(new ProfilingService(null), null);
		req.addValueFilter(new String[] { ProfilingService.PROP_CONTROLS }, new User(userURI));
		req.addRequiredOutput(ARG_OUT, new String[] { 
				ProfilingService.PROP_CONTROLS, 
				Profilable.PROP_HAS_PROFILE, 
				Profile.PROP_HAS_SUB_PROFILE });

		ServiceResponse sr = caller.call(req);
		if(sr.getCallStatus() == CallStatus.succeeded) {
			try {
		    	List<?> subProfiles = sr.getOutput(ARG_OUT, true);
		    	if(subProfiles == null || subProfiles.size() == 0) {
		    		moduleContext.logInfo(this.getClass().getName(),"there are no sub profiles, creating default health subprofile", null);
		    		return newHealthProfile(userURI);
		    	}
		    	moduleContext.logDebug(getClass().getName(), "searching in " + subProfiles.size() + " suprofiles", null);
		    	Iterator<?> iter = subProfiles.iterator();
		    	while(iter.hasNext()) {
		    		SubProfile subProfile = (SubProfile)iter.next();
		    		if(subProfile.getClassURI().equals(HealthProfile.MY_URI)) {
		    			return (HealthProfile)subProfile;
		    		}
		    	}
	    		moduleContext.logInfo(this.getClass().getName(),"there is no health profile, creating default one", null);
	    		return newHealthProfile(userURI);
			} catch(Exception e) {
				moduleContext.logError(this.getClass().getName(), "got exception", e);
				return null;
		    }
		} else {
			moduleContext.logWarn(this.getClass().getName(),"callstatus is not succeeded", null);
			return null;
		}
	}

	private HealthProfile newHealthProfile(String uri) {
		HealthProfile hp = new HealthProfile(uri+"HealthSubprofile");
		AssistedPerson ap = new AssistedPerson(uri);
		hp.assignHealthProfileToAP(ap);
		
		ServiceRequest req = new ServiceRequest(new ProfilingService(null),
				null);
		req.addValueFilter(new String[] { ProfilingService.PROP_CONTROLS }, ap);
		req.addAddEffect(new String[] { ProfilingService.PROP_CONTROLS,
				Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE },
				hp);
		ServiceResponse resp = caller.call(req);
		if (resp.getCallStatus().equals(CallStatus.succeeded)) {
			moduleContext.logDebug(this.getClass().getName(),"Added new Health Profile", null);
			return hp;
		}
		else {
			moduleContext.logWarn(this.getClass().getName(),"callstatus is not succeeded, subprofile not added", null);
			return null;
		}
	}
	
	/** {@inheritDoc} */
	public void updateHealthProfile(HealthProfile healthProfile) {

		ServiceRequest req = new ServiceRequest(new ProfilingService(null), null);
		req.addAddEffect(new String[] { 
				ProfilingService.PROP_CONTROLS, 
				Profilable.PROP_HAS_PROFILE, 
				Profile.PROP_HAS_SUB_PROFILE }, healthProfile);

		 ServiceResponse sr = caller.call(req);
		 if(sr.getCallStatus() != CallStatus.succeeded) {
			 moduleContext.logWarn(this.getClass().getName(),"callstatus is not succeeded", null);
		 }
	}
	
}
