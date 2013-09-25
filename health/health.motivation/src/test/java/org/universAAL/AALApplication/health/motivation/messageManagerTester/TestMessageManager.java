package org.universAAL.AALApplication.health.motivation.messageManagerTester;

import java.util.Locale;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.questionnaire.*;

public class TestMessageManager extends TestIfaceForMM{

	private SendMotivationMessageIface iTestForMM = this;
	private MotivationServiceRequirementsIface iTestForMMVariables = this;
	
	@Before
	public void setUp() throws Exception{
		TestIfaceForMM.registerClassesNeeded();
		
		MessageManager.setLanguage(Locale.ENGLISH);
		MessageManager.setMMSenderIface(iTestForMM);
		
		MessageManager.buildMapStructure();
		MessageVariables.setMotivationServiceRequirementsIface(iTestForMMVariables);
		MessageManager.buildInitialMapOfVariables();
		
		MessageVariables.addToMapOfVariables("treatmentName", "Diet");
		MessageVariables.addToMapOfVariables("treatmentDescription", "eating 5 pieces of fruit each day");
	}

	
	//Prueba que se construye un mapa del mismo tama�o que el n�mero de mensajes almacenados en la base de datos de prueba
	@Test
	public void testBuildMapStructureMethod(){
		
		//Assert.assertEquals(true, MessageManager.map.size()==2);
	
	}
	
	// Probamos que carga correctamente los mensajes almacenados en la base de datos (se comprueba que son los mensajes que deben estar cargados) 
	
	@Test
	public void testCorrectMapStructureMethod(){
		Assert.assertEquals(true, MessageManager.map.containsKey("HeartFailure","Treatment","precontemplation","inquiry", "treatment_agreement"));
		Assert.assertEquals(true, MessageManager.map.containsKey("HeartFailure","Treatment","precontemplation","inquiry", "treatment_agreement"));
	}
	
	
	@Test
	//Aqu� probamos tanto que hace bien la b�squeda en el mapa como el Java reflection
	public void testGetMessageContentMethod(){
		
		Object content1 = MessageManager.getMotivationalMessageContent(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, MotivationalMessageSubclassification.treatment_agreement);
		Object content2 = MessageManager.getMotivationalMessageContent(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_agreement);
		
		boolean testContent1 = content1 instanceof Questionnaire;
		boolean testContent2 = content2 instanceof String;
		
		String qWording ="Good $partOfDay $userName! A new treatment, named $treatmentName has been asigned to you. This treatment consists of $treatmentDescription. Do you plan to follow it?";
		
		ChoiceLabel choice1 = new ChoiceLabel(Boolean.TRUE, "Yes");
		ChoiceLabel choice2 = new ChoiceLabel(Boolean.FALSE, "No");
		ChoiceLabel[] booleanChoices = {choice1,choice2};

		SingleChoiceQuestion question = new SingleChoiceQuestion(qWording,TypeMapper.getDatatypeURI(Boolean.class),booleanChoices);
		Questionnaire questionnaire = new Questionnaire ("First inquiry", 
				"This message will be displayed whenever a new treatment is detected, to ask the user" + 
				" about his/her predisposition to follow the treatment.", question); 
		
		
		Questionnaire questionnaire1Expected = questionnaire;
		String content2Expected ="Good $partOfDay $caregiverName! $userName has agreeded to follow the treatment $treatmentName." +
		" You'll be notified whether the first session will be performed or not by $userName.";

		Assert.assertEquals(true, testContent1); //Comprobamos que el contenido es tipo Questionnaire (MotivationalQuestionnaire)
		Assert.assertEquals(true, testContent2); //Comprobamos que el contenido es de tipo String (MotivationalPlainMessage)
		System.out.println(content2Expected);
		Assert.assertEquals(true, content1.equals(questionnaire1Expected)); // Comprobamos que el contenido es el esperado
		Assert.assertEquals(true, content2.equals(content2Expected)); // o sea, el contenido sin pulir.
	
	}
	
	@Test
	public void testGetVariablesMethod(){
		String messageContent ="Good $partOfDay $caregiverName! $userName has agreeded to follow the treatment $treatmentName." +
		" You'll be notified whether the first session will be performed or not by $userName.";

		String[] variables = MessageManager.getVariables(messageContent);
	
		Assert.assertEquals(true, variables.length == 5);
		Assert.assertEquals(true, variables[0].equals("partOfDay") );
		Assert.assertEquals(true, variables[1].equals("caregiverName") );
		Assert.assertEquals(true, variables[2].equals("userName") );
		Assert.assertEquals(true, variables[3].equals("treatmentName") );
		Assert.assertEquals(true, variables[4].equals("userName") );
	}
	
	
	@Test
	public void testDecodeMessageContentMethod(){
		
		Object content = MessageManager.getMotivationalMessageContent(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.notification, MotivationalMessageSubclassification.treatment_performance_agreement);
	
		String decodedContent =  MessageManager.decodeMessageContent(content);
	
		String decodedContentExpected =  "Good morning Andrea! Peter has agreeded to follow the treatment Diet."+
		" You'll be notified whether the first session will be performed or not by Peter.";

		
		Assert.assertEquals(true, decodedContent.equals(decodedContentExpected));
	}

	@Test
	public void testDecodeQuestionnaire(){
		
		MotivationalMessage mm = MessageManager.getMessageToSendToUser(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, MotivationalMessageSubclassification.treatment_agreement);
		Questionnaire q = (Questionnaire)mm.getContent();
		
		Question[] firstQuestion = q.getQuestions();
		String content = firstQuestion[0].getQuestionWording();
		
		String qWording ="Good morning Peter! A new treatment, named Diet has been asigned to you. This treatment consists of eating 5 pieces of fruit each day. Do you plan to follow it?";
		Assert.assertEquals(content,qWording);
	}

@Test
public void testsendMessageToAssistedPerson(){
	MessageManager.getMessageToSendToUser(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, MotivationalMessageSubclassification.treatment_agreement);
}
}
