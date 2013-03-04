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


package org.universAAL.AALapplication.contact_manager.impl;

import org.universAAL.AALapplication.contact_manager.persistence.layer.ContactManagerPersistentService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.User;

import static org.universAAL.AALapplication.contact_manager.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class RemoveContactServiceProvider extends ServiceCallee {


  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  public RemoveContactServiceProvider(ModuleContext context) {
    super(context, RemoveContactService.profiles);

  }

  @Override
  public void communicationChannelBroken() {
    //Not implemented yet
  }

  @Override
  public ServiceResponse handleCall(ServiceCall call) {
    String processURI = call.getProcessURI();

    Log.info("Received call %s", getClass(), processURI);

    User involvedUser = (User) call.getInvolvedUser();

    Log.info("involvedUser %s", getClass(), involvedUser);

    if (involvedUser == null) {
      return invalidInput;
    }

    if (!processURI.startsWith(RemoveContactService.REMOVE_CONTACT)) {
      return invalidInput;
    }

    ContactManagerPersistentService persistentService = getContactManagerPersistentService();
    persistentService.removeVCard(involvedUser.getURI());


    return getSuccessfulServiceResponse(involvedUser);

  }

  private ServiceResponse getSuccessfulServiceResponse(User involvedUser) {
    String userId = involvedUser.getURI();
    Log.info("Remove contact Service Response for the user %s", getClass(), userId);

    return new ServiceResponse(CallStatus.succeeded);
  }
}
