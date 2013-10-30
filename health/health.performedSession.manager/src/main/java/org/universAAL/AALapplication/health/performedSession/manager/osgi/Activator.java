/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Polit�cnica de Madrdid
	
	OCO Source Materials
	� Copyright IBM Corp. 2011
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.AALapplication.health.performedSession.manager.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.health.performedSession.manager.PerformedSessionManagerProvider;
import org.universAAL.AALapplication.health.performedSession.manager.ProvidedPerformedSessionManagementService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * @author amedrano
 * @author roni
 */
public class Activator implements BundleActivator {

	ModuleContext context = null;
	private PerformedSessionManagerProvider provider = null;
	
	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bcontext) throws Exception {
		context=uAALBundleContainer.THE_CONTAINER
				.registerModule(new BundleContext[] { bcontext });
		
		LogUtils.logDebug(context, getClass(), "strat", "initialising");
		
		ProvidedPerformedSessionManagementService.initialize(context);
		provider = new PerformedSessionManagerProvider(context,
			ProvidedPerformedSessionManagementService.profiles);
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext Context) throws Exception {
		provider.close();
	}
}
