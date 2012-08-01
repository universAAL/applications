package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.BiomedicalSensorsOntology;

/**
 * Ontological service that handles biomedical sensors.
 * 
 * @author joemoul, billyk
 * 
 */
public class BiomedicalSensorService extends Service {
	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "BiomedicalSensorService";
	public static final String PROP_CONTROLS = BiomedicalSensorsOntology.NAMESPACE
			+ "controls";

	public BiomedicalSensorService() {
		super();
	}

	public BiomedicalSensorService(String uri) {
		super(uri);
	}

	public String getClassURI() {
		return MY_URI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
	 * (java.lang.String)
	 */
	public int getPropSerializationType(String propURI) {
		return PROP_CONTROLS.equals(propURI) ? PROP_SERIALIZATION_FULL : super
				.getPropSerializationType(propURI);
	}

	public boolean isWellFormed() {
		return true;
	}
}
