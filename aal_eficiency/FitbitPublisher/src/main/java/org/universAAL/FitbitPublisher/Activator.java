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

package org.universAAL.FitbitPublisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.FitbitPublisher.FitbitAPI.FitbitService;
import org.universAAL.FitbitPublisher.database.FitbitDBInterface;
import org.universAAL.FitbitPublisher.utils.Setup;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;


public class Activator implements BundleActivator {
    public static BundleContext osgiContext = null;
    public static ModuleContext context = null;
    
    public static Setup s = new Setup();
    
    
    public void start(BundleContext bcontext) throws Exception {
	Activator.osgiContext = bcontext;
	Activator.context = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { bcontext });
	
	Timer t1 = new Timer();
	t1.scheduleAtFixedRate(new FitbitPublisher(bcontext), getTime(), 1000*60*60*24);
	
		
    }

    public void stop(BundleContext arg0) throws Exception {
    }

    public static Date getTime(){
    	 Calendar tomorrow = new GregorianCalendar();
         tomorrow.add(Calendar.DATE, 0);
         Calendar result = new GregorianCalendar(
           tomorrow.get(Calendar.YEAR),
           tomorrow.get(Calendar.MONTH),
           tomorrow.get(Calendar.DATE),
           13,
           33
         );
         return result.getTime();
    }
    
}
