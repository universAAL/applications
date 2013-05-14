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


package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.medMgr.CaregiverNotifier;
import org.universAAL.ontology.medMgr.CaregiverNotifierData;
import org.universAAL.ontology.medMgr.MissedIntake;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.List;

import static org.universAAL.AALapplication.medication_manager.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public final class MissedIntakeEventSubscriber extends ContextSubscriber {

  private final ServiceCaller serviceCaller;

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, MissedIntake.MY_URI, 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }

  public MissedIntakeEventSubscriber(ModuleContext context) {
    super(context, getContextEventPatterns());

    serviceCaller = new DefaultServiceCaller(context);
  }

  public void communicationChannelBroken() {
    //"Not implemented yet"
  }

  public void handleContextEvent(ContextEvent event) {
    try {
      Log.info("Received event of type %s", getClass(), event.getType());

      MissedIntake missedIntake = (MissedIntake) event.getRDFSubject();

      Time time = missedIntake.getTime();

      Log.info("Time %s", getClass(), time);

      User user = missedIntake.getUser();

      Log.info("Calling the Caregiver Notification Service for the userId %s", getClass(), user);

      PersistentService persistentService = getPersistentService();

      PersonDao personDao = persistentService.getPersonDao();

      Person person = personDao.findPersonByPersonUri(user.getURI());
      IntakeDao intakeDao = persistentService.getIntakeDao();
      List<Intake> intakes = intakeDao.getIntakesByUserAndTime(user, time);
      String intakesMessage = createMessage(intakes);

      if (intakesMessage != null) {
        notifyCaregiver(time, user, persistentService, person, intakesMessage);
      }

    } catch (Exception e) {
      Log.error(e, "Error while processing the the context event", getClass());
    }

  }

  private String createMessage(List<Intake> intakes) {
    StringBuffer sb = new StringBuffer();
    boolean hasAlerts = false;
    int counter = 0;
    for (Intake intake : intakes) {
      Treatment treatment = intake.getTreatment();
      if (treatment.isMissedIntakeAlert()) {
        hasAlerts = true;
        Medicine medicine = treatment.getMedicine();
        counter++;
        sb.append("\n");
        sb.append(counter);
        sb.append(". ");
        sb.append("The patient has missed to take the following medicine: ");
        sb.append(medicine.getMedicineName());
        sb.append(". With the following dose: ");
        sb.append(intake.getQuantity());
        sb.append(" ");
        sb.append(intake.getUnitClass());
      }
    }

    if (!hasAlerts) {
      return null;
    }

    sb.append("\n");
    return sb.toString();
  }

  private void notifyCaregiver(Time time, User user, PersistentService persistentService,
                               Person person, String intakesMessage) {

    ServiceRequest serviceRequest = new ServiceRequest(new CaregiverNotifier(), user);

    CaregiverNotifierData caregiverNotifierData = new CaregiverNotifierData();
    String smsNumber = getCaregiverSms(person, persistentService.getPatientLinksDao());
    caregiverNotifierData.setSmsNumber(smsNumber);
    String smsText = getSmsText(time, person, intakesMessage);
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

  private String getSmsText(Time time, Person person, String intakesMessage) {

    StringBuffer sb = new StringBuffer();

    sb.append("Missed intake occurred at : ");
    sb.append(time.getDailyTextFormat());
    sb.append(" for the following user: ");
    sb.append(person.getName());
    sb.append(".\n Detailed information about missed intake: ");
    sb.append(intakesMessage);


    sb.append("\n");

    return sb.toString();
  }

}
