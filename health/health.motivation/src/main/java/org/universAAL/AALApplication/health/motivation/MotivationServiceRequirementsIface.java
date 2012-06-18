package org.universAAL.AALApplication.health.motivation;

import java.util.Locale;

import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.health.HealthProfile;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;

public interface MotivationServiceRequirementsIface {
	
	public String getAssistedPersonName();
	public String getCaregiverName();
	public HealthProfile getHealthProfile(User u);
	public String getPartOfDay();
	
	

}
