/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es - Universidad Polit√©cnica de Madrid
 *
 * OCO Source Materials
 * © Copyright IBM Corp. 2011
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

import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.SubProfile;

public class HealthProfile extends SubProfile{

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

	/**
	 * Deletes a treatment with the given URI from the list.
	 *  
	 * @param treatmentURI The URI of the treatment to be deleted
	 * 
	 * @return True if the treatment was found and deleted from the list, 
	 * otherwise returns False 
	 */
	public boolean deleteTreatment(String treatmentURI) {
		boolean found = false;
		List list = new ArrayList();
		Treatment[] treatments = getTreatments();
		for(int i=0; i<treatments.length; i++) {
			if(treatments[i].getURI().equals(treatmentURI)) {
				found = true;	
			} else {
				list.add(treatments[i]);
			}
		}
		props.put(PROP_HAS_TREATMENT, list);
		return found;
	}

	/**
	 * Replaces the given treatment with the treatment in the list with the same 
	 * URI.
	 *  
	 * @param treatment The new treatment to be added to the list  
	 * 
	 * @return True if the treatment was found and replaced, otherwise returns 
	 * False 
	 */
	public boolean editTreatment(Treatment treatment) {
		boolean found = false;
		List list = new ArrayList();
		Treatment[] treatments = getTreatments();
		for(int i=0; i<treatments.length; i++) {
			if(treatments[i].getURI().equals(treatment.getURI())) {
				found = true;	
				list.add(treatment);
			} else {
				list.add(treatments[i]);
			}
		}
		props.put(PROP_HAS_TREATMENT, list);
		return found;
	}
  
	//OTHER METHODS
	public void assignHealthProfileToAP(AssistedPerson ap){
		this.setAssignedAssistedPerson(ap);
		//The assisted person becomes an assisted person with a health profile
		//AssistedPersonWithHealthProfile apwhp = new AssistedPersonWithHealthProfile(this);
	}
}
