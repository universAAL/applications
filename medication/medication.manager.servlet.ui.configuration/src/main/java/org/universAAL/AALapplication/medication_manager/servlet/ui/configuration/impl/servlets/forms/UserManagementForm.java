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

package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.configuration.MedicationManagerConfigurationException;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PatientLinksDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.PatientLinks;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.layer.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.CaregiverUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.UserInfo;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.ontology.profile.User;

import java.util.ArrayList;
import java.util.List;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class UserManagementForm extends ScriptForm {

  public static final String NULL = "null";
  private final List<AssistedPersonUserInfo> patients;
  private final List<CaregiverUserInfo> caregivers;
  private final List<Dispenser> dispensers;
  private final PersistentService persistentService;
  private final UserManager userManager;
  private final Session session;

  public UserManagementForm(PersistentService persistentService, UserManager userManager, Session session) {
    super();


    this.persistentService = persistentService;
    this.userManager = userManager;
    this.session = session;

    patients = new ArrayList<AssistedPersonUserInfo>();
    caregivers = new ArrayList<CaregiverUserInfo>();
    dispensers = new ArrayList<Dispenser>();
  }

  public void prepareData() {

    Log.info("Prepare data required by the UserManagementForm", getClass());

    fillUsers();

    if (patients.isEmpty()) {
      throw new MedicationManagerConfigurationException("Missing patients!");
    }

    checkForDatabasePresence();

    PatientLinksDao patientLinksDao = persistentService.getPatientLinksDao();
    List<PatientLinks> patientLinkses = patientLinksDao.getAllPatientLinks();

    setDataToAssistedPersonUserInfoPresentInDatabase(patientLinkses);

    DispenserDao dispenserDao = persistentService.getDispenserDao();
    List<Dispenser> allDispensers = dispenserDao.getAllDispensers();

    Log.info("Found dispensers (size): ", getClass(), allDispensers.size());

    dispensers.addAll(allDispensers);

    session.setAttribute(PATIENTS, patients);
    session.setAttribute(CAREGIVERS, caregivers);
  }

  private void checkForDatabasePresence() {

    Log.info("Check users for database presence!", getClass());

    PersonDao personDao = persistentService.getPersonDao();
    List<Person> persons = personDao.getAllPersons();

    for (AssistedPersonUserInfo assistedPersonUserInfo : patients) {
      Log.info("Check assistedPersonUserInfo: %s for database presence!", getClass(), assistedPersonUserInfo.getUri());
      Person patient = getPersonFromDatabase(persons, assistedPersonUserInfo);
      if (patient != null) {
        Log.info("The assistedPersonUserInfo has database records", getClass());
        assistedPersonUserInfo.setId(patient.getId());
        assistedPersonUserInfo.setPresentInDatabase(true);
        DispenserDao dispenserDao = persistentService.getDispenserDao();
        Dispenser dispenser = dispenserDao.getDispenserByPersonId(patient.getId());
        assistedPersonUserInfo.setDispenser(dispenser);
      }
    }

    for (CaregiverUserInfo caregiverUserInfo : caregivers) {
      Log.info("Check caregiverUserInfo: %s for database presence!", getClass(), caregiverUserInfo.getUri());
      Person caregiver = getPersonFromDatabase(persons, caregiverUserInfo);
      if (caregiver != null) {
        Log.info("The caregiverUserInfo has database records", getClass());
        caregiverUserInfo.setId(caregiver.getId());
        caregiverUserInfo.setPresentInDatabase(true);
      }
    }
  }

  private Person getPersonFromDatabase(List<Person> databasePatients, UserInfo assistedPersonUserInfo) {

    for (Person patient : databasePatients) {
      if (patient.getPersonUri().equals(assistedPersonUserInfo.getUri())) {
        return patient;
      }
    }

    return null;
  }

  private void setDataToAssistedPersonUserInfoPresentInDatabase(List<PatientLinks> patientLinkses) {

    for (AssistedPersonUserInfo assistedPersonUserInfo : patients) {
      Log.info("Check assistedPersonUserInfo: %s for PatientLinks!", getClass(), assistedPersonUserInfo.getUri());
      PatientLinks patientLinks = findPatientLinks(assistedPersonUserInfo, patientLinkses);
      if (patientLinks != null) {
        Log.info("assistedPersonUserInfo: %s has PatientLinks!", getClass(), assistedPersonUserInfo.getUri());
        setData(patientLinks, assistedPersonUserInfo);
      }
    }

  }

  private void setData(PatientLinks patientLinks, AssistedPersonUserInfo assistedPersonUserInfo) {

    Person caregiver = patientLinks.getCaregiver();

    CaregiverUserInfo caregiverUserInfo = null;

    for (CaregiverUserInfo userInfo : caregivers) {
      if (userInfo.getUri().equals(caregiver.getPersonUri())) {
        caregiverUserInfo = userInfo;
      }
    }

    assistedPersonUserInfo.setCaregiverUserInfo(caregiverUserInfo);

    Person doctor = patientLinks.getDoctor();

    CaregiverUserInfo doctorUserInfo = null;
    for (CaregiverUserInfo userInfo : caregivers) {
      if (userInfo.getUri().equals(doctor.getPersonUri())) {
        doctorUserInfo = userInfo;
        doctorUserInfo.setDoctor(true);
      }
    }

    assistedPersonUserInfo.setDoctor(doctorUserInfo);

  }

  private PatientLinks findPatientLinks(AssistedPersonUserInfo assistedPersonUserInfo, List<PatientLinks> patientLinkses) {

    for (PatientLinks patientLinks : patientLinkses) {
      Person patient = patientLinks.getPatient();
      if (assistedPersonUserInfo.getUri().equals(patient.getPersonUri())) {
        return patientLinks;
      }
    }

    return null;
  }

  private void fillUsers() {

    Log.info("Trying to fill UserInfo Lists fields : patient and caregivers", getClass());

    Log.info("Checking if the users are cached", getClass());
    if (isCached()) {
      Log.info("Using cached users", getClass());
      return;
    }

    Log.info("Trying to get UserInfo objects from the UserManager", getClass());

    List<UserInfo> users = userManager.getAllUsers();

    if (users == null || users.isEmpty()) {
      Log.info("The UserManager returned null or empty list with users", getClass());
    }

    Log.info("Trying to get UserInfo objects from the UserManager", getClass());

    for (UserInfo user : users) {
      String uri = user.getUri();
      Log.info("user.getURI() = %s | user.getName() = %s", getClass(), uri, user.getName());
      if (user.getClass().equals(AssistedPersonUserInfo.class) || user.getClass().equals(User.class)) {
        Log.info("The user is a AssistedPerson", getClass());
        AssistedPersonUserInfo assistedPersonUserInfo = (AssistedPersonUserInfo) user;
        patients.add(assistedPersonUserInfo);
      } else if (user.getClass().equals(CaregiverUserInfo.class)) {
        Log.info("The user is a Caregiver", getClass());
        CaregiverUserInfo caregiverUserInfo = (CaregiverUserInfo) user;
        caregivers.add(caregiverUserInfo);
      }

    }
  }

  private boolean isCached() {
    boolean isCached = false;
    List<AssistedPersonUserInfo> allPatients = (List<AssistedPersonUserInfo>) session.getAttribute(PATIENTS);
    if (allPatients != null && !allPatients.isEmpty()) {
      isCached = true;
      List<CaregiverUserInfo> allCaregivers = (List<CaregiverUserInfo>) session.getAttribute(CAREGIVERS);
      patients.addAll(allPatients);
      caregivers.addAll(allCaregivers);
    }

    return isCached;
  }

  @Override
  public void setSingleJavascriptObjects() {
    // nothing to do here
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void process() {
    //nothing to do here
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public String createScriptText() {
    StringBuffer sb = new StringBuffer();

    sb.append('\n');

    sb.append(SCRIPT_START);

    createPersonArrays(sb);

    createDispenserArray(sb);

    createUsersJavascriptArray(sb);

    sb.append(SCRIPT_END);


    return sb.toString();
  }

  private void createDispenserArray(StringBuffer sb) {
    DispenserScriptArrayCreator creator = new DispenserScriptArrayCreator(dispensers);
    sb.append("\n\t");
    String javaScriptArrayText = creator.createJavaScriptArrayText();
    sb.append(javaScriptArrayText);
    sb.append("\n");

  }

  private void createUsersJavascriptArray(StringBuffer sb) {
    UsersJavaScriptArrayCreator creator =
        new UsersJavaScriptArrayCreator(persistentService, patients, caregivers);

    String usersArrayText = creator.createUsersArrayText();

    sb.append("\n\t");
    sb.append(usersArrayText);
    sb.append("\n");
  }

  private void createPersonArrays(StringBuffer sb) {
    PersonScriptArrayCreator creator = new PersonScriptArrayCreator("patients", patients);
    String patientArrayText = creator.createJavaScriptArrayText();
    if (!NULL.equalsIgnoreCase(patientArrayText)) {
      sb.append(patientArrayText);
    }
    sb.append('\n');

    creator = new PersonScriptArrayCreator("physicians", caregivers);
    String physiciansArrayText = creator.createJavaScriptArrayText();
    if (!NULL.equalsIgnoreCase(physiciansArrayText)) {
      sb.append(physiciansArrayText);
    }
    sb.append('\n');

    creator = new PersonScriptArrayCreator("caregivers", caregivers);
    String caregiversArrayText = creator.createJavaScriptArrayText();
    if (!NULL.equalsIgnoreCase(caregiversArrayText)) {
      sb.append(caregiversArrayText);
    }
    sb.append('\n');
  }

}
