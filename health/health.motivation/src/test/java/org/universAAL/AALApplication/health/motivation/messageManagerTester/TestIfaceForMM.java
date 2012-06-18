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
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.health.HealthProfile;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.ontology.space.SpaceOntology;
import org.universaal.ontology.disease.owl.DiseaseOntology;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.QuestionnaireOntology;

public class TestIfaceForMM implements SendMotivationMessageIface, ClassesNeededRegistration, MotivationServiceRequirementsIface{

	
	public File getDBRoute(Locale language) {
		File file = new File("C://universAAL/motivationalMessages/test_MM.csv"); //fichero de prueba para probar MessageManager
		return file;
	}

	public ArrayList<MotivationalMessage> getMMsentToAP() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MotivationalMessage> getMMsentToCaregiver() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendMessageToAP(MotivationalMessage mm) {
		// TODO Auto-generated method stub
		
	}

	public void sendMessageToCaregiver(MotivationalMessage mm) {
		// TODO Auto-generated method stub
		
	}

	public void registerClassesNeeded() {
		
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

	public String getAssistedPersonName() {
		return "Peter";
	}

	public String getCaregiverName() {
		return null;
	}

	public HealthProfile getHealthProfile(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPartOfDay() {
		return "morning";
	}

}
