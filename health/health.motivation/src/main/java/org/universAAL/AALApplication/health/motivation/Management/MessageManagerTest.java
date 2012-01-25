package org.universAAL.AALApplication.health.motivation.Management;

import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * This class chechks if the MessageManager works properly. 
 * @author mdelafuente
 *
 */

public class MessageManagerTest extends TestCase{

	private static MessageManager manager;  
	
	
	/**
	 * This method checks if the message obtained by a set of keys is whether or not correct. 
	 * For the specified keys the message returned must be: 
	 * "Hello $username! Your $caregiverRol has suggested you to perform a treatment, named $treatmentName, 
	 * based on $treatmentDefinition, so as to $treatmentPuropose. Are you planning to follow it? YES/NO"
	 * @param treatment_type
	 * @param motivational_status
	 * @param message_context
	 * @param depth
	 * @param message_type
	 */
	
	public void testCorrectMessage(String treatment_type, String motivational_status, String message_context, String depth, String message_type){
		
		String motivationalMessage = manager.getMotivationalMessage(treatment_type, motivational_status, message_context, depth, message_type);
		String motivationalMessageExpected = "Hello $username! Your $caregiverRol has suggested you to perform a treatment, named $treatmentName, based on $treatmentDefinition, so as to $treatmentPuropose. Are you planning to follow it? YES/NO";
		
		assertEquals(true, motivationalMessage.equals(motivationalMessageExpected));
		System.out.println("Messages well detected");
		
	}
	
	/**
	 * This method checks if the message obtained by a set of keys is whether or not correct. 
	 * In this case, we are getting two different messages which have the same set of keys.
	 * 
	 * @param treatment_type
	 * @param motivational_status
	 * @param message_context
	 * @param depth
	 * @param message_type
	 */
	
	public void testCorrectMessageSameKeys(String treatment_type, String motivational_status, String message_context, String depth, String message_type){
		
		String mMessagesDetected = manager.getMotivationalMessage(treatment_type, motivational_status, message_context, depth, message_type);
		
		String possibleMessage1 = "";
		String possibleMessage2 = "";
		
		assertEquals(true,(mMessagesDetected.equals(possibleMessage1) || mMessagesDetected.equals(possibleMessage2)));
		System.out.println("Messages well detected, between a set of two");
	}
	
	
	public void testCorrectVariables(String[] variables){
		
		String[] variablesExpected = new String[4];
		
		variablesExpected[0] = "username";
		variablesExpected[1] = "caregiverRol";
		variablesExpected[2] = "treatment";
		variablesExpected[3] = "username";
		
		assertEquals(true, variables.equals(variablesExpected));
		
	} 
	
	
	public void testCorrectReplacement(){
		
	}
	
	
	public static void main (String[] args){
		
		manager = new MessageManager("english"); 
		
		String mm1 = manager.getMotivationalMessage("MeasurementTaking", "contemplation", "satisfaction", "1", "plain_text");
		
		String mm2 = manager.getMotivationalMessage("PhysicalActivity, HealthyHabits, MeasurementTaking", "precontemplation", "personalized_feedback", "0", "questionnaire");
		String mm3 = manager.getMotivationalMessage("PhysicalActivity, HealthyHabits, MeasurementTaking", "precontemplation", "personalized_feedback", "0", "questionnaire");
		
		String mensajeProblematico = "Hello $username! Your $caregiverRol has suggested you to perform a treatment, named $treatmentName, based on $treatmentDefinition, so as to $treatmentPuropose.";
		
		System.out.println(manager.readMessage(mensajeProblematico));
		
		//System.out.println(manager.readMessage(mm2));
		//System.out.println(manager.readMessage(mm3));

	}
	
}
