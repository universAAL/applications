package org.universAAL.agenda.gui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

    // private static final String BUNDLE_NAME = "org.universAAL.agenda.gui.messages_" + Locale.getDefault().getLanguage().toLowerCase(); //$NON-NLS-1$

    private static final String BUNDLE_NAME = "org.universAAL.agenda.gui.messages_"
	    + System.getProperty("user.language", "en");
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
	    .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
	try {
	    return RESOURCE_BUNDLE.getString(key);
	} catch (MissingResourceException e) {
	    return "ff!" + key + '!';
	}
    }
}
