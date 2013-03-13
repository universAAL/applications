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


package org.universAAL.AALapplication.medication_manager.simulation.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.AALapplication.medication_manager.simulation.export.DispenserUpsideDownContextProvider;
import org.universAAL.AALapplication.medication_manager.simulation.export.MedicationConsumer;
import org.universAAL.AALapplication.medication_manager.simulation.export.MedicationReminderContextProvider;
import org.universAAL.AALapplication.medication_manager.simulation.export.NewPrescriptionHandler;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author George Fournadjiev
 */
public class Activator implements BundleActivator {

  public static ModuleContext mc;

  public static final Logger logger = LoggerFactory.getLogger(Activator.class);

  /*
    * (non-Javadoc)
    * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
    */
  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    new Thread() {
      public void run() {
        new MedicationConsumer(mc);
        new CaregiverNotificationProvider(mc);
        MedicationReminderContextProvider medicationReminderContextProvider = new MedicationReminderContextProvider(mc);
        registerService(medicationReminderContextProvider, context);
        DispenserUpsideDownContextProvider dispenserUpsideDownContextProvider = new DispenserUpsideDownContextProvider(mc);
        registerService(dispenserUpsideDownContextProvider, context);
        new HealthPrescriptionServiceProvider(mc);
      }
    }.start();

    NewPrescriptionContextProvider newPrescriptionContextProvider = new NewPrescriptionContextProvider(mc);
    NewPrescriptionHandler newPrescriptionHandler = new NewPrescriptionHandler(mc, newPrescriptionContextProvider);

    context.registerService(NewPrescriptionHandler.class.getName(), newPrescriptionHandler, null);

  }

  private void registerService(Object service, BundleContext context) {

    context.registerService(service.getClass().getName(), service, null);

  }

  /*
    * (non-Javadoc)
    * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
    */
  public void stop(BundleContext context) throws Exception {

  }
}
