package org.universAAL.ontology.Shopping;

import org.universAAL.ontology.Shopping.ShoppingOntology;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.phThing.Device;


/**
 * @author dimokas
 *
 */

public class Refrigerator extends Device {
	public static final String MY_URI = ShoppingOntology.NAMESPACE + "Refrigerator"; 
	public static final String PROP_TEMPERATURE = ShoppingOntology.NAMESPACE + "temperature";
	//public static final String PROP_PHYSICAL_LOCATION = PROP_PHYSICAL_LOCATION;
	public static final String PROP_DEVICE_STATUS = ShoppingOntology.NAMESPACE + "deviceStatus";
	public static final String PROP_HAS_FOODITEMS = ShoppingOntology.NAMESPACE + "FoodItems";
	

	public Refrigerator(){
		super();
	}
	
	public Refrigerator(String uri) {
		super(uri);				
	}

    public String getClassURI() {
    	return MY_URI;
    }

	public static String[] getStandardPropertyURIs() {
		//return new String[] {PROP_PHYSICAL_LOCATION, PROP_DEVICE_STATUS};
		return new String[] {PROP_TEMPERATURE, PROP_PHYSICAL_LOCATION, PROP_DEVICE_STATUS, PROP_HAS_FOODITEMS};
	}
/*	
	public static String getRDFSComment() {
		return "The class of all Refrigerators";
	}
	
	public static String getRDFSLabel() {
		return "Refrigerator";
	}
*/		
		
	public Refrigerator(String uri, Location loc) {
		super(uri);
		if (loc == null)
			   throw new IllegalArgumentException();
		props.put(PROP_DEVICE_STATUS, new Integer(0));
		props.put(PROP_PHYSICAL_LOCATION, loc);
	}
	
	public Refrigerator(String uri, Location loc, int temp ) {
		super(uri);
		if (loc == null)
			   throw new IllegalArgumentException();
		props.put(PROP_DEVICE_STATUS, new Integer(0));
		props.put(PROP_PHYSICAL_LOCATION, loc);
		props.put(PROP_TEMPERATURE, new Integer(temp));
	}

	
	public int getStatus() {
		Integer i = (Integer) props.get(PROP_DEVICE_STATUS);
		return (i == null) ? -1 : i.intValue();
	    }
	
	public Location getDeviceLocation() {
		return (Location) props.get(PROP_PHYSICAL_LOCATION);
	}
	public void setStatus(int state) {
		if (state > -1 && state < 101)
		    props.put(PROP_DEVICE_STATUS, new Integer(state));
	}
	
	public Integer getDeviceTemperature() {
		return (Integer) props.get(PROP_TEMPERATURE);
	    }
	public void setTemperature(int temp) {
		if (temp > -18 && temp < 20)
		    props.put(PROP_TEMPERATURE, new Integer(temp));
	    }

	
	public void setDeviceLocation(Location l) {
		if (l != null)
		    props.put(PROP_PHYSICAL_LOCATION, l);
    }

	public void setFoodItems(FoodItem[] fi) {
	    props.put(PROP_HAS_FOODITEMS, fi);
	}

	
	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
		//return (PROP_PHYSICAL_LOCATION.equals(propURI)) ? PROP_SERIALIZATION_REDUCED
		//	: PROP_SERIALIZATION_FULL;
	}
		
	public boolean isWellFormed() {
		return true;
		//return props.containsKey(PROP_DEVICE_STATUS)
		//&& props.containsKey(PROP_PHYSICAL_LOCATION);
	}
	
	
}
