/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnolog�as para la Salud y el Bienestar
	
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
package org.universAAL.ltba.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.ltba.functional.energy.EnergyConsequenceListener;
import org.universAAL.ltba.functional.pir.PIRConsequenceListener;
import org.universAAL.ltba.service.LTBAProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author mllorente
 * 
 */
public class Activator implements BundleActivator {

	private LTBAProvider provider;
	public static ModuleContext mc;

	public void start(BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });
		ConsequenceListener listener = new ConsequenceListener(mc);
		EnergyConsequenceListener ecl = new EnergyConsequenceListener(mc);
		PIRConsequenceListener pir = new PIRConsequenceListener(mc);
		provider = new LTBAProvider(mc, listener);

	}

	public void stop(BundleContext context) throws Exception {
	    provider.close();

	}

}
