package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {


  public static ModuleContext mc;
  public static BundleContext bundleContext;


  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    PersistentService persistentService = getPersistentService();

    UserManager userManager = new UserManagerImpl(mc, persistentService);


    context.registerService(UserManager.class.getName(),
        userManager, null);


    ConfigurationProperties configurationProperties = getConfigurationProperties();

    boolean insertDummyUsersIntoChe = configurationProperties.isInsertDummyUsersIntoChe();

    Log.info("Checking the configuration property for loading dummy users into CHE", getClass(), insertDummyUsersIntoChe);

    if (insertDummyUsersIntoChe) {
      insertDummyUsers(userManager);
    }

  }

  private void insertDummyUsers(final UserManager userManager) {
    Timer timer = new Timer();

    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        Log.info("Inserting dummy users into CHE ", getClass());
        userManager.loadDummyUsersIntoChe();
      }
    }, 7 * 1000);
  }

  public void stop(BundleContext context) throws Exception {

  }

  public static PersistentService getPersistentService() {
    if (bundleContext == null) {
      throw new MedicationManagerUserManagementException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(PersistentService.class.getName());

    if (srPS == null) {
      throw new MedicationManagerUserManagementException("The ServiceReference is null for PersistentService");
    }

    PersistentService persistentService = (PersistentService) bundleContext.getService(srPS);

    if (persistentService == null) {
      throw new MedicationManagerUserManagementException("The PersistentService is missing");
    }
    return persistentService;
  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (bundleContext == null) {
      throw new MedicationManagerUserManagementException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(ConfigurationProperties.class.getName());

    if (srPS == null) {
      throw new MedicationManagerUserManagementException("The ServiceReference is null for ConfigurationProperties");
    }

    ConfigurationProperties service = (ConfigurationProperties) bundleContext.getService(srPS);

    if (service == null) {
      throw new MedicationManagerUserManagementException("The ConfigurationProperties is missing");
    }

    return service;
  }


}
