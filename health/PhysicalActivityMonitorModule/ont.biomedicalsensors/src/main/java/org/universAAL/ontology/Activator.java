package org.universAAL.ontology;

/**
 * @author billyk, joemoul
 * 
 */
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.owl.OntologyManagement;

public class Activator implements BundleActivator {

	static BundleContext context = null;
	BiomedicalSensorsOntology ont = new BiomedicalSensorsOntology();

	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		OntologyManagement.getInstance().register(ont);
	}

	public void stop(BundleContext context) throws Exception {
		OntologyManagement.getInstance().unregister(ont);
	}

}