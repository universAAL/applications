package org.universAAL.agenda.gui;

import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.TimeInterval;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.agenda.gui.components.ImagePanel;
import org.universAAL.agenda.gui.util.ButtonCreator;
import org.universAAL.agenda.gui.util.DateInstance;
import org.universAAL.agenda.gui.util.DateUtilities;
import org.universAAL.agenda.gui.util.GuiConstants;

/**
 * 
 * Event list view.
 * 
 */
public class JEventList implements IPersonaWindow {
    public static final String CARD_NAME = "JEventListCard"; //$NON-NLS-1$

    CalendarGUI parent;
    private JPanel navScreen, mainScreen, titleScreen;
    private static final String EVENT_TAB_NAME = "eventTab"; //$NON-NLS-1$
    private static final String EMPTY_TAB = "emptyTab"; //$NON-NLS-1$
    private static final JPanel noEventPanel;
    private JLabel headerMessage;
    private JEventInfo eventInfoScreen = null;
    private DateInstance selectedDate = null;
    // KS
    private JCalendar activeCalendarScreen = null;

    public void setActiveCalendarScreen(JCalendar activeCalendarScreen) {
	this.activeCalendarScreen = activeCalendarScreen;
    }

    static {
	noEventPanel = new JPanel();

	noEventPanel.setLayout(new GridLayout(4, 1));
	noEventPanel.setBackground(GuiConstants.wholePanelBackground);
	JLabel l = new JLabel("   " + //$NON-NLS-1$
		Messages.getString("JEventList.NoEventIsStoredForThisDate")); //$NON-NLS-1$
	l.setFont(GuiConstants.jEventListLabelFont); //$NON-NLS-1$
	l.setForeground(GuiConstants.jEventListLabelColor);

	noEventPanel.add(l);
    }

    public JEventList(CalendarGUI parent) {
	this.parent = parent;
	initComponents();
    }

    public void initComponents() {
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
    }

    public void setCurrentDate(DateInstance date) {
	this.selectedDate = date;
    }

    public DateInstance getSelectedDate() {

	if (this.selectedDate == null) {
	    System.err.println("Assertion error: NULL date"); //$NON-NLS-1$
	    Calendar c = Calendar.getInstance();
	    return new DateInstance(c.get(Calendar.YEAR),
		    c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	} else
	    return this.selectedDate;
    }

    private JPanel createTitleScreen() {
	ImageIcon project_logo = new ImageIcon(getClass().getResource(
		"/icons/month_header.jpg")); //$NON-NLS-1$
	headerMessage = new JLabel();

	updateTitleScreen(-1, 2000, 0, 1);

	headerMessage.setForeground(GuiConstants.headerMessageForeground);
	headerMessage.setFont(GuiConstants.headerFont); //$NON-NLS-1$

	JPanel headPanel = new ImagePanel(project_logo.getImage()); // (persona_logo.getImage());
	headPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	headPanel.setBackground(GuiConstants.headerPanelBackground);

	headPanel.add(headerMessage);

	// breadcrumbs
	JLabel l = new JLabel(Messages
		.getString("JEventInfo.Breadcrumb.Home.EventList"));
	l.setBackground(GuiConstants.breadcrumbsLabelColor);
	l.setFont(GuiConstants.breadcrumbsLabelFont);

	JPanel whole = new JPanel(new FlowLayout(FlowLayout.LEFT));
	whole.setBackground(GuiConstants.wholePanelBackground);
	whole.add(headPanel);
	whole.add(l);
	return whole;
    }

    private JPanel createMainScreen() {
	JPanel main = new JPanel(new CardLayout());
	main.add(EMPTY_TAB, noEventPanel());
	return main;
    }

    public JPanel createNavScreen() {
	JPanel nav = new JPanel();

	final ImageIcon bigIcon = new ImageIcon(getClass().getResource(
		"/icons/big_button.jpeg")); //$NON-NLS-1$
	final ImageIcon bigPressedIcon = new ImageIcon(getClass().getResource(
	    "/icons/big_button_pressed.jpeg")); //$NON-NLS-1$

	JButton add = ButtonCreator.createButton(bigIcon, Messages
		.getString("JEventList.Add"), GuiConstants.bigButtonsBigFont);
	add.setFocusPainted(false);
	add.setBorderPainted(false);
	add.setContentAreaFilled(false);
	add.setPressedIcon(bigPressedIcon);
	add.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (eventInfoScreen == null) {
		    createEventInfoScreen();
		}
		eventInfoScreen.populateData(null, false);
		eventInfoScreen.setStartTime(selectedDate);
		eventInfoScreen.updateTitleScreen();
		parent.showScreen(JEventInfo.CARD_NAME);
	    }
	});

	JButton back = ButtonCreator.createButton(bigIcon, Messages
		.getString("JEventList.Back"), GuiConstants.bigButtonsBigFont);
	back.setFocusPainted(false);
	back.setBorderPainted(false);
	back.setContentAreaFilled(false);
	back.setPressedIcon(bigPressedIcon);
	back.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		activeCalendarScreen
			.populateCalendar(activeCalendarScreen.calendar);
		CardLayout cl = (CardLayout) mainScreen.getLayout();
		cl.next(mainScreen);
		System.out.println("Card changed: Previous"); //$NON-NLS-1$
	    }
	});

	JButton next = ButtonCreator.createButton(bigIcon, Messages
		.getString("JEventList.Next"), GuiConstants.bigButtonsBigFont);
	next.setFocusPainted(false);
	next.setBorderPainted(false);
	next.setContentAreaFilled(false);
	next.setPressedIcon(bigPressedIcon);
	next.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout) mainScreen.getLayout();
		cl.previous(mainScreen);
		System.out.println("Card changed: Next"); //$NON-NLS-1$

	    }
	});

	JButton mainb = ButtonCreator.createButton(bigIcon, Messages
		.getString("JEventList.Main"), GuiConstants.bigButtonsBigFont);
	mainb.setFocusPainted(false);
	mainb.setBorderPainted(false);
	mainb.setContentAreaFilled(false);
	mainb.setPressedIcon(bigPressedIcon);
	mainb.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		selectedDate = null;
		activeCalendarScreen
			.populateCalendar(activeCalendarScreen.calendar);
		parent.showScreen(JCalendar.CARD_NAME);
	    }
	});

	// Create the right zone of buttons
	nav.setLayout(new GridLayout(4, 1));
	nav.setBackground(GuiConstants.wholePanelBackground);

	// before clock was added
	// nav.add(new JLabel()); //<<Create a void combined with
	// nav.setLayout(new GridLayout(6, 1));

	AnalogClock clock = new AnalogClock();
	nav.add(clock);
	nav.add(add);
	// nav.add(next);
	nav.add(mainb);
	nav.add(CurrentDateAndDigitalClock.getDateAndDigitalClockPanel());

	return nav;
    }

    private static JPanel noEventPanel() {
	return noEventPanel;
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

    boolean incomplete = true;

    public void updateMainScreen(List<Event> events) {
	this.mainScreen.removeAll();

	// show only visible events
	int removed = 0;
	for (Iterator<Event> it = events.iterator(); it.hasNext();) {
	    if (!(it.next().isVisible())) {
		it.remove();
		++removed;
	    }
	}
	System.out
		.println("JEventList.updateMainScreen->Invisible events(2): " + removed); //$NON-NLS-1$
	if (events == null || events.size() == 0) {
	    this.mainScreen.add(EMPTY_TAB, noEventPanel());
	    return;
	}

	JPanel middle = new JPanel();
	middle.setLayout(new GridLayout(4, 1));
	middle.setBackground(GuiConstants.wholePanelBackground);

	int counter = 0;
	for (Event event : events) {
	    middle.add(new AppointmentEntry(event));
	    ++counter;

	    if (counter % 3 == 0) {
		this.mainScreen.add(EVENT_TAB_NAME + (counter / 3), middle); // eventTab1,
		// 2,...
		middle = new JPanel();
		middle.setLayout(new GridLayout(4, 1));
		middle.setBackground(GuiConstants.wholePanelBackground);
		incomplete = false;
	    } else {
		incomplete = true;
	    }
	}
	int tabNo = (counter / 3) + 1;
	if (incomplete) {
	    this.mainScreen.add(EVENT_TAB_NAME + tabNo, middle);
	} else {
	}
    }

    public void updateTitleScreen(int eventNo, int year, int month, int day) {
	String eventDescription;
	if (eventNo < 1) {
	    eventDescription = Messages
		    .getString("JEventList.NoScheduledAppointment"); //$NON-NLS-1$
	} else if (eventNo == 1) {
	    eventDescription = "<b>1</b> " + //$NON-NLS-1$
		    Messages.getString("JEventList.ScheduleAppointment"); //$NON-NLS-1$
	} else {
	    eventDescription = "<b>" + eventNo + "</b> " + //$NON-NLS-1$ //$NON-NLS-2$
		    Messages.getString("JEventList.ScheduledAppointments"); //$NON-NLS-1$
	}
	headerMessage
		.setText("<html><font face=\"MyriadPro\" size=\"7\" color=\"white\">" + day //$NON-NLS-1$
			+ "</font><font size=\"6\">" + DateUtilities.forEnglishLangReturnDayEnding("" + day) //$NON-NLS-1$ //$NON-NLS-2$
			+ "</font><font size=\"7\"> " + DateUtilities.months[month] + ", </font>" //$NON-NLS-1$ //$NON-NLS-2$
			+ "<font size=\"6\" color=\"white\">" + year + "<p></font>" //$NON-NLS-1$ //$NON-NLS-2$
			+ "<font size=\"5\" color=\"#2f594f\">" + eventDescription + "</font></html>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void updateTitleScreen(int eventNo) {
	String eventDescription;
	if (eventNo < 1) {
	    eventDescription = Messages
		    .getString("JEventList.NoScheduledAppointment"); //$NON-NLS-1$
	} else if (eventNo == 1) {
	    eventDescription = "<b>1</b> " + //$NON-NLS-1$
		    Messages.getString("JEventList.ScheduledAppointment"); //$NON-NLS-1$
	} else {
	    eventDescription = "<b>" + eventNo + "</b> " + //$NON-NLS-1$ //$NON-NLS-2$
		    Messages.getString("JEventList.ScheduledAppointments"); //$NON-NLS-1$
	}
	headerMessage
		.setText("<html><font face=\"MyriadPro\" size=\"7\" color=\"white\">" //$NON-NLS-1$
			+ "</font><font size=\"7\"> " + //$NON-NLS-1$
			Messages.getString("JEventList.SearchResults") + //$NON-NLS-1$
			"  </font><br/>" //$NON-NLS-1$
			+ "<font size=\"5\" color=\"#2f594f\">" + eventDescription + "</font></html>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void overrideTitle(String headerText, String explanatoryText) {
	headerMessage
		.setText("<html><font face=\"MyriadPro\" size=\"7\" color=\"white\">" + headerText //$NON-NLS-1$
			+ "</font><br/>" //$NON-NLS-1$
			+ "<font size=\"5\" color=\"#2f594f\">" + explanatoryText + "</font></html>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private void createEventInfoScreen() {
	eventInfoScreen = new JEventInfo(getParentScreen());
	eventInfoScreen.setActiveCalendarScreen(this.activeCalendarScreen);
	eventInfoScreen.setActiveJEventListScreen(this);
    }

    public CalendarGUI getParentScreen() {
	return this.parent;
    }

    class AppointmentEntry extends JPanel {
	private static final long serialVersionUID = -8006508828071201209L;
	private final Event event;
	String type, hour, date, place;
	JLabel info, data, moreInfo;

	public AppointmentEntry(Event e) {
	    super();
	    this.event = e;
	    this.type = " - "; //$NON-NLS-1$
	    this.hour = " - "; //$NON-NLS-1$
	    this.date = "( - )"; //$NON-NLS-1$
	    this.place = " - "; //$NON-NLS-1$
	    extractInfo();
	    initComponents();
	}

	private void extractInfo() {
	    if (event.getEventDetails() != null) {
		String s = event.getEventDetails().getDescription();
		if (s != null) {
		    this.type = s;
		}
		System.out.println(type);
		TimeInterval ti = event.getEventDetails().getTimeInterval();
		if (ti != null) {
		    XMLGregorianCalendar start = ti.getStartTime();
		    if (start != null) {
			int startH = start.getHour();
			int startM = start.getMinute();
			String h = ""; //$NON-NLS-1$
			String m = ""; //$NON-NLS-1$
			if (startH < 10)
			    h = "0"; //$NON-NLS-1$
			if (startM < 10)
			    m = "0"; //$NON-NLS-1$
			this.hour = h + start.getHour()
				+ ":" + m + start.getMinute(); //$NON-NLS-1$

			int day = start.getDay();
			int month = start.getMonth(); // + 1;
			int year = start.getYear();
			this.date = "(" + //$NON-NLS-1$
				Messages.getString("JEventList.StartDay") + //$NON-NLS-1$
				": " + day + "/" + month + "/" + year + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		    } else {
			start = ti.getEndTime();
			if (start != null) {
			    int startH = start.getHour();
			    int startM = start.getMinute();
			    String h = ""; //$NON-NLS-1$
			    String m = ""; //$NON-NLS-1$
			    if (startH < 10)
				h = "0"; //$NON-NLS-1$
			    if (startM < 10)
				m = "0"; //$NON-NLS-1$
			    this.hour = h + start.getHour()
				    + ":" + m + start.getMinute(); //$NON-NLS-1$

			    int day = start.getDay();
			    int month = start.getMonth() + 1;
			    int year = start.getYear();
			    this.date = "(" + //$NON-NLS-1$
				    Messages.getString("JEventList.EndDate") + //$NON-NLS-1$
				    ": " + day + "/" + month + "/" + year + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
		    }
		}

		String p = event.getEventDetails().getPlaceName();
		if (p != null)
		    this.place = p;

	    }
	}

	public void initComponents() {
	    info = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;" + //$NON-NLS-1$
		    Messages.getString("JEventList.Appointment") + //$NON-NLS-1$
		    "&nbsp;&nbsp;&nbsp;&nbsp;<br/>" + //$NON-NLS-1$
		    "&nbsp;&nbsp;&nbsp;&nbsp;" + //$NON-NLS-1$
		    Messages.getString("JEventList.Hour") + //$NON-NLS-1$
		    "<br/>" + //$NON-NLS-1$
		    "&nbsp;&nbsp;&nbsp;&nbsp;" + //$NON-NLS-1$
		    Messages.getString("JEventList.Place")); //$NON-NLS-1$
	    info.setHorizontalAlignment(JLabel.CENTER);
	    info.setFont(GuiConstants.jEventListInfoAndDataFont); //$NON-NLS-1$
	    info.setForeground(GuiConstants.jEventListInfoForeground);
	    info.setBackground(GuiConstants.jEventListInfoBackground);
	    info.setOpaque(true);

	    String labelText = "<Html>" + type + "<br/>" //$NON-NLS-1$ //$NON-NLS-2$
		    + hour
		    + "&nbsp;&nbsp;<font size=\"5\" color=\"blue\">" + date + "</font><br/>" //$NON-NLS-1$ //$NON-NLS-2$
		    + place + "</html>"; //$NON-NLS-1$
	    data = new JLabel(labelText);
	    data.setFont(GuiConstants.jEventListInfoAndDataFont); //$NON-NLS-1$
	    data.setForeground(GuiConstants.jEventListDataForeground);

	    moreInfo = new JLabel(Messages
		    .getString("JEventList.ClickForMoreInfo")); //$NON-NLS-1$
	    moreInfo.setFont(GuiConstants.jEventListMoreInfoFont); //$NON-NLS-1$
	    moreInfo.setForeground(GuiConstants.jEventListMoreInfoForeground); // orange

	    setLayout(new BorderLayout());// GridLayout(1, 3));
	    add(info, BorderLayout.WEST);
	    add(data, BorderLayout.CENTER);
	    add(moreInfo, BorderLayout.EAST);

	    setBackground(GuiConstants.wholePanelBackground);

	    addMouseListener(new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
		    setBorder(BorderFactory.createEtchedBorder(Color.blue,
			    Color.gray));
		}

		public void mouseExited(MouseEvent e) {
		    setBorder(BorderFactory.createEtchedBorder(Color.white,
			    Color.white));
		}

		public void mouseClicked(MouseEvent e) {
		    if (eventInfoScreen == null) {
			createEventInfoScreen();
		    }

		    eventInfoScreen.populateData(event, false);
		    eventInfoScreen.updateTitleScreen();
		    parent.showScreen(JEventInfo.CARD_NAME);
		}
	    });
	}

    }
}
