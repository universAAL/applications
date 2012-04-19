/*******************************************************************************
 * Copyright 2011 Universidad Polit�cnica de Madrid
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
package org.universAAL.AALapplication.health.manager;

import java.util.Locale;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.ontology.profile.User;

public class HealthManager {

	private static HealthManager singleton;
	private static ModuleContext context;
	private ISubscriber isubcriber;
	private OPublisher opublisher;	
	private User user;
	
	private HealthManager() {
		isubcriber = new ISubscriber(context);
		opublisher = new OPublisher(context);
	}
	
	public static HealthManager getInstance() {
		if (singleton==null) {
			singleton = new HealthManager();
		}
		return singleton;
	}
	
	public static void setContext(ModuleContext ctxt) {
		context=ctxt;
	}
	public ISubscriber getIsubcriber() {
		return isubcriber;
	}
	public OPublisher getOpublisher() {
		return opublisher;
	}

	public User getUser() {
		return user;
	}

	public static Locale getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
