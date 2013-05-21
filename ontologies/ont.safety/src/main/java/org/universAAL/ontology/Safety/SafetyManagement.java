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

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.Safety.SafetyOntology;

/**
 * @author dimokas
 * 
 */
public class SafetyManagement extends Service {
    public static final String MY_URI = SafetyOntology.NAMESPACE
	    + "SafetyManagement";
    public static final String PROP_CONTROLS = SafetyOntology.NAMESPACE
	    + "controls";

    public SafetyManagement() {
	super();
    }

    public SafetyManagement(String uri) {
	super(uri);
    }

    public String getClassURI() {
	return MY_URI;
    }

    public int getPropSerializationType(String propURI) {
	return PROP_CONTROLS.equals(propURI) ? PROP_SERIALIZATION_FULL : super
		.getPropSerializationType(propURI);
    }

    public boolean isWellFormed() {
	return true;
    }
}
