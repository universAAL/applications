package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.owl.MotivationalMessage;
import org.universAAL.ontology.owl.MotivationalMessageClassification;
import org.universAAL.ontology.owl.MotivationalMessageSubclassification;
import org.universAAL.ontology.owl.MotivationalPlainMessage;

public class SessionPerformanceNotReceivedActionAlertReminder implements MotivationalMessageContent{

	String content = "Hey there $userName! By now you are supposed to have performed a session for treatment $treatmentName." + 
	" Please, introduce the session performance confirmation in the system if yes or perform your session as soon as possible if not.";

	String name = "Alert message when planned session performance not received";
	
	MotivationalPlainMessage sessionPerformanceConfirmationNotReceivedNotificationToAP = new MotivationalPlainMessage (name, HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.action, MotivationalMessageClassification.reminder, MotivationalMessageSubclassification.session_performance_alert, content);
	
	public Object getMessageContent() {
		return sessionPerformanceConfirmationNotReceivedNotificationToAP.getContent();
	}

	public MotivationalMessage getMotivationalMessage() {
		return sessionPerformanceConfirmationNotReceivedNotificationToAP;
	}
}
