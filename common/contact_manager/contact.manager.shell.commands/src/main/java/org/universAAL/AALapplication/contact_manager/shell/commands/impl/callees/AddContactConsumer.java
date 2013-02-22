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


package org.universAAL.AALapplication.contact_manager.shell.commands.impl.callees;

import org.universAAL.AALapplication.contact_manager.shell.commands.impl.Log;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.service.ProfilingService;

/**
 * @author George Fournadjiev
 */
public final class AddContactConsumer {

  private static ServiceCaller serviceCaller;

  private static final String ADD_CONTACT_CONSUMER_NAMESPACE = "http://ontology.igd.fhg.de/AddContactConsumer.owl#";


  public AddContactConsumer(ModuleContext moduleContext) {

    serviceCaller = new DefaultServiceCaller(moduleContext);

  }


  public static boolean sendAddContact(User user, PersonalInformationSubprofile personalInformationSubprofile) {

    Log.info("Trying to send a contact", AddContactConsumer.class);

    ServiceRequest serviceRequest = new ServiceRequest(new ProfilingService(), user);

    String[] ppInputAddContact = new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE};

    serviceRequest.addAddEffect(ppInputAddContact, personalInformationSubprofile);

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();
    Log.info("callStatus %s", AddContactConsumer.class, callStatus);

    if (callStatus.equals(CallStatus.succeeded)) {
      return true;
    }

    return false;
  }

}
