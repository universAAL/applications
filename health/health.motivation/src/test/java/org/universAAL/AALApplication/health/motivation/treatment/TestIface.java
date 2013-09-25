package org.universAAL.AALApplication.health.motivation.treatment;

import java.util.ArrayList;
import java.util.Locale;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
import org.universAAL.AALApplication.health.motivation.schedulingTools.SchedulingTools;
import org.universAAL.container.JUnit.JUnitModuleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.disease.owl.DiseaseOntology;
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.measurement.MeasurementOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.profile.ProfileOntology;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.questionnaire.Questionnaire;
import org.universAAL.ontology.questionnaire.QuestionnaireOntology;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.ontology.space.SpaceOntology;
import org.universAAL.ontology.unit.UnitOntology;
import org.universAAL.ontology.vcard.VCardOntology;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.QuestionnaireStrategyOntology;

public class TestIface implements SendMotivationMessageIface, MotivationServiceRequirementsIface {

	public static ArrayList <MotivationalMessage> motivationalMessagesSentToAP = new ArrayList <MotivationalMessage>();
	public static ArrayList <MotivationalMessage> motivationalMessagesSentToCaregiver = new ArrayList <MotivationalMessage>();

	public static final Locale SPANISH = new Locale ("es", "ES");

	public static void registerClassesNeeded() {

		ModuleContext mc = new JUnitModuleContext();
		OntologyManagement.getInstance().register(mc,new DataRepOntology());
		OntologyManagement.getInstance().register(mc,new ServiceBusOntology());
		OntologyManagement.getInstance().register(mc,new UIBusOntology());
		OntologyManagement.getInstance().register(mc,new LocationOntology());
		OntologyManagement.getInstance().register(mc,new ShapeOntology());
		OntologyManagement.getInstance().register(mc,new PhThingOntology());
		OntologyManagement.getInstance().register(mc,new SpaceOntology());
		OntologyManagement.getInstance().register(mc,new VCardOntology());
		OntologyManagement.getInstance().register(mc,new ProfileOntology());
		OntologyManagement.getInstance().register(mc,new UnitOntology());
		OntologyManagement.getInstance().register(mc,new MeasurementOntology());
		OntologyManagement.getInstance().register(mc,new QuestionnaireOntology());
		OntologyManagement.getInstance().register(mc,new DiseaseOntology());
		OntologyManagement.getInstance().register(mc,new HealthMeasurementOntology());
		OntologyManagement.getInstance().register(mc,new HealthProfileOntology());
		OntologyManagement.getInstance().register(mc,new MessageOntology());
		OntologyManagement.getInstance().register(mc,new QuestionnaireStrategyOntology());
	}

	public static void resetMessagesSent(){
		motivationalMessagesSentToAP.clear();
		motivationalMessagesSentToCaregiver.clear();
	}

	public static boolean sentToAPContainsQuestionnaire(String questionnaireName){

		for (int i=0; i<motivationalMessagesSentToAP.size(); i++){

			Object mm = motivationalMessagesSentToAP.get(i).getContent();

			if( mm instanceof Questionnaire){
				Questionnaire q = (Questionnaire)mm;
				if(q.getName().equals(questionnaireName))
					return true;
			}
		}
		return false;
	}

	public static boolean sentToCaregiverContainsQuestionnaire(String questionnaireName){

		for (int i=0;i<motivationalMessagesSentToCaregiver.size();i++){

			Object mm = motivationalMessagesSentToCaregiver.get(i).getContent();

			if( mm instanceof Questionnaire){
				Questionnaire q = (Questionnaire)mm;
				if(q.getName().equals(questionnaireName))
					return true;
			}
		}
		return false;
	}

	public static boolean sentToAPContainsPlainMessage(String plainMessageContent){
		for (int i=0;i<motivationalMessagesSentToAP.size();i++){
			Object mm = motivationalMessagesSentToAP.get(i).getContent();

			if( mm instanceof String){
				String content = (String) mm;
				if(content.equals(plainMessageContent))
					return true;
			}
		}
		return false;
	}

	/*
	public static boolean sentToAPContainsPlainMessage(String plainMessageName){
		ArrayList <MotivationalMessage> prueba = motivationalMessagesSentToAP; 
		for (int i=0;i<motivationalMessagesSentToAP.size();i++){
			Object mm = motivationalMessagesSentToAP.get(i).getContent();

			if( mm instanceof String){ // si el contenido es tipo String entonces estamos ante un PlainMessge
				String name = motivationalMessagesSentToAP.get(i).getMotivationalPlainMessageName();
				if(name.equals(plainMessageName))
					return true;
			}
		}
		return false;
	}
	*/
	public static boolean sentToCaregiverContainsPlainMessage(String plainMessageContent){

		for (int i=0;i<motivationalMessagesSentToCaregiver.size();i++){

			Object mmc = motivationalMessagesSentToCaregiver.get(i).getContent();

			if( mmc instanceof String){
				String content = (String) mmc;
				if(content.equals(plainMessageContent))
					return true;
			}
		}
		return false;
	}

	public static boolean questionnaireSentToAPSince(String questionnaireName, XMLGregorianCalendar sinceTime) throws Exception{

		XMLGregorianCalendar now = SchedulingTools.getNow();

		if(!sentToAPContainsQuestionnaire(questionnaireName))
			return false;

		else{

			for (int i=0;i<motivationalMessagesSentToAP.size();i++){

				Object mm = motivationalMessagesSentToAP.get(i).getContent();
				MotivationalMessage motmes = motivationalMessagesSentToAP.get(i);
				XMLGregorianCalendar sentDate = motmes.getSentDate();

				if( mm instanceof Questionnaire){
					Questionnaire q = (Questionnaire)mm;
					if(q.getName().equals(questionnaireName))// el cuestionario est� en los mensajes enviados al AP
						if(sentDate.compare(sinceTime) == DatatypeConstants.GREATER && sentDate.compare(now)== DatatypeConstants.LESSER )
							return true;
				}
			}
			return false;
		}

	}


	public static boolean messageSentToAPSince(String diseaseURI, String treatmentURI, MotivationalStatusType mst, MotivationalMessageClassification mmc, MotivationalMessageSubclassification mmsc, Treatment t, XMLGregorianCalendar sinceTime) throws Exception{

		MessageVariables.addToMapOfVariables("treatmentName", t.getName());
		
		MotivationalMessage mm = MessageManager.getMessageToSendToUser(diseaseURI, treatmentURI, mst, mmc, mmsc);
		String plainMessageContent = (String) mm.getContent();
		XMLGregorianCalendar now = SchedulingTools.getNow();

		if(!sentToAPContainsPlainMessage(plainMessageContent))
			return false;

		else{

			for (int i=0;i<motivationalMessagesSentToAP.size();i++){

				Object mmContent = motivationalMessagesSentToAP.get(i).getContent();
				MotivationalMessage motmes = motivationalMessagesSentToAP.get(i);
				XMLGregorianCalendar sentDate = motmes.getSentDate();

				if( mmContent instanceof String){
					String content = (String) mmContent;
					if(content.equals(plainMessageContent))// el cuestionario est� en los mensajes enviados al AP
						if(sentDate.compare(sinceTime) == DatatypeConstants.GREATER && sentDate.compare(now)== DatatypeConstants.LESSER )
							return true;
				}
			}
			return false;
		}
	}


	public void sendMessageToAP(MotivationalMessage mm, Treatment t) {
		System.out.println("Message sent to assisted person type "+mm.getMMessageType().toString()+" subtype "+mm.getMMessageSubtype());
		motivationalMessagesSentToAP.add(mm);
	}

	public void sendMessageToCaregiver(MotivationalMessage mm, Treatment t)  {
		System.out.println("Message sent to caregiver type "+mm.getMMessageType().toString()+" subtype "+mm.getMMessageSubtype());
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
		User ap = new User("Peter");
		return ap;
	}


	public String getAPGenderArticle() {
		return "him";
	}

	public String getAPPosesiveGenderArticle() {
		return "his";
	}

	public String getCaregiverGenderArticle() {
		return "her";
	}

	public String getCaregiverPosesiveGenderArticle() {
		return "her";
	}


	//---------------------DARIO---------------------------------------

	/*
	 * 
	 * public static String convertToCalendarToString(GregorianCalendar cal){
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		int secs = cal.get(Calendar.SECOND);

		String s = "" + cal.get(Calendar.YEAR) +
		(month < 9? "0" + month : month)
		+ (day < 9? "0" + day : day) +"T"+ 
		(hour < 9? "0" + hour : hour)
		+ (minutes < 9? "0" + minutes : minutes)
		+ (secs < 9? "0" + secs : secs);

		return s;
	}

	public static GregorianCalendar getLastPreviousMonday(GregorianCalendar cal) throws Exception{
		GregorianCalendar retCalendar = (GregorianCalendar) cal.clone();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		switch(day){
		case Calendar.MONDAY:
			retCalendar.add(Calendar.DAY_OF_WEEK, -7);
			return retCalendar;
		case Calendar.TUESDAY:
			retCalendar.add(Calendar.DAY_OF_WEEK, -1);
			return retCalendar;
		case Calendar.WEDNESDAY:
			retCalendar.add(Calendar.DAY_OF_WEEK, -2);
			return retCalendar;
		case Calendar.THURSDAY:
			retCalendar.add(Calendar.DAY_OF_WEEK, -3);
			return retCalendar;
		case Calendar.FRIDAY:
			retCalendar.add(Calendar.DAY_OF_WEEK, -4);
			return retCalendar;
		case Calendar.SATURDAY:
			retCalendar.add(Calendar.DAY_OF_WEEK, -5);
			return retCalendar;
		case Calendar.SUNDAY:
			retCalendar.add(Calendar.DAY_OF_WEEK, -6);
			return retCalendar;
		default:
			throw new Exception("Somehting really strange happened!!");
		}
	}


	public static String convertDayOfWeekToICALString(GregorianCalendar cal) throws Exception{
		int day = cal.get(Calendar.DAY_OF_WEEK);
		switch(day){
		case Calendar.MONDAY:
			return "MO";
		case Calendar.TUESDAY:
			return "TU";
		case Calendar.WEDNESDAY:
			return "WE";
		case Calendar.THURSDAY:
			return "TH";
		case Calendar.FRIDAY:
			return "FR";
		case Calendar.SATURDAY:
			return "SA";
		case Calendar.SUNDAY:
			return "SU";
		default:
			throw new Exception("Somehting really strange happened!!");
		}
	}

	 * 
	 * 
	 * */
}
