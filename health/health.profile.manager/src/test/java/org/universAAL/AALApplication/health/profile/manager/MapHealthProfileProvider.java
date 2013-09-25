/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 * Universidad Polit�cnica de Madrid
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
package org.universAAL.AALApplication.health.profile.manager;

import java.util.TreeMap;

import org.universAAL.ontology.health.owl.HealthProfile;

/**
 * @author amedrano
 *
 */
public class MapHealthProfileProvider implements IHealthProfileProvider {

	private TreeMap<String, HealthProfile> healthProfileDB;
	
	public MapHealthProfileProvider() {
		healthProfileDB = new TreeMap<String, HealthProfile>();
	}
	
	/* (non-Javadoc)
	 * @see org.universAAL.AALApplication.health.profile.manager.HealthProfileProvider#getHealthProfile(java.lang.String)
	 */
	public HealthProfile getHealthProfile(String userURI) {
		if (healthProfileDB.containsKey(userURI)) {
			return healthProfileDB.get(userURI);
		}
		else {
			return new HealthProfile();
		}
	}

	/* (non-Javadoc)
	 * @see org.universAAL.AALApplication.health.profile.manager.HealthProfileProvider#updateHealthProfile(org.universaal.ontology.health.owl.HealthProfile)
	 */
	public void updateHealthProfile(HealthProfile healthProfile) {
		String uri = healthProfile.getAssignedAssistedPerson().getURI();
		if (healthProfileDB.containsKey(uri)) {
			healthProfileDB.put(uri, healthProfile);
		}
		else {
			healthProfileDB.remove(uri);
			healthProfileDB.put(uri, healthProfile);
		}

	}

}
