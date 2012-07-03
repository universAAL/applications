package org.universAAL.ontology.safetyDevices;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.phThing.Device;


/**
 * @author dimokas
 *
 * Window Ontology
 */
public class SmokeSensor extends Device {
	public static final String MY_URI = SafetyOntology.NAMESPACE + "Smoke"; 
	//public static final String PROP_DEVICE_LOCATION;
	public static final String PROP_DEVICE_STATUS = SafetyOntology.NAMESPACE + "deviceStatus";
	public static final String PROP_SMOKE = SafetyOntology.NAMESPACE + "homeSmoke";
	
	public SmokeSensor(){
		super();
	}
	
	public SmokeSensor(String uri) {
		super(uri);				
	}

    public String getClassURI() {
    	return MY_URI;
    }
	
	public static String[] getStandardPropertyURIs() {
		return new String[] {PROP_PHYSICAL_LOCATION, PROP_DEVICE_STATUS, PROP_SMOKE};
	}
/*	
	public static String getRDFSComment() {
		return "A smoke sensor";
	}
	
	public static String getRDFSLabel() {
		return "Smoke";
	}
*/		
		
	public SmokeSensor(String uri, Location loc) {
		super(uri);
		if (loc == null)
			   throw new IllegalArgumentException();
	
		props.put(PROP_DEVICE_STATUS, new Integer(0));
		props.put(PROP_PHYSICAL_LOCATION, loc);
	}

	public SmokeSensor(String uri, Location loc, boolean smoke) {
		super(uri);
		if (loc == null)
			   throw new IllegalArgumentException();
	
		props.put(PROP_DEVICE_STATUS, new Integer(0));
		props.put(PROP_PHYSICAL_LOCATION, loc);
		props.put(PROP_SMOKE, new Boolean(smoke));
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
	
	public void setSmoke(boolean smoke){
		props.put(PROP_SMOKE, new Boolean(smoke));
	}
	
	public boolean getSmoke() {
		Boolean i = (Boolean) props.get(PROP_SMOKE);
		return (i == true) ? true : false;
    }

	public void setDeviceLocation(Location l) {
		if (l != null)
		    props.put(PROP_PHYSICAL_LOCATION, l);
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
