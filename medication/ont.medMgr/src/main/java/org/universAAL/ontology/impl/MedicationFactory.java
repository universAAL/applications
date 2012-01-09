package org.universAAL.ontology.impl;


import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.medMgr.Precaution;

/**
 * @author George Fournadjiev
 */
public final class MedicationFactory extends ResourceFactoryImpl {


  public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {

    if (factoryIndex == 0) {
      return new Precaution(instanceURI);
    }

    return null;
  }
}
