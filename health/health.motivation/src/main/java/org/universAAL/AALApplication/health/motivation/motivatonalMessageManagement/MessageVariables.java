/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 Universidad Politecnica de Madrid
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

package org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement;


import java.util.Map;
import java.util.TreeMap;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;

/**
 * This class manages all the issues related to the motivational message variables.
 */

public class MessageVariables {
	
	public static Map<String, String> mapOfVariables = new TreeMap<String, String>();
	
	public static MotivationServiceRequirementsIface requirements;
	
	public static void setMotivationServiceRequirementsIface(MotivationServiceRequirementsIface iface){
		requirements = iface;
	}
	
	/**
	 * This method builds the initial map of variables, from which
	 * the variables will be substituted. The value of these variables
	 * should be provided by the platform.
	 */
	public static void buildInitialMapOfVariables(){
		
		String username = requirements.getAssistedPersonName();
		mapOfVariables.put("userName", username);
		
		String caregivername = requirements.getCaregiverName(requirements.getAssistedPerson());
		mapOfVariables.put("caregiverName", caregivername);
		
		String partOfDay = requirements.getPartOfDay();
		mapOfVariables.put("partOfDay", partOfDay);
		
		String apGenderArticle = requirements.getAPGenderArticle();
		mapOfVariables.put("userGender", apGenderArticle);
		
		String apPosesiveGenderArticle = requirements.getAPPosesiveGenderArticle();
		mapOfVariables.put("userPosesiveGender", apPosesiveGenderArticle);
		
		String caregiverGenderArticle = requirements.getCaregiverGenderArticle();
		mapOfVariables.put("caregiverGender", caregiverGenderArticle);
		
		String caregiverPosesiveGenderArticle = requirements.getCaregiverPosesiveGenderArticle();
		mapOfVariables.put("caregiverPosesiveGender", caregiverPosesiveGenderArticle);
	}
	
	/**
	 * This method add new variables to the map of variables.
	 * @param key (variable)
	 * @param value
	 */
	public static void addToMapOfVariables(String key, String value){
		mapOfVariables.put(key, value);
	}
	
	/**
	 * This method substitutes the variable for its value.
	 * @param the set of variables to be substituted
	 * @return the set of values associated to the variables.
	 */
	public static String[] replaceVariables (String[] variable){
		String[] values = new String[variable.length];
		for (int i=0;i<variable.length;i++){
			if (mapOfVariables.containsKey(variable[i])){
				values[i] = (String) mapOfVariables.get(variable[i]);
			}
			else
				values[i] = null;
		}
		return values;
	}
	
}
