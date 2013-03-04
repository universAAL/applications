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


package org.universAAL.AALapplication.contact_manager.shell.commands.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.AALapplication.contact_manager.persistence.layer.ContactManagerPersistentService;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.callers.AddContactConsumer;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.callers.EditContactConsumer;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.callers.GetContactConsumer;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.callers.RemoveContactConsumer;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactConsoleCommands;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactManagerCommands;
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
  public static File contactManagerConfigurationDirectory;

  public static final Logger logger = LoggerFactory.getLogger(Activator.class);
  private static final String OSGI_COMMAND_SCOPE = "osgi.command.scope";
  private static final String OSGI_COMMAND_FUNCTION = "osgi.command.function";

  public void start(final BundleContext context) throws Exception {

    contactManagerConfigurationDirectory = getContactManagerConfigurationDirectory();

    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    new Thread() {
          public void run() {
            new AddContactConsumer(mc);
            new EditContactConsumer(mc);
            new RemoveContactConsumer(mc);
            new GetContactConsumer(mc);
          }
        }.start();

    Hashtable props = new Hashtable();
    props.put(OSGI_COMMAND_SCOPE, ContactConsoleCommands.COMMAND_PREFIX);
    props.put(OSGI_COMMAND_FUNCTION, new String[]{
        ContactConsoleCommands.HELP_COMMAND,
        ContactConsoleCommands.ADD_CONTACT,
        ContactConsoleCommands.GET_CONTACT,
        ContactConsoleCommands.REMOVE_CONTACT,
        ContactConsoleCommands.EDIT_CONTACT,
        ContactConsoleCommands.LIST_CONTACTS,
        ContactConsoleCommands.IMPORT_CONTACT,
        ContactConsoleCommands.EXPORT_CONTACT,
        ContactConsoleCommands.PRINT_TABLES}
    );
    context.registerService(ContactManagerCommands.class.getName(),
        new ContactManagerCommands(), props);

    System.out.println("contact.manager.shell.commands started");
  }

  public void stop(BundleContext context) throws Exception {
    contactManagerConfigurationDirectory = null;
    mc = null;
    bundleContext = null;
  }

  private File getContactManagerConfigurationDirectory() {

      String pathToMedicationManagerConfigurationDirectory;
      try {
        File currentDir = new File(".");
        String pathToCurrentDir = currentDir.getCanonicalPath();
        String bundlesConfigurationLocationProperty = System.getProperty("bundles.configuration.location");
        pathToMedicationManagerConfigurationDirectory = pathToCurrentDir + separator +
            bundlesConfigurationLocationProperty + separator + "contact_manager";
      } catch (Exception e) {
        throw new ContactManagerShellException(e);
      }

      File directory = new File(pathToMedicationManagerConfigurationDirectory);
      if (!directory.exists()) {
        throw new ContactManagerShellException("The directory does not exists:" + directory);
      }

      if (!directory.isDirectory()) {
        throw new ContactManagerShellException("The following file:" + directory + " is not a valid directory");
      }

      return directory;
    }

  public static ContactManagerPersistentService getContactManagerPersistentService() {
    if (bundleContext == null) {
      throw new ContactManagerShellException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(ContactManagerPersistentService.class.getName());

    if (srPS == null) {
      throw new ContactManagerShellException("The ServiceReference is null for ContactManagerPersistentService");
    }

    ContactManagerPersistentService persistentService = (ContactManagerPersistentService) bundleContext.getService(srPS);

    if (persistentService == null) {
      throw new ContactManagerShellException("The ContactManagerPersistentService is missing");
    }
    return persistentService;
  }


}
