package org.universaal.ontology.owl;

import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.questionnaire.*;
import org.universaal.ontology.owl.QuestionnaireStrategyOntology;

public class Questionnaire4TreatmentStrategy extends Questionnaire {

    // NAMESPACE & PROPERTIES

    public static final String MY_URI = QuestionnaireStrategyOntology.NAMESPACE
	    + "Questionnaire4TreatmentStrategy";
    public static final String PROP_IS_ASSOCIATED_TO_TREATMENT = QuestionnaireOntology.NAMESPACE
	    + "associatedTreatment";

    // CONSTRUCTORS
    public Questionnaire4TreatmentStrategy() {
	super();
    }

    public Questionnaire4TreatmentStrategy(Treatment t, String name,
	    String description, Question[] questions) {
	super(name, description, questions);
	this.setAssociatedTreatment(t);
    }

    public Questionnaire4TreatmentStrategy(Treatment t, String name,
	    String description, Question question) {
	super(name, description, question);
	this.setAssociatedTreatment(t);
    }

    public Questionnaire4TreatmentStrategy(Treatment t, String name,
	    String description, Question[] questions, boolean ordered) {
	super(name, description, questions, ordered);
	this.setAssociatedTreatment(t);
    }

    public Questionnaire4TreatmentStrategy(Treatment t, String name,
	    String description) {
	super(name, description);
	this.setAssociatedTreatment(t);
    }

    public Questionnaire4TreatmentStrategy(String instanceURI) {
	super(instanceURI);
    }

    public String getClassURI() {
	return MY_URI;
    }

    public int getPropSerializationType(String arg0) {
	return PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return true && props.containsKey(PROP_IS_ASSOCIATED_TO_TREATMENT);
    }

    // GETTERS AND SETTERS

    public Treatment getAssociatedTreatment() {
	return (Treatment) props.get(PROP_IS_ASSOCIATED_TO_TREATMENT);
    }

    public void setAssociatedTreatment(Treatment treatment) {
	if (treatment != null)
	    props.put(PROP_IS_ASSOCIATED_TO_TREATMENT, treatment);
    }

}
