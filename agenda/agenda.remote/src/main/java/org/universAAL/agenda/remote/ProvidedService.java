/**
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
	2012 Ericsson Nikola Tesla d.d., www.ericsson.com/hr
	
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
package org.universAAL.agenda.remote;

import java.util.Hashtable;

import org.universAAL.ontology.agenda.service.CalendarUIService;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.agenda.remote.osgi.Activator;

/**
 * 
 * @author alfiva
 * @author eandgrg
 */
public class ProvidedService extends CalendarUIService {
    public static final String CALENDAR_UI_NAMESPACE = "http://ui.calendar.universAAL.org/CalendarUIService.owl#";
    public static final String MY_URI = CALENDAR_UI_NAMESPACE
	    + "CalendarWebUIService";

    static final String SERVICE_START_UI = CALENDAR_UI_NAMESPACE
	    + "startUserInterfaceWeb";

    static final String INPUT_USER = CALENDAR_UI_NAMESPACE + "iUser";

    private static final int PROVIDED_SERVICES = 1; // The number of services
    // provided by this class

    static final ServiceProfile[] profiles = new ServiceProfile[PROVIDED_SERVICES];
    private static Hashtable<?, ?> serverPEditorRestrictions = new Hashtable();

    static {
	OntologyManagement.getInstance().register(
		Activator.getMcontext(),
		new SimpleOntology(MY_URI, CalendarUIService.MY_URI,
			new ResourceFactory() {
			    
			    public Resource createInstance(String classURI,
				    String instanceURI, int factoryIndex) {
				return new ProvidedService(instanceURI);
			    }
			}));

	addRestriction((MergedRestriction) CalendarUIService
		.getClassRestrictionsOnProperty(CalendarUIService.MY_URI,
			CalendarUIService.PROP_CONTROLS).copy(),
		new String[] { CalendarUIService.PROP_CONTROLS },
		serverPEditorRestrictions);

	profiles[0] = InitialServiceDialog.createInitialDialogProfile(
		CalendarUIService.MY_URI, "http://www.tsb.upv.es",
		"Calendar Web UI", SERVICE_START_UI);
    }

    private ProvidedService(String uri) {
	super(uri);
    }

    protected Hashtable<?, ?> getClassLevelRestrictions() {
	return serverPEditorRestrictions;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.ontology.agenda.service.CalendarUIService#
     * getPropSerializationType(java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.ontology.agenda.service.CalendarUIService#isWellFormed()
     */
    public boolean isWellFormed() {
	return true;
    }
}
