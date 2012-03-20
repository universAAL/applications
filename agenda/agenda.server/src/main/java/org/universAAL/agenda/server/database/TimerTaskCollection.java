package org.universAAL.agenda.server.database;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskCollection {
    Map eventReminders = new HashMap(5);

    private void addReminderTask(int eventId, TimerTask task) {

    }

    private boolean updateReminderTask(int eventId, TimerTask task) {
	return true;
    }

    public boolean remindertTaskExists(int eventId) {
	return eventReminders.containsKey(new Integer(eventId));
    }

    public boolean addOrUpdateReminderTask(int eventId, TimerTask task) {
	eventReminders.put(new Integer(eventId), task);
	return true;
    }

    public static void main(String[] str) {
	TimerTaskCollection ttc = new TimerTaskCollection();
	TimerTask tt1 = new TimerTask() {
	    public void run() {
		System.out.println("1. in run...");
	    }
	};
	TimerTask tt2 = new TimerTask() {
	    public void run() {
		System.out.println("2. in run...");
	    }
	};

	TimerTask tt3 = new TimerTask() {
	    public void run() {
		System.out.println("3. in run...");
	    }
	};

	// ttc.addOrUpdateReminderTask(1, tt1);
	Timer timer = new Timer();
	timer.schedule(tt1, Calendar.getInstance().getTime(), 1000 * 5);
	timer.schedule(tt2, Calendar.getInstance().getTime(), 1000 * 5);
	timer.schedule(tt3, Calendar.getInstance().getTime(), 1000 * 5);
	try {
	    Thread.sleep(1000 * 10);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	System.out.println("Canceling tt1...");
	tt1.cancel();
	try {
	    Thread.sleep(1000 * 10);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	System.out.println("Canceling tt2...");
	tt2.cancel();

    }

    public void removeReminderTask(int eventid) {
	eventReminders.remove(new Integer(eventid));
    }

}
