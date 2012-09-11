/*
	Copyright 2011-2012 Itaca-TSB, http://www.tsb.upv.es
	Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package uAAL.services;

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

  private static final String OUTPUT_PRECAUTION = PRECAUTION_CONSUMER_NAMESPACE + "precaution";

  public MedicationConsumer(ModuleContext moduleContext) {

    serviceCaller = new DefaultServiceCaller(moduleContext);
  }


  public static Precaution requestDetails(User user) {

    ServiceRequest serviceRequest = new ServiceRequest(new Precaution(), user);

    serviceRequest.addRequiredOutput(OUTPUT_PRECAUTION, new String[]{Precaution.SIDEEFFECT, Precaution.INCOMPLIANCE});

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();
//    Log.info("callStatus %s", MedicationConsumer.class, callStatus);

    Precaution precaution = getPrecaution(serviceResponse);
    if (callStatus.equals(CallStatus.succeeded) && precaution != null) {
      return precaution;
    }

    return null;
  }

  private static Precaution getPrecaution(ServiceResponse serviceResponse) {
    List list = serviceResponse.getOutput(OUTPUT_PRECAUTION, true);
    if (list.isEmpty() || list.size() > 1) {
      return null;
    }

    return (Precaution) list.get(0);

  }
}
