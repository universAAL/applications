package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APBackToPrecontemplationNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName. $userName hasn't performed, in tree attempts, the first session of $userPosesiveGender treament, $treatmentName." + 
	" The platform will try to find out the reason, but may be you prefer to contact $userGender before it to set out the cause of the giving up by yourself. Regards.";

	String name = "Message sent to caregiver when AP goes from contemplation to precontemplation phase";
	MotivationalPlainMessage backToPrecontemplationNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.contemplation, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_disagreement, content);

	public Object getMessageContent() {
		return backToPrecontemplationNotificationToCaregiver.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return backToPrecontemplationNotificationToCaregiver;
	}

}
