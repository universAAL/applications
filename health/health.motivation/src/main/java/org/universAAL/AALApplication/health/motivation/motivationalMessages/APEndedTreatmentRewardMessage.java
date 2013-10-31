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

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.owl.MotivationalMessage;
import org.universAAL.ontology.owl.MotivationalMessageClassification;
import org.universAAL.ontology.owl.MotivationalMessageSubclassification;
import org.universAAL.ontology.owl.MotivationalPlainMessage;

public class APEndedTreatmentRewardMessage implements MotivationalMessageContent{
	String content = "Congratulations $userName! Today you just finished your treatment, $treatmentName. You should be proud of it, because you have performed all the sessions, contributing to improve your health status." + 
	" Why don't you phone your friends or relatives to share the good news? Have a nice and healthy day!";

	String name = "Message sent to AP when he/she ends the treatment rightly";
	
	MotivationalPlainMessage treatmentFinishedAPRewardMessage = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.reward, MotivationalMessageSubclassification.treatment_status_finished, content);

	public Object getMessageContent() {
		return treatmentFinishedAPRewardMessage.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return treatmentFinishedAPRewardMessage;
	}

}
