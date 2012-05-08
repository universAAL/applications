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

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.MessageOntologyFactory;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;


/**
 * @author AAL Studio 
 */
public final class MessageOntology extends Ontology {

  private static MessageOntologyFactory factory = new MessageOntologyFactory();
  public static final String NAMESPACE ="http://ontology.universaal.org/MessageOntology#";
	
  public MessageOntology() {
    super(NAMESPACE);
  }

  public void create() {
	  Resource r = getInfo();
	    r.setResourceComment("The ontology defining the most general concepts of a message.");
	    r.setResourceLabel("Message");
	    addImport(DataRepOntology.NAMESPACE);
	    addImport(PhThingOntology.NAMESPACE);
		addImport(ServiceBusOntology.NAMESPACE);
	    addImport(ProfileOntology.NAMESPACE);
		
    OntClassInfoSetup oci;


    // ******* Enumeration classes of the ontology ******* //

    // load MotivationalMessageClassification
    oci = createNewAbstractOntClassInfo(MotivationalMessageClassification.MY_URI);
    oci.setResourceComment("");
    oci.setResourceLabel("MotivationalMessageClassification");
    oci.toEnumeration(new ManagedIndividual[] {
       MotivationalMessageClassification.educational, MotivationalMessageClassification.reminder, MotivationalMessageClassification.reward, MotivationalMessageClassification.personalizedFeedback, MotivationalMessageClassification.test, MotivationalMessageClassification.inquiry, MotivationalMessageClassification.notification });

    //load TreatmentTypeClassification
    oci = createNewAbstractOntClassInfo(TreatmentTypeClassification.MY_URI);
    oci.setResourceComment("");
    oci.setResourceLabel("TreatmentTypeClassification");
    oci.toEnumeration(new ManagedIndividual[] {
    		TreatmentTypeClassification.all, TreatmentTypeClassification.takeMeasurement, TreatmentTypeClassification.physicalActivity, TreatmentTypeClassification.diet });

    // ******* Regular classes of the ontology ******* //

  //load Message 
    oci = createNewOntClassInfo(Message.MY_URI, factory, 1);
    oci.setResourceComment("");
    oci.setResourceLabel("Message");
    oci.addSuperClass(ManagedIndividual.MY_URI); 
    
    oci.addObjectProperty(Message.PROP_SENDER).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Message.PROP_SENDER, 
     User.MY_URI, 1, 1));
    
    oci.addObjectProperty(Message.PROP_CONTENT).setFunctional();
    oci.addRestriction(MergedRestriction.getCardinalityRestriction(Message.PROP_CONTENT, 1, 1)); 
    
    oci.addDatatypeProperty(Message.PROP_READ);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Message.PROP_READ, 
      TypeMapper.getDatatypeURI(Boolean.class), 1, 1));
    
    oci.addObjectProperty(Message.PROP_RECEIVER).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Message.PROP_RECEIVER, 
     User.MY_URI, 1, 1));
    
    //load MotivationalMessage 
    oci = createNewOntClassInfo(MotivationalMessage.MY_URI, factory, 3);
    oci.setResourceComment("");
    oci.setResourceLabel("MotivationalMessage");
    oci.addSuperClass(Message.MY_URI); 
    
    oci.addObjectProperty(MotivationalMessage.PROP_ILLNESS);
//    oci.addRestriction(MergedRestriction.getCardinalityRestriction(MotivationalMessage.PROP_ASSOCIATED_TREATMENT, 1, 1));
    oci.addRestriction(MergedRestriction
    	      .getAllValuesRestrictionWithCardinality(MotivationalMessage.PROP_ILLNESS, 
    	    		  TypeMapper.getDatatypeURI(String.class), 1, 1));
    	      
    oci.addDatatypeProperty(MotivationalMessage.PROP_MESSAGE_TYPE);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(MotivationalMessage.PROP_MESSAGE_TYPE, 
      MotivationalMessageClassification.MY_URI, 1, 1));
    
    oci.addDatatypeProperty(MotivationalMessage.PROP_TREATMENT_TYPE);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(MotivationalMessage.PROP_TREATMENT_TYPE, 
    				TreatmentTypeClassification.MY_URI, 1, 1));
    
    oci.addDatatypeProperty(MotivationalMessage.PROP_MOTIVATIONAL_STATUS);
    oci.addRestriction(MergedRestriction
  	      .getAllValuesRestrictionWithCardinality(MotivationalMessage.PROP_MOTIVATIONAL_STATUS, 
  	    		  MotivationalStatusType.MY_URI, 1, 1));
    
    //load MotivationalQuestionnaire 
    oci = createNewOntClassInfo(MotivationalQuestionnaire.MY_URI, factory, 0);
    oci.setResourceComment("");
    oci.setResourceLabel("MotivationalQuestionnaire");
    oci.addSuperClass(MotivationalMessage.MY_URI); 
    oci.addObjectProperty(MotivationalQuestionnaire.PROP_HAS_QUESTIONNAIRE).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(MotivationalQuestionnaire.PROP_HAS_QUESTIONNAIRE, 
      Questionnaire.MY_URI, 1, 1));

    

    //load MotivationalPlainMessage 
    oci = createNewOntClassInfo(MotivationalPlainMessage.MY_URI, factory, 2);
    oci.setResourceComment("");
    oci.setResourceLabel("MotivationalPlainMessage");
    oci.addSuperClass(MotivationalMessage.MY_URI); 


    
  }
}
