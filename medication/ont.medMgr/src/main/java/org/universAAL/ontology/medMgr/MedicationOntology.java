package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.ontology.impl.MedicationFactory;

/**
 * @author George Fournadjiev
 */

public final class MedicationOntology extends Ontology {

  private static MedicationFactory factory = new MedicationFactory();

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
    oci = createNewOntClassInfo(Precaution.MY_URI, factory, 0);
    oci.setResourceComment("The type of a Precaution");
    oci.setResourceLabel("Precaution");

    // load MissedIntake
    oci = createNewOntClassInfo(MissedIntake.MY_URI, factory, 1);
    oci.setResourceComment("The type of a MissedIntake");
    oci.setResourceLabel("MissedIntake");

  }
}
