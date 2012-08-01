package org.universAAL.ontology;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.biomedicalsensors.BiomedicalSensorService;
import org.universAAL.ontology.biomedicalsensors.CompositeBiomedicalSensor;
import org.universAAL.ontology.biomedicalsensors.MeasuredEntity;

/**
 * The factory for instantiating objects of the ontology classes.
 * 
 * @author joemoul
 */

public class BiomedicalSensorsFactory extends ResourceFactoryImpl {

	public Resource createInstance(String classURI, String instanceURI,
			int factoryIndex) {

		switch (factoryIndex) {
		case 0:
			return new CompositeBiomedicalSensor(instanceURI);
		case 1:
			return new BiomedicalSensorService(instanceURI);
		case 2:
			return new MeasuredEntity(instanceURI);
		}

		return null;
	}
}
