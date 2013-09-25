package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.owl.MotivationalMessage;
import org.universAAL.ontology.owl.MotivationalMessageClassification;
import org.universAAL.ontology.owl.MotivationalMessageSubclassification;
import org.universAAL.ontology.owl.MotivationalQuestionnaire;
import org.universAAL.ontology.questionnaire.ChoiceLabel;
import org.universAAL.ontology.questionnaire.Questionnaire;
import org.universAAL.ontology.questionnaire.SingleChoiceQuestion;

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


