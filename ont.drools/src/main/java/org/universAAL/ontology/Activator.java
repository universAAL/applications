package org.universAAL.ontology;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.owl.OntologyManagement;

public class Activator implements BundleActivator{

	DroolsReasoningOntology dro = new DroolsReasoningOntology();
	
	public void start(BundleContext arg0) throws Exception {
		OntologyManagement.getInstance().register(dro);
	}

	public void stop(BundleContext arg0) throws Exception {
		OntologyManagement.getInstance().unregister(dro);
		
	}

}
