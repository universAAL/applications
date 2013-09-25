package org.universAAL.AALApplication.health.motivation;

import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.profile.User;

public interface MotivationServiceRequirementsIface {
	
	public String getAssistedPersonName();
	public String getCaregiverName(User ap);
	public HealthProfile getHealthProfile(User u);
	public String getPartOfDay();
	public User getAssistedPerson();
	public String getAPGenderArticle();
	public String getAPPosesiveGenderArticle();
	public String getCaregiverGenderArticle();
	public String getCaregiverPosesiveGenderArticle();
	
	

}
