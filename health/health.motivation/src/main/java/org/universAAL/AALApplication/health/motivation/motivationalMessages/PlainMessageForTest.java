package org.universAAL.AALApplication.health.motivation.motivationalMessages;

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
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalPlainMessage;
import org.universaal.ontology.owl.QuestionnaireOntology;
import org.universaal.ontology.disease.owl.DiseaseOntology;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MotivationalMessageClassification;


public class PlainMessageForTest extends MotivationalPlainMessage {
public static String content = "Hola, esto es un mensaje de $prueba";

	
public Object getContenido(MotivationalPlainMessage message) {
	//System.out.println(message.getContent());
	return message.getContent();
	}

public PlainMessageForTest(){
	
}
public static void main (String args[]){
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
	
	MotivationalPlainMessage message = new MotivationalPlainMessage(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation,MotivationalMessageClassification.notification, content);
	PlainMessageForTest pmnb = new PlainMessageForTest();
	
	pmnb.getContenido(message);
	
	//MessageManager.sendMessageToUser(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation,MotivationalMessageClassification.notification, content);
}

}
