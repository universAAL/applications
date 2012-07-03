package org.universAAL.AALapplication.ont;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.AALapplication.ont.foodDevices.ShoppingOntology;


public class Activator implements BundleActivator {

    static BundleContext context = null;
    ShoppingOntology shoppingOntology = new ShoppingOntology();

    public void start(BundleContext context) throws Exception {
	Activator.context = context;
	OntologyManagement.getInstance().register(shoppingOntology);
    }

    public void stop(BundleContext arg0) throws Exception {
	OntologyManagement.getInstance().unregister(shoppingOntology);
    }
}
