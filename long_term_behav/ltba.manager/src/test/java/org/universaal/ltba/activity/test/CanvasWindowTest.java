package org.universaal.ltba.activity.test;

import java.awt.Color;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;
import org.universAAL.ltba.activity.representation.DayCollectionActivityMap;
import org.universAAL.ltba.activity.representation.WeeklyGraphicReport;

public class CanvasWindowTest {

	public void testActivityLoggerTestCreation() {
		// new ActivityLoggerTest().getTestLogger();
	}

	@Test
	public void testColorArrayLastColumn() {

		Color[] colorTestArray = new Color[288];

		for (int i = 0; i < colorTestArray.length; i++) {
			if (i < 60) {
				colorTestArray[i] = Color.RED;
			} else if (i > 70 && i < 90) {
				colorTestArray[i] = Color.ORANGE;
			} else if (i > 110 && i < 117) {
				colorTestArray[i] = Color.YELLOW;
			} else if (i > 120 && i < 184) {
				colorTestArray[i] = Color.GREEN;
			} else if (i > 213 && i < 235) {
				colorTestArray[i] = Color.BLUE;
			} else if (i > 250 && i < 280) {
				colorTestArray[i] = Color.PINK;
			} else {
				colorTestArray[i] = Color.DARK_GRAY;
			}
		}
		DayCollectionActivityMap wam = new DayCollectionActivityMap(288, 7,
				new ActivityLoggerTest().getTestLogger().dayList);
		// wam.auxPrinter();
		// try {
		// System.in.read();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// wam.initWeekActivityMap();
		/**
		WeeklyGraphicReport wgr = new WeeklyGraphicReport(wam);
		*/
		// try {
		// System.in.read();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// // wgr.printCanvas();
		// try {
		// System.in.read();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// try {
		// System.in.read();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
