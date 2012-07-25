package org.universAAL.AALApplication.health.motivation;

import java.util.Locale;

import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;

public class Motivation implements MotivationInterface{

	public String getMessageDatabaseRoute(Locale messageDatabaseLanguage) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMessageVariablesRoute(Locale variablesDatabaseLanguage) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerClassesNeeded() {
		// TODO Auto-generated method stub
		
	}

	public static void sendMessageToAssistedPerson(String illness,
			String treatmentType, MotivationalStatusType motStatus,
			MotivationalMessageClassification messageType) {
		// TODO Auto-generated method stub
		
	}

	public static void sendMessageToCaregiver(String illness, String treatmentType,
			MotivationalStatusType motStatus,
			MotivationalMessageClassification messageType) {
		// TODO Auto-generated method stub
		
	}


	public String setMessageDatabaseRoute(String messageDatabaseRoute) {
		// TODO Auto-generated method stub
		return null;
	}

	public String setMessageVariablesRoute(String variablesDatabaseRoute) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getAssistedPerson() {
		// TODO Auto-generated method stub
		return null;
	}

	public User getCaregiver(User assistedPerson) {
		// TODO Auto-generated method stub
		return null;
	}

	public HealthProfile getHealthProfile(User u) {
		// TODO Auto-generated method stub
		return null;
	}


}
