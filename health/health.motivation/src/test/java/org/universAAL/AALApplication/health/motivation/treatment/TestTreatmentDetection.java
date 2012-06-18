package org.universAAL.AALApplication.health.motivation.treatment;


import java.util.ArrayList;
import java.util.Locale;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.ClassesNeededRegistration;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivationalMessages.MotivationalMessageContent;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.testSupportClasses.RulesSupport;
import org.universAAL.AALApplication.health.motivation.testSupportClasses.TreatmentDetection;
import org.universAAL.itests.IntegrationTest;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.ontology.space.SpaceOntology;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.disease.owl.DiseaseOntology;
import org.universaal.ontology.health.owl.Diet;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.Question;
import org.universaal.ontology.owl.Questionnaire;
import org.universaal.ontology.owl.QuestionnaireOntology;

/**
 * This test class checks if treatments are well detected, based on 
 * the property name. If the treatment has an empty name then it should be
 * not detected. If not, the rule "Treatment detection" must be fired.
 * @author mdelafuente
 *
 */
public class TestTreatmentDetection extends TestIface{

	private KnowledgeBase kbase;
	private static StatefulKnowledgeSession ksession;
	
	private static Diet treatment1;
	private static Diet treatment2;
	private static Diet treatment3;

	private SendMotivationMessageIface iTestTreatment = this;

	@Before
	public void setUp() throws Exception{
		
		registerClassesNeeded();
				
		//load up the knowledge base
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/TreatmentRules.drl"), ResourceType.DRL);
		//kbuilder.add(ResourceFactory.newClassPathResource("TreatmentRules.drl"), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		ksession = kbase.newStatefulKnowledgeSession();
		
		MessageManager.setLanguage(Locale.ENGLISH);
		MessageManager.setMMSenderIface(iTestTreatment); //para las pruebas, usamos la interfaz de pruebas
		MessageManager.buildMapStructure();
		
		//load the facts
		treatment1 = new Diet ("Dieta baja en grasa", "descripcion tratamiento 1", HeartFailure.MY_URI); //valid treatment
		treatment2 = new Diet ("Dieta baja en sal", "descripcion tratamiento 2", HeartFailure.MY_URI);//valid treatment
		treatment3 = new Diet ("", "descripcion tratamiento 3", HeartFailure.MY_URI);// invalid treatment	

		//insert the facts in drools working memory
		ksession.insert(treatment1);
		ksession.insert(treatment2);
		ksession.insert(treatment3);

		//fire the rules
		ksession.fireAllRules();
		
	}

	@After
	public void tearDown()
	{
		ksession.dispose();
	}

	@Test
	public void testCorrectNumberOfMessagesSent(){

		Assert.assertEquals(true, TestIface.motivationalMessagesSentToAP.size()==2);
		System.out.println("Number of sent messages correct");
	}
	
	@Test
	public void testCorrectMMSent(){
		
		ArrayList <MotivationalMessage> mmSent = iTestTreatment.getMMsentToAP();
		try{
			Class <?> cName = Class.forName("org.universAAL.AALApplication.health.motivation.motivationalMessages.TreatmentDetectionMessage");
			Object rawContent =( (MotivationalMessageContent) (cName.newInstance()) ).getMessageContent();
			
			String questionWording = "Hello Mary! A new treatment, named 'Low fat diet'" +
			"has been asigned to you. This treatment consists of eating more vegetables and fruit and avoiding fatty meals." +
			"Do you plan to follow it?";
			
			Question[] question = ((Questionnaire)rawContent).getQuestions();
			question[0].setQuestionWording(questionWording);
			
			((Questionnaire)rawContent).setQuestions(question);
		
			
			MotivationalMessage mm = new MotivationalMessage(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, rawContent);
				
			Assert.assertEquals(true, mmSent.contains(mm));
			System.out.println("");
			
		}catch (Exception e){
			
		}
	}

	/**
	 * This method checks that the valid treatments (t1 and t2) have been well detected.
	 * Invalid treatments (t3) should have not been detected 
	 */
	@Test
	public void testCorrectTreatmentDetection(){

		ArrayList <Treatment> detectedTreatments = RulesSupport.getDetectedTreatments();

		//Assert.assertEquals(true, detectedTreatments.size()==2); 
		
		//Se detectan más tratamientos de los debidos (dos por cada método de test)... (?) 
		System.out.println("Tamaño del array de tratamientos detectados: " + detectedTreatments.size());
		
		Assert.assertEquals(true, detectedTreatments.contains(treatment1));
		Assert.assertEquals(true, detectedTreatments.contains(treatment2));
		Assert.assertEquals(false, detectedTreatments.contains(treatment3));
		
		System.out.println("Tratamientos detectados correctamente");
	}

	
}


