package org.universAAL.AALfficiency;


/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_5#Ontologies_in_universAAL */
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
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
 	public static final String SERVICE_GET_CHALLENGE_INFO = NAMESPACE+"getChallengeInfo";
 	public static final String INPUT_CHALLENGE_URI = NAMESPACE+"ChallengeURI";
 	public static final String OUTPUT_CHALLENGE = NAMESPACE+"ChallengeInfo";
 	//Advices Managing
 	public static final String SERVICE_GET_ADVICES = NAMESPACE+"getAdvices";
 	public static final String OUTPUT_ADVICES = NAMESPACE+"Advices";
 	public static final String SERVICE_GET_ADVICE_INFO = NAMESPACE+"getAdviceInfo";
 	public static final String INPUT_ADVICE_URI = NAMESPACE + "AdviceURI";
 	public static final String OUTPUT_ADVICE_INFO = NAMESPACE+"AdviceInfo";
 	
 	
 	static final ServiceProfile[] profiles = new ServiceProfile[5];
 	
 	static{
 		OntologyManagement.getInstance().register(
				new SimpleOntology(MY_URI, AalfficiencyScores.MY_URI,
						new ResourceFactoryImpl() {
							@Override
							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new ProvidedAALfficiencyService(
										instanceURI);
							}
						}));
 		
 		/*ProvidedAALfficiencyService getActivityData = new ProvidedAALfficiencyService(SERVICE_GET_ACTIVITY_DATA);
 		getActivityData.addOutput(OUTPUT_ACTIVITY_DATA, ActivityScore.MY_URI,1, 1, new String[]{AalfficiencyScores.PROP_HAS_ACTIVITY_SCORE});
 		profiles[0] = getActivityData.myProfile;
 		
 		ProvidedAALfficiencyService getElectricityData = new ProvidedAALfficiencyService(SERVICE_GET_ELECTRICITY_DATA);
 		getElectricityData.addOutput(OUTPUT_ELECTRICITY_DATA, ElectricityScore.MY_URI, 1,1, new String[]{AalfficiencyScores.PROP_HAS_ELECTRICITY_SCORE});
 		profiles[1] = getElectricityData.myProfile;*/
 		
 		ProvidedAALfficiencyService getAdvices = new ProvidedAALfficiencyService(SERVICE_GET_ADVICES);
 		getAdvices.addOutput(OUTPUT_ADVICES, AalfficiencyAdvices.MY_URI, 1, 1, new String[]{AalfficiencyScores.PROP_HAS_ADVICES});
 		profiles[2] = getAdvices.myProfile;
 		
 		ProvidedAALfficiencyService getAdviceInfo = new ProvidedAALfficiencyService(
 	 			SERVICE_GET_ADVICE_INFO);
 	 	getAdviceInfo.addFilteringInput(INPUT_ADVICE_URI, Advice.MY_URI, 1, 1,
 	 				 new String[]{Advice.MY_URI});
 	 	getAdviceInfo.addOutput(OUTPUT_ADVICE_INFO, Advice.MY_URI, 1, 1, new String[] 
 	 						{Advice.MY_URI});
 	 	profiles[3] = getAdviceInfo.myProfile;
 	 		
 	 	ProvidedAALfficiencyService getChallengeInfo = new ProvidedAALfficiencyService(
 	 			SERVICE_GET_CHALLENGE_INFO);
 	 	getChallengeInfo.addFilteringInput(INPUT_CHALLENGE_URI, Challenge.MY_URI, 1, 1,
 	 					 new String[]{Challenge.MY_URI});
 	 	getChallengeInfo.addOutput(OUTPUT_CHALLENGE, Challenge.MY_URI, 1, 1, new String[] 
 	 	 						{Challenge.MY_URI});
 	 	profiles[4] = getChallengeInfo.myProfile;
 		 	}
 	
 	public ProvidedAALfficiencyService(String instanceURI) {
		super(instanceURI);
		// TODO Auto-generated constructor stub
	}
 	public String getClassURI() {
 		return MY_URI;
 	    }
}
