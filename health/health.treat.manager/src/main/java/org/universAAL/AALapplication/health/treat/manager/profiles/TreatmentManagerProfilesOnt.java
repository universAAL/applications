/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 * Universidad Politécnica de Madrid
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
package org.universAAL.AALapplication.health.treat.manager.profiles;

import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universaal.ontology.health.owl.services.TreatmentManagementService;

/**
 * @author amedrano
 *
 */
public class TreatmentManagerProfilesOnt extends Ontology {

	  public static final String NAMESPACE ="http://health.ontology.universaal.org/TreatmentManagementOntology#";
	
	  private static final ResourceFactory factory = new Factory();
	  
	  public TreatmentManagerProfilesOnt() {
		    super(NAMESPACE);
		  }

	/** {@inheritDoc} */
	@Override
	public void create() {
		OntClassInfoSetup oci;
		
		oci = createNewOntClassInfo(EditTreatmentService.MY_URI, factory, 0);
		oci.addSuperClass(TreatmentManagementService.MY_URI);
		
		oci = createNewOntClassInfo(ListTreatmentBetweenTimeStampsService.MY_URI, factory, 1);
		oci.addSuperClass(TreatmentManagementService.MY_URI);
		
		oci = createNewOntClassInfo(ListTreatmentService.MY_URI, factory, 2);
		oci.addSuperClass(TreatmentManagementService.MY_URI);

		oci = createNewOntClassInfo(NewTreatmentService.MY_URI, factory, 4);
		oci.addSuperClass(TreatmentManagementService.MY_URI);
		
		oci = createNewOntClassInfo(RemoveTreatmentService.MY_URI, factory, 5);
		oci.addSuperClass(TreatmentManagementService.MY_URI);
	}

	public static class Factory implements ResourceFactory {

		public Resource createInstance(String classURI, String instanceURI,
				int factoryIndex) {
			switch (factoryIndex) {
			case 0:
				return new EditTreatmentService(instanceURI);
			case 1:
				return new ListTreatmentBetweenTimeStampsService(instanceURI);
			case 2:
				return new ListTreatmentService(instanceURI);
			case 3:
				return new NewTreatmentService(instanceURI);
			case 4:
				return new RemoveTreatmentService(instanceURI);

			default:
				break;
			}
			return null;
		}

		public Resource castAs(Resource r, String classURI) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
