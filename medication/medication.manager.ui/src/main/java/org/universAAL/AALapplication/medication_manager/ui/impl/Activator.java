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


package org.universAAL.AALapplication.medication_manager.ui.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;


public class Activator implements BundleActivator {

  static ModuleContext mc;
  static BundleContext context;
  public ReminderDialogProvider service;
  public MedicationManagerServiceButtonProvider medicationManagerServiceButtonProvider;

  public void start(BundleContext bundleContext) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER
        .registerModule(new Object[]{bundleContext});
    context = bundleContext;
    service = new ReminderDialogProvider(mc);
    medicationManagerServiceButtonProvider = new MedicationManagerServiceButtonProvider(mc);

  }


  public void stop(BundleContext arg0) throws Exception {
    // TODO Auto-generated method stub

  }

  public static PersistentService getPersistentService() {
    if (context == null) {
      throw new MedicationManagerUIException("The bundleContext is not set");
    }

    ServiceReference srPS = context.getServiceReference(PersistentService.class.getName());

    if (srPS == null) {
      throw new MedicationManagerUIException("The ServiceReference is null for PersistentService");
    }

    PersistentService persistentService = (PersistentService) context.getService(srPS);

    if (persistentService == null) {
      throw new MedicationManagerUIException("The PersistentService is missing");
    }
    return persistentService;
  }

  public static void validateParameter(int parameter, String parameterName) {

      if (parameter <= 0) {
        throw new MedicationManagerUIException("The parameter : " +
            parameterName + " must be positive number");
      }

    }

    public static void validateParameter(Object parameter, String parameterName) {

      if (parameter == null) {
        throw new MedicationManagerUIException("The parameter : " + parameterName + " cannot be null");
      }

    }

}
