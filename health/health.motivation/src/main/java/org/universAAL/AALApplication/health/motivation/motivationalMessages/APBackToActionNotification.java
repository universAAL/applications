package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APBackToActionNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName. $userName hasn't performed 5 consecutive sessions of $userPosesiveGender treament, $treatmentName." + 
	" The platform will try to encourage $userGender to perform the following ones, but may be you prefer to contact $userGender to find out the reason of the giving up. Regards.";

	MotivationalPlainMessage backToActionNotificationToCaregiver = new MotivationalPlainMessage (HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_disagreement, content);

	public Object getMessageContent() {
		return backToActionNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return backToActionNotificationToCaregiver;
	}

}
