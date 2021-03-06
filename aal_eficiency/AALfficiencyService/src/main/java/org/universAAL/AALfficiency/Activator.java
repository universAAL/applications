/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
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
package org.universAAL.AALfficiency;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALfficiency.utils.Setup;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;


public class Activator implements BundleActivator {
    public static BundleContext osgiContext = null;
    public static  ModuleContext context = null;

	public static final AALfficiencyProvider scallee=null;


    public void start(BundleContext bcontext) throws Exception {
    	System.out.print("Starting AALfficiency Service");
	Activator.osgiContext = bcontext;
	Activator.context = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { bcontext });
		//scallee=new AALfficiencyProvider(context);
		new Thread() {
		    public void run() {
			new AALfficiencyProvider(context);
			new ScoresConsumer(context);
			System.out.print("FILE AALFFICIENCY "+Setup.getSetupFileName());
		    }
		}.start();

    }

    public void stop(BundleContext arg0) throws Exception {
    	System.out.print("Stopping AALfficiency Service");
		scallee.close();

    }
    /**
     * @return the mcontext
     */
    public static ModuleContext getMcontext() {
        return context;
    }
}
