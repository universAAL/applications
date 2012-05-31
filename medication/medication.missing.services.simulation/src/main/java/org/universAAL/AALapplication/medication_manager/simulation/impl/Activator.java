package org.universAAL.AALapplication.medication_manager.simulation.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.AALapplication.medication_manager.simulation.DispenserUpsideDownContextProvider;
import org.universAAL.AALapplication.medication_manager.simulation.MedicationConsumer;
import org.universAAL.AALapplication.medication_manager.simulation.MedicationReminderContextProvider;
import org.universAAL.AALapplication.medication_manager.simulation.MissedIntakeContextProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author George Fournadjiev
 */
public class Activator implements BundleActivator {

  public static ModuleContext mc;

  public static final Logger logger = LoggerFactory.getLogger(Activator.class);

  /*
    * (non-Javadoc)
    * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
    */
  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    new Thread() {
      public void run() {
        new MedicationConsumer(mc);
        new MissedIntakeContextProvider(mc);
        new CaregiverNotificationProvider(mc);
        new MedicationReminderContextProvider(mc);
        new DispenserUpsideDownContextProvider(mc);
      }
    }.start();

  }

  /*
    * (non-Javadoc)
    * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
    */
  public void stop(BundleContext context) throws Exception {
  }
}
