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

public class APAgreeToFollowTreatmentNotification implements MotivationalMessageContent{

	// Crear un mensaje de notificaci�n para el caregiver en el que se le notifique de que
	// el assisted person va a seguir un tratamiento.
	
	String content = "Good $partOfDay $caregiverName! $userName has agreeded to follow the treatment $treatmentName."+
			" You'll be notified whether the first session will be performed or not by $userName.";

	String name = "Message sent to caregiver when AP agrees to follow the treatment";
	MotivationalPlainMessage treatmentPerformanceAgreementNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_agreement, content);
	
	public Object getMessageContent() {
		return treatmentPerformanceAgreementNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return treatmentPerformanceAgreementNotificationToCaregiver;
	}
}
