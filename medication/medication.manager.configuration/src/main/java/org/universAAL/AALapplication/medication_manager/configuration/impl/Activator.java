package org.universAAL.AALapplication.medication_manager.configuration.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {


  public static ModuleContext mc;

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

  }

  public void stop(BundleContext context) throws Exception {

  }

}
