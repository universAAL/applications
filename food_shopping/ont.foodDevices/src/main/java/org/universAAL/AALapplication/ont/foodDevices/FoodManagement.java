package org.universAAL.AALapplication.ont.foodDevices;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.AALapplication.ont.foodDevices.ShoppingOntology;

/**
 * @author dimokas
 * 
 */
public class FoodManagement extends Service {
	public static final String MY_URI = ShoppingOntology.NAMESPACE + "FoodManagement";
	public static final String PROP_CONTROLS = ShoppingOntology.NAMESPACE + "controls";

	public FoodManagement() {
		super();
	}

	public FoodManagement(String uri) {
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
