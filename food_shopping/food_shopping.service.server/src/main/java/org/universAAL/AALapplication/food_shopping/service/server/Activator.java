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

package org.universAAL.AALapplication.food_shopping.service.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.food_shopping.service.db.Derby.DerbyInterface;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * @author dimokas
 *
 */
public class Activator implements BundleActivator {
	
    private FoodManagementProvider provider = null;
    private DerbyInterface db = new DerbyInterface();
    public static ModuleContext mc;
	
	public void start(final BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		LogUtils.logInfo(Activator.mc,	Activator.class,	"start",
				new Object[] { "Food Shopping Service started ..." }, null);
		db.init();
		if (!db.exist()){
			LogUtils.logInfo(Activator.mc,	DerbyInterface.class, "createDB",
					new Object[] { "Create database started ..." }, null);
			db.createDB();
			LogUtils.logInfo(Activator.mc,	DerbyInterface.class, "createDB",
					new Object[] { "Database creation completed ..." }, null);
		}
		
		new Thread() {
			public void run() {
				provider = new FoodManagementProvider(mc);
			}
		}.start();
	}

	public void stop(BundleContext context) throws Exception {
		if (provider != null) {
		    provider.close();
		    provider = null;
		}
	}
	
	public static ModuleContext getMc() {
		return mc;
	}
}
