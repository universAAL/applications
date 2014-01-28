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
package org.universaal.ltba.ui.activator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.Timer;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universaal.ltba.ui.impl.common.LTBACaller;
import org.universaal.ltba.ui.impl.common.SharedResources;
import org.universaal.ltba.ui.impl.reports.DayUI;
import org.universaal.ltba.ui.impl.reports.MonthUI;
import org.universaal.ltba.ui.impl.reports.WeekUI;

/**
 * User interface provider.
 * 
 * @author mllorente
 * 
 */
public class MainLTBAUIProvider extends UICaller {

	static final String MY_UI_NAMESPACE = "http://www.tsbtecnolgias.es/LTBAUICaller.owl#";
	public static final String SUBMISSION_ON = MY_UI_NAMESPACE + "submissionOn";
	static final String SUBMISSION_OFF = MY_UI_NAMESPACE + "submissionOff";
	static final String SUBMISSION_SHOW = MY_UI_NAMESPACE + "submissionDay";
	static final String SUBMISSION_WEEK = MY_UI_NAMESPACE + "submissionWeek";
	static final String SUBMISSION_MONTH = MY_UI_NAMESPACE + "submissionMonth";

	public final String HOME_FOLDER = "ltba.manager";
	public final String PROPERTIES_FILE = "ltba.properties";
	public final String PROPERTY_MAX_TIME_DISCONNECTED = "max.time.disconnected";

	private static int timeToConnect = SharedResources.OFFTIME;
	private static Timer disconnectionTimer;

	static final String USER_INPUT_SELECTED_RANGE = MY_UI_NAMESPACE
			+ "selectedRange";

	static ModuleContext myModuleContext;

	public MainLTBAUIProvider(ModuleContext context) {
		super(context);
		myModuleContext = context;
		SharedResources.context = context;
	}

	@Override
	public void communicationChannelBroken() {

	}

	public void dialogAborted(String dialogID) {

	}

	public Form showDialog(Resource inputUser) {
		SharedResources.lastUser = inputUser;
		Form f = Form.newDialog("LTBA UI", new Resource());
		new SimpleOutput(f.getIOControls(), null, null,
				SharedResources.getMainMessage());
		// new SimpleOutput(f.getIOControls(), null, null, "NANONANO");
		// new SimpleOutput(f.getIOControls(), null, null, "TETETETE");

		// Submit s = new Submit(f.getIOControls(), new Label("Activate LTBA",
		// null), "ltbaButton");

		if (SharedResources.ltbaIsOn) {
			new Submit(f.getIOControls(), new Label(">LTBA ON<", null),
					SUBMISSION_ON);
			new Submit(f.getIOControls(), new Label("LTBA OFF", null),
					SUBMISSION_OFF);
		} else {

			new Submit(f.getIOControls(), new Label("LTBA ON", null),
					SUBMISSION_ON);
			new Submit(f.getIOControls(), new Label(">LTBA OFF<", null),
					SUBMISSION_OFF);
		}
		// new Submit(f.getSubmits(), new Label("Day Report", null),
		// SUBMISSION_SHOW);
		// new Submit(f.getSubmits(), new Label("Week Report", null),
		// SUBMISSION_WEEK);
		// new Submit(f.getSubmits(), new Label("Month Report", null),
		// SUBMISSION_MONTH);
		if (inputUser == null) {
			System.out.println("NULL INPUT USER");
		}
		new Submit(f.getSubmits(), new Label("Done", null), "doneForm");

		/**
		 * TODO There is a bug. Despite when the service request is created, a
		 * user is specified, when this call is handled, the user extracted from
		 * the object of the call is null. This workaround will allow keep the
		 * service working, but the users must be correctly handled in the
		 * future.
		 */
		if (inputUser == null) {
			inputUser = new AssistedPerson(
					Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
		}
		UIRequest req = new UIRequest(inputUser, f, LevelRating.none,
				Locale.ENGLISH, PrivacyLevel.insensible);
		this.sendUIRequest(req);
		SharedResources.thisform = f;
		return f;
	}

	public void handleUIResponse(UIResponse uir) {
		if (SharedResources.lastUser == null) {
			SharedResources.lastUser = new AssistedPerson(
					Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
		}
		if (uir != null) {
			if (SUBMISSION_ON.equals(uir.getSubmissionID())) {
				LogUtils.logDebug(myModuleContext, getClass(),
						"handleUIResponse", "SUBMISSION_ON");
				switchOnOperations();
				new LTBACaller(myModuleContext, SharedResources.lastUser);
				LTBACaller.switchOn();
			} else if (SUBMISSION_OFF.equals(uir.getSubmissionID())) {
				LogUtils.logDebug(myModuleContext, getClass(),
						"handleUIResponse", "SUBMISSION_OFF");
				switchOffOperations(this);
				new LTBACaller(myModuleContext, SharedResources.lastUser);
				LTBACaller.switchOff();
			}
			// yoda conditions everywhere
			if ("doneForm".equals(uir.getSubmissionID())) {
				// startMainDialog();
			} else {
				showDialog(SharedResources.lastUser);
			}
		}
	}

	public void switchOffOperations(MainLTBAUIProvider mainLTBAUIProvider) {
		initDisconnectionPeriod();
		SharedResources.ltbaIsOn = false;
		setReconnectionTime();
	}

	public void switchOnOperations() {
		disconnectionTimer.stop();
		disconnectionTimer = null;
		timeToConnect = SharedResources.OFFTIME;
		SharedResources.ltbaIsOn = true;
	}

	/**
	 * Set the re-connection time. Configures the hour shown to the user.
	 */
	private void setReconnectionTime() {

		Date disconnection = new Date();
		Date reconnection = new Date(disconnection.getTime()
				+ (1000 * 60 * getTimeDisconected()));
		LogUtils.logDebug(myModuleContext, getClass(), "setReconnectionTime",
				"DISCONNECTION->" + disconnection);
		LogUtils.logDebug(myModuleContext, getClass(), "setReconnectionTime",
				"RECONNECTION->" + reconnection);
		Calendar disconnectedTime = Calendar.getInstance();
		disconnectedTime.setTime(disconnection);
		Calendar reconnectionTime = Calendar.getInstance();
		reconnectionTime.setTime(reconnection);
		int recHour = reconnectionTime.get(Calendar.HOUR_OF_DAY);
		int recMinutes = reconnectionTime.get(Calendar.MINUTE);
		String recTimeFormatted;
		if (recHour < 10) {
			recTimeFormatted = "0" + recHour;
		} else {
			recTimeFormatted = "" + recHour;
		}
		if (recMinutes < 10) {
			recTimeFormatted = recTimeFormatted + ":0" + recMinutes;
		} else {
			recTimeFormatted = recTimeFormatted + ":" + recMinutes;
		}
		SharedResources.reconnectHour = recTimeFormatted;

		if (reconnectionTime.get(Calendar.DAY_OF_MONTH) > disconnectedTime
				.get(Calendar.DAY_OF_MONTH)) {
			SharedResources.reconnectToday = false;
		} else {
			SharedResources.reconnectToday = true;
		}
	}

	/**
	 * Timer that will reconnect automatically the LTBA service after some
	 * defined hours.
	 */
	public void initDisconnectionPeriod() {
		/**
		 * Timer update period -> 1 minute timeToConnect defines the number of
		 * minutes remaining for the reconnection
		 */
		disconnectionTimer = new Timer(60 * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogUtils.logDebug(myModuleContext, getClass(),
						"actionPerformed", "timeToConnect->" + timeToConnect
								+ " mintues");
				if (timeToConnect == 0) {
					new LTBACaller(myModuleContext, null);
					SharedResources.ltbaIsOn = true;
					LTBACaller.switchOn();
					UIResponse uir = new UIResponse();
					// NEW WINDOW WITH LTBA ON
					handleUIResponse(new UIResponse(SharedResources.lastUser,
							null, new Submit(SharedResources.thisform
									.getIOControls(), new Label(">LTBA ON<",
									null), SUBMISSION_ON)));
					SharedResources.ltbaIsOn = true;
					showDialog(SharedResources.lastUser);
					disconnectionTimer.stop();
					timeToConnect = SharedResources.OFFTIME;
				}
				timeToConnect--;// 1 minute left
			}
		});
		disconnectionTimer.start();
	}

	private long getTimeDisconected() {
		String home = new BundleConfigHome(HOME_FOLDER).getAbsolutePath();
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(home + "/" + PROPERTIES_FILE));
			return Long.parseLong(properties
					.getProperty(PROPERTY_MAX_TIME_DISCONNECTED));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void dialogAborted(String arg0, Resource arg1) {
		// TODO Auto-generated method stub
		
	}
}
