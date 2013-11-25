package org.universAAL.AALapplication.medication_manager.client.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  }

  /*
    * (non-Javadoc)
    * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
    */
  public void stop(BundleContext context) throws Exception {
  }
}
