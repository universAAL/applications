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

import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.messageManagement.MessageManager;



public abstract class MotivationalMessage extends Message {
  
//NAMESPACE & PROPERTIES	
  public static final String MY_URI = MessageOntology.NAMESPACE
    + "MotivationalMessage";
  public static final String PROP_ASSOCIATED_TREATMENT = MessageOntology.NAMESPACE
    + "associatedTreatment";
  public static final String PROP_CONTEXT = MessageOntology.NAMESPACE
    + "context";
  public static final String PROP_TYPE_OF_MESSAGE = MessageOntology.NAMESPACE
    + "typeOfMessage";
  public static final String PROP_MOTIVATIONAL_STATUS = MessageOntology.NAMESPACE
    + "motivationalStatus";
  public static final String PROP_DEPTH = MessageOntology.NAMESPACE
    + "depth";

//CONSTRUCTORS
  
  public MotivationalMessage () {
    super();
  }
  
  public MotivationalMessage (String uri) {
    super(uri);
  }
  
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
	  MessageManager.storeMotivationalMessage(this,file_rute); 
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
	  MessageManager.storeMotivationalMessage(this,file_rute); 
  }
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
      && props.containsKey(PROP_ASSOCIATED_TREATMENT)
      && props.containsKey(PROP_CONTEXT)
      && props.containsKey(PROP_TYPE_OF_MESSAGE)
      && props.containsKey(PROP_MOTIVATIONAL_STATUS)
      && props.containsKey(PROP_DEPTH);
  }

  
  // GETTERS & SETTERS
  public String getMotivationalStatus() {
    return (String)props.get(PROP_MOTIVATIONAL_STATUS);
  }		

  public void setMotivationalStatus(MotivationalStatusType mst) {
    if (mst != null)
      props.put(PROP_MOTIVATIONAL_STATUS, mst);
  }		

  public MotivationalMessageClassification getContext() {
    return (MotivationalMessageClassification)props.get(PROP_CONTEXT);
  }		

  public void setContext(MotivationalMessageClassification mmc) {
    if (mmc != null)
      props.put(PROP_CONTEXT, mmc);
  }		

  public int getDepth() {
	Integer i = (Integer) props.get(PROP_DEPTH);
	return (i == null) ? 0 : i.intValue();
  }		

  public void setDepth(int depth) {
      props.put(PROP_DEPTH, new Integer(depth));
  }		

  public Treatment getAssociatedTreatment() {
    return (Treatment)props.get(PROP_ASSOCIATED_TREATMENT);
  }		

  public void setAssociatedTreatment(Treatment treatment) {
    if (treatment != null)
      props.put(PROP_ASSOCIATED_TREATMENT, treatment);
  }		

  public String getTypeOfMessage() {
    return (String)props.get(PROP_TYPE_OF_MESSAGE);
  }		

  public void setTypeOfMessage(String type) {
    if (type != null)
      props.put(PROP_TYPE_OF_MESSAGE, type);
  }	
  
  //OTHER METHODS
  
}
