package org.universAAL.ontology;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.ltba.ActivityReportType;
import org.universAAL.ontology.ltba.LTBAService;

/**
 * Main ontology for the LTBA Service and concepts.
 * 
 * @author mllorente
 * 
 */
public class LTBAOntology extends Ontology {

	private static LTBAFactory factory = new LTBAFactory();
	public static final String NAMESPACE = "http://ontology.universAAL.org/LTBA.owl#";

	public LTBAOntology() {
		super(NAMESPACE);
	}

	public LTBAOntology(String uri) {
		super(uri);
	}

	public void create() {
		Resource r = getInfo();
		// This ontology
		r
				.setResourceComment("Ontology for Long Term Behaviour Analyzer and related concepts.");
		r.setResourceLabel("Long Term Behaviour Analyzer");
		addImport(DataRepOntology.NAMESPACE);
		OntClassInfoSetup oci;
		// ActivityReportType
		oci = createNewAbstractOntClassInfo(ActivityReportType.MY_URI);
		oci
				.setResourceComment("Enumeration for listing the activity report types");
		oci.setResourceLabel("Activity report type");
		oci.addSuperClass(ManagedIndividual.MY_URI);

		// When creating enumerations you just need to list the possible values
		// in this method
		oci.toEnumeration(new ManagedIndividual[] { ActivityReportType.day,
				ActivityReportType.week, ActivityReportType.month });
		// LTBAService
		oci = createNewOntClassInfo(LTBAService.MY_URI, factory, 0);
		oci
				.setResourceComment("Service providing the intetactions with the LTBA");
		oci.setResourceLabel("LTBA Service");
		oci.addSuperClass(Service.MY_URI);
		// Status value On/Off
		oci.addDatatypeProperty(LTBAService.PROP_SERVICE_HAS_STATUS_VALUE)
				.setFunctional();
		oci.addRestriction(MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						LTBAService.PROP_SERVICE_HAS_STATUS_VALUE, TypeMapper
								.getDatatypeURI(Boolean.class), 1, 1));
		// Text report
		oci.addDatatypeProperty(LTBAService.PROP_HAS_TEXT_REPORT)
				.setFunctional();
		oci.addRestriction(MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						LTBAService.PROP_HAS_TEXT_REPORT, TypeMapper
								.getDatatypeURI(String.class), 1, 1));
		// Week report
		oci.addDatatypeProperty(LTBAService.PROP_HAS_WEEK_REPORT)
				.setFunctional();
		oci.addRestriction(MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						LTBAService.PROP_HAS_WEEK_REPORT, TypeMapper
								.getDatatypeURI(String.class), 1, 1));
		// Activity report
		oci.addDatatypeProperty(LTBAService.PROP_HAS_ACTIVITY_REPORT)
				.setFunctional();
		oci.addRestriction(MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						LTBAService.PROP_HAS_ACTIVITY_REPORT,
						ActivityReportType.MY_URI, 1, 1));

	}

}
