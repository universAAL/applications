package org.universAAL.ontology;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.ltba.LTBAService;

/**
 * Factory for the LTBA Ontology.
 * 
 * @author mllorente
 * 
 */
public class LTBAFactory extends ResourceFactoryImpl {

	public Resource createInstance(String classURI, String instanceURI,
			int factoryIndex) {
		switch (factoryIndex) {
		case 0:
			return new LTBAService(instanceURI);
		}
		return null;
	}

}
