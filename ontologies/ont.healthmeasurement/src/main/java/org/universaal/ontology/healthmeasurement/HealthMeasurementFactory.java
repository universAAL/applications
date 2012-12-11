
package org.universaal.ontology.healthmeasurement;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universaal.ontology.healthmeasurement.owl.*;

public class HealthMeasurementFactory extends ResourceFactoryImpl {


  public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {

	switch (factoryIndex) {
     case 0:
       return new HealthMeasurement(instanceURI);
     case 1:
       return new Unit(instanceURI);
     case 2:
       return new MultiValueMeasurement(instanceURI);
     case 3:
       return new PersonWeight(instanceURI);
     case 4:
       return new SingleValueMeasurement(instanceURI);
     case 5:
       return new Measurement(instanceURI);
     case 6:
       return new BloodPressure(instanceURI);
     case 7:
       return new HeartRate(instanceURI);
     case 8:
       return new HeartRateSignal(instanceURI);
     case 9:
       return new Signal(instanceURI);

	}
	return null;
  }
}
