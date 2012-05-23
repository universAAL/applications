/**
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
	2012 Ericsson Nikola Tesla d.d., www.ericsson.com/hr
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.agenda.remote;

import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.agenda.TimeInterval;
import org.universAAL.agenda.remote.osgi.Activator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ontology.profile.User;

/**
 * 
 * @author alfiva
 * @author eandgrg
 */
public class UIProvider extends UICaller {

    private final static Logger log = LoggerFactory.getLogger(UIProvider.class);

    private Hashtable<String, String> dialogs = new Hashtable<String, String>();

    public static boolean BMIField = true;
    
    private AgendaWebGUI webUI=null;

    public UIProvider(ModuleContext mcontext, AgendaWebGUI agendaWebUI) {
	super(mcontext);
	webUI=agendaWebUI;
    }

    /* (non-Javadoc)
     * @see org.universAAL.middleware.ui.UICaller#communicationChannelBroken()
     */
    public void communicationChannelBroken() {
	// Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL.middleware.ui.UIResponse)
     */
    public void handleUIResponse(UIResponse event) {

	User user = (User) event.getUser();
	log.info("Received an Input Event from user {}", user.getURI());
	dialogs.put(event.getDialogID(), user.getURI());
	String submit = null;
	submit = event.getSubmissionID();

	try {
	    if (submit.equals("submit")) {
		log.debug("Processing Input: Submit");
		try {
		    Calendar cal = (Calendar) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_CALENDAR });
		    Integer day = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_DAY });
		    Integer month = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_MONTH });
		    Integer year = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_YEAR });
		    Integer hour = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_HOUR });
		    Integer min = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_MIN });
		    String type = (String) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_TYPE });
		    String desc = (String) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_DESC });
		    String place = (String) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_PLACE });
		    String remmsg = (String) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_REM_MSG });
		    Integer remday = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_REM_DAY });
		    Integer remmonth = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_REM_MONTH });
		    Integer remyear = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_REM_YEAR });
		    Integer remhour = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_REM_HOUR });
		    Integer remmin = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_REM_MIN });

		    Integer remrepeat = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_REM_REP });
		    Integer remint = (Integer) event
			    .getUserInput(new String[] { AgendaWebGUI.REF_REM_INT });

		    Event ev = new Event();

		    EventDetails evDet = new EventDetails();
		    evDet.setDescription(desc);
		    evDet.setPlaceName(place);
		    evDet.setCategory(type);
		    evDet.setSpokenLanguage(Locale.getDefault().toString());

		    XMLGregorianCalendar evDate = TypeMapper
			    .getDataTypeFactory()
			    .newXMLGregorianCalendar(
				    year.intValue(),
				    month.intValue(),
				    day.intValue(),
				    hour.intValue(),
				    min.intValue(),
				    0,
				    0,
				    TimeZone.getDefault().getOffset(
					    System.currentTimeMillis()) / 60000);
		    log.debug("event date: {}", evDate);

		    TimeInterval ti = new TimeInterval();
		    ti.setStartTime(evDate);
		    evDet.setTimeInterval(ti);
		    ev.setEventDetails(evDet);

		    XMLGregorianCalendar remDate = TypeMapper
			    .getDataTypeFactory()
			    .newXMLGregorianCalendar(
				    remyear.intValue(),
				    remmonth.intValue(),
				    remday.intValue(),
				    remhour.intValue(),
				    remmin.intValue(),
				    0,
				    0,
				    TimeZone.getDefault().getOffset(
					    System.currentTimeMillis()) / 60000);
		    log.debug("event date: {}", evDate);

		    Reminder evRem = new Reminder();
		    if (remmsg != null)
			if (!remmsg.isEmpty() && !remmsg.equals(" ")) {
			    evRem.setMessage(remmsg);
			    evRem.setReminderType(ReminderType.visualMessage);
			    // evRem.setRepeatInterval(5 * 60000);//
			    // remint.intValue());
			    // evRem.setTimesToBeTriggered(2);//
			    // remrepeat.intValue());
			    evRem.setRepeatInterval(remint.intValue());// remint.intValue());
			    evRem.setTimesToBeTriggered(remrepeat.intValue());// remrepeat.intValue());
			    evRem.setReminderTime(remDate);
			    ev.setReminder(evRem);
			}

		    if (Activator.guicaller.addEventToCalendarService(cal, ev) != -1) {
			log.debug("Event successfully added");
			showMessageScreen(user, Messages
				.getString("UIProvider.1"));
		    } else {
			log.debug("Event could not be added");
			showMessageScreen(user, Messages
				.getString("UIProvider.2"));
		    }
		} catch (IllegalArgumentException e) {
		    log
			    .error(
				    "Error processing the event input: Probably a bad formatted date: {}",
				    e);
		    showMessageScreen(user, Messages.getString("ISubscriber3"));
		} catch (NullPointerException e) {
		    log
			    .error(
				    "Error processing the event input: Probably missing input: {}",
				    e);
		    showMessageScreen(user, Messages.getString("UIProvider.4"));
		} catch (Exception e) {
		    log
			    .error(
				    "Unknown error processing the event input: {}",
				    e);
		    showMessageScreen(user, Messages.getString("UIProvider.5"));
		}
		submit = null;
	    } else if (submit.equals("add")) {
		log.debug("Processing Input: Add another");
		showMainScreen(user);
		submit = null;
	    } 
	    
	   //FIXME commented when transferring to UI Bus (no InputEvent.uAAL_MAIN_MENU_REQUEST) related to: AgendaWebGUI line 305
	    //check tip: if button is pressed but not handled result is return to main menu
	    else if (submit.equals("home")) {
		log.debug("Processing Input: Home");
//		event = new UIResponse(user, null/*
//						  * newPLocation(
//						  * MiddlewareConstants .
//						  * uAAL_MIDDLEWARE_LOCAL_ID_PREFIX
//						  * +"Internet")
//						  */,
//			InputEvent.uAAL_MAIN_MENU_REQUEST);

		submit = null;
	    }

	    // SC2011 button Events, Event editor
	    else if (submit.equals("Events")) {
		Calendar cal = (Calendar) event
			.getUserInput(new String[] { AgendaWebGUI.REF_CALENDAR });
		log.debug("Processing Input: Go to Events");
		showEventsScreen(user, cal);
		// takes events from specific calendar
		submit = null;

	    } else if (submit.toString().startsWith("delete")) {
		Calendar cal = new Calendar(Calendar.MY_URI);
		Event ev = AgendaWebGUI.map.get(Integer.parseInt(submit
			.toString().substring(6)));

		if (Activator.guicaller.deleteCalendarEventService(cal, ev
			.getEventID())) {
		    log.debug("Event successfully removed");
		    showMessageScreen(user, Messages.getString("UIProvider.7"));
		} else {
		    log.debug("Event could not be removed");
		    showMessageScreen(user, Messages.getString("UIProvider.8"));
		}

		submit = null;
	    }

	    else if (submit.equals("Event_editor")) {
		log.debug("Processing Input: Go to Event Editor");
		showMainScreen(user);
		submit = null;
	    } else if (submit.equals("google")) {
		log.debug("Running Google calendar");
		Calendar cal = (Calendar) event
			.getUserInput(new String[] { AgendaWebGUI.REF_CALENDAR });

		showGoogleScreen(user, cal);

		submit = null;
	    }

	    else {
		log.warn("We shouldnt have got here, with submit: ", submit);
		// EXIT?
		submit = null;
	    }
	} catch (Exception e) {
	    log.error("Error while processing the user input: {}", e);
	    showMessageScreen(user, Messages.getString("UIProvider.6"));
	}

    }

    // removed when transferring to UIBus
    // public void subscribe(String dialogID) {
    // addNewRegParams(dialogID);
    // }

    public void dialogAborted(String dialogID) {
	log.warn("Dialog aborted. Doing nothing...");
    }

    public void showMainScreen(User user) {
	log
		.info("Publishing Output: showMainScreen for user {}", user
			.getURI());
	Form f = webUI.getMainScreenMenuForm();
	UIRequest oe = new UIRequest(user, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	sendUIRequest(oe);
    }

    public void showMessageScreen(User user, String msg) {
	log
		.info("Publishing Output: showMainScreen for user {}", user
			.getURI());
	Form f = webUI.getMessageForm(msg);
	UIRequest oe = new UIRequest(user, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	sendUIRequest(oe);
    }

    // SC2011 show events screen
    public void showEventsScreen(User user, Calendar cal) {
	log.info("Publishing Output: showEventsScreen for user {}", user
		.getURI());
	Form f = webUI.getEventsForm(cal);
	UIRequest oe = new UIRequest(user, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	sendUIRequest(oe);
    }

    // SC2011 show google screen
    public void showGoogleScreen(User user, Calendar cal) {
	log.info("Publishing Output: showGoogleScreen for user {}", user
		.getURI());
	Form f = webUI.getGoogleForm(cal);
	UIRequest oe = new UIRequest(user, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	sendUIRequest(oe);
    }

}
