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


package org.universAAL.AALapplication.medication_manager.ui;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.ui.impl.MedicationManagerUIException;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.medMgr.CaregiverNotifier;
import org.universAAL.ontology.medMgr.CaregiverNotifierData;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.List;
import java.util.Locale;

import static org.universAAL.AALapplication.medication_manager.persistence.layer.Util.*;
import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

public class ReminderDialog extends UICaller {

  private final ModuleContext moduleContext;
  private final Time time;
  private final Person patient;
  private final List<Intake> intakes;
  private final PersistentService persistentService;
  private final ServiceCaller serviceCaller;

  private boolean userActed;

  private static final String CLOSE_BUTTON = "closeButton";
  private static final String INFO_BUTTON = "reminderButton";
  private static final String REQUEST_NEW_DOSE_BUTTON = "requestNewDoseButton";

  public ReminderDialog(ModuleContext context, Time time, Person patient,
                        List<Intake> intakes, PersistentService persistentService) {
    super(context);

    validateParameter(context, "context");


    moduleContext = context;
    this.time = time;
    this.userActed = false;
    this.patient = patient;
    this.intakes = intakes;
    this.persistentService = persistentService;
    this.serviceCaller = new DefaultServiceCaller(context);
  }

  public ReminderDialog(ModuleContext context) {
    this(context, null, null, null, null);
  }

  public ReminderDialog(ModuleContext context, Time time) {
    this(context, time, null, null, null);
  }

  @Override
  public void communicationChannelBroken() {
  }

  @Override
  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    try {
      userActed = true;
      if (CLOSE_BUTTON.equals(input.getSubmissionID())) {
        System.out.println("close");
      } else if (INFO_BUTTON.equals(input.getSubmissionID())) {
        showRequestMedicationInfoDialog((User) input.getUser());
      } else if (REQUEST_NEW_DOSE_BUTTON.equals(input.getSubmissionID())) {
        decreaseInventory();
        String newDoseMessage = createMessage();
        if (newDoseMessage != null) {
          notifyCaregiver(newDoseMessage);
        }
      } else {
        System.out.println("unknown");
      }
    } catch (Exception e) {
      Log.error(e, "Error while handling UI response", getClass());
    }

  }

  private void notifyCaregiver(String newDoseMessage) {

    User involvedHumanUser = new User(patient.getPersonUri());
    ServiceRequest serviceRequest = new ServiceRequest(new CaregiverNotifier(), involvedHumanUser);

    CaregiverNotifierData caregiverNotifierData = new CaregiverNotifierData();
    String smsNumber = getCaregiverSms(patient, persistentService.getPatientLinksDao());
    caregiverNotifierData.setSmsNumber(smsNumber);
    String smsText = getSmsText(time, patient, newDoseMessage);
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

  private String getSmsText(Time time, Person person, String newDoseMessage) {

    StringBuffer sb = new StringBuffer();

    sb.append("Requested a new dose : ");
    sb.append(time.getDailyTextFormat());
    sb.append(" for the following user: ");
    sb.append(person.getName());
    sb.append(".\n Detailed information about requested new dose: ");
    sb.append(newDoseMessage);


    sb.append("\n");

    return sb.toString();
  }

  private String createMessage() {
    StringBuffer sb = new StringBuffer();
    boolean hasAlerts = false;
    int counter = 0;
    for (Intake intake : intakes) {
      Treatment treatment = intake.getTreatment();
      if (treatment.isNewDoseAlert()) {
        hasAlerts = true;
        Medicine medicine = treatment.getMedicine();
        counter++;
        sb.append("\n");
        sb.append(counter);
        sb.append(". ");
        sb.append("The patient has requested a new dose for the following medicines: ");
        sb.append(medicine.getMedicineName());
        sb.append(". With the following quantity: ");
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

  private void decreaseInventory() {
    MedicineInventoryDao medicineInventoryDao = persistentService.getMedicineInventoryDao();
    if (medicineInventoryDao == null || patient == null || intakes == null) {
      throw new MedicationManagerUIException("There are fields in the ReminderDialog class which are null");
    }
    medicineInventoryDao.decreaseInventory(patient, intakes);
  }

  private void showRequestMedicationInfoDialog(User user) {

    if (time == null) {
      throw new MedicationManagerUIException("There are fields in the ReminderDialog class which are null");
    }

    RequestMedicationInfoDialog dialog = new RequestMedicationInfoDialog(moduleContext, time);
    dialog.showDialog(user);

  }

  public void showDialog(User inputUser) {

    try {
      validateParameter(inputUser, "inputUser");

      Form f = Form.newDialog("Medication Manager UI", new Resource());

      //start of the form model

      String timeText;
      if (time != null) {
        timeText = '<' + time.getDailyTextFormat() + '>';
      } else {
        timeText = "";
      }
      String reminderMessage = getUserfriendlyName(inputUser) + ",\nit is time " + timeText + " to get your medicine.";

      new SimpleOutput(f.getIOControls(), null, null, reminderMessage);
      //...
      new Submit(f.getSubmits(), new Label("Close", null), CLOSE_BUTTON);
      new Submit(f.getSubmits(), new Label("Info", null), INFO_BUTTON);
      new Submit(f.getSubmits(), new Label("New dose", null), REQUEST_NEW_DOSE_BUTTON);
      //stop of form model
      UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
      this.sendUIRequest(req);
    } catch (Exception e) {
      Log.error(e, "Error while trying to show dialog", getClass());
    }
  }

  public static String getUserfriendlyName(User inputUser) {
    String fullUserUriName = inputUser.toString();
    int index = fullUserUriName.lastIndexOf('#');
    if (index == -1) {
      throw new MedicationManagerUIException("Expected # symbol in the user.getUri() format like: \n" +
          "urn:org.universAAL.aal_space:test_env#saied");
    }
    String firstLetter = fullUserUriName.substring(index + 1).toUpperCase();
    return firstLetter.charAt(0) + fullUserUriName.substring(index + 2);
  }

  public boolean isUserActed() {
    return userActed;
  }
}
