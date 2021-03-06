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

package org.universAAL.AALapplication.safety_home.service.uiclient.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.safety_home.service.uiclient.SharedResources;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.AALapplication.safety_home.service.uiclient.db.*;

/**
 * @author dimokas
 * 
 */

public class Activator implements BundleActivator {
	private static ModuleContext moduleContext;
    SharedResources sr;
    private DerbyInterface db = new DerbyInterface();

    public void start(final BundleContext context) throws Exception {
    	moduleContext = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
   		sr = new SharedResources(moduleContext);

		db.init();
		if (!db.exist()){
			LogUtils.logInfo(moduleContext,	DerbyInterface.class, "createDB",
					new Object[] { "Create database started ..." }, null);
			db.createDB();
			LogUtils.logInfo(moduleContext,	DerbyInterface.class, "createDB",
					new Object[] { "Database creation completed ..." }, null);
		}
		
   		new Thread() {
   		    public void run() {
   			try {
				sr.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		    }
   		}.start();
    }

    public void stop(BundleContext context) throws Exception {
    }

	public static ModuleContext getModuleContext() {
		return moduleContext;
	}
    
    
}
