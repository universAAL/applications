package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.BiomedicalSensorsOntology;

public abstract class ConnectionType extends ManagedIndividual {
	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "connectionType";

	protected ConnectionType(String uri) {
		super(uri);
	}

	public String getClassURI() {
		return MY_URI;
	}
}
