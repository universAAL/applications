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


public class PhysicalActivityPhase extends PhysicalActivity {

	public static final String TREATMENT_NAMESPACE = Treatment.TREATMENT_NAMESPACE;
	public static final String MY_URI;
	
	public static final String PROP_INTENSITY;
	public static final String PROP_TYPE;
	public static final String PROP_REPETITIONS;
	public static final String PROP_ORDER;
	
	
	static {
		MY_URI = TREATMENT_NAMESPACE + "PhysicalActivityPhase";
		PROP_INTENSITY = TREATMENT_NAMESPACE + "Intensity";
		PROP_TYPE = TREATMENT_NAMESPACE + "Type";
		PROP_REPETITIONS = TREATMENT_NAMESPACE + "Repetitions";
		PROP_ORDER = TREATMENT_NAMESPACE + "Repetitions";
		
		register(PhysicalActivityPhase.class);
    }
	
	 public static Restriction getClassRestrictionsOnProperty(String propURI) {
     	
	    	if (PROP_INTENSITY.equals(propURI))
	    		return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String.class), 1, 1);
			if (PROP_TYPE.equals(propURI))
				return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String.class), 1, 1);
			if (PROP_REPETITIONS.equals(propURI))
				return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(Integer.class), 1, 1);
			if (PROP_ORDER.equals(propURI))
				return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(Integer.class), 1, 1);
			
			return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
		}

	 public static String[] getStandardPropertyURIs() {
	        String[] inherited = ManagedIndividual.getStandardPropertyURIs();
	        String[] proper = {PROP_INTENSITY, PROP_TYPE, PROP_REPETITIONS, PROP_ORDER};
			String[] merged = new String[inherited.length + proper.length];
			System.arraycopy(inherited, 0, merged, 0, inherited.length);
			System.arraycopy(proper, 0, merged, inherited.length, proper.length);
			return merged;
		}

		public static String getRDFSComment() {
			return "Physical activity is divided into phases";
		}

		public static String getRDFSLabel() {
			return "Physical activity phase";
		}

		public int getPropSerializationType(String propURI) {
			return ManagedIndividual.PROP_SERIALIZATION_FULL;
		}

		public boolean isWellFormed() {
			return true;
		}

		//Constructors
		
		public PhysicalActivityPhase() {
			
		}

		public PhysicalActivityPhase(String uri) {
			super(uri);
		}

		public PhysicalActivityPhase(String uriPrefix, int numProps) {
			super(uriPrefix, numProps);
		}
		
}
