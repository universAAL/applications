/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.universAAL.middleware.container.ModuleContext;
import org.universaal.ltba.ui.activator.MainLTBAUIProvider;

public class SharedResources {

	public static final String DROOLS_UI_NAMESPACE = "http://www.tsbtecnologias.es/DroolsUI.owl#";
	public static ModuleContext moduleContext;
	static LTBAUIProvider serviceProvider;
	public static boolean ltbaIsOn = true;
	public static boolean processingReport = false;
	/**
	 * Period (in minutes) off switching off. After that the LTBA will
	 * automatically restart with normal operation.
	 */
	public static int OFFTIME = 5; // 8 hours
	/**
	 * Time (in minutes) to finish the disconnection period.
	 */
	private int timeToConnect = OFFTIME;
	private Timer disconnectionTimer;

	// static ServiceProvider serviceProvider;
	static MainLTBAUIProvider uIProvider;

	// static final AssistedPerson testUser = new AssistedPerson(
	// Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start() throws Exception {
		new Thread() {
			public void run() {
				SharedResources.serviceProvider = new LTBAUIProvider(
						moduleContext);
				SharedResources.uIProvider = new MainLTBAUIProvider(
						moduleContext);
			}
		}.start();
	}

	public void initDisconnectionPeriod() {
		disconnectionTimer = new Timer(60 * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (timeToConnect == 0) {
					new LTBACaller(moduleContext, null);
					LTBACaller.switchOn();
				}
				timeToConnect--;// 1 minute left
			}
		});
		disconnectionTimer.start();
	}

}
