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
