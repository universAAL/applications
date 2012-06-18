package org.universAAL.AALApplication.health.motivation.messageManagerTester;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
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
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.QuestionnaireOntology;

public class TestMessageManager extends TestIfaceForMM{

	private SendMotivationMessageIface iTestForMM = this;
	private MotivationServiceRequirementsIface iTestForMMVariables = this;
	@Before
	public void setUp() throws Exception{
	
		registerClassesNeeded();
		MessageManager.setMMSenderIface(iTestForMM);
		MessageManager.buildMapStructure();
		MessageVariables.setMotivationServiceRequirementsIface(iTestForMMVariables);
		MessageManager.buildInitialMapOfVariables();
		
		MessageVariables.addToMapOfVariables("userPetName", "Bobby");
		MessageVariables.addToMapOfVariables("veterinarianDate", "16 h");
		MessageVariables.addToMapOfVariables("veterinarianPlace", "City Mall");
	}


	@Test
	public void testBuilMapStructureMethod(){
		
		Assert.assertEquals(true, MessageManager.map.size()==2);
		Assert.assertEquals(true, MessageManager.map.containsKey("HeartFailure","Treatment","action","notification"));
		Assert.assertEquals(true, MessageManager.map.containsKey("HeartFailure","Treatment","maintenance","notification"));
	}
	
	@Test
	//Aquí probamos tanto que hace bien la búsqueda en el mapa, como Java reflection
	public void testGetMessageContentMethod(){
		
		
		Object content1 = MessageManager.getMotivationalMessageContent(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.action, MotivationalMessageClassification.notification);
		Object content2 = MessageManager.getMotivationalMessageContent(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.notification);
		
		boolean testContent1 = content1 instanceof String;
		boolean testContent2 = content2 instanceof String;
		
		String content1Expected ="Hello Peter! Today is a great day to start yogging. Come on, put on your trainers and take a walk!";
		String content2Expected ="Good $partOfDay $userName! Don't forget to carry your dog $userPetName to the veterinarian today. The date is at $veterinarianDate in $veterinarianPlace. Have a nice day!";
		
		Assert.assertEquals(true, testContent1);
		Assert.assertEquals(true, testContent2);
		
		Assert.assertEquals(true, content1.equals(content1Expected));
		Assert.assertEquals(true, content2.equals(content2Expected));
		
	}

	@Test
	public void testGetVariablesMethod(){
		String messageContent ="Good $partOfDay $userName! Don't forget to carry your dog $userPetName to the veterinarian today. The date is at $veterinarianDate in $veterinarianPlace. Have a nice day!";
		String[] variables = MessageManager.getVariables(messageContent);
	
		Assert.assertEquals(true, variables.length == 5);
		Assert.assertEquals(true, variables[0].equals("partOfDay") );
		Assert.assertEquals(true, variables[1].equals("userName") );
		Assert.assertEquals(true, variables[2].equals("userPetName") );
		Assert.assertEquals(true, variables[3].equals("veterinarianDate") );
		Assert.assertEquals(true, variables[4].equals("veterinarianPlace") );
	}
	
	@Test
	public void testDecodeMessageContentMethod(){
		
		MessageManager.buildMapStructure();
		
		Object content1 = MessageManager.getMotivationalMessageContent(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.action, MotivationalMessageClassification.notification);
		Object content2 = MessageManager.getMotivationalMessageContent(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.maintenance, MotivationalMessageClassification.notification);
	
		String decodedContent1 =  MessageManager.decodeMessageContent(content1);
		String decodedContent2 =  MessageManager.decodeMessageContent(content2);
	
		String decodedContent1Expected = "Hello Peter! Today is a great day to start yogging. Come on, put on your trainers and take a walk!";
		String decodedContent2Expected = "Good morning Pepe! Don't forget to carry your dog Bobby to the veterinarian today. The date is at 16 h in City Mall. Have a nice day!";
		
		Assert.assertEquals(true, decodedContent1.equals(decodedContent1Expected));
		Assert.assertEquals(true, decodedContent2.equals(decodedContent2Expected));
	}

}
