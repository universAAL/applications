/*******************************************************************************
 * Copyright 2013 Universidad Politécnica de Madrid
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
package org.universAAL.AALapplication.health.performedSession.manager.profiles;

import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universaal.ontology.health.owl.services.PerformedSessionManagementService;

/**
 * @author amedrano
 *
 */
public class PerformedSessionServiceProfilesOnt extends Ontology{

	public static final String NAMESPACE = "http://health.ontology.universaal.org/PerformedSessionManagementOntology#";

	private static final ResourceFactory factory = new Factory();
	
	/**
	 * 
	 */
	public PerformedSessionServiceProfilesOnt() {
		super(NAMESPACE);
	}

	/** {@inheritDoc} */
	@Override
	public void create() {
		OntClassInfoSetup oci;
		
		oci = createNewOntClassInfo(ListPerformedSessionBetweenTimeStampsService.MY_URI, factory, 0);
		oci.addSuperClass(PerformedSessionManagementService.MY_URI);
		
		oci = createNewOntClassInfo(ListPerformedSessionService.MY_URI, factory, 0);
		oci.addSuperClass(PerformedSessionManagementService.MY_URI);
		
		oci = createNewOntClassInfo(SessionPerformedService.MY_URI, factory, 0);
		oci.addSuperClass(PerformedSessionManagementService.MY_URI);
		
	}

	public static class Factory implements ResourceFactory {

		public Resource createInstance(String classURI, String instanceURI,
				int factoryIndex) {
			switch (factoryIndex) {
			case 0:
				return new ListPerformedSessionBetweenTimeStampsService(instanceURI);
			case 1:
				return new ListPerformedSessionService(instanceURI);
			case 2:
				return new SessionPerformedService(instanceURI);
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
