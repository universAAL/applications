package org.universAAL.ontology.safetyDevices;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.safetyDevices.SafetyOntology;

/**
 * @author dimokas
 * 
 */
public class SafetyManagement extends Service {
	public static final String MY_URI = SafetyOntology.NAMESPACE + "SafetyManagement";
	public static final String PROP_CONTROLS = SafetyOntology.NAMESPACE + "controls";

	public SafetyManagement() {
		super();
	}

	public SafetyManagement(String uri) {
		super(uri);
	}

	public String getClassURI() {
		return MY_URI;
	}

	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		//return true && props.containsKey(PROP_CONTROLS);
		return true;
	}
}
