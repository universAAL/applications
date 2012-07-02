package org.universAAL.ontology.drools;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.DroolsReasoningOntology;

public class DroolsReasoning extends Service{
	
	public static final String MY_URI = DroolsReasoningOntology.NAMESPACE + "DroolsReasoningService";

	public static final String PROP_KNOWS_FACTS = DroolsReasoningOntology.NAMESPACE+"knowsFacts";
	public static final String PROP_KNOWS_RULES = DroolsReasoningOntology.NAMESPACE+"knowsRules";
	public static final String PROP_PRODUCES_CONSEQUENCES = DroolsReasoningOntology.NAMESPACE+"producesConsequences";
	//public static final String PROP_RECEIVES_CONTEXT_EVENTS = "receivesContextEvents";
	
	
	public DroolsReasoning(String instanceURI) {
		super(instanceURI);
	}


	public DroolsReasoning() {
		super();
	}

	  public String getClassURI(){
			return MY_URI;  
		  }

}
