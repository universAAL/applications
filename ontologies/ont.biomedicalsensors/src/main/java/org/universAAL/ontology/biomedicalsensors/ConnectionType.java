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

import org.universAAL.middleware.owl.ManagedIndividual;

public abstract class ConnectionType extends ManagedIndividual {
    public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
	    + "connectionType";

    protected ConnectionType(String uri) {
	super(uri);
    }

    public String getClassURI() {
	return MY_URI;
    }
}
