package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.BiomedicalSensorsOntology;

/**
 * Ontological representation of a the sensor type concept, to be extended by
 * different enumerations of types.
 * 
 * @author billyk, joemoul
 * 
 */
public abstract class SensorType extends ManagedIndividual {
	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "sensorType";

	protected SensorType(String uri) {
		super(uri);
	}

	public String getClassURI() {
		return MY_URI;
	}
}
