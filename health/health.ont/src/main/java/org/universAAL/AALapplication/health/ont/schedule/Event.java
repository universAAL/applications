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


package org.universAAL.AALapplication.health.ont.schedule;


import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;

public class Event extends ManagedIndividual {

	public static final String SCHEDULE_NAMESPACE = "http://ontology.universAAL.org/Schedule.owl#";
	public static final String MY_URI;
	
	public static final String PROP_NAME;
	public static final String PROP_RECURRENCE;
	
	static {
		MY_URI = SCHEDULE_NAMESPACE + "Event";
		
		PROP_NAME = SCHEDULE_NAMESPACE + "Name";
		PROP_RECURRENCE = SCHEDULE_NAMESPACE + "Recurrence";
		
		register(Event.class);
    }
	
	public static Restriction getClassRestrictionsOnProperty(String propURI) {
    	
    	if (PROP_NAME.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String.class), 1, 1);
		if (PROP_RECURRENCE.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String.class), 1, 1);
				
		return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
        String[] inherited = ManagedIndividual.getStandardPropertyURIs();
        String[] proper = {PROP_NAME, PROP_RECURRENCE};
		String[] merged = new String[inherited.length + proper.length];
		System.arraycopy(inherited, 0, merged, 0, inherited.length);
		System.arraycopy(proper, 0, merged, inherited.length, proper.length);
		return merged;
	}

	
	public static String getRDFSComment() {
		return "Event description";
	}

	public static String getRDFSLabel() {
		return "Event";
	}

	public int getPropSerializationType(String propURI) {
		return ManagedIndividual.PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true;
	}

	//Constructors
	
	public Event() {
		
	}

	public Event(String uri) {
		super(uri);
	}

	public Event(String uriPrefix, int numProps) {
		super(uriPrefix, numProps);
	}

	
}
