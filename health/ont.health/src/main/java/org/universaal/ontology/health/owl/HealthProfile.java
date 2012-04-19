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

import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.profile.AssistedPerson;

public class HealthProfile extends ManagedIndividual{

//NAMESPACE & PROPERTIES
  public static final String MY_URI = HealthOntology.NAMESPACE
    + "HealthProfile";
  public static final String PROP_HAS_TREATMENT = HealthOntology.NAMESPACE
    + "hasTreatment";
  public static final String PROP_IS_ASSIGNED_TO_AP = HealthOntology.NAMESPACE
  + "isAssignedToAP";
  
//CONSTRUCTORS
  public HealthProfile () {
    super();
  }
  
  public HealthProfile (String uri) {
	  super(uri);
  }
  public HealthProfile (Treatment t){
	  this.addTreatment(t);
  }
  
  public HealthProfile (Treatment[] ts){
	  this.setTreatments(ts);
  }
  /*
  public HealthProfile (AssistedPersonWithHealthProfile apwhp, Treatment t){
	  this.setAssignedAssistedPerson(apwhp);
	  this.addTreatment(t);
  }

  public HealthProfile (AssistedPersonWithHealthProfile apwhp, Treatment[] ts){
	  this.setAssignedAssistedPerson(apwhp);
	  this.setTreatments(ts);
  }
*/
  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true 
      && props.containsKey(PROP_HAS_TREATMENT)
      && props.containsKey(PROP_IS_ASSIGNED_TO_AP);
  }
  
//GETTERS & SETTERS

  public AssistedPerson getAssignedAssistedPerson() {
	  return (AssistedPerson)props.get(PROP_IS_ASSIGNED_TO_AP);
  }		

  public void setAssignedAssistedPerson(AssistedPerson ap) {
	  if (ap != null)
		  props.put(PROP_IS_ASSIGNED_TO_AP, ap);
  }
  
  public Treatment[] getTreatments() {

		Object propList = props.get(PROP_HAS_TREATMENT);
		if (propList instanceof List) {
			return (Treatment[]) ((List) propList).toArray(new Treatment[0]);
		} else {
			List returnList = new ArrayList();
			if (propList != null)
				returnList.add((Treatment) propList);
			return (Treatment[]) returnList.toArray(new Treatment[0]);
		}  
	}	

	public void setTreatments(Treatment[] treatments) {
		List propList = new ArrayList(treatments.length);
		for (int i = 0; i < treatments.length; i++) {
			propList.add(treatments[i]);
		}
		props.put(PROP_HAS_TREATMENT, propList);
	}

	public void addTreatment(Treatment treatment) {
		Object propList = props.get(PROP_HAS_TREATMENT);
		if (propList instanceof List) {
			List list = (List) propList;
			list.add(treatment);
			props.put(PROP_HAS_TREATMENT, list);
		} else if (propList == null) {
			props.put(PROP_HAS_TREATMENT, treatment);
		} else {
			List list = new ArrayList();
			list.add((Treatment) propList);
			list.add(treatment);
			props.put(PROP_HAS_TREATMENT, list);
		}
	}

  
 //OTHER METHODS
	
  public void assignHealthProfileToAP(AssistedPersonWithHealthProfile ap){
	  this.setAssignedAssistedPerson(ap);
	  //The assisted person becomes an assisted person with a health profile
	  AssistedPersonWithHealthProfile apwhp = new AssistedPersonWithHealthProfile(this);
	  
  }
}
