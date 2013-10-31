/*******************************************************************************
 * Copyright 2013 Universidad Polit�cnica de Madrid
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


