/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 Universidad Polit�cnica de Madrid
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

package org.universaal.ontology.owl;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.profile.ProfileOntology;
import org.universaal.ontology.QuestionnaireStrategyOntologyFactory;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.Treatment;

public class QuestionnaireStrategyOntology extends Ontology {

    private static QuestionnaireStrategyOntologyFactory factory = new QuestionnaireStrategyOntologyFactory();
    public static final String NAMESPACE = "http://ontology.universaal.org/QuestionnaireStrategyOntology#";

    public QuestionnaireStrategyOntology() {
	super(NAMESPACE);
    }

    public void create() {

	Resource r = getInfo();
	r
		.setResourceComment("The ontology defining the most general concepts dealing with questionnaires.");
	r.setResourceLabel("Questionnaire");

	addImport(DataRepOntology.NAMESPACE);
	addImport(PhThingOntology.NAMESPACE);
	addImport(ServiceBusOntology.NAMESPACE);
	addImport(ProfileOntology.NAMESPACE);
	addImport(HealthOntology.NAMESPACE);

	OntClassInfoSetup oci;

	// ******* Regular classes of the ontology ******* //

	// load Questionnaire4TreatmentStrategy

	oci = createNewOntClassInfo(Questionnaire4TreatmentStrategy.MY_URI,
		factory, 0);
	oci.setResourceComment("");
	oci.setResourceLabel("Questionnaire4TreatmentStrategy");
	oci.addSuperClass(ManagedIndividual.MY_URI);

	oci
		.addObjectProperty(
			Questionnaire4TreatmentStrategy.PROP_IS_ASSOCIATED_TO_TREATMENT)
		.setFunctional();
	oci
		.addRestriction(MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				Questionnaire4TreatmentStrategy.PROP_IS_ASSOCIATED_TO_TREATMENT,
				Treatment.MY_URI, 1, 1));

    }

}
