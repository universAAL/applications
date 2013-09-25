package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.owl.MotivationalMessage;
import org.universAAL.ontology.owl.MotivationalMessageClassification;
import org.universAAL.ontology.owl.MotivationalMessageSubclassification;
import org.universAAL.ontology.owl.MotivationalPlainMessage;

public class APBeganTreatmentNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName! $userName has begun $userPosesiveGender treatment: $treatmentName." + 
	" The platform will notify you with new updates, whenever they happen. Regards.";

	String name = "Meessage sent to caregiver when user performs the first session of her/his treatment";
	MotivationalPlainMessage treatmentFirstSessionPerformanceNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.contemplation, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_agreement, content);

	public Object getMessageContent() {
		return treatmentFirstSessionPerformanceNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return treatmentFirstSessionPerformanceNotificationToCaregiver;
	}

}
