package org.universAAL.ontology.ltba;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.LTBAOntology;

/**
 * Ontology for the LTBA service.
 * 
 * @author mllorente
 * 
 */
public class LTBAService extends Service {

	public static final String MY_URI = LTBAOntology.NAMESPACE + "LTBAService";
	public static final String PROP_SERVICE_HAS_STATUS_VALUE = LTBAOntology.NAMESPACE
			+ "hasStatusValue";
	public static final String PROP_HAS_TEXT_REPORT = LTBAOntology.NAMESPACE
			+ "hasTextReport";
	public static final String PROP_HAS_WEEK_REPORT = LTBAOntology.NAMESPACE
			+ "hasWeekReport";
	public static final String PROP_HAS_MONTH_REPORT = LTBAOntology.NAMESPACE
			+ "hasMonthReport";
	public static final String PROP_HAS_ACTIVITY_REPORT = LTBAOntology.NAMESPACE
			+ "hasActivityReport";

	public LTBAService() {
		super();
	}

	public LTBAService(String uri) {
		super(uri);
	}

	public String getClassURI() {
		return MY_URI;
	}

	public void setServiceStatus(boolean status) {
		props.put(PROP_SERVICE_HAS_STATUS_VALUE, new Boolean(status));
	}

	public Boolean getServiceStatus() {
		return (Boolean) props.get(PROP_SERVICE_HAS_STATUS_VALUE);
	}

}
