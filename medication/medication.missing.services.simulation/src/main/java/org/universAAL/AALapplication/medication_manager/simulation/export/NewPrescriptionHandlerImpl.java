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
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.services.TreatmentManagementService;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionHandlerImpl extends NewPrescriptionHandler {

  public static final String INPUT_USER = HealthOntology.NAMESPACE + "user";
  public static final String INPUT_TREATMENT = HealthOntology.NAMESPACE + "treatment";

  public NewPrescriptionHandlerImpl(ModuleContext context, NewPrescriptionContextProvider contextProvider) {
    super(context, contextProvider);
  }


  public boolean callHealthService(MedicationTreatment medicationTreatment) {
    User saiedUser = UserIDs.getSaiedUser();
    ServiceRequest serviceRequest = new ServiceRequest(new TreatmentManagementService(), saiedUser);
//    serviceRequest.addValueFilter(new String[]{INPUT_USER}, saiedUser);
    Treatment treatment = (Treatment) medicationTreatment;
    serviceRequest.addAddEffect(new String[]{TreatmentManagementService.PROP_MANAGES_TREATMENT}, treatment);


    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();
    Log.info("callStatus %s", NewPrescriptionHandlerImpl.class, callStatus);

    if (callStatus.equals(CallStatus.succeeded)) {
      Log.info("The call succeeded", NewPrescriptionHandlerImpl.class);
      return true;
    } else {
      Log.error("There is the problem with the response with the Health Service", NewPrescriptionHandlerImpl.class);
      return false;
    }


  }


}
