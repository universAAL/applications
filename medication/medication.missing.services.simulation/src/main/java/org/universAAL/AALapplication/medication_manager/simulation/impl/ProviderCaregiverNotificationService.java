/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
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


package org.universAAL.AALapplication.medication_manager.simulation.impl;

import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.medMgr.CaregiverNotifier;
import org.universAAL.ontology.medMgr.CaregiverNotifierData;

/**
 * @author George Fournadjiev
 */
public final class ProviderCaregiverNotificationService extends CaregiverNotifier {

  public static final String CAREGIVER_NOTIFICATION_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/CaregiverNotificationServer.owl#";

  public static final String MY_URI = CAREGIVER_NOTIFICATION_SERVER_NAMESPACE + "CaregiverNotificationService";

  public static final String SERVICE_NOTIFY = CAREGIVER_NOTIFICATION_SERVER_NAMESPACE + "notify";

  static final String INPUT_CAREGIVER_NOTIFIER_DATA = CAREGIVER_NOTIFICATION_SERVER_NAMESPACE + "CaregiverNotifierData";

  static final String OUTPUT_RECEIVED_MESSAGE = CAREGIVER_NOTIFICATION_SERVER_NAMESPACE + "receivedMessage";

  static final ServiceProfile[] profiles = new ServiceProfile[1];

  static {

    //Register

    OntologyManagement.getInstance().register(Activator.mc,
        new SimpleOntology(MY_URI, CaregiverNotifier.MY_URI,
            new ResourceFactory() {
              @Override
              public Resource createInstance(String classURI,
                                             String instanceURI, int factoryIndex) {
                return new ProviderCaregiverNotificationService(instanceURI);
              }
            }));

    String[] ppInputCaregiverNotifier = new String[]{CaregiverNotifier.PROP_CAREGIVER_NOTIFIER_DATA};

    ProviderCaregiverNotificationService notify =
        new ProviderCaregiverNotificationService(SERVICE_NOTIFY);

    notify.addInputWithAddEffect(INPUT_CAREGIVER_NOTIFIER_DATA, CaregiverNotifierData.MY_URI, 1, 1, ppInputCaregiverNotifier);

    String[] ppOutputReceivedMessage = new String[]{CaregiverNotifier.PROP_RECEIVED_MESSAGE};

    notify.addOutput(OUTPUT_RECEIVED_MESSAGE,
        CaregiverNotifier.MY_URI, 1, 1, ppOutputReceivedMessage);

    profiles[0] = notify.myProfile;

  }

  private ProviderCaregiverNotificationService(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }

}

