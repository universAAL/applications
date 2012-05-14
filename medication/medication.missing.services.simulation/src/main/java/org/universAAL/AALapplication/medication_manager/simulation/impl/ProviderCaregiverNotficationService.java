package org.universAAL.AALapplication.medication_manager.simulation.impl;

import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.medMgr.MissedIntake;
import org.universAAL.ontology.medMgr.Precaution;

/**
 * @author George Fournadjiev
 */
public final class ProviderCaregiverNotficationService extends Precaution {

  public static final String CAREGIVER_NOTIFICATION_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/CaregiverNotificationServer.owl#";

  public static final String MY_URI = CAREGIVER_NOTIFICATION_SERVER_NAMESPACE + "CaregiverNotificationService";

  public static final String SERVICE_NOTIFY = CAREGIVER_NOTIFICATION_SERVER_NAMESPACE + "notify";

  static final String OUTPUT_NOTIFY = CAREGIVER_NOTIFICATION_SERVER_NAMESPACE + "notify";

  static final ServiceProfile[] profiles = new ServiceProfile[1];

  static {

    //Register

    OntologyManagement.getInstance().register(
        new SimpleOntology(MY_URI, MissedIntake.MY_URI,
            new ResourceFactoryImpl() {
              @Override
              public Resource createInstance(String classURI,
                                             String instanceURI, int factoryIndex) {
                return new ProviderCaregiverNotficationService(instanceURI);
              }
            }));

    String[] ppMissedIntake = new String[]{MissedIntake.TIME, MissedIntake.USER_ID};

    ProviderCaregiverNotficationService notify =
        new ProviderCaregiverNotficationService(SERVICE_NOTIFY);

    notify.addOutput(OUTPUT_NOTIFY,
        MissedIntake.MY_URI, 1, 1, ppMissedIntake);

    profiles[0] = notify.myProfile;

  }

  private ProviderCaregiverNotficationService(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }


}

