/*	
	Copyright 2010-2014 UPM http://www.upm.es
	Universidad Politécnica de Madrdid
	
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
package org.universAAL.AALapplication.health.ont;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{
	public static BundleContext context=null;

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		
		Class.forName("org.universAAL.AALapplication.health.ont.measurement.Measure");
		Class.forName("org.universAAL.AALapplication.health.ont.measurement.MultidimensionalMeasure");
		Class.forName("org.universAAL.AALapplication.health.ont.measurement.UnidimensionalMeasure");
		
		Class.forName("org.universAAL.AALapplication.health.ont.schedule.Event");
		Class.forName("org.universAAL.AALapplication.health.ont.schedule.Task");
		
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.HealthyHabitsAdoption");
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.MonitorizationMeasures");
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.PhysicalActivity");
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.QuitSmocking");
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.Treatment");
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.Treatment");
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.TreatmentDetails");
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.TreatmentPlanning");
		Class.forName("org.universAAL.AALapplication.health.ont.treatment.UserHealthProfile");
		
	}

	public void stop(BundleContext arg0) throws Exception {
	}

}
