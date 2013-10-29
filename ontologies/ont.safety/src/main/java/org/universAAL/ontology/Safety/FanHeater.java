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
 * Fan Heater Device Ontology
 */

public class FanHeater extends Device {

	public static final String MY_URI = SafetyOntology.NAMESPACE + "FanHeater"; 
	public static final String PROP_IS_ENABLED = SafetyOntology.NAMESPACE + "isHeatingEnabled";
	
	public FanHeater(){
		super();
	}
	
	public FanHeater(String uri) {
		super(uri);				
	}
		
    public String getClassURI() {
    	return MY_URI;
    }
	
	public static String[] getStandardPropertyURIs() {
		return new String[] {PROP_PHYSICAL_LOCATION, PROP_IS_ENABLED};
	}

	public FanHeater(String uri, Location loc) {
		super(uri);
		if (loc == null)
			   throw new IllegalArgumentException();
	
		props.put(PROP_PHYSICAL_LOCATION, loc);
	}

	public FanHeater(String uri, Location loc, boolean isEnabled) {
		super(uri);
		if (loc == null)
			   throw new IllegalArgumentException();
	
		props.put(PROP_PHYSICAL_LOCATION, loc);
		props.put(PROP_IS_ENABLED, new Boolean(isEnabled));
	}

	public Location getDeviceLocation() {
		return (Location) props.get(PROP_PHYSICAL_LOCATION);
	}

	public void setIsEnabled(boolean isEnabled){
		props.put(PROP_IS_ENABLED, new Boolean(isEnabled));
	}
	
	public boolean getIsEnabled() {
		Boolean b = (Boolean) props.get(PROP_IS_ENABLED);
		return b.booleanValue();
    }

	public void setDeviceLocation(Location l) {
		if (l != null)
		    props.put(PROP_PHYSICAL_LOCATION, l);
    }
	
	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
	}
		
	public boolean isWellFormed() {
		return true;
	}
}
