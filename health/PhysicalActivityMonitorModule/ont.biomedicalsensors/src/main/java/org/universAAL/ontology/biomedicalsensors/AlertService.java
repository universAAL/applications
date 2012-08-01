package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.ontology.BiomedicalSensorsOntology;

/**
 * Ontological service that handles Alerts.
 * 
 * @author joemoul, billyk
 * 
 */
public class AlertService extends BiomedicalSensorService {
	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "AlertService";
	public static final String PROP_MONITORS = BiomedicalSensorsOntology.NAMESPACE
			+ "monitorsAlerts";

	public AlertService() {
		super();
	}

	public AlertService(String uri) {
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
