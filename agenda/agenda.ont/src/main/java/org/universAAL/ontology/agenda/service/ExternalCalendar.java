package org.universAAL.ontology.agenda.service;

import org.universAAL.ontology.agenda.AgendaOntology;

/**
 * @author kagnantis
 * 
 */
public class ExternalCalendar {
    public static final String MY_URI = AgendaOntology.NAMESPACE
	    + "externalCalendar";

    // do nothing at the moment


    public String getClassURI() {
	return MY_URI;
    }
}
