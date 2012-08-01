package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.BiomedicalSensorsOntology;

/**
 * Ontological representation of a Measure Entity.
 * 
 * @author billyk, joemoul
 * 
 */
public class MeasuredEntity extends ManagedIndividual {

	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "MeasuredEntity";
	public static final String PROP_MEASUREMENT_NAME = BiomedicalSensorsOntology.NAMESPACE
			+ "measurementName";
	public static final String PROP_MEASUREMENT_VALUE = BiomedicalSensorsOntology.NAMESPACE
			+ "measurementValue";
	public static final String PROP_MEASUREMENT_ERROR = BiomedicalSensorsOntology.NAMESPACE
			+ "measurementError";
	public static final String PROP_MEASUREMENT_UNIT = BiomedicalSensorsOntology.NAMESPACE
			+ "measurementUnit";
	public static final String PROP_MEASUREMENT_TIME = BiomedicalSensorsOntology.NAMESPACE
			+ "measurementTime";
	public static final String PROP_TERMINOLOGY_CODE = BiomedicalSensorsOntology.NAMESPACE
			+ "terminologyCode";
	public static final String PROP_TERMINOLOGY_PURL = BiomedicalSensorsOntology.NAMESPACE
			+ "terminologyPURL";

	public MeasuredEntity() {
		super();
	}

	public MeasuredEntity(String uri, String name, String value, String error,
			String unit, String time, String termincode, String terminPURL) {
		super(uri);

		props.put(PROP_MEASUREMENT_NAME, name);
		props.put(PROP_MEASUREMENT_VALUE, value);
		props.put(PROP_MEASUREMENT_ERROR, error);
		props.put(PROP_MEASUREMENT_UNIT, unit);
		props.put(PROP_MEASUREMENT_TIME, time);
		props.put(PROP_TERMINOLOGY_CODE, termincode);
		props.put(PROP_TERMINOLOGY_PURL, terminPURL);
	}

	public MeasuredEntity(String uri) {
		super(uri);
	}

	public String getMeasurementName() {
		return ((String) props.get(MeasuredEntity.PROP_MEASUREMENT_NAME))
				.toString();
	}

	public void setMeasurementName(String val) {
		props.put(MeasuredEntity.PROP_MEASUREMENT_NAME, val);
	}

	public String getMeasurementValue() {
		return ((String) props.get(MeasuredEntity.PROP_MEASUREMENT_VALUE))
				.toString();
	}

	public void setMeasurementValue(String val) {
		props.put(MeasuredEntity.PROP_MEASUREMENT_VALUE, val);
	}

	public String getMeasurementError() {
		return ((String) props.get(MeasuredEntity.PROP_MEASUREMENT_ERROR))
				.toString();
	}

	public void setMeasurementError(String val) {
		props.put(MeasuredEntity.PROP_MEASUREMENT_ERROR, val);
	}

	public String getMeasurementUnit() {
		return ((String) props.get(MeasuredEntity.PROP_MEASUREMENT_UNIT))
				.toString();
	}

	public void setMeasurementUnit(String val) {
		props.put(MeasuredEntity.PROP_MEASUREMENT_UNIT, val);
	}

	public String getMeasurementTime() {
		return ((String) props.get(MeasuredEntity.PROP_MEASUREMENT_TIME))
				.toString();
	}

	public void setMeasurementTime(String val) {
		props.put(MeasuredEntity.PROP_MEASUREMENT_TIME, val);
	}

	public String getTerminologyCode() {
		return ((String) props.get(MeasuredEntity.PROP_TERMINOLOGY_CODE))
				.toString();
	}

	public void setTerminologyCode(String val) {
		props.put(MeasuredEntity.PROP_TERMINOLOGY_CODE, val);
	}

	public String getTerminologyPURL() {
		return ((String) props.get(MeasuredEntity.PROP_TERMINOLOGY_PURL))
				.toString();
	}

	public void setTerminologyPURL(String val) {
		props.put(MeasuredEntity.PROP_TERMINOLOGY_PURL, val);
	}

	public String getClassURI() {
		return MY_URI;
	}

	public int getPropSerializationType(String propURI) {
		return 0;
	}
}
