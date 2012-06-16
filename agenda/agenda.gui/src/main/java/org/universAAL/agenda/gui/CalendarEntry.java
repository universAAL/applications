package org.universAAL.agenda.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.universAAL.agenda.gui.util.GuiConstants;

/**
 * Calendar view used in first screen where selecting adding/deleting calendars
 * or selecting calendars to add/see their events.
 * 
 * @author kagnantis
 * @author eandgrg
 * 
 */
class CalendarEntry extends JPanel {

    private static final ImageIcon CHECKED_ICON = new ImageIcon(
	    CalendarEntry.class.getResource(IconsHome.getIconsHomePath()
		    + "/checked.jpg"));
    private static ImageIcon NOT_CHECKED_ICON = new ImageIcon(
	    CalendarEntry.class.getResource(IconsHome.getIconsHomePath()
		    + "/unchecked.jpg"));
    private static final ImageIcon HOVER_CHECKED_ICON = new ImageIcon(
	    CalendarEntry.class.getResource(IconsHome.getIconsHomePath()
		    + "/hover_checked.jpg"));
    private static ImageIcon HOVER_NOT_CHECKED_ICON = new ImageIcon(
	    CalendarEntry.class.getResource(IconsHome.getIconsHomePath()
		    + "/hover_unchecked.jpg"));

    private final String calendarName;
    private final JButton selectButton;
    private final JLabel textLabel;

    private static final long serialVersionUID = -9023051747414682371L;
    boolean isSelected = false;

    CalendarEntry(String calendarName, boolean isSelected) {
	this.calendarName = calendarName;
	this.isSelected = isSelected;
	this.selectButton = new JButton();
	this.textLabel = new JLabel("   " + calendarName);
	updateGUI();
	initComponents();
    }

    boolean isSelected() {
	return this.isSelected;
    }

    String getCalendarName() {
	return this.calendarName;
    }

    void setSelected(boolean isSelected) {
	this.isSelected = isSelected;
	updateGUI();
    }

    void updateGUI() {
	if (isSelected) {
	    this.selectButton.setIcon(CHECKED_ICON);
	    this.textLabel.setForeground(GuiConstants.calendarEntrySelectedForeground);
	} else {
	    this.selectButton.setIcon(NOT_CHECKED_ICON);
	    this.textLabel.setForeground(GuiConstants.calendarEntryNotSelectedForeground);
	}

    }

    void initComponents() {
	this.setLayout(new GridBagLayout());
	this.setBackground(GuiConstants.wholePanelBackground);
	GridBagConstraints c = new GridBagConstraints();

	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	c.weightx = 100.75;

	textLabel.setFont(GuiConstants.calendarEntryLabelFont);
	this.add(textLabel, c);

	selectButton.setFocusPainted(false);
	selectButton.setBorderPainted(false);
	selectButton.setContentAreaFilled(false);
	selectButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		setSelected(!isSelected);
	    }
	});

	selectButton.addMouseListener(new MouseAdapter() {
	    public void mouseEntered(MouseEvent event) {
		if (isSelected)
		    selectButton.setIcon(HOVER_CHECKED_ICON);
		else
		    selectButton.setIcon(HOVER_NOT_CHECKED_ICON);
	    }

	    public void mouseExited(MouseEvent event) {
		if (isSelected)
		    selectButton.setIcon(CHECKED_ICON);
		else
		    selectButton.setIcon(NOT_CHECKED_ICON);
	    }

	});
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = .25;
	c.gridx = 1;
	c.gridy = 0;
	c.anchor = GridBagConstraints.LINE_START;

	this.add(selectButton, c);

    }

    public static void main(String[] str) {
	JFrame frame = new JFrame();
	frame.add(new CalendarEntry("SomeCalendar", false));
	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
