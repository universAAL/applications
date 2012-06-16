package org.universAAL.agenda.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.universAAL.agenda.gui.IEventInfoListener;
import org.universAAL.agenda.gui.IconsHome;
import org.universAAL.agenda.gui.util.GuiConstants;

/**
 * 
 * Reminder info panel with status (active/inactive), date, type, and a button
 * to set reminder.
 * 
 */
public class ReminderPanel extends JPanel {
    private static final long serialVersionUID = 7749867619210140899L;
    private IEventInfoListener listener;

    public ReminderPanel(IEventInfoListener listener) {
	this.listener = listener;
	initComponents();
    }

    private void initComponents() {
	ImageIcon setRemIcon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/set_reminder_big.jpg")); //$NON-NLS-1$
	ImageIcon setRemDisabledIcon = new ImageIcon(getClass()
		.getResource(
			IconsHome.getIconsHomePath()
				+ "/set_reminder_big_inactive.jpg")); //$NON-NLS-1$
	ImageIcon setRemHoverIcon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/set_reminder_hover.jpg")); //$NON-NLS-1$

	JPanel center = new JPanel(new GridLayout(3, 2));
	center.setBackground(GuiConstants.wholePanelBackground);

	JLabel remLabel = new JLabel(" " + //$NON-NLS-1$
		Messages.getString("ReminderPanel.Reminder") + //$NON-NLS-1$
		":         ", SwingConstants.LEFT); //$NON-NLS-1$
	remLabel.setFont(GuiConstants.reminderPanelLabelFont);
	remLabel.setForeground(GuiConstants.labelColor);

	JLabel dateLabel = new JLabel(" " + //$NON-NLS-1$
		Messages.getString("ReminderPanel.Date") + //$NON-NLS-1$
		":"); //$NON-NLS-1$
	dateLabel.setFont(GuiConstants.reminderPanelLabelFont);
	dateLabel.setForeground(GuiConstants.labelColor);

	JLabel typeLabel = new JLabel(" " + //$NON-NLS-1$
		Messages.getString("ReminderPanel.Type") + //$NON-NLS-1$
		":"); //$NON-NLS-1$
	typeLabel.setFont(GuiConstants.reminderPanelLabelFont);
	typeLabel.setForeground(GuiConstants.labelColor);

	remValue = new JLabel(Messages.getString("ReminderPanel.Active")); //$NON-NLS-1$
	remValue.setFont(GuiConstants.reminderPanelLabelFont);
	remValue.setForeground(GuiConstants.textActiveForeground);

	dateValue = new JLabel("88:88 (20/88/2008)"); //$NON-NLS-1$
	dateValue.setFont(GuiConstants.reminderPanelLabelFontItalic);
	dateValue.setForeground(GuiConstants.textActiveForeground);

	typeValue = new JLabel(Messages.getString("ReminderPanel.VisualAlarm")); //$NON-NLS-1$
	typeValue.setFont(GuiConstants.reminderPanelLabelFontItalic);
	typeValue.setForeground(GuiConstants.textActiveForeground);

	center.add(remLabel);
	center.add(remValue);
	center.add(dateLabel);
	center.add(dateValue);
	center.add(typeLabel);
	center.add(typeValue);

	this.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	TitledBorder b = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("ReminderPanel.ReminderInfo") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b.setTitleFont(GuiConstants.reminderPanelTitleFont);
	b.setTitleColor(GuiConstants.reminderPanelTitleColor);
	this.setBorder(b);
	this.setBackground(GuiConstants.wholePanelBackground);

	gbc.gridx = 0;
	gbc.gridy = 0;
	this.add(center, gbc);
	gbc.gridx = 1;
	gbc.gridy = 0;
	gbc.insets = new Insets(0, 20, 0, 0);
	gbc.anchor = GridBagConstraints.PAGE_START;

	setReminderB = new JButton(setRemIcon);
	setReminderB.setFocusPainted(false);
	setReminderB.setBorderPainted(false);
	setReminderB.setContentAreaFilled(false);

	setReminderB.setPressedIcon(setRemHoverIcon);
	setReminderB.setDisabledIcon(setRemDisabledIcon);
	setReminderB.addActionListener(this.listener);
	this.add(setReminderB, gbc);
    }

    public void setInactiveReminder() {
	remValue.setText(Messages.getString("ReminderPanel.Inactive")); //$NON-NLS-1$
	typeValue.setText(" - "); //$NON-NLS-1$
	dateValue.setText(" - "); //$NON-NLS-1$
    }

    public void setActiveReminder() {
	remValue.setText(Messages.getString("ReminderPanel.Active")); //$NON-NLS-1$
    }

    public void setReminderDate(Calendar date) {
	// HH:MM (DD/MM/YYYY)
	String hour = "" + date.get(Calendar.HOUR_OF_DAY); //$NON-NLS-1$
	hour = (hour.length() == 1) ? "0" + hour : hour; //$NON-NLS-1$
	String minute = "" + date.get(Calendar.MINUTE); //$NON-NLS-1$
	minute = (minute.length() == 1) ? "0" + minute : minute; //$NON-NLS-1$
	String day = "" + date.get(Calendar.DAY_OF_MONTH); //$NON-NLS-1$
	day = (day.length() == 1) ? "0" + day : day; //$NON-NLS-1$
	String month = "" + (date.get(Calendar.MONTH) + 1); //$NON-NLS-1$
	//		System.err.println("x. SSSSS: " + date.get(Calendar.MONTH)); //$NON-NLS-1$
	//		System.err.println("1.SSSSS: " + month); //$NON-NLS-1$
	month = (month.length() == 1) ? "0" + month : month; //$NON-NLS-1$
	//		System.err.println("2.SSSSS: " + month); //$NON-NLS-1$
	String year = "" + date.get(Calendar.YEAR); //$NON-NLS-1$
	year = (year.length() == 1) ? "0" + year : year; //$NON-NLS-1$

	dateValue.setText(hour
		+ ":" + minute + " (" + day + "/" + month + "/" + year + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    public void setReminderType(String value) {
	typeValue.setText(value);
    }

    public void setReminderMessage(String message) {
	// TODO: evaluate if it is needed
    }

    public void setEnabled(boolean isEnabled) {
	super.setEnabled(isEnabled);
	setReminderB.setEnabled(isEnabled);
    }

    public void closeReminderInfoScreen() {
	listener.closeReminderInfoScreen();
    }

    public static void main(String[] str) {
	JFrame f = new JFrame();
	f.setBackground(GuiConstants.wholePanelBackground);
	f.add(new ReminderPanel(null));
	f.pack();
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JLabel remValue, dateValue, typeValue;
    private JButton setReminderB;
}
