package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APEndedTreatmentWrightlyNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName! $userName has ended wrightly $userPosesiveGender treament, $treatmentName." + 
	" All the sessions were performed and they have been done in the correct time interval. Regards.";

	MotivationalPlainMessage apEndedTreatmentRightlyNotificationToCaregiver = new MotivationalPlainMessage (HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_status_finished, content);

	
	public Object getMessageContent() {
		return apEndedTreatmentRightlyNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return apEndedTreatmentRightlyNotificationToCaregiver;
	}

}
