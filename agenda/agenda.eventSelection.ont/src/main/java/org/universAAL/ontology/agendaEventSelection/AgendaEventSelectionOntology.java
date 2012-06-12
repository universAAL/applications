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
package org.universAAL.ontology.agendaEventSelection;


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

import org.universAAL.ontology.ProfileOntology;

import org.universAAL.ontology.agenda.AgendaOntology;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agendaEventSelection.service.EventSelectionToolService;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.profile.UserProfile;

/**
 * @author eandgrg
 */
public final class AgendaEventSelectionOntology extends Ontology {

    private static AgendaEventSelectionOntologyFactory factory = new AgendaEventSelectionOntologyFactory();
    public static final String NAMESPACE = Resource.uAAL_NAMESPACE_PREFIX+"EventSelectionTool.owl#";

    public AgendaEventSelectionOntology() {
	super(NAMESPACE);
    }

    public void create() {
	Resource r = getInfo();
	r
		.setResourceComment("The ontology defining the Agenda and Reminders service selection tool.");
	r.setResourceLabel("AgendaSelection");
	addImport(DataRepOntology.NAMESPACE);
	addImport(ServiceBusOntology.NAMESPACE);
	addImport(LocationOntology.NAMESPACE);
	addImport(ProfileOntology.NAMESPACE);
	addImport(AgendaOntology.NAMESPACE);

	OntClassInfoSetup oci;

	// ******* Enumeration classes of the ontology ******* //

	// load TimeSearchType
	oci = createNewAbstractOntClassInfo(TimeSearchType.MY_URI);
	oci.setResourceComment("The type of temporal search.");
	oci.setResourceLabel("Temporal search type");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.toEnumeration(new ManagedIndividual[] {
		TimeSearchType.startsBetween, TimeSearchType.endsBetween,
		TimeSearchType.startsAndEndsBetween,
		TimeSearchType.startsBeforeAndEndsAfter,
		TimeSearchType.allBefore, TimeSearchType.allAfter,
		TimeSearchType.allCases });

	// ******* Regular classes of the ontology ******* //
	
	// load FilterParams
	oci = createNewOntClassInfo(FilterParams.MY_URI, factory, 1);
	oci.setResourceComment("The class of all FilterParams.");
	oci.setResourceLabel("FilterParams");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(FilterParams.PROP_DT_BEGIN).setFunctional();
	oci.addDatatypeProperty(FilterParams.PROP_DT_END).setFunctional();
	oci.addDatatypeProperty(FilterParams.PROP_CATEGORY).setFunctional();
	oci.addDatatypeProperty(FilterParams.PROP_SPOKEN_LANGUAGE).setFunctional();
	oci.addObjectProperty(FilterParams.PROP_HAS_USER_PROFILE).setFunctional();
	oci.addObjectProperty(FilterParams.PROP_HAS_LOCATION).setFunctional();
	oci.addObjectProperty(FilterParams.PROP_HAS_SEARCH_TYPE).setFunctional();
	oci.addDatatypeProperty(FilterParams.PROP_DESCRIPTION).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FilterParams.PROP_DT_BEGIN, TypeMapper
			    .getDatatypeURI(XMLGregorianCalendar.class),0,1 ));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FilterParams.PROP_DT_END, TypeMapper
			    .getDatatypeURI(XMLGregorianCalendar.class),0,1 ));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FilterParams.PROP_CATEGORY, TypeMapper
			    .getDatatypeURI(String.class),0,1 ));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FilterParams.PROP_SPOKEN_LANGUAGE, TypeMapper
			    .getDatatypeURI(String.class),0,1 ));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FilterParams.PROP_HAS_USER_PROFILE, UserProfile.MY_URI,0,1 ));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FilterParams.PROP_HAS_LOCATION, Location.MY_URI,0,1 ));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FilterParams.PROP_HAS_SEARCH_TYPE, TimeSearchType.MY_URI,1,1 ));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FilterParams.PROP_DESCRIPTION, TypeMapper
			    .getDatatypeURI(String.class),1,1 ));

	// load EventSelectionTool
	oci = createNewOntClassInfo(EventSelectionTool.MY_URI, factory, 0);
	oci.setResourceComment("The class of a EventSelectionTool.");
	oci.setResourceLabel("EventSelectionTool");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addObjectProperty(EventSelectionTool.PROP_HAS_CALENDARS)
		.setFunctional();
	oci.addObjectProperty(EventSelectionTool.PROP_HAS_FILTER_PARAMS)
		.setFunctional();
	oci.addDatatypeProperty(EventSelectionTool.PROP_MAX_EVENT_NO)
		.setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			EventSelectionTool.PROP_HAS_CALENDARS, Calendar.MY_URI,
			2, 2));
	//FilterParams have to be loaded before use
	oci
		.addRestriction(MergedRestriction.getAllValuesRestriction(
			EventSelectionTool.PROP_HAS_FILTER_PARAMS,
			FilterParams.MY_URI));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			EventSelectionTool.PROP_MAX_EVENT_NO, TypeMapper
				.getDatatypeURI(Integer.class), 0, 1));

	/*
	 * Services
	 */

	// load EventSelectionToolService
	oci = createNewOntClassInfo(EventSelectionToolService.MY_URI, factory, 2);
	oci.setResourceComment("The class of services controling the EventSelectionTool Service.");
	oci.setResourceLabel("EventSelectionToolService");
	oci.addSuperClass(Service.MY_URI);
	oci.addObjectProperty(EventSelectionToolService.PROP_CONTROLS).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestriction(
			EventSelectionToolService.PROP_CONTROLS, EventSelectionTool.MY_URI));

    }
}
