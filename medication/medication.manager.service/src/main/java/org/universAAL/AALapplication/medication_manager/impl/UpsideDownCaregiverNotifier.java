package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PatientLinksDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.medMgr.CaregiverNotifier;
import org.universAAL.ontology.medMgr.CaregiverNotifierData;
import org.universAAL.ontology.profile.User;

import static org.universAAL.AALapplication.medication_manager.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public final class UpsideDownCaregiverNotifier {

  private final ServiceCaller serviceCaller;

  public UpsideDownCaregiverNotifier() {
    serviceCaller = new DefaultServiceCaller(mc);
  }

  public void notifyCaregiverForUpsiseDown(Person patient, PatientLinksDao patientLinksDao) {

    User involvedHumanUser = new User(patient.getPersonUri());
    ServiceRequest serviceRequest = new ServiceRequest(new CaregiverNotifier(), involvedHumanUser);

    CaregiverNotifierData caregiverNotifierData = new CaregiverNotifierData();
    String smsNumber = getCaregiverSms(patient, patientLinksDao);
    caregiverNotifierData.setSmsNumber(smsNumber);
    String smsText = getUpsideDownAlertMessage(patient);
    caregiverNotifierData.setSmsText(smsText);


    serviceRequest.addAddEffect(new String[]{CaregiverNotifier.PROP_CAREGIVER_NOTIFIER_DATA}, caregiverNotifierData);
    serviceRequest.addRequiredOutput(OUTPUT_CAREGIVER_RECEIVED_MESSAGE,
        new String[]{CaregiverNotifier.PROP_RECEIVED_MESSAGE});

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();

    String msg;
    if (callStatus.toString().contains("call_succeeded")) {
      msg = getMessage(serviceResponse);
    } else {
      msg = "The Medication Manager service was unable notified the Caregiver Notification Service";
    }
    Log.info("Caregiver Notification callStatus %s\n" + msg, getClass(), callStatus);
  }

  private String getUpsideDownAlertMessage(Person patient) {

    StringBuffer sb = new StringBuffer();

    sb.append("The dispenser is upside down and the patient: ");
    sb.append(patient.getName());
    sb.append(" didn't respond in the predefined interval");
    sb.append("\n");

    return sb.toString();

  }


}
