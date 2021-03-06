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


package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.providers.MissedIntakeContextProvider;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.commands.MedicationConsoleCommands;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.commands.MedicationManagerCommands;
import org.universAAL.AALapplication.medication_manager.simulation.export.DispenserUpsideDownContextProvider;
import org.universAAL.AALapplication.medication_manager.simulation.export.MedicationReminderContextProvider;
import org.universAAL.AALapplication.medication_manager.simulation.export.NewPrescriptionHandler;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import java.io.File;
import java.util.Hashtable;

import static java.io.File.*;


/**
 * @author George Fournadjiev
 */
public class Activator implements BundleActivator {

  public static ModuleContext mc;
  public static BundleContext bundleContext;
  public static File medicationManagerConfigurationDirectory;
  private static ServiceTracker medicationReminderContextProviderTracker;
  private static ServiceTracker persistenceServiceTracker;
  private static ServiceTracker httpServiceTracker;
  private static ServiceTracker newPrescriptionHandlerTracker;
  private static ServiceTracker dispenserUpsideDownContextProviderTracker;
  private static ServiceTracker missedIntakeContextProviderTracker;
  private static ServiceTracker configurationPropertiesServiceTracker;
  private static ServiceTracker userManagerTracker;

  private static final String OSGI_COMMAND_SCOPE = "osgi.command.scope";
  private static final String OSGI_COMMAND_FUNCTION = "osgi.command.function";

  public void start(final BundleContext context) throws Exception {

    medicationManagerConfigurationDirectory = getMedicationManagerConfigurationDirectory();

    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    medicationReminderContextProviderTracker = new ServiceTracker(context, MedicationReminderContextProvider.class.getName(), null);
    persistenceServiceTracker = new ServiceTracker(context, PersistentService.class.getName(), null);
    httpServiceTracker = new ServiceTracker(context, HttpService.class.getName(), null);
    newPrescriptionHandlerTracker = new ServiceTracker(context, NewPrescriptionHandler.class.getName(), null);
    missedIntakeContextProviderTracker = new ServiceTracker(context, MissedIntakeContextProvider.class.getName(), null);
    dispenserUpsideDownContextProviderTracker =
        new ServiceTracker(context, DispenserUpsideDownContextProvider.class.getName(), null);
    configurationPropertiesServiceTracker = new ServiceTracker(context, ConfigurationProperties.class.getName(), null);
    userManagerTracker = new ServiceTracker(context, UserManager.class.getName(), null);

    medicationReminderContextProviderTracker.open();
    persistenceServiceTracker.open();
    httpServiceTracker.open();
    newPrescriptionHandlerTracker.open();
    missedIntakeContextProviderTracker.open();
    dispenserUpsideDownContextProviderTracker.open();
    configurationPropertiesServiceTracker.open();
    userManagerTracker.open();

    Hashtable props = new Hashtable();
    props.put(OSGI_COMMAND_SCOPE, MedicationConsoleCommands.COMMAND_PREFIX);
    props.put(OSGI_COMMAND_FUNCTION, new String[]{
        MedicationConsoleCommands.HELP_COMMMAND,
        MedicationConsoleCommands.LISTIDS_COMMMAND,
        MedicationConsoleCommands.USECASE_COMMMAND,
        MedicationConsoleCommands.INSERT_USER_COMMMAND,
        MedicationConsoleCommands.SQL_COMMMAND}
    );
    context.registerService(MedicationManagerCommands.class.getName(),
        new MedicationManagerCommands(), props);

  }

  public void stop(BundleContext context) throws Exception {
    medicationManagerConfigurationDirectory = null;
    mc = null;
    bundleContext = null;
  }

  private File getMedicationManagerConfigurationDirectory() {

    String pathToMedicationManagerConfigurationDirectory;
    try {
      File currentDir = new File(".");
      String pathToCurrentDir = currentDir.getCanonicalPath();
      String bundlesConfigurationLocationProperty = System.getProperty("bundles.configuration.location");
      pathToMedicationManagerConfigurationDirectory = pathToCurrentDir + separator +
          bundlesConfigurationLocationProperty + separator + "medication_manager";
    } catch (Exception e) {
      throw new MedicationManagerShellException(e);
    }

    File directory = new File(pathToMedicationManagerConfigurationDirectory);
    if (!directory.exists()) {
      throw new MedicationManagerShellException("The directory does not exists:" + directory);
    }

    if (!directory.isDirectory()) {
      throw new MedicationManagerShellException("The following file:" + directory + " is not a valid directory");
    }

    return directory;
  }

  public static MedicationReminderContextProvider getMedicationReminderContextProvider() {
    if (medicationReminderContextProviderTracker == null) {
      throw new MedicationManagerShellException("The MedicationReminderContextProvider ServiceTracker is not set");
    }
    MedicationReminderContextProvider service =
        (MedicationReminderContextProvider) medicationReminderContextProviderTracker.getService();
    if (service == null) {
      throw new MedicationManagerShellException("The MedicationReminderContextProvider is missing");
    }

    return service;
  }

  public static DispenserUpsideDownContextProvider getDispenserUpsideDownContextProvider() {
    if (dispenserUpsideDownContextProviderTracker == null) {
      throw new MedicationManagerShellException("The DispenserUpsideDownContextProvider ServiceTracker is not set");
    }
    DispenserUpsideDownContextProvider service =
        (DispenserUpsideDownContextProvider) dispenserUpsideDownContextProviderTracker.getService();
    if (service == null) {
      throw new MedicationManagerShellException("The DispenserUpsideDownContextProvider is missing");
    }

    return service;
  }

  public static MissedIntakeContextProvider getMissedIntakeContextProvider() {
    if (missedIntakeContextProviderTracker == null) {
      throw new MedicationManagerShellException("The MissedIntakeContextProvider ServiceTracker is not set");
    }
    MissedIntakeContextProvider service =
        (MissedIntakeContextProvider) missedIntakeContextProviderTracker.getService();
    if (service == null) {
      throw new MedicationManagerShellException("The MissedIntakeContextProvider is missing");
    }

    return service;
  }

  public static PersistentService getPersistentService() {
    if (persistenceServiceTracker == null) {
      throw new MedicationManagerShellException("The PersistentService ServiceTracker is not set");
    }
    PersistentService service = (PersistentService) persistenceServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerShellException("The PersistentService is missing");
    }

    return service;
  }

  private static HttpService getHttpService() {
    if (httpServiceTracker == null) {
      throw new MedicationManagerShellException("The HttpService ServiceTracker is not set");
    }
    HttpService service = (HttpService) httpServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerShellException("The HttpService is missing");
    }

    return service;
  }

  public static NewPrescriptionHandler getNewPrescriptionHandler() {
    if (newPrescriptionHandlerTracker == null) {
      throw new MedicationManagerShellException("The NewPrescriptionHandler ServiceTracker is not set");
    }
    NewPrescriptionHandler service = (NewPrescriptionHandler) newPrescriptionHandlerTracker.getService();
    if (service == null) {
      throw new MedicationManagerShellException("The NewPrescriptionHandler is missing");
    }

    return service;
  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (configurationPropertiesServiceTracker == null) {
      throw new MedicationManagerShellException("The ConfigurationProperties ServiceTracker is not set");
    }
    ConfigurationProperties service = (ConfigurationProperties) configurationPropertiesServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerShellException("The ConfigurationProperties is missing");
    }

    return service;
  }

  public static UserManager getUserManager() {
    if (userManagerTracker == null) {
      throw new MedicationManagerShellException("The UserManager ServiceTracker is not set");
    }
    UserManager service = (UserManager) userManagerTracker.getService();
    if (service == null) {
      throw new MedicationManagerShellException("The UserManager is missing");
    }

    return service;
  }
}
