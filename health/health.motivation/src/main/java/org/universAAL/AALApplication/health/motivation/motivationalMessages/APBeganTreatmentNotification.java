package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APBeganTreatmentNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName! $userName has begun $userPosesiveGender treatment: $treatmentName." + 
	" The platform will notify you with new updates, whenever they happen. Regards.";

	MotivationalPlainMessage treatmentFirstSessionPerformanceNotificationToCaregiver = new MotivationalPlainMessage (HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.contemplation, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_agreement, content);

	public Object getMessageContent() {
		return treatmentFirstSessionPerformanceNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return treatmentFirstSessionPerformanceNotificationToCaregiver;
	}

}
