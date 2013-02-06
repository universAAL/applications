package org.universAAL.ontology;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.Safety.Door;
import org.universAAL.ontology.Safety.HumiditySensor;
import org.universAAL.ontology.Safety.LightSensor;
import org.universAAL.ontology.Safety.MotionSensor;
import org.universAAL.ontology.Safety.SafetyManagement;
import org.universAAL.ontology.Safety.SmokeSensor;
import org.universAAL.ontology.Safety.TemperatureSensor;
import org.universAAL.ontology.Safety.Window;

public class SafetyFactory extends ResourceFactoryImpl {

    public SafetyFactory() {
    }

    public Resource createInstance(String classURI, String instanceURI,
	    int factoryIndex) {
	switch (factoryIndex) {
	case 0:
	    return new Door(instanceURI);
	case 1:
	    return new HumiditySensor(instanceURI);
	case 2:
	    return new LightSensor(instanceURI);
	case 3:
	    return new MotionSensor(instanceURI);
	case 4:
	    return new SmokeSensor(instanceURI);
	case 5:
	    return new TemperatureSensor(instanceURI);
	case 6:
	    return new Window(instanceURI);
	case 7:
	    return new SafetyManagement(instanceURI);
	}

	return null;
    }

    public Resource castAs(Resource r, String classURI) {
	return null;
    }
}
