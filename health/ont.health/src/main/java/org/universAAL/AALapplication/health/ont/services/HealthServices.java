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
package org.universAAL.AALapplication.health.ont.services;

import java.util.Hashtable;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.profile.HealthProfile;
import org.universAAL.ontology.profile.User;

/**
 * Ontological service that handles health functions. Methods included in this
 * class are the mandatory ones for representing an ontological service in Java
 * classes for uAAL.
 * 
 * @author roni
 */
public class HealthServices extends Service {

	// all the static Strings are used to unique identify special functions and objects
    public static final String HEALTH_SERVICES_NAMESPACE = 
    	"http://ontology.universAAL.org/HealthServices.owl#"; 
    public static final String MY_URI = 
    	HEALTH_SERVICES_NAMESPACE + "HealthServices";

    public static final String PROP_MANAGES = 
    	HEALTH_SERVICES_NAMESPACE + "propManages";
    public static final String PROP_MANAGES_FOR_USERS = 
    	HEALTH_SERVICES_NAMESPACE + "propManagesForUsers";
    public static final String PROP_TIMESTAMP_FROM = 
    	HEALTH_SERVICES_NAMESPACE + "timestampFrom";
    public static final String PROP_TIMESTAMP_TO = 
    	HEALTH_SERVICES_NAMESPACE + "timestampTo";

    private static Hashtable healthRestrictions = new Hashtable();
    static {
    	// register in the ontology for the serialization of the object
    	register(HealthServices.class);
    	
    	// define the restrictions
    	addRestriction(Restriction.getAllValuesRestriction(PROP_MANAGES,
    			HealthProfile.MY_URI), new String[] { PROP_MANAGES },
    			healthRestrictions);

    	addRestriction(Restriction.getAllValuesRestriction(PROP_MANAGES_FOR_USERS,
    			User.MY_URI), new String[] { PROP_MANAGES_FOR_USERS },
    			healthRestrictions);

    	addRestriction(Restriction.getAllValuesRestriction(PROP_TIMESTAMP_FROM,
    			TypeMapper.getDatatypeURI(Long.class)),
    			new String[] { PROP_TIMESTAMP_FROM }, 
    			healthRestrictions);
    	
    	addRestriction(Restriction.getAllValuesRestriction(PROP_TIMESTAMP_TO,
    			TypeMapper.getDatatypeURI(Long.class)),
    			new String[] { PROP_TIMESTAMP_TO }, 
    			healthRestrictions);
    }
    
    public static Restriction getClassRestrictionsOnProperty(String propURI) {
    	if(propURI == null)
    	    return null;
    	Object r = healthRestrictions.get(propURI);
    	if(r instanceof Restriction)
    	    return (Restriction) r;
    	return Service.getClassRestrictionsOnProperty(propURI);
    }

    public static String getRDFSComment() {
    	return "The class of services for health management";
    }

    public static String getRDFSLabel() {
    	return "Health Management";
    }

    /**
     * Constructor.
     */
    public HealthServices(String uri) {
    	super(uri);
    }

    protected Hashtable getClassLevelRestrictions() {
    	return healthRestrictions;
    }

    public int getPropSerializationType(String propURI) {
    	return PROP_MANAGES.equals(propURI) ? PROP_SERIALIZATION_FULL
    		: PROP_SERIALIZATION_OPTIONAL;
    }

    public boolean isWellFormed() {
    	return true;
    }
}
