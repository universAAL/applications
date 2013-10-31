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
package org.universAAL.AALApplication.health.motivation.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALApplication.health.motivation.MotivationManager;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

public class Activator implements BundleActivator{
	public void start(BundleContext arg0) throws Exception {
		MotivationManager.start(uAALBundleContainer.THE_CONTAINER
				.registerModule(new BundleContext[] { arg0 }));
		
	}

	public void stop(BundleContext arg0) throws Exception {
		MotivationManager.stop(uAALBundleContainer.THE_CONTAINER
				.registerModule(new BundleContext[] { arg0 }));		
	}
	



}
