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

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.medMgr.CaregiverNotifierData;
import org.universAAL.ontology.profile.User;

import static org.universAAL.AALapplication.medication_manager.simulation.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class CaregiverNotificationProvider extends ServiceCallee {


  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  static {
    invalidInput.addOutput(new ProcessOutput(
        ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
  }

  public CaregiverNotificationProvider(ModuleContext context) {
    super(context, ProviderCaregiverNotificationService.profiles);

  }

  public void communicationChannelBroken() {
    //Not implemented yet
  }

  public ServiceResponse handleCall(ServiceCall call) {
    try {
      String processURI = call.getProcessURI();

      Log.info("Received call %s", getClass(), processURI);

      User involvedUser = (User) call.getInvolvedUser();

      Log.info("involvedUser %s", getClass(), involvedUser);

      if (involvedUser == null) {
        return invalidInput;
      }

      if (processURI.startsWith(ProviderCaregiverNotificationService.SERVICE_NOTIFY)) {
        return getSuccessfulServiceResponse(call, involvedUser);
      }

      return invalidInput;
    } catch (Exception e) {
      Log.error(e, "Error while processing the client call", getClass());
      return invalidInput;
    }
  }

  private ServiceResponse getSuccessfulServiceResponse(ServiceCall call, User involvedUser) {
    String userId = involvedUser.getURI();
    CaregiverNotifierData caregiverNotifierData =
        (CaregiverNotifierData) call.getInputValue(ProviderCaregiverNotificationService.INPUT_CAREGIVER_NOTIFIER_DATA);

    if (caregiverNotifierData == null) {
      throw new MedicationManagerSimulationServicesException("CaregiverNotifierData object is null");
    }

    Log.info("Successful Caregiver Notification Service Response for the user %s", getClass(), userId);
    ServiceResponse response = new ServiceResponse(CallStatus.succeeded);

    String message = createMessage(caregiverNotifierData, involvedUser);

    response.addOutput(new ProcessOutput(ProviderCaregiverNotificationService.OUTPUT_RECEIVED_MESSAGE, message));

    return response;
  }

  private String createMessage(CaregiverNotifierData caregiverNotifierData, User involvedUser) {
    StringBuffer sb = new StringBuffer();
    sb.append("The Caregiver Notification Service received notification with the following content:\n\t");
    sb.append("The notification is for the following user: ");
    PersistentService persistentService = getPersistentService();
    PersonDao personDao = persistentService.getPersonDao();
    Person person = personDao.findPersonByPersonUri(involvedUser.getURI());
    sb.append(person.getName());
    sb.append(".\n\t Provided caregiver sms number: ");
    sb.append(caregiverNotifierData.getSmsNumber());
    sb.append(".\n\t With the following sms text: ");
    sb.append(caregiverNotifierData.getSmsText());

    return sb.toString();

  }

}
