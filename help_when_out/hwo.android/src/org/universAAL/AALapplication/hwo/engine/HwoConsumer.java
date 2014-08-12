package org.universAAL.AALapplication.hwo.engine;

// Class that listen to the context bus for events linked to Hwo.
// This class has to be put in the servlet

import java.util.Date;

import org.universAAL.AALapplication.hwo.BackgroundService;
import org.universAAL.AALapplication.hwo.model.AssistedPerson;
import org.universAAL.AALapplication.hwo.model.Point;
import org.universAAL.AALapplication.hwo.model.User;

import android.util.Log;

public class HwoConsumer {

	private static final String TAG = "HwoConsumer";
	private long prevtime;
	private Point prevpoint;
	public boolean noresponsefromuser = false; // With this, when an alert has
												// to be sent, we can decide
												// whether ask the user if he's
												// alright or just alert the
												// caregivers directly.

	// *****************************************************************
	// Listening to the bus
	// *****************************************************************

	public void handleContextEvent(AssistedPerson subj, String pred, Point p) {

		prevtime = new Date().getTime();
		User dummyuser;
		Log.i(TAG,  "Received context event:\n"
						+ "    Subject     =" + subj.MY_URI + "\n"
						+ "    Subject type=" + subj.getClassURI()
						+ "\n" + "    Predicate   =" + pred
						+ "\n" + "    Object      =" + p);

		System.out.println("Las coordenadas del evento son " + p.getX() + ", "
				+ p.getY() + " Y el tiempo: " + new Date().getTime());
		Log.i(TAG, "enviando al Wandering detector");
		prevpoint = p;
		String wd = BackgroundService.wanderingdetector.isWandering(p,
				new Date().getTime());
		if (wd == "NOTYET")
			return;
		if (BackgroundService.scallee.user == null)
			dummyuser = new User("DummyUser"); // To avoid exceptions
		else
			dummyuser = BackgroundService.scallee.user;
		if (noresponsefromuser == false) {
			Log.d(TAG, "debug We ask the user");
			BackgroundService.uimanager.showalertForm(dummyuser, wd); // TODO : this has
																// to be sent
																// through the
																// context bus,
																// instead of
																// calling the
																// method,
																// because
																// uimanager is
																// in the mobile
																// device
		} else {
			Log.d(TAG, "debug We don't ask the user");
			BackgroundService.wanderingdetector.alert_yes();

		}

	}

	public void startWanderingThread() { // starting the thread that will check
											// the GPS to make sure that every
											// minute Wandering detector is
											// called. This way, if the GPS
											// doesn't send "change position"
											// events (this can happen if the
											// user stops completely or if its
											// location is get by WIFI signal),
											// we keep activating the
											// WanderingDetector,
											// so it can check is everything is
											// Ok.
		prevtime = new Date().getTime();
		prevpoint = null;

		CheckThread checklauncher = new CheckThread();
		Log.d(TAG, "lanzando checklauncher");
		checklauncher.start();

	}

	public void communicationChannelBroken() {

	}

	public class CheckThread extends Thread {

		public void run() {
			long thistime;

			while (true) {
				try {
					Log.d(TAG, "going into sleep");
					sleep(60000);
					Log.d(TAG, "going out of sleep");
				} catch (InterruptedException e) {

				}
				thistime = new Date().getTime();
				if (((thistime - prevtime) > 60000) && (prevpoint != null)) {
					Log.d(TAG,
							"A minute has passed without GPS events, sending again last known position.");
					BackgroundService.wanderingdetector
							.isWandering(prevpoint, thistime);
				}

			}

		}
	}
}
