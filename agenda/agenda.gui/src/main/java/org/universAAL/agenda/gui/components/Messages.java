package org.universAAL.agenda.gui.components;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

class Messages {
    // private static final String BUNDLE_NAME = "org.universAAL.agenda.gui.components.messages_" + Locale.getDefault().getLanguage().toLowerCase(); //$NON-NLS-1$

    // new, with system props
    private static final String BUNDLE_NAME = "org.universAAL.agenda.gui.components.messages_"
	    + System.getProperty("user.language", "en");
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
	    .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
	try {
	    return RESOURCE_BUNDLE.getString(key);
	} catch (MissingResourceException e) {
	    return "!!!x" + key + '!';
	}
    }
}
