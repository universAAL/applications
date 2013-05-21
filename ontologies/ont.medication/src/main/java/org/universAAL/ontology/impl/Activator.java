/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
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

package org.universAAL.ontology.impl;

import org.universAAL.middleware.container.ModuleActivator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.medMgr.MedicationOntology;

/**
 * @author George Fournadjiev
 */

public class Activator implements ModuleActivator {

    public static ModuleContext mc;
    MedicationOntology medicationOntology = new MedicationOntology();

    public void start(ModuleContext mcontext) throws Exception {
	// Activator.context = context;
	// mc = uAALBundleContainer.THE_CONTAINER
	// .registerModule(new Object[]{mcontext});
	// Log.info("Registering %s", Activator.class,
	// "The medication ontology");
	OntologyManagement.getInstance().register(mcontext, medicationOntology);
    }

    public void stop(ModuleContext mcontext) throws Exception {
	OntologyManagement.getInstance().unregister(mcontext,
		medicationOntology);
    }

}
