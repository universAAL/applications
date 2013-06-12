/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
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
package org.universAAL.AALfficiency;


import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.aalfficiency.scores.*;

import java.util.Hashtable;

public class ProvidedAALfficiencyService extends AalfficiencyScores{

    private static Hashtable serverLevelRestrictions = new Hashtable();
    
    // Naming
 	public static final String NAMESPACE = "http://www.tsbtecnologias.es/AALfficiency.owl#";
 	public static final String MY_URI = NAMESPACE+"ProvidedAALfficiencyService";
 	
 	//Activity Data Managing
 	public static final String SERVICE_GET_ACTIVITY_DATA = NAMESPACE+"getActivityData";
 	public static final String OUTPUT_ACTIVITY_DATA = NAMESPACE+"ActivityData";
 	
 	//Electricity Data Managing
 	public static final String SERVICE_GET_ELECTRICITY_DATA = NAMESPACE+"getElectricityData";
 	public static final String OUTPUT_ELECTRICITY_DATA = NAMESPACE+"ElectricityData";
 	
 	//Challenges Managing
 	public static final String SERVICE_GET_ELECTRICITY_CHALLENGE = NAMESPACE+"getElectricityChallenge";
 	public static final String OUTPUT_ELECTRICITY_CHALLENGE = NAMESPACE+"ElectricityChallenge";
 	public static final String SERVICE_GET_ACTIVITY_CHALLENGE = NAMESPACE+"getActivityChallenge";
 	public static final String OUTPUT_ACTIVITY_CHALLENGE = NAMESPACE+"ActivityChallenge";
 	public static final String SERVICE_GET_CHALLENGE = NAMESPACE+"getChallenge";
 	public static final String OUTPUT_CHALLENGE = NAMESPACE+"Challenge";
 	public static final String INPUT_CHALLENGE = NAMESPACE + "ChallengeURI";
 	
 	//Advices Managing
 	public static final String SERVICE_GET_ADVICES = NAMESPACE+"getAdvices";
 	public static final String OUTPUT_ADVICES = NAMESPACE+"Advices";
 	public static final String SERVICE_GET_ADVICE_INFO = NAMESPACE+"getAdviceInfo";
 	public static final String INPUT_ADVICE_URI = NAMESPACE + "AdviceURI";
 	public static final String OUTPUT_ADVICE_INFO = NAMESPACE+"AdviceInfo";
 	
 	
 	static final ServiceProfile[] profiles = new ServiceProfile[7];
 	
 	static{
 		OntologyManagement.getInstance().register(Activator.getMcontext(),
				new SimpleOntology(MY_URI, AalfficiencyScores.MY_URI,
						new ResourceFactoryImpl() {
							@Override
							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new ProvidedAALfficiencyService(
										instanceURI);
							}
						}));
 		
 		ProvidedAALfficiencyService getActivityData = new ProvidedAALfficiencyService(SERVICE_GET_ACTIVITY_DATA);
 		getActivityData.addOutput(OUTPUT_ACTIVITY_DATA, ActivityScore.MY_URI,1, 1, new String[]{AalfficiencyScores.PROP_ACTIVITY_SCORE});
 		profiles[0] = getActivityData.myProfile;

 		ProvidedAALfficiencyService getElectricityData = new ProvidedAALfficiencyService(SERVICE_GET_ELECTRICITY_DATA);
 		getElectricityData.addOutput(OUTPUT_ELECTRICITY_DATA, ActivityScore.MY_URI,1, 1, new String[]{AalfficiencyScores.PROP_ELECTRICITY_SCORE});
 		profiles[1] = getElectricityData.myProfile;
		
 		ProvidedAALfficiencyService getAdvices = new ProvidedAALfficiencyService(SERVICE_GET_ADVICES);
 		getAdvices.addOutput(OUTPUT_ADVICES, AalfficiencyAdvices.MY_URI, 1, 1, new String[]{AalfficiencyScores.PROP_AALFFICIENCY_ADVICES});
 		profiles[2] = getAdvices.myProfile;
 		
 		ProvidedAALfficiencyService getAdviceInfo = new ProvidedAALfficiencyService(SERVICE_GET_ADVICE_INFO);
 	 	getAdviceInfo.addFilteringInput(INPUT_ADVICE_URI, Advice.MY_URI, 1, 1,new String[]{AalfficiencyAdvices.PROP_ADVICE});
 	 	getAdviceInfo.addOutput(OUTPUT_ADVICE_INFO, Advice.MY_URI, 1, 1, new String[]{AalfficiencyAdvices.PROP_ADVICE});
 	 	profiles[3] = getAdviceInfo.myProfile;

 	 	ProvidedAALfficiencyService getElectricityChallenge = new ProvidedAALfficiencyService(SERVICE_GET_ELECTRICITY_CHALLENGE);
 	 	getElectricityChallenge.addOutput(OUTPUT_ELECTRICITY_CHALLENGE, Challenge.MY_URI, 1, 1, new String[]{ElectricityScore.PROP_CHALLENGE});
 		profiles[4] = getElectricityChallenge.myProfile;
 	 	 		
 		ProvidedAALfficiencyService getActivityChallenge = new ProvidedAALfficiencyService(SERVICE_GET_ACTIVITY_CHALLENGE);
 		getActivityChallenge.addOutput(OUTPUT_ACTIVITY_CHALLENGE, Challenge.MY_URI, 1, 1, new String[]{ActivityScore.PROP_CHALLENGE});
 		profiles[5] = getActivityChallenge.myProfile;

 	 	ProvidedAALfficiencyService getChallenge = new ProvidedAALfficiencyService(SERVICE_GET_CHALLENGE);
 	 	getChallenge.addFilteringInput(INPUT_CHALLENGE, Challenge.MY_URI, 1, 1,new String[]{Challenge.MY_URI});
 	 	getChallenge.addOutput(OUTPUT_CHALLENGE, Challenge.MY_URI, 1, 1, new String[]{Challenge.MY_URI});
 	 	profiles[6] = getChallenge.myProfile;

 	}
 	
 	public ProvidedAALfficiencyService(String instanceURI) {
		super(instanceURI);
	}
 	public String getClassURI() {
 		return MY_URI;
 	    }
}
