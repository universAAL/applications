package org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement;

import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessageClassification;

public class PruebaDeReflection {

	public static void main (String args[]){
		MessageManager.sendMessageToUser(HeartFailure.MY_URI, Treatment.MY_URI,
                MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry);
	}


}
