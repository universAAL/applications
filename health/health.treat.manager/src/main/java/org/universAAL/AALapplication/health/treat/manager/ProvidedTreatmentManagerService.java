/**
 * 
 */
package org.universAAL.AALapplication.health.treat.manager;

import java.util.Hashtable;

import org.universAAL.AALapplication.health.treat.manager.osgi.Activator;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.services.TreatmentManagementService;

/**
 * @author amedrano
 *
 */
public class ProvidedTreatmentManagerService extends TreatmentManagementService {

	private ProvidedTreatmentManagerService(String instanceURI) {
		super(instanceURI);
	}
	
	public String getClassURI() {
		return MY_URI;
	}
	
	public static final String NAMESPACE = "http://ontology.lst.tfo.upm.es/TreatmentManagerServoce.owl#";
	
	public static final String MY_URL = NAMESPACE + "TreatmentService";
	
	public static final String SERVICE_EDIT = NAMESPACE +"editTreatment";
	 
	public static final String SERVICE_LIST = NAMESPACE +"listTreatment";
	public static final String SERVICE_LIST_BT= NAMESPACE +"listBetweenTimestamps";
	public static final String SERVICE_NEW = NAMESPACE +"newTreatment";
	public static final String SERVICE_REMOVE = NAMESPACE +"removeTreatment";
	
	//INPUT PARAMETERS URI
		public static final String INPUT_USER      = HealthOntology.NAMESPACE + "user";
		public static final String INPUT_TREATMENT      = HealthOntology.NAMESPACE + "treatment";
		public static final String INPUT_TIMESTAMP_FROM = HealthOntology.NAMESPACE + "timestampFrom";
		public static final String INPUT_TIMESTAMP_TO   = HealthOntology.NAMESPACE + "timestampTo";
		public static final String OUTPUT_TREATMENTS = HealthOntology.NAMESPACE + "matchingTreatments";
	
	static final ServiceProfile[] profiles = new ServiceProfile[5];
    private static Hashtable serverLightingRestrictions = new Hashtable();
    static {
	// we need to register all classes in the ontology for the serialization
	// of the object
	// OntologyManagement.getInstance().register(new SimpleOntology(MY_URI,
	// Lighting.MY_URI));
	OntologyManagement.getInstance().register(Activator.context,
		new SimpleOntology(MY_URI, TreatmentManagementService.MY_URI,
			new ResourceFactory() {
			    public Resource createInstance(String classURI,
				    String instanceURI, int factoryIndex) {
				return new ProvidedTreatmentManagerService(instanceURI);
			    }
			}));

		String[] ppManages = new String[] {TreatmentManagementService.PROP_MANAGES_TREATMENT};
		
		addRestriction((MergedRestriction) TreatmentManagementService
			.getClassRestrictionsOnProperty(TreatmentManagementService.MY_URI,
				TreatmentManagementService.PROP_MANAGES_TREATMENT).copy(), ppManages ,
			serverLightingRestrictions);
		
		//Edit
		ProvidedTreatmentManagerService edit = new ProvidedTreatmentManagerService(SERVICE_EDIT);
		edit.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
		edit.addInputWithChangeEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_TREATMENT });
		profiles[0] = edit.getProfile();
		
		//list
		ProvidedTreatmentManagerService list = new ProvidedTreatmentManagerService(SERVICE_LIST);
		list.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
    	list.addOutput(OUTPUT_TREATMENTS, Treatment.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES_TREATMENT });
    	profiles[1] = list.getProfile();
    	
		//listBetween
    	ProvidedTreatmentManagerService listBetween = new ProvidedTreatmentManagerService(SERVICE_LIST_BT);
    	listBetween.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
    	listBetween.addFilteringInput(INPUT_TIMESTAMP_FROM, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_FROM });
    	listBetween.addFilteringInput(INPUT_TIMESTAMP_TO, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_TO });
    	listBetween.addOutput(OUTPUT_TREATMENTS, Treatment.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES_TREATMENT });
    	profiles[2] = listBetween.getProfile();
		
    	//new
    	ProvidedTreatmentManagerService newT = new ProvidedTreatmentManagerService(SERVICE_NEW);
    	newT.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
    	newT.addInputWithAddEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_TREATMENT });
    	profiles[3] = newT.getProfile();
    	
		//remove
		ProvidedTreatmentManagerService remove = new ProvidedTreatmentManagerService(SERVICE_REMOVE);
		remove.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_ASSISTED_USER });
		remove.addInputWithRemoveEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_TREATMENT });
		profiles[4] = remove.getProfile();
    }
}
