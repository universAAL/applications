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

import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.agenda.TimeInterval;
import org.universAAL.agenda.remote.osgi.Activator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.User;

/**
 * 
 * @author alfiva
 * @author eandgrg
 */
public class UIProvider extends UICaller {
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    private Hashtable<String, String> dialogs = new Hashtable<String, String>();

    private AgendaWebGUI webUI = null;

    private User currentlySelectedCalOwner = null;

    public UIProvider(ModuleContext mcontext, AgendaWebGUI agendaWebUI) {
	super(mcontext);
	UIProvider.mcontext = mcontext;
	webUI = agendaWebUI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.ui.UICaller#communicationChannelBroken()
     */
    public void communicationChannelBroken() {
	// Auto-generated method stub
    }

    User remoteLoggedUser = null;

    /**
     * Sets currently selected calendar owner (assisted person). In other words
     * reads dropdown selection of the user whose events remote caregiver wants
     * to manage.
     * 
     * @param uiresp
     */
    private void readAndSetCurrentlySelectedCalendarOwner(UIResponse uiresp) {
	String username = (String) uiresp
		.getUserInput(new String[] { AgendaWebGUI.REF_USER });
	currentlySelectedCalOwner = new User(
		Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + username);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL
     * .middleware.ui.UIResponse)
     */
    public void handleUIResponse(UIResponse uiresponse) {

	remoteLoggedUser = (User) uiresponse.getUser();

	LogUtils.logInfo(mcontext, this.getClass(), "handleUIResponse",
		new Object[] { "Received an Input Event from user {}",
			remoteLoggedUser.getURI() }, null);
	dialogs.put(uiresponse.getDialogID(), remoteLoggedUser.getURI());
	String submit = null;
	submit = uiresponse.getSubmissionID();

	try {
	    if (submit.equals("submit")) {
		handleSubmit(uiresponse);
		submit = null;
	    }

	    else if (submit.equals("agenda_home")) {
		LogUtils.logDebug(mcontext, this.getClass(),
			"handleUIResponse",
			new Object[] { "Processing Input: Agenda Home" }, null);
		// event = new UIResponse(user, null/*
		// * newPLocation(
		// * MiddlewareConstants .
		// * uAAL_MIDDLEWARE_LOCAL_ID_PREFIX
		// * +"Internet")
		// */,
		// InputEvent.uAAL_MAIN_MENU_REQUEST);
		showInitialScreen(remoteLoggedUser);
		submit = null;
	    }

	    else if (submit.equals("get_event_list")) {
		readAndSetCurrentlySelectedCalendarOwner(uiresponse);
		LogUtils.logInfo(mcontext, this.getClass(), "handleUIResponse",
			new Object[] { "Processing Input: Go to Events List" },
			null);
		showEventsScreen(remoteLoggedUser, currentlySelectedCalOwner);
		// takes events from specific calendar
		submit = null;

	    } else if (submit.equals("add")) {
		// get cal owner when selecting from initial screen, before
		// going to main screen for editing events
		LogUtils
			.logInfo(
				mcontext,
				this.getClass(),
				"handleUIResponse",
				new Object[] { "Processing Input: add event for user" },
				null);
		readAndSetCurrentlySelectedCalendarOwner(uiresponse);

		showMainScreen(remoteLoggedUser, currentlySelectedCalOwner);
		// takes events from specific calendar
		submit = null;

	    } else if (submit.toString().startsWith("delete")) {
		Calendar cal = new Calendar(Calendar.MY_URI);
		Event ev = AgendaWebGUI.map.get(Integer.parseInt(submit
			.toString().substring(6)));
		if (Activator.sCaller.deleteCalendarEventService(cal, ev
			.getEventID())) {
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "handleUIResponse",
				    new Object[] { "Event successfully removed" },
				    null);
		    showMessageScreen(remoteLoggedUser, Messages
			    .getString("UIProvider.EventSucessfullyRemoved"));
		} else {
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "handleUIResponse",
				    new Object[] { "Event could not be removed" },
				    null);
		    showMessageScreen(remoteLoggedUser, Messages
			    .getString("UIProvider.ErrorRemovingEvent"));
		}

		submit = null;

		// } else if (submit.equals("google")) {
		//
		// LogUtils.logInfo(mcontext, this.getClass(),
		// "handleUIResponse",
		// new Object[] { "Running Google calendar" }, null);
		// Calendar cal = (Calendar) uiresponse
		// .getUserInput(new String[] { AgendaWebGUI.REF_CALENDAR });
		//
		// showGoogleScreen(remoteLoggedUser, cal);
		// submit = null;
	    } else {
		LogUtils.logError(mcontext, this.getClass(),
			"handleUIResponse", new Object[] {
				"We shouldn't have got here, with submit: ",
				submit }, null);

		submit = null;
	    }
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "handleUIResponse",
		    new Object[] { "Error while processing the user input.",
			    submit }, e);
	    showMessageScreen(remoteLoggedUser, Messages
		    .getString("UIProvider.UnknownServiceError"));
	}

    }// end handle UI Response

    /**
     * Handle submit event data
     * 
     * @param event
     *            uiresponse
     */
    protected void handleSubmit(UIResponse event) {
	LogUtils.logInfo(mcontext, this.getClass(), "handleSubmit",
		new Object[] { "Processing Input: Submit" }, null);
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

	    XMLGregorianCalendar evDate = TypeMapper.getDataTypeFactory()
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

	    LogUtils.logDebug(mcontext, this.getClass(), "handleSubmit",
		    new Object[] { "event date: {}", evDate }, null);

	    TimeInterval ti = new TimeInterval();
	    ti.setStartTime(evDate);
	    evDet.setTimeInterval(ti);
	    ev.setEventDetails(evDet);

	    XMLGregorianCalendar remDate = TypeMapper.getDataTypeFactory()
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

	    if (Activator.sCaller.addEventToCalendarService(cal, ev) != -1) {
		LogUtils.logInfo(mcontext, this.getClass(), "handleSubmit",
			new Object[] { "Event successfully added" }, null);

		showMessageScreen(remoteLoggedUser, Messages
			.getString("UIProvider.EventSuccessfullyAdded"));
	    } else {
		LogUtils.logWarn(mcontext, this.getClass(), "handleSubmit",
			new Object[] { "Event could not be added." }, null);

		showMessageScreen(remoteLoggedUser, Messages
			.getString("UIProvider.ErrorAddingEvent"));
	    }
	} catch (IllegalArgumentException e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "handleSubmit",
			    new Object[] { "Error processing the event input: Probably a bad formatted date" },
			    e);

	    showMessageScreen(remoteLoggedUser, Messages
		    .getString("UIProvider.InvalidInputDate"));
	} catch (NullPointerException e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "handleSubmit",
			    new Object[] { "Error processing the event input: Probably missing input" },
			    e);

	    showMessageScreen(remoteLoggedUser, Messages
		    .getString("UIProvider.InvalidInputMissing"));
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "handleSubmit",
			    new Object[] { "Unknown error processing the event input" },
			    e);
	    showMessageScreen(remoteLoggedUser, Messages
		    .getString("UIProvider.InvalidInputUnknown"));
	}

    }

    // removed when transferring to UIBus
    // public void subscribe(String dialogID) {
    // addNewRegParams(dialogID);
    // }

    public void dialogAborted(String dialogID) {
	LogUtils.logWarn(mcontext, this.getClass(), "dialogAborted",
		new Object[] { "Dialog aborted. Doing nothing..." }, null);
    }

    /**
     * @param remoteLoggedUser
     *            {@link User} accessing the service
     */
    public void showInitialScreen(User remoteLoggedUser) {
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"showInitialScreen",
			new Object[] {
				"Sending UI Request: showInitial Screen for selecting calendar owner: ",
				remoteLoggedUser.getURI() }, null);

	Form f = webUI.getSelectUserMenuForm();
	UIRequest oe = new UIRequest(remoteLoggedUser, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	sendUIRequest(oe);
    }

    public void showMainScreen(User remoteLoggedUser, User calOwner) {
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"showMainScreen",
			new Object[] {
				"Sending UI Request: showMainScreen for adding event and reminder for user {}",
				remoteLoggedUser.getURI() }, null);

	Form f = webUI.getMainScreenMenuForm(calOwner);
	UIRequest oe = new UIRequest(remoteLoggedUser, f, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.insensible);
	sendUIRequest(oe);
    }

    public void showMessageScreen(User user, String msg) {
	LogUtils.logInfo(mcontext, this.getClass(), "showMessageScreen",
		new Object[] {
			"Sending UI Request with info message: " + msg
				+ " screen for user {}", user.getURI() }, null);
	Form f = webUI.getMessageForm(msg);
	UIRequest oe = new UIRequest(user, f, LevelRating.middle, Locale
		.getDefault(), PrivacyLevel.insensible);
	sendUIRequest(oe);
    }

    public void showEventsScreen(User user, User calOwner) {
	LogUtils.logInfo(mcontext, this.getClass(), "showEventsScreen",
		new Object[] {
			"Sending UI Request: showEventsScreen for user {}",
			user.getURI() + " for calendar owner: "
				+ calOwner.getURI() }, null);

	Form f = webUI.getEventsForm(currentlySelectedCalOwner);
	UIRequest oe = new UIRequest(user, f, LevelRating.middle, Locale
		.getDefault(), PrivacyLevel.insensible);
	sendUIRequest(oe);
    }

	@Override
	public void dialogAborted(String arg0, Resource arg1) {
		// TODO Auto-generated method stub
		
	}

    // showGoogleScreen
    // public void showGoogleScreen(User user, Calendar cal) {
    // LogUtils.logInfo(mcontext, this.getClass(), "showEventsScreen",
    // new Object[] {
    // "Sending UI Request: showGoogleScreen for user {}",
    // user.getURI() }, null);
    //
    // Form f = webUI.getGoogleForm(cal);
    // UIRequest oe = new UIRequest(user, f, LevelRating.middle, Locale
    // .getDefault(), PrivacyLevel.insensible);
    // sendUIRequest(oe);
    // }

}
