
package org.universaal.ontology.disease;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universaal.ontology.disease.owl.*;

public class DiseaseFactory extends ResourceFactoryImpl {


  public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {

	switch (factoryIndex) {
     case 0:
       return new EnvironmentalDisease(instanceURI);
     case 1:
       return new Disease(instanceURI);

	}
	return null;
  }
}
