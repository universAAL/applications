package org.universAAL.agenda.gui;

import java.util.Locale;
import org.universAAL.agenda.gui.osgi.Activator;

/**
 * Singleton containg home path for icons.
 * 
 * @author eandgrg
 * 
 */
public class IconsHome {

    protected String ICON_PATH_PREFIX = null;

    // disable instantiation
    protected IconsHome() {
	ICON_PATH_PREFIX = "/lang/icons_"
		+ Locale.getDefault().getLanguage().toLowerCase();

	// if asked localization does not exist use english
	if (Activator.getBundleContext().getBundle().getResource(ICON_PATH_PREFIX) == null) {
	    ICON_PATH_PREFIX = "/lang/icons_en";
	    System.err
		    .println("Agenda.Gui.IconsHome file does not exists->use ENG");
	} else {
	    System.err.println("Agenda.Gui.IconsHome: " + ICON_PATH_PREFIX
		    + " exists.");
	}

    }

    private static IconsHome iconsHome;

    protected static IconsHome getInstance() {
	if (iconsHome == null)
	    iconsHome = new IconsHome();
	return iconsHome;
    }

    public static String getIconsHomePath() {
	return getInstance().ICON_PATH_PREFIX;
    }
}
