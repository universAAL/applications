/*
	Copyright 2012 CERTH, http://www.certh.gr
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.ontology.biomedicalsensors;

/**
 * Ontological enumeration of possible Zephyr sensors.
 * 
 * @author billk, joemoul
 * 
 */
public class Zephyr extends SensorType {

    public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
	    + "Zephyr";

    public static final Zephyr zephyr = new Zephyr();

    public String getClassURI() {
	return MY_URI;
    }

    public static final Zephyr valueOf(String rqzephyrName) {
	if (rqzephyrName == null)
	    return null;

	if (rqzephyrName.startsWith(BiomedicalSensorsOntology.NAMESPACE))
	    rqzephyrName = rqzephyrName
		    .substring(BiomedicalSensorsOntology.NAMESPACE.length());

	if (rqzephyrName.equals(MY_URI))
	    return new Zephyr();

	return null;
    }

    private Zephyr() {
	super(MY_URI);

    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_OPTIONAL;

    }

    public boolean isWellFormed() {
	return true;
    }

}
