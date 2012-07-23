package org.universAAL.AALApplication.health.motivation.testSupportClasses;

import java.util.ArrayList;

import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;

public class DetectedTreatments {


	public static ArrayList <Treatment> detectedTreatments = new ArrayList <Treatment>();

	
	/**
	 * Getters y setters de tratamientos detectados y sesiones realizadas
	 */
	
	public static void insertDetectedTreatment(Treatment t){
		detectedTreatments.add(t);
	}
	
	public static ArrayList <Treatment> getDetectedTreatments(){
		return detectedTreatments;
	}
	
	public static boolean containsTreatment(Treatment t){
		
		for (int i=0;i<detectedTreatments.size();i++){

			Treatment treatment = detectedTreatments.get(i);

			if(treatment.getName().equals(t.getName()))
					return true;
		}
		return false;
	}
	
}
