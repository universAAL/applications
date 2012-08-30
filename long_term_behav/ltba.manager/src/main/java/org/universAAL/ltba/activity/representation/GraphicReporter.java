package org.universAAL.ltba.activity.representation;

import java.util.ArrayList;
import java.util.Date;

import org.universAAL.ltba.activity.ActivityLogger;
import org.universAAL.ltba.activity.DailyActivity;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;

/**
 * Provides graphic reports for activity intensity in different periods of time.
 * 
 * @author mllorente
 * 
 */
public class GraphicReporter {

	public static void showDayReport(Date date, ActivityLogger al,
			ModuleContext context, Resource inputUser) {
		DayCollectionActivityMap dam = new DayCollectionActivityMap(
				DayCanvas.DAY_COLUMN_LENGTH, 1, new ArrayList<DailyActivity>(
						al.dayList.subList(al.dayList.size() - 1, al.dayList
								.size())));
		/** Create (and show) a WeeklyGraphicReport */
		DayGraphicReport dgr = new DayGraphicReport(dam, context, inputUser);
	}

	/**
	 * Show a report with the latest 7 days TODO add a dueDate to represent any
	 * week from the logged days
	 * 
	 * @param al
	 *            The Activity Logger
	 * @param inputUser 
	 * @param mc 
	 */
	public static void showWeekReport(ActivityLogger al, ModuleContext mc, Resource inputUser) {

		/** Week to be shown */
		ArrayList<DailyActivity> weekShown = new ArrayList<DailyActivity>(7);
		/** Collection of days extracted from the ActivityLogger */
		ArrayList<DailyActivity> dayList = al.dayList;

		/**
		 * If there are logged less than 7 days, the rest of the days to
		 * complete a week report will be filled with empty days.
		 */
		if (dayList.size() < 7) {
			for (int i = 0; i < 7 - dayList.size(); i++) {
				/** Create and add an empty day from the begging of the times */
				weekShown.add(new DailyActivity(0));
			}
			/** The last part of the list is filled with the actual logged days */
			for (int i = 7 - dayList.size(); i < 7; i++) {
				weekShown.add(dayList.get(i - 7 + dayList.size()));
			}
		} else {
			/** Take the last 7 days */
			for (int i = dayList.size() - 7; i < dayList.size(); i++) {
				weekShown.add(dayList.get(i));
			}
		}
		/**
		 * Create a collection of days in a format understandable by the graphic
		 * report generator
		 */
		DayCollectionActivityMap dcam = new DayCollectionActivityMap(
				WeekCanvas.DAY_COLUMN_LENGTH, 7, weekShown);
		/** Create (and show) a WeeklyGraphicReport */
		WeeklyGraphicReport wgr = new WeeklyGraphicReport(dcam,mc,inputUser);

	}

	public static void showMonthReport(ActivityLogger al,
			ModuleContext context, Resource user) {

		/** Month to be shown */
		ArrayList<DailyActivity> monthShown = new ArrayList<DailyActivity>(7);
		/** Collection of days extracted from the ActivityLogger */
		ArrayList<DailyActivity> dayList = al.dayList;

		/**
		 * If there are logged less than 7 days, the rest of the days to
		 * complete a week report will be filled with empty days.
		 */
		if (dayList.size() < 30) {
			for (int i = 0; i < 30 - dayList.size(); i++) {
				/** Create and add an empty day from the begging of the times */
				monthShown.add(new DailyActivity(0));
			}
			/** The last part of the list is filled with the actual logged days */
			for (int i = 30 - dayList.size(); i < 30; i++) {
				monthShown.add(dayList.get(i - 30 + dayList.size()));
			}
		} else {
			/** Take the last 7 days */
			for (int i = dayList.size() - 30; i < dayList.size(); i++) {
				monthShown.add(dayList.get(i));
			}
		}
		/**
		 * Create a collection of days in a format understandable by the graphic
		 * report generator
		 */
		DayCollectionActivityMap dcam = new DayCollectionActivityMap(
				WeekCanvas.DAY_COLUMN_LENGTH, 30, monthShown);
		/** Create (and show) a WeeklyGraphicReport */
		MonthGraphicReport mgr = new MonthGraphicReport(dcam, context, user);

	}

}
