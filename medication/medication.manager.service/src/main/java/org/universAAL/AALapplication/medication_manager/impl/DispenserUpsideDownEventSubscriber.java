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
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PatientLinksDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.ui.DispenserUpsideDownDialog;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.medMgr.DispenserUpsideDown;
import org.universAAL.ontology.profile.User;

import java.util.Timer;
import java.util.TimerTask;

import static org.universAAL.AALapplication.medication_manager.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class DispenserUpsideDownEventSubscriber extends ContextSubscriber {

  private final ModuleContext moduleContext;
  private final UpsideDownCaregiverNotifier notifyCaregiver;

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, DispenserUpsideDown.MY_URI, 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }

  public DispenserUpsideDownEventSubscriber(ModuleContext context) {
    super(context, getContextEventPatterns());

    this.moduleContext = context;
    notifyCaregiver = new UpsideDownCaregiverNotifier();
  }

  public void communicationChannelBroken() {
    //"Not implemented yet"
  }

  public void handleContextEvent(ContextEvent event) {
    try {
      Log.info("Received event of type %s", getClass(), event.getType());

      DispenserUpsideDown dispenserUpsideDown = (DispenserUpsideDown) event.getRDFSubject();

      String deviceUri = dispenserUpsideDown.getDeviceId();

      Log.info("DeviceUri %s", getClass(), deviceUri);

      PersistentService persistentService = getPersistentService();
      DispenserDao dispenserDao = persistentService.getDispenserDao();
      Dispenser dispenser = dispenserDao.getByDispenserUri(deviceUri);

      if (dispenser == null) {
        throw new MedicationManagerException("Missing dispenser with deviceUri: " + deviceUri);
      }

      Person patient = dispenser.getPatient();

      if (patient == null) {
        throw new MedicationManagerException("This device is not associated with any patient");
      }

      User user = new User(patient.getPersonUri());

      DispenserUpsideDownDialog dispenserUpsideDownDialog =
          new DispenserUpsideDownDialog(moduleContext);

      dispenserUpsideDownDialog.showDialog(user);
      setTimeOut(dispenserUpsideDownDialog, patient, dispenser);
    } catch (MedicationManagerException e) {
      Log.error(e, "Error while processing the the context event", getClass());
    }

  }

  private void setTimeOut(final DispenserUpsideDownDialog upsideDownDialog,
                          final Person patient, final Dispenser dispenser) {

    ConfigurationProperties properties = getConfigurationProperties();

    final int timeoutSeconds = properties.getMedicationReminderTimeout();

    final Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        try {
          boolean userActed = upsideDownDialog.isUserActed();
          Log.info("Did the user responded in time?: %s", getClass(), userActed);
          if (dispenser.isUpsideDownAlert() && !userActed) {
            PersistentService persistentService = getPersistentService();
            PatientLinksDao patientLinksDao = persistentService.getPatientLinksDao();
            notifyCaregiver.notifyCaregiverForUpsiseDown(patient, patientLinksDao);
          }

          timer.cancel();
        } catch (Exception e) {
          Log.error(e, "Error while processing the timeout", getClass());
        }
      }

    }, timeoutSeconds * 1000);


  }
}
