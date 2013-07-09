package org.universAAL.agenda.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.universAAL.agenda.gui.components.ImagePanel;
import org.universAAL.agenda.gui.util.ButtonCreator;
import org.universAAL.agenda.gui.util.DateUtilities;
import org.universAAL.agenda.gui.util.GuiConstants;
import org.universAAL.ontology.agenda.Event;

/**
 * 
 * Search events view. Select month and year. Search by category. Search by
 * description. Decide on showing only present and future events or all. Set
 * maximum number of events.
 * 
 */
public class JSearchEvents extends JPanel implements IPersonaWindow {
    private static final long serialVersionUID = 1449159192602147400L;
    public static final String CARD_NAME = "JSearchEvents"; //$NON-NLS-1$

    public static final int MAIN_SCREEN = 1;
    public static final int NAVIGATION_SCREEN = 2;
    private static String[] resultsNumber = { "5", "10", "15", "20", "25+" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    public JPanel mainScreen;
    private JPanel navScreen;
    private JPanel titleScreen;
    private CalendarGUI parent;
    private JButton home, search;

    private JTextField descTextField;
    private JComboBox monthCB, yearCB, rNoCB, categoryList;
    private JButton presentBut, limitBut;
    private JLabel headerMessage;
    private JEventList eventScreen;

    final ImageIcon bigIcon = new ImageIcon(getClass().getResource(
	    "/icons/big_button.jpeg")); //$NON-NLS-1$
    final ImageIcon smallIcon = new ImageIcon(getClass().getResource(
	    "/icons/small_button.jpeg")); //$NON-NLS-1$
    final ImageIcon smallPressedIcon = new ImageIcon(getClass().getResource(
	    "/icons/small_button_pressed.jpeg")); //$NON-NLS-1$
    final ImageIcon bigPressedIcon = new ImageIcon(getClass().getResource(
	    "/icons/big_button_pressed.jpeg")); //$NON-NLS-1$

    public JSearchEvents(CalendarGUI parent, JEventList eventScreen) {
	this.parent = parent;
	this.eventScreen = eventScreen;
	initComponents();
    }

    private void initComponents() {
	this.navScreen = createNavScreen();
	this.mainScreen = createMainScreen();
	this.titleScreen = createTitleScreen();

	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel centerPanel = new JPanel(new BorderLayout());
	centerPanel.add(this.titleScreen, BorderLayout.NORTH);
	centerPanel.add(this.mainScreen, BorderLayout.CENTER);
	mainPanel.add(centerPanel, BorderLayout.CENTER);
	mainPanel.add(this.navScreen, BorderLayout.EAST);
	this.parent.addNewCardFrame(mainPanel, CARD_NAME);
    }

    private JPanel createTitleScreen() {
	ImageIcon persona_logo = new ImageIcon(getClass().getResource(
		"/icons/month_header.jpg")); //$NON-NLS-1$
	headerMessage = new JLabel();

	updateTitle();

	headerMessage.setForeground(GuiConstants.headerMessageForeground);
	headerMessage.setFont(GuiConstants.headerFont); //$NON-NLS-1$

	JPanel headPanel = new ImagePanel(persona_logo.getImage()); // (persona_logo.getImage());
	headPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	headPanel.setBackground(GuiConstants.headerPanelBackground);

	headPanel.add(headerMessage);

	// breadcrumbs
	JLabel l = new JLabel(Messages
		.getString("JEventInfo.Breadcrumb.Home.SearchEvents"));
	l.setBackground(GuiConstants.breadcrumbsLabelColor);
	l.setFont(GuiConstants.breadcrumbsLabelFont);

	JPanel whole = new JPanel(new FlowLayout(FlowLayout.LEFT));
	whole.setBackground(GuiConstants.wholePanelBackground);
	whole.add(headPanel);
	whole.add(l);
	return whole;
    }

    public void updateTitle() {
	headerMessage
		.setText("<html><font face=\"MyriadPro\" size=\"7\" color=\"white\"> " + //$NON-NLS-1$
			Messages
				.getString("JSearchEvents.SearchForAppointments") + //$NON-NLS-1$
			" </font>"); //$NON-NLS-1$
    }

    public JPanel createMainScreen() {
	JPanel main = new JPanel(new GridBagLayout());
	main.setBackground(GuiConstants.wholePanelBackground);

	// panel for category search
	JPanel categoryP = new JPanel(new GridLayout(1, 1));
	TitledBorder b = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("JSearchEvents.SearchByCategory") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b.setTitleFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	b.setTitleColor(GuiConstants.jSearchEventsTitleColor);
	categoryP.setBorder(b);
	categoryP.setBackground(GuiConstants.jSearchEventsPanelBackground);

	List<String> allCategories = this.parent.getAllEventCategories();
	allCategories.add(""); //$NON-NLS-1$
	Collections.sort(allCategories);
	categoryList = new JComboBox(allCategories.toArray());
	categoryList.setFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	categoryList.setForeground(GuiConstants.jSearchEventsBlueForeground);
	categoryList.setBackground(GuiConstants.jSearchEventsBackground);
	categoryList.setOpaque(true);
	categoryList.setEditable(true);
	categoryP.add(categoryList);

	// panel for description search
	JPanel descriptionP = new JPanel(new GridLayout(1, 1));
	TitledBorder b1 = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("JSearchEvents.SearchByDescription") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b1.setTitleFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	b1.setTitleColor(GuiConstants.jSearchEventsTitleColor);
	descriptionP.setBorder(b1);
	descriptionP.setBackground(GuiConstants.jSearchEventsBackground);

	descTextField = new JTextField(32);
	descTextField.setHorizontalAlignment(JTextField.LEFT);
	descTextField.setFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	descTextField.setForeground(GuiConstants.jSearchEventsGreenForeground);
	descTextField.setBackground(GuiConstants.jSearchEventsBackground);
	descTextField.setOpaque(true);
	descTextField.setEnabled(true);
	descriptionP.add(descTextField);

	// panel for time search
	GridLayout gl = new GridLayout(2, 2);
	gl.setVgap(5);
	JPanel timeMainP = new JPanel(new BorderLayout());
	JPanel timeP = new JPanel(gl);
	TitledBorder b2 = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("JSearchEvents.SearchByDescription") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b2.setTitleFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	b2.setTitleColor(GuiConstants.jSearchEventsTitleColor);
	timeP.setBorder(b2);
	timeP.setBackground(GuiConstants.jSearchEventsBackground);

	JLabel monthL = new JLabel(Messages
		.getString("JSearchEvents.PickAMonth") + //$NON-NLS-1$
		":               "); //$NON-NLS-1$
	monthL.setHorizontalAlignment(JLabel.RIGHT);
	monthL.setFont(GuiConstants.jSearchEventsLabelFont); //$NON-NLS-1$
	monthL.setForeground(GuiConstants.jSearchEventsBlueForeground);
	monthL.setBackground(GuiConstants.jSearchEventsBackground);
	monthL.setOpaque(true);
	JLabel yearL = new JLabel(
		Messages.getString("JSearchEvents.PickAYear") + //$NON-NLS-1$
			":             "); //$NON-NLS-1$
	yearL.setHorizontalAlignment(JLabel.RIGHT);
	yearL.setFont(GuiConstants.jSearchEventsLabelFont); //$NON-NLS-1$
	yearL.setForeground(GuiConstants.jSearchEventsGreenForeground);
	yearL.setBackground(GuiConstants.jSearchEventsBackground);
	yearL.setOpaque(true);

	yearCB = new JComboBox(buildYearSelectionCombo());
	yearCB.setFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	yearCB.setForeground(GuiConstants.jSearchEventsBlueForeground);
	yearCB.setBackground(GuiConstants.jSearchEventsBackground);
	yearCB.setOpaque(true);

	monthCB = new JComboBox(DateUtilities.months);
	monthCB.setFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	monthCB.setForeground(GuiConstants.jSearchEventsGreenForeground);
	monthCB.setBackground(GuiConstants.jSearchEventsBackground);
	monthCB.setOpaque(true);
	monthCB.setEnabled(false);
	yearCB.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if (yearCB.getSelectedIndex() == 0) {
		    monthCB.setEnabled(false);
		} else {
		    monthCB.setEnabled(true);
		}
	    }
	});

	timeP.add(monthL);
	timeP.add(monthCB);
	timeP.add(yearL);
	timeP.add(yearCB);
	timeMainP.add(timeP, BorderLayout.NORTH);
	JPanel empty = new JPanel();
	empty.setBackground(GuiConstants.jSearchEventsBackground);
	timeMainP.add(empty, BorderLayout.CENTER);

	// Miscellaneous options
	JPanel miscP = new JPanel(new GridLayout(2, 2));
	TitledBorder b4 = BorderFactory.createTitledBorder(" " + //$NON-NLS-1$
		Messages.getString("JSearchEvents.MiscOptions") + //$NON-NLS-1$
		" "); //$NON-NLS-1$
	b4.setTitleFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	b4.setTitleColor(GuiConstants.jSearchEventsTitleColor);
	miscP.setBorder(b4);
	miscP.setBackground(GuiConstants.jSearchEventsBackground);

	JLabel onlyUpcomming = new JLabel(
		"<html>" + //$NON-NLS-1$
			Messages
				.getString("JSearchEvents.ShoWOnlyPresentAndFurureEvents") + //$NON-NLS-1$
			"</html>"); //$NON-NLS-1$
	onlyUpcomming.setHorizontalAlignment(JLabel.LEFT);
	onlyUpcomming.setFont(GuiConstants.jSearchEventsLabelFont); //$NON-NLS-1$
	onlyUpcomming.setForeground(GuiConstants.jSearchEventsGreenForeground);
	onlyUpcomming.setBackground(GuiConstants.jSearchEventsBackground);
	onlyUpcomming.setOpaque(true);

	presentBut = ButtonCreator.createButton(smallIcon, Messages
		.getString("JSearchEvents.No"), GuiConstants.smallButtonsFont);
	presentBut.setFocusPainted(false);
	presentBut.setBorderPainted(false);
	presentBut.setContentAreaFilled(false);
	presentBut.setPressedIcon(smallPressedIcon);
	presentBut.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (presentBut.getText().contentEquals(
			Messages.getString("JSearchEvents.Yes"))) {
		    presentBut.setText(Messages.getString("JSearchEvents.No"));
		} else {
		    presentBut.setText(Messages.getString("JSearchEvents.Yes"));
		}
	    }
	});

	JLabel limitEvents = new JLabel("<html>" + //$NON-NLS-1$
		Messages.getString("JSearchEvents.MaximumNumberOfEvents") + //$NON-NLS-1$
		": <html>"); //$NON-NLS-1$
	limitEvents.setHorizontalAlignment(JLabel.LEFT);
	limitEvents.setFont(GuiConstants.jSearchEventsLabelFont); //$NON-NLS-1$
	limitEvents.setForeground(GuiConstants.jSearchEventsGreenForeground);
	limitEvents.setBackground(GuiConstants.jSearchEventsBackground);
	limitEvents.setOpaque(true);

	JPanel dummy1 = new JPanel(new BorderLayout());

	rNoCB = new JComboBox(resultsNumber);
	rNoCB.setFont(GuiConstants.jSearchEventsFont); //$NON-NLS-1$
	rNoCB.setForeground(GuiConstants.jSearchEventsBlueForeground);
	rNoCB.setBackground(GuiConstants.jSearchEventsBackground);
	rNoCB.setOpaque(true);
	rNoCB.setVisible(false);

	limitBut = ButtonCreator.createButton(smallIcon, Messages
		.getString("JSearchEvents.No"), GuiConstants.smallButtonsFont);
	limitBut.setFocusPainted(false);
	limitBut.setBorderPainted(false);
	limitBut.setContentAreaFilled(false);
	limitBut.setPressedIcon(smallPressedIcon);
	limitBut.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (limitBut.getText().contentEquals(
			Messages.getString("JSearchEvents.Yes"))) {
		    limitBut.setText(Messages.getString("JSearchEvents.No"));
		    rNoCB.setSelectedIndex(resultsNumber.length - 1);
		    rNoCB.setVisible(false);
		} else {
		    limitBut.setText(Messages.getString("JSearchEvents.Yes"));
		    rNoCB.setVisible(true);
		}
	    }
	});

	dummy1.add(rNoCB, BorderLayout.WEST);
	JLabel dL3 = new JLabel(" "); //$NON-NLS-1$
	dL3.setFont(GuiConstants.jSearchEventsDummyLabelFont); //$NON-NLS-1$
	dummy1.add(dL3, BorderLayout.CENTER);
	JLabel dL1 = new JLabel(" "); //$NON-NLS-1$
	dL1.setFont(GuiConstants.jSearchEventsDummyLabelFont); //$NON-NLS-1$
	JLabel dL2 = new JLabel(" "); //$NON-NLS-1$
	dL2.setFont(GuiConstants.jSearchEventsDummyLabelFont); //$NON-NLS-1$
	dummy1.add(dL1, BorderLayout.NORTH);
	dummy1.add(dL2, BorderLayout.SOUTH);
	dummy1.setBackground(GuiConstants.jSearchEventsBackground);
	JPanel dummy10 = new JPanel(new GridLayout(1, 2));
	dummy10.add(presentBut);
	dummy10.add(new JLabel("")); //$NON-NLS-1$
	dummy10.setBackground(GuiConstants.jSearchEventsBackground);
	JPanel dummy11 = new JPanel(new GridLayout(1, 2));
	dummy11.add(limitBut);
	dummy11.add(dummy1);
	dummy11.setBackground(GuiConstants.jSearchEventsBackground);
	miscP.add(onlyUpcomming);
	miscP.add(dummy10);
	miscP.add(limitEvents);
	miscP.add(dummy11);

	GridBagConstraints gbc = new GridBagConstraints();

	setGridBagConstraints(gbc, 0, 0, 1, 1, 1, 1,
		GridBagConstraints.FIRST_LINE_START);
	gbc.fill = GridBagConstraints.HORIZONTAL;
	main.add(timeMainP, gbc);
	setGridBagConstraints(gbc, 0, 1, 1, 1, 1, 1,
		GridBagConstraints.FIRST_LINE_START);
	main.add(categoryP, gbc);
	setGridBagConstraints(gbc, 0, 2, 1, 1, 1, 1,
		GridBagConstraints.FIRST_LINE_START);
	main.add(descriptionP, gbc);
	setGridBagConstraints(gbc, 0, 3, 1, 1, 1, 1,
		GridBagConstraints.FIRST_LINE_START);
	main.add(miscP, gbc);

	JPanel grigoro = new JPanel(new BorderLayout());
	grigoro.setBackground(GuiConstants.jSearchEventsBackground);
	JLabel adeio1 = new JLabel("    "); //$NON-NLS-1$
	grigoro.add(adeio1, BorderLayout.CENTER);

	grigoro.add(main, BorderLayout.EAST);
	return grigoro;

    }

    private String[] buildYearSelectionCombo() {
	int numberOfPreviousYears = 6;

	// TODO: possible property configured externally
	int numberOfNextYears = 5;
	// TODO: possible property configured externally
	int sumYears = numberOfNextYears + numberOfPreviousYears + 1; // +1:
	// current
	// year
	String[] years = new String[sumYears + 1];// +1: all years
	years[0] = "<html><i>" + //$NON-NLS-1$
		Messages.getString("JSearchEvents.AllYears") + //$NON-NLS-1$
		"</i></html>"; //$NON-NLS-1$
	Calendar c = Calendar.getInstance();
	int baseYear = c.get(Calendar.YEAR) - numberOfPreviousYears;

	for (int i = 1; i <= sumYears;) {
	    years[i++] = "" + (baseYear + i); //$NON-NLS-1$
	}

	return years;

    }

    private List<Event> getFilteredEvents() {
	String category = categoryList.getSelectedItem().toString().trim();// catTextField.getText().trim();
	String description = descTextField.getText().trim();
	int month;
	int year;
	try {
	    if (!(monthCB.isEnabled()))
		month = -1;
	    else
		month = Integer.valueOf(monthCB.getSelectedIndex()).intValue();
	} catch (NumberFormatException nfe) {
	    month = -1;
	}

	try {
	    year = Integer.valueOf((String) yearCB.getSelectedItem())
		    .intValue();
	} catch (NumberFormatException nfe) {
	    year = -1;
	    month = -1;
	}

	boolean notPastEvents = false;
	if (presentBut.getText().contentEquals(
		Messages.getString("JSearchEvents.Yes")))
	    notPastEvents = true;
	int eventMaxNo = -1;
	if (limitBut.getText().contentEquals(
		Messages.getString("JSearchEvents.Yes"))) {
	    try {
		eventMaxNo = Integer.valueOf((String) rNoCB.getSelectedItem())
			.intValue();
	    } catch (NumberFormatException nfe) {
		eventMaxNo = -1;
	    }
	}
	return parent.getFilteredEvents(category, description, year, month,
		notPastEvents, eventMaxNo);
    }

    private JPanel createNavScreen() {
	search = ButtonCreator.createButton(bigIcon, Messages
		.getString("JSearchEvents.Search"),
		GuiConstants.bigButtonsBigFont);
	search.setFocusPainted(false);
	search.setBorderPainted(false);
	search.setContentAreaFilled(false);
	search.setPressedIcon(bigPressedIcon);
	search.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent a) {
		List<Event> events = getFilteredEvents();
		eventScreen.updateMainScreen(events);
		eventScreen.updateTitleScreen(events.size());
		parent.showScreen(JEventList.CARD_NAME);
	    }
	});

	home = ButtonCreator.createButton(bigIcon, Messages
		.getString("JSearchEvents.Home"),
		GuiConstants.bigButtonsBigFont);
	home
		.setToolTipText(Messages
			.getString("JSearchEvents.RemiveThisEvent")); //$NON-NLS-1$
	home.setFocusPainted(false);
	home.setBorderPainted(false);
	home.setContentAreaFilled(false);
	home.setPressedIcon(bigPressedIcon);
	home.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent a) {
		parent.showScreen(JCalendar.CARD_NAME);
	    }
	});

	// Create the right zone of buttons
	JPanel homeP = new JPanel(new GridLayout(6, 1));
	homeP.setBackground(GuiConstants.jSearchEventsBackground);

	// before clock // homeP.add(new JLabel()); //<< Create a void combined
	// with

	AnalogClock clock = new AnalogClock();
	homeP.add(clock);

	homeP.add(home);
	homeP.add(search);
	JLabel l1 = new JLabel(""); //$NON-NLS-1$
	l1.setBackground(GuiConstants.jSearchEventsBackground);
	JLabel l2 = new JLabel(""); //$NON-NLS-1$
	l1.setBackground(GuiConstants.jSearchEventsBackground);
	homeP.add(l1);
	homeP.add(l2);
	homeP.add(CurrentDateAndDigitalClock.getDateAndDigitalClockPanel());

	return homeP;
    }

    public JPanel getMainScreenPanel() {
	return this.mainScreen;
    }

    public JPanel getNavigationScreenPanel() {
	return this.navScreen;
    }

    public JPanel getTitleScreenPanel() {
	return this.titleScreen;
    }

    public static void main(String[] str) {
	JFrame f = new JFrame();
	f.add(new JSearchEvents(null, null).createMainScreen());
	f.pack();
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setGridBagConstraints(GridBagConstraints c, int gridx,
	    int gridy, int gridwidth, int gridheight, double weightx,
	    double weighty, int anchor) {
	c.gridx = gridx;
	c.gridy = gridy;
	c.gridwidth = gridwidth;
	c.gridheight = gridheight;
	c.weightx = weightx;
	c.weighty = weighty;
	c.anchor = anchor;
    }
}
