package org.universAAL.AALApplication.health.motivation.action;

import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
import org.universAAL.AALApplication.health.motivation.schedulingTools.Treatment4Rules;
import org.universAAL.AALApplication.health.motivation.treatment.TestIface;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.Diet;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.TreatmentPlanning;
import org.universaal.ontology.health.owl.Walking;

public class TestActionRules extends TestIface{
		
		private KnowledgeBase kbase;
		private static StatefulKnowledgeSession ksession;
		
		private SendMotivationMessageIface iTestTreatment = this;
		private MotivationServiceRequirementsIface iTestStatusForMMVariables = this;
		
		public User ap; 
		
		@Before
		public void setUp() throws Exception{
			
			registerClassesNeeded();
			
			TestIface.resetMessagesSent();
			ap = this.getAssistedPerson(); 		
			//load up the knowledge base
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			InputStream is = getClass().getResourceAsStream("/rules/ActionRules.drl");
			kbuilder.add(ResourceFactory.newInputStreamResource(is), ResourceType.DRL);
			KnowledgeBuilderErrors errors = kbuilder.getErrors();
			if (errors.size() > 0) {
				for (KnowledgeBuilderError error: errors) {
					System.err.println(error);
				}
				throw new IllegalArgumentException("Could not parse knowledge.");
			}
			
			KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
			kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
			ksession = kbase.newStatefulKnowledgeSession();
			
			MessageManager.setLanguage(Locale.ENGLISH);
			MessageManager.setMMSenderIface(iTestTreatment); //para las pruebas, usamos la interfaz de pruebas
			MessageManager.buildMapStructure();
			MessageVariables.setMotivationServiceRequirementsIface(iTestStatusForMMVariables);
			MessageManager.buildInitialMapOfVariables();

			//insert the facts in drools working memory
			
			//fire the rules
			ksession.fireAllRules();
		}
		
		@After
		public void tearDown()
		{
			TestIface.resetMessagesSent();
		}
		
		public Treatment4Rules generateTreatment4Test(int offsetPastMinutes, int startDaysAgo, int endsInDays) throws Exception{

			GregorianCalendar startTime = new GregorianCalendar();
			GregorianCalendar endTime = new GregorianCalendar();
			GregorianCalendar endDate = new GregorianCalendar(); 

			startTime.add(Calendar.DAY_OF_YEAR, -startDaysAgo);
			startTime.add(Calendar.MINUTE, -offsetPastMinutes);
			endTime.add(Calendar.DAY_OF_YEAR, -startDaysAgo);
			endTime.add(Calendar.MINUTE, (60-offsetPastMinutes));

			endDate.add(Calendar.DAY_OF_YEAR, endsInDays);

			XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTime);
			XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTime);

			String recurrence = "FREQ=WEEKLY;UNTIL="+convertToCalendarToString(endDate); // las sesiones serï¿½n los lunes, miï¿½rcoles y viernes hasta fin de aï¿½o, en total, 4 meses de tratamiento.
			String description = "These treatment sessions consists of walking 30 minutes at a moderate step";

			TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
			Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);

			return new Treatment4Rules(wt);
		}
		
		public Treatment4Rules generateDailyTreatment4Test(int offsetPastMinutes, int startDaysAgo, int endsInDays) throws Exception{

			GregorianCalendar startTime = new GregorianCalendar();
			GregorianCalendar endTime = new GregorianCalendar();
			GregorianCalendar endDate = new GregorianCalendar();

			startTime.add(Calendar.DAY_OF_YEAR, -startDaysAgo);
			startTime.add(Calendar.MINUTE, -offsetPastMinutes);
			endTime.add(Calendar.DAY_OF_YEAR, -startDaysAgo);
			endTime.add(Calendar.MINUTE, (60-offsetPastMinutes));

			endDate.add(Calendar.DAY_OF_YEAR, endsInDays);

			XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTime);
			XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTime);

			String recurrence = "FREQ=DAILY;UNTIL="+convertToCalendarToString(endDate); // las sesiones serï¿½n los lunes, miï¿½rcoles y viernes hasta fin de aï¿½o, en total, 4 meses de tratamiento.
			String description = "These treatment sessions consists of walking 30 minutes at a moderate step";

			TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
			Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);

			return new Treatment4Rules(wt);
		}
		
		public static String convertToCalendarToString(GregorianCalendar cal){
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
		
		@Test
		
		public void testCompletenessIncreasement() throws DatatypeConfigurationException{
			Treatment treatment = new Diet ("Low fat diet", "eating less fatty", HeartFailure.MY_URI); //valid treatment
			treatment.setCompletenessUnit(1/10);
			treatment.setCompleteness(4/10);
			treatment.setMotivationalStatus(MotivationalStatusType.action);
			
			GregorianCalendar sessionStart = new GregorianCalendar();
			GregorianCalendar sessionEnd = new GregorianCalendar();
			
			sessionStart.add(Calendar.DAY_OF_YEAR, 0); 
			sessionEnd.add(Calendar.DAY_OF_YEAR, 0);
			
			XMLGregorianCalendar sessionStXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(sessionStart);
			XMLGregorianCalendar sessionEndXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(sessionEnd);
			
			sessionStXML.setTime(2, 0, 0);
			sessionEndXML.setTime(2, 30, 0);
			
			PerformedSession ps = new PerformedSession(treatment, sessionStXML, sessionEndXML, true);
			
			System.out.println("Tratamiento asociado:" + ps.getAssociatedTreatment().getName());
			
			//wmt1ChangingStatus = (FactHandle) ksession.insert(wmt1);
			ksession.insert(ps);
			ksession.fireAllRules();
			
			Assert.assertTrue(treatment.getCompleteness() == 5/10);
			
			// Enviar un mensaje a la AP según corresponda
		}
		
}
