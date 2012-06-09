package org.universAAL.AALApplication.health.motivation.motivationalMessages;

import java.util.Locale;

import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.TypeMapper;
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
import org.universaal.ontology.owl.ChoiceLabel;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalQuestionnaire;
import org.universaal.ontology.owl.Questionnaire;
import org.universaal.ontology.owl.QuestionnaireOntology;
import org.universaal.ontology.owl.SingleChoiceQuestion;

public class TreatmentDetectionMessage extends MotivationalQuestionnaire implements MotivationalMessageContent{
	
	/*private String qWording = "$partOfDay $username! A new treatment, named $treatmentName" +
	"has been asigned to you. This treatment consists of $treatmentDescription." +
	"Do you plan to follow it?";
	*/
	// Para probar, de momento usamos un mensaje sin variables
	
	
	
	private String qWording = "Hello Mary! A new treatment, named 'Low fat diet'" +
	"has been asigned to you. This treatment consists of eating more vegetables and fruit and avoiding fatty meals." +
	"Do you plan to follow it?";


private ChoiceLabel choice1 = new ChoiceLabel(Boolean.TRUE, "Yes");
private ChoiceLabel choice2 = new ChoiceLabel(Boolean.FALSE, "No");
private ChoiceLabel[] booleanChoices = {choice1,choice2};

private SingleChoiceQuestion question = new SingleChoiceQuestion(qWording,TypeMapper.getDatatypeURI(Boolean.class),booleanChoices);
private Questionnaire questionnaire = new Questionnaire ("First inquiry", 
		"This message will be displayed whenever a new treatment is detected, to ask the user" + 
		" about his/her predisposition to follow the treatment.", question); 

private MotivationalQuestionnaire followTreatmentAgreementInquiry = new MotivationalQuestionnaire(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, questionnaire);

public TreatmentDetectionMessage(){
	
}

public Object getMessageContent() {
	return followTreatmentAgreementInquiry.getContent();
}
/*
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
*/
	/*TreatmentDetectionMessage tdm = new TreatmentDetectionMessage();
	if(tdm.getMessageContent() instanceof Questionnaire ){
		System.out.println("El contenido es un cuestionario");
		System.out.println(((Questionnaire)(tdm.getMessageContent())).questionnaireToString());
	}
	else{
		System.out.println("El contenido NO es un cuestionario");
	}
	*/
/*
	 String qWording = "$partOfDay $username! A new treatment, named $treatmentName has been asigned to you. This treatment consists of $treatmentDescription. Do you plan to follow it?";


 ChoiceLabel choice1 = new ChoiceLabel(Boolean.TRUE, "Yes");
 ChoiceLabel choice2 = new ChoiceLabel(Boolean.FALSE, "No");
 ChoiceLabel[] booleanChoices = {choice1,choice2};

	SingleChoiceQuestion question = new SingleChoiceQuestion(qWording,TypeMapper.getDatatypeURI(Boolean.class),booleanChoices);
	Questionnaire questionnaire = new Questionnaire ("First inquiry", 
			"This message will be displayed whenever a new treatment is detected, to ask the user" + 
			" about his/her predisposition to follow the treatment.", question); 
	MotivationalQuestionnaire followTreatmentAgreementInquiry = new MotivationalQuestionnaire(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, questionnaire);
	MessageManager mm = new MessageManager(Locale.ENGLISH);
	MessageManager.sendMessageToUser(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation,MotivationalMessageClassification.inquiry);
	
	}*/
}


