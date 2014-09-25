package org.universAAL.AALapplication.hwo;

import org.universAAL.AALapplication.hwo.engine.DataStorage;
import org.universAAL.AALapplication.hwo.engine.HwoConsumer;
import org.universAAL.AALapplication.hwo.engine.SCallee;
import org.universAAL.AALapplication.hwo.engine.UImanager;
import org.universAAL.AALapplication.hwo.engine.WanderingDetector;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class BackgroundService extends Service {

	private static final String TAG = "BackgroundService";
	public static WanderingDetector wanderingdetector;
	public static HwoConsumer hwoconsumer;
	public static UImanager uimanager;
	public static SCallee scallee;
	public static Context activityHandle;
	private static final int ONGOING_NOTIFICATION = 2597353; // TODO Random one?

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Check! Supposedly, this starts as foreground but without
		// notification. Not on 4.0 anymore
		@SuppressWarnings("deprecation")
		Notification notif = new Notification(0, null,
				System.currentTimeMillis());
		notif.flags |= Notification.FLAG_NO_CLEAR;
		startForeground(ONGOING_NOTIFICATION, notif);

		//Log.i(TAG, "1 Starting Help when Outside Mobile Module");
		activityHandle = this;
		uimanager = new UImanager();
		scallee = new SCallee();
		//Log.d(TAG, "2 ACT: SCallee started");
		hwoconsumer = new HwoConsumer();
		//Log.d(TAG, "3 ACT:  hwoconsumer created");
		DataStorage dataStorage = DataStorage.getInstance();
		//Log.d(TAG, "4 ACT: dataStorage created");
		wanderingdetector = new WanderingDetector(5, 5); // This has to be put
															// in the servlet
		//Log.d(TAG, "5 ACT: wandering created");

		//Log.i(TAG, "6 ACT: Started Help when Outside Mobile Module");
		scallee.Location(null); // desactivado GPS mientras pruebas con SMS
		//Log.d(TAG, "7 ACT: GPS thread launched");
		hwoconsumer.startWanderingThread();
		//Log.d(TAG, "8 ACT: check GPS thread launched  ");
		//Log.i(TAG, "9 ACT: All classes initialized");

		Toast.makeText(getApplicationContext(), wanderingdetector.getDistance(), Toast.LENGTH_LONG).show();
		//Button p1_button = (Button)findViewById(R.id.panic);
		//p1_button.setText("Some text");
		
		return START_STICKY;
		
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
