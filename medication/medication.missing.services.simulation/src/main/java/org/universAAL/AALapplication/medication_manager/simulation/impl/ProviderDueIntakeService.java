package org.universAAL.AALapplication.medication_manager.simulation.impl;

import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.medMgr.DueIntake;

/**
 * @author George Fournadjiev
 */
public final class ProviderDueIntakeService extends DueIntake {

  public static final String DUE_INTAKE_SERVER_NAMESPACE =
      "http://ontologies.universAAL.com/DueIntakeServer.owl#";

  public static final String MY_URI = DUE_INTAKE_SERVER_NAMESPACE + "DueIntakeService";

  public static final String SERVICE_GET_DUE_INTAKE = DUE_INTAKE_SERVER_NAMESPACE + "getDueIntake";

  static final String OUTPUT_DUE_INTAKE = DUE_INTAKE_SERVER_NAMESPACE + "dueIntake";

  static final ServiceProfile[] profiles = new ServiceProfile[1];

  static {

    //Register

    System.out.println("URAAAAAAAAAAAAAAAAAAAA");

    OntologyManagement.getInstance().register(
        new SimpleOntology(MY_URI, DueIntake.MY_URI,
            new ResourceFactoryImpl() {
              @Override
              public Resource createInstance(String classURI,
                                             String instanceURI, int factoryIndex) {
                return new ProviderDueIntakeService(instanceURI);
              }
            }));

    String[] ppDueIntake = new String[]{DueIntake.USER, DueIntake.DEVICE_ID};

    ProviderDueIntakeService getDueIntake =
        new ProviderDueIntakeService(SERVICE_GET_DUE_INTAKE);

    getDueIntake.addOutput(OUTPUT_DUE_INTAKE,
        DueIntake.MY_URI, 1, 1, ppDueIntake);

    profiles[0] = getDueIntake.myProfile;

  }

  private ProviderDueIntakeService(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }


}

