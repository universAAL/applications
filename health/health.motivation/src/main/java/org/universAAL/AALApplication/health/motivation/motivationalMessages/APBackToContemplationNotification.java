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

public class APBackToContemplationNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName. $userName hasn't performed 3 consecutive sessions of $userPosesiveGender treament, $treatmentName." + 
	" The platform will try to find out the reason, but may be you prefer to contact $userGender before it to set out the cause of the giving up by yourself. Regards.";
	
	String name = "Message sent to caregiver when AP goes to contemplation from action phase";

	MotivationalPlainMessage backToContemplationNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.action, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_disagreement, content);

	public Object getMessageContent() {
		return backToContemplationNotificationToCaregiver.getContent(); 
	}

	public MotivationalMessage getMotivationalMessage() {
		return backToContemplationNotificationToCaregiver ;
	}

}
