
package org.universaal.ontology.disease;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;

import org.universaal.ontology.disease.owl.*;

import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.*;
import org.universaal.ontology.ICD10Diseases.owl.*;

public class DiseaseFactory extends ResourceFactoryImpl {


  public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {

	switch (factoryIndex) {
     case 0:
    	 return new Disease(instanceURI);
     case 1:
    	 return new Epidemiology(instanceURI);
     case 2:
    	 return new Etiology (instanceURI);
     case 3:
    	 return new Patogeny(instanceURI);
     case 4:
    	 return new Sympthom(instanceURI);
     case 5:
         return new Pronostic (instanceURI);
     case 6:
         return new Diagnostic (instanceURI);
     case 7:
         return new  CertainInfectiousParasiticDisease(instanceURI);
     case 8:
         return new CirculatorySystemDisease(instanceURI);
     case 9:
    	 return new DigestiveSystemDisease(instanceURI);
     case 10:
         return new MentalDisorder(instanceURI);
     case 11:
    	 return new Neoplasms(instanceURI);
     case 12:
         return new NervousSystemDisease(instanceURI);
     case 13:
    	 return new RespiratorySystemDisease(instanceURI);
     case 14:
         return new AcuteRheumaticFever(instanceURI);
     case 15:
    	 return new HeartFailure(instanceURI);
     case 16:
         return new HypertensiveDisease(instanceURI);
     case 17:
    	 return new IschemicHeartDisease(instanceURI);
     case 18:
    	 return new OtherCirculatorySystemDisease(instanceURI);

	}

	return null;
  }
}
