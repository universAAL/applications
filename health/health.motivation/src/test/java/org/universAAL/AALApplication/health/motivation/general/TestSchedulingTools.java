package org.universAAL.AALApplication.health.motivation.general;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

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
import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
import org.universAAL.AALApplication.health.motivation.schedulingTools.SchedulingTools;
import org.universAAL.AALApplication.health.motivation.treatment.TestIface;
import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.Walking;

public class TestSchedulingTools  extends TestIface{

	private KnowledgeBase kbase;
	private static StatefulKnowledgeSession ksession;
	
	private SendMotivationMessageIface iTestTreatment = this;
	private MotivationServiceRequirementsIface iTestStatusForMMVariables = this;
	
	@Before

	public void setUp() throws Exception{
		TestIface.registerClassesNeeded();
		
		//load up the knowledge base
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/ScheduleRules.drl"), ResourceType.DRL);
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
		
		//load the facts
		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();

		startTime.add(Calendar.DAY_OF_YEAR, -10); //comienzo hace 10 d�as
		startTime.add(Calendar.MINUTE, -30);
		endTime.add(Calendar.DAY_OF_YEAR, -10);
		endTime.add(Calendar.MINUTE, (60-30));

		endDate.add(Calendar.DAY_OF_YEAR, 10);

		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTime);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTime);

		String recurrence = "FREQ=WEEKLY;BYDAY=MO,WE,FR;UNTIL="+convertToCalendarToString(endDate); // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";

		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);

		// load global variables
		
		//insert the facts in drools working memory
		
		ksession.insert(wt);
		
		//fire the rules
		ksession.fireAllRules();
		
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

	public Treatment generateTreatment4Test(int offsetPastMinutes) throws Exception{

		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();

		startTime.add(Calendar.DAY_OF_YEAR, -10); //comienzo hace 10 d�as
		startTime.add(Calendar.MINUTE, -offsetPastMinutes);
		endTime.add(Calendar.DAY_OF_YEAR, -10);
		endTime.add(Calendar.MINUTE, (60-offsetPastMinutes));

		endDate.add(Calendar.DAY_OF_YEAR, 10);

		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTime);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTime);

		String recurrence = "FREQ=WEEKLY;BYDAY=MO,WE,FR;UNTIL="+convertToCalendarToString(endDate); // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";

		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);

		return wt;
	}

	public Treatment generateTreatmentEndsToday4Test() throws Exception{

		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();

		startDate.add(Calendar.DAY_OF_YEAR, -1); //comenz� hace 10 d�as
		endDate.add(Calendar.DAY_OF_YEAR, -1); // termin� en 10 d�as

		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);

		firstEventStartDate.setTime(20, 0, 0);
		firstEventEndDate.setTime(20, 30, 0);

		String recurrence = "FREQ=WEEKLY;BYDAY=SU;UNTIL=20120722T235959"; // cambiar el d�a manualmente
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";

		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);

		return wt;
	}

	@Test

	public void testIsNowInPerformingInterval() throws Exception{
		Treatment t = generateTreatment4Test(30);
		Assert.assertTrue(SchedulingTools.isNowInPerformingInterval(t));
	}
	
	@Test
	public void testIsNotNowInPerformingInterval() throws Exception{
		Treatment t = generateTreatment4Test(90);
		Assert.assertFalse(SchedulingTools.isNowInPerformingInterval(t));
	}
	
	@Test
	public void testPSNotInExtraInterval(){
		
		
		
	}
	
	
	/*
	@Test
	public void testLastSession() throws Exception{

		Treatment t =  generateTreatment4Test();
		VEvent event = SchedulingTools.tpToEvent(t);
		DateList sessionDatesAndTimes = SchedulingTools.getPlannedSessions(t);

		for(int i=0;i<sessionDatesAndTimes.size();i++){
			DateTime fecha = (DateTime) sessionDatesAndTimes.get(i);

			System.out.println("Fecha sesi�n: " + fecha + "\n" );
			System.out.println("Hora fin pr�xima sesi�n: " + event.getEndDate().getValue() + "\n");
		}

		System.out.println("Fecha de la �ltima sesi�n: " + SchedulingTools.getLastPlannedSession(t));

		boolean inPerformingInterval = SchedulingTools.isNowInPerformingInterval(t);

		Assert.assertEquals(false, inPerformingInterval);
	}
	 */
	/*
	@Test

	public void testVEvent() throws Exception{
		Treatment t =  generateTreatment4Test();
		VEvent event = SchedulingTools.tpToEvent(t);
		DtStart start = event.getStartDate();
		DtEnd end = event.getEndDate();
		Dur duration = new Dur(start.getDate(), end.getDate());
		System.out.println("Duraci�n: " + duration.getMinutes());

	}
	 */

	/*

	@Test
	public void testLastDate() throws Exception{

		Date lastDate = SchedulingTools.getTreatmentEndDate(generateTreatment4Test());

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.MONTH,11);

		Date expectedDate = new Date(cal.getTime());

		Assert.assertEquals(true, lastDate.compareTo(expectedDate)==0);
	}

	@Test
	public void testTreatmentEndsAfterToday() throws Exception{

		Assert.assertEquals(true, SchedulingTools.treatmentEndsAfterToday(generateTreatment4Test()));
	}


	@Test
	public void testTreatmentEndsToday() throws Exception{

		Assert.assertEquals(true, SchedulingTools.treatmentEndsToday(generateTreatmentEndsToday4Test()));
	}
	 */
}
