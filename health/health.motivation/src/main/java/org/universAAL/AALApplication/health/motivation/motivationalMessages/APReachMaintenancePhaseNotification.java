package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.owl.MotivationalMessage;
import org.universAAL.ontology.owl.MotivationalMessageClassification;
import org.universAAL.ontology.owl.MotivationalMessageSubclassification;
import org.universAAL.ontology.owl.MotivationalPlainMessage;

public class APReachMaintenancePhaseNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName! $userName has performed the 85% of the treament $treatmentName.";

	String name = "Message sent to caregiver when AP reaches the maintenance phase";
	MotivationalPlainMessage treatmentMaintenancePhaseReachedNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.action, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_agreement, content);

	
	public Object getMessageContent() {
		return treatmentMaintenancePhaseReachedNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return treatmentMaintenancePhaseReachedNotificationToCaregiver;
	}

}
