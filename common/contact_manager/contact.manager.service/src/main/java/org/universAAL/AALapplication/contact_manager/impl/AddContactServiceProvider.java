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

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.User;


/**
 * @author George Fournadjiev
 */
public final class AddContactServiceProvider extends ServiceCallee {


  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  static {
    invalidInput.addOutput(new ProcessOutput(
        ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
  }

  public AddContactServiceProvider(ModuleContext context) {
    super(context, AddContactService.profiles);

  }

  public void communicationChannelBroken() {
    //Not implemented yet
  }

  public ServiceResponse handleCall(ServiceCall call) {
    String processURI = call.getProcessURI();

    Log.info("Received call %s", getClass(), processURI);

    User involvedUser = (User) call.getInvolvedUser();

    Log.info("involvedUser %s", getClass(), involvedUser);

    if (involvedUser == null) {
      return invalidInput;
    }

    System.out.println("processURI = " + processURI);

    if (!processURI.startsWith(AddContactService.ADD_CONTACT)) {
      return invalidInput;
    }

    PersonalInformationSubprofile personalInformationSubprofile =
        (PersonalInformationSubprofile) call.getInputValue(AddContactService.INPUT_ADD_CONTACT);

    System.out.println("personalInformationSubprofile = " + personalInformationSubprofile);


    if (personalInformationSubprofile != null && personalInformationSubprofile.isWellFormed()) {
      printPersonalInformationSubprofileData(personalInformationSubprofile);
      return getSuccessfulServiceResponse(involvedUser);
    }

    return invalidInput;
  }

  private ServiceResponse getSuccessfulServiceResponse(User involvedUser) {
    String userId = involvedUser.getURI();
    Log.info("Add contact Service Response for the user %s", getClass(), userId);

    return new ServiceResponse(CallStatus.succeeded);
  }

  private void printPersonalInformationSubprofileData(PersonalInformationSubprofile personalInformationSubprofile) {
    /*Log.info("personalInformationSubprofile.getPrescriptionId() = %s",
        HealthPrescriptionServiceProvider.class, personalInformationSubprofile.getPrescriptionId());
    Log.info("personalInformationSubprofile.getName() = %s",
        HealthPrescriptionServiceProvider.class, personalInformationSubprofile.getName());
    Log.info("personalInformationSubprofile.getDescription() = %s",
        HealthPrescriptionServiceProvider.class, personalInformationSubprofile.getDescription());
    Log.info("personalInformationSubprofile.getDoctorName() = %s",
        HealthPrescriptionServiceProvider.class, personalInformationSubprofile.getDoctorName());
    Log.info("personalInformationSubprofile.getStatus() = %s",
        HealthPrescriptionServiceProvider.class, personalInformationSubprofile.getStatus());
    Log.info("personalInformationSubprofile.getMedicationTreatmentStartDate() = %s",
        HealthPrescriptionServiceProvider.class, personalInformationSubprofile.getMedicationTreatmentStartDate());
    Log.info("personalInformationSubprofile.getTreatmentPlanning().getStartDate() = %s",
        HealthPrescriptionServiceProvider.class, personalInformationSubprofile.getTreatmentPlanning().getStartDate());
    Log.info("personalInformationSubprofile.getTreatmentPlanning().getEndDate() = %s",
        HealthPrescriptionServiceProvider.class, personalInformationSubprofile.getTreatmentPlanning().getEndDate());
//   List<Medicine> medicines = personalInformationSubprofile.getMedicine();
    Medicine medicine = personalInformationSubprofile.getMedicine();
    Log.info("Printing medicine", HealthPrescriptionServiceProvider.class);*/
  }



}
