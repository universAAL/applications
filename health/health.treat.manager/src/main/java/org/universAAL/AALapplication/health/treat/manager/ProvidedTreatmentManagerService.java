/*******************************************************************************
 * Copyright 2013 Universidad Polit�cnica de Madrid
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
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.services.TreatmentManagementService;
import org.universAAL.ontology.profile.User;

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
		public static final String INPUT_USER      = HealthProfileOntology.NAMESPACE + "user";
		public static final String INPUT_TREATMENT      = HealthProfileOntology.NAMESPACE + "treatment";
		public static final String INPUT_TIMESTAMP_FROM = HealthProfileOntology.NAMESPACE + "timestampFrom";
		public static final String INPUT_TIMESTAMP_TO   = HealthProfileOntology.NAMESPACE + "timestampTo";
		public static final String OUTPUT_TREATMENTS = HealthProfileOntology.NAMESPACE + "matchingTreatments";
	
	static final ServiceProfile[] profiles = new ServiceProfile[5];
    private static Hashtable serverRestrictions = new Hashtable();
    static {

	OntologyManagement.getInstance().register(Activator.context,
		new SimpleOntology(MY_URI, TreatmentManagementService.MY_URI,
			new ResourceFactory() {
			    public Resource createInstance(String classURI,
				    String instanceURI, int factoryIndex) {
				return new ProvidedTreatmentManagerService(instanceURI);
			    }
			}));

		String[] ppManages = new String[] {TreatmentManagementService.PROP_MANAGES_TREATMENT};
		
//		addRestriction((MergedRestriction) TreatmentManagementService
//			.getClassRestrictionsOnProperty(TreatmentManagementService.MY_URI,
//				TreatmentManagementService.PROP_MANAGES_TREATMENT).copy(), ppManages ,
//			serverRestrictions);
		
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
