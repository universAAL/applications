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
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;
import org.universAAL.AALapplication.medication_manager.ui.impl.Activator;
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
import org.universAAL.ontology.medMgr.MedicinesInfo;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static org.universAAL.AALapplication.medication_manager.persistence.layer.Util.*;
import static org.universAAL.AALapplication.medication_manager.persistence.layer.Util.getMessage;
import static org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass.*;
import static org.universAAL.AALapplication.medication_manager.ui.IntakeReviewDialog.*;
import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.getMessage;

public class ReminderDialog extends UICaller {

  private final ModuleContext moduleContext;
  private final Time time;
  private final Person patient;
  private final List<Intake> intakes;
  private final PersistentService persistentService;
  private final ServiceCaller serviceCaller;
  private final IntakeDao intakeDao;
  private final MedicineInventoryDao medicineInventoryDao;
  private User currentUser; //TODO to be removed (hack for saied user)
  private boolean userActed;
  private DueIntakeTimer dueIntakeTimer;
  private MedicinesInfo medicinesInfo;

  private static final String TAKEN_BUTTON = "takenButton";
  private static final String MISSED_BUTTON = "missedButton";
  private static final String INFO_BUTTON = "reminderButton";
  private static final String REQUEST_NEW_DOSE_BUTTON = "requestNewDoseButton";
  public static final String PILL = "pill";

  public ReminderDialog(ModuleContext context, Time time, Person patient, List<Intake> intakes,
                        PersistentService persistentService, IntakeDao intakeDao, MedicineInventoryDao medicineInventoryDao) {
    super(context);

    validateParameter(context, "context");


    moduleContext = context;
    this.time = time;
    this.userActed = false;
    this.patient = patient;
    this.intakes = intakes;
    this.persistentService = persistentService;
    this.serviceCaller = new DefaultServiceCaller(context);
    this.intakeDao = intakeDao;
    this.medicineInventoryDao = medicineInventoryDao;
  }

  public ReminderDialog(ModuleContext context) {
    this(context, null, null, null, null, null, null);
  }

  public ReminderDialog(ModuleContext context, Time time) {
    this(context, time, null, null, null, null, null);
  }

  @Override
  public void communicationChannelBroken() {
  }

  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    validateDueIntakeTimerIsSet();
    try {
      userActed = true;
      //TODO to be removed (hack for saied user)
      User user = (User) input.getUser();
      if (user.getURI().equals(SAIED.getURI())) {
        user = currentUser;
      }
      String submissionID = input.getSubmissionID();
      handleUIResponse(submissionID, user);
    } catch (Exception e) {
      Log.error(e, "Error while handling UI response", getClass());
    }

    userActed = false;
  }

  private void handleUIResponse(String submissionID, User user) {

    if (TAKEN_BUTTON.equals(submissionID)) {
      handleTakenButton();
    } else if (MISSED_BUTTON.equals(submissionID)) {
      handleMissedButton();
    } else if (INFO_BUTTON.equals(submissionID)) {
      handleInfoButton(user);
    } else if (REQUEST_NEW_DOSE_BUTTON.equals(submissionID)) {
      intakeDao.setTimeTakenColumn(intakes);
      medicineInventoryDao.decreaseInventory(patient, intakes);
      medicineInventoryDao.decreaseInventory(patient, intakes);
      String newDoseMessage = createMessage();
      if (newDoseMessage != null) {
        String smsText = getSmsText(time, patient, newDoseMessage);
        notifyCaregiver(smsText);
      }
      if (!dueIntakeTimer.isTimeoutExpired()) {
        dueIntakeTimer.cancel();
      }
    } else {
      Log.info("Unknown button %s\n", getClass(), submissionID);
    }
  }

  private void handleInfoButton(User user) {
    showMedicationInfoDialog(user);
    if (!dueIntakeTimer.isTimeoutExpired()) {
      takenIntakeDatabaseRecords();
      dueIntakeTimer.cancel();
    }
  }

  private void handleTakenButton() {
    takenIntakeDatabaseRecords();

    if (dueIntakeTimer.isTimeoutExpired()) {
      String smsText = createDeferredTakenMessage();
      notifyCaregiver(smsText);
    } else {
      dueIntakeTimer.cancel();
    }
  }

  private void takenIntakeDatabaseRecords() {
    medicineInventoryDao.decreaseInventory(patient, intakes);
    intakeDao.setTimeTakenColumn(intakes);
  }

  private void handleMissedButton() {
    if (!dueIntakeTimer.isTimeoutExpired()) {
      String smsText = createMissedMessage();
      notifyCaregiver(smsText);
      dueIntakeTimer.cancel();
    }
  }

  private String createDeferredTakenMessage() {
    StringBuffer sb = new StringBuffer();

    sb.append(patient.getName());
    sb.append(" has taken the medicine scheduled for ");
    String timeText = getTimeText(time);
    sb.append(timeText);
    sb.append(" with a delay at ");
    Calendar now = Calendar.getInstance();
    Time t = new Time(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE));
    sb.append(getTimeText(t));
    sb.append(".\n");

    return sb.toString();
  }

  private String createMissedMessage() {
    StringBuffer sb = new StringBuffer();

    sb.append(patient.getName());
    sb.append(" missed to take the intake at");
    String timeText = getTimeText(time);
    sb.append(timeText);
    sb.append(".\n");

    return sb.toString();
  }

  private void notifyCaregiver(String smsText) {

    User involvedHumanUser = new User(patient.getPersonUri());
    ServiceRequest serviceRequest = new ServiceRequest(new CaregiverNotifier(), involvedHumanUser);

    CaregiverNotifierData caregiverNotifierData = new CaregiverNotifierData();
    String smsNumber = getCaregiverSms(patient, persistentService.getPatientLinksDao());
    caregiverNotifierData.setSmsNumber(smsNumber);
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

  private void showMedicationInfoDialog(User user) {

    if (time == null) {
      throw new MedicationManagerUIException("There are fields in the ReminderDialog class which are null");
    }

    MedicationInfoDialog medicationInfoDialog = new MedicationInfoDialog(moduleContext, time, getMedicinesInfo());
    medicationInfoDialog.showDialog(user);

  }

  public void showDialog(User inputUser) {

    validateParameter(inputUser, "inputUser");
    validateDueIntakeTimerIsSet();

    try {
      //TODO to be removed (hack for saied user)
      currentUser = inputUser;

      createMedicineInfo(inputUser);

      Form f = Form.newDialog(getMessage("medication.manager.ui.title"), new Resource());

      //start of the form model

      String reminderMessage = getReminderMessage(inputUser);

      new SimpleOutput(f.getIOControls(), null, null, reminderMessage);
      //...
      new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.taken"), null), TAKEN_BUTTON);
      new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.missed"), null), MISSED_BUTTON);
      new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.info"), null), INFO_BUTTON);
      new Submit(f.getSubmits(), new Label(getMessage("medication.manager.ui.new.dose"), null), REQUEST_NEW_DOSE_BUTTON);
      //stop of form model
      //TODO to remove SAIED user and to return inputUser variable
      UIRequest req = new UIRequest(SAIED, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
      this.sendUIRequest(req);
      dueIntakeTimer.setTimeOut();
    } catch (Exception e) {
      Log.error(e, "Error while trying to show dialog", getClass());
    }
  }

  private String getReminderMessage(User inputUser) {
    String timeText = getTimeText(time);
    String name = getName(persistentService, inputUser);
    String titleMessage = getMessage("medication.manager.ui.reminder.message", name, timeText);


    return titleMessage + "\n" + medicinesInfo.getGeneralInfo();
  }

  private String getTimeText(Time t) {
    String timeText;
    if (t != null) {
      timeText = '<' + t.getDailyTextFormat() + '>';
    } else {
      timeText = "";
    }
    return timeText;
  }

  private void validateDueIntakeTimerIsSet() {
    if (dueIntakeTimer == null) {
      throw new MedicationManagerUIException("The DueIntakeTimer has not been set");
    }
  }

  public boolean isUserActed() {
    return userActed;
  }

  public void setDueIntakeTimer(DueIntakeTimer dueIntakeTimer) {
    this.dueIntakeTimer = dueIntakeTimer;
  }

  private void createMedicineInfo(User inputUser) {
    PersistentService persistentService = getPersistentService();

    IntakeDao intakeDao = persistentService.getIntakeDao();

    List<Intake> intakes = intakeDao.getIntakesByUserAndTime(inputUser, time);

    this.medicinesInfo = createMedicineInfoFromIntakes(intakes);
  }

  public MedicinesInfo getMedicinesInfo() {
    if (medicinesInfo == null) {
      throw new MedicationManagerUIException("The MedicineInfo field is not set");
    }
    return medicinesInfo;
  }

  public MedicinesInfo createMedicineInfoFromIntakes(List<Intake> intakes) {
    String generalInfo = getGeneralInfo(intakes);
    String detailsInfo = getDetailsInfo(intakes);

    return new MedicinesInfo(generalInfo, detailsInfo, time);
  }

  private String getGeneralInfo(List<Intake> intakes) {
    StringBuilder sb = new StringBuilder();

    sb.append("\t\t\t");
    String dailyTextFormat = time.getDailyTextFormat();
    String title = getMessage("medication.manager.ui.intake.info.for.title", dailyTextFormat);
    sb.append(title);

    sb.append("\n");
    appendQuantityAndUnits(sb, intakes);

    return sb.toString();
  }

  private void appendQuantityAndUnits(StringBuilder sb, List<Intake> intakes) {
    int count = 0;
    for (Intake in : intakes) {
      sb.append('\n');
      count++;
      Treatment treatment = in.getTreatment();
      String medicineName = treatment.getMedicine().getMedicineName();
      UnitClass unitClass = in.getUnitClass();
      String type;

      if (DROPS == unitClass) {
        type = Activator.getMessage("medication.manager.ui.intake.type.drops");
      } else if (PILLS == unitClass) {
        type = Activator.getMessage("medication.manager.ui.intake.type.pills");
      } else {

        throw new MedicationManagerUIException("Unexpected Enum type for the UnitClass: " + unitClass);
      }
      String rowMessage = getMessage("medication.manager.ui.intake.info.for.row",
          count, medicineName, in.getQuantity(), type);
      sb.append(rowMessage);
    }


    sb.append('\n');
  }

  /*
  <id>. <medicine_name> \n\n
  <tab> <subtitle> \n
  <tab> <text>\n\n
   */

  private String getDetailsInfo(List<Intake> intakes) {
    StringBuilder sb = new StringBuilder();

    sb.append("\n\t\t\t");
    sb.append(Activator.getMessage("medication.manager.ui.medication.info"));
    sb.append("\n\t\t\t");


    int count = 0;
    for (Intake in : intakes) {
      sb.append('\n');
      count++;
      sb.append(count);
      sb.append(". ");
      Treatment treatment = in.getTreatment();
      Medicine medicine = treatment.getMedicine();
      sb.append(medicine.getMedicineName());
      sb.append("\n\n\t");
      sb.append(Activator.getMessage("medication.manager.ui.medicine.description"));
      sb.append("\n\t");
      String medicineInfo = medicine.getMedicineInfo();
      if (medicineInfo.trim().isEmpty()) {
        medicineInfo = Activator.getMessage("medication.manager.ui.missing.medicine.description");
      }
      sb.append(medicineInfo);
      sb.append("\n\n\t");

      sb.append(Activator.getMessage("medication.manager.ui.medicine.sideeffects"));
      sb.append("\n\t");
      String sideeffects = medicine.getMedicineSideEffects();
      if (sideeffects == null || medicineInfo.trim().isEmpty()) {
        sideeffects = Activator.getMessage("medication.manager.ui.missing.medicine.sideeffects");
      }
      sb.append(sideeffects);
      sb.append("\n\n\t");

      sb.append(Activator.getMessage("medication.manager.ui.medicine.incompliances"));
      sb.append("\n\t");
      String incompliances = medicine.getIncompliances();
      if (incompliances == null || medicineInfo.trim().isEmpty()) {
        incompliances = Activator.getMessage("medication.manager.ui.missing.medicine.incompliances");
      }
      sb.append(incompliances);
      sb.append("\n\n");
    }

    return sb.toString();
  }

@Override
public void dialogAborted(String arg0, Resource arg1) {
	// TODO Auto-generated method stub
	
}


}
