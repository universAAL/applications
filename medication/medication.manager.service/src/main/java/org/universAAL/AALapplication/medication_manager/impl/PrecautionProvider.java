package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.profile.User;

import java.util.Arrays;

/**
 * @author George Fournadjiev
 */
public final class PrecautionProvider extends ServiceCallee {

  private final MyPrecautionDatabase myPrecaution;

  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  static {
    invalidInput.addOutput(new ProcessOutput(
        ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
  }

  public PrecautionProvider(ModuleContext context) {
    super(context, ProviderPrecautionService.profiles);

    myPrecaution = new MyPrecautionDatabase();
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

    if (processURI.startsWith(ProviderPrecautionService.SERVICE_GET_PRECAUTION)) {
      return getSuccessfulServiceResponse(involvedUser);
    }

    return invalidInput;
  }

  private ServiceResponse getSuccessfulServiceResponse(User involvedUser) {
    Log.info("Successful Service Response for the user %s", getClass(), involvedUser);
    return getPrecaution(involvedUser);
  }

  private ServiceResponse getPrecaution(User user) {
    ServiceResponse response = new ServiceResponse(CallStatus.succeeded);

    Precaution[] precautions = MyPrecautionDatabase.getPrecaution(user);

    Log.info("Found the following precaution: %s", getClass(), Arrays.asList(precautions));

    if (precautions == null || precautions.length != 2) {
      return invalidInput;
    }

    response.addOutput(new ProcessOutput(ProviderPrecautionService.OUTPUT_PRECAUTION_SIDEEFFECT, precautions[0]));
    response.addOutput(new ProcessOutput(ProviderPrecautionService.OUTPUT_PRECAUTION_INCOMPLIANCE, precautions[1]));

    return response;
  }
}
