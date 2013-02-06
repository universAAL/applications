package org.universAAL.ontology;

import org.universAAL.middleware.container.ModuleActivator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.Safety.SafetyOntology;

public class SafetyActivator implements ModuleActivator {

    static ModuleContext context = null;
    SafetyOntology safetyOntology = new SafetyOntology();

    public void start(ModuleContext context) throws Exception {
	SafetyActivator.context = context;
	OntologyManagement.getInstance().register(safetyOntology);
    }

    public void stop(ModuleContext arg0) throws Exception {
	OntologyManagement.getInstance().unregister(safetyOntology);
    }
}
