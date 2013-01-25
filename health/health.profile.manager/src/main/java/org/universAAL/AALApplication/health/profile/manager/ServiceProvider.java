/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 * Universidad Politécnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.AALApplication.health.profile.manager;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.services.ProfileManagementService;

/**
 * @author amedrano
 *
 */
public class ServiceProvider extends ServiceCallee {

	static private ServiceProfile[] profiles
		= new ServiceProfile[] { new ServiceProvider.GetProfileService().getProfile(), 
		new ServiceProvider.UpdateProfileService().getProfile()};
	
    // prepare a standard error message for later use
    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    static {
    	invalidInput.addOutput(new ProcessOutput(
    			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }

	private HealthProfileProvider psm;
	
	/**
	 * @param context
	 * @param realizedServices
	 */
	protected ServiceProvider(ModuleContext context,
			ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		psm = new ProfileServerHealthProfileProvider(context);
	}
	
	public ServiceProvider(ModuleContext context) {
		this(context,profiles);
	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken()
	 */
	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL.middleware.service.ServiceCall)
	 */
	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		if(call == null)
		    return invalidInput;

		String operation = call.getProcessURI();
		if(operation == null)
		    return invalidInput;

		Object userInput = call.getInputValue(GetProfileService.INPUT_USER);
		if(userInput == null)
		    return invalidInput;
		
		if(operation.startsWith(GetProfileService.MY_URI))
			return getProfile((String)userInput);
		
		HealthProfile profile = (HealthProfile) call.getInputValue(UpdateProfileService.INPUT_PROFILE);
		if(operation.startsWith(UpdateProfileService.MY_URI) && profile!= null)
			return updateProfile(profile);
		
		return invalidInput;
	}

	private ServiceResponse updateProfile(HealthProfile profile) {
		psm.updateHealthProfile(profile);
		return new ServiceResponse(CallStatus.succeeded);
	}

	private ServiceResponse getProfile(String userInput) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		sr.addOutput(new ProcessOutput(GetProfileService.OUTPUT_PROFILE, psm.getHealthProfile(userInput)));
		return sr;
	}
	
	/**
	 * 
	 * @author mdelafuente
	 * 
	 * @navassoc - "manages" - HealthProfile
	 */
	public static class GetProfileService extends ProfileManagementService{

		
		
		//NAMESPACE & PROPERTIES
		public static final String MY_URI = HealthOntology.NAMESPACE
		+ "GetProfileService";

		//INPUT PARAMETERS URI
		public static final String INPUT_PROFILE      = HealthOntology.NAMESPACE + "healthProfile";

	    //OUTPUT PARAMETERS URI    
		public static final String OUTPUT_PROFILE = HealthOntology.NAMESPACE + "matchingHealthProfile";
		
		static {
			OntologyManagement.getInstance().register(
					new SimpleOntology(MY_URI, ProfileManagementService.MY_URI, 
							new ResourceFactory() {
						
						public Resource createInstance(String classURI, String instanceURI,
								int factoryIndex) {
							return new GetProfileService(classURI);
						}
						
						public Resource castAs(Resource r, String classURI) {
							return null;
						}
					})
					);
		}
		private void buildProfile() {
			addFilteringInput(INPUT_USER, AssistedPerson.MY_URI, 1, 1, new String[] {PROP_ASSISTED_USER});
			addOutput(OUTPUT_PROFILE, HealthProfile.MY_URI, 0, 1, new String[] {PROP_ASSISTED_USER_PROFILE});
		}

	    //CONSTRUCTORS
		public GetProfileService() {
			super();
			buildProfile();
		}

		public GetProfileService(String uri) {
			super(uri);
			buildProfile();
		}

		public String getClassURI() {
			return MY_URI;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
		 * (java.lang.String)
		 */
		public int getPropSerializationType(String propURI) {
			return  PROP_ASSISTED_USER_PROFILE.equals(propURI) 
			 ? PROP_SERIALIZATION_FULL : super
					.getPropSerializationType(propURI);
		}

		public boolean isWellFormed() {
			return true;
		}
	}
	
	/**
	 * 
	 * @author mdelafuente
	 * 
	 * @navassoc - "manages" - HealthProfile
	 */
	public static class UpdateProfileService extends ProfileManagementService{

		//NAMESPACE & PROPERTIES
		public static final String MY_URI = HealthOntology.NAMESPACE
		+ "UpdateProfileService";
		
		//INPUT PARAMETERS URI
		public static final String INPUT_PROFILE      = HealthOntology.NAMESPACE + "healthProfile";

		static {
			OntologyManagement.getInstance().register(
					new SimpleOntology(MY_URI, ProfileManagementService.MY_URI, 
							new ResourceFactory() {
						
						public Resource createInstance(String classURI, String instanceURI,
								int factoryIndex) {
							return new UpdateProfileService(classURI);
						}
						
						public Resource castAs(Resource r, String classURI) {
							return null;
						}
					})
					);
		}

		private void buildProfile() {
			//addFilteringInput(INPUT_USER, AssistedPerson.MY_URI, 1, 1, new String[] {PROP_ASSISTED_USER});
			addInputWithChangeEffect(INPUT_PROFILE, HealthProfile.MY_URI, 0, 1, new String[] {PROP_ASSISTED_USER_PROFILE});
			//addOutput(OUTPUT_PROFILE, HealthProfile.MY_URI, 0, 1, new String[] {PROP_ASSISTED_PERSON_PROFILE});
		}

	    //CONSTRUCTORS
		public UpdateProfileService() {
			super();
			buildProfile();
		}

		public UpdateProfileService(String uri) {
			super(uri);
			buildProfile();
		}

		public String getClassURI() {
			return MY_URI;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
		 * (java.lang.String)
		 */
		public int getPropSerializationType(String propURI) {
			return  PROP_ASSISTED_USER_PROFILE.equals(propURI) 
			 ? PROP_SERIALIZATION_FULL : super
					.getPropSerializationType(propURI);
		}

		public boolean isWellFormed() {
			return true;
		}
	}


}
