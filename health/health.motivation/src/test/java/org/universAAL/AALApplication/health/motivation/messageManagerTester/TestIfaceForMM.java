package org.universAAL.AALApplication.health.motivation.messageManagerTester;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import org.universAAL.AALApplication.health.motivation.ClassesNeededRegistration;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.profile.ProfileOntology;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.health.HealthProfile;
import org.universAAL.ontology.questionnaire.QuestionnaireOntology;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.ontology.space.SpaceOntology;
import org.universaal.ontology.disease.owl.DiseaseOntology;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.QuestionnaireStrategyOntology;

public class TestIfaceForMM implements SendMotivationMessageIface, MotivationServiceRequirementsIface{

	
	public static ArrayList <MotivationalMessage> motivationalMessagesSentToAP = new ArrayList <MotivationalMessage>();
	public static ArrayList <MotivationalMessage> motivationalMessagesSentToCaregiver = new ArrayList <MotivationalMessage>();



	public static final Locale SPANISH = new Locale ("es", "ES");

	public static void registerClassesNeeded() {

		OntologyManagement.getInstance().register(new DataRepOntology());
		OntologyManagement.getInstance().register(new ServiceBusOntology());
		OntologyManagement.getInstance().register(new UIBusOntology());
		OntologyManagement.getInstance().register(new LocationOntology());
		OntologyManagement.getInstance().register(new ShapeOntology());
		OntologyManagement.getInstance().register(new PhThingOntology());
		OntologyManagement.getInstance().register(new SpaceOntology());
		OntologyManagement.getInstance().register(new ProfileOntology());
		OntologyManagement.getInstance().register(new QuestionnaireOntology());
		OntologyManagement.getInstance().register(new DiseaseOntology());
		OntologyManagement.getInstance().register(new HealthMeasurementOntology());
		OntologyManagement.getInstance().register(new HealthOntology());
		OntologyManagement.getInstance().register(new MessageOntology());
		OntologyManagement.getInstance().register(new QuestionnaireStrategyOntology());
	}

	
	public File getDBRoute(Locale language) {

		File file;

		if (language.equals(SPANISH))
			return file = new File("");
		else if (language.equals(Locale.ENGLISH))
			return file = new File("C://universAAL/motivationalMessages/test_MM.csv");
		else
			return null;
	}

	public void sendMessageToAP(MotivationalMessage mm, Treatment t) {
		motivationalMessagesSentToAP.add(mm);
	}

	public void sendMessageToCaregiver(MotivationalMessage mm, Treatment t) {
		motivationalMessagesSentToCaregiver.add(mm);
	}


	public HealthProfile getHealthProfile(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MotivationalMessage> getMMsentToAP() {
		return motivationalMessagesSentToAP;
	}

	public ArrayList<MotivationalMessage> getMMsentToCaregiver() {
		return motivationalMessagesSentToCaregiver;
	}

	public void fillVariablesContent(){

	}

	public String getAssistedPersonName() {
		return "Peter";
	}

	public String getCaregiverName(User assistedPerson) {
		return "Andrea";
	}

	public String getPartOfDay() {
		return "morning";
	}

	public User getAssistedPerson() {
		User ap = new User("peter");
		return ap;
	}


	public String getAPGenderArticle() {
		return "him";
	}


	public String getAPPosesiveGenderArticle() {
		return "his";
	}


	public String getCaregiverGenderArticle() {
		return "him";
	}


	public String getCaregiverPosesiveGenderArticle() {
		return "his";
	}
	

}
