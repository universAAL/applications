/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
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
 *****************************************************************************************/

package org.universAAL.ontology;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.ModuleActivator;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.Safety.SafetyOntology;

/**
 * @author dimokas
 * 
 */

/*
public class SafetyActivator implements BundleActivator {

    static BundleContext context = null;
    SafetyOntology safetyOntology = new SafetyOntology();

    public void start(BundleContext context) throws Exception {
	SafetyActivator.context = context;
	OntologyManagement.getInstance().register(safetyOntology);
    }

    public void stop(BundleContext arg0) throws Exception {
	OntologyManagement.getInstance().unregister(safetyOntology);
    }
}
*/

public class SafetyActivator implements ModuleActivator {
	public static ModuleContext context;
    SafetyOntology safetyOntology = new SafetyOntology();

    public void start(ModuleContext context) throws Exception {
    	//SafetyActivator.context = context;
    	OntologyManagement.getInstance().register(safetyOntology);
    }

    public void stop(ModuleContext arg0) throws Exception {
		OntologyManagement.getInstance().unregister(safetyOntology);
    }
}
