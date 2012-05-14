package org.universAAL.ontology.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.medMgr.MedicationOntology;


/**
 * @author George Fournadjiev
 */

public class Activator implements BundleActivator {

  public static ModuleContext mc;
  static BundleContext context = null;
  MedicationOntology medicationOntology = new MedicationOntology();

  public void start(BundleContext context) throws Exception {
    Activator.context = context;
    mc = uAALBundleContainer.THE_CONTAINER
        .registerModule(new Object[]{context});
    Log.info("Registering %s", Activator.class, "The medication ontology");
    OntologyManagement.getInstance().register(medicationOntology);
  }

  public void stop(BundleContext arg0) throws Exception {
    OntologyManagement.getInstance().unregister(medicationOntology);
  }

}
