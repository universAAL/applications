package org.universAAL.ontology;


import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.drools.Consequence;
import org.universAAL.ontology.drools.ConsequenceProperty;
import org.universAAL.ontology.drools.DroolsReasoning;
import org.universAAL.ontology.drools.Fact;
import org.universAAL.ontology.drools.FactProperty;
import org.universAAL.ontology.drools.Rule;

public class DroolsReasoningFactory extends ResourceFactoryImpl {

	public Resource createInstance(String classURI, String instanceURI,
			int factoryIndex) {
		switch (factoryIndex) {
		case 0:
			return new Consequence(instanceURI);
		case 1:
			return new Fact(instanceURI);
		case 2:
			return new Rule(instanceURI);
		case 3:
			return new FactProperty(instanceURI);
		case 4:
			return new ConsequenceProperty(instanceURI);
		case 5:
			return new DroolsReasoning(instanceURI);
		}
		return null;
	}
}
