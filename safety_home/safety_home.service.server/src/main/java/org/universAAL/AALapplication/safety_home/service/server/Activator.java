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

package org.universAAL.AALapplication.safety_home.service.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author dimokas
 *
 */

public class Activator implements BundleActivator, ServiceListener {
	
    private SafetyProvider provider = null;
    public static ModuleContext mc;
	
	public void start(final BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		System.out.println("Safety at Home Service started ...");
		new Thread() {
			public void run() {
				provider = new SafetyProvider(mc);
			}
		}.start();
		
		System.out.println("Starting to listen for service events.");
        context.addServiceListener(this);
		//context.registerService(DataValidatorFactory.class.getName(), new DataValidatorFactory(), null);
	}

	public void stop(BundleContext context) throws Exception {
		if (provider != null) {
		    provider.close();
		    provider = null;
		}
		
		context.removeServiceListener(this);
        System.out.println("Stopped listening for service events.");
	}
	
	public static ModuleContext getMc() {
		return mc;
	}

	public void serviceChanged(ServiceEvent event) {
		String[] objectClass = (String[])event.getServiceReference().getProperty("objectClass");

		if (event.getType() == ServiceEvent.REGISTERED){
	            System.out.println("Ex1: Service of type " + objectClass[0] + " registered.");
	    }
		else if (event.getType() == ServiceEvent.UNREGISTERING){
			System.out.println("Ex1: Service of type " + objectClass[0] + " unregistered.");
		}
		else if (event.getType() == ServiceEvent.MODIFIED){
			System.out.println("Ex1: Service of type " + objectClass[0] + " modified.");
		}		
	}
}
