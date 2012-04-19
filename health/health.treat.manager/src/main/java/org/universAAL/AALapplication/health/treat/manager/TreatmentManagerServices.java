/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Politécnica de Madrdid
	
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
package org.universAAL.AALapplication.health.treat.manager;

import java.util.Hashtable;

import org.universAAL.AALapplication.health.ont.services.HealthServices;
import org.universAAL.AALapplication.health.ont.treatment.Treatment;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.User;

/**
 * The provided services that are registered by the Treatment Manager in the 
 * service bus
 * 
 * @author amedrano
 * @author roni
 */
public class TreatmentManagerServices extends HealthServices {

	// all the static Strings are used to unique identify special functions and objects
    public static final String TREATMENT_MANAGER_NAMESPACE = 
    	"http://ontology.universAAL.org/TreatmentManager.owl#"; 
    public static final String MY_URI = 
    	TREATMENT_MANAGER_NAMESPACE + "TreatmentManagerServices";

    // the services provided in the service bus
    static final String SERVICE_NEW_TREATMENT = 
    	TREATMENT_MANAGER_NAMESPACE + "newTreatment";
    static final String SERVICE_DELETE_TREATMENT = 
    	TREATMENT_MANAGER_NAMESPACE + "deleteTreatment";
    static final String SERVICE_EDIT_TREATMENT = 
    	TREATMENT_MANAGER_NAMESPACE + "editTreatment";
    static final String SERVICE_LIST_ALL_TREATMENTS = 
    	TREATMENT_MANAGER_NAMESPACE + "listAllTreatments";
    static final String SERVICE_LIST_TREATMENTS_BETWEEN_TIMESTAMPS = 
    	TREATMENT_MANAGER_NAMESPACE + "listTreatmentsBetweenTimestamps";
    
    static final String INPUT_USER = TREATMENT_MANAGER_NAMESPACE + "user";
    static final String INPUT_TIMESTAMP_FROM = TREATMENT_MANAGER_NAMESPACE + "timestampFrom";
    static final String INPUT_TIMESTAMP_TO = TREATMENT_MANAGER_NAMESPACE + "timestampTo";
    static final String INPUT_TREATMENT = TREATMENT_MANAGER_NAMESPACE + "treatment";
    static final String INPUT_OLD_TREATMENT = TREATMENT_MANAGER_NAMESPACE + "oldTreatment";
    static final String INPUT_NEW_TREATMENT = TREATMENT_MANAGER_NAMESPACE + "newTreatment";

    static final String OUTPUT_TREATMENTS = TREATMENT_MANAGER_NAMESPACE + "matchingTreatments";

    static final ServiceProfile[] profiles = new ServiceProfile[5];

    private static Hashtable treatmentManagerRestrictions = new Hashtable();
    static {
    	// register in the ontology for the serialization of the object
    	register(TreatmentManagerServices.class);
    	
    	// inherit the restrictions defined by the superclass
    	addRestriction((Restriction) HealthServices
    			.getClassRestrictionsOnProperty(HealthServices.PROP_MANAGES).copy(),
    			new String[] { HealthServices.PROP_MANAGES },
    			treatmentManagerRestrictions);
    		
    	addRestriction((Restriction) HealthServices
    			.getClassRestrictionsOnProperty(HealthServices.PROP_MANAGES_FOR_USERS).copy(),
    			new String[] { HealthServices.PROP_MANAGES_FOR_USERS },
    			treatmentManagerRestrictions);

    	addRestriction((Restriction) HealthServices
    			.getClassRestrictionsOnProperty(HealthServices.PROP_TIMESTAMP_FROM).copy(),
    			new String[] { HealthServices.PROP_TIMESTAMP_FROM },
    			treatmentManagerRestrictions);

    	addRestriction((Restriction) HealthServices
    			.getClassRestrictionsOnProperty(HealthServices.PROP_TIMESTAMP_TO).copy(),
    			new String[] { HealthServices.PROP_TIMESTAMP_TO },
    			treatmentManagerRestrictions);

    	// NEW_TREATMENT
    	TreatmentManagerServices newTreatment = new TreatmentManagerServices(
    			SERVICE_NEW_TREATMENT);
    	newTreatment.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_FOR_USERS });
    	newTreatment.addInputWithAddEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES });
    	profiles[0] = newTreatment.getProfile();
    	
    	// DELETE_TREATMENT
    	TreatmentManagerServices deleteTreatment = new TreatmentManagerServices(
    			SERVICE_DELETE_TREATMENT);
    	deleteTreatment.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_FOR_USERS });
    	deleteTreatment.addInputWithRemoveEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES });
    	profiles[1] = deleteTreatment.getProfile();

    	// EDIT_TREATMENT
    	TreatmentManagerServices editTreatment = new TreatmentManagerServices(
    			SERVICE_EDIT_TREATMENT);
    	editTreatment.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_FOR_USERS });
    	editTreatment.addInputWithChangeEffect(INPUT_OLD_TREATMENT, Treatment.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES });
    	editTreatment.addInputWithAddEffect(INPUT_NEW_TREATMENT, Treatment.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES });
    	profiles[2] = editTreatment.getProfile();

    	// LIST_ALL_TREATMENTS
    	TreatmentManagerServices listAllTreatments = new TreatmentManagerServices(
    			SERVICE_LIST_ALL_TREATMENTS);
    	listAllTreatments.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_FOR_USERS });
    	listAllTreatments.addOutput(OUTPUT_TREATMENTS, Treatment.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES });
    	profiles[3] = listAllTreatments.getProfile();

    	// LIST_ALL_TREATMENTS)BETWEEN_TIMESTAMPS
    	TreatmentManagerServices listTreatmentsBetweenTimestamp = new TreatmentManagerServices(
    			SERVICE_LIST_TREATMENTS_BETWEEN_TIMESTAMPS);
    	listTreatmentsBetweenTimestamp.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_FOR_USERS });
    	listTreatmentsBetweenTimestamp.addFilteringInput(INPUT_TIMESTAMP_FROM, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_FROM });
    	listTreatmentsBetweenTimestamp.addFilteringInput(INPUT_TIMESTAMP_TO, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_TO });
    	listTreatmentsBetweenTimestamp.addOutput(OUTPUT_TREATMENTS, Treatment.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES });
		profiles[4] = listTreatmentsBetweenTimestamp.getProfile();
    }

    /**
     * Constructor.
     */
    protected TreatmentManagerServices(String uri) {
    	super(uri);
    }
    
    protected Hashtable getClassLevelRestrictions() {
		return treatmentManagerRestrictions;
	}
}
