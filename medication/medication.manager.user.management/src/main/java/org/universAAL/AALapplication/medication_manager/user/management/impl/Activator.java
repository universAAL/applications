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

package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.configuration.Pair;
import org.universAAL.AALapplication.medication_manager.configuration.PropertyInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicationPropertiesDao;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {


  public static final String FALSE = "FALSE";
  public static ModuleContext mc;
  public static BundleContext bundleContext;
  static ServiceTracker configurationPropertiesServiceTracker;
  static ServiceTracker persistenceServiceTracker;


  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    configurationPropertiesServiceTracker = new ServiceTracker(context, ConfigurationProperties.class.getName(), null);
    persistenceServiceTracker = new ServiceTracker(context, PersistentService.class.getName(), null);

    configurationPropertiesServiceTracker.open();
    persistenceServiceTracker.open();

    PersistentService persistentService = getPersistentService();

    ConfigurationProperties configurationProperties = getConfigurationProperties();

    UserManager userManager = new UserManagerImpl(mc, persistentService, configurationProperties);


    context.registerService(UserManager.class.getName(), userManager, null);


    insertDummyUsers(userManager, persistentService);

  }

  private void insertDummyUsers(final UserManager userManager, final PersistentService persistentService) {
    Timer timer = new Timer();

    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        ConfigurationProperties configurationProperties = getConfigurationProperties();
        boolean insertDummyUsersIntoChe = configurationProperties.isInsertDummyUsersIntoChe();

        Log.info("Checking the configuration property for loading dummy users into CHE", getClass(), insertDummyUsersIntoChe);
        if (insertDummyUsersIntoChe) {
          Log.info("Inserting dummy users into CHE ", getClass());
          userManager.loadDummyUsersIntoChe();
          setInsertDummyUsersPropertyToFalse(persistentService);
        }
      }
    }, 7 * 1000);
  }

  private void setInsertDummyUsersPropertyToFalse(PersistentService persistentService) {
    Log.info("Setting medication.manager.insert.dummy.users.into.che property to false ", getClass());
    MedicationPropertiesDao medicationPropertiesDao = persistentService.getMedicationPropertiesDao();

    PropertyInfo propertyInfo = medicationPropertiesDao.getProperty(ConfigurationProperties.MEDICATION_MANAGER_INSERT_DUMMY_USERS_INTO_CHE);

    Pair<Integer, String> pair = new Pair<Integer, String>(propertyInfo.getId(), FALSE);
    medicationPropertiesDao.updatePropertyValue(pair);

    medicationPropertiesDao.setSystemPropertiesLoadedFromDatabase();

    Log.info("medication.manager.insert.dummy.users.into.che property has been set to false ", getClass());

  }

  public void stop(BundleContext context) throws Exception {

  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (configurationPropertiesServiceTracker == null) {
      throw new MedicationManagerUserManagementException("The ConfigurationProperties ServiceTracker is not set");
    }
    ConfigurationProperties service = (ConfigurationProperties) configurationPropertiesServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerUserManagementException("The ConfigurationProperties is missing");
    }

    return service;
  }

  public static PersistentService getPersistentService() {
    if (persistenceServiceTracker == null) {
      throw new MedicationManagerUserManagementException("The PersistentService ServiceTracker is not set");
    }
    PersistentService service = (PersistentService) persistenceServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerUserManagementException("The PersistentService is missing");
    }

    return service;
  }


}
