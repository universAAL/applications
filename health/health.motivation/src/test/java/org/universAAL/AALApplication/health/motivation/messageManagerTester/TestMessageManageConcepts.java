package org.universAAL.AALApplication.health.motivation.messageManagerTester;

import org.universAAL.AALApplication.health.motivation.motivationalMessages.PlainMessageForTest;
import org.universAAL.AALApplication.health.motivation.motivationalMessages.TreatmentDetectionMessage;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universaal.ontology.owl.MessageOntology;

import junit.framework.TestCase;

public class TestMessageManageConcepts extends TestCase {

	public void testReflexion() {
		String p = "org.universAAL.AALApplication.health.motivation.motivationalMessages.";
		String nameClass = p + "TreatmentDetectionMessage";
		try{
			Class <?> cName = Class.forName(nameClass);
			Object content = cName.newInstance();
			assertNotNull(content);
			assertTrue(content instanceof TreatmentDetectionMessage);
		}catch (Exception e){
			
		} 
	}
	
}
