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
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.Caregiver;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.profile.service.ProfilingService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;

public class UserManagerImpl implements UserManager {

  private static ServiceCaller caller = null;

  private static final String OUTPUT_GET_ALL_USERS = MedicationOntology.NAMESPACE + "out1";
  private static final String OUTPUT_GET_SUBPROFILES = MedicationOntology.NAMESPACE + "out2";
  private static final String OUTPUT_GET_SUBPROFILE = MedicationOntology.NAMESPACE + "out3";

  public UserManagerImpl(ModuleContext context) {
    caller = new DefaultServiceCaller(context);
  }

  public void loadDummyUsersIntoChe() {
    VCardPropertiesParser parser = new VCardPropertiesParser();

    insertDummyUser(parser, NEW_VCARD_BILL_PROPERTIES);
    insertDummyUser(parser, NEW_VCARD_NIK_PROPERTIES);
    insertDummyUser(parser, NEW_VCARD_NIKOLA_PROPERTIES);
    insertDummyUser(parser, NEW_VCARD_JOHN_PROPERTIES);
    insertDummyUser(parser, NEW_VCARD_MARIA_PROPERTIES);
    insertDummyUser(parser, NEW_VCARD_IREN_PROPERTIES);

  }

  private void insertDummyUser(VCardPropertiesParser parser, String propertyFileName) {
    PersonalInformationSubprofile subprofile = parser.createSubprofile(propertyFileName);
    Properties properties = parser.getProps();
    String userUri = properties.getProperty(USER_URI);
    if (userUri == null) {
      throw new MedicationManagerUserManagementException("The " + USER_URI + " property is not set " +
          "to the PersonalInformationSubprofile instance");
    }

    String type = properties.getProperty(TYPE);
    if (type == null) {
      throw new MedicationManagerUserManagementException("The " + TYPE + " property is not set " +
          "to the PersonalInformationSubprofile instance");
    }

    User user;
    if (ASSISTED_PERSON.equals(type)) {
      user = new AssistedPerson(userUri);
    } else if (CAREGIVER.equals(type)) {
      user = new Caregiver(userUri);
    } else {
      throw new MedicationManagerUserManagementException("Unknown type : " + type);
    }


    addUser(user);

    UserProfile profile = new UserProfile(userUri + "Prof");
    addProfile(user, profile);
    addUserSubprofile(user, subprofile);

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
      PersonalInformationSubprofile subprofile = getUserSubprofiles(ur);
      users.add(ur);
    }
  }

  public void addProfile(User profilable, UserProfile profile) {
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, profilable);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE}, profile);
    ServiceResponse resp = caller.call(req);
    CallStatus callStatus = resp.getCallStatus();

    Log.info("CallStatus: %s", getClass(), callStatus);

    if (!CallStatus.succeeded.equals(callStatus)) {
      throw new MedicationManagerUserManagementException("Unsuccessful add of a subprofile");
    }
  }

  public PersonalInformationSubprofile getUserSubprofiles(User user) {

    Log.info("Get all Subprofiles for user: %s", getClass(), user.getURI());

    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);

    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);

    req.addTypeFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, PersonalInformationSubprofile.MY_URI);

    req.addRequiredOutput(OUTPUT_GET_SUBPROFILES, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE});

    ServiceResponse resp = caller.call(req);


    if (!CallStatus.succeeded.equals(resp.getCallStatus())) {
      throw new MedicationManagerUserManagementException("Unsuccessful call status");
    }

    Object out = getReturnValue(resp.getOutputs(), OUTPUT_GET_SUBPROFILES);
    if (out != null) {
      PersonalInformationSubprofile userSubprofile = getUserSubprofile(out, user);

      Log.info("Found a PersonalInformationSubprofile : %s", getClass(), userSubprofile);

      return userSubprofile;
    } else {
      Log.info("Problem with the response outputs!", getClass());
      return null;
    }

  }

  private PersonalInformationSubprofile getUserSubprofile(Object out, User user) {
    List list = (List) out;

    PersonalInformationSubprofile subprofile = (PersonalInformationSubprofile) list.get(0);

    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    String uri = subprofile.getURI();
    System.out.println("uri = " + uri);
    SubProfile subProfile = new SubProfile(uri);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, subprofile);
    req.addRequiredOutput(OUTPUT_GET_SUBPROFILE, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE});
    ServiceResponse resp = caller.call(req);

    if (resp.getCallStatus() == CallStatus.succeeded) {

      PersonalInformationSubprofile informationSubprofile = processOutput(OUTPUT_GET_SUBPROFILE, resp.getOutputs());
      if (informationSubprofile != null) {

        System.out.println("informationSubprofile = " + informationSubprofile);

        return informationSubprofile;

      } else {
        System.out.println("PersonalInformationSubprofile is null !");
      }
    } else {
      System.out.println("Other CallStatus results: " + resp.getCallStatus().name());
    }

    throw new MedicationManagerUserManagementException("Unsuccessful response");

  }

  private PersonalInformationSubprofile processOutput(String expectedOutput, List outputs) {

    for (Object o : outputs) {
      ProcessOutput output = (ProcessOutput) o;
      if (output.getURI().equals(expectedOutput)) {
        PersonalInformationSubprofile subprofile = (PersonalInformationSubprofile) output.getParameterValue();
        Log.info("PersonalInformationSubprofile found: %s", getClass(), subprofile);
        return subprofile;
      } else {
        Log.info("output ignored: %s", getClass(), output.getURI());
      }
    }


    return null;
  }

  private void addUserSubprofile(User user, SubProfile subProfile) {
    Log.info("Add subProfile for user: %s and subprofile: %s", getClass(), user.getURI(), subProfile.toString());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
    req.addAddEffect(new String[]{
        ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, subProfile);

    ServiceResponse resp = caller.call(req);
    CallStatus callStatus = resp.getCallStatus();
    Log.info("CallStatus: %s", getClass(), callStatus);

    if (!CallStatus.succeeded.equals(callStatus)) {
      throw new MedicationManagerUserManagementException("Unsuccessful add of a subprofile");
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
