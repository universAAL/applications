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
package org.universAAL.EnergyReader;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.EnergyReader.database.EnergyReaderDBInterface;
import org.universAAL.EnergyReader.utils.Setup;
import org.universAAL.middleware.container.ModuleContext;


public class Activator implements BundleActivator {
    public static BundleContext osgiContext = null;
    public static ModuleContext context = null;
    private Timer t,t1;

    public void start(BundleContext bcontext) throws Exception {
    	System.out.print("ACTIVATOR");
    	EnergyReaderDBInterface db = new EnergyReaderDBInterface();
    	System.out.print("FILE ENERGY "+Setup.getSetupFileName());
    	//db.createDB();
    	t = new Timer();
		t.schedule(new MinutePublisher(bcontext), 0, 60*1000);
		t1 = new Timer();
		t1.scheduleAtFixedRate(new DailyPublisher(bcontext), get1115am(), 1000*60*60*24);
    }

    public void stop(BundleContext arg0) throws Exception {
    	t.cancel();
    	t1.cancel();
    }

    private static Date get1115am(){
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(Calendar.DATE, 0);
        Calendar result = new GregorianCalendar(
          tomorrow.get(Calendar.YEAR),
          tomorrow.get(Calendar.MONTH),
          tomorrow.get(Calendar.DATE),
          13,
          55
        );
        return result.getTime();
      }
    
}
