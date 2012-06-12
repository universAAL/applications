package org.universAAL.agenda.gui;

import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.TimeInterval;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
import org.universAAL.agenda.gui.util.DateInstance;
import org.universAAL.agenda.gui.util.DateUtilities;

public class JEventList implements PersonaWindow {
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
	noEventPanel.setBackground(Color.white);
	JLabel l = new JLabel("   " + //$NON-NLS-1$
		Messages.getString("JEventList.NoEventIsStoredForThisDate")); //$NON-NLS-1$
	l.setFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	l.setForeground(Color.gray);

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
	ImageIcon persona_logo = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/month_header.jpg")); //$NON-NLS-1$
	headerMessage = new JLabel();

	updateTitleScreen(-1, 2000, 0, 1);

	headerMessage.setForeground(Color.white);
	headerMessage.setFont(new Font("MyriadPro", Font.PLAIN, 35)); //$NON-NLS-1$

	JPanel headPanel = new ImagePanel(persona_logo.getImage()); // (persona_logo.getImage());
	headPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	headPanel.setBackground(Color.white);

	headPanel.add(headerMessage);

	//breadcrumbs
	JLabel l = new JLabel(
		"<html><font face=\"MyriadPro\" size=\"5\" >"+Messages.getString("JEventInfo.Breadcrumb.Home.EventList")+"</font>");
	l.setBackground(Color.white);

	JPanel whole = new JPanel(new FlowLayout(FlowLayout.LEFT));
	whole.setBackground(Color.white);
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
	ImageIcon add_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/add.jpg")); //$NON-NLS-1$
	ImageIcon back_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/back.jpg")); //$NON-NLS-1$
	ImageIcon next_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/next.jpg")); //$NON-NLS-1$
	ImageIcon main_icon = new ImageIcon(getClass().getResource(
		IconsHome.getIconsHomePath() + "/main.jpg")); //$NON-NLS-1$

	JButton add = new JButton(add_icon);
	add.setFocusPainted(false);
	add.setBorderPainted(false);
	add.setContentAreaFilled(false);
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

	JButton back = new JButton(back_icon);
	back.setFocusPainted(false);
	back.setBorderPainted(false);
	back.setContentAreaFilled(false);
	back.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		activeCalendarScreen
			.populateCalendar(activeCalendarScreen.calendar);
		CardLayout cl = (CardLayout) mainScreen.getLayout();
		cl.next(mainScreen);
		System.out.println("Card changed: Previous"); //$NON-NLS-1$
	    }
	});

	JButton next = new JButton(next_icon);
	next.setFocusPainted(false);
	next.setBorderPainted(false);
	next.setContentAreaFilled(false);
	next.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		CardLayout cl = (CardLayout) mainScreen.getLayout();
		cl.previous(mainScreen);
		System.out.println("Card changed: Next"); //$NON-NLS-1$

	    }
	});

	JButton mainb = new JButton(main_icon);
	mainb.setFocusPainted(false);
	mainb.setBorderPainted(false);
	mainb.setContentAreaFilled(false);
	mainb.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		selectedDate = null;
		activeCalendarScreen
			.populateCalendar(activeCalendarScreen.calendar);
		parent.showScreen(JCalendar.CARD_NAME);
	    }
	});

	JButton voice = new VoiceButton();

	// Create the right zone of buttons
	nav.setLayout(new GridLayout(4, 1));
	nav.setBackground(Color.white);

	// SC2011 dodan sat i zakomentirana linija
	// nav.add(new JLabel()); //<<Create a void combined with
	// nav.setLayout(new GridLayout(6, 1));

	AnalogClock clock = new AnalogClock();
	nav.add(clock);
	nav.add(add);
	// nav.add(next);
	nav.add(mainb);
	nav.add(voice);

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
	System.out.println("Invisible events(2): " + removed); //$NON-NLS-1$
	if (events == null || events.size() == 0) {
	    this.mainScreen.add(EMPTY_TAB, noEventPanel());
	    return;
	}

	JPanel middle = new JPanel();
	middle.setLayout(new GridLayout(4, 1));
	middle.setBackground(Color.white);

	int counter = 0;
	for (Event event : events) {
	    middle.add(new AppointmentEntry(event));
	    ++counter;

	    if (counter % 3 == 0) {
		this.mainScreen.add(EVENT_TAB_NAME + (counter / 3), middle); // eventTab1,
									     // 2,...
		middle = new JPanel();
		middle.setLayout(new GridLayout(4, 1));
		middle.setBackground(Color.white);
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
			+ "</font><font size=\"6\">" + DateUtilities.dayEnding("" + day) //$NON-NLS-1$ //$NON-NLS-2$
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
	    info.setFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	    info.setForeground(new java.awt.Color(0, 167, 127));
	    info.setBackground(Color.white);
	    info.setOpaque(true);

	    String labelText = "<Html>" + type + "<br/>" //$NON-NLS-1$ //$NON-NLS-2$
		    + hour
		    + "&nbsp;&nbsp;<font size=\"5\" color=\"blue\">" + date + "</font><br/>" //$NON-NLS-1$ //$NON-NLS-2$
		    + place + "</html>"; //$NON-NLS-1$
	    data = new JLabel(labelText);
	    data.setFont(new Font("MyriadPro", Font.PLAIN, 25)); //$NON-NLS-1$
	    data.setForeground(new java.awt.Color(86, 86, 86));

	    moreInfo = new JLabel(Messages
		    .getString("JEventList.ClickForMoreInfo")); //$NON-NLS-1$
	    moreInfo.setFont(new Font("MyriadPro", Font.ITALIC, 25)); //$NON-NLS-1$
	    moreInfo.setForeground(new java.awt.Color(255, 102, 127)); //orange

	    setLayout(new BorderLayout());// GridLayout(1, 3));
	    add(info, BorderLayout.WEST);
	    add(data, BorderLayout.CENTER);
	    add(moreInfo, BorderLayout.EAST);

	    setBackground(Color.white);

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
