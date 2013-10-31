/*******************************************************************************
 * Copyright 2013 Universidad Polit�cnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
