
package org.universaal.ontology.healthmeasurement;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universaal.ontology.healthmeasurement.owl.*;

public class Activator implements BundleActivator {

  static BundleContext context = null;
  HealthMeasurementOntology ontology = new HealthMeasurementOntology();

  public void start(BundleContext context) throws Exception {
    Activator.context = context;
    OntologyManagement.getInstance().register(ontology);
  }

  public void stop(BundleContext arg0) throws Exception {
    OntologyManagement.getInstance().unregister(ontology);
  }
}	
