package org.universAAL.ontology.agenda;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    //private static final String BUNDLE_NAME = "org.universAAL.ontology.messages_" + Locale.getDefault().getLanguage().toLowerCase(); //$NON-NLS-1$
    // private static final String BUNDLE_NAME =
    // "org.universAAL.ontology.messages_en";

    private static final String BUNDLE_NAME = "org.universAAL.ontology.agenda.messages";
    
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
	    .getBundle(BUNDLE_NAME,  Locale.getDefault()); 

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
