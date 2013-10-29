package org.universAAL.agendaEventSelectionTool.server;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

    // oldest
    // private static final String BUNDLE_NAME = "org.universAAL.agendaEventSelectionTool.server.messages_" + Locale.getDefault().getLanguage().toLowerCase(); //$NON-NLS-1$
    // private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
    // .getBundle(BUNDLE_NAME);

    // older
    // private static final String BUNDLE_NAME =
    // "org.universAAL.agendaEventSelectionTool.server.messages";
    // private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
    // .getBundle(BUNDLE_NAME, Locale.getDefault());

    // new, with system props
    private static final String BUNDLE_NAME = "org.universAAL.agendaEventSelectionTool.server.messages_"
	    + System.getProperty("user.language", "en");
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
	    .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    /**
     * 
     * @param key
     *            key for which the value is needed from corresponding
     *            properties file
     * @return value assigned to the given key
     */
    public static String getString(String key) {
	try {
	    return RESOURCE_BUNDLE.getString(key);
	} catch (MissingResourceException e) {
	    return '!' + key + '!';
	}
    }
}
