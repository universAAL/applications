/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es - Universidad Politécnica de Madrid
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
package org.universaal.ontology.health;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universaal.ontology.health.owl.*;

public class HealthOntologyFactory extends ResourceFactoryImpl {


  public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {

	switch (factoryIndex) {
     case 0:
	    return new BloodPressureRequirement(instanceURI);
     case 1:
	    return new PhysicalActivity(instanceURI);
     case 2:
	    return new MeasurementRequirements(instanceURI);
     case 3:
	    return new WeightRequirement(instanceURI);
     case 4:
	    return new PlannedSession(instanceURI);
     case 5:
	    return new PerformedMeasurementSession(instanceURI);
     case 6:
    	 //return new Privacy(instanceURI);
     case 7:
    	 return new AssistedPersonWithHealthProfile(instanceURI);
     case 8:
	    return new HeartRateRequirement(instanceURI);
     case 9:
	    return new TakeMeasurementActivity(instanceURI);
     case 10:
	    return new Caregiver(instanceURI);
     case 11:
	    return new TreatmentPlanning(instanceURI);
     case 12:
	    return new HealthProfile(instanceURI);
     case 13:
 	    return new PerformedSession(instanceURI);
     case 14:
  	    return new DiastolicBloodPressureRequirement(instanceURI);
     case 15:
  	    return new SystolicBloodPressureRequirement(instanceURI);
     case 16:
   	    return new ActivityHeartRateRequirement(instanceURI);
      case 17:
   	    return new ReposeHeartRateRequirement(instanceURI);
      case 18:
     	return new TreatmentManagementService(instanceURI);
      case 19:
       	return new PlannedSessionManagementService(instanceURI);
      case 20:
        return new PerformedSessionManagementService(instanceURI);
      case 21:
          return new HealthService(instanceURI);
	}
	return null;
  }
}
