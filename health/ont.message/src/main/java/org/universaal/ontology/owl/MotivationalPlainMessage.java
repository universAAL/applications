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
//NAMESPACE & PROPERTIES
public class MotivationalPlainMessage extends MotivationalMessage {
  public static final String MY_URI = MessageOntology.NAMESPACE
    + "MotivationalTextMessage";

//CONSTRUCTORS
  public MotivationalPlainMessage () {
    super();
  }
  
  public MotivationalPlainMessage (String uri) {
    super(uri);
  }
  /*
  public MotivationalPlainMessage(User sender, User receiver, MotivationalMessageClassification contextType, int depth, Treatment t, String mType, MotivationalStatusType mStatus, Object content, String file_rute){
	  super(sender, receiver, contextType, depth, t, mType, mStatus, content, file_rute);
	  //this.setTypeOfMessage("PlainMessage");
  }
  */
  
  public MotivationalPlainMessage(String illness, String ttype, MotivationalStatusType motStatus, MotivationalMessageClassification mtype, MotivationalMessageSubclassification msubtype, String content){
	  super(illness, ttype, motStatus, mtype, msubtype, content);
  }
  
  public String getClassURI() {
    return MY_URI;
  }
  
  public int getPropSerializationType(String arg0) {
	  return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true;
  }
}
