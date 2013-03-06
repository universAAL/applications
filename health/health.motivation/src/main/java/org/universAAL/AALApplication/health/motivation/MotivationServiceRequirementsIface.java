package org.universAAL.AALApplication.health.motivation;

import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthProfile;

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
