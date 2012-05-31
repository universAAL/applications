package org.universAAL.AALapplication.medication_manager.ui.impl;

import org.universAAL.AALapplication.medication_manager.ui.ReminderDialog;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.User;

public class ReminderDialogProvider extends ServiceCallee {

  private static final String NAMESPACE = "http://ontologies.universAAL.com/Medication.owl#";
  private static final String MY_URI = NAMESPACE + "ReminderDialogService";
  private static final String START_UI = NAMESPACE + "startUI";

  private ModuleContext ctxt;

  public ReminderDialogProvider(ModuleContext context, ServiceProfile[] realizedServices) {
    super(context, realizedServices);
    this.ctxt = context;
  }

  public ReminderDialogProvider(ModuleContext context) {
    this(context, getProfiles());
  }

  private static ServiceProfile[] getProfiles() {
    ServiceProfile initDP = InitialServiceDialog
        .createInitialDialogProfile(
            MY_URI,
            "http://depot.universAAL.com",
            "Medication Manager : Reminder Dialog",
            START_UI);
    return new ServiceProfile[]{initDP};
  }

  @Override
  public void communicationChannelBroken() {
    // TODO Auto-generated method stub

  }

  @Override
  public ServiceResponse handleCall(ServiceCall call) {
    Object inputUser = call.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
    new ReminderDialog(this.ctxt).showDialog((User)inputUser);
    return new ServiceResponse(CallStatus.succeeded);
  }

}
