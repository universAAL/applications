/*******************************************************************************
 * Copyright 2011 Universidad Politécnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.AALapplication.personal_safety.sms.owl;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owl.ServiceBusOntology;


public class SMSOntology extends Ontology {

	private static final ResourceFactory factory = new SMSOntFactory();
	public static final String NAMESPACE ="http://ontology.universaal.org/SMSService#";
	
	public SMSOntology() {
		super(NAMESPACE);
	}

	public void create() {
		Resource r = getInfo();
	    r.setResourceComment("The ontology defining the health service, based on the treatment concept.");
	    r.setResourceLabel("HealthOntology");
	    addImport(DataRepOntology.NAMESPACE);
	    addImport(ServiceBusOntology.NAMESPACE);
	   
		OntClassInfoSetup oci;

		oci = createNewOntClassInfo(EnvioSMSService.MY_URI, factory, 1);
		oci.setResourceComment("A service that sends a SMS.");
	    oci.setResourceLabel("SMSSend");
	    oci.addSuperClass(Service.MY_URI);
	    
	    oci.addDatatypeProperty(EnvioSMSService.PROP_MANDA_TEXTO);
	    oci.addRestriction(MergedRestriction
	    		.getAllValuesRestriction(EnvioSMSService.PROP_MANDA_TEXTO,
	    				TypeMapper.getDatatypeURI(String.class)));
	    
	    oci.addDatatypeProperty(EnvioSMSService.PROP_TIENE_NUMERO);
	    oci.addRestriction(MergedRestriction
	    		.getAllValuesRestriction(EnvioSMSService.PROP_TIENE_NUMERO,
	    				TypeMapper.getDatatypeURI(String.class)));
	    
		oci = createNewOntClassInfo(SMSService.MY_URI, factory, 2);
		oci.setResourceComment("A service that sends a SMS.");
	    oci.setResourceLabel("SMSSendService");
	    oci.addSuperClass(EnvioSMSService.MY_URI);
	    
	    oci.addDatatypeProperty(SMSService.INPUT_TEXTO_MENSAJE)
	    	.addSuperProperty(EnvioSMSService.PROP_MANDA_TEXTO);
	    oci.addDatatypeProperty(SMSService.INPUT_TEXTO_TELEFONO)
	    	.addSuperProperty(EnvioSMSService.PROP_TIENE_NUMERO);	    

	}

}
