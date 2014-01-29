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
import org.universAAL.ontology.medMgr.MedicationTreatment;
import org.universAAL.ontology.medMgr.NewMedicationTreatmentNotifier;

/**
 * @author George Fournadjiev
 */
public final class ProviderHealthPrescriptionService extends NewMedicationTreatmentNotifier {

  public static final String HEALTH_PRESCRIPTION_SERVICE_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/HealthPrescriptionServer.owl#";

  public static final String MY_URI = HEALTH_PRESCRIPTION_SERVICE_SERVER_NAMESPACE + "HealthPrescriptionService";

  public static final String SERVICE_NOTIFY = HEALTH_PRESCRIPTION_SERVICE_SERVER_NAMESPACE + "notify";

  static final String OUTPUT_RECEIVED_MESSAGE = HEALTH_PRESCRIPTION_SERVICE_SERVER_NAMESPACE + "receivedMessage";
  static final String INPUT_MEDICATION_TREATMENT = HEALTH_PRESCRIPTION_SERVICE_SERVER_NAMESPACE + "medicationTreatment";

  static final ServiceProfile[] profiles = new ServiceProfile[1];

  static {


    //Register

    OntologyManagement.getInstance().register(Activator.mc,
        new SimpleOntology(MY_URI, NewMedicationTreatmentNotifier.MY_URI,
            new ResourceFactory() {
              public Resource createInstance(String classURI,
                                             String instanceURI, int factoryIndex) {
                return new ProviderHealthPrescriptionService(instanceURI);
              }
            }));

    String[] ppNewPrescription = new String[]{NewMedicationTreatmentNotifier.PROP_RECEIVED_MESSAGE};

    ProviderHealthPrescriptionService notify =
        new ProviderHealthPrescriptionService(SERVICE_NOTIFY);

    String[] ppInputNewPrescription = new String[]{NewMedicationTreatmentNotifier.PROP_MEDICATION_TREATMENT};

    notify.addInputWithAddEffect(INPUT_MEDICATION_TREATMENT, MedicationTreatment.MY_URI, 1, 1, ppInputNewPrescription);
    notify.addOutput(OUTPUT_RECEIVED_MESSAGE,
        NewMedicationTreatmentNotifier.MY_URI, 1, 1, ppNewPrescription);


    profiles[0] = notify.myProfile;

  }

  private ProviderHealthPrescriptionService(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }

}

