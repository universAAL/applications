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

package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.CaregiverUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.UserInfo;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.VCardPropertiesParser;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.medMgr.MedicationOntology;
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
import static org.universAAL.ontology.profile.PersonalInformationSubprofile.*;

public class UserManagerImpl implements UserManager {

  private static ServiceCaller caller = null;

  private static final String OUTPUT_GET_ALL_USERS = MedicationOntology.NAMESPACE + "out1";
  private static final String OUTPUT_GET_SUBPROFILES = MedicationOntology.NAMESPACE + "out2";
  private static final String OUTPUT_GET_SUBPROFILE = MedicationOntology.NAMESPACE + "out3";
  private final PersistentService persistentService;

  public UserManagerImpl(ModuleContext context, PersistentService persistentService) {

    this.persistentService = persistentService;
    caller = new DefaultServiceCaller(context);
  }

  public void loadDummyUsersIntoChe() {
    VCardPropertiesParser parser = new VCardPropertiesParser();

    for (String propertyFileName : DUMMY_USERS_PROPERTIES) {
      insertDummyUser(parser, propertyFileName);
    }

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

  public List<UserInfo> getAllUsers() {
    Log.info("getAllUsers() from CHE", getClass());

    List<UserInfo> users = new ArrayList<UserInfo>();

    Log.info("Calling ProfilingService in order to get Users objects from the CHE", getClass());

    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);

    req.addRequiredOutput(OUTPUT_GET_ALL_USERS, new String[]{ProfilingService.PROP_CONTROLS});

    ServiceResponse resp = caller.call(req);

    CallStatus callStatus = resp.getCallStatus();

    Log.info("The response status is: %s", getClass(), callStatus);

    if (callStatus.equals(CallStatus.succeeded)) {

      handleSuccessfulResponse(users, resp);

    } else {
      Log.info("CallStatus: %s", getClass(), callStatus.name());
    }

    return users;
  }

  private void handleSuccessfulResponse(List<UserInfo> users, ServiceResponse resp) {
    Log.info("Trying to get users from the ServiceResponse object", getClass());
    List out = getReturnValue(resp.getOutputs(), OUTPUT_GET_ALL_USERS);
    Log.info("Received List with users object (out) : %s", getClass(), out);

    for (int i = 0; i < out.size(); i++) {
      UserInfo ur = getUserInfo(out, i);
      if (ur != null) {
        users.add(ur);
      }
    }
  }

  private UserInfo getUserInfo(List out, int i) {
    Log.info("Creating UserInfo object from The User object", getClass());
    Object o = out.get(i);
    System.out.println("o.getClass().getName() = " + o.getClass().getName());
    System.out.println("o.getClass().getClassLoader() = " + o.getClass().getClassLoader());
    System.out.println("User.class.getClassLoader() = " + User.class.getClassLoader());
    User ur = (User) o;
    String uri = ur.getURI();
    Log.info("User object uri is : %s", getClass(), uri);

    if ("urn:org.universAAL.aal_space:test_environment#saied".equalsIgnoreCase(uri)) {
      return null;
    }

    PersonalInformationSubprofile subprofile = getUserSubprofiles(ur);
    int id = persistentService.generateId();
    String name = (String) subprofile.getProperty(PROP_FN);

    Class<? extends User> aClass = ur.getClass();
    if (AssistedPerson.class.equals(aClass)) {
      return new AssistedPersonUserInfo(id, uri, name);
    } else if (Caregiver.class.equals(aClass)) {
      String username = (String) subprofile.getProperty(PROP_NICKNAME); //TODO to be fixed to use real username
      String gmsNumber = (String) subprofile.getProperty(PROP_UCI_ADDITIONAL_DATA); //TODO to use tel property when fixed by Alvaro
      return new CaregiverUserInfo(id, uri, name, username, gmsNumber);
    } else {
      throw new MedicationManagerUserManagementException("Unknown User subclass: " + aClass);
    }


  }

  public void addProfile(User profilable, UserProfile profile) {
    Log.info("Sending UserProfile to the CHE for the user: %s", getClass(), profilable.getURI());
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

    Log.info("Trying to get PersonalInformationSubprofile for user: %s", getClass(), user.getURI());

    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);

    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);

    req.addTypeFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, PersonalInformationSubprofile.MY_URI);

    req.addRequiredOutput(OUTPUT_GET_SUBPROFILES, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE});

    ServiceResponse resp = caller.call(req);

    CallStatus callStatus = CallStatus.succeeded;

    if (!callStatus.equals(resp.getCallStatus())) {
      throw new MedicationManagerUserManagementException("Unsuccessful call status");
    }

    List out = getReturnValue(resp.getOutputs(), OUTPUT_GET_SUBPROFILES);
    if (!out.isEmpty()) {
      Log.info("Found output with PersonalInformationSubprofile URI", getClass());
      PersonalInformationSubprofile userSubprofile = getUserSubprofile(out, user);

      Log.info("Found a PersonalInformationSubprofile : %s", getClass(), userSubprofile);

      return userSubprofile;
    }

    throw new MedicationManagerUserManagementException("Problem with the response outputs." +
        " Cannot get the PersonalInformationSubprofile object for the user : " + user.getURI());

  }

  private PersonalInformationSubprofile getUserSubprofile(List list, User user) {
    Log.info("Trying to get PersonalInformationSubprofile URI from the list", getClass());

    PersonalInformationSubprofile subprofile = (PersonalInformationSubprofile) list.get(0);

    Log.info("Found PersonalInformationSubprofile object, which has only URI of our PersonalInformationSubprofile object." +
        "So we need to ask CHE to give real PersonalInformationSubprofile object. Making ServiceRequest...", getClass());

    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    String uri = subprofile.getURI();
    SubProfile subProfile = new SubProfile(uri);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, subprofile);
    req.addRequiredOutput(OUTPUT_GET_SUBPROFILE, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE});
    ServiceResponse resp = caller.call(req);

    CallStatus callStatus = resp.getCallStatus();
    Log.info("ServiceResponse status is : %s", getClass(), callStatus);

    if (callStatus == CallStatus.succeeded) {

      PersonalInformationSubprofile informationSubprofile = processOutput(OUTPUT_GET_SUBPROFILE, resp.getOutputs());
      if (informationSubprofile != null) {

        Log.info("Found informationSubprofile = %s", getClass(), informationSubprofile);

        return informationSubprofile;

      } else {
        Log.info("PersonalInformationSubprofile is null !", getClass());
      }
    } else {
      Log.info("Other CallStatus results: %s", getClass(), callStatus.name());
    }

    throw new MedicationManagerUserManagementException("Unsuccessful response");

  }

  private PersonalInformationSubprofile processOutput(String expectedOutput, List outputs) {

    Log.info("ProcessOutput method is called", getClass());

    if (outputs == null) {
      throw new MedicationManagerUserManagementException("The output List parameter is null");
    }

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
    Log.info("Add subProfile into the CHE for user: %s and subprofile: %s", getClass(), user.getURI(), subProfile.toString());
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
    Log.info("Getting outputs size for the following expectedOutput: %s", getClass(), expectedOutput);
    if (outputs == null) {
      Log.info("Outputs are null. Returning empty List", getClass());
      return Collections.emptyList();
    }

    Log.info("Outputs size is : %s", getClass(), outputs.size());
    List returnValue = null;

    for (Object output1 : outputs) {
      ProcessOutput output = (ProcessOutput) output1;
      Log.info("Getting ProcessOutput: %s", getClass(), output);
      returnValue = processOutput(expectedOutput, returnValue, output);
    }

    Log.info("returnValue is: %s", getClass(), returnValue);

    if (returnValue == null) {
      returnValue = new ArrayList();
    }

    return returnValue;
  }

  private List processOutput(String expectedOutput, List returnValue, ProcessOutput output) {

    if (output.getURI().equals(expectedOutput)) {
      returnValue = getReturnValue(returnValue, output);
      Log.info("returnValue found equals expectedOutput: %s", getClass(), returnValue);
    } else {
      Log.info("output ignored (not equal to the expectedOutput): %s", getClass(), output.getURI());
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
    Log.info("Add user with URI to the CHE :%s", getClass(), user.getURI());
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
