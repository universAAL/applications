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
package org.universaal.ontology.owl;


import org.universaal.ontology.health.owl.MotivationalStatusType;



public abstract class MotivationalMessage extends Message {
  
//NAMESPACE & PROPERTIES
	
  public static final String MY_URI = MessageOntology.NAMESPACE
    + "MotivationalMessage";
  
  public static final String PROP_ILLNESS = MessageOntology.NAMESPACE
		    + "illness";
  public static final String PROP_TREATMENT_TYPE = MessageOntology.NAMESPACE
    + "treatment_type";
  public static final String PROP_MOTIVATIONAL_STATUS = MessageOntology.NAMESPACE
    + "motivationalStatus";
  public static final String PROP_MESSAGE_TYPE = MessageOntology.NAMESPACE
		    + "messageType";
  
//CONSTRUCTORS
  
  public MotivationalMessage () {
    super();
  }
  
  public MotivationalMessage (String uri) {
    super(uri);
  }
  
  public MotivationalMessage (String illness, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification mtype, Object content) {
	  	this.setIllness(illness);
	  	this.setTreatmentType(treatmentType);
	  	this.setMotivationalStatus(motStatus);
	  	this.setMMessageType(mtype);
	  	this.setContent(content);
}
  
  
  /*
  public MotivationalMessage(User sender, User receiver, MotivationalMessageClassification contextType, int depth, Treatment t, String mType, MotivationalStatusType mStatus, Object content, String file_rute){
	  this.setContext(contextType);
	  this.setDepth(depth);
	  this.setMotivationalStatus(mStatus);
	  this.setTypeOfMessage(mType);
	  this.setAssociatedTreatment(t);
	  this.setContent(content);
	  this.setSender(sender);
	  this.setReceiver(receiver);
	  this.setRead(false); //until we confirm that the message has been read, we initialize it to false
  }
  
  public MotivationalMessage(User sender, User receiver, MotivationalMessageClassification contextType, int depth, Treatment t, String mType, MotivationalStatusType mStatus, Questionnaire q, String file_rute){
	  this.setContext(contextType);
	  this.setDepth(depth);
	  this.setMotivationalStatus(mStatus);
	  this.setTypeOfMessage(mType);
	  this.setAssociatedTreatment(t);
	  this.setContent(q);
	  this.setSender(sender);
	  this.setReceiver(receiver);
	  this.setRead(false); //until we confirm that the message has been read, we initialize it to false
  }*/
  /*
  public MotivationalMessage(MotivationalMessageClassification contextType, int depth, MotivationalStatusType mStatus){
	  this.setContext(contextType);
	  this.setDepth(depth);
	  this.setMotivationalStatus(mStatus);
	  
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
      && props.containsKey(PROP_ILLNESS)
      && props.containsKey(PROP_MESSAGE_TYPE)
      && props.containsKey(PROP_MOTIVATIONAL_STATUS)
      && props.containsKey(PROP_TREATMENT_TYPE);
  }

  
  // GETTERS & SETTERS
  public String getMotivationalStatus() {
    return (String)props.get(PROP_MOTIVATIONAL_STATUS);
  }		

  public void setMotivationalStatus(MotivationalStatusType mst) {
    if (mst != null)
      props.put(PROP_MOTIVATIONAL_STATUS, mst);
  }		

  public MotivationalMessageClassification getMMessageType() {
    return (MotivationalMessageClassification)props.get(PROP_MESSAGE_TYPE);
  }		

  public void setMMessageType(MotivationalMessageClassification mmc) {
    if (mmc != null)
      props.put(PROP_MESSAGE_TYPE, mmc);
  }		


  public String getTreatmentType() {
    return (String)props.get(PROP_TREATMENT_TYPE);
  }		

  public void setTreatmentType(String ttype) {
    if (ttype != null)
      props.put(PROP_TREATMENT_TYPE, ttype);
  }	
  
  public String getIllness() {
	    return (String)props.get(PROP_ILLNESS);
	  }		

	  public void setIllness(String illness) {
	    if (illness != null)
	      props.put(PROP_ILLNESS, illness);
	  }	

  //OTHER METHODS
  
}
