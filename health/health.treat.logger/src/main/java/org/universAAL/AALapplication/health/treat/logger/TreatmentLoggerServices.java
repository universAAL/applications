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
package org.universAAL.AALapplication.health.treat.logger;

import java.util.Hashtable;

import org.universAAL.AALapplication.health.ont.services.HealthServices;
import org.universAAL.AALapplication.health.ont.treatment.Treatment;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.User;

/**
 * The provided services that are registered by the Treatment Logger in the 
 * service bus
 * 
 * @author amedrano
 * @author roni
 */
public class TreatmentLoggerServices extends HealthServices {

	// all the static Strings are used to unique identify special functions and objects
    public static final String TREATMENT_LOGGER_NAMESPACE = 
    	"http://ontology.universAAL.org/TreatmentLogger.owl#"; 
    public static final String MY_URI = 
    	TREATMENT_LOGGER_NAMESPACE + "TreatmentLoggerServices";

    // the services provided in the service bus
    static final String SERVICE_LIST_ALL_TREATMENT_LOG = 
    	TREATMENT_LOGGER_NAMESPACE + "listAllTreatmentLog";
    static final String SERVICE_LIST_TREATMENT_LOG_BETWEEN_TIMESTAMPS = 
    	TREATMENT_LOGGER_NAMESPACE + "listTreatmentsLogBetweenTimestamps";
    static final String SERVICE_TREATMENT_DONE = 
    	TREATMENT_LOGGER_NAMESPACE + "treatmentDone";
    
    static final String INPUT_USER = TREATMENT_LOGGER_NAMESPACE + "user";
    static final String INPUT_TIMESTAMP_FROM = TREATMENT_LOGGER_NAMESPACE + "timestampFrom";
    static final String INPUT_TIMESTAMP_TO = TREATMENT_LOGGER_NAMESPACE + "timestampTo";
    static final String INPUT_TREATMENT = TREATMENT_LOGGER_NAMESPACE + "treatment";

    static final String OUTPUT_TREATMENTS = TREATMENT_LOGGER_NAMESPACE + "matchingTreatments";

    static final ServiceProfile[] profiles = new ServiceProfile[3];

    private static Hashtable treatmentLoggerRestrictions = new Hashtable();
    static {
    	// register in the ontology for the serialization of the object
    	register(TreatmentLoggerServices.class);
    	
    	// inherit the restrictions defined by the superclass
    	addRestriction((Restriction) HealthServices
    			.getClassRestrictionsOnProperty(HealthServices.PROP_MANAGES).copy(),
    			new String[] { HealthServices.PROP_MANAGES },
    			treatmentLoggerRestrictions);
    		
    	addRestriction((Restriction) HealthServices
    			.getClassRestrictionsOnProperty(HealthServices.PROP_MANAGES_FOR_USERS).copy(),
    			new String[] { HealthServices.PROP_MANAGES_FOR_USERS },
    			treatmentLoggerRestrictions);

    	addRestriction((Restriction) HealthServices
    			.getClassRestrictionsOnProperty(HealthServices.PROP_TIMESTAMP_FROM).copy(),
    			new String[] { HealthServices.PROP_TIMESTAMP_FROM },
    			treatmentLoggerRestrictions);

    	addRestriction((Restriction) HealthServices
    			.getClassRestrictionsOnProperty(HealthServices.PROP_TIMESTAMP_TO).copy(),
    			new String[] { HealthServices.PROP_TIMESTAMP_TO },
    			treatmentLoggerRestrictions);

    	// TREATMENT_DONE
    	TreatmentLoggerServices treatmentDone = new TreatmentLoggerServices(
    			SERVICE_TREATMENT_DONE);
    	treatmentDone.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_FOR_USERS });
    	treatmentDone.addInputWithAddEffect(INPUT_TREATMENT, Treatment.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES });
    	profiles[0] = treatmentDone.getProfile();
    	
    	// LIST_ALL_TREATMENT_LOG
    	TreatmentLoggerServices listAllTreatmentLog = new TreatmentLoggerServices(
    			SERVICE_LIST_ALL_TREATMENT_LOG);
    	listAllTreatmentLog.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_FOR_USERS });
    	listAllTreatmentLog.addOutput(OUTPUT_TREATMENTS, Treatment.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES });
    	profiles[1] = listAllTreatmentLog.getProfile();

    	// LIST_ALL_TREATMENTS)BETWEEN_TIMESTAMPS
    	TreatmentLoggerServices listTreatmentLogBetweenTimestamp = new TreatmentLoggerServices(
    			SERVICE_LIST_TREATMENT_LOG_BETWEEN_TIMESTAMPS);
    	listTreatmentLogBetweenTimestamp.addFilteringInput(INPUT_USER, User.MY_URI, 1, 1, 
    			new String[] { PROP_MANAGES_FOR_USERS });
    	listTreatmentLogBetweenTimestamp.addFilteringInput(INPUT_TIMESTAMP_FROM, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_FROM });
    	listTreatmentLogBetweenTimestamp.addFilteringInput(INPUT_TIMESTAMP_TO, TypeMapper.getDatatypeURI(Long.class), 1, 1, 
    			new String[] { PROP_TIMESTAMP_TO });
    	listTreatmentLogBetweenTimestamp.addOutput(OUTPUT_TREATMENTS, Treatment.MY_URI, 0, -1, 
    			new String[] { PROP_MANAGES });
		profiles[2] = listTreatmentLogBetweenTimestamp.getProfile();
    }

    /**
     * Constructor.
     */
    protected TreatmentLoggerServices(String uri) {
    	super(uri);
    }
    
    protected Hashtable getClassLevelRestrictions() {
		return treatmentLoggerRestrictions;
	}
}
