package org.universAAL.ontology.foodDevices;

import java.util.Hashtable;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.owl.Service;

/**
 * @author dimokas
 *
 */
public class FoodManagement extends Service {
	public static final String MY_URI;
	public static final String PROP_CONTROLS;
	public static final String FOODMANAGEMENT_NAMESPACE = "http://ontology.universaal.org/FoodManagement.owl#";

	
	private static Hashtable FoodManagementRestrictions = new Hashtable(2);
	static {
		
		MY_URI = Refrigerator.FOODMANAGEMENT_NAMESPACE + "FoodManagement";
		PROP_CONTROLS = Refrigerator.FOODMANAGEMENT_NAMESPACE + "controls";
		register(FoodManagement.class);
		addRestriction(Restriction.getAllValuesRestriction(PROP_CONTROLS,
				Refrigerator.MY_URI), new String[] { PROP_CONTROLS },
				FoodManagementRestrictions);
	}

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI == null)
			return null;
		Object r = FoodManagementRestrictions.get(propURI);
		if (r instanceof Restriction)
			return (Restriction) r;
		return Service.getClassRestrictionsOnProperty(propURI);
	}

	public static String getRDFSComment() {
		return "The class of services controling home devices.";
	}

	public static String getRDFSLabel() {
		return "FoodManagement";
	}

	public FoodManagement() {
		super();
	}

	public FoodManagement(String uri) {
		super(uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universAAL.ontology.Service#getClassLevelRestrictions()
	 */
	protected Hashtable getClassLevelRestrictions() {
		return FoodManagementRestrictions;
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
