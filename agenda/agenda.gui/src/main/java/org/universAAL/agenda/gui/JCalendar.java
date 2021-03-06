package org.universAAL.agenda.gui;

import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.TimeInterval;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.agenda.gui.components.ImagePanel;
import org.universAAL.agenda.gui.util.ButtonCreator;
import org.universAAL.agenda.gui.util.DateInstance;
import org.universAAL.agenda.gui.util.DateUtilities;
import org.universAAL.agenda.gui.util.GuiConstants;

/**
 * 
 * Class showing calendar month view.
 * 
 */
public class JCalendar implements IPersonaWindow {

    private JPanel mainScreen;
    private JPanel navScreen;
    private JPanel titleScreen;

    public static final String CARD_NAME = "JCalendarCard";
    private int monthShown;
    private int yearShown;
    private int dayShow;
    private JDateButton[] days;
    private JButton[] weeks;
    private JPanel weekPanel;
    private JPanel dayPanel;
    public Calendar calendar;
    private JPanel daysOfTheWeekPanel;
    private JLabel headerMessage;
    private Calendar today;
    private int firstDayOfWeek;
    private CalendarGUI parent;
    private JEventList eventListPanel = null;
    private JSearchEvents eventSearchScreen = null;

    public JCalendar(CalendarGUI rs) {
	this.parent = rs;
	initComponents();
	calendar = Calendar.getInstance();
	populateCalendar(Calendar.getInstance());
    }

    private void initComponents() {
	this.mainScreen = createMainScreen();
	this.navScreen = createNavScreen();
	this.titleScreen = createTitleScreen();
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel centerPanel = new JPanel(new BorderLayout());
	centerPanel.add(this.titleScreen, BorderLayout.NORTH);
	centerPanel.add(this.mainScreen, BorderLayout.CENTER);
	mainPanel.add(centerPanel, BorderLayout.CENTER);
	mainPanel.add(this.navScreen, BorderLayout.EAST);
	this.parent.addNewCardFrame(mainPanel, CARD_NAME);
	this.parent.showScreen(JSelectionCalendar.CARD_NAME);
    }

    private JPanel createTitleScreen() {
	ImageIcon uAAL_logo = new ImageIcon(getClass().getResource(
		"/icons/month_header.jpg")); //$NON-NLS-1$
	headerMessage = new JLabel();

	String month = "May"; //$NON-NLS-1$
	String year = "2009"; //$NON-NLS-1$
	updateTitle(month, year);

	headerMessage.setForeground(GuiConstants.headerMessageForeground);
	headerMessage.setFont(GuiConstants.headerFont); //$NON-NLS-1$

	JPanel headPanel = new ImagePanel(uAAL_logo.getImage());
	headPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	headPanel.setBackground(GuiConstants.headerPanelBackground);

	headPanel.add(headerMessage);

	// breadcrumbs
	// JLabel l = new JLabel("<html><font face=\"MyriadPro\" size=\"5\" >"
	// + Messages.getString("JCalendar.Breadcrumb.Home")
	// + "</font></html>");
	JLabel l = new JLabel(Messages.getString("JCalendar.Breadcrumb.Home"));
	l.setBackground(GuiConstants.breadcrumbsLabelColor);
	l.setFont(GuiConstants.breadcrumbsLabelFont);

	JPanel whole = new JPanel(new FlowLayout(FlowLayout.LEFT));
	whole.setBackground(GuiConstants.wholePanelBackground);
	whole.add(headPanel);
	whole.add(l);
	return whole;
    }

    private JPanel createMainScreen() {
	JPanel panel = new JPanel();
	days = new JDateButton[6 * 7];
	weeks = new JButton[6];
	calendar = Calendar.getInstance();
	panel.setLayout(new BorderLayout());

	daysOfTheWeekPanel = new JPanel(new GridLayout(1, 7));
	daysOfTheWeekPanel
		.setBackground(GuiConstants.jCalendarWeekDaysPanelBackground);
	JButton sun = new DecoratorButton(Messages.getString("JCalendar.Sun")); //$NON-NLS-1$
	sun.setFont(GuiConstants.jCalendarWeekDaysFont); //$NON-NLS-1$
	sun.setBackground(GuiConstants.jCalendarWeekDaysBackground); // light
	// blue
	sun.setForeground(GuiConstants.jCalendarWeekDaysForeground);

	JButton mon = new DecoratorButton(Messages.getString("JCalendar.Mon")); //$NON-NLS-1$
	mon.setFont(GuiConstants.jCalendarWeekDaysFont); //$NON-NLS-1$
	mon.setBackground(GuiConstants.jCalendarWeekDaysBackground);
	mon.setForeground(GuiConstants.jCalendarWeekDaysForeground);

	JButton tue = new DecoratorButton(Messages.getString("JCalendar.Tue")); //$NON-NLS-1$
	tue.setFont(GuiConstants.jCalendarWeekDaysFont); //$NON-NLS-1$
	tue.setBackground(GuiConstants.jCalendarWeekDaysBackground);
	tue.setForeground(GuiConstants.jCalendarWeekDaysForeground);

	JButton wed = new DecoratorButton(Messages.getString("JCalendar.Wed")); //$NON-NLS-1$
	wed.setFont(GuiConstants.jCalendarWeekDaysFont); //$NON-NLS-1$
	wed.setBackground(GuiConstants.jCalendarWeekDaysBackground);
	wed.setForeground(GuiConstants.jCalendarWeekDaysForeground);

	JButton thu = new DecoratorButton(Messages.getString("JCalendar.Thu")); //$NON-NLS-1$
	thu.setFont(GuiConstants.jCalendarWeekDaysFont); //$NON-NLS-1$
	thu.setBackground(GuiConstants.jCalendarWeekDaysBackground);
	thu.setForeground(GuiConstants.jCalendarWeekDaysForeground);

	JButton fri = new DecoratorButton(Messages.getString("JCalendar.Fri")); //$NON-NLS-1$
	fri.setFont(GuiConstants.jCalendarWeekDaysFont); //$NON-NLS-1$
	fri.setBackground(GuiConstants.jCalendarWeekDaysBackground);
	fri.setForeground(GuiConstants.jCalendarWeekDaysForeground);

	JButton sat = new DecoratorButton(Messages.getString("JCalendar.Sat")); //$NON-NLS-1$
	sat.setFont(GuiConstants.jCalendarWeekDaysFont); //$NON-NLS-1$
	sat.setBackground(GuiConstants.jCalendarWeekDaysBackground);
	sat.setForeground(GuiConstants.jCalendarWeekDaysForeground);

	JButton[] allDays = new JButton[] { sun, mon, tue, wed, thu, fri, sat };

	// create panel week
	today = Calendar.getInstance();
	firstDayOfWeek = today.getFirstDayOfWeek();

	for (int d = 0; d < 7; ++d) {
	    daysOfTheWeekPanel.add(allDays[(firstDayOfWeek + d - 1) % 7]);
	}

	dayPanel = new JPanel(new GridLayout(6, 7));
	dayPanel.setBackground(GuiConstants.jCalendarDayPanelBackground);
	for (int y = 0; y < 6; ++y) {
	    for (int x = 0; x < 7; ++x) {
		int index = (y * 7) + x;

		JDateButton common_day = new JDateButton(JDateButton.NO_DATE,
			false);

		days[index] = common_day;
		dayPanel.add(days[index]);
	    }
	}

	JPanel middle1 = new JPanel(new BorderLayout());
	middle1.add(daysOfTheWeekPanel, BorderLayout.NORTH);
	middle1.add(dayPanel, BorderLayout.CENTER);

	weekPanel = new JPanel();
	weekPanel.setLayout(new GridLayout(6, 1));
	weeks = new JButton[6];

	for (int i = 0; i < 6; i++) {
	    weeks[i] = new DecoratorButton();
	    weeks[i].setMargin(new Insets(0, 0, 0, 0));
	    weeks[i].setFocusPainted(false);
	    weeks[i].setForeground(GuiConstants.jCalendarWeeksForeground);

	    if (i != 0) {
		weeks[i].setText("0" + (i + 1)); //$NON-NLS-1$
	    }

	    weekPanel.add(weeks[i]);
	}

	JPanel middle2 = new JPanel(new BorderLayout());
	// button in left above corner where week numbers and days of the week
	// meet
	JButton empty = new DecoratorButton(" "); //$NON-NLS-1$
	// although font is not needed since there is no text, if font is not
	// set then this button is too small
	empty.setFont(GuiConstants.jCalendarWeekDaysFont); //$NON-NLS-1$
	// empty.setBackground(Color.white);
	empty.setBackground(GuiConstants.wholePanelBackground);
	// empty.setForeground(new java.awt.Color(101, 104, 9));

	middle2.add(empty, BorderLayout.NORTH);
	middle2.add(weekPanel, BorderLayout.CENTER);

	panel.add(middle1, BorderLayout.CENTER);
	panel.add(middle2, BorderLayout.WEST);

	return panel;
    }

    public JPanel createNavScreen() {

	final ImageIcon bigIcon = new ImageIcon(getClass().getResource(
		"/icons/big_button.jpeg")); //$NON-NLS-1$
	  final ImageIcon bigPressedIcon = new ImageIcon(getClass().getResource(
	    "/icons/big_button_pressed.jpeg")); //$NON-NLS-1$

	final JButton next_month = ButtonCreator.createButton(
		bigIcon, Messages.getString("JCalendar.NextMonth"),
		GuiConstants.bigButtonsSmallFont);
	next_month.setFocusPainted(false);
	next_month.setBorderPainted(false);
	next_month.setContentAreaFilled(false);
	next_month.setPressedIcon(bigPressedIcon);
	next_month.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		showNextMonth();
		updateTitleWithCurrentDate();
	    }
	});
	
	final JButton previous_month = ButtonCreator.createButton(
		bigIcon, Messages.getString("JCalendar.PreviousMonth"),
		GuiConstants.bigButtonsSmallFont);
	previous_month.setFocusPainted(false);
	previous_month.setBorderPainted(false);
	previous_month.setContentAreaFilled(false);
	previous_month.setPressedIcon(bigPressedIcon);
	previous_month.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		showPreviousMonth();
		updateTitleWithCurrentDate();
	    }
	});

	final JButton gohome =ButtonCreator.createButton(
		bigIcon, Messages.getString("JCalendar.Home"),
		GuiConstants.bigButtonsBigFont); 
	gohome.setFocusPainted(false);
	gohome.setBorderPainted(false);
	gohome.setContentAreaFilled(false);
	gohome.setPressedIcon(bigPressedIcon);
	gohome.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		showCurrentMonth();
		updateTitleWithCurrentDate();
		parent.showScreen(JSelectionCalendar.CARD_NAME);
	    }
	});

	final JButton searchButton =ButtonCreator.createButton(
		bigIcon, Messages.getString("JCalendar.Search"),
		GuiConstants.bigButtonsBigFont); 
	searchButton.setFocusPainted(false);
	searchButton.setBorderPainted(false);
	searchButton.setContentAreaFilled(false);
	searchButton.setPressedIcon(bigPressedIcon);
	searchButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		if (eventSearchScreen == null) {
		    createEventSearchScreen();
		}
		eventSearchScreen.updateTitle();
		// proceed without selecting a day -> null out any previous date
		// reference
		parent.setSelectedDate(null);
		parent.showScreen(JSearchEvents.CARD_NAME);
	    }
	});

	

	// Create the right zone of buttons
	JPanel main = new JPanel(new GridLayout(6, 1));
	main.setBackground(GuiConstants.jCalendarRightButtonsPanelBackground);

	JButton dummyButton =ButtonCreator.createButton(
		bigIcon, " ",
		GuiConstants.bigButtonsBigFont); 
	dummyButton.setVisible(false);

	// the necessary buttons are included

	// before adding clock below line was used
	// main.add(new JLabel()); //<<Create a void combined with
	// nav.setLayout(new GridLayout(6, 1));

	AnalogClock clock = new AnalogClock();

	main.add(clock);
	main.add(next_month);
	main.add(previous_month);
	main.add(searchButton);
	main.add(gohome);
	main.add(CurrentDateAndDigitalClock.getDateAndDigitalClockPanel());

	return main;
    }

    private void createEventSearchScreen() {
	if (eventListPanel == null)
	    createEventListPanel();
	eventSearchScreen = new JSearchEvents(this.getParentScreen(),
		this.eventListPanel);
    }

    private void createEventListPanel() {
	eventListPanel = new JEventList(getParentScreen());
	eventListPanel.setActiveCalendarScreen(this);
    }

    public void updateTitle(String month, String year) {
	headerMessage
		.setText("<html><font face=\"MyriadPro\" size=\"7\" color=\"white\">" + month + ", </font>" + //$NON-NLS-1$ //$NON-NLS-2$
			"<font size=\"6\" color=\"white\">"
			+ year
			+ "<p></font>" + //$NON-NLS-1$ //$NON-NLS-2$
			"<font size=\"4\" color=\"#2f594f\">"
			+ Messages
				.getString("JCalendar.PleaseSelectADayToSeeMoreInfo") + "</font>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    // With 'currentDate' I mean the date which is stored in rhis.calendar
    // object
    // not the present day
    private void updateTitleWithCurrentDate() {
	String month = DateUtilities.months[calendar.get(Calendar.MONTH)];
	String year = "" + calendar.get(Calendar.YEAR); //$NON-NLS-1$
	updateTitle(month, year);
    }

    public JPanel getMainScreenPanel() {
	return this.mainScreen;
    }

    public JPanel getNavigationScreenPanel() {
	return this.navScreen;
    }

    public JPanel getTitleScreenPanel() {
	return null;
    }

    private List<Integer> extractDaysOfTheMonth(List<Event> events) {
	List<Integer> l = new ArrayList<Integer>();
	for (Event e : events) {
	    EventDetails ed = e.getEventDetails();
	    if (ed == null)
		continue;
	    TimeInterval ti = ed.getTimeInterval();
	    if (ti == null)
		continue;
	    XMLGregorianCalendar calStart = ti.getStartTime();
	    XMLGregorianCalendar calEnd = ti.getEndTime();
	    int sDay = -1;
	    int eDay = sDay;
	    if (calStart != null) {
		sDay = calStart.getDay();
		if (calEnd != null)
		    eDay = calEnd.getDay();
		else
		    eDay = sDay;
	    } else {
		if (calEnd != null) {
		    eDay = calEnd.getDay();
		    sDay = eDay;
		}
	    }

	    l.add(sDay);
	}
	return l;
    }

    public void populateCalendar(Calendar currentCal) {
	setYearAndMonth(currentCal.get(Calendar.YEAR), currentCal
		.get(Calendar.MONTH));
	updateTitleWithCurrentDate();

	int tempMonth = getMonth() + 1;
	List<Event> monthEvents = parent.getFilteredEvents(null, null,
		getYear(), tempMonth, false, -1);
	// show only visible Events
	int removed = 0;
	for (Iterator<Event> it = monthEvents.iterator(); it.hasNext();) {
	    if (!(it.next().isVisible())) {
		it.remove();
		++removed;
	    }
	}
	System.out.println("Invisible events(1): " + removed); //$NON-NLS-1$

	List<Integer> allDays = extractDaysOfTheMonth(monthEvents);

	Calendar finalDay = Calendar.getInstance();
	finalDay.set(currentCal.get(Calendar.YEAR), currentCal
		.get(Calendar.MONTH), 1, 0, 0, 0);
	finalDay.add(Calendar.MONTH, 1);
	finalDay.add(Calendar.DAY_OF_MONTH, -1);
	int lastDay = finalDay.get(Calendar.DAY_OF_MONTH);

	Calendar initialDay = Calendar.getInstance();
	initialDay.set(currentCal.get(Calendar.YEAR), currentCal
		.get(Calendar.MONTH), 1, 0, 0, 0);

	int initDay = initialDay.get(Calendar.DAY_OF_WEEK);
	initialDay.add(Calendar.DAY_OF_MONTH, -1);
	int lastDayOfPreviousMonth = initialDay.get(Calendar.DAY_OF_MONTH);

	int startCounting = initDay - firstDayOfWeek;
	if (startCounting <= 0)
	    startCounting += 7;

	// before first day of the month
	int counter = lastDayOfPreviousMonth;
	for (int j = startCounting; j > 0; --j) {
	    days[j - 1].setStatus(JDateButton.NO_DATE);
	    days[j - 1].setText("" + counter); //$NON-NLS-1$
	    --counter;
	}

	int nowDay = today.get(Calendar.DAY_OF_MONTH);
	int nowMonth = today.get(Calendar.MONTH);
	int nowYear = today.get(Calendar.YEAR);
	// days of the month
	counter = 1;
	int month = currentCal.get(Calendar.MONTH);
	int year = currentCal.get(Calendar.YEAR);
	int endCounting = startCounting + lastDay;
	for (int j = startCounting; j < endCounting; ++j) {
	    int numberOfDayEvents = 0;

	    for (int i : allDays) {
		if (i == counter) {
		    ++numberOfDayEvents;
		}
	    }
	    days[j].setNoOfStoredEvents(numberOfDayEvents);
	    days[j].setStatus(JDateButton.WITH_DATE);
	    days[j].setDate(currentCal.get(Calendar.YEAR), month, counter);
	    if (allDays.contains(new Integer(counter)))
		days[j].setHasStoredEvents(true);
	    if ((counter == nowDay) && (nowMonth == month) && (nowYear == year)) {
		days[j]
			.setBackground(GuiConstants.jCalendarCurrentDayBackground);
	    }

	    ++counter;
	}

	// after last day of the month
	int left = 6 * 7 - endCounting;
	counter = 1;
	for (int j = 0; j < left; ++j) {
	    days[j + endCounting].setStatus(JDateButton.NO_DATE);
	    days[j + endCounting].setText("" + counter); //$NON-NLS-1$
	    counter++;
	}
	// weeks
	int weekNumber = initialDay.get(Calendar.WEEK_OF_YEAR) - 1; // values:
	// 0..51
	int w = weekNumber;
	for (int j = 0; j < 6; ++j) {
	    int count = w % 52 + 1;
	    String weekString = " "; //$NON-NLS-1$
	    if (count < 10)
		weekString += "0"; //$NON-NLS-1$
	    weekString += count + " "; //$NON-NLS-1$
	    weeks[j].setText(weekString);
	    ++w;
	}
    }

    private void setYearAndMonth(int year, int month) {
	this.yearShown = year;
	this.monthShown = month;
    }

    public int getYear() {
	return this.yearShown;
    }

    public int getMonth() {
	return this.monthShown;
    }

    private void setDay(int day) {
	this.dayShow = day;
    }

    public int getDay() {
	return this.dayShow;
    }

    public CalendarGUI getParentScreen() {
	return this.parent;
    }

    class DecoratorButton extends JButton {
	private static final long serialVersionUID = -8110894591349863950L;

	public DecoratorButton(String text) {
	    this();
	    setText(text);

	}

	public DecoratorButton() {
	    setForeground(GuiConstants.jDecoratorButtonForeground);
	    setBackground(GuiConstants.jDecoratorButtonBackground);
	    setOpaque(true);
	    setBorder(BorderFactory
		    .createLineBorder(GuiConstants.jDecoratorButtonLine));
	}

	public void addMouseListener(MouseListener l) {
	}

	public boolean isFocusable() {
	    return false;
	}
    }

    class JDateButton extends JButton implements ActionListener, MouseListener {
	public static final int NO_DATE = 0;
	public static final int WITH_DATE = 1;
	// default date: 01/01/2000
	private int year = 2000;
	private int month = 0; // range 0..11 : Jan..Dec
	private int day = 1; // range 1..(28-31)
	private int numberOfDayEvents = 0;

	private int status;

	private static final long serialVersionUID = 6243426756268099696L;

	public JDateButton(String text, int status, boolean hasStoredEvents) {
	    this(status, hasStoredEvents);
	    setText(text);
	}

	public JToolTip createToolTip() {
	    JToolTip tt = new JToolTip();
	    tt.setBackground(GuiConstants.jDateTooltipBackground);
	    tt.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3,
		    GuiConstants.jDateTooltipLineColor));
	    tt.setComponent(this);
	    return tt;
	}

	public void setNoOfStoredEvents(int numberOfDayEvents) {
	    this.numberOfDayEvents = numberOfDayEvents;
	    this.setToolTipText("<html><font size=25 color=\"#666655\">" + //$NON-NLS-1$
		    Messages.getString("JCalendar.EventsStored") + //$NON-NLS-1$
		    " </font><font size=25 color=\"#dc3939\"><b><i>"
		    + this.numberOfDayEvents + "</i></b></font></html>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getNoOfStoredEvents() {
	    return this.numberOfDayEvents;
	}

	public JDateButton(int status, boolean hasStoredEvents) {
	    this.status = status;
	    setFont(GuiConstants.jDateCurrentDateFont);
	    setForeground(GuiConstants.jDateActiveFGColor);
	    if (hasStoredEvents)
		setBackground(GuiConstants.jDateHasStoredEventsBackground);// FIXME
									   // this
									   // is
									   // where
									   // green
									   // was?
	    else
		setBackground(GuiConstants.jDateOtherDaysBackground);
	    setFocusable(false);
	    setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1,
		    GuiConstants.jDateLineColor));// BorderFactory.createLineBorder(line));
	    addActionListener(this);
	    addMouseListener(this);
	}

	public void setHasStoredEvents(boolean hasStoredEvents) {
	    if (hasStoredEvents)
		setBackground(GuiConstants.jDateHasStoredEventsBackground);
	    else
		setBackground(GuiConstants.jDateOtherDaysBackground);
	}

	public void setStatus(int status) {
	    this.status = status;
	    if (this.status == NO_DATE) {
		this.setEnabled(false);
		this.setFont(GuiConstants.jDateOtherDateFont);
		this.setBackground(GuiConstants.jDateInactiveBGColor);
	    } else {
		this.setEnabled(true);
		this.setFont(GuiConstants.jDateCurrentDateFont);
		this.setBackground(GuiConstants.jDateOtherDaysBackground);
	    }
	}

	public int getStatus() {
	    return this.status;
	}

	public void actionPerformed(ActionEvent e) {
	    //System.err.println(day + "-" + (month + 1) + "-" + year); //$NON-NLS-1$ //$NON-NLS-2$
	    setDay(this.day);
	    // day-button pressed -> initialize day
	    parent.setSelectedDate(new DateInstance(getYear(), getMonth(),
		    getDay()));

	    // lazy initialization of the panel
	    if (eventListPanel == null) {
		createEventListPanel();
	    }
	    int[] datePressed = getDate();
	    List<Event> events = parent.getEvents(datePressed[0],
		    datePressed[1], datePressed[2]);
	    eventListPanel.updateMainScreen(events);
	    eventListPanel.setCurrentDate(new DateInstance(getYear(),
		    getMonth() + 1, getDay()));
	    eventListPanel.updateTitleScreen(events.size(), datePressed[0],
		    datePressed[1], datePressed[2]);
	    parent.showScreen(JEventList.CARD_NAME);
	    // System.err.println("Control - returned");// ks
	}

	public void setDate(int year, int month, int day) {
	    this.year = year;
	    this.month = month;
	    this.day = day;
	    this.setText("" + day); //$NON-NLS-1$
	}

	public int[] getDate() {
	    return new int[] { this.year, this.month, this.day };
	}

	public void mouseClicked(MouseEvent e) {
	    // setDay(this.day);
	    // parent.changeNavigationPanel(CalendarGUI.EVENT_NAV_SCREEN);
	    // parent.changeMainPanel(CalendarGUI.EVENT_MAIN_SCREEN);
	    // parent.updateEventTitle();
	}

	public void mouseEntered(MouseEvent e) {
	    // this.setBackground(hoverFGColor);
	    this.setForeground(GuiConstants.jDateMouseEnteredForeground);
	}

	public void mouseExited(MouseEvent e) {
	    // this.setBackground(GuiConstants.jDateOtherDaysBackground);
	    this.setForeground(GuiConstants.jDateActiveFGColor);
	}

	public void mousePressed(MouseEvent e) {
	    // TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
	    // TODO Auto-generated method stub

	}
    }

    public void showNextMonth() {
	calendar.add(Calendar.MONTH, 1);
	populateCalendar(calendar);
    }

    public void showPreviousMonth() {
	calendar.add(Calendar.MONTH, -1);
	populateCalendar(calendar);
    }

    public void showCurrentMonth() {
	calendar = Calendar.getInstance();
	populateCalendar(calendar);
    }

}
