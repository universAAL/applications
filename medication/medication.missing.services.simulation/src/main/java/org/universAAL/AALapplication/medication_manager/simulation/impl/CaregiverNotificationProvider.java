package org.universAAL.AALapplication.medication_manager.simulation.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;


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
    String processURI = call.getProcessURI();

    Log.info("Received call %s", getClass(), processURI);

    Resource involvedUser = call.getInvolvedUser();

    Log.info("involvedUser %s", getClass(), involvedUser);

    if (involvedUser == null) {
      return invalidInput;
    }

    if (processURI.startsWith(ProviderCaregiverNotificationService.SERVICE_NOTIFY)) {
      return getSuccessfulServiceResponse(involvedUser);
    }

    return invalidInput;
  }

  private ServiceResponse getSuccessfulServiceResponse(Resource involvedUser) {
    String userId = involvedUser.getURI();
    Log.info("Successful Service Response for the user %s", getClass(), userId);
    ServiceResponse response = new ServiceResponse(CallStatus.succeeded);

    return response;
  }

}
