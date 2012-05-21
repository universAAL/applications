/*******************************************************************************
 * Copyright 2012 Ericsson Nikola Tesla d.d.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.ontology.agenda;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.ontology.agenda.service.CalendarUIService;
import org.universAAL.ontology.agenda.service.ExternalCalendar;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.location.address.Address;
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;

/**
 * @author eandgrg
 */
public final class AgendaOntology extends Ontology {

    private static AgendaOntologyFactory factory = new AgendaOntologyFactory();
    public static final String NAMESPACE = "http://ontology.universaal.org/PersonalAgenda.owl#";

    public AgendaOntology() {
	super(NAMESPACE);
    }

    public void create() {
	Resource r = getInfo();
	r
		.setResourceComment("The ontology defining the Agenda and Reminders service.");
	r.setResourceLabel("Agenda");
	addImport(DataRepOntology.NAMESPACE);
	addImport(ServiceBusOntology.NAMESPACE);
	addImport(LocationOntology.NAMESPACE);
	addImport(ProfileOntology.NAMESPACE);

	OntClassInfoSetup oci;

	// ******* Enumeration classes of the ontology ******* //

	// load CEType
	oci = createNewAbstractOntClassInfo(CEType.MY_URI);
	oci
		.setResourceComment("This class defines the types of possible type of the Event for Agenda when it is when it is beeing published.");
	oci.setResourceLabel("Published CEType");
	oci.toEnumeration(new ManagedIndividual[] { CEType.newEvent,
		CEType.updatedEvent, CEType.deletedEvent, CEType.reminder,
		CEType.startEvent, CEType.endEvent, CEType.plannedEvent });

	// load ReminderType
	oci = createNewAbstractOntClassInfo(ReminderType.MY_URI);
	oci.setResourceComment("This class defines the types of Reminders.");
	oci.setResourceLabel("ReminderType");
	oci.toEnumeration(new ManagedIndividual[] { ReminderType.noReminder,
		ReminderType.blinkingLight, ReminderType.sound,
		ReminderType.visualMessage, ReminderType.voiceMessage });

	// ******* Regular classes of the ontology ******* //

	// load Calendar
	oci = createNewOntClassInfo(Calendar.MY_URI, factory, 0);
	oci.setResourceComment("The class of a calendar");
	oci.setResourceLabel("Calendar");
	oci.addObjectProperty(Calendar.PROP_HAS_EVENT).setFunctional();
	oci.addObjectProperty(Calendar.PROP_HAS_OWNER).setFunctional();
	oci.addDatatypeProperty(Calendar.PROP_NAME).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		Calendar.PROP_HAS_EVENT, Event.MY_URI));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Calendar.PROP_HAS_OWNER, User.MY_URI, 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Calendar.PROP_NAME,
			TypeMapper.getDatatypeURI(String.class), 0, 1));

	// load Event
	oci = createNewOntClassInfo(Event.MY_URI, factory, 2);
	oci.setResourceComment("The class of all events.");
	oci.setResourceLabel("Event");
	oci.addObjectProperty(Event.PROP_HAS_REMINDER).setFunctional();
	oci.addObjectProperty(Event.PROP_HAS_EVENT_DETAILS).setFunctional();
	oci.addDatatypeProperty(Event.PROP_ID).setFunctional();
	oci.addObjectProperty(Event.PROP_CE_TYPE).setFunctional();
	oci.addDatatypeProperty(Event.PROP_PERSISTENT).setFunctional();
	oci.addDatatypeProperty(Event.PROP_VISIBLE).setFunctional();
	oci.addObjectProperty(Event.PROP_HAS_PARENT_CALENDAR).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Event.PROP_HAS_REMINDER, Reminder.MY_URI, 0, 1));
	oci
		.addRestriction(MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				Event.PROP_HAS_EVENT_DETAILS,
				EventDetails.MY_URI, 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Event.PROP_ID,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Event.PROP_CE_TYPE,
			CEType.MY_URI, 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Event.PROP_PERSISTENT,
			TypeMapper.getDatatypeURI(Boolean.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Event.PROP_VISIBLE,
			TypeMapper.getDatatypeURI(Boolean.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Event.PROP_HAS_PARENT_CALENDAR, Calendar.MY_URI, 0, 1));

	// load EventDetails
	oci = createNewOntClassInfo(EventDetails.MY_URI, factory, 3);
	oci.setResourceComment("The class of event details.");
	oci.setResourceLabel("Event details");
	oci.addDatatypeProperty(EventDetails.PROP_CATEGORY).setFunctional();
	oci.addObjectProperty(EventDetails.PROP_HAS_ADDRESS).setFunctional();
	oci.addDatatypeProperty(EventDetails.PROP_DESCRIPTION).setFunctional();
	oci.addDatatypeProperty(EventDetails.PROP_PLACE_NAME).setFunctional();
	oci.addDatatypeProperty(EventDetails.PROP_SPOKEN_LANGUAGE)
		.setFunctional();
	oci.addDatatypeProperty(EventDetails.PROP_PARTICIPANTS).setFunctional();
	oci.addObjectProperty(EventDetails.PROP_HAS_VALID_PERIOD)
		.setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			EventDetails.PROP_CATEGORY, TypeMapper
				.getDatatypeURI(String.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			EventDetails.PROP_HAS_ADDRESS, Address.MY_URI, 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			EventDetails.PROP_DESCRIPTION, TypeMapper
				.getDatatypeURI(String.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			EventDetails.PROP_PLACE_NAME, TypeMapper
				.getDatatypeURI(String.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			EventDetails.PROP_SPOKEN_LANGUAGE, TypeMapper
				.getDatatypeURI(String.class), 0, 1));
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		EventDetails.PROP_PARTICIPANTS, TypeMapper
			.getDatatypeURI(String.class)));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			EventDetails.PROP_HAS_VALID_PERIOD,
			TimeInterval.MY_URI, 0, 1));

	// load Reminder
	oci = createNewOntClassInfo(Reminder.MY_URI, factory, 1);
	oci.setResourceComment("The class of a reminder.");
	oci.setResourceLabel("Reminder");
	oci.addObjectProperty(Reminder.PROP_HAS_TYPE).setFunctional();
	oci.addDatatypeProperty(Reminder.PROP_HAS_TIME).setFunctional();
	oci.addObjectProperty(Reminder.PROP_HAS_USER_PROFILE).setFunctional();
	oci.addDatatypeProperty(Reminder.PROP_MESSAGE).setFunctional();
	oci.addDatatypeProperty(Reminder.PROP_TRIGGER_TIMES).setFunctional();
	oci.addDatatypeProperty(Reminder.PROP_REPEAT_INTERVAL).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Reminder.PROP_HAS_TYPE,
			ReminderType.MY_URI, 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Reminder.PROP_HAS_TIME,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Reminder.PROP_HAS_USER_PROFILE, UserProfile.MY_URI, 0,
			1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Reminder.PROP_MESSAGE,
			TypeMapper.getDatatypeURI(String.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Reminder.PROP_TRIGGER_TIMES, TypeMapper
				.getDatatypeURI(Integer.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Reminder.PROP_REPEAT_INTERVAL, TypeMapper
				.getDatatypeURI(Integer.class), 0, 1));

	// load TimeInterval
	oci = createNewOntClassInfo(TimeInterval.MY_URI, factory, 4);
	oci.setResourceComment("The class of a time interval.");
	oci.setResourceLabel("Time interval");
	oci.addDatatypeProperty(TimeInterval.PROP_TIME_PERIOD).setFunctional();
	oci.addDatatypeProperty(TimeInterval.PROP_START_TIME).setFunctional();
	oci.addDatatypeProperty(TimeInterval.PROP_END_TIME).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			TimeInterval.PROP_TIME_PERIOD, TypeMapper
				.getDatatypeURI(Duration.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			TimeInterval.PROP_START_TIME, TypeMapper
				.getDatatypeURI(XMLGregorianCalendar.class), 1,
			1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			TimeInterval.PROP_END_TIME, TypeMapper
				.getDatatypeURI(XMLGregorianCalendar.class), 1,
			1));

	/*
	 * Services
	 */

	// load CalendarAgenda
	oci = createNewOntClassInfo(CalendarAgenda.MY_URI, factory, 5);
	oci
		.setResourceComment("The class of services controling the Personal Agenda.");
	oci.setResourceLabel("Personal Agenda");
	oci.addSuperClass(Service.MY_URI);
	oci.addObjectProperty(CalendarAgenda.PROP_CONTROLS).setFunctional();
	oci.addObjectProperty(CalendarAgenda.PROP_CONNECTED_TO).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		CalendarAgenda.PROP_CONTROLS, Calendar.MY_URI));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			CalendarAgenda.PROP_CONNECTED_TO,
			ExternalCalendar.MY_URI, 1,
			CalendarAgenda.MAX_NUMBER_OF_EXTERNAL_CALENDARS));

	// load CalendarUIService
	oci = createNewOntClassInfo(CalendarUIService.MY_URI, factory, 6);
	oci
		.setResourceComment("The class of services controling the UI of Personal Agenda.");
	oci.setResourceLabel("Calendar/Agenda UI service");
	oci.addSuperClass(Service.MY_URI);
	oci.addObjectProperty(CalendarUIService.PROP_CONTROLS).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestriction(
			CalendarUIService.PROP_CONTROLS,
			Calendar.MY_URI));

	// load MobileDevice?
//	oci = createNewOntClassInfo(MobileDevice.MY_URI, factory, 7);
//	oci.setResourceComment("The class of a Mobile device.");
//	oci.setResourceLabel("Mobile Device");

    }
}
