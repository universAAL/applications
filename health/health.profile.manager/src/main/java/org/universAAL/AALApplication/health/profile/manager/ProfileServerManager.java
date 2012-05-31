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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.AssistedPersonProfile;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
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
public class ProfileServerManager {

    protected static final String OUTPUT_SUB_PROFILE = HealthOntology.NAMESPACE + "subProfile";

	private static final String ARG_OUT = HealthOntology.NAMESPACE + "argOut";;
	
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
	/*
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
		    	List<?> subProfiles = sr.getOutput(OUTPUT_SUB_PROFILE, true);
		    	if(subProfiles == null || subProfiles.size() == 0) {
		    		moduleContext.logInfo("there are no sub profiles, creating default health subprofile", null);
		    		return newHealthProfile(userURI);
		    	}
		    	Iterator<?> iter = subProfiles.iterator();
		    	while(iter.hasNext()) {
		    		SubProfile subProfile = (SubProfile)iter.next();
		    		if(subProfile.getClassURI().equals(HealthProfile.MY_URI)) {
		    			return (HealthProfile)subProfile;
		    		}
		    	}
	    		moduleContext.logInfo("there is no health profile, creating default one", null);
	    		return newHealthProfile(userURI);
			} catch(Exception e) {
				moduleContext.logError( "got exception", e);
				return null;
		    }
		} else {
			moduleContext.logWarn("callstatus is not succeeded", null);
			return null;
		}
	}
	*/
	 public HealthProfile getHealthProfile(String userURI) {
		 AssistedPersonProfile app = getProfile(new AssistedPerson(userURI));
			if( app != null) {
				if (app.hasProperty(AssistedPersonProfile.PROP_HAS_SUB_PROFILE)) {
					return (HealthProfile) getReturnValue(
							(List) app.getProperty(AssistedPersonProfile.PROP_HAS_SUB_PROFILE),
							HealthProfile.MY_URI);
				}else {
					return new HealthProfile();
				}
			}
			else {
				moduleContext.logError("No User Profile Found", null);
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
				moduleContext.logDebug("Added new Health Profile", null);
				return hp;
			}
			else {
				moduleContext.logWarn("callstatus is not succeeded, subprofile not added", null);
				return null;
			}	
	}

	/**
	 * Updates the {org.universAAL.ontology.profile.health.HealthProfile}.
	 *  
	 * @param healthProfile The updated health profile
	 */
	/*
	public void updateHealthProfile(HealthProfile healthProfile) {
		
		ServiceRequest req = new ServiceRequest(new ProfilingService(null), null);
		req.addAddEffect(new String[] { 
				ProfilingService.PROP_CONTROLS, 
				Profilable.PROP_HAS_PROFILE, 
				Profile.PROP_HAS_SUB_PROFILE }, healthProfile);

		 ServiceResponse sr = caller.call(req);
		 if(sr.getCallStatus() != CallStatus.succeeded) {
			 moduleContext.logWarn("callstatus is not succeeded", null);
		 }
	}
	*/
	public void updateHealthProfile(HealthProfile healthProfile) {
		AssistedPersonProfile app = getProfile(healthProfile.getAssignedAssistedPerson());
		Object subprf = app.getProperty(AssistedPersonProfile.PROP_HAS_SUB_PROFILE);
		Resource hp = null;
		
		if (subprf == null
				|| subprf instanceof HealthProfile) {
			app.setSubProfile(healthProfile);
		}
		else {
			List<Resource> subprofiles;
			try {
				subprofiles = (List<Resource>) subprf;
				for (Iterator iterator = subprofiles.iterator(); iterator.hasNext();) {
					Resource subP = (Resource) iterator.next();
					if (subP.getURI().equals(HealthProfile.MY_URI)) {
						hp = subP;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				subprofiles = new ArrayList<Resource>();
				subprofiles.add((Resource) subprf);
			}
			subprofiles.remove(hp);
			subprofiles.add(healthProfile);
			app.setProperty(AssistedPersonProfile.PROP_HAS_SUB_PROFILE, subprofiles);
		}		
		ServiceRequest req = new ServiceRequest(new ProfilingService(null),	null);
		req.addChangeEffect(new String[] { ProfilingService.PROP_CONTROLS,
				Profilable.PROP_HAS_PROFILE }, app);
		ServiceResponse resp = caller.call(req);
	}
	/**
	 * Copied from org.universAAL.context.prof.serv.ArtifactIntegrationTest
	 * @param profile
	 * @return
	 */
	private AssistedPersonProfile getProfile(AssistedPerson assistedPerson) {
		ServiceRequest req = new ServiceRequest(new ProfilingService(null),	null);
		req.addValueFilter(new String[] { ProfilingService.PROP_CONTROLS }, assistedPerson);
		req.addRequiredOutput(ARG_OUT, new String[] {
				ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE });
		ServiceResponse resp = caller.call(req);
		if (resp.getCallStatus().equals(CallStatus.succeeded)) {
			return (AssistedPersonProfile) getReturnValue(resp.getOutputs(), ARG_OUT);
		} else {
			return null;
		}
	}
	/**
	 * Copied from org.universAAL.context.prof.serv.ArtifactIntegrationTest
	 * @param outputs
	 * @param expectedOutput
	 * @return
	 */
	private Object getReturnValue(List outputs, String expectedOutput) {
		Object returnValue = null;
		if (!(outputs == null)) {
			for (Iterator i = outputs.iterator(); i.hasNext();) {
				ProcessOutput output = (ProcessOutput) i.next();
				if (output.getURI().equals(expectedOutput))
					if (returnValue == null)
						returnValue = output.getParameterValue();
			}
		}
		return returnValue;
	}
}
