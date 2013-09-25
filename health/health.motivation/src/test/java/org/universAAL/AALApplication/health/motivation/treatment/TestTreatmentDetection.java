package org.universAAL.AALApplication.health.motivation.treatment;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import junit.framework.Assert;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.event.rule.ActivationCancelledEvent;
import org.drools.event.rule.ActivationCreatedEvent;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.AgendaGroupPoppedEvent;
import org.drools.event.rule.AgendaGroupPushedEvent;
import org.drools.event.rule.BeforeActivationFiredEvent;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
import org.universAAL.AALApplication.health.motivation.testSupportClasses.DetectedTreatments;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalQuestionnaire;
import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.Diet;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.questionnaire.*;

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
	private MotivationServiceRequirementsIface iTestTreatmentForMMVariables = this;


	// Agreement questionnaire sent to the assisted person
	private String qWording = "Good $partOfDay $userName! A new treatment, named $treatmentName has been asigned to you. This treatment consists of $treatmentDescription. Do you plan to follow it?";

	private ChoiceLabel choice1;
	private ChoiceLabel choice2;
	private ChoiceLabel[] booleanChoices;

	private SingleChoiceQuestion agreementQuestion;

	private Questionnaire questionnaire;
	private MotivationalQuestionnaire firstQuestionnaire;


	@Before
	public void setUp() throws Exception{
		registerClassesNeeded();

		TestIface.resetMessagesSent();
		//load up the knowledge base
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/TreatmentRules.drl"), ResourceType.DRL);
		
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		ksession = kbase.newStatefulKnowledgeSession();

		MessageManager.setLanguage(Locale.ENGLISH);
		MessageManager.setMMSenderIface(iTestTreatment); //para las pruebas, usamos la interfaz de pruebas
		MessageManager.buildMapStructure();
		MessageVariables.setMotivationServiceRequirementsIface(iTestTreatmentForMMVariables);
		MessageManager.buildInitialMapOfVariables();

		//load the facts
		treatment1 = new Diet ("Low fat diet", "eating less fatty", HeartFailure.MY_URI); //valid treatment
		treatment2 = new Diet ("Low salt diet", "eating less salty", HeartFailure.MY_URI);//valid treatment
		treatment3 = new Diet ("", "treatment 3 description", HeartFailure.MY_URI);// invalid treatment

		// Agreement questionnaire sent to the assisted person

		choice1 = new ChoiceLabel(Boolean.TRUE, "Yes");
		choice2 = new ChoiceLabel(Boolean.FALSE, "No");
		booleanChoices = new ChoiceLabel[2];
		booleanChoices[0]=choice1;
		booleanChoices[1]=choice2;
		agreementQuestion = new SingleChoiceQuestion(qWording,TypeMapper.getDatatypeURI(Boolean.class),booleanChoices);

		questionnaire = new Questionnaire ("First inquiry", 
				"This message will be displayed whenever a new treatment is detected, to ask the user" + 
				" about his/her predisposition to follow the treatment.", agreementQuestion); 

		firstQuestionnaire = new MotivationalQuestionnaire(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, MotivationalMessageSubclassification.treatment_agreement, questionnaire);

		// load global variables
		ksession.setGlobal("agreementQuestion",agreementQuestion);
		ksession.setGlobal("firstQuestionnaire",firstQuestionnaire);

		//insert the facts in drools working memory
		ksession.insert(treatment1);
		ksession.insert(treatment2);
		ksession.insert(treatment3);
		ksession.addEventListener(new AgendaEventListener() {
			
			public void beforeActivationFired(BeforeActivationFiredEvent arg0) {
			}
			
			public void agendaGroupPushed(AgendaGroupPushedEvent arg0) {
			}
			
			public void agendaGroupPopped(AgendaGroupPoppedEvent arg0) {
			}
			
			public void afterActivationFired(AfterActivationFiredEvent arg0) {
				System.out.println("RULE ACTIVATED !!! "+arg0.getActivation().getRule().getName());
			}
			
			public void activationCreated(ActivationCreatedEvent arg0) {
			}
			
			public void activationCancelled(ActivationCancelledEvent arg0) {
			}
		});
		//fire the rules
		ksession.fireAllRules();	
	}

	@After
	public void tearDown()
	{
		TestIface.resetMessagesSent();
	}

	@Test
	public void testCorrectNumberOfMessagesSent(){
		Assert.assertEquals(true, TestIface.motivationalMessagesSentToAP.size()==2);

		String qNameExpected = "First inquiry"; 
		Assert.assertEquals(true, TestIface.sentToAPContainsQuestionnaire(qNameExpected));
		
		ArrayList <Treatment> detectedTreatments = DetectedTreatments.getDetectedTreatments();

		Assert.assertEquals(true, detectedTreatments.size()==2); 

		Assert.assertEquals(true, detectedTreatments.contains(treatment1));
		Assert.assertEquals(true, detectedTreatments.contains(treatment2));
		Assert.assertEquals(false, detectedTreatments.contains(treatment3));
	}


}


