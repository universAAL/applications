/**
 * 
 */
package org.universAAL.AALapplication.health.performedSession.manager;

import java.util.Hashtable;

import org.universAAL.AALapplication.health.performedSession.manager.osgi.Activator;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.health.owl.PerformedSession;
import org.universAAL.ontology.health.owl.services.PerformedSessionManagementService;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public class ProvidedPerformedSessionManagementService extends
		PerformedSessionManagementService {

	/**
	 * @param uri
	 */
	private ProvidedPerformedSessionManagementService(String uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getClassURI() {
		return MY_URI;
	}
	
public static final String NAMESPACE = "http://ontology.lst.tfo.upm.es/PerformedSessionManagementService.owl#";
	
	public static final String MY_URL = NAMESPACE + "PerformedSessionService";
		 
	public static final String SERVICE_LIST = NAMESPACE +"listPerSes";
	public static final String SERVICE_LIST_BT= NAMESPACE +"listBetweenTimestamps";
	public static final String SERVICE_NEW = NAMESPACE +"newPerSes";
	
	//PARAMETERS URI
	public static final String INPUT_USER      = HealthProfileOntology.NAMESPACE + "user";
	public static final String INPUT_TREATMENT = HealthProfileOntology.NAMESPACE + "associatedTreatment";
	public static final String INPUT_TIMESTAMP_FROM = HealthProfileOntology.NAMESPACE + "psTimestampFrom";
	public static final String INPUT_TIMESTAMP_TO   = HealthProfileOntology.NAMESPACE + "psTimestampTo";
	public static final String INPUT_PERFORMED_SESSION = HealthProfileOntology.NAMESPACE + "performedSession";

	public static final String OUTPUT_PERFORMED_SESSIONS = HealthProfileOntology.NAMESPACE + "matchingPerformedSessions";
	
	
	static final ServiceProfile[] profiles = new ServiceProfile[3];
	private static Hashtable serverRestrictions = new Hashtable();
	    
	static {
		OntologyManagement.getInstance().register(Activator.context,
				new SimpleOntology(MY_URI, PerformedSessionManagementService.MY_URI,
						new ResourceFactory() {
					public Resource createInstance(String classURI,
							String instanceURI, int factoryIndex) {
						return new ProvidedPerformedSessionManagementService(instanceURI);
					}
				}));

	    
		String[] ppManages = new String[] {PerformedSessionManagementService.PROP_MANAGES_SESSION};
//		String[] ppTreat = new String[] {PerformedSessionManagementService.PROP_ASSOCIATED_TREATMENT};
		
//		addRestriction((MergedRestriction) PerformedSessionManagementService
//				.getClassRestrictionsOnProperty(PerformedSessionManagementService.MY_URI,
//						PerformedSessionManagementService.PROP_MANAGES_SESSION).copy(), ppManages ,
//				serverRestrictions);
		
		//List 
		ProvidedPerformedSessionManagementService list = new ProvidedPerformedSessionManagementService(SERVICE_LIST);
		list.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
    	list.addOutput(OUTPUT_PERFORMED_SESSIONS, PerformedSession.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES_SESSION });
    	profiles[0] = list.getProfile();
    	
    	//List Between timestamps
    	ProvidedPerformedSessionManagementService listBetween = new ProvidedPerformedSessionManagementService(SERVICE_LIST);
    	listBetween.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
    	listBetween.addOutput(OUTPUT_PERFORMED_SESSIONS, PerformedSession.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES_SESSION });
    	listBetween.addFilteringInput(INPUT_TIMESTAMP_FROM, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_FROM });
    	listBetween.addFilteringInput(INPUT_TIMESTAMP_TO, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_TO });
    	profiles[1] = listBetween.getProfile();
    	
    	//new PerformedSession
    	ProvidedPerformedSessionManagementService newSession = new ProvidedPerformedSessionManagementService(SERVICE_NEW);
    	newSession.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
    	newSession.addInputWithAddEffect(INPUT_PERFORMED_SESSION, PerformedSession.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_SESSION });
    	profiles[2] = newSession.getProfile();
	}
}
