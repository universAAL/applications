package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
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

    UserManager userManager = new UserManagerImpl(mc, persistentService);


    context.registerService(UserManager.class.getName(),
        userManager, null);


    insertDummyUsers(userManager);

  }

  private void insertDummyUsers(final UserManager userManager) {
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
        }
      }
    }, 7 * 1000);
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
