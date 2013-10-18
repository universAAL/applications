/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
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
package org.universAAL.ltba.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.universAAL.middleware.container.osgi.util.BundleConfigHome;

public class Setup {
	public static final String HOME_FOLDER = "ltba.manager";
	public static final String PROPERTIES_FILE = "ltba.properties";

	private static String getSetupFileName() {
		return new BundleConfigHome(HOME_FOLDER).getAbsolutePath();
	}

	static public String getServerIP() {
		Properties properties = new Properties();
		try {
			String home = getSetupFileName();
			properties.load(new FileInputStream(home + "/" + PROPERTIES_FILE));
			return properties.getProperty("nomhad.ip");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public String getUserCode() {
		Properties properties = new Properties();
		try {
			String home = getSetupFileName();
			properties.load(new FileInputStream(home + "/" + PROPERTIES_FILE));
			return properties.getProperty("nomhad.user.code");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public String getUserPassword() {
		Properties properties = new Properties();
		try {
			String home = getSetupFileName();
			properties.load(new FileInputStream(home + "/" + PROPERTIES_FILE));
			return properties.getProperty("nomhad.user.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
