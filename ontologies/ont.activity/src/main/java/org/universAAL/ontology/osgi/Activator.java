package org.universAAL.ontology.osgi;

import org.universAAL.middleware.container.ModuleActivator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.activity.*;

public class Activator implements ModuleActivator {

    static ModuleContext context = null;

    ActivityOntology _activityontology = new ActivityOntology();

    public void start(ModuleContext context) throws Exception {
	Activator.context = context;

	OntologyManagement.getInstance().register(context, _activityontology);

    }

    public void stop(ModuleContext arg0) throws Exception {

	OntologyManagement.getInstance().unregister(context, _activityontology);

    }
}
