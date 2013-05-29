package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.medication.MedicationOntology;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.service.ProfilingService;

import java.util.ArrayList;
import java.util.List;

public class UserManagerImpl {

  private static ServiceCaller caller = null;

  private static final String OUTPUT_GETPROFILABLE = MedicationOntology.NAMESPACE
      + "out1";
  private static final String OUTPUT_GETUSERS = MedicationOntology.NAMESPACE
      + "out3";
  private static final String OUTPUT = MedicationOntology.NAMESPACE
      + "outX";

  public UserManagerImpl(ModuleContext context) {
    caller = new DefaultServiceCaller(context);
  }

  public boolean addUser(User user) {
    Log.info("Add user with URI:%", getClass(), user.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS}, user);
    ServiceResponse resp = caller.call(req);
    CallStatus callStatus = resp.getCallStatus();
    Log.info("CallStatus:%", getClass(), callStatus.name());

    return CallStatus.succeeded.equals(callStatus);
  }

  public List<User> getAllUsers() {
    System.out.println("Profile Agent: getAllUsers");
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addRequiredOutput(OUTPUT_GETUSERS, new String[]{ProfilingService.PROP_CONTROLS});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETUSERS);
      if (out != null) {
        System.out.println(out.toString());
        List<User> users = new ArrayList<User>();
        List outl = (List) out;
        for (int i = 0; i < outl.size(); i++) {
          User ur = (User) outl.get(i);
          users.add(ur);
        }
        return users;
      } else {
        System.out.println("NOTHING!");
        return null;
      }
    } else {
      System.out.println("Other results: " + resp.getCallStatus().name());
      return null;
    }
  }

  private List getReturnValue(List outputs, String expectedOutput) {

    if (outputs == null) {
      return null;
    }

    List returnValue = null;

    for (Object output1 : outputs) {
      ProcessOutput output = (ProcessOutput) output1;
      returnValue = processOutput(expectedOutput, returnValue, output);
    }


    return returnValue;
  }

  private List processOutput(String expectedOutput, List returnValue, ProcessOutput output) {

    if (output.getURI().equals(expectedOutput)) {
      returnValue = getReturnValue(returnValue, output);
      Log.info("returnValue found: ", getClass(), returnValue);
    } else {
      Log.info("output ignored: ", getClass(), output.getURI());
    }
    return returnValue;
  }

  private List getReturnValue(List returnValue, ProcessOutput output) {
    if (returnValue == null) {
      returnValue = (List) output.getParameterValue();
    } else {
      Log.info("Redundant return value!", getClass());
    }

    return returnValue;
  }

}
