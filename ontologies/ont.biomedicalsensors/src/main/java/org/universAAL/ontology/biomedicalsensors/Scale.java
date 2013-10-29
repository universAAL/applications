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
 * Ontological enumeration of possible Scale (sensors).
 * 
 * @author joemoul, billyk
 * 
 */
public class Scale extends SensorType {

    public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
	    + "Scale";

    private static final String name = "Scale";
    public static final Scale scale = new Scale();

    public String getClassURI() {
	return MY_URI;
    }

    public static final Scale valueOf(String rqname) {
	if (rqname == null)
	    return null;

	if (rqname.startsWith(BiomedicalSensorsOntology.NAMESPACE))
	    rqname = rqname.substring(BiomedicalSensorsOntology.NAMESPACE
		    .length());

	if (rqname.equals(name))
	    return new Scale();

	return null;
    }

    private Scale() {
	super(BiomedicalSensorsOntology.NAMESPACE + name);

    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_OPTIONAL;

    }

    public boolean isWellFormed() {
	return true;
    }

}
