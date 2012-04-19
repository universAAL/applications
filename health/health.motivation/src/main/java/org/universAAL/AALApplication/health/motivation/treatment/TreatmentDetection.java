package org.universAAL.AALApplication.health.motivation.treatment;

import java.util.ArrayList;

import org.universAAL.AALapplication.health.ont.treatment.Treatment;

public class TreatmentDetection {

	public static ArrayList <Treatment> treatments = new ArrayList<Treatment>();
	
	public static void insertTreatment(Treatment t){
		treatments.add(t);
	}
	
	public static ArrayList <Treatment> getTreatments(){
		return treatments;
	}
	
}
