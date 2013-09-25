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
import org.universAAL.ontology.questionnaire.ConditionalQuestion;
import org.universAAL.ontology.questionnaire.Question;
import org.universAAL.ontology.questionnaire.Questionnaire;
import org.universAAL.ontology.questionnaire.SingleChoiceQuestion;

public class TreatmentExtensionInquiryToCaregiver implements MotivationalMessageContent{

	private String qWording1 = "Good $partOfDay $caregiverName. $userName hasn't performed all the sessions of $userPosesiveGender treatment, $treatmentName and the end date of the treatment has passed." + 
	" The current completeness of the treatment is $treatmentCompleteness. Do you want to prolong the treatment until all the planned sessions are performed?";

	private String qWording2 = "Please, introduce a new ending date for the treatment.";

	private ChoiceLabel choice1 = new ChoiceLabel(Boolean.TRUE, "Yes");
	private ChoiceLabel choice2 = new ChoiceLabel(Boolean.FALSE, "No");
	private ChoiceLabel[] booleanChoices = {choice1,choice2};
	
	private SingleChoiceQuestion extensionInquiry = new SingleChoiceQuestion(qWording1,TypeMapper.getDatatypeURI(Boolean.class),booleanChoices);

	private ConditionalQuestion introduceDate = new ConditionalQuestion(qWording2, Boolean.TRUE, extensionInquiry, TypeMapper.getDatatypeURI(String.class));
	
	private Questionnaire questionnaire = new Questionnaire ("Pronlong treatment in time inquiry", 
			"This inquiry asks the caregiver if a treatment should be prolong in time."); 

	Question[] questions = {extensionInquiry,introduceDate};
	private MotivationalQuestionnaire prolongEndDateForIncompleteTreatment;
	
	private void createQuestionnaire(){
		
		questionnaire.setQuestions(questions);
		prolongEndDateForIncompleteTreatment = new MotivationalQuestionnaire(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.inquiry, MotivationalMessageSubclassification.treatment_status_prolonged, questionnaire);
	}

	public Object getMessageContent() {
		createQuestionnaire();
		return prolongEndDateForIncompleteTreatment.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		createQuestionnaire();
		return prolongEndDateForIncompleteTreatment;
	}

}
