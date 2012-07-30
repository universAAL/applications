package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import java.util.Locale;

import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.ontology.space.SpaceOntology;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.disease.owl.DiseaseOntology;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalQuestionnaire;
import org.universAAL.ontology.questionnaire.*;

public class TreatmentDetectionMessage extends MotivationalQuestionnaire implements MotivationalMessageContent{
	
	private String qWording = "Good $partOfDay $userName! A new treatment, named $treatmentName has been asigned to you. This treatment consists of $treatmentDescription. Do you plan to follow it?";

	private ChoiceLabel choice1 = new ChoiceLabel(Boolean.TRUE, "Yes");
	private ChoiceLabel choice2 = new ChoiceLabel(Boolean.FALSE, "No");
	private ChoiceLabel[] booleanChoices = {choice1,choice2};

	private SingleChoiceQuestion question = new SingleChoiceQuestion(qWording,TypeMapper.getDatatypeURI(Boolean.class),booleanChoices);
	private Questionnaire questionnaire = new org.universAAL.ontology.questionnaire.Questionnaire("First inquiry", 
			"This message will be displayed whenever a new treatment is detected, to ask the user" + 
			" about his/her predisposition to follow the treatment.", question); 

	private MotivationalQuestionnaire followTreatmentAgreementInquiry = new MotivationalQuestionnaire(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, MotivationalMessageSubclassification.treatment_agreement, questionnaire);

	public Object getMessageContent() {
		return followTreatmentAgreementInquiry.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return followTreatmentAgreementInquiry;
	}
}


