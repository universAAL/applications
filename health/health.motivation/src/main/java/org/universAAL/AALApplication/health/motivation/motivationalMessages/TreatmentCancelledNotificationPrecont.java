package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class TreatmentCancelledNotificationPrecont implements MotivationalMessageContent{
	
	String content = "$userName, your caregiver, $caregiverName, has cancelled your treatment $treatmentName."+
	" You can get in touch with $caregiverGender to know the reasons or for further information. Regards.";
	String name ="Message sent to AP when her/his caregiver cancells her/his treatment";
	MotivationalPlainMessage treatmentCancellationNotificationToAP = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_status_cancelled, content);

public Object getMessageContent() {
return treatmentCancellationNotificationToAP.getContent();
}

public MotivationalMessage getMotivationalMessage() {
return treatmentCancellationNotificationToAP;
}
}
