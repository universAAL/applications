package org.universAAL.AALApplication.health.motivation.testSupportClasses;

import java.util.ArrayList;

import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;

public class RulesSupport {


	public static ArrayList <Treatment> detectedTreatments = new ArrayList <Treatment>();

	public static void insertDetectedTreatment(Treatment t){
		detectedTreatments.add(t);
	}
	
	public static ArrayList <Treatment> getDetectedTreatments(){
		return detectedTreatments;
	}
}
