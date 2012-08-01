package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.ontology.BiomedicalSensorsOntology;

/**
 * Ontological enumeration of possible Scale (sensors).
 * 
 * @author joemoul, billyk
 * 
 */
public class Scale extends SensorType {

	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "Scale";

	private static final String name = "Scale";
	public static final Scale scale = new Scale();

	public String getClassURI() {
		return MY_URI;
	}

	public static final Scale valueOf(String rqname) {
		if (rqname == null)
			return null;

		if (rqname.startsWith(BiomedicalSensorsOntology.NAMESPACE))
			rqname = rqname.substring(BiomedicalSensorsOntology.NAMESPACE
					.length());

		if (rqname.equals(name))
			return new Scale();

		return null;
	}

	private Scale() {
		super(BiomedicalSensorsOntology.NAMESPACE + name);

	}

	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_OPTIONAL;

	}

	public boolean isWellFormed() {
		return true;
	}

}
