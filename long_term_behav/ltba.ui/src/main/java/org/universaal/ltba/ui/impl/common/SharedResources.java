/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnolog�as para la Salud y el Bienestar
	
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
package org.universaal.ltba.ui.impl.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.rdf.Form;

public class SharedResources {

	public static final String DROOLS_UI_NAMESPACE = "http://www.tsbtecnologias.es/DroolsUI.owl#";
	public static boolean ltbaIsOn = true;
	public static boolean processingReport = false;
	public static Resource lastUser = new Resource();
	public static ModuleContext context;
	public static String reconnectHour;
	/**
	 * Period (in minutes) off switching off. After that the LTBA will
	 * automatically restart with normal operation.
	 */
	public static int OFFTIME = getOfftime(); // minutes
	public static Form thisform = null;
	public static boolean reconnectToday = true;
	/**
	 * Time (in minutes) to finish the disconnection period.
	 */

	public static final String HOME_FOLDER = "ltba.manager";
	public static final String PROPERTIES_FILE = "ltba.properties";
	public static final String MESSAGES_FILE = "ltba.messages";

	private static String getSetupFileName() {
		return new BundleConfigHome(HOME_FOLDER).getAbsolutePath();
	}

	private static int getOfftime() {
		Properties properties = new Properties();
		try {
			String home = getSetupFileName();
			properties.load(new FileInputStream(home + "/" + PROPERTIES_FILE));
			return Integer.parseInt(properties
					.getProperty("max.time.disconnected"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	static public String getDisconnectedTimeString() {
		Properties properties = new Properties();
		try {
			String home = getSetupFileName();
			properties.load(new FileInputStream(home + "/" + PROPERTIES_FILE));
			return properties.getProperty("max.time.disconnected");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public String getLanguage() {
	//	Properties properties = new Properties();
		try {
//			String home = getSetupFileName();
//			properties.load(new FileInputStream(home + "/" + PROPERTIES_FILE));
//			return properties.getProperty("language");
			
			return System.getProperty("user.language", "en");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static public String getMainMessage() {
		String lan = getLanguage();
		String home = getSetupFileName();
		LogUtils.logDebug(context, SharedResources.class, "getMainMessage", lan);
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(home + "/" + MESSAGES_FILE));
			LogUtils.logDebug(context, SharedResources.class, "getMainMessage",
					"LTBA is ON ->" + ltbaIsOn);
			if (ltbaIsOn) {
				LogUtils.logDebug(context, SharedResources.class,
						"getMainMessage", "ASKING FOR ltba.ui.connected.main."
								+ lan.toLowerCase());
				LogUtils.logDebug(
						context,
						SharedResources.class,
						"getMainMessage",
						properties.getProperty("ltba.ui.connected.main."
								+ lan.toLowerCase()));
				return properties.getProperty("ltba.ui.connected.main."
						+ lan.toLowerCase());
			} else {
				if (reconnectToday) {
					return properties.getProperty("ltba.ui.disconnected.main."
							+ lan.toLowerCase())
							+ reconnectHour
							+ " "
							+ properties
									.getProperty("ltba.ui.disconnected.today."
											+ lan.toLowerCase());
				} else {
					return properties.getProperty("ltba.ui.disconnected.main."
							+ lan.toLowerCase())
							+ reconnectHour
							+ " "
							+ properties
									.getProperty("ltba.ui.disconnected.tomorrow."
											+ lan.toLowerCase());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "No message";
	}
}
