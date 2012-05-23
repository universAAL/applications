package org.universAAL.agenda.gui.components;

import java.awt.Color;
import java.awt.Font;
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

import org.universAAL.agenda.gui.EventInfoListener;
import org.universAAL.agenda.gui.osgi.Activator;

public class ReminderPanel extends JPanel {
    private static final long serialVersionUID = 7749867619210140899L;
    private EventInfoListener listener;

    public ReminderPanel(EventInfoListener listener) {
	this.listener = listener;
	initComponents();
    }

    private void initComponents() {
	ImageIcon setRemIcon = new ImageIcon(getClass().getResource(
		Activator.ICON_PATH_PREFIX + "/set_reminder_big.jpg")); //$NON-NLS-1$
	ImageIcon setRemDisabledIcon = new ImageIcon(getClass().getResource(
		Activator.ICON_PATH_PREFIX + "/set_reminder_big_inactive.jpg")); //$NON-NLS-1$
	ImageIcon setRemHoverIcon = new ImageIcon(getClass().getResource(
		Activator.ICON_PATH_PREFIX + "/set_reminder_hover.jpg")); //$NON-NLS-1$
	Font mediumFont = new Font("MyriadPro", Font.PLAIN, 25); //$NON-NLS-1$
	Font smallFont = new Font("MyriadPro", Font.PLAIN, 20); //$NON-NLS-1$
	Font smallItalicsFont = new Font("MyriadPro", Font.ITALIC, 20); //$NON-NLS-1$
	Color labelColor = new Color(0x565656);
	Color textColor = new Color(0x00c129);

	JPanel center = new JPanel(new GridLayout(3, 2));
	center.setBackground(Color.WHITE);

	JLabel remLabel = new JLabel(" " + //$NON-NLS-1$
		Messages.getString("ReminderPanel.Reminder") + //$NON-NLS-1$
		":         ", SwingConstants.LEFT); //$NON-NLS-1$
	remLabel.setFont(smallFont);
	remLabel.setForeground(labelColor);

	JLabel dateLabel = new JLabel(" " + //$NON-NLS-1$
		Messages.getString("ReminderPanel.Date") + //$NON-NLS-1$
		":"); //$NON-NLS-1$
	dateLabel.setFont(smallFont);
	dateLabel.setForeground(labelColor);

	JLabel typeLabel = new JLabel(" " + //$NON-NLS-1$
		Messages.getString("ReminderPanel.Type") + //$NON-NLS-1$
		":"); //$NON-NLS-1$
	typeLabel.setFont(smallFont);
	typeLabel.setForeground(labelColor);

	remValue = new JLabel(Messages.getString("ReminderPanel.Active")); //$NON-NLS-1$
	remValue.setFont(smallFont);
	remValue.setForeground(textColor);

	dateValue = new JLabel("88:88 (20/88/2008)"); //$NON-NLS-1$
	dateValue.setFont(smallItalicsFont);
	dateValue.setForeground(textColor);

	typeValue = new JLabel(Messages.getString("ReminderPanel.VisualAlarm")); //$NON-NLS-1$
	typeValue.setFont(smallItalicsFont);
	typeValue.setForeground(textColor);

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
	b.setTitleFont(mediumFont);
	b.setTitleColor(new Color(0x565656));
	this.setBorder(b);
	this.setBackground(Color.WHITE);

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
	f.setBackground(Color.white);
	f.add(new ReminderPanel(null));
	f.pack();
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JLabel remValue, dateValue, typeValue;
    private JButton setReminderB;
}
