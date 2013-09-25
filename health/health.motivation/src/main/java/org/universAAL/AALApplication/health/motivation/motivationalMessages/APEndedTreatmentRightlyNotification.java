package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APEndedTreatmentRightlyNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName! $userName has ended wrightly $userPosesiveGender treament, $treatmentName." + 
	" All the sessions were performed and they have been done in the correct time interval. Regards.";

	String name = "Message sent to caregiver when AP ends treatment rightly";
	MotivationalPlainMessage apEndedTreatmentRightlyNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_status_finished, content);

	
	public Object getMessageContent() {
		return apEndedTreatmentRightlyNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return apEndedTreatmentRightlyNotificationToCaregiver;
	}

}
