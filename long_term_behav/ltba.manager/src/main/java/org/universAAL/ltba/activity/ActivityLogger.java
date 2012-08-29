package org.universAAL.ltba.activity;

import java.awt.Color;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.universAAL.ltba.activity.ActivityIntensity;
import org.universAAL.ltba.activity.representation.DayCollectionActivityMap;
import org.universAAL.ltba.activity.representation.__WeekActivityMap;
import org.universAAL.ltba.activity.representation.WeekCanvas;
import org.universAAL.ltba.activity.representation.WeeklyGraphicReport;
import org.universAAL.middleware.container.ModuleContext;

public class ActivityLogger {

	private ModuleContext context;
	public ArrayList<DailyActivity> dayList;

	public ActivityLogger(ModuleContext context) {
		this.context = context;
		dayList = new ArrayList<DailyActivity>();
	}

	/**
	 * Just for test purposes.
	 * 
	 * @deprecated
	 */
	public ActivityLogger() {
		dayList = new ArrayList<DailyActivity>();
	}

	public void putEntry(long timestamp, Room room, ActivityIntensity intensity) {
		// System.out.println("INSERTANDO ENTRADA");
		Calendar thisCal = Calendar.getInstance();
		thisCal.setTime(new Date(timestamp));
//		System.out.println("ENTRY:" + thisCal.get(Calendar.YEAR) + "-"
//				+ thisCal.get(Calendar.MONTH) + "-"
//				+ thisCal.get(Calendar.DAY_OF_MONTH));
		if (!dayList.isEmpty()) {
			// System.out.println("LISTA NO VACÍA");
			DailyActivity lastDay = dayList.get(dayList.size() - 1);

			if (lastDay.getYear() == thisCal.get(Calendar.YEAR)
					&& lastDay.getMonth() == (thisCal.get(Calendar.MONTH))
					&& lastDay.getDay() == (thisCal.get(Calendar.DAY_OF_MONTH))) {
				// System.out.println("ESTAMOS EN EL MISMO DÍA");
				// System.out.println("Este día: " + thisCal.get(Calendar.YEAR)
				// + "-" + thisCal.get(Calendar.MONTH) + "-"
				// + thisCal.get(Calendar.DAY_OF_MONTH));
				lastDay.addNewEntry(timestamp, room, intensity);

			} else {
				// System.out.println("Ultimo día: " + lastDay.getYear() + "-"
				// + lastDay.getMonth() + "-" + lastDay.getDay());
				// System.out.println("Este día: " + thisCal.get(Calendar.YEAR)
				// + "-" + thisCal.get(Calendar.MONTH) + "-"
				// + thisCal.get(Calendar.DAY_OF_MONTH));
				DailyActivity newDay = new DailyActivity(timestamp);
				newDay.addNewEntry(timestamp, room, intensity);
				dayList.add(newDay);
			}
		} else {
			// System.out.println("LISTA VACÍA");
			DailyActivity newDay = new DailyActivity(timestamp);
			newDay.addNewEntry(timestamp, room, intensity);
			dayList.add(newDay);
		}

	}

	public void printReport() {
		for (DailyActivity day : dayList) {
			System.out
					.println("****************************************************************************************\n"
							+ ""
							+ day.getDay()
							+ "-"
							+ day.getMonth()
							+ "-"
							+ day.getYear()
							+ "\n****************************************************************************************");
			System.out.println();
			day.print();
		}
		showGraphicTable();
	}

	public void showGraphicTable() {

		// String[][] tableData = showTableReport();
		// String[] header = new String[tableData[0].length];
		// for (int i = 0; i < tableData[0].length; i++) {
		// header[i] = "-";
		// }
		// JTable table1 = new JTable(tableData, header);
		// table1.setDefaultRenderer(String.class, new MyTableRenderer());
		// JPanel jp = new SingleReportingTable(tableData);
		// // jp.add(table1);
		// JFrame jf = new JFrame("ACTIVITY REPORT");
		// jf.setSize(640, 480);
		// jf.add(jp);
		// jf.setVisible(true);
		// jf.repaint();
		ArrayList<DailyActivity> lastWeek = new ArrayList<DailyActivity>(7);

		System.out
				.println("My current dayList has the size: " + dayList.size());

		if (dayList.size() < 7) {
			for (int i = 0; i < 7 - dayList.size(); i++) {
				System.out
						.println("Inserting a day from the begginning of the world: "
								+ i);
				lastWeek.add(new DailyActivity(0));
			}

			for (int i = 7 - dayList.size(); i < 7; i++) {
				System.out.println("Inserting a day from the daylist: " + i);
				lastWeek.add(dayList.get(i - 7 + dayList.size()));
			}
		} else {
			for (int i = dayList.size() - 7; i < dayList.size(); i++) {
				System.out.println("Inserting a day from the dayList");
				lastWeek.add(dayList.get(i));
			}
		}
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DayCollectionActivityMap dcam = new DayCollectionActivityMap(
				WeekCanvas.DAY_COLUMN_LENGTH, 7, lastWeek);
		// wam.initWeekActivityMap();
		WeeklyGraphicReport jf2 = new WeeklyGraphicReport(dcam);
	}

	public String[][] showTableReport() {
		String[][] data = new String[25][1];
		String[] hours = { "DAY/HOUR", "00:00", "01:00", "02:00", "03:00",
				"04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00",
				"11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
				"18:00", "19:00", "20:00", "21:00", "22:00", "23:00" };
		// Fill the first column
		for (int i = 0; i < hours.length; i++) {
			data[i][0] = hours[i];
		}
		// System.out.println("FIRST COLUMN");

		// for (int i = 0; i < data.length; i++) {
		// for (int j = 0; j < data[0].length; j++) {
		// System.out.println(data.length);
		// System.out.println(data[0].length);
		// System.out.print("i:" + i + " j:" + j + " " + data[i][j]);
		// }
		// }

		String[] thisDay = new String[25];
		for (DailyActivity day : dayList) {
			ArrayList<ActivityEntry> entryList = day.getEntryList();
			int lastHour = 0;
			ActivityIntensity lastIntensity = ActivityIntensity.NULL;
			thisDay = new String[25];
			thisDay[0] = day.getDay() + "-" + day.getMonth() + "-"
					+ day.getYear();
			for (ActivityEntry activityEntry : entryList) {
				int hour = activityEntry.getHour();
				for (int i = lastHour; i <= hour; i++) {
					thisDay[i + 1] = lastIntensity.getIntensityString();
					// System.out.println("Day: " + day.getDay() + "-"
					// + day.getMonth() + " Hour " + i + " intensity: "
					// + thisDay[i + 1]);
				}
				lastIntensity = activityEntry.getIntensity();
				lastHour = activityEntry.getHour();
			}
			for (int i = lastHour; i <= 23; i++) {
				thisDay[i + 1] = lastIntensity.getIntensityString();
				thisDay[i + 1] = ActivityIntensity.NULL.getIntensityString();
			}
			lastHour = 0;
			String[][] dataNew = new String[25][data[0].length + 1];
			System.out.println("data->dataNew");
			for (int i = 0; i < data.length; i++) {
				// System.out.println("i--" + i);
				// System.out.println("Data.lengh->" + data.length);
				// System.out.println("DataNew.lengh->" + dataNew.length);
				// System.out.println("Data[i].lengh->" + data[i].length);
				// System.out.println("DataNew[i].lengh->" + dataNew[i].length);
				System.arraycopy(data[i], 0, dataNew[i], 0, data[i].length);
				for (int j = 0; j < data[i].length; j++) {
					System.out.print(" " + dataNew[i][j]);
				}
			}

			for (int i = 0; i < dataNew.length; i++) {
				// System.out.println("datanew[i].length--->" +
				// dataNew[i].length);
				dataNew[i][dataNew[0].length - 1] = thisDay[i];
			}

			// System.arraycopy(thisDay, 0, dataNew[dataNew.length - 1], 0,
			// data.length);
			// System.out.println("ThisDay->DataNew");
			// for (int i = 0; i < dataNew.length; i++) {
			// for (int j = 0; j < dataNew[0].length; j++) {
			// System.out.print(" " + dataNew[i][j]);
			// }
			// }
			data = new String[dataNew.length][dataNew[0].length];
			data = dataNew;
		}
		// for (int i = 0; i < data.length; i++) {
		// System.out.print("\n");
		// for (int j = 0; j < data[0].length; j++) {
		// System.out.print(" " + data[i][j]);
		// }
		// }

		return data;
	}

}
