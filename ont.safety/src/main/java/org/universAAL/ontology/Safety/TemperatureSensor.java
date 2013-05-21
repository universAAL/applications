/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.ontology.Safety;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.phThing.Device;


/**
 * @author dimokas
 *
 * Temperature sensor Ontology
 */
public class TemperatureSensor extends Device {
	public static final String MY_URI = SafetyOntology.NAMESPACE + "Temperature"; 
	//public static final String PROP_DEVICE_LOCATION;
	public static final String PROP_DEVICE_STATUS = SafetyOntology.NAMESPACE + "deviceStatus";
	public static final String PROP_TEMPERATURE = SafetyOntology.NAMESPACE + "homeTemperature";
	
	public TemperatureSensor(){
		super();
	}
	
	public TemperatureSensor(String uri) {
		super(uri);				
	}
		
    public String getClassURI() {
    	return MY_URI;
    }
	
	public static String[] getStandardPropertyURIs() {
		return new String[] {PROP_PHYSICAL_LOCATION, PROP_DEVICE_STATUS, PROP_TEMPERATURE};
	}
/*	
	public static String getRDFSComment() {
		return "A temperature sensor";
	}
	
	public static String getRDFSLabel() {
		return "Temperature";
	}
*/		
	public TemperatureSensor(String uri, Location loc) {
		super(uri);
		if (loc == null)
			   throw new IllegalArgumentException();
	
		props.put(PROP_DEVICE_STATUS, new Integer(0));
		props.put(PROP_PHYSICAL_LOCATION, loc);
	}

	public TemperatureSensor(String uri, Location loc, float temperature) {
		super(uri);
		if (loc == null)
			   throw new IllegalArgumentException();
	
		props.put(PROP_DEVICE_STATUS, new Integer(0));
		props.put(PROP_PHYSICAL_LOCATION, loc);
		props.put(PROP_TEMPERATURE, new Float(temperature));
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
	
	public void setTemperature(float temperature){
		props.put(PROP_TEMPERATURE, new Float(temperature));
	}
	
	public float getTemperature() {
		Float i = (Float) props.get(PROP_TEMPERATURE);
		return (i == null) ? -1 : i.floatValue();
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
