package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.ontology.BiomedicalSensorsOntology;

/**
 * Ontological enumeration of possible Zephyr sensors. 
 * 
 * @author billk, joemoul
 * 
 */
public class Zephyr extends SensorType {

	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "Zephyr";

	public static final Zephyr zephyr = new Zephyr();

	public String getClassURI() {
		return MY_URI;
	}

	public static final Zephyr valueOf(String rqzephyrName) {
		if (rqzephyrName == null)
			return null;

		if (rqzephyrName.startsWith(BiomedicalSensorsOntology.NAMESPACE))
			rqzephyrName = rqzephyrName
					.substring(BiomedicalSensorsOntology.NAMESPACE.length());

		if (rqzephyrName.equals(MY_URI))
			return new Zephyr();

		return null;
	}

	private Zephyr() {
		super(MY_URI);

	}

	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_OPTIONAL;

	}

	public boolean isWellFormed() {
		return true;
	}

}
