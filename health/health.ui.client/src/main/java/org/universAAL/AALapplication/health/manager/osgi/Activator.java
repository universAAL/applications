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
package org.universAAL.AALapplication.health.manager.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.health.manager.MainButtonProvider;
import org.universAAL.AALapplication.health.manager.service.GenericTreatmentDisplayService;
import org.universAAL.AALapplication.health.manager.service.GenericTreatmentDisplayer;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * @author amedrano
 *
 */
public class Activator implements BundleActivator {

	static ModuleContext context;
	public MainButtonProvider service;
	public GenericTreatmentDisplayer gds;

	public void start(BundleContext arg0) throws Exception {	
		context = uAALBundleContainer.THE_CONTAINER
                .registerModule(new Object[] {arg0});	
		LogUtils.logInfo(context, getClass(), "start", "Starting Health UI");

		/*
		 * uAAL stuff
		 */
		service = new MainButtonProvider(context);
		
		GenericTreatmentDisplayService.initialize(context);
		gds = new GenericTreatmentDisplayer(context,GenericTreatmentDisplayService.profs);
		
		LogUtils.logInfo(context, getClass(), "start", "Health UI started");
	}


	public void stop(BundleContext arg0) throws Exception {
		service.close();
		gds.close();
		LogUtils.logInfo(context, getClass(), "stop", "stoped Health UI");
	}

}
