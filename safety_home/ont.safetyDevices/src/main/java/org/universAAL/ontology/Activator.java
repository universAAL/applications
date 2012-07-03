package org.universAAL.ontology;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.safetyDevices.SafetyOntology;


public class Activator implements BundleActivator {

    static BundleContext context = null;
    SafetyOntology safetyOntology = new SafetyOntology();

    public void start(BundleContext context) throws Exception {
	Activator.context = context;
	OntologyManagement.getInstance().register(safetyOntology);
    }

    public void stop(BundleContext arg0) throws Exception {
	OntologyManagement.getInstance().unregister(safetyOntology);
    }
}
