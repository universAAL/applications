
package org.universaal.ontology.disease.owl;

import org.universAAL.middleware.owl.BoundingValueRestriction;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.Device;
import org.universaal.ontology.disease.DiseaseFactory;


/**
 * @author AAL Studio 
 */
public final class DiseaseOntology extends Ontology {

  private static DiseaseFactory factory = new DiseaseFactory();
  public static final String NAMESPACE ="http://ontology.universaal.org/Disease#";
	
  public DiseaseOntology() {
    super(NAMESPACE);
  }

  public void create() {
    Resource r = getInfo();
    r.setResourceComment("");
    r.setResourceLabel("Disease");
    addImport(DataRepOntology.NAMESPACE);
    addImport(ServiceBusOntology.NAMESPACE);
    addImport(LocationOntology.NAMESPACE);
		
    


    // ******* Declaration of regular classes of the ontology ******* //
    OntClassInfoSetup oci_EnvironmentalDisease = createNewOntClassInfo(EnvironmentalDisease.MY_URI, factory, 0);
    OntClassInfoSetup oci_Disease = createNewOntClassInfo(Disease.MY_URI, factory, 1);


    // ******* Add content to regular classes of the ontology ******* //
    oci_EnvironmentalDisease.setResourceComment("");
    oci_EnvironmentalDisease.setResourceLabel("EnvironmentalDisease");
    oci_EnvironmentalDisease.addSuperClass(Disease.MY_URI); 
    oci_Disease.setResourceComment("");
    oci_Disease.setResourceLabel("Disease");
    oci_Disease.addSuperClass(ManagedIndividual.MY_URI); 
  }
}
