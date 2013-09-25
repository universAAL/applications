package org.universAAL.AALApplication.health.motivation.testSupportClasses;

import java.util.ArrayList;

import org.universAAL.ontology.health.owl.Treatment;

/**
 * The purpose of this class is to manage the already detected treatments.  
 * 
 */
public class DetectedTreatments {


	//Detected treatments will be stored in an arraylist
	public static ArrayList <Treatment> detectedTreatments = new ArrayList <Treatment>();

	
	/**
	 * This method inserts treatments in the main arraylist
	 * @param treatment to be stored
	 */
	
	public static void insertDetectedTreatment(Treatment t){
		detectedTreatments.add(t);
	}
	
	/**
	 * This method returns all the treatments stored in the main arraylist
	 * @return all detected treatments.
	 */
	
	public static ArrayList <Treatment> getDetectedTreatments(){
		return detectedTreatments;
	}
	
	
	/**
	 * This method checks whether a treatment has been stored or not
	 * in the main arraylist
	 * @param treatment to be checked in the arraylist
	 */
	public static boolean containsTreatment(Treatment t){
		
		if (detectedTreatments.contains(t))
			return true;
		else
			return false;
	}
	
	/**
	 * This method deletes a specicif treatment from the arraylist
	 * @param t
	 */
	public static void deleteTreatmentFromDetectedTreatments(Treatment t){
		detectedTreatments.remove(t);
	}
	
}
