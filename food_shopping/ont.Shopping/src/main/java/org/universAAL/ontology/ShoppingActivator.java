package org.universAAL.ontology;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.Shopping.ShoppingOntology;

public class ShoppingActivator implements BundleActivator {

    static BundleContext context = null;
    ShoppingOntology shoppingOntology = new ShoppingOntology();

    public void start(BundleContext context) throws Exception {
	ShoppingActivator.context = context;
	OntologyManagement.getInstance().register(shoppingOntology);
    }

    public void stop(BundleContext arg0) throws Exception {
	OntologyManagement.getInstance().unregister(shoppingOntology);
    }
}
