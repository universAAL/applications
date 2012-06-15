package org.universAAL.AALApplication.health.motivation;

import java.util.Locale;

import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.ontology.space.SpaceOntology;
import org.universaal.ontology.disease.owl.DiseaseOntology;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.QuestionnaireOntology;

import junit.framework.TestCase;

public class TestMotivation extends TestCase implements MotivationInterface {

	String messageDatabaseRoute=null;
	String variableDatabaseRoute=null;
	
	public String getMessageDatabaseRoute(Locale messageDatabaseLanguage) {
		return null;
	}

	public String getMessageVariablesRoute(Locale variablesDatabaseLanguage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void registerClassesNeeded(){
		
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

	public String sendMotivationalMessageToUser(MotivationalMessage mm) {
		// TODO Auto-generated method stub
		return null;
	}

	public String setMessageDatabaseRoute(String messageDatabaseRoute) {
		// TODO Auto-generated method stub
		return null;
	}

	public String setMessageVariablesRoute(String variablesDatabaseRoute) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendMessageToAssistedPerson(String illness,
			String treatmentType, MotivationalStatusType motStatus,
			MotivationalMessageClassification messageType) {
		MessageManager.getMessageToSendToUser(illness, treatmentType, motStatus, messageType);
		
	}

	public void sendMessageToCaregiver(String illness, String treatmentType,
			MotivationalStatusType motStatus,
			MotivationalMessageClassification messageType) {
		MessageManager.getMessageToSendToUser(illness, treatmentType, motStatus, messageType);
		
	}

}
