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

package org.universAAL.AALapplication.medication_manager.event.issuer.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.simulation.export.MedicationReminderContextProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import java.util.Timer;

/**
 * Implementing event issuers via timer in a predefined interval (property)
 * and to check all intake due in that interval and to start timer for each intake for that interval
 *
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {

  private Timer timer;

  private static ServiceTracker medicationReminderContextProviderTracker;
  private static ServiceTracker serviceTrackerProperties;
  private static ServiceTracker serviceTrackerPersistence;
  public static ModuleContext mc;

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    medicationReminderContextProviderTracker =
        new ServiceTracker(context, MedicationReminderContextProvider.class.getName(), null);
    medicationReminderContextProviderTracker.open();

    serviceTrackerProperties = new ServiceTracker(context, ConfigurationProperties.class.getName(), null);
    serviceTrackerProperties.open();

    serviceTrackerPersistence = new ServiceTracker(context, PersistentService.class.getName(), null);
    serviceTrackerPersistence.open();

    timer = new Timer();
    EventIssuer eventIssuer = new EventIssuer(timer);

    eventIssuer.start();

  }

  public void stop(BundleContext context) throws Exception {
    timer.cancel();
  }

  public static PersistentService getPersistentService() {
    if (serviceTrackerPersistence == null) {
      throw new MedicationManagerEventIssuerException("The PersistentService ServiceTracker is not set");
    }
    PersistentService service = (PersistentService) serviceTrackerPersistence.getService();
    if (service == null) {
      throw new MedicationManagerEventIssuerException("The PersistentService is missing");
    }

    return service;
  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (serviceTrackerProperties == null) {
      throw new MedicationManagerEventIssuerException("The ConfigurationProperties ServiceTracker is not set");
    }
    ConfigurationProperties service = (ConfigurationProperties) serviceTrackerProperties.getService();
    if (service == null) {
      throw new MedicationManagerEventIssuerException("The ConfigurationProperties is missing");
    }

    return service;
  }

  public static int getEventInssuerInterval() {
    ConfigurationProperties configurationProperties = getConfigurationProperties();

    return configurationProperties.getMedicationManagerIssuerIntervalInMinutes();
  }

  public static MedicationReminderContextProvider getMedicationReminderContextProvider() {
    if (medicationReminderContextProviderTracker == null) {
      throw new MedicationManagerEventIssuerException("The MedicationReminderContextProvider ServiceTracker is not set");
    }
    MedicationReminderContextProvider service =
        (MedicationReminderContextProvider) medicationReminderContextProviderTracker.getService();
    if (service == null) {
      throw new MedicationManagerEventIssuerException("The MedicationReminderContextProvider is missing");
    }

    return service;
  }

}
