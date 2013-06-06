package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.util;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.MedicationManagerServletUIConfigurationException;
import org.universAAL.AALapplication.medication_manager.user.management.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.user.management.CaregiverUserInfo;

import javax.servlet.ServletRequest;
import java.util.List;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class UserManagementParameterHandler {

  private final ServletRequest request;
  private final Session session;
  private final int patientId;
  private final int caregiverId;
  private final int physicianId;
  private final int dispenserId;
  private final boolean dueIntakeAlert;
  private final boolean successfulIntakeAlert;
  private final boolean missedIntakeAlert;
  private final boolean upsideDownAlert;

  private final static String PATIENT = "patient";
  private final static String CAREGIVER = "caregiver";
  private final static String PHYSICIAN = "physician";
  private final static String DISPENSER = "dispenser";
  private final static String DUE = "due";
  private final static String UPSIDE = "upside";
  private final static String SUCCESSFUL = "successful";
  private final static String MISSED = "missed";
  private static final String ON = "on";

  public UserManagementParameterHandler(ServletRequest request, Session session) {
    this.request = request;
    this.session = session;

    patientId = getIntFromParameter(PATIENT);
    caregiverId = getIntFromParameter(CAREGIVER);
    physicianId = getIntFromParameter(PHYSICIAN);
    dispenserId = getIntFromParameter(DISPENSER);
    dueIntakeAlert = getBooleanFromParameter(DUE);
    successfulIntakeAlert = getBooleanFromParameter(SUCCESSFUL);
    missedIntakeAlert = getBooleanFromParameter(MISSED);
    upsideDownAlert = getBooleanFromParameter(UPSIDE);
  }

  private int getIntFromParameter(String parameterName) {
    Log.info("Getting parameter: " + parameterName, getClass());
    String paramValue = request.getParameter(parameterName);
    if (paramValue == null) {
      throw new MedicationManagerServletUIConfigurationException("Missing parameter: " + parameterName);
    }

    try {
      return Integer.parseInt(paramValue);
    } catch (NumberFormatException e) {
      throw new MedicationManagerServletUIConfigurationException("the parameter value is not a number");
    }
  }

  private boolean getBooleanFromParameter(String parameterName) {
    Log.info("Getting parameter: " + parameterName, getClass());
    String paramValue = request.getParameter(parameterName);
    if (paramValue == null) {
      return false;
    }

    return ON.equalsIgnoreCase(paramValue);

  }


  @Override
  public String toString() {
    return "UserManagementParameterHandler{" +
        "patientId=" + patientId +
        ", caregiverId=" + caregiverId +
        ", physicianId=" + physicianId +
        ", dispenserId=" + dispenserId +
        ", dueIntakeAlert=" + dueIntakeAlert +
        ", successfulIntakeAlert=" + successfulIntakeAlert +
        ", missedIntakeAlert=" + missedIntakeAlert +
        ", upsideDownAlert=" + upsideDownAlert +
        '}';
  }

  public void saveData() {
    List<AssistedPersonUserInfo> allPatients = (List<AssistedPersonUserInfo>) session.getAttribute(PATIENTS);
    if (allPatients == null || allPatients.isEmpty()) {
      throw new MedicationManagerServletUIConfigurationException("The patients attribute is not set correctly");
    }

    AssistedPersonUserInfo patient = findPatient(allPatients);

    List<CaregiverUserInfo> allCaregivers = (List<CaregiverUserInfo>) session.getAttribute(CAREGIVERS);
    if (allCaregivers == null || allCaregivers.isEmpty()) {
      throw new MedicationManagerServletUIConfigurationException("The caregivers attribute is not set correctly");
    }

    CaregiverUserInfo doctor = findDoctor(allCaregivers);

    CaregiverUserInfo caregiver = findCaregiver(allCaregivers);

    patient.setDoctor(doctor);
    patient.setCaregiverUserInfo(caregiver);

    PersistentService persistentService = getPersistentService();

    DispenserDao dispenserDao = persistentService.getDispenserDao();
    Dispenser dispenser = dispenserDao.getById(dispenserId);

    patient.setDispenser(dispenser);
  }

  private CaregiverUserInfo findCaregiver(List<CaregiverUserInfo> allCaregivers) {

    for (CaregiverUserInfo caregiverUserInfo : allCaregivers) {
      if (caregiverId == caregiverUserInfo.getId()) {
        return caregiverUserInfo;
      }
    }

    throw new MedicationManagerServletUIConfigurationException("The parameter caregiverId: " + caregiverId + " is incorrect");
  }

  private CaregiverUserInfo findDoctor(List<CaregiverUserInfo> allCaregivers) {

    for (CaregiverUserInfo caregiverUserInfo : allCaregivers) {
      if (physicianId == caregiverUserInfo.getId()) {
        caregiverUserInfo.setDoctor(true);
        return caregiverUserInfo;
      }
    }

    throw new MedicationManagerServletUIConfigurationException("The parameter physicianId: " + physicianId + " is incorrect");
  }

  private AssistedPersonUserInfo findPatient(List<AssistedPersonUserInfo> allPatients) {

    for (AssistedPersonUserInfo assistedPersonUserInfo : allPatients) {
      if (patientId == assistedPersonUserInfo.getId()) {
        return assistedPersonUserInfo;
      }
    }

    throw new MedicationManagerServletUIConfigurationException("The parameter patientId: " + patientId + " is incorrect");
  }
}
