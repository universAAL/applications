package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PatientLinksDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.PatientLinks;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class UsersJavaScriptArrayCreator {

  private final PersistentService persistentService;
  private final List<Person> patients;
  private final List<Person> physicians;
  private final List<Person> caregivers;

  public UsersJavaScriptArrayCreator(PersistentService persistentService,
                                     List<Person> patients, List<Person> physicians, List<Person> caregivers) {

    this.persistentService = persistentService;
    this.patients = patients;
    this.physicians = physicians;
    this.caregivers = caregivers;
  }

  public String createUsersArrayText() {
    StringBuffer sb = new StringBuffer();
    sb.append("users = [");
    sb.append("\n\t\t");
    int size = patients.size();
    for (int i = 0; i < size; i++) {
      Person patient = patients.get(i);
      createTableRowData(patient, sb, i, size);
      sb.append("\n\t\t");
    }

    sb.append("\n\t");
    sb.append("];");

    return sb.toString();

  }

  private void createTableRowData(Person patient, StringBuffer sb, int i, int size) {
    sb.append('{');

    addPatient(patient, sb);

    addPhysicianAndCaregiver(patient, sb);

    addDispenser(patient, sb);

    addAlerts(patient, sb);

    sb.append("\n\t\t");
    sb.append("}");
    if (i < size - 1) {
      sb.append(',');
    }

  }

  private void addAlerts(Person patient, StringBuffer sb) {
    sb.append(",\n\t\t\t\t");
    sb.append("alerts:{");



    sb.append("}");

//    "alerts":{"due":false, "missed":true, "successful":true, "upside":false}

  }

  private void addDispenser(Person patient, StringBuffer sb) {
    sb.append(",\n\t\t\t\t");
    sb.append("dispenser:");

    DispenserDao dispenserDao = persistentService.getDispenserDao();

    Dispenser dispenser = dispenserDao.getDispenserByPerson(patient);

    if (dispenser != null) {
      String dispenserId = getNumberQuoted(dispenser.getId());
      sb.append(dispenserId);
    } else {
      sb.append(getNumberQuoted(-1));
    }

  }

  private void addPatient(Person patient, StringBuffer sb) {
    sb.append("\n\t\t\t\t");
    sb.append("patient:");
    String patientId = getNumberQuoted(patient.getId());
    sb.append(patientId);
  }

  private void addPhysicianAndCaregiver(Person patient, StringBuffer sb) {
    sb.append(",\n\t\t\t\t");
    sb.append("physician:");

    PatientLinksDao patientLinksDao = persistentService.getPatientLinksDao();
    PatientLinks patientLinks = patientLinksDao.getPatientLinksForPatient(patient);

    String physicianId = getNumberQuoted(patientLinks.getDoctor().getId());
    sb.append(physicianId);

    sb.append(",\n\t\t\t\t");
    sb.append("caregiver:");

    String caregiverId = getNumberQuoted(patientLinks.getCaregiver().getId());
    sb.append(caregiverId);
  }

  private String getNumberQuoted(int number) {
    return "\"" + number + "\"";
  }
}
