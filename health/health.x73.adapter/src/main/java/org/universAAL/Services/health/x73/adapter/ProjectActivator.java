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
package org.universAAL.Services.health.x73.adapter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

public class ProjectActivator implements BundleActivator {

	static ModuleContext context;
	private EventSubscriber sub;
	
	public void start(BundleContext arg0) throws Exception {	
		context = uAALBundleContainer.THE_CONTAINER
                .registerModule(new Object[] {arg0});	
		LogUtils.logDebug(context, getClass(), "start", "Starting.");
		/*
		 * uAAL stuff
		 */
		sub=new EventSubscriber(context, EventSubscriber.getContextSubscriptionParams());
		LogUtils.logDebug(context, getClass(), "start", "Started.");
	}


	public void stop(BundleContext arg0) throws Exception {
		LogUtils.logDebug(context, getClass(), "start", "Stopping.");
		/*
		 * close uAAL stuff
		 */
		sub.close();
		LogUtils.logDebug(context, getClass(), "start", "Stopped.");

	}

}
