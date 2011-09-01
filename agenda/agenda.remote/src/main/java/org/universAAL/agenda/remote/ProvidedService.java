/*
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
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

import org.universAAL.agenda.ont.service.CalendarUIService;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

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
    private static Hashtable serverPEditorRestrictions = new Hashtable();

    static {
	register(ProvidedService.class);

	addRestriction(
		(Restriction) CalendarUIService.getClassRestrictionsOnProperty(
			CalendarUIService.PROP_CONTROLS).copy(),
		new String[] { CalendarUIService.PROP_CONTROLS },
		serverPEditorRestrictions);
	
System.err.println("prije registracije usluge");
	profiles[0] = InitialServiceDialog.createInitialDialogProfile(
		CalendarUIService.MY_URI, "http://www.tsb.upv.es",
		"Calendar Web UI", SERVICE_START_UI);
	System.err.println("Nakon registracije usluge");
	System.err.println("registrirao sam uslugu:"+profiles[0]);
    }

    private ProvidedService(String uri) {
	super(uri);
    }

    protected Hashtable getClassLevelRestrictions() {
	return serverPEditorRestrictions;
    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return true;
    }
}
