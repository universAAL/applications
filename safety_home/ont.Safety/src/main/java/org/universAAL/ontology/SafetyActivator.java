package org.universAAL.ontology;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.Safety.SafetyOntology;


public class SafetyActivator implements BundleActivator {

    static BundleContext context = null;
    SafetyOntology safetyOntology = new SafetyOntology();

    public void start(BundleContext context) throws Exception {
	SafetyActivator.context = context;
	OntologyManagement.getInstance().register(safetyOntology);
    }

    public void stop(BundleContext arg0) throws Exception {
	OntologyManagement.getInstance().unregister(safetyOntology);
    }
}
