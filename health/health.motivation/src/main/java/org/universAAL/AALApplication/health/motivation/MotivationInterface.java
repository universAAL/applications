
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
 package org.universAAL.AALApplication.health.motivation;

import java.util.Locale;

import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.owl.MotivationalMessage;
import org.universAAL.ontology.owl.MotivationalMessageClassification;
import org.universAAL.ontology.profile.User;

public interface MotivationInterface {
	
	//Users needed
	
	public User getAssistedPerson();
	public User getCaregiver(User assistedPerson);
	public HealthProfile getHealthProfile(User u);
	
	
	public void registerClassesNeeded();
	
	//Message context needed
	//public String getMessageDatabaseRoute(Locale messageDatabaseLanguage);
	//public String getMessageVariablesRoute(Locale variablesDatabaseLanguage);
	//public String setMessageDatabaseRoute(String messageDatabaseRoute);
	//public String setMessageVariablesRoute(String variablesDatabaseRoute);
	//public String sendMotivationalMessageToUser(MotivationalMessage mm);
	//In the real context the message should be sent to the user's inbox
	//public void sendMessageToCaregiver(String illness, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType);
	//public void sendMessageToAssistedPerson(String illness, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType);

}
