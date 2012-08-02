package org.universaal.ltba.activity.test;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.universAAL.ltba.activity.DailyActivity;
import org.universAAL.ltba.activity.representation.DayCollectionActivityMap;
import org.universAAL.ltba.activity.representation.DayGraphicReport;
import org.universAAL.ltba.activity.representation.MonthGraphicReport;
import org.universAAL.ltba.activity.representation.__WeekActivityMap;
import org.universAAL.ltba.activity.representation.WeeklyGraphicReport;

public class DayCanvasWindowTest extends TestCase {

	public void testActivityLoggerTestCreation() {
		// new ActivityLoggerTest().getTestLogger();
	}

	@Override
	protected void runTest() throws Throwable {

		super.runTest();
	}

	public void testColorArrayLastColumn() {

		ActivityLoggerTest alt = new ActivityLoggerTest();
		ArrayList<DailyActivity> al = alt.getTestLogger().dayList;
		ArrayList<DailyActivity> aDayInAList = new ArrayList<DailyActivity>(al
				.subList(0, 1));

		DayCollectionActivityMap dam = new DayCollectionActivityMap(288, 1,
				aDayInAList);
		DayCollectionActivityMap wam = new DayCollectionActivityMap(288, 7, al);
		DayCollectionActivityMap mam = new DayCollectionActivityMap(288, 7, al);
		// wam.auxPrinter();
		// try {
		// System.in.read();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// wam.initWeekActivityMap();
		DayGraphicReport dgr = new DayGraphicReport(dam);
		WeeklyGraphicReport wgr = new WeeklyGraphicReport(wam);
		MonthGraphicReport mgr = new MonthGraphicReport(mam);
		// try {
		// System.in.read();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// try {
		// System.in.read();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
