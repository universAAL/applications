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
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.commands.MedicationConsoleCommands;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.commands.MedicationManagerCommands;
import org.universAAL.AALapplication.medication_manager.ui.NewPrescriptionHandler;
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

  public static final Logger logger = LoggerFactory.getLogger(Activator.class);
  private static final String OSGI_COMMAND_SCOPE = "osgi.command.scope";
  private static final String OSGI_COMMAND_FUNCTION = "osgi.command.function";

  public void start(final BundleContext context) throws Exception {

    medicationManagerConfigurationDirectory = getMedicationManagerConfigurationDirectory();

    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    Hashtable props = new Hashtable();
    props.put(OSGI_COMMAND_SCOPE, MedicationConsoleCommands.COMMAND_PREFIX);
    props.put(OSGI_COMMAND_FUNCTION, new String[]{
        MedicationConsoleCommands.HELP_COMMMAND,
        MedicationConsoleCommands.LISTIDS_COMMMAND,
        MedicationConsoleCommands.USECASE_COMMMAND,
        MedicationConsoleCommands.SQL_COMMMAND}
    );
    context.registerService(MedicationManagerCommands.class.getName(),
        new MedicationManagerCommands(), props);

  }

  public void stop(BundleContext context) throws Exception {
    medicationManagerConfigurationDirectory = null;
  }

  private File getMedicationManagerConfigurationDirectory() {

    String pathToMedicationManagerConfigurationDirectory;
    try {
      File currentDir = new File(".");
      String pathToCurrentDir = currentDir.getCanonicalPath();
      String bundlesConfigurationLocationProperty = System.getProperty("bundles.configuration.location");
      pathToMedicationManagerConfigurationDirectory = pathToCurrentDir + separator +
          bundlesConfigurationLocationProperty + separator + "services" + separator + "medication_manager";
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

  public static PersistentService getPersistentService() {
    if (bundleContext == null) {
      throw new MedicationManagerShellException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(PersistentService.class.getName());

    if (srPS == null) {
      throw new MedicationManagerShellException("The ServiceReference is null for PersistentService");
    }

    PersistentService persistentService = (PersistentService) bundleContext.getService(srPS);

    if (persistentService == null) {
      throw new MedicationManagerShellException("The PersistentService is missing");
    }
    return persistentService;
  }

  public static NewPrescriptionHandler getNewPrescriptionHandler() {
    if (bundleContext == null) {
      throw new MedicationManagerShellException("The bundleContext is not set");
    }

    ServiceReference sr = bundleContext.getServiceReference(NewPrescriptionHandler.class.getName());

    if (sr == null) {
      throw new MedicationManagerShellException("The ServiceReference is null for NewPrescriptionHandler");
    }

    NewPrescriptionHandler newPrescriptionHandler = (NewPrescriptionHandler) bundleContext.getService(sr);
    if (newPrescriptionHandler == null) {
      throw new MedicationManagerShellException("The NewPrescriptionHandler service is missing");
    }
    return newPrescriptionHandler;
  }


}
