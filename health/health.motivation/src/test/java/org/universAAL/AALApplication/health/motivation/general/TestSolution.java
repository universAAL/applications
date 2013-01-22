package org.universAAL.AALApplication.health.motivation.general;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationInterface;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.profile.ProfileOntology;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.questionnaire.Answer;
import org.universAAL.ontology.questionnaire.AnsweredQuestionnaire;
import org.universAAL.ontology.questionnaire.ChoiceLabel;
import org.universAAL.ontology.questionnaire.Question;
import org.universAAL.ontology.questionnaire.Questionnaire;
import org.universAAL.ontology.questionnaire.QuestionnaireOntology;
import org.universAAL.ontology.questionnaire.SingleChoiceQuestion;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.ontology.space.SpaceOntology;
import org.universaal.ontology.disease.owl.DiseaseOntology;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MessageOntology;

public class TestSolution implements MotivationInterface{

	public User getAssistedPerson() {
		// TODO Auto-generated method stub
		return null;
	}

	public User getCaregiver(User assistedPerson) {
		// TODO Auto-generated method stub
		return null;
	}

	public HealthProfile getHealthProfile(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerClassesNeeded() {
		OntologyManagement.getInstance().register(new DataRepOntology());
		OntologyManagement.getInstance().register(new ServiceBusOntology());
		OntologyManagement.getInstance().register(new UIBusOntology());
		OntologyManagement.getInstance().register(new LocationOntology());
		OntologyManagement.getInstance().register(new ShapeOntology());
		OntologyManagement.getInstance().register(new PhThingOntology());
		OntologyManagement.getInstance().register(new SpaceOntology());
		OntologyManagement.getInstance().register(new ProfileOntology());//hay otra
		OntologyManagement.getInstance().register(new QuestionnaireOntology());//hay otra
		OntologyManagement.getInstance().register(new DiseaseOntology());
		OntologyManagement.getInstance().register(new HealthMeasurementOntology());
		OntologyManagement.getInstance().register(new HealthOntology());
		OntologyManagement.getInstance().register(new MessageOntology());
		
		
	}
	
	public Questionnaire createQuestionnaire(){
		
		Questionnaire questionnaire = new Questionnaire ("Cuestionario de test", "Este cuestionario sirve para probar la soluci�n a un test con preguntas con respuesta correcta");
		
		String questionWording1 = "�Es bueno fumar?";
		String questionWording2 = "�Es bueno hacer ejercicio?";
		
		ChoiceLabel choiceYES = new ChoiceLabel(Boolean.TRUE, "S�");
		ChoiceLabel choiceNO = new ChoiceLabel(Boolean.FALSE, "No");
		
		ChoiceLabel[] choices = {choiceYES, choiceNO};
		
		SingleChoiceQuestion q1 = new SingleChoiceQuestion(questionWording1, Boolean.FALSE, TypeMapper.getDatatypeURI(Boolean.class), choices);
		SingleChoiceQuestion q2 = new SingleChoiceQuestion(questionWording2, Boolean.TRUE, TypeMapper.getDatatypeURI(Boolean.class), choices);		
				
		Question[] questions = {q1,q2};
		questionnaire.setQuestions(questions);
		
		return questionnaire;
	}
	
	@Before
	public void setUp(){
		registerClassesNeeded();
	}
//	@Test
//	public void testSolution(){
//		Questionnaire q = createQuestionnaire();
//		Solution solution = new Solution();
//		AnsweredQuestionnaire testSolution = solution.getSolution(q);
//		
//		Answer[] testAnswers = testSolution.getAnswers();
//		
//		Object[] answerContent1 = testAnswers[0].getAnswerContent();
//		Object[] answerContent2 = testAnswers[1].getAnswerContent();
//		
//		Assert.assertEquals(Boolean.FALSE, answerContent1[0]);	
//		Assert.assertEquals(Boolean.TRUE, answerContent2[0]);	
//	}
}
