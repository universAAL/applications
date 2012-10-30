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


package org.universAAL.AALapplication.medication_manager.simulation;

import org.universAAL.AALapplication.medication_manager.simulation.impl.Log;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.profile.User;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class MedicationConsumer {

  private static ServiceCaller serviceCaller;

  private static final String PRECAUTION_CONSUMER_NAMESPACE = "http://ontology.igd.fhg.de/MedicationConsumer.owl#";

  private static final String OUTPUT_PRECAUTION_SIDEEFFECT = PRECAUTION_CONSUMER_NAMESPACE + "sideeffect";
  private static final String OUTPUT_PRECAUTION_INCOMPLIANCE = PRECAUTION_CONSUMER_NAMESPACE + "incompliance";

  public MedicationConsumer(ModuleContext moduleContext) {

    serviceCaller = new DefaultServiceCaller(moduleContext);
  }


  public static Precaution[] requestDetails(User user) {

    ServiceRequest serviceRequest = new ServiceRequest(new Precaution(), user);

    serviceRequest.addRequiredOutput(OUTPUT_PRECAUTION_SIDEEFFECT, new String[]{Precaution.PROP_SIDEEFFECT});
    serviceRequest.addRequiredOutput(OUTPUT_PRECAUTION_INCOMPLIANCE, new String[]{Precaution.PROP_INCOMPLIANCE});

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();
    Log.info("callStatus %s", MedicationConsumer.class, callStatus);

    Precaution[] precaution = getPrecaution(serviceResponse);
    if (callStatus.equals(CallStatus.succeeded) && precaution != null) {
      return precaution;
    }

    return null;
  }

  private static Precaution[] getPrecaution(ServiceResponse serviceResponse) {
    Precaution[] precautionsArray = new Precaution[2];
    List listSideeffect = serviceResponse.getOutput(OUTPUT_PRECAUTION_SIDEEFFECT, true);
    if (listSideeffect.isEmpty() || listSideeffect.size() > 1) {
      Log.info("received Precaution object as a null or list is bigger than 1", MedicationConsumer.class);
      return null;
    }
    precautionsArray[0] = (Precaution) listSideeffect.get(0);
    Log.info("received Precaution with sideeffect %s", MedicationConsumer.class, precautionsArray[0].getSideEffect());

    List listIncompliance = serviceResponse.getOutput(OUTPUT_PRECAUTION_INCOMPLIANCE, true);
    if (listIncompliance.isEmpty() || listIncompliance.size() > 1) {
      Log.info("received Precaution object as a null or list is bigger than 1", MedicationConsumer.class);
    }

    precautionsArray[1] = (Precaution) listIncompliance.get(0);

    Log.info("received Precaution with incompliance %s", MedicationConsumer.class, precautionsArray[1].getIncompliance());

    return precautionsArray;

  }
}
