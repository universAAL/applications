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


import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.profile.User;

//NAMESPACE & PROPERTIES
public class Message extends ManagedIndividual {
  public static final String MY_URI = MessageOntology.NAMESPACE
    + "Message";
  public static final String PROP_SENDER = MessageOntology.NAMESPACE
    + "sender";
  public static final String PROP_CONTENT = MessageOntology.NAMESPACE
    + "content";
  public static final String PROP_READ = MessageOntology.NAMESPACE
    + "read";
  public static final String PROP_RECEIVER = MessageOntology.NAMESPACE
    + "receiver";

//CONSTRUCTORS
  public Message () {
    super();
  }
  
  public Message (String uri) {
    super(uri);
  }

  public Message (User sender, User receiver, Object content, boolean isRead){
	  
  }
  
  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	  return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true 
      && props.containsKey(PROP_SENDER)
      && props.containsKey(PROP_CONTENT)
      && props.containsKey(PROP_READ)
      && props.containsKey(PROP_RECEIVER);
  }

  //GETTERS & SETTERS
  public User getSender() {
    return (User)props.get(PROP_SENDER);
  }		

  public void setSender(User sender) {
    if (sender != null)
      props.put(PROP_SENDER, sender);
  }		

  public User getReceiver() {
    return (User)props.get(PROP_RECEIVER);
  }		

  public void setReceiver(User receiver) {
    if (receiver != null)
      props.put(PROP_RECEIVER, receiver);
  }		

  public Object getContent() {
    return (Object)props.get(PROP_CONTENT);
  }		

  public void setContent(Object content) {
    if (content != null)
      props.put(PROP_CONTENT, content);
  }		

  public boolean isRead() {
	Boolean b = (Boolean) props.get(PROP_READ);
	return (b == null) ? false : b.booleanValue();
  }		

  public void setRead(boolean isRead) {
      props.put(PROP_READ, new Boolean(isRead));
  }		
}
