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


public class PhysicalActivity extends Treatment {

	public static final String TREATMENT_NAMESPACE = Treatment.TREATMENT_NAMESPACE;
	public static final String MY_URI;
	
	public static final String PROP_HAS_PHYSICAL_ACTIVITY_PHASE;
	    
	static {
		MY_URI = TREATMENT_NAMESPACE + "PhysicalActivity";
		PROP_HAS_PHYSICAL_ACTIVITY_PHASE = TREATMENT_NAMESPACE + "hasPhysicalActivityPhase";
				
		register(PhysicalActivity.class);
    }
       
    public static Restriction getClassRestrictionsOnProperty(String propURI) {
            	
    	if (PROP_HAS_PHYSICAL_ACTIVITY_PHASE.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TreatmentDetails.MY_URI, -1, 1);
		
		return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
        String[] inherited = ManagedIndividual.getStandardPropertyURIs();
        String[] proper = {PROP_HAS_PHYSICAL_ACTIVITY_PHASE};
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	public static String getRDFSComment() {
		return "Physical activity is a type of treatment";
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
	
	public PhysicalActivity() {
		
	}

	public PhysicalActivity(String uri) {
		super(uri);
	}

	public PhysicalActivity(String uriPrefix, int numProps) {
		super(uriPrefix, numProps);
	}


}
