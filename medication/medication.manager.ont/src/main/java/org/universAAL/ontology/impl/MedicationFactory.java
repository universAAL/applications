package org.universAAL.ontology.impl;


import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.medMgr.DueIntake;
import org.universAAL.ontology.medMgr.MissedIntake;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.Time;

/**
 * @author George Fournadjiev
 */
public final class MedicationFactory extends ResourceFactoryImpl {


  public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {

    if (factoryIndex == 0) {
      return new Precaution(instanceURI);
    } else if (factoryIndex == 1) {
      return new Time(instanceURI);
    } else if (factoryIndex == 2) {
      return new MissedIntake(instanceURI);
    } else if (factoryIndex == 3) {
      return new DueIntake(instanceURI);
    }

    return null;
  }
}
