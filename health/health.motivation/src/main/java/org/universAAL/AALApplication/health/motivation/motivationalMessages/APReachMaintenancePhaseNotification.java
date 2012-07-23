package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APReachMaintenancePhaseNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName! $userName has performed the 85% of the treament $treatmentName.";

	MotivationalPlainMessage treatmentMaintenancePhaseReachedNotificationToCaregiver = new MotivationalPlainMessage (HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.action, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_agreement, content);

	
	public Object getMessageContent() {
		return treatmentMaintenancePhaseReachedNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return treatmentMaintenancePhaseReachedNotificationToCaregiver;
	}

}
