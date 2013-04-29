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


package org.universAAL.AALapplication.medication_manager.simulation.export;

import org.universAAL.AALapplication.medication_manager.simulation.impl.Log;
import org.universAAL.AALapplication.medication_manager.simulation.impl.NewPrescriptionContextProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.medMgr.MedicationTreatment;
import org.universAAL.ontology.medMgr.NewMedicationTreatmentNotifier;
import org.universAAL.ontology.medMgr.UserIDs;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionHandlerMocked extends NewPrescriptionHandler {

  private static final String NEW_MEDICATION_TREATMENT_NOTIFIER_NAMESPACE =
      "http://ontology.igd.fhg.de/NewMedicationTreatmentNotifier.owl#";
  private static final String OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE =
      NEW_MEDICATION_TREATMENT_NOTIFIER_NAMESPACE + "receivedMessage";

  public NewPrescriptionHandlerMocked(ModuleContext context, NewPrescriptionContextProvider contextProvider) {
    super(context, contextProvider);

  }

  public boolean callHealthService(MedicationTreatment medicationTreatment) {
    ServiceRequest serviceRequest = new ServiceRequest(new NewMedicationTreatmentNotifier(), UserIDs.getSaiedUser());
    serviceRequest.addAddEffect(new String[]{NewMedicationTreatmentNotifier.PROP_MEDICATION_TREATMENT}, medicationTreatment);
    serviceRequest.addRequiredOutput(OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE,
        new String[]{NewMedicationTreatmentNotifier.PROP_RECEIVED_MESSAGE});

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();
    Log.info("callStatus %s", NewPrescriptionHandlerMocked.class, callStatus);

    String receivedMessage = getReceivedMessage(serviceResponse);
    if (callStatus.equals(CallStatus.succeeded) && receivedMessage != null) {
      Log.info("The received message from the Health Service is %s", NewPrescriptionHandlerMocked.class, receivedMessage);
      return true;
    } else {
      Log.error("There is the problem with the response with the Health Service", NewPrescriptionHandlerMocked.class);
      return false;
    }


  }

  private String getReceivedMessage(ServiceResponse serviceResponse) {
    List receivedMessageList = serviceResponse.getOutput(OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE, true);
    if (receivedMessageList.isEmpty() || receivedMessageList.size() > 1) {
      Log.info("received response object as a null or list is bigger than 1", NewPrescriptionHandlerMocked.class);
      return null;
    }

    return (String) receivedMessageList.get(0);

  }


}
