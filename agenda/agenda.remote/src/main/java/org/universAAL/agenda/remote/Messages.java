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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    // private static final String BUNDLE_NAME = "org.universAAL.agenda.remote.messages_" + Locale.getDefault().getLanguage().toLowerCase(); //$NON-NLS-1$
    // new, with system props
    private static final String BUNDLE_NAME = "org.universAAL.agenda.remote.messages_"
	    + System.getProperty("user.language", "en");
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
	    .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
	try {
	    return RESOURCE_BUNDLE.getString(key);
	} catch (MissingResourceException e) {
	    return '!' + key + '!';
	}
    }
}
