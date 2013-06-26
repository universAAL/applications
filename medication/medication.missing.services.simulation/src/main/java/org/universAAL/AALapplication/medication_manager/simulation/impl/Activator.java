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
import org.osgi.util.tracker.ServiceTracker;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.simulation.export.DispenserUpsideDownContextProvider;
import org.universAAL.AALapplication.medication_manager.simulation.export.MedicationConsumer;
import org.universAAL.AALapplication.medication_manager.simulation.export.MedicationReminderContextProvider;
import org.universAAL.AALapplication.medication_manager.simulation.export.NewPrescriptionHandler;
import org.universAAL.AALapplication.medication_manager.simulation.export.NewPrescriptionHandlerImpl;
import org.universAAL.AALapplication.medication_manager.simulation.export.NewPrescriptionHandlerMocked;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author George Fournadjiev
 */
public class Activator implements BundleActivator {

  public static ModuleContext mc;
  private static ServiceTracker configurationPropertiesServiceTracker;
  private static ServiceTracker persistenceServiceTracker;

  static BundleContext bc;

  /*
    * (non-Javadoc)
    * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
    */
  public void start(final BundleContext context) throws Exception {
    bc = context;
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    configurationPropertiesServiceTracker = new ServiceTracker(context, ConfigurationProperties.class.getName(), null);
    persistenceServiceTracker = new ServiceTracker(context, PersistentService.class.getName(), null);

    configurationPropertiesServiceTracker.open();
    persistenceServiceTracker.open();

    new Thread() {
      public void run() {
        new MedicationConsumer(mc);
        new CaregiverNotificationProvider(mc);
        MedicationReminderContextProvider medicationReminderContextProvider = new MedicationReminderContextProvider(mc);
        registerService(medicationReminderContextProvider, context);
        DispenserUpsideDownContextProvider dispenserUpsideDownContextProvider = new DispenserUpsideDownContextProvider(mc);
        registerService(dispenserUpsideDownContextProvider, context);
        new HealthPrescriptionServiceProvider(mc);
        NewPrescriptionContextProvider newPrescriptionContextProvider = new NewPrescriptionContextProvider(mc);
        NewPrescriptionHandler newPrescriptionHandler = getNewPrescriptionHandler(newPrescriptionContextProvider);

        context.registerService(NewPrescriptionHandler.class.getName(), newPrescriptionHandler, null);
      }
    }.start();


  }

  private NewPrescriptionHandler getNewPrescriptionHandler(NewPrescriptionContextProvider provider) {

    ConfigurationProperties configurationProperties = getConfigurationProperties();

    boolean isMocked = configurationProperties.isHealthTreatmentServiceMocked();

    NewPrescriptionHandler newPrescriptionHandler;

    if (isMocked) {
      newPrescriptionHandler = new NewPrescriptionHandlerMocked(mc, provider);
    } else {
      newPrescriptionHandler = new NewPrescriptionHandlerImpl(mc, provider);
    }

    return newPrescriptionHandler;
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

  /*public static ConfigurationProperties getConfigurationProperties() {
    if (bc == null) {
      throw new MedicationManagerSimulationServicesException("The bundleContext is not set");
    }

    ServiceReference srPS = bc.getServiceReference(ConfigurationProperties.class.getName());

    if (srPS == null) {
      throw new MedicationManagerSimulationServicesException("The ServiceReference is null for ConfigurationProperties");
    }

    ConfigurationProperties service = (ConfigurationProperties) bc.getService(srPS);

    if (service == null) {
      throw new MedicationManagerSimulationServicesException("The ConfigurationProperties is missing");
    }
    return service;
  }

  public static PersistentService getPersistentService() {
    if (bc == null) {
      throw new MedicationManagerSimulationServicesException("The bundleContext is not set");
    }

    ServiceReference srPS = bc.getServiceReference(PersistentService.class.getName());

    if (srPS == null) {
      throw new MedicationManagerSimulationServicesException("The ServiceReference is null for PersistentService");
    }

    PersistentService persistentService = (PersistentService) bc.getService(srPS);

    if (persistentService == null) {
      throw new MedicationManagerSimulationServicesException("The PersistentService is missing");
    }
    return persistentService;
  }*/

  public static ConfigurationProperties getConfigurationProperties() {
    if (configurationPropertiesServiceTracker == null) {
      throw new MedicationManagerSimulationServicesException("The ConfigurationProperties ServiceTracker is not set");
    }
    ConfigurationProperties service = (ConfigurationProperties) configurationPropertiesServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerSimulationServicesException("The ConfigurationProperties is missing");
    }

    return service;
  }

  public static PersistentService getPersistentService() {
    if (persistenceServiceTracker == null) {
      throw new MedicationManagerSimulationServicesException("The PersistentService ServiceTracker is not set");
    }
    PersistentService service = (PersistentService) persistenceServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerSimulationServicesException("The PersistentService is missing");
    }

    return service;
  }
}
