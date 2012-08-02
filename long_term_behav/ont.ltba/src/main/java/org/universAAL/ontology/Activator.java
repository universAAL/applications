package org.universAAL.ontology;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.owl.OntologyManagement;

/**
 * Activator class of the LTBA Ontology.
 * 
 * @author mllorente
 * 
 */
public class Activator implements BundleActivator {

	LTBAOntology ltba = new LTBAOntology();

	public void start(BundleContext context) throws Exception {
		OntologyManagement.getInstance().register(ltba);
	}

	public void stop(BundleContext context) throws Exception {
		OntologyManagement.getInstance().unregister(ltba);
	}

}
