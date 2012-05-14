/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es - Universidad Politécnica de Madrid
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
package org.universaal.ontology.health.owl;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.ontology.profile.AssistedPersonProfile;
import org.universAAL.ontology.profile.Caregiver;

public class PhysicalActivity extends HealthyHabitsAdoption {
  public static final String MY_URI = HealthOntology.NAMESPACE
    + "PhysicalActivity";
  public static final String PROP_HAS_ASSOCIATED_MEASUREMENT = HealthOntology.NAMESPACE
    + "hasAssociatedMeasurement";


  public PhysicalActivity () {
    super();
  }
  
  public PhysicalActivity (String uri) {
    super(uri);
  }

  public PhysicalActivity (AssistedPersonProfile assistedPerson, Caregiver caregiver, String tname, String description, XMLGregorianCalendar stDt){
	 super(assistedPerson, caregiver, tname, description, stDt);
  } 
  
  public PhysicalActivity (AssistedPersonProfile assistedPerson, Caregiver caregiver, String tname, String description, TreatmentPlanning tp){
	  super(assistedPerson, caregiver, tname, description, tp);
  }
  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	  return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true 
      && props.containsKey(PROP_HAS_ASSOCIATED_MEASUREMENT);
  }

  public TakeMeasurementActivity getHasAssociatedMeasurement() {
    return (TakeMeasurementActivity)props.get(PROP_HAS_ASSOCIATED_MEASUREMENT);
  }		

  public void setHasAssociatedMeasurement(TakeMeasurementActivity associatedMeasurement) {
    if (associatedMeasurement != null)
      props.put(PROP_HAS_ASSOCIATED_MEASUREMENT, associatedMeasurement);
  }		
}
