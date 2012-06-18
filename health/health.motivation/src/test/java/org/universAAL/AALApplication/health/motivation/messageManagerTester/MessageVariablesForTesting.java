package org.universAAL.AALApplication.health.motivation.messageManagerTester;

import org.apache.commons.collections.map.MultiKeyMap;

public class MessageVariablesForTesting {

	public MultiKeyMap mapForVariables = new MultiKeyMap();
	
	public void fillVariables(){
		
		mapForVariables.put("$username", "Susan");
		mapForVariables.put("$partOfDay", "morning");
		mapForVariables.put("$userPetName", "Bobby");
		mapForVariables.put("$veterinarianDate", "16 h");
		mapForVariables.put("$veterinarianPlace", "City Mall");
		
	}
	
	
}
