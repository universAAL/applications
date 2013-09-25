package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalPlainMessage;

public class APEndedTreatmentRewardMessage implements MotivationalMessageContent{
	String content = "Congratulations $userName! Today you just finished your treatment, $treatmentName. You should be proud of it, because you have performed all the sessions, contributing to improve your health status." + 
	" Why don't you phone your friends or relatives to share the good news? Have a nice and healthy day!";

	String name = "Message sent to AP when he/she ends the treatment rightly";
	
	MotivationalPlainMessage treatmentFinishedAPRewardMessage = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.reward, MotivationalMessageSubclassification.treatment_status_finished, content);

	public Object getMessageContent() {
		return treatmentFinishedAPRewardMessage.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return treatmentFinishedAPRewardMessage;
	}

}
