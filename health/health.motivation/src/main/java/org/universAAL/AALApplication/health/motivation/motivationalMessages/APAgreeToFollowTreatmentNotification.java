package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

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
