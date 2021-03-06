/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
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
 *****************************************************************************************/

package org.universAAL.AALapplication.safety_home.service.windowProvider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author dimokas
 * 
 */

public class Activator implements BundleActivator{

    public static ModuleContext mc;

    public void start(final BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		System.out.println("Window Provider started ...");
		new Thread() {
			public void run() {
				new CPublisher(mc);
			}
		}.start();
	}

	public void stop(BundleContext arg0) throws Exception {
	}

}
