package org.universAAL.AALapplication.medication_manager.configuration.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.ontology.vcard.Tel;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {


  public static ModuleContext mc;

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    Tel tel = new Tel();

    tel.setProperty(Tel.PROP_VALUE, "1234567890");
  }

  public void stop(BundleContext context) throws Exception {

  }

}
