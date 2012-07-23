package org.universAAL.AALfficiency;


/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_5#Ontologies_in_universAAL */
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.aalfficiency.*;

import java.util.Hashtable;

public class ProvidedAALfficiencyService extends Aalfficiency{

    private static Hashtable serverLevelRestrictions = new Hashtable();
    
    // Naming
 	public static final String NAMESPACE = "http://www.tsbtecnologias.es/AALfficiency.owl#";
 	public static final String MY_URI = NAMESPACE+"ProvidedAALfficiencyService";
 	
 	//Score Managing
 	public static final String SERVICE_GET_SCORE = NAMESPACE+"getScore";
 	public static final String OUTPUT_SCORE = NAMESPACE+"Score";
 	//Challenges Managing
 	public static final String SERVICE_GET_CHALLENGES = NAMESPACE+"getChallenges";
 	public static final String OUTPUT_CHALLENGES = NAMESPACE+"Challenges";
 	//Advices Managing
 	public static final String SERVICE_GET_ADVICES = NAMESPACE+"getAdvices";
 	public static final String OUTPUT_ADVICES = NAMESPACE+"Advices";
 	public static final String SERVICE_GET_ADVICE_INFO = NAMESPACE+"getAdviceInfo";
 	public static final String INPUT_ADVICE_URI = NAMESPACE + "AdviceURI";
 	public static final String OUTPUT_ADVICE_TYPE = NAMESPACE+"AdviceType";
 	public static final String OUTPUT_ADVICE_TEXT = NAMESPACE+"AdviceText";
 	
 	
 	static final ServiceProfile[] profiles = new ServiceProfile[4];
 	
 	static{
 		OntologyManagement.getInstance().register(
				new SimpleOntology(MY_URI, Aalfficiency.MY_URI,
						new ResourceFactoryImpl() {
							@Override
							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new ProvidedAALfficiencyService(
										instanceURI);
							}
						}));
 		
 		ProvidedAALfficiencyService getScore = new ProvidedAALfficiencyService(SERVICE_GET_SCORE);
 		getScore.addOutput(OUTPUT_SCORE, AalfficiencyScore.MY_URI,1, 1, new String[]{Aalfficiency.PROP_HAS_SCORE});
 		profiles[0] = getScore.myProfile;
 		
 		ProvidedAALfficiencyService getChallenges = new ProvidedAALfficiencyService(SERVICE_GET_CHALLENGES);
 		getChallenges.addOutput(OUTPUT_CHALLENGES, AalfficiencyChallenges.MY_URI, 1,1, new String[]{Aalfficiency.PROP_HAS_CHALLENGES});
 		profiles[1] = getChallenges.myProfile;
 		
 		ProvidedAALfficiencyService getAdvices = new ProvidedAALfficiencyService(SERVICE_GET_ADVICES);
 		getAdvices.addOutput(OUTPUT_ADVICES, AalfficiencyAdvices.MY_URI, 1, 1, new String[]{Aalfficiency.PROP_HAS_ADVICES});
 		profiles[2] = getAdvices.myProfile;
 		
 		ProvidedAALfficiencyService getAdviceInfo = new ProvidedAALfficiencyService(
 	 			SERVICE_GET_ADVICE_INFO);
 	 		getAdviceInfo.addFilteringInput(INPUT_ADVICE_URI, Advice.MY_URI, 1, 1,
 	 				 new String[]{Aalfficiency.PROP_HAS_ADVICES, AalfficiencyAdvices.PROP_HAS_ADVICES,Advice.MY_URI});
 	 		getAdviceInfo.addOutput(OUTPUT_ADVICE_TYPE, Advice.MY_URI, 1, 1, new String[] 
 	 						{ Aalfficiency.PROP_HAS_ADVICES, AalfficiencyAdvices.PROP_HAS_ADVICES,Advice.MY_URI});
 	 		profiles[3] = getAdviceInfo.myProfile;
 		 	}
 	
 	public ProvidedAALfficiencyService(String instanceURI) {
		super(instanceURI);
		// TODO Auto-generated constructor stub
	}

}
