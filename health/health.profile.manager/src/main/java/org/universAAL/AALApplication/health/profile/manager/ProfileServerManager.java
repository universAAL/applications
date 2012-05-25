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
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.SubProfile;
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
 */
public class ProfileServerManager {

    protected static final String OUTPUT_SUB_PROFILE = HealthOntology.NAMESPACE + "subProfile";
	
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
	 public ProfileServerManager(ModuleContext context) {
		 
		 moduleContext = context;
		 
		 // the DefaultServiceCaller will be used to make ServiceRequest
		 caller = new DefaultServiceCaller(context);
	 }
	 	
	/**
	 * Returns the {org.universAAL.ontology.profile.health.HealthProfile} of the
	 * given user.
	 * 
	 * @param userURI The URI of the user
	 * 
	 * @return The health profile of the user
	 */
	public HealthProfile getHealthProfile(String userURI) {

		ServiceRequest req = new ServiceRequest(new ProfilingService(null), null);
		req.addValueFilter(new String[] { ProfilingService.PROP_CONTROLS }, userURI);
		req.addRequiredOutput(OUTPUT_SUB_PROFILE, new String[] { 
				ProfilingService.PROP_CONTROLS, 
				Profilable.PROP_HAS_PROFILE, 
				Profile.PROP_HAS_SUB_PROFILE });

		ServiceResponse sr = caller.call(req);
		if(sr.getCallStatus() == CallStatus.succeeded) {
			try {
		    	List subProfiles = sr.getOutput(OUTPUT_SUB_PROFILE, true);
		    	if(subProfiles == null || subProfiles.size() == 0) {
		    		LogUtils.logInfo(moduleContext, ProfileServerManager.class,
		    				"getHealthProfile",
		    				new Object[] { "there are no sub profiles" }, null);
		    		return newHealthProfile(userURI);
		    	}
		    	Iterator iter = subProfiles.iterator();
		    	while(iter.hasNext()) {
		    		SubProfile subProfile = (SubProfile)iter.next();
		    		if(subProfile.getClassURI().equals(HealthProfile.MY_URI)) {
		    			return (HealthProfile)subProfile;
		    		}
		    	}
	    		LogUtils.logInfo(moduleContext, ProfileServerManager.class,
	    				"getHealthProfile",
	    				new Object[] { "there is no health profile" }, null);
		    	return null;	
			} catch(Exception e) {
				LogUtils.logError(moduleContext, ProfileServerManager.class,
						"getHealthProfile", new Object[] { "got exception",
						e.getMessage() }, e);
				return null;
		    }
		} else {
			LogUtils.logWarn(moduleContext, ProfileServerManager.class,
					"getHealthProfile",
					new Object[] { "callstatus is not succeeded" }, null);
			return null;
		}
	}
	
	private HealthProfile newHealthProfile(String userURI) {
		HealthProfile hp = new HealthProfile();
		ServiceRequest req = new ServiceRequest(new ProfilingService(null),
				null);
			req.addValueFilter(new String[] { ProfilingService.PROP_CONTROLS }, userURI);
			req.addAddEffect(new String[] { ProfilingService.PROP_CONTROLS,
				Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE },
				hp);
			ServiceResponse resp = caller.call(req);
			if (resp.getCallStatus().equals(CallStatus.succeeded)) {
				return hp;
			}
			else {
				return null;
			}	
	}

	/**
	 * Updates the {org.universAAL.ontology.profile.health.HealthProfile}.
	 *  
	 * @param healthProfile The updated health profile
	 */
	public void updateHealthProfile(HealthProfile healthProfile) {
		
		ServiceRequest req = new ServiceRequest(new ProfilingService(null), null);
		req.addAddEffect(new String[] { 
				ProfilingService.PROP_CONTROLS, 
				Profilable.PROP_HAS_PROFILE, 
				Profile.PROP_HAS_SUB_PROFILE }, healthProfile);

		 ServiceResponse sr = caller.call(req);
		 if(sr.getCallStatus() != CallStatus.succeeded) {
			 LogUtils.logWarn(moduleContext, ProfileServerManager.class,
					 "updateHealthProfile",
					 new Object[] { "callstatus is not succeeded" }, null);
		 }
	}
}
