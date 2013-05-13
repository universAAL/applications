package org.universAAL.AALapplication.medication_manager.persistence.layer;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PatientLinksDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.middleware.service.ServiceResponse;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class Util {

  private static final String CAREGIVER_NOTIFIER_NAMESPACE =
      "http://ontology.igd.fhg.de/CaregiverNotifier.owl#";
  public static final String OUTPUT_CAREGIVER_RECEIVED_MESSAGE =
          CAREGIVER_NOTIFIER_NAMESPACE + "receivedMessage";

  private Util() {
  }

  public static String getCaregiverSms(Person person, PatientLinksDao patientLinksDao) {
    Person caregiver = patientLinksDao.findPatientCaregiver(person);
    String caregiverSms = caregiver.getCaregiverSms();
    if (caregiverSms == null) {
      throw new MedicationManagerPersistenceException("Missing caregiver sms for a caregiver: " + caregiver);
    }
    return caregiverSms;
  }

  public static String getMessage(ServiceResponse serviceResponse) {
    StringBuffer sb = new StringBuffer();
    sb.append("The Medication Manager service successfully notified the Caregiver Notification Service");
    sb.append("\n\t. Received the following message: ");

    List list = serviceResponse.getOutput(OUTPUT_CAREGIVER_RECEIVED_MESSAGE, true);

    if (list == null || list.size() != 1) {
      throw new MedicationManagerPersistenceException("Missing correct output in ServiceResponse object");
    }

    String msg = (String) list.get(0);

    sb.append(msg);

    return sb.toString();
  }

}
