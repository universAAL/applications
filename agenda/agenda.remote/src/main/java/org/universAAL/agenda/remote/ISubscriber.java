/*
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
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

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.agenda.ont.Calendar;
import org.universAAL.agenda.ont.Event;
import org.universAAL.agenda.ont.EventDetails;
import org.universAAL.agenda.ont.Reminder;
import org.universAAL.agenda.ont.ReminderType;
import org.universAAL.agenda.ont.TimeInterval;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.input.InputSubscriber;
import org.universAAL.middleware.output.OutputEvent;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.profile.User;

/**
 * 
 * @author alfiva
 */
public class ISubscriber extends InputSubscriber {

    private final static Logger log = LoggerFactory
	    .getLogger(ISubscriber.class);

    private Hashtable dialogs = new Hashtable();

    public static boolean BMIField = true;

    protected ISubscriber(BundleContext context) {
	super(context);
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.persona.middleware.input.InputSubscriber#handleInputEvent(org.persona
     * .middleware.input.InputEvent)
     */
    public void handleInputEvent(InputEvent event) {
	User user = (User) event.getUser();
	log.info("Received an Input Event from user {}", user.getURI());
	dialogs.put(event.getDialogID(), user.getURI());
	String submit = event.getSubmissionID();

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
		    // Integer remrepeat=(Integer)event.getUserInput(new
		    // String[]{AgendaWebGUI.REF_REM_REP});
		    // Integer remint=(Integer)event.getUserInput(new
		    // String[]{AgendaWebGUI.REF_REM_INT});
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
			    evRem.setRepeatInterval(5 * 60000);// remint.intValue());
			    evRem.setTimesToBeTriggered(2);// remrepeat.intValue());
			    evRem.setReminderTime(remDate);
			    ev.setReminder(evRem);
			}

		    if (Activator.guicaller.addEventToCalendarService(cal, ev) != -1) {
			log.debug("Event successfully added");
			Activator.guioutput.showMessageScreen(user, Messages
				.getString("ISubscriber.1"));
		    } else {
			log.debug("Event could not be added");
			Activator.guioutput.showMessageScreen(user, Messages
				.getString("ISubscriber.2"));
		    }
		} catch (IllegalArgumentException e) {
		    log
			    .error(
				    "Error processing the event input: Probably a bad formatted date: {}",
				    e);
		    Activator.guioutput.showMessageScreen(user, Messages
			    .getString("ISubscriber3"));
		} catch (NullPointerException e) {
		    log
			    .error(
				    "Error processing the event input: Probably missing input: {}",
				    e);
		    Activator.guioutput.showMessageScreen(user, Messages
			    .getString("ISubscriber.4"));
		} catch (Exception e) {
		    log
			    .error(
				    "Unknown error processing the event input: {}",
				    e);
		    Activator.guioutput.showMessageScreen(user, Messages
			    .getString("ISubscriber.5"));
		}
	    } else if (submit.equals("add")) {
		log.debug("Processing Input: Add another");
		Activator.guioutput.showMainScreen(user);
	    } else if (submit.equals("home")) {
		log.debug("Processing Input: Home");
		  event = new InputEvent(user, null/*
			   * newPLocation(
			   * MiddlewareConstants
			   * .
			   * uAAL_MIDDLEWARE_LOCAL_ID_PREFIX
			   * +"Internet")
			   */,
			   	InputEvent.uAAL_MAIN_MENU_REQUEST);
	    }
	    
	    //SC2011 button Events, Event editor
	    else if (submit.equals("Events")) {
	     log.debug("Processing Input: Go to Events");
		Activator.guioutput.showEventsScreen(user);
	    }
	    else if (submit.equals("Event_editor")) {
		     log.debug("Processing Input: Go to Event Editor");
			Activator.guioutput.showMainScreen(user);
		    }
	    
	    else {
		log.warn("We shouldnt have got here, with submit: ", submit);
		// EXIT?
	    }
	} catch (Exception e) {
	    log.error("Error while processing the user input: {}", e);
	    Activator.guioutput.showMessageScreen(user, Messages
		    .getString("ISubscriber.6"));
	}

    }

    public void subscribe(String dialogID) {
	addNewRegParams(dialogID);
    }

    public void dialogAborted(String dialogID) {
	log.warn("Dialog aborted. Doing nothing...");
    }

}
