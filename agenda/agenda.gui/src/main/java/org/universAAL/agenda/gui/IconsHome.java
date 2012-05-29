package org.universAAL.agenda.gui;

import java.io.File;
import java.util.Locale;

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

	File file = new File(ICON_PATH_PREFIX);

	// if asked localization does not exist return english
	if (!file.exists()) {
	    ICON_PATH_PREFIX = "/lang/icons_en";
	} else {
	    System.out.println("Agenda.Gui.IconsHome: " + ICON_PATH_PREFIX
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
