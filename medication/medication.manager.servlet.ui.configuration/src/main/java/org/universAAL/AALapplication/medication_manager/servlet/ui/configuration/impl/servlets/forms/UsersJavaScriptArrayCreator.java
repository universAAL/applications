package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.user.management.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.user.management.CaregiverUserInfo;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class UsersJavaScriptArrayCreator {

  private final PersistentService persistentService;
  private final List<AssistedPersonUserInfo> patients;
  private final List<CaregiverUserInfo> caregivers;

  public UsersJavaScriptArrayCreator(PersistentService persistentService, List<AssistedPersonUserInfo> patients,
                                     List<CaregiverUserInfo> caregivers) {

    this.persistentService = persistentService;
    this.patients = patients;
    this.caregivers = caregivers;
  }

  public String createUsersArrayText() {
    StringBuffer sb = new StringBuffer();
    sb.append("users = [");
    sb.append("\n\t\t");
    int size = patients.size();
    for (int i = 0; i < size; i++) {
      AssistedPersonUserInfo patient = patients.get(i);
      createTableRowData(patient, sb, i, size);
      sb.append("\n\t\t");
    }

    sb.append("\n\t");
    sb.append("];");

    return sb.toString();

  }

  private void createTableRowData(AssistedPersonUserInfo patient, StringBuffer sb, int i, int size) {
    sb.append('{');

    addPatient(patient, sb);

    addPhysicianAndCaregiver(patient, sb);

    if (patient.isPresentInDatabase()) {
      addDispenser(patient, sb);
    }

    addAlerts(patient, sb);

    sb.append("\n\t\t");
    sb.append("}");
    if (i < size - 1) {
      sb.append(',');
    }

  }

  private void addAlerts(AssistedPersonUserInfo patient, StringBuffer sb) {
    sb.append(",\n\t\t\t\t");
    sb.append("alerts:{");



    sb.append("}");

//    "alerts":{"due":false, "missed":true, "successful":true, "upside":false}

  }

  private void addDispenser(AssistedPersonUserInfo patient, StringBuffer sb) {
    sb.append(",\n\t\t\t\t");
    sb.append("dispenser:");

    DispenserDao dispenserDao = persistentService.getDispenserDao();

    Dispenser dispenser = dispenserDao.getDispenserByPersonId(patient.getId());

    if (dispenser != null) {
      String dispenserId = getNumberQuoted(dispenser.getId());
      sb.append(dispenserId);
    } else {
      sb.append(getNumberQuoted(-1));
    }

  }

  private void addPatient(AssistedPersonUserInfo patient, StringBuffer sb) {
    sb.append("\n\t\t\t\t");
    sb.append("patient:");
    String patientId = getNumberQuoted(patient.getId());
    sb.append(patientId);
  }

  private void addPhysicianAndCaregiver(AssistedPersonUserInfo patient, StringBuffer sb) {

    CaregiverUserInfo doctor = patient.getDoctor();

    if (doctor != null) {
      sb.append(",\n\t\t\t\t");
      sb.append("physician:");

      String physicianId = getNumberQuoted(doctor.getId());
      sb.append(physicianId);
    }

    CaregiverUserInfo caregiverUserInfo = patient.getCaregiverUserInfo();

    if (caregiverUserInfo != null) {
      sb.append(",\n\t\t\t\t");
      sb.append("caregiver:");

      String caregiverId = getNumberQuoted(caregiverUserInfo.getId());
      sb.append(caregiverId);
    }
  }

  private String getNumberQuoted(int number) {
    return "\"" + number + "\"";
  }
}
