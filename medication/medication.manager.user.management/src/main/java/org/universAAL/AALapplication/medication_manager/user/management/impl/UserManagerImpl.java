package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.VCardPropertiesParser;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.medication.MedicationOntology;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.service.ProfilingService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;
import static org.universAAL.ontology.profile.PersonalInformationSubprofile.*;

public class UserManagerImpl implements UserManager {

  private static ServiceCaller caller = null;

  private static final String OUTPUT_GET_ALL_USERS = MedicationOntology.NAMESPACE + "out";

  public UserManagerImpl(ModuleContext context) {
    caller = new DefaultServiceCaller(context);
  }

  public void loadDummyUsersIntoChe() {
    VCardPropertiesParser parser = new VCardPropertiesParser();

    insertDummyUser(parser, NEW_VCARD_BILL_PROPERTIES);
    insertDummyUser(parser, NEW_VCARD_GEORGE_PROPERTIES);
    insertDummyUser(parser, NEW_VCARD_NIKOLA_PROPERTIES);
  }

  private void insertDummyUser(VCardPropertiesParser parser, String propertyFileName) {
    PersonalInformationSubprofile subprofile = parser.createSubprofile(propertyFileName);

    String userUri = (String) subprofile.getProperty(PROP_URL);
    if (userUri == null) {
      throw new MedicationManagerUserManagementException("The " + PROP_URL + " property is not set " +
          "to the PersonalInformationSubprofile instance");
    }

    User user = new User(userUri);

    System.out.println("user = " + user.getURI());
  }

  public List<User> getAllUsers() {
    Log.info("getAllUsers() from CHE", getClass());

    List<User> users = new ArrayList<User>();

    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);

    req.addRequiredOutput(OUTPUT_GET_ALL_USERS, new String[]{ProfilingService.PROP_CONTROLS});

    ServiceResponse resp = caller.call(req);

    if (resp.getCallStatus().equals(CallStatus.succeeded)) {

      handleSuccessfulResponse(users, resp);

    } else {
      Log.info("CallStatus: %s", getClass(), resp.getCallStatus().name());
    }

    return users;
  }

  private void handleSuccessfulResponse(List<User> users, ServiceResponse resp) {
    List out = getReturnValue(resp.getOutputs(), OUTPUT_GET_ALL_USERS);
    for (int i = 0; i < out.size(); i++) {
      User ur = (User) out.get(i);
      users.add(ur);
    }
  }

  private List getReturnValue(List outputs, String expectedOutput) {

    if (outputs == null) {
      return Collections.emptyList();
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
      Log.info("returnValue found: %s", getClass(), returnValue);
    } else {
      Log.info("output ignored: %s", getClass(), output.getURI());
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

  private void addUser(User user) {
    Log.info("Add user with URI:%s", getClass(), user.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS}, user);
    ServiceResponse resp = caller.call(req);
    CallStatus callStatus = resp.getCallStatus();
    Log.info("CallStatus: %s", getClass(), callStatus.name());

    if (!CallStatus.succeeded.equals(callStatus)) {
      throw new MedicationManagerUserManagementException("Unable to add a user: " + user.getURI() + " to the CHE");
    }

  }

}
