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

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.AALapplication.health.ont.schedule.Event;
import org.universAAL.AALapplication.health.ont.schedule.Task;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;

public class TreatmentPlanning extends ManagedIndividual {

	public static final String TREATMENT_NAMESPACE = Treatment.TREATMENT_NAMESPACE;
	public static final String MY_URI;
	
	public static final String PROP_STARTDATE;
	public static final String PROP_HAS_EVENT;
	public static final String PROP_HAS_TASK;
	
	static {
		MY_URI = TREATMENT_NAMESPACE + "TreatmentPlanning";
		
		PROP_STARTDATE = TREATMENT_NAMESPACE + "StartDate";
		PROP_HAS_EVENT = TREATMENT_NAMESPACE + "hasEvent";
		PROP_HAS_TASK = TREATMENT_NAMESPACE + "hasTask";
				
		register(TreatmentPlanning.class);
    }
	
	 public static Restriction getClassRestrictionsOnProperty(String propURI) {
     	
	    	if (PROP_STARTDATE.equals(propURI))
				return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 1);
	    	if (PROP_HAS_EVENT.equals(propURI))
	    		return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(Event.MY_URI), -1, 1);
			if (PROP_HAS_TASK.equals(propURI))
				return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(Task.MY_URI), -1, 1);
			
			return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
		}

		public static String[] getStandardPropertyURIs() {
	        String[] inherited = ManagedIndividual.getStandardPropertyURIs();
	        String[] proper = {PROP_STARTDATE, PROP_HAS_EVENT, PROP_HAS_TASK};
			String[] merged = new String[inherited.length + proper.length];
			System.arraycopy(inherited, 0, merged, 0, inherited.length);
			System.arraycopy(proper, 0, merged, inherited.length, proper.length);
			return merged;
		}

		public static String getRDFSComment() {
			return "The planning for the treatment";
		}

		public static String getRDFSLabel() {
			return "Treatment planning";
		}

		public int getPropSerializationType(String propURI) {
			return ManagedIndividual.PROP_SERIALIZATION_FULL;
		}

		public boolean isWellFormed() {
			return true;
		}

		//Constructors
		
		public TreatmentPlanning() {
			
		}

		public TreatmentPlanning(String uri) {
			super(uri);
		}

		public TreatmentPlanning(String uriPrefix, int numProps) {
			super(uriPrefix, numProps);
		}
}
