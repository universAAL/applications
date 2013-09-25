package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APDisagreeToFollowTreatmentNotification implements MotivationalMessageContent{

	String content = "Good $partOfDay $caregiverName. $userName has disagreeded to follow the treatment $treatmentName."+
	" The system will try to find out the reason why $userName refuses to perform the treatment and will try to convince $userGender to change this attitude." + 
	" If any updates they will be sent to you.";

	String name ="Message sent to caregiver when AP disagrees to follow her/his treatment";
	
	MotivationalPlainMessage treatmentPerformanceDisagreementNotificationToCaregiver = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_disagreement, content);

public Object getMessageContent() {
return treatmentPerformanceDisagreementNotificationToCaregiver.getContent();
}

public MotivationalMessage getMotivationalMessage() {
	return treatmentPerformanceDisagreementNotificationToCaregiver;
}
}
