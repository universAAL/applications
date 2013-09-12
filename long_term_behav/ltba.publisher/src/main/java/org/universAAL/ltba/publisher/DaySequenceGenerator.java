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
package org.universAAL.ltba.publisher;

import java.util.Calendar;
import java.util.Random;

import org.universAAL.ltba.activity.ActivityIntensity;
import org.universAAL.ltba.activity.ActivityLogger;
import org.universAAL.ltba.activity.Room;
import org.universAAL.ltba.manager.ConsequenceListener;
import org.universAAL.middleware.container.ModuleContext;

/**
 * This class is used for genetaring the information of the activity in a
 * sequence of days. The objective is to provide an easy way to show graphical
 * reports from the LTBA. For this reason, the middleware cannot be used, so the
 * information is directly generated and sent to the ltba.manager module.
 * 
 * @author mllorente
 * 
 */
public class DaySequenceGenerator {

    private Calendar cal = Calendar.getInstance();;
    private ModuleContext context;
    private ActivityLogger alDemo;

    /**
     * Standard constructor
     * 
     * @param context
     */
    public DaySequenceGenerator(ModuleContext context) {
	this.cal = Calendar.getInstance();
	this.context = context;
	this.alDemo = ConsequenceListener.getInstance(context)
		.getActivityLogger();
    }

    /**
     * Inserts a list of days in the ActivityLogger of the module ltba.manager.
     * The last day generated will coincide with the day before of the current
     * system date (yesterday).
     * 
     * @param numberOfDaysToBeGenerated
     *            The number of days to be generated.
     */
    public void generateDaySecuence(int numberOfDaysToBeGenerated) {
	cal.roll(Calendar.DAY_OF_YEAR, (-1) * (numberOfDaysToBeGenerated));

	for (int i = 0; i < numberOfDaysToBeGenerated; i++) {

	    putDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
		    .get(Calendar.DAY_OF_MONTH));
	    cal.roll(Calendar.DAY_OF_YEAR, true);
	}
    }

    /**
     * Put a complete day in the current ActivityLogger
     * 
     * @param year
     *            The year of the date.
     * @param month
     *            The month of the date.
     * @param day
     *            The day of the date.
     */
    private void putDay(int year, int month, int day) {
	context.logDebug("", "PUTTING:" + cal.get(Calendar.YEAR) + "-"
		+ cal.get(Calendar.MONTH) + "-"
		+ cal.get(Calendar.DAY_OF_MONTH), null);
	cal.set(year, month, day, 4, 0, 0);

	if (new Random().nextBoolean()) {
	    putMidNighAwakening(cal);
	}

	cal.set(year, month, day, 6, 30, 0);

	putAwakening(cal);

	cal.set(year, month, day, 8, 0, 0);

	if (new Random().nextBoolean()) {
	    if (new Random().nextBoolean()) {
		putGardenActivity();
	    } else {
		putLivingRoomActivity();
	    }
	}

	cal.set(year, month, day, 10, 0, 0);

	if (new Random().nextBoolean()) {
	    if (new Random().nextBoolean()) {
		putGardenActivity();
	    } else {
		putLivingRoomActivity();
	    }
	}

	cal.set(year, month, day, 12, 0, 0);

	if (new Random().nextBoolean()) {
	    putVisitReceived();
	}

	cal.set(year, month, day, 13, 0, 0);

	putLunch();

	cal.set(year, month, day, 16, 0, 0);

	if (new Random().nextBoolean()) {
	    putLivingRoomActivity();
	}

	cal.set(year, month, day, 18, 0, 0);

	if (new Random().nextBoolean()) {
	    if (new Random().nextBoolean()) {
		putVisitReceived();
	    } else if (new Random().nextBoolean()) {
		putLivingRoomActivity();
	    }
	}

	cal.set(year, month, day, 20, 0, 0);

	putDinner();

	cal.set(year, month, day, 21, 0, 0);

	putGoingToBed();

	cal.set(year, month, day, 22, 20, 0);

	putNullActivity();

    }

    /**
     * Puts null activity in all the rooms.
     */
    private void putNullActivity() {
	cal.roll(Calendar.MINUTE, 10 + new Random().nextInt(90));

	alDemo.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
		ActivityIntensity.NULL);
	alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		ActivityIntensity.NULL);
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.NULL);
	alDemo.putEntry(cal.getTimeInMillis(), Room.GARDEN,
		ActivityIntensity.NULL);
	alDemo.putEntry(cal.getTimeInMillis(), Room.HALL,
		ActivityIntensity.NULL);
	alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		ActivityIntensity.NULL);

    }

    /**
     * Puts an event simulating a midnight awakening.
     * 
     * @param c
     *            Time to put the event.
     */
    private void putMidNighAwakening(Calendar c) {
	// System.out.println("Mid night awakening start: " + printThisCal());
	cal.roll(Calendar.MINUTE, new Random().nextInt(90));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		ActivityIntensity.LOW);
	cal.roll(Calendar.MINUTE, new Random().nextInt(5));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
		ActivityIntensity.LOW);
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(2));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	// System.out.println("Mid night awakening end: " + printThisCal());
	alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		ActivityIntensity.NULL);
	cal.roll(Calendar.SECOND, new Random().nextInt(60));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
		ActivityIntensity.NULL);
    }

    /**
     * Puts an event simulating the awakening of the assisted person.
     * 
     * @param c
     *            Time to put the event.
     */
    private void putAwakening(Calendar c) {
	cal.roll(Calendar.MINUTE, new Random().nextInt(120));
	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		    ActivityIntensity.LOW);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		    ActivityIntensity.MEDIUM);
	}
	cal.roll(Calendar.MINUTE, new Random().nextInt(5));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
		ActivityIntensity.LOW);
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(2));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		ActivityIntensity.NULL);
	cal.roll(Calendar.SECOND, new Random().nextInt(60));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
		ActivityIntensity.NULL);
	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.LOW);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.MEDIUM);
	}
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(20));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		ActivityIntensity.NULL);
    }

    /**
     * Puts an event simulating activity in the garden.
     */
    private void putGardenActivity() {
	cal.roll(Calendar.MINUTE, new Random().nextInt(60));
	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.GARDEN,
		    ActivityIntensity.LOW);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.GARDEN,
		    ActivityIntensity.MEDIUM);
	}
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(75));
	cal.roll(Calendar.SECOND, 1 + new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.GARDEN,
		ActivityIntensity.NULL);
    }

    /**
     * Puts an event simulating a visit received by the assisted person.
     */
    private void putVisitReceived() {
	cal.roll(Calendar.MINUTE, new Random().nextInt(60));
	alDemo
		.putEntry(cal.getTimeInMillis(), Room.HALL,
			ActivityIntensity.LOW);
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(10));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.HALL,
		ActivityIntensity.NULL);
	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		    ActivityIntensity.LOW);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		    ActivityIntensity.MEDIUM);
	}
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(30));
	cal.roll(Calendar.SECOND, 1 + new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.NULL);

    }

    /**
     * Puts an event to simulate the lunch hour.
     */
    private void putLunch() {
	cal.roll(Calendar.MINUTE, new Random().nextInt(45));
	// System.out.println("Lunch start: " + printThisCal());
	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.MEDIUM);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.MEDIUM);
	    cal.roll(Calendar.MINUTE, new Random().nextInt(5));
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.HIGH);
	}
	cal.roll(Calendar.MINUTE, new Random().nextInt(45));
	alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		ActivityIntensity.LOW);
	cal.roll(Calendar.MINUTE, new Random().nextInt(5));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.LOW);
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(3));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.MEDIUM);
	alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		ActivityIntensity.NULL);

	cal.roll(Calendar.MINUTE, new Random().nextInt(30));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.LOW);
	cal.roll(Calendar.SECOND, 5 + new Random().nextInt(30));
	// System.out.println("Lunch end: " + printThisCal());
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.NULL);
    }

    /**
     * Prints the current calendar used in this class. For developement
     * purposes.
     * 
     * @return A string with the relevant information of the calendar.
     */
    @SuppressWarnings("unused")
    private String printThisCal() {
	return ("DAY:" + cal.get(Calendar.DAY_OF_MONTH) + "-"
		+ cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR)
		+ " AT HOUR:" + cal.get(Calendar.HOUR) + ":"
		+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
    }

    /**
     * Puts an event simulating the dinner hour.
     */
    private void putDinner() {
	cal.roll(Calendar.MINUTE, new Random().nextInt(45));
	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.MEDIUM);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.MEDIUM);
	    cal.roll(Calendar.MINUTE, new Random().nextInt(5));
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.HIGH);
	}
	cal.roll(Calendar.MINUTE, new Random().nextInt(25));
	alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		ActivityIntensity.LOW);
	cal.roll(Calendar.MINUTE, new Random().nextInt(5));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.LOW);
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(3));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.MEDIUM);
	alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		ActivityIntensity.NULL);

	cal.roll(Calendar.MINUTE, new Random().nextInt(20));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.LOW);
	cal.roll(Calendar.SECOND, 5 + new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.NULL);
    }

    /**
     * Puts events to simulate the bedtime.
     */
    private void putGoingToBed() {
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(90));

	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.NULL);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		    ActivityIntensity.LOW);
	}
	cal.roll(Calendar.SECOND, 300 + new Random().nextInt(400));

	alDemo.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
		ActivityIntensity.NULL);

	cal.roll(Calendar.MINUTE, new Random().nextInt(5));
	cal.roll(Calendar.SECOND, new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
		ActivityIntensity.LOW);
	cal.roll(Calendar.SECOND, 5 + new Random().nextInt(240));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
		ActivityIntensity.NULL);

	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		    ActivityIntensity.LOW);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		    ActivityIntensity.MEDIUM);
	}
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(2));
	cal.roll(Calendar.SECOND, 1 + new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
		ActivityIntensity.NULL);
    }

    /**
     * Puts events for simulating general activity in the living room.
     */
    private void putLivingRoomActivity() {
	cal.roll(Calendar.MINUTE, new Random().nextInt(30));
	if (new Random().nextBoolean()) {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		    ActivityIntensity.LOW);
	} else {
	    alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		    ActivityIntensity.MEDIUM);
	}
	cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(30));
	cal.roll(Calendar.SECOND, 1 + new Random().nextInt(30));
	alDemo.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
		ActivityIntensity.NULL);
    }

}