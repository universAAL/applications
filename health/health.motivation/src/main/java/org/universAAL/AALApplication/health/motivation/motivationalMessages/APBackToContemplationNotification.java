package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APBackToContemplationNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName. $userName hasn't performed 3 consecutive sessions of $userPosesiveGender treament, $treatmentName." + 
	" The platform will try to find out the reason, but may be you prefer to contact $userGender before it to set out the cause of the giving up by yourself. Regards.";
	
	String name = "Message sent to caregiver when AP goes to contemplation from action phase";

	MotivationalPlainMessage backToContemplationNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.action, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_disagreement, content);

	public Object getMessageContent() {
		return backToContemplationNotificationToCaregiver.getContent(); 
	}

	public MotivationalMessage getMotivationalMessage() {
		return backToContemplationNotificationToCaregiver ;
	}

}
