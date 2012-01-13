package org.universAAL.agenda.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.universAAL.agenda.gui.util.DateInstance;
import org.universAAL.agenda.gui.util.DateTimeInstance;
import org.universAAL.agenda.gui.util.GuiConstants;
import org.universAAL.agenda.gui.util.TimeInstance;

public class DatePanel extends JPanel {
	private static final long serialVersionUID = 3762589404597728131L;
	private final String title;

	public DatePanel(String title) {
		this.title = title;
		initComponents();
	}

	private void initComponents() {
		GridBagLayout gb = new GridBagLayout();
		Font smallFont = GuiConstants.smallFont;
		Font mediumFont = GuiConstants.mediumFont;

		dayL = new JLabel(
				Messages.getString("DatePanel.Day"), SwingConstants.RIGHT); //$NON-NLS-1$
		dayL.setFont(smallFont);
		dayL.setForeground(Color.BLUE);
		monthL = new JLabel(
				Messages.getString("DatePanel.Month"), SwingConstants.RIGHT); //$NON-NLS-1$
		monthL.setFont(smallFont);
		monthL.setForeground(Color.BLUE);
		yearL = new JLabel(
				Messages.getString("DatePanel.Year"), SwingConstants.RIGHT); //$NON-NLS-1$
		yearL.setFont(smallFont);
		yearL.setForeground(Color.BLUE);
		hourL = new JLabel(
				Messages.getString("DatePanel.Hour"), SwingConstants.RIGHT); //$NON-NLS-1$
		hourL.setFont(smallFont);
		hourL.setForeground(Color.BLUE);
		minuteL = new JLabel(
				Messages.getString("DatePanel.Mins"), SwingConstants.RIGHT); //$NON-NLS-1$
		minuteL.setFont(smallFont);
		minuteL.setForeground(Color.BLUE);
		dateL = new JLabel(Messages.getString("DatePanel.date") + //$NON-NLS-1$
				":   ", SwingConstants.RIGHT); //$NON-NLS-1$
		dateL.setFont(mediumFont);
		timeL = new JLabel(Messages.getString("DatePanel.time") + //$NON-NLS-1$
				":   ", SwingConstants.RIGHT); //$NON-NLS-1$
		timeL.setFont(mediumFont);
		dateSeperator1L = new JLabel("/"); //$NON-NLS-1$
		dateSeperator1L.setFont(mediumFont);
		dateSeperator2L = new JLabel("/"); //$NON-NLS-1$
		dateSeperator2L.setFont(mediumFont);
		timeSeperatorL = new JLabel(":"); //$NON-NLS-1$
		timeSeperatorL.setFont(mediumFont);

		dayText = new DayTextField(2);
		dayText.setFont(mediumFont);
		dayText.setBackground(GuiConstants.textActiveBackground);
		monthText = new MonthTextField(2);
		monthText.setFont(mediumFont);
		monthText.setBackground(GuiConstants.textActiveBackground);
		yearText = new YearTextField(3);
		yearText.setFont(mediumFont);
		yearText.setBackground(GuiConstants.textActiveBackground);
		hourText = new HourTextField(2);
		hourText.setFont(mediumFont);
		hourText.setBackground(GuiConstants.textActiveBackground);
		minuteText = new MinuteTextField(2);
		minuteText.setFont(mediumFont);
		minuteText.setBackground(GuiConstants.textActiveBackground);

		main = this; // new JPanel();
		main.setBackground(Color.white);
		main.setLayout(gb);

		GridBagConstraints c = new GridBagConstraints();

		// first row
		c.gridy = 0;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.CENTER;
		main.add(dayL, c);

		c.gridy = 0;
		c.gridx = 3;
		c.gridwidth = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.CENTER;
		main.add(monthL, c);

		c.gridy = 0;
		c.gridx = 5;
		c.gridwidth = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.CENTER;
		main.add(yearL, c);

		// second row
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weightx = 0.3;
		c.anchor = GridBagConstraints.LINE_START;
		main.add(dateL, c);

		c.gridy = 1;
		c.gridx = 1;
		c.weightx = 0.2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		main.add(dayText, c);

		c.gridy = 1;
		c.gridx = 2;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		main.add(dateSeperator1L, c);

		c.gridy = 1;
		c.gridx = 3;
		c.weightx = 0.2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		main.add(monthText, c);

		c.gridy = 1;
		c.gridx = 4;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		main.add(dateSeperator2L, c);

		c.gridy = 1;
		c.gridx = 5;
		c.weightx = 0.2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		main.add(yearText, c);

		// third row
		c.gridy = 2;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.CENTER;
		main.add(hourL, c);

		c.gridy = 2;
		c.gridx = 3;
		c.gridwidth = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.CENTER;
		main.add(minuteL, c);

		// forth row
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weightx = 0.3;
		c.anchor = GridBagConstraints.LINE_START;
		main.add(timeL, c);

		c.gridy = 3;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.CENTER;
		main.add(hourText, c);

		c.gridy = 3;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weightx = 0.1;
		c.anchor = GridBagConstraints.CENTER;
		main.add(timeSeperatorL, c);

		c.gridy = 3;
		c.gridx = 3;
		c.gridwidth = 1;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.CENTER;
		main.add(minuteText, c);

		TitledBorder b = BorderFactory.createTitledBorder(this.title);
		b.setTitleFont(mediumFont);
		b.setTitleColor(new Color(0x565656));
		main.setBorder(b);
		// this.setBackground(Color.white);
		// this.add(main);
	}

	public DateInstance getDays() throws NumberFormatException {
		int day = Integer.valueOf(dayText.getText());
		int month = Integer.valueOf(monthText.getText());
		int year = Integer.valueOf(yearText.getText());
		return new DateInstance(year, month, day);
	}

	public TimeInstance getTime() throws NumberFormatException {
		int hour = Integer.valueOf(hourText.getText());
		int minute = Integer.valueOf(minuteText.getText());

		return new TimeInstance(hour, minute);
	}

	public Calendar getDate() {
		try {
			Calendar c = Calendar.getInstance();
			DateInstance date = getDays();
			TimeInstance time = getTime();

			// c.get
			c.set(Calendar.YEAR, date.year);
			c.set(Calendar.MONTH, date.month - 1);
			c.set(Calendar.DAY_OF_MONTH, Math.min(date.day, c
					.getActualMaximum(Calendar.DAY_OF_MONTH)));
			c.set(Calendar.HOUR_OF_DAY, time.hour);
			c.set(Calendar.MINUTE, time.minute);
			c.set(Calendar.SECOND, time.second);

			return c;
		} catch (NumberFormatException nfe) {

			// nfe.printStackTrace();
		}
		return null;

	}

	public DateTimeInstance getDateTime() {
		Calendar c = getDate();
		if (c == null)
			return null;

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		return new DateTimeInstance(new DateInstance(year, month, day),
				new TimeInstance(hour, minute));
	}

	public void setDate(DateInstance date) {
		if (date == null) {
			dayText.setText(""); //$NON-NLS-1$
			monthText.setText(""); //$NON-NLS-1$
			yearText.setText(""); //$NON-NLS-1$
		} else {
			String day = "" + date.day; //$NON-NLS-1$
			String month = "" + date.month; //$NON-NLS-1$
			String year = "" + date.year; //$NON-NLS-1$

			dayText.setText((day.length() == 1) ? "0" + day : day); //$NON-NLS-1$
			monthText.setText((month.length() == 1) ? "0" + month : month); //$NON-NLS-1$
			yearText.setText((year.length() == 1) ? "0" + year : year); //$NON-NLS-1$
		}
	}

	public void setTime(TimeInstance time) {
		if (time == null) {
			hourText.setText(""); //$NON-NLS-1$
			minuteText.setText(""); //$NON-NLS-1$
		} else {
			String hour = "" + time.hour; //$NON-NLS-1$
			String minute = "" + time.minute; //$NON-NLS-1$
			hourText.setText((hour.length() == 1) ? "0" + hour : hour); //$NON-NLS-1$
			minuteText.setText((minute.length() == 1) ? "0" + minute : minute); //$NON-NLS-1$
		}
	}

	public void setDateTime(DateTimeInstance dti) {
		if (dti == null) {
			setDate(null);
			setTime(null);
		} else {
			setDate(dti.date);
			setTime(dti.time);
		}
	}

	public void setEditable(boolean isEditable) {
		dayText.setEditable(isEditable);
		monthText.setEditable(isEditable);
		yearText.setEditable(isEditable);
		minuteText.setEditable(isEditable);
		hourText.setEditable(isEditable);

		if (isEditable) {
			dayText.setBackground(GuiConstants.textActiveBackground);
			monthText.setBackground(GuiConstants.textActiveBackground);
			yearText.setBackground(GuiConstants.textActiveBackground);
			minuteText.setBackground(GuiConstants.textActiveBackground);
			hourText.setBackground(GuiConstants.textActiveBackground);
		} else {
			dayText.setBackground(GuiConstants.textInactiveBackground);
			monthText.setBackground(GuiConstants.textInactiveBackground);
			yearText.setBackground(GuiConstants.textInactiveBackground);
			minuteText.setBackground(GuiConstants.textInactiveBackground);
			hourText.setBackground(GuiConstants.textInactiveBackground);
		}
	}

	public static void main(String[] str) {
		JFrame f = new JFrame();
		f.setBackground(Color.white);
		JPanel p = new JPanel(new GridLayout(1, 2));
		p.setBackground(Color.white);
		p.add(new DatePanel(" " + //$NON-NLS-1$
				Messages.getString("DatePanel.StartDate") + //$NON-NLS-1$
				" ")); //$NON-NLS-1$
		p.add(new DatePanel(" " + //$NON-NLS-1$
				Messages.getString("DatePanel.EndDate") + //$NON-NLS-1$
				" ")); //$NON-NLS-1$

		f.add(p);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	JPanel main;
	JLabel dayL, monthL, yearL, dateL, hourL, minuteL, timeL, dateSeperator1L,
			dateSeperator2L, timeSeperatorL;
	JTextField dayText, monthText, yearText, hourText, minuteText;

	// JSpinner daySpinner, monthSpinner, yearSpinner, hourSpinner,
	// minuteSpinner;

	public void clearFields() {
		dayText.setText(""); //$NON-NLS-1$
		monthText.setText(""); //$NON-NLS-1$
		yearText.setText(""); //$NON-NLS-1$
		hourText.setText(""); //$NON-NLS-1$
		minuteText.setText(""); //$NON-NLS-1$

	}
}
