package org.universAAL.agenda.gui;

import org.universAAL.agenda.gui.impl.AgendaClientWrapper;
import org.universAAL.agenda.gui.util.DateInstance;
import org.universAAL.agenda.ont.Calendar;
import org.universAAL.agenda.ont.Event;

import java.awt.CardLayout;
import java.awt.Color;
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
import org.osgi.service.log.LogService;
import org.universAAL.ontology.profile.User;

public class CalendarGUI extends JWindow {

	private static final long serialVersionUID = 1L;
	
	JFrame f;	
	JButton backButton;
	JPanel panelButtons;
	JLabel headerMessage;
	JPanel header, navigation, mainPanel;
	JPanel calMainScreen, calNavScreen, eventMainScreen, eventNavScreen, eventSearchMainScreen,
			eventSearchNavScreen, eventHeader, calHeader, eventInfoMainScreen;
	
	private org.universAAL.agenda.client.Activator agendaActivator;
	private org.universAAL.agendaEventSelectionTool.client.Activator estActivator;
	private AgendaClientWrapper caller;
	private JPanel rootScreen; 
	private List<Calendar> activeCalendars;
	private DateInstance selectedDate = null;
	
	
	public CalendarGUI(BundleContext bc){
		try {
			agendaActivator = new org.universAAL.agenda.client.Activator(); 
			agendaActivator.start(bc);
			estActivator = new org.universAAL.agendaEventSelectionTool.client.Activator();
			estActivator.start(bc);
			
			caller = new AgendaClientWrapper(agendaActivator.getAgendaConsumer(), estActivator.getConsumer());
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_ERROR, "Unable to start bundle: " + e.getMessage());
			Activator.log.log(LogService.LOG_ERROR,  "User interface has no functionality");
		}
		
		f = new JFrame();
		f.setUndecorated(true);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();  
		f.setSize(screen);

		Dimension window = f.getSize();
		f.setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);

		f.setBackground(Color.white);
		f.setAlwaysOnTop(true);
		f.setVisible(true);

		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		createScreen();
		showInitialScreen();
		f.setAlwaysOnTop(true);
		Activator.log.log(LogService.LOG_INFO, "Display Calendar...");
		
	}
	
	
	private void createScreen(){
		rootScreen = new JPanel();
		rootScreen.setLayout(new CardLayout());
		
		f.add(rootScreen);
		f.setAlwaysOnTop(true);
		f.setVisible(true);
	}
	
	private void showInitialScreen() {
		setVisible(false);
		new JSelectionCalendar(this);
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
		System.out.println("Main");
		CardLayout cl = (CardLayout)(rootScreen.getLayout());
        cl.show(rootScreen, screenName);
        rootScreen.updateUI();
	}

	public List<Event> getEvents(int year, int month, int day)  {
		if (caller == null) {
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
			return new ArrayList<Event>();
		}
		return caller.getDateEvents(activeCalendars, year, month, day);
	}


	public void updateEvent(Event e) {
		if (caller == null)
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
		else 
			caller.updateEvent(e);	
	}


	public int saveNewEvent(Calendar c, Event e) {
		if (caller == null) 
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
		else 
			return caller.saveNewEvent(c, e);	
		
		return -1;
	}
	
	public void removeEvent(Calendar c, int eventId){
		if (caller == null)
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
		else 
			caller.removeEvent(c, eventId);	
	}
	
	public DateInstance getSelectedDate() {
		if (this.selectedDate == null) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			this.selectedDate = new DateInstance(c.get(java.util.Calendar.YEAR), c.get(java.util.Calendar.MONTH), c.get(java.util.Calendar.DAY_OF_MONTH));
		}
		//System.err.println("Ask for date: " + this.selectedDate);
		return this.selectedDate;
	}
	
	public void setSelectedDate(DateInstance selectedDate) {
		//System.err.println("Set the date: " + selectedDate);
		this.selectedDate = selectedDate;
	}


	public List<Event> getFilteredEvents(String category, String description, int year, int month, boolean notPastEvents, int eventMaxNo) {
		if (caller == null) {
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
			return new ArrayList<Event>(0);
		} else 
			return caller.getFilteredEvents(activeCalendars, category, description, year, month, notPastEvents, eventMaxNo);
	}
	
	public List<String> getAllEventCategories() {
		if (caller == null) {
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
			return new ArrayList<String>(0);
		} else 
			return caller.getAllEventCategories();
	}


	public List<Calendar> getAllCalendars() {
		if (caller == null) {
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
			return new ArrayList<Calendar>(0);
		} else 
			return caller.getAllCalendars();
	}
	
	public List<Calendar> getCalendarsByOwner(User owner) {
		if (caller == null) {
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
			return new ArrayList<Calendar>(0);
		} else 
			return caller.getCalendarsByOwner(owner);
	}


	public Calendar addNewCalendar(String name, User owner) {
		if (caller == null) {
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
			return null;
		} else 
			return caller.addNewCalendar(name, owner);
	}
	
	public boolean removeCalendar(Calendar cal) {
		if (caller == null) {
			Activator.log.log(LogService.LOG_INFO, "Caller is not enabled");
			return false;
		} else 
			return caller.removeCalendar(cal);
	}
}
