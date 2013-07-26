package org.universAAL.AALapplication.medication_manager.event.issuer.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
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

  private static ServiceTracker serviceTrackerProperties;
  private static ServiceTracker serviceTrackerPersistence;
  public static ModuleContext mc;

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

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

}
