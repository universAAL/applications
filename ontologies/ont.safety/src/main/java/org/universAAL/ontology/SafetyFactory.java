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

package org.universAAL.ontology;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.ontology.Safety.Door;
import org.universAAL.ontology.Safety.HumiditySensor;
import org.universAAL.ontology.Safety.LightSensor;
import org.universAAL.ontology.Safety.MotionSensor;
import org.universAAL.ontology.Safety.Notification;
import org.universAAL.ontology.Safety.SafetyManagement;
import org.universAAL.ontology.Safety.SmokeSensor;
import org.universAAL.ontology.Safety.TemperatureSensor;
import org.universAAL.ontology.Safety.Window;
import org.universAAL.ontology.Safety.FanHeater;
import org.universAAL.ontology.Safety.DoorBell;

/**
 * @author dimokas
 * 
 */

public class SafetyFactory implements ResourceFactory {

    public SafetyFactory() {
    }

    public Resource createInstance(String classURI, String instanceURI,
	    int factoryIndex) {
		switch (factoryIndex) {
		case 0:
		    return new DoorBell(instanceURI);
		case 1:
		    return new Door(instanceURI);
		case 2:
		    return new HumiditySensor(instanceURI);
		case 3:
		    return new LightSensor(instanceURI);
		case 4:
		    return new MotionSensor(instanceURI);
		case 5:
		    return new SmokeSensor(instanceURI);
		case 6:
		    return new TemperatureSensor(instanceURI);
		case 7:
		    return new Window(instanceURI);
		case 8:
		    return new FanHeater(instanceURI);
		case 9:
		    return new Notification(instanceURI);
		case 10:
		    return new SafetyManagement(instanceURI);
		}

		return null;
    }
}
