package org.universaal.ltba.activity.test;

import java.util.Calendar;
import java.util.Random;

import org.universAAL.ltba.activity.ActivityIntensity;
import org.universAAL.ltba.activity.ActivityLogger;
import org.universAAL.ltba.activity.Room;

@SuppressWarnings("deprecation")
public class ActivityLoggerTest {

	// private ActivityLogger alTest = ActivityLogger.getInstance();
	private ActivityLogger alTest = new ActivityLogger();
	Calendar cal = Calendar.getInstance();

	private void initTestLog() {

		for (int i = 0; i < 7; i++) {
			putDay(2012, Calendar.JUNE, i + 1);
		}

	}

	private void putDay(int year, int month, int day) {

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

	private void putNullActivity() {
		cal.roll(Calendar.MINUTE, 10 + new Random().nextInt(90));
		alTest.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
				ActivityIntensity.NULL);
		alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
				ActivityIntensity.NULL);
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.NULL);
		alTest.putEntry(cal.getTimeInMillis(), Room.GARDEN,
				ActivityIntensity.NULL);
		alTest.putEntry(cal.getTimeInMillis(), Room.HALL,
				ActivityIntensity.NULL);
		alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
				ActivityIntensity.NULL);

	}

	private void putMidNighAwakening(Calendar c) {
		// System.out.println("Mid night awakening start: " + printThisCal());
		cal.roll(Calendar.MINUTE, new Random().nextInt(90));
		alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
				ActivityIntensity.LOW);
		cal.roll(Calendar.MINUTE, new Random().nextInt(5));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
				ActivityIntensity.LOW);
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(2));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		// System.out.println("Mid night awakening end: " + printThisCal());
		alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
				ActivityIntensity.NULL);
		cal.roll(Calendar.SECOND, new Random().nextInt(60));
		alTest.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
				ActivityIntensity.NULL);
	}

	private void putAwakening(Calendar c) {
		cal.roll(Calendar.MINUTE, new Random().nextInt(120));
		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
					ActivityIntensity.LOW);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
					ActivityIntensity.MEDIUM);
		}
		cal.roll(Calendar.MINUTE, new Random().nextInt(5));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
				ActivityIntensity.LOW);
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(2));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
				ActivityIntensity.NULL);
		cal.roll(Calendar.SECOND, new Random().nextInt(60));
		alTest.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
				ActivityIntensity.NULL);
		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.LOW);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.MEDIUM);
		}
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(20));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
				ActivityIntensity.NULL);
	}

	private void putGardenActivity() {
		cal.roll(Calendar.MINUTE, new Random().nextInt(60));
		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.GARDEN,
					ActivityIntensity.LOW);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.GARDEN,
					ActivityIntensity.MEDIUM);
		}
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(75));
		cal.roll(Calendar.SECOND, 1 + new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.GARDEN,
				ActivityIntensity.NULL);
	}

	private void putVisitReceived() {
		cal.roll(Calendar.MINUTE, new Random().nextInt(60));
		alTest
				.putEntry(cal.getTimeInMillis(), Room.HALL,
						ActivityIntensity.LOW);
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(10));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.HALL,
				ActivityIntensity.NULL);
		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
					ActivityIntensity.LOW);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
					ActivityIntensity.MEDIUM);
		}
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(30));
		cal.roll(Calendar.SECOND, 1 + new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.NULL);

	}

	private void putLunch() {
		cal.roll(Calendar.MINUTE, new Random().nextInt(45));
		// System.out.println("Lunch start: " + printThisCal());
		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.MEDIUM);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.MEDIUM);
			cal.roll(Calendar.MINUTE, new Random().nextInt(5));
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.HIGH);
		}
		cal.roll(Calendar.MINUTE, new Random().nextInt(45));
		alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
				ActivityIntensity.LOW);
		cal.roll(Calendar.MINUTE, new Random().nextInt(5));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.LOW);
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(3));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.MEDIUM);
		alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
				ActivityIntensity.NULL);

		cal.roll(Calendar.MINUTE, new Random().nextInt(30));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.LOW);
		cal.roll(Calendar.SECOND, 5 + new Random().nextInt(30));
		// System.out.println("Lunch end: " + printThisCal());
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.NULL);
	}

	private String printThisCal() {
		return ("DAY:" + cal.get(Calendar.DAY_OF_MONTH) + "-"
				+ cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR)
				+ " AT HOUR:" + cal.get(Calendar.HOUR) + ":"
				+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
	}

	private void putDinner() {
		cal.roll(Calendar.MINUTE, new Random().nextInt(45));
		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.MEDIUM);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.MEDIUM);
			cal.roll(Calendar.MINUTE, new Random().nextInt(5));
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.HIGH);
		}
		cal.roll(Calendar.MINUTE, new Random().nextInt(25));
		alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
				ActivityIntensity.LOW);
		cal.roll(Calendar.MINUTE, new Random().nextInt(5));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.LOW);
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(3));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.MEDIUM);
		alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
				ActivityIntensity.NULL);

		cal.roll(Calendar.MINUTE, new Random().nextInt(20));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.LOW);
		cal.roll(Calendar.SECOND, 5 + new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.NULL);
	}

	private void putGoingToBed() {
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(90));

		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.NULL);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
					ActivityIntensity.LOW);
		}
		cal.roll(Calendar.SECOND, 300 + new Random().nextInt(400));

		alTest.putEntry(cal.getTimeInMillis(), Room.KITCHEN,
				ActivityIntensity.NULL);

		cal.roll(Calendar.MINUTE, new Random().nextInt(5));
		cal.roll(Calendar.SECOND, new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
				ActivityIntensity.LOW);
		cal.roll(Calendar.SECOND, 5 + new Random().nextInt(240));
		alTest.putEntry(cal.getTimeInMillis(), Room.BATHROOM,
				ActivityIntensity.NULL);

		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
					ActivityIntensity.LOW);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
					ActivityIntensity.MEDIUM);
		}
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(2));
		cal.roll(Calendar.SECOND, 1 + new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.BEDROOM,
				ActivityIntensity.NULL);
	}

	private void putLivingRoomActivity() {
		cal.roll(Calendar.MINUTE, new Random().nextInt(30));
		if (new Random().nextBoolean()) {
			alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
					ActivityIntensity.LOW);
		} else {
			alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
					ActivityIntensity.MEDIUM);
		}
		cal.roll(Calendar.MINUTE, 5 + new Random().nextInt(30));
		cal.roll(Calendar.SECOND, 1 + new Random().nextInt(30));
		alTest.putEntry(cal.getTimeInMillis(), Room.LIVINGROOM,
				ActivityIntensity.NULL);
	}

	public ActivityLogger getTestLogger() {
		initTestLog();
		// for (DailyActivity alEntry : alTest.dayList) {
		// ArrayList<ActivityEntry> entries = alEntry.getEntryList();
		// for (ActivityEntry activityEntry : entries) {
		// activityEntry.print();
		// }
		// }
		return alTest;
	}

}
