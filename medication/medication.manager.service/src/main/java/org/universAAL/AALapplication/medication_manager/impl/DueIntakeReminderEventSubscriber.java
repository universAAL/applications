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

import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.providers.MissedIntakeContextProvider;
import org.universAAL.AALapplication.medication_manager.ui.ReminderDialog;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.medMgr.DueIntake;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.universAAL.AALapplication.medication_manager.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class DueIntakeReminderEventSubscriber extends ContextSubscriber {

  private final ModuleContext moduleContext;
  private final MissedIntakeContextProvider missedIntakeEventSubscriber;

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, DueIntake.MY_URI, 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }

  public DueIntakeReminderEventSubscriber(ModuleContext context, MissedIntakeContextProvider missedIntakeEventSubscriber) {
    super(context, getContextEventPatterns());

    this.moduleContext = context;
    this.missedIntakeEventSubscriber = missedIntakeEventSubscriber;

  }

  public void communicationChannelBroken() {
    //"Not implemented yet"
  }

  public void handleContextEvent(ContextEvent event) {
    try {
      Log.info("Received event of type %s", getClass(), event.getType());

      DueIntake dueIntake = (DueIntake) event.getRDFSubject();

      validateDueIntake(dueIntake);

      Time time = dueIntake.getTime();

      Log.info("Time %s", getClass(), time);

      String deviceUri = dueIntake.getDeviceUri();

      Log.info("DeviceUri %s", getClass(), deviceUri);

      PersistentService persistentService = getPersistentService();
      PersonDao personDao = persistentService.getPersonDao();

      Person patient = personDao.findPersonByDeviceUri(deviceUri);

      User user = new User(patient.getPersonUri());

      MedicineInventoryDao medicineInventoryDao = persistentService.getMedicineInventoryDao();

      IntakeDao intakeDao = persistentService.getIntakeDao();

      List<Intake> intakes = intakeDao.getIntakesByUserAndTime(user, time);

      ReminderDialog reminderDialog =
          new ReminderDialog(moduleContext, time, patient, intakes, persistentService);

      reminderDialog.showDialog(user);

      setTimeOut(reminderDialog, dueIntake, medicineInventoryDao, intakeDao, user, intakes, patient);
    } catch (Exception e) {
      Log.error(e, "Error while processing the context event", getClass());
    }

  }

  private void validateDueIntake(DueIntake dueIntake) {

    if (dueIntake.getTime() == null) {
      throw new MedicationManagerException("The time property is not set in the dueIntake event object");
    }

    if (dueIntake.getDeviceUri() == null) {
      throw new MedicationManagerException("The deviceUri property is not set in the dueIntake event object");
    }

  }

  private void setTimeOut(final ReminderDialog reminderDialog, final DueIntake dueIntake,
                          final MedicineInventoryDao medicineInventoryDao, final IntakeDao intakeDao,
                          final User user, final List<Intake> intakes, final Person patient) {

    ConfigurationProperties properties = getConfigurationProperties();

    final int timeoutSeconds = properties.getMedicationReminderTimeout();

    final Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        try {
          boolean userActed = reminderDialog.isUserActed();
          Log.info("Did the user responded in time?: %s", getClass(), userActed);
          if (userActed) {
            medicineInventoryDao.decreaseInventory(patient, intakes);
            intakeDao.setTimeTakenColumn(intakes);
          } else {
            publishMissedIntakeEvent(dueIntake, user, timeoutSeconds);
          }
          timer.cancel();
        } catch (Exception e) {
          Log.error(e, "Error while processing the timeout", getClass());
        }
      }

    }, timeoutSeconds * 1000);


  }

  private void publishMissedIntakeEvent(DueIntake dueIntake, User user, int timeoutSeconds) {
    Log.info("The user didn't respond in the required time: %s. " +
        "Publishing missed intake event", getClass(), timeoutSeconds);

    Time time = dueIntake.getTime();

    missedIntakeEventSubscriber.missedIntakeTimeEvent(time, user);

  }
}
