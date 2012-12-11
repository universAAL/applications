
package org.universaal.ontology.disease;

import org.universAAL.middleware.container.ModuleActivator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universaal.ontology.disease.owl.DiseaseOntology;

public class DeseaseActivator implements ModuleActivator {

	static ModuleContext context = null;
	DiseaseOntology ontology = new DiseaseOntology();

	public void start(ModuleContext arg0) throws Exception {
		context = arg0;
		OntologyManagement.getInstance().register(ontology);

	}

	public void stop(ModuleContext arg0) throws Exception {
		OntologyManagement.getInstance().unregister(ontology);

	}
}	
