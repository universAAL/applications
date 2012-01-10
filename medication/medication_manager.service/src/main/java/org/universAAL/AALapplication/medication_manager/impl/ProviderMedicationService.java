package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.medMgr.Precaution;

/**
 * @author George Fournadjiev
 */
public final class ProviderMedicationService extends Precaution {

  public static final String MEDICATION_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/MedicationServer.owl#";

  public static final String MY_URI = MEDICATION_SERVER_NAMESPACE + "MedicationService";

  public static final String SERVICE_GET_PRECAUTION = MEDICATION_SERVER_NAMESPACE + "getPrecaution";

  static final String OUTPUT_PRECAUTION = MEDICATION_SERVER_NAMESPACE + "precaution";

  static final ServiceProfile[] profiles = new ServiceProfile[1];

  static {

    OntologyManagement.getInstance().register(
        new SimpleOntology(MY_URI, Precaution.MY_URI,
            new ResourceFactoryImpl() {
              @Override
              public Resource createInstance(String classURI,
                                             String instanceURI, int factoryIndex) {
                return new ProviderMedicationService(instanceURI);
              }
            }));

    String[] ppPrecation = new String[] { Precaution.SIDEEFFECT, Precaution.INCOMPLIANCE};

    ProviderMedicationService getPrecation =
        new ProviderMedicationService(SERVICE_GET_PRECAUTION);

    getPrecation.addOutput(OUTPUT_PRECAUTION,
        Precaution.MY_URI, 1, 1, ppPrecation);

    profiles[0] = getPrecation.myProfile;

  }

  private ProviderMedicationService(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }


}

