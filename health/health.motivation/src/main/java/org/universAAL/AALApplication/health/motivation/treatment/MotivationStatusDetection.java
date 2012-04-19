package org.universAAL.AALApplication.health.motivation.treatment;

import java.util.ArrayList;

import org.universAAL.AALapplication.health.ont.treatment.Treatment;


public class MotivationStatusDetection {

	public static ArrayList <Treatment> treatmentsByAcceptance = new ArrayList<Treatment>();
	public static ArrayList <Treatment> treatmentsByCompletness = new ArrayList<Treatment>();
	
	public static void insertTreatmentByAcceptance(Treatment t){
		treatmentsByAcceptance.add(t);
	}
	
	public static ArrayList <Treatment> getTreatmentsByAcceptance(){
		return treatmentsByAcceptance;
	} 
	
	public static void insertTreatmentByCompletness(Treatment t){
		treatmentsByCompletness.add(t);
		
	}
	
	public static ArrayList <Treatment> getTreatmentsByCompletness(){
		return treatmentsByCompletness;
	} 
	
	
	
}
