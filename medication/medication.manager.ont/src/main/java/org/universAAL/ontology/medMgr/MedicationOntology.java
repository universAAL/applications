package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.impl.MedicationFactory;

/**
 * @author George Fournadjiev
 */

public final class MedicationOntology extends Ontology {

  private static final MedicationFactory FACTORY = new MedicationFactory();

  public static final String NAMESPACE = "http://ontology.universaal.org/Medication.owl#";

  public MedicationOntology() {
    super(NAMESPACE);
  }


  public void create() {
    Resource resource = getInfo();
    resource.setResourceComment("The medication ontology defining the precaution(warning) server response");
    resource.setResourceLabel("Medication");

    OntClassInfoSetup oci;

    // load Precaution
    oci = createNewOntClassInfo(Precaution.MY_URI, FACTORY, 0);
    oci.setResourceComment("The type of a Precaution");
    oci.setResourceLabel("Precaution");
    oci.addSuperClass(Service.MY_URI);
    oci.addDatatypeProperty(Precaution.SIDEEFFECT);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Precaution.SIDEEFFECT,
            TypeMapper.getDatatypeURI(String.class), 0, 1));
    oci.addDatatypeProperty(Precaution.INCOMPLIANCE);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Precaution.INCOMPLIANCE,
            TypeMapper.getDatatypeURI(String.class), 0, 1));

    // load Time
    oci = createNewOntClassInfo(Time.MY_URI, FACTORY, 1);
    oci.setResourceComment("The type of a Time");
    oci.setResourceLabel("Time");
    oci.addDatatypeProperty(Time.YEAR);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.YEAR,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.MONTH);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.MONTH,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.DAY);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.DAY,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.HOUR);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.HOUR,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.MINUTES);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.MINUTES,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));

    // load MissedIntake
    oci = createNewOntClassInfo(MissedIntake.MY_URI, FACTORY, 2);
    oci.setResourceComment("The type of a MissedIntake");
    oci.setResourceLabel("MissedIntake");
    oci.addObjectProperty(MissedIntake.TIME).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            MissedIntake.TIME, Time.MY_URI, 1, 1));
    oci.addDatatypeProperty(MissedIntake.USER_ID);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            MissedIntake.USER_ID,
            TypeMapper.getDatatypeURI(String.class), 0, 1));


  }
}
