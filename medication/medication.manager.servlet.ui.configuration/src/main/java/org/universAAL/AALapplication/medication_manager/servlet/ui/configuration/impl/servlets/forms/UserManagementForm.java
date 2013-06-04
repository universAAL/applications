package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.user.management.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.user.management.CaregiverUserInfo;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.AALapplication.medication_manager.user.management.UserInfo;

import java.util.ArrayList;
import java.util.List;

import static org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;

/**
 * @author George Fournadjiev
 */
public final class UserManagementForm extends ScriptForm {

  private final List<Person> patients;
  private final List<Person> physicians;
  private final List<Person> caregivers;
  private final PersistentService persistentService;
  private final UserManager userManager;

  public UserManagementForm(PersistentService persistentService, UserManager userManager) {
    super();


    this.persistentService = persistentService;
    this.userManager = userManager;

    PersonDao personDao = persistentService.getPersonDao();
    List<Person> persons = personDao.getAllPersonsWithoutAdmins();

    patients = getPersonInGivenRole(persons, PATIENT);
    physicians = getPersonInGivenRole(persons, PHYSICIAN);
    caregivers = getPersonInGivenRole(persons, CAREGIVER);

    printUsers();

  }

  private List<Person> getPersonInGivenRole(List<Person> persons, Role role) {
    List<Person> personList = new ArrayList<Person>();

    for (Person person : persons) {
      if (person.getRole().equals(role)) {
        personList.add(person);
      }
    }

    return personList;
  }

  private void printUsers() {
    List<UserInfo> users = userManager.getAllUsers();

    for (UserInfo user : users) {
      System.out.println("\n******** user *****************");
      String uri = user.getUri();
      System.out.println("user.getURI() = " + uri);
      if (user.getClass().equals(AssistedPersonUserInfo.class)) {
        System.out.println("The user is a AssistedPerson");
      } else if (user.getClass().equals(CaregiverUserInfo.class)) {
        System.out.println("The user is a Caregiver");
      }

      PersonDao personDao = persistentService.getPersonDao();
      Person person = personDao.getPersonByPersonUri(uri);
      boolean presentInDatabase = person != null;

      System.out.println("presentInDatabase = " + presentInDatabase);

      System.out.println("\n******** end *****************");

    }
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
    DispenserDao dispenserDao = persistentService.getDispenserDao();
    List<Dispenser> dispensers = dispenserDao.getAllDispensers();
    DispenserScriptArrayCreator creator = new DispenserScriptArrayCreator(dispensers);
    sb.append("\n\t");
    String javaScriptArrayText = creator.createJavaScriptArrayText();
    sb.append(javaScriptArrayText);
    sb.append("\n");

  }

  private void createUsersJavascriptArray(StringBuffer sb) {
    UsersJavaScriptArrayCreator creator =
        new UsersJavaScriptArrayCreator(persistentService, patients, physicians, caregivers);

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

    creator = new PersonScriptArrayCreator("physicians", physicians);
    String physiciansArrayText = creator.createJavaScriptArrayText();
    sb.append(physiciansArrayText);
    sb.append('\n');

    creator = new PersonScriptArrayCreator("caregivers", caregivers);
    String caregiversArrayText = creator.createJavaScriptArrayText();
    sb.append(caregiversArrayText);
    sb.append('\n');
  }

}
