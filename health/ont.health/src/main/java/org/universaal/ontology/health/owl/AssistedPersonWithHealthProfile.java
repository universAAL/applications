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

import org.universAAL.ontology.profile.AssistedPerson;

public class AssistedPersonWithHealthProfile extends AssistedPerson{

//NAMESPACE & PROPERTIES
  public static final String MY_URI = HealthOntology.NAMESPACE
    + "AssistedPersonWithHealthProfile";
  public static final String PROP_HAS_HEALTH_PROFILE = HealthOntology.NAMESPACE
    + "hasHealthProfile";
  public static final String PROP_ASSISTED_PERSONWHP = HealthOntology.NAMESPACE
  + "assistedPersonWHP";
  
//CONSTRUCTORS
  public AssistedPersonWithHealthProfile () {
    super();
  }
  
  public AssistedPersonWithHealthProfile (String uri) {
    super(uri);
  }

  public AssistedPersonWithHealthProfile (AssistedPerson ap, HealthProfile hp){
	  hp.setAssignedAssistedPerson(ap);
	  this.setHealthProfile(hp);
	  this.setAssistedPersonWHP(ap);
	  
  }
  //En caso de que creemos el hp y luego asignemos el assited person
  public AssistedPersonWithHealthProfile (HealthProfile hp){
	  this.setHealthProfile(hp);
	  this.setAssistedPersonWHP(hp.getAssignedAssistedPerson());
	  
  }
  public AssistedPersonWithHealthProfile (AssistedPerson ap, Treatment[] ts){
	  this.setAssistedPersonWHP(ap);
	  HealthProfile hp = new HealthProfile(ts);
	  hp.setAssignedAssistedPerson(ap);
	  this.setHealthProfile(hp);
	  
  }
  
  public AssistedPersonWithHealthProfile (AssistedPerson ap, Treatment t){
	  this.setAssistedPersonWHP(ap);
	  HealthProfile hp = new HealthProfile(t);
	  hp.setAssignedAssistedPerson(ap);
	  this.setHealthProfile(hp);
  }
  
  
  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	  return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true 
      && props.containsKey(PROP_HAS_HEALTH_PROFILE)
      && props.containsKey(PROP_ASSISTED_PERSONWHP);
  }

//GETTERS & SETTERS
  
  public HealthProfile getHealthProfile() {
	  return (HealthProfile)props.get(PROP_HAS_HEALTH_PROFILE);
  }		

  public void setHealthProfile(HealthProfile hp) {
	  if (hp != null)
		  props.put(PROP_HAS_HEALTH_PROFILE, hp);
  }	

  public AssistedPerson getAssistedPersonWHP() {
	  return (AssistedPerson)props.get(PROP_ASSISTED_PERSONWHP);
  }		

  public void setAssistedPersonWHP(AssistedPerson ap) {
	  if (ap != null)
		  props.put(PROP_ASSISTED_PERSONWHP, ap);
  }	

  
//OTHER METHODS
  
  public Treatment[] getAssociatedTreatments(){
	  Treatment[] associatedTreatments = getHealthProfile().getTreatments(); 
	  return associatedTreatments;
  }
  
  
}
