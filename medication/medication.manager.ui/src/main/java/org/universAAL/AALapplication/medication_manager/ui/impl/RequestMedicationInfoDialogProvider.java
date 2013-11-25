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


package org.universAAL.AALapplication.medication_manager.ui.impl;

import org.universAAL.AALapplication.medication_manager.ui.RequestMedicationInfoDialog;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.User;

public class RequestMedicationInfoDialogProvider extends ServiceCallee {

  private static final String NAMESPACE = "http://ontologies.universAAL.com/Medication.owl#";
  private static final String MY_URI = NAMESPACE + "RequestMedicationInfoDialogService";
  private static final String START_UI = NAMESPACE + "startUI";

  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  private ModuleContext ctxt;

  public RequestMedicationInfoDialogProvider(ModuleContext context, ServiceProfile[] realizedServices) {
    super(context, realizedServices);
    this.ctxt = context;
  }

  public RequestMedicationInfoDialogProvider(ModuleContext context) {
    this(context, getProfiles());
  }

  private static ServiceProfile[] getProfiles() {
    ServiceProfile initDP = InitialServiceDialog
        .createInitialDialogProfile(
            MY_URI,
            "http://depot.universAAL.com",
            "Medication Manager : Request Medication information Dialog",
            START_UI);
    return new ServiceProfile[]{initDP};
  }

  @Override
  public void communicationChannelBroken() {
    // TODO Auto-generated method stub

  }

  @Override
  public ServiceResponse handleCall(ServiceCall call) {
    try {
      User inputUser = (User) call.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
      new RequestMedicationInfoDialog(this.ctxt).showDialog((inputUser));
      return new ServiceResponse(CallStatus.succeeded);
    } catch (Exception e) {
      Log.error(e, "Error while processing the client call", getClass());
      return invalidInput;
    }
  }

}
