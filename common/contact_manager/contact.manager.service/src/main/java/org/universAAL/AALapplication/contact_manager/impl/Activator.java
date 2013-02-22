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


package org.universAAL.AALapplication.contact_manager.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.AALapplication.contact_manager.persistence.layer.ContactManagerPersistentService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author George Fournadjiev
 */
public class Activator implements BundleActivator {

  public static ModuleContext mc;
  public static BundleContext bundleContext;
  public static final Logger LOGGER = LoggerFactory.getLogger(Activator.class);

  /*
    * (non-Javadoc)
    * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
    */
  public void start(final BundleContext context) throws Exception {

    bundleContext = context;

    mc = uAALBundleContainer.THE_CONTAINER
        .registerModule(new Object[]{context});
    Log.info("Starting %s", getClass(), "Starting the Medication Service");
    new Thread() {
      public void run() {
        new AddContactServiceProvider(mc);
      }
    }.start();

    System.out.println("contact.manager.service started");

  }

  /*
    * (non-Javadoc)
    * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
    */
  public void stop(BundleContext context) throws Exception {

    bundleContext = null;
  }

  public static ContactManagerPersistentService getContactManagerPersistentService() {
      if (bundleContext == null) {
        throw new ContactManagerException("The bundleContext is not set");
      }

      ServiceReference srPS = bundleContext.getServiceReference(ContactManagerPersistentService.class.getName());

      if (srPS == null) {
        throw new ContactManagerException("The ServiceReference is null for ContactManagerPersistentService");
      }

      ContactManagerPersistentService persistentService = (ContactManagerPersistentService) bundleContext.getService(srPS);

      if (persistentService == null) {
        throw new ContactManagerException("The PersistentService is missing");
      }
      return persistentService;
    }

}
