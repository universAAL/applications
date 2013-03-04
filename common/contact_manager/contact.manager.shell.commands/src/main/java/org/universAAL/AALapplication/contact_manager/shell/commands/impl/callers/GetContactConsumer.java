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


package org.universAAL.AALapplication.contact_manager.shell.commands.impl.callers;

import org.universAAL.AALapplication.contact_manager.shell.commands.impl.ContactManagerShellException;
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

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class GetContactConsumer {

  private static ServiceCaller serviceCaller;

  private static final String PERSONAL_INFORMATION_SUBPROFILE_CONSUMER_NAMESPACE =
      "http://ontology.igd.fhg.de/ContactConsumer.owl#";

  private static final String OUTPUT =
      PERSONAL_INFORMATION_SUBPROFILE_CONSUMER_NAMESPACE + "PersonalInformationSubprofile";

  public GetContactConsumer(ModuleContext moduleContext) {

    serviceCaller = new DefaultServiceCaller(moduleContext);

  }


  public static PersonalInformationSubprofile getContact(User user) {

    Log.info("Trying to remove a contact", GetContactConsumer.class);

    if (serviceCaller == null) {
      throw new ContactManagerShellException("The ServiceCaller is note set");
    }

    ServiceRequest serviceRequest = new ServiceRequest(new ProfilingService(), user);

    String[] ppInputAddContact = new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE};

    serviceRequest.addRequiredOutput(OUTPUT, ppInputAddContact);

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();

    Log.info("callStatus %s", GetContactConsumer.class, callStatus);

    if (callStatus.equals(CallStatus.succeeded)) {
      return getVCard(serviceResponse);
    }

    return null;
  }

  private static PersonalInformationSubprofile getVCard(ServiceResponse serviceResponse) {
    List output = serviceResponse.getOutput(OUTPUT, true);
    if (output.isEmpty() || output.size() > 1) {
      Log.info("received VCard object as a null or list is bigger than 1", GetContactConsumer.class);
      return null;
    }
    PersonalInformationSubprofile vcard = (PersonalInformationSubprofile) output.get(0);
    Log.info("received VCard : %s", GetContactConsumer.class, vcard);


    return vcard;

  }

}
