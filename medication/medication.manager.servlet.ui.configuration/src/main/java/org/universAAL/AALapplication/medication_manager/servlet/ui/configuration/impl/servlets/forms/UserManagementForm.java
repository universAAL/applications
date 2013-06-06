package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

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

    fillUsers();

    checkForDatabasePresence();

    PatientLinksDao patientLinksDao = persistentService.getPatientLinksDao();
    List<PatientLinks> patientLinkses = patientLinksDao.getAllPatientLinks();

    setDataToAssistedPersonUserInfoPresentInDatabase(patientLinkses);

    DispenserDao dispenserDao = persistentService.getDispenserDao();
    List<Dispenser> allDispensers = dispenserDao.getAllDispensers();

    dispensers.addAll(allDispensers);

    session.setAttribute(PATIENTS, patients);
    session.setAttribute(CAREGIVERS, caregivers);
  }

  private void checkForDatabasePresence() {
    PersonDao personDao = persistentService.getPersonDao();
    List<Person> persons = personDao.getAllPersons();

    for (AssistedPersonUserInfo assistedPersonUserInfo : patients) {
      Person patient = getPersonFromDatabase(persons, assistedPersonUserInfo);
      if (patient != null) {
        assistedPersonUserInfo.setId(patient.getId());
        assistedPersonUserInfo.setPresentInDatabase(true);
        DispenserDao dispenserDao = persistentService.getDispenserDao();
        Dispenser dispenser = dispenserDao.getDispenserByPersonId(patient.getId());
        assistedPersonUserInfo.setDispenser(dispenser);
      }
    }

    for (CaregiverUserInfo caregiverUserInfo : caregivers) {
      Person caregiver = getPersonFromDatabase(persons, caregiverUserInfo);
      if (caregiver != null) {
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
      PatientLinks patientLinks = findPatientLinks(assistedPersonUserInfo, patientLinkses);
      if (patientLinks != null) {
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

    if (isCached()) {
      return;
    }

    List<UserInfo> users = userManager.getAllUsers();

    for (UserInfo user : users) {
      System.out.println("\n******** user *****************");
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

      System.out.println("\n******** end *****************");

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
    sb.append(patientArrayText);
    sb.append('\n');

    creator = new PersonScriptArrayCreator("physicians", caregivers);
    String physiciansArrayText = creator.createJavaScriptArrayText();
    sb.append(physiciansArrayText);
    sb.append('\n');

    creator = new PersonScriptArrayCreator("caregivers", caregivers);
    String caregiversArrayText = creator.createJavaScriptArrayText();
    sb.append(caregiversArrayText);
    sb.append('\n');
  }

}
