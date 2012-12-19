/*******************************************************************************
 * Copyright 2012 Ericsson Nikola Tesla d.d.
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
 ******************************************************************************/
package org.universAAL.ontology;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.ExternalCalendar;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.TimeInterval;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.ontology.agenda.service.CalendarUIService;

/**
 * 
 * @author eandgrg
 * 
 */
public class AgendaOntologyFactory extends ResourceFactoryImpl {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.rdf.impl.ResourceFactoryImpl#createInstance
     * (java.lang.String, java.lang.String, int)
     */
    public Resource createInstance(String classURI, String instanceURI,
	    int factoryIndex) {

	switch (factoryIndex) {
	case 0:
	    return new Calendar(instanceURI);
	case 1:
	    return new Reminder(instanceURI);
	case 2:
	    return new Event(instanceURI);
	case 3:
	    return new EventDetails(instanceURI);
	case 4:
	    return new TimeInterval(instanceURI);
	case 5:
	    return new CalendarAgenda(instanceURI);
	case 6:
	    return new CalendarUIService(instanceURI);
	case 7:
	    return new ExternalCalendar(instanceURI);

	}
	return null;
    }
}
