package org.universAAL.agenda.gui;

import org.universAAL.agenda.gui.impl.AgendaClientWrapper;
import org.universAAL.agenda.gui.util.DateInstance;
import org.universAAL.agenda.gui.util.GuiConstants;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.osgi.framework.BundleContext;
import org.universAAL.ontology.profile.User;

/**
 * Main class of Agenda GUI.
 * 
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class CalendarGUI extends JWindow {

    private static final long serialVersionUID = 1L;

    JFrame f;
    JButton backButton;
    JPanel panelButtons;
    JLabel headerMessage;
    JPanel header, navigation, mainPanel;
    JPanel calMainScreen, calNavScreen, eventMainScreen, eventNavScreen,
	    eventSearchMainScreen, eventSearchNavScreen, eventHeader,
	    calHeader, eventInfoMainScreen;

    private org.universAAL.agenda.client.osgi.Activator agendaActivator;
    private org.universAAL.agendaEventSelectionTool.client.osgi.Activator estActivator;
    private AgendaClientWrapper caller;
    private JPanel rootScreen;
    private List<Calendar> activeCalendars;
    private DateInstance selectedDate = null;
  

    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    public CalendarGUI(BundleContext bc, ModuleContext mcontext, User loggedInUser) {
	
	CalendarGUI.mcontext = mcontext;
	try {
	    agendaActivator = new org.universAAL.agenda.client.osgi.Activator();
	    agendaActivator.start(bc);
	    estActivator = new org.universAAL.agendaEventSelectionTool.client.osgi.Activator();
	    estActivator.start(bc);

	    caller = new AgendaClientWrapper(mcontext, agendaActivator
		    .getAgendaConsumer(), estActivator.getConsumer());
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "constructor",
			    new Object[] { "Unable to start bundle agenda.client or eventSelectionTool.client! User interface has no functionality!" },
			    null);

	}

	f = new JFrame();
	f.setUndecorated(true);

	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	f.setSize(screen);

	Dimension window = f.getSize();
	f.setLocation((screen.width - window.width) / 2,
		(screen.height - window.height) / 2);

	f.setBackground(GuiConstants.wholePanelBackground);
	f.setAlwaysOnTop(true);
	f.setVisible(true);

	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	createScreen();
	showInitialScreen(loggedInUser);
	f.setAlwaysOnTop(true);

	LogUtils.logInfo(mcontext, this.getClass(), "constructor",
		new Object[] { "Agenda GUI is being displayed.." }, null);

    }

    private void createScreen() {
	rootScreen = new JPanel();
	rootScreen.setLayout(new CardLayout());

	f.add(rootScreen);
	f.setAlwaysOnTop(true);
	f.setVisible(true);
    }

    private void showInitialScreen(User loggedInUser) {
	setVisible(false);
	new JSelectionCalendar(this, loggedInUser);
	setAlwaysOnTop(true);

	setVisible(true);

    }

    public void setActiveCalendars(List<Calendar> activeCalendars) {
	this.activeCalendars = activeCalendars;
    }

    public void addToActiveList(Calendar c) {
	this.activeCalendars.add(c);
    }

    public List<Calendar> getActiveCalendars() {
	return this.activeCalendars;
    }

    public void addNewCardFrame(JPanel panel, String name) {
	rootScreen.add(panel, name);
    }

    public void removeCardFrame(JPanel panel) {
	rootScreen.remove(panel);
    }

    public void showScreen(String screenName) {
	LogUtils.logInfo(mcontext, this.getClass(), "showScreen",
		new Object[] { "Show main screen." }, null);
	CardLayout cl = (CardLayout) (rootScreen.getLayout());
	cl.show(rootScreen, screenName);
	rootScreen.updateUI();
    }

    public List<Event> getEvents(int year, int month, int day) {
	if (caller == null) {
	    LogUtils.logInfo(mcontext, this.getClass(), "getEvents",
		    new Object[] { "Caller is not enabled" }, null);
	    return new ArrayList<Event>();
	}
	return caller.getDateEvents(activeCalendars, year, month, day);
    }

    public void updateEvent(Event e) {
	if (caller == null)
	    LogUtils.logInfo(mcontext, this.getClass(), "updateEvent",
		    new Object[] { "Caller is not enabled" }, null);
	else
	    caller.updateEvent(e);
    }

    public int saveNewEvent(Calendar c, Event e) {
	if (caller == null)
	    LogUtils.logInfo(mcontext, this.getClass(), "saveNewEvent",
		    new Object[] { "Caller is not enabled" }, null);
	else
	    return caller.saveNewEvent(c, e);

	return -1;
    }

    public void removeEvent(Calendar c, int eventId) {
	if (caller == null)
	    LogUtils.logInfo(mcontext, this.getClass(), "removeEvent",
		    new Object[] { "Caller is not enabled" }, null);
	else
	    caller.removeEvent(c, eventId);
    }

    public DateInstance getSelectedDate() {
	if (this.selectedDate == null) {
	    java.util.Calendar c = java.util.Calendar.getInstance();
	    this.selectedDate = new DateInstance(
		    c.get(java.util.Calendar.YEAR), c
			    .get(java.util.Calendar.MONTH), c
			    .get(java.util.Calendar.DAY_OF_MONTH));
	}
	// System.err.println("Ask for date: " + this.selectedDate);
	return this.selectedDate;
    }

    public void setSelectedDate(DateInstance selectedDate) {
	// System.err.println("Set the date: " + selectedDate);
	this.selectedDate = selectedDate;
    }

    public List<Event> getFilteredEvents(String category, String description,
	    int year, int month, boolean notPastEvents, int eventMaxNo) {
	if (caller == null) {
	    LogUtils.logInfo(mcontext, this.getClass(), "getFilteredEvents",
		    new Object[] { "Caller is not enabled" }, null);
	    return new ArrayList<Event>(0);
	} else
	    return caller.getFilteredEvents(activeCalendars, category,
		    description, year, month, notPastEvents, eventMaxNo);
    }

    public List<String> getAllEventCategories() {
	if (caller == null) {
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "getAllEventCategories",
		    new Object[] { "Caller is not enabled" }, null);

	    return new ArrayList<String>(0);
	} else
	    return caller.getAllEventCategories();
    }

//    public List<Calendar> getAllCalendars() {
//	if (caller == null) {
//	    LogUtils.logInfo(mcontext, this.getClass(), "getAllCalendars",
//		    new Object[] { "Caller is not enabled" }, null);
//	    return new ArrayList<Calendar>(0);
//	} else
//	    return caller.getAllCalendars();
//    }

    public List<Calendar> getCalendarsByOwner(User owner) {
	if (caller == null) {
	    LogUtils.logInfo(mcontext, this.getClass(), "getCalendarsByOwner",
		    new Object[] { "Caller is not enabled" }, null);
	    return new ArrayList<Calendar>(0);
	} else
	    return caller.getCalendarsByOwner(owner);
    }

    public Calendar addNewCalendar(String name, User owner) {
	if (caller == null) {
	    LogUtils.logInfo(mcontext, this.getClass(), "addNewCalendar",
		    new Object[] { "Caller is not enabled" }, null);
	    return null;
	} else
	    return caller.addNewCalendar(name, owner);
    }

    public boolean removeCalendar(Calendar cal) {
	if (caller == null) {
	    LogUtils.logInfo(mcontext, this.getClass(), "removeCalendar",
		    new Object[] { "Caller is not enabled" }, null);
	    return false;
	} else
	    return caller.removeCalendar(cal);
    }

}
