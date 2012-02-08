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

package org.universAAL.AALapplication.health.ont.measurement;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;

public class MultidimensionalMeasure extends Measure {
	
	public static final String MEASUREMENT_NAMESPACE = Measure.MEASUREMENT_NAMESPACE;
	public static final String MY_URI;

	public static final String PROP_VALUE;
	public static final String PROP_UNIT;
	public static final String PROP_DIMENSION;
	
	static {
		MY_URI = MEASUREMENT_NAMESPACE + "UnidimensionalMeasure";
		
		PROP_VALUE = MEASUREMENT_NAMESPACE + "Value";
		PROP_UNIT = MEASUREMENT_NAMESPACE + "Unit";
		PROP_DIMENSION = MEASUREMENT_NAMESPACE + "Dimension";
		
		register(MultidimensionalMeasure.class);
    }
	
	public static Restriction getClassRestrictionsOnProperty(String propURI) {
    	
    	if (PROP_VALUE.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(Integer[].class), 1, 1);
		if (PROP_UNIT.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String[].class), 1, 1);
		if (PROP_DIMENSION.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String[].class), 1, 1);
					
		return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}
	
	public static String[] getStandardPropertyURIs() {
        String[] inherited = ManagedIndividual.getStandardPropertyURIs();
        String[] proper = {PROP_VALUE, PROP_UNIT, PROP_DIMENSION};
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}
	
	public static String getRDFSComment() {
		return "The description of a multidimensional measure";
	}

	public static String getRDFSLabel() {
		return "Multidimensional measure";
	}

	public int getPropSerializationType(String propURI) {
		return ManagedIndividual.PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true;
	}

	//Constructors
	
	public MultidimensionalMeasure() {
		
	}

	public MultidimensionalMeasure(String uri) {
		super(uri);
	}

	public MultidimensionalMeasure(String uriPrefix, int numProps) {
		super(uriPrefix, numProps);
	}

}
