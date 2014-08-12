package org.universAAL.AALapplication.hwo.engine;
//Class that provides String messages in different languages.
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static ResourceBundle RESOURCE_BUNDLE;
	
	static{
		try{
		RESOURCE_BUNDLE = ResourceBundle.getBundle("org.universAAL.AALapplication.hwo.messages",Locale.getDefault());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

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
