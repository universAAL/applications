package org.universAAL.ontology.Safety;

import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.phThing.Device;

/**
 * @author dimokas
 * 
 *         Door is similar to LightSource
 */
public class Door extends Device {
    // public static final String SAFETY_NAMESPACE =
    // "http://ontology.universaal.org/Safety.owl#";
    public static final String MY_URI = SafetyOntology.NAMESPACE + "Door";
    // public static final String PROP_DEVICE_LOCATION =
    // SafetyOntology.NAMESPACE + "deviceLocation";
    public static final String PROP_DEVICE_STATUS = SafetyOntology.NAMESPACE
	    + "deviceStatus";
    public static final String PROP_DEVICE_RFID = SafetyOntology.NAMESPACE
	    + "deviceRfid";

    public Door() {
	super();
    }

    public Door(String uri) {
	super(uri);
    }

    public String getClassURI() {
	return MY_URI;
    }

    public static String[] getStandardPropertyURIs() {
	// return new String[] {PROP_DEVICE_LOCATION, PROP_DEVICE_STATUS};
	return new String[] { PROP_PHYSICAL_LOCATION, PROP_DEVICE_STATUS,
		PROP_DEVICE_RFID };
    }

    /*
     * public static String getRDFSComment() { return "The class of all Doors";
     * }
     * 
     * public static String getRDFSLabel() { return "Door"; }
     */
    public Door(String uri, Location loc) {
	super(uri);
	if (loc == null)
	    throw new IllegalArgumentException();

	props.put(PROP_DEVICE_STATUS, new Integer(0));
	props.put(PROP_PHYSICAL_LOCATION, loc);
    }

    public Door(String uri, Location loc, String cardName) {
	super(uri);
	if (loc == null)
	    throw new IllegalArgumentException();

	props.put(PROP_DEVICE_STATUS, new Integer(0));
	props.put(PROP_PHYSICAL_LOCATION, loc);
	props.put(PROP_DEVICE_RFID, cardName);
    }

    public int getStatus() {
	Integer i = (Integer) props.get(PROP_DEVICE_STATUS);
	return (i == null) ? -1 : i.intValue();
    }

    public String getRfid() {
	return (String) props.get(PROP_DEVICE_RFID);
    }

    public void setDeviceRfid(String cardName) {
	if (cardName != null)
	    props.put(PROP_DEVICE_RFID, cardName);
    }

    public Location getDeviceLocation() {
	return (Location) props.get(PROP_PHYSICAL_LOCATION);
    }

    public void setStatus(int state) {
	if (state > -1 && state < 101)
	    props.put(PROP_DEVICE_STATUS, new Integer(state));
    }

    public void setDeviceLocation(Location l) {
	if (l != null)
	    props.put(PROP_PHYSICAL_LOCATION, l);
    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
	// return (PROP_PHYSICAL_LOCATION.equals(propURI)) ?
	// PROP_SERIALIZATION_REDUCED
	// : PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return true;
	// return props.containsKey(PROP_DEVICE_STATUS)
	// && props.containsKey(PROP_DEVICE_LOCATION);
    }

}
