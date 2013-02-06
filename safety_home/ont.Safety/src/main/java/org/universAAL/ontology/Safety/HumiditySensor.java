package org.universAAL.ontology.Safety;

import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.phThing.Device;

/**
 * @author dimokas
 * 
 *         Window Ontology
 */
public class HumiditySensor extends Device {
    public static final String MY_URI = SafetyOntology.NAMESPACE + "Humidity";
    // public static final String PROP_DEVICE_LOCATION;
    public static final String PROP_DEVICE_STATUS = SafetyOntology.NAMESPACE
	    + "deviceStatus";
    public static final String PROP_HUMIDITY = SafetyOntology.NAMESPACE
	    + "homeHumidity";

    public HumiditySensor() {
	super();
    }

    public HumiditySensor(String uri) {
	super(uri);
    }

    public String getClassURI() {
	return MY_URI;
    }

    public static String[] getStandardPropertyURIs() {
	return new String[] { PROP_PHYSICAL_LOCATION, PROP_DEVICE_STATUS,
		PROP_HUMIDITY };
    }

    /*
     * public static String getRDFSComment() { return "A humidity sensor"; }
     * 
     * public static String getRDFSLabel() { return "Humidity"; }
     */
    public HumiditySensor(String uri, Location loc) {
	super(uri);
	if (loc == null)
	    throw new IllegalArgumentException();

	props.put(PROP_DEVICE_STATUS, new Integer(0));
	props.put(PROP_PHYSICAL_LOCATION, loc);
    }

    public HumiditySensor(String uri, Location loc, float humidity) {
	super(uri);
	if (loc == null)
	    throw new IllegalArgumentException();

	props.put(PROP_DEVICE_STATUS, new Integer(0));
	props.put(PROP_PHYSICAL_LOCATION, loc);
	props.put(PROP_HUMIDITY, new Float(humidity));
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

    public void setHumidity(float humidity) {
	props.put(PROP_HUMIDITY, new Float(humidity));
    }

    public float getHumidity() {
	Float i = (Float) props.get(PROP_HUMIDITY);
	return (i == null) ? -1 : i.floatValue();
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
	// && props.containsKey(PROP_PHYSICAL_LOCATION);
    }

}
