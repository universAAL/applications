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

public class MotivationalQuestionnaire extends MotivationalMessage {

//NAMESPACE & PROPERTIES
  public static final String MY_URI = MessageOntology.NAMESPACE
    + "MotivationalQuestionnaire";
  /*public static final String PROP_HAS_QUESTIONNAIRE = MessageOntology.NAMESPACE
    + "hasQuestionnaire";
*/
//CONSTRUCTORS
  public MotivationalQuestionnaire () {
    super();
  }
  
  public MotivationalQuestionnaire (String uri) {
    super(uri);
  }
  
  public MotivationalQuestionnaire(String illness, String treatmentType, MotivationalStatusType motStatus, Object content){
	  
  }
  
  /*
  public MotivationalQuestionnaire(User sender, User receiver, Questionnaire q, MotivationalStatusType ms, MotivationalMessageClassification mc,String mType, 
		  int depth, Treatment t, String file_rute, Object content){
	 // this.setTypeOfMessage("Questionnaire");
	  super(sender, receiver, mc, depth, t, mType, ms, content,file_rute);
	  
	  
	  this.setHasQuestionnaire(q);
	  //this.setContent(q.getWrittenQuestionnaire(q));
  }
  */
  
  public MotivationalQuestionnaire(String illness, String ttype, MotivationalStatusType motStatus, MotivationalMessageClassification mtype, Questionnaire questionnaire){
	  super(illness, ttype, motStatus, mtype, questionnaire);
	  //this.setHasQuestionnaire(questionnaire);
	  this.setContent(questionnaire);
  }
  
  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	  return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true 
      //&& props.containsKey(PROP_HAS_QUESTIONNAIRE)
	;
  }

  		

//GETTERS & SETTERS
  
  /*
  public void setHasQuestionnaire(Questionnaire questionnaire) {
    if (questionnaire != null)
      props.put(PROP_HAS_QUESTIONNAIRE, questionnaire);
  }		

  public Questionnaire getHasQuestionnaire() {
	    return (Questionnaire)props.get(PROP_HAS_QUESTIONNAIRE);
  }
*/
}
