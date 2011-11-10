package org.universAAL.ontology.safetyDevices;

import java.util.Hashtable;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.owl.Service;

/**
 * @author dimokas 
 * 
 * Safety is similar to Lighting
 * 
 */
public class Safety extends Service {
	public static final String MY_URI;
	public static final String PROP_CONTROLS;
	public static final String SAFETY_NAMESPACE = "http://ontology.universaal.org/Safety.owl#";

	
	private static Hashtable SafetyRestrictions = new Hashtable(2);
	static {
		
		MY_URI = SAFETY_NAMESPACE + "Safety";
		PROP_CONTROLS = SAFETY_NAMESPACE + "controls";
		register(Safety.class);
		addRestriction(Restriction.getAllValuesRestriction(PROP_CONTROLS,
				Door.MY_URI), new String[] { PROP_CONTROLS },
				SafetyRestrictions);
	}

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI == null)
			return null;
		Object r = SafetyRestrictions.get(propURI);
		if (r instanceof Restriction)
			return (Restriction) r;
		return Service.getClassRestrictionsOnProperty(propURI);
	}

	public static String getRDFSComment() {
		return "The class of services controling home devices.";
	}

	public static String getRDFSLabel() {
		return "Safety";
	}

	public Safety() {
		super();
	}

	public Safety(String uri) {
		super(uri);
	}

	protected Hashtable getClassLevelRestrictions() {
		return SafetyRestrictions;
	}

	public int getPropSerializationType(String propURI) {
		return PROP_CONTROLS.equals(propURI) ? PROP_SERIALIZATION_FULL : super
				.getPropSerializationType(propURI);
	}

	public boolean isWellFormed() {
		return true;
	}
}
