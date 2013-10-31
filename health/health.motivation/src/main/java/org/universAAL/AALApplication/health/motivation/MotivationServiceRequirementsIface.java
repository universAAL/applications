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
