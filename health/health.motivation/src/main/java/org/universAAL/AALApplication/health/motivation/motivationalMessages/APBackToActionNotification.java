package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APBackToActionNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName. $userName hasn't performed 5 consecutive sessions of $userPosesiveGender treament, $treatmentName." + 
	" The platform will try to encourage $userGender to perform the following ones, but may be you prefer to contact $userGender to find out the reason of the giving up. Regards.";

	String name = "Message sent to caregiver when the AP goes from maintenance to action phase";
	MotivationalPlainMessage backToActionNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_disagreement, content);

	public Object getMessageContent() {
		return backToActionNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return backToActionNotificationToCaregiver;
	}

}
