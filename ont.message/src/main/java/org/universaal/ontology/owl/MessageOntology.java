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

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.profile.ProfileOntology;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.questionnaire.Questionnaire;
import org.universaal.ontology.MessageOntologyFactory;
import org.universaal.ontology.health.owl.MotivationalStatusType;

/**
 * @author AAL Studio
 */
public final class MessageOntology extends Ontology {

    private static MessageOntologyFactory factory = new MessageOntologyFactory();
    public static final String NAMESPACE = "http://ontology.universaal.org/MessageOntology#";

    public MessageOntology() {
	super(NAMESPACE);
    }

    public void create() {
	Resource r = getInfo();
	r
		.setResourceComment("The ontology defining the most general concepts of a message.");
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
		MotivationalMessageClassification.educational,
		MotivationalMessageClassification.reminder,
		MotivationalMessageClassification.reward,
		MotivationalMessageClassification.personalizedFeedback,
		MotivationalMessageClassification.test,
		MotivationalMessageClassification.inquiry,
		MotivationalMessageClassification.notification,
		MotivationalMessageClassification.emotion });

	// load MotivationalMessageSubclassification
	oci = createNewAbstractOntClassInfo(MotivationalMessageSubclassification.MY_URI);
	oci.setResourceComment("");
	oci.setResourceLabel("MotivationalMessageSubclassification");
	oci
		.toEnumeration(new ManagedIndividual[] {
			MotivationalMessageSubclassification.academic,
			MotivationalMessageSubclassification.advice,
			MotivationalMessageSubclassification.benefit,
			MotivationalMessageSubclassification.curiosity,
			MotivationalMessageSubclassification.explanation,
			MotivationalMessageSubclassification.session_recognition,
			MotivationalMessageSubclassification.treatment_performance_recognition,
			MotivationalMessageSubclassification.agreement,
			MotivationalMessageSubclassification.academic_knowledge,
			MotivationalMessageSubclassification.personal_knowledge,
			MotivationalMessageSubclassification.solution,
			MotivationalMessageSubclassification.session_performance_reminder,
			MotivationalMessageSubclassification.session_performance_alert,
			MotivationalMessageSubclassification.treatment_needs,
			MotivationalMessageSubclassification.agreement_pendant,
			MotivationalMessageSubclassification.unusual_measurement,
			MotivationalMessageSubclassification.abandonment,
			MotivationalMessageSubclassification.treatment_management,
			MotivationalMessageSubclassification.treatment_performance_agreement,
			MotivationalMessageSubclassification.session,
			MotivationalMessageSubclassification.evolution,
			MotivationalMessageSubclassification.session_interest,
			MotivationalMessageSubclassification.treatment_interest,
			MotivationalMessageSubclassification.beginer_interest,
			MotivationalMessageSubclassification.familiar_interest,
			MotivationalMessageSubclassification.non_performance_cause,
			MotivationalMessageSubclassification.session_agreement,
			MotivationalMessageSubclassification.treatment_agreement,
			MotivationalMessageSubclassification.agreement_proposal,
			MotivationalMessageSubclassification.session_performance,
			MotivationalMessageSubclassification.vs_fear,
			MotivationalMessageSubclassification.vs_despair,
			MotivationalMessageSubclassification.vs_frustration,
			MotivationalMessageSubclassification.vs_lack_of_hability,
			MotivationalMessageSubclassification.treatment_performance_disagreement,
			MotivationalMessageSubclassification.treatment_status_cancelled,
			MotivationalMessageSubclassification.treatment_status_finished,
			MotivationalMessageSubclassification.treatment_status_prolonged });

	// ******* Regular classes of the ontology ******* //

	// load Message
	oci = createNewOntClassInfo(Message.MY_URI, factory, 1);
	oci.setResourceComment("");
	oci.setResourceLabel("Message");
	oci.addSuperClass(ManagedIndividual.MY_URI);

	oci.addObjectProperty(Message.PROP_SENDER).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Message.PROP_SENDER,
			User.MY_URI, 1, 1));

	oci.addObjectProperty(Message.PROP_CONTENT).setFunctional();
	oci.addRestriction(MergedRestriction.getCardinalityRestriction(
		Message.PROP_CONTENT, 1, 1));

	oci.addDatatypeProperty(Message.PROP_READ);
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Message.PROP_READ,
			TypeMapper.getDatatypeURI(Boolean.class), 1, 1));

	oci.addObjectProperty(Message.PROP_RECEIVER).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Message.PROP_RECEIVER,
			User.MY_URI, 1, 1));

	oci.addObjectProperty(Message.PROP_SENT_DATE).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Message.PROP_SENT_DATE,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			1, 1));

	// load MotivationalMessage
	oci = createNewOntClassInfo(MotivationalMessage.MY_URI, factory, 3);
	oci.setResourceComment("");
	oci.setResourceLabel("MotivationalMessage");
	oci.addSuperClass(Message.MY_URI);

	oci.addObjectProperty(MotivationalMessage.PROP_DISEASE);
	// oci.addRestriction(MergedRestriction.getCardinalityRestriction(MotivationalMessage.PROP_ASSOCIATED_TREATMENT,
	// 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			MotivationalMessage.PROP_DISEASE, TypeMapper
				.getDatatypeURI(String.class), 1, 1));

	oci.addDatatypeProperty(MotivationalMessage.PROP_MESSAGE_TYPE);
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			MotivationalMessage.PROP_MESSAGE_TYPE,
			MotivationalMessageClassification.MY_URI, 1, 1));

	oci.addDatatypeProperty(MotivationalMessage.PROP_MESSAGE_SUBTYPE);
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			MotivationalMessage.PROP_MESSAGE_SUBTYPE,
			MotivationalMessageSubclassification.MY_URI, 1, 1));

	oci.addDatatypeProperty(MotivationalMessage.PROP_TREATMENT_TYPE);
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			MotivationalMessage.PROP_TREATMENT_TYPE, TypeMapper
				.getDatatypeURI(String.class), 1, 1));

	oci.addDatatypeProperty(MotivationalMessage.PROP_MOTIVATIONAL_STATUS);
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			MotivationalMessage.PROP_MOTIVATIONAL_STATUS,
			MotivationalStatusType.MY_URI, 1, 1));

	oci.addDatatypeProperty(MotivationalMessage.PROP_NAME);
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			MotivationalMessage.PROP_NAME, TypeMapper
				.getDatatypeURI(String.class), 0, 1));

	// load MotivationalQuestionnaire
	oci = createNewOntClassInfo(MotivationalQuestionnaire.MY_URI, factory,
		0);
	oci.setResourceComment("");
	oci.setResourceLabel("MotivationalQuestionnaire");
	oci.addSuperClass(MotivationalMessage.MY_URI);

	oci.addDatatypeProperty(MotivationalQuestionnaire.PROP_CONTENT)
		.addSuperProperty(MotivationalMessage.PROP_CONTENT);
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			MotivationalQuestionnaire.PROP_CONTENT,
			Questionnaire.MY_URI, 1, 1)); // un mensaje de
						      // cuestionario tiene como
						      // contenido un
						      // questionnaire
	/*
	 * oci.addObjectProperty(MotivationalQuestionnaire.PROP_HAS_QUESTIONNAIRE
	 * ).setFunctional(); oci.addRestriction(MergedRestriction
	 * .getAllValuesRestrictionWithCardinality
	 * (MotivationalQuestionnaire.PROP_HAS_QUESTIONNAIRE,
	 * Questionnaire.MY_URI, 1, 1));
	 */

	// load MotivationalPlainMessage
	oci = createNewOntClassInfo(MotivationalPlainMessage.MY_URI, factory, 2);
	oci.setResourceComment("");
	oci.setResourceLabel("MotivationalPlainMessage");
	oci.addSuperClass(MotivationalMessage.MY_URI);

	oci.addDatatypeProperty(MotivationalPlainMessage.PROP_CONTENT)
		.addSuperProperty(MotivationalMessage.PROP_CONTENT);
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			MotivationalPlainMessage.PROP_CONTENT, TypeMapper
				.getDatatypeURI(String.class), 1, 1)); // un
								       // mensaje
								       // plano
								       // tiene
								       // como
								       // contenido
								       // un
								       // String

    }
}
