package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.impl.MedicationFactory;
import org.universAAL.ontology.profile.User;

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
    oci.addDatatypeProperty(Precaution.PROP_SIDEEFFECT);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Precaution.PROP_SIDEEFFECT,
            TypeMapper.getDatatypeURI(String.class), 0, 1));
    oci.addDatatypeProperty(Precaution.PROP_INCOMPLIANCE);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Precaution.PROP_INCOMPLIANCE,
            TypeMapper.getDatatypeURI(String.class), 0, 1));

    // load Time
    oci = createNewOntClassInfo(Time.MY_URI, FACTORY, 1);
    oci.setResourceComment("The type of a Time");
    oci.setResourceLabel("Time");
    oci.addDatatypeProperty(Time.PROP_YEAR);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_YEAR,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.PROP_MONTH);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_MONTH,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.PROP_DAY);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_DAY,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.PROP_HOUR);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_HOUR,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.PROP_MINUTES);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_MINUTES,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));

    // load MissedIntake
    oci = createNewOntClassInfo(MissedIntake.MY_URI, FACTORY, 2);
    oci.setResourceComment("The type of a MissedIntake");
    oci.setResourceLabel("MissedIntake");
    oci.addObjectProperty(MissedIntake.PROP_TIME).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            MissedIntake.PROP_TIME, Time.MY_URI, 1, 1));
    oci.addObjectProperty(MissedIntake.PROP_USER).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            MissedIntake.PROP_USER, User.MY_URI, 1, 1));

    // load DueIntake
    oci = createNewOntClassInfo(DueIntake.MY_URI, FACTORY, 3);
    oci.setResourceComment("The type of a DueIntake");
    oci.setResourceLabel("DueIntake");
    oci.addDatatypeProperty(DueIntake.PROP_DEVICE_ID);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            DueIntake.PROP_DEVICE_ID, TypeMapper.getDatatypeURI(String.class), 1, 1));
    oci.addObjectProperty(DueIntake.PROP_TIME).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            DueIntake.PROP_TIME, Time.MY_URI, 1, 1));

    // load DispenserUpsideDown
    oci = createNewOntClassInfo(DispenserUpsideDown.MY_URI, FACTORY, 4);
    oci.setResourceComment("The type of a DispenserUpsideDown");
    oci.setResourceLabel("DispenserUpsideDown");
    oci.addDatatypeProperty(DispenserUpsideDown.PROP_DEVICE_ID);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            DispenserUpsideDown.PROP_DEVICE_ID, TypeMapper.getDatatypeURI(String.class), 1, 1));


  }
}
