/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Politécnica de Madrdid
	
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
package org.universAAL.AALapplication.health.ont.treatment;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.profile.UserProfile;


public class Treatment extends ManagedIndividual {
	
	public static final String TREATMENT_NAMESPACE = "http://ontology.universAAL.org/HealthManager.owl#";
	public static final String MY_URI;
	
	public static final String PROP_HAS_TREATMENTDETAILS;
	public static final String PROP_HAS_TREATMENTPLANNING;
	public static final String PROP_HAS_CAREGIVER;
	
	public static final String PROP_NAME;
	public static final String PROP_COMPLETITION;

    
	static {
		MY_URI = TREATMENT_NAMESPACE + "Treatment";
		
		PROP_HAS_TREATMENTDETAILS = TREATMENT_NAMESPACE + "hasDetails";
		PROP_HAS_TREATMENTPLANNING = TREATMENT_NAMESPACE + "hasPlanning";
		PROP_HAS_CAREGIVER = TREATMENT_NAMESPACE + "hasCaregiver";
		
		PROP_NAME = TREATMENT_NAMESPACE + "name";
		PROP_COMPLETITION = TREATMENT_NAMESPACE + "completition";
		
		register(Treatment.class);
    }
       
    public static Restriction getClassRestrictionsOnProperty(String propURI) {
            	
    	if (PROP_HAS_TREATMENTDETAILS.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TreatmentDetails.MY_URI, 1, 1);
		if (PROP_HAS_TREATMENTPLANNING.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TreatmentPlanning.MY_URI, 1, 1);
		if (PROP_HAS_CAREGIVER.equals(propURI))
			return Restriction.getAllValuesRestriction(propURI,UserProfile.MY_URI); 
					
		if (PROP_NAME.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String.class), 1, 0);
		if (PROP_NAME.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(Integer.class), 1, 1);
		
		return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
        String[] inherited = ManagedIndividual.getStandardPropertyURIs();
        String[] proper = {PROP_HAS_TREATMENTDETAILS, PROP_HAS_TREATMENTPLANNING, PROP_HAS_CAREGIVER, 
        				   PROP_NAME, PROP_COMPLETITION};
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "The description of the treatment";
	}

	public static String getRDFSLabel() {
		return "Treatment";
	}

	public int getPropSerializationType(String propURI) {
		return ManagedIndividual.PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true;
	}

	//Constructors
	
	public Treatment() {
		
	}

	public Treatment(String uri) {
		super(uri);
	}

	public Treatment(String uriPrefix, int numProps) {
		super(uriPrefix, numProps);
	}



}
