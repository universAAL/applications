
package org.universaal.ontology.healthmeasurement;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.uAALModuleActivator;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;

public class HealthMeasruementActivator implements uAALModuleActivator {

  static ModuleContext context = null;
  HealthMeasurementOntology ontology = new HealthMeasurementOntology();

  public void start(ModuleContext context) throws Exception {
    HealthMeasruementActivator.context = context;
    OntologyManagement.getInstance().register(ontology);
  }

  public void stop(ModuleContext arg0) throws Exception {
    OntologyManagement.getInstance().unregister(ontology);
  }
}	
