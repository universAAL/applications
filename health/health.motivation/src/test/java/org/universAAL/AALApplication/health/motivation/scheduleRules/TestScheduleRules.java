package org.universAAL.AALApplication.health.motivation.scheduleRules;

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
import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.PerformedSession;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.Walking;
import org.universAAL.ontology.profile.User;

public class TestScheduleRules extends TestIface{
	
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
		InputStream is = getClass().getResourceAsStream("/rules/ScheduleRules.drl");
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
	
	public Treatment4Rules generateWeeklyTreatment4Test(int offsetPastMinutes, int startDaysAgo, int endsInDays) throws Exception{

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

		String recurrence = "FREQ=WEEKLY;UNTIL="+convertToCalendarToString(endDate); // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 60 minutes at a moderate step";

		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking 1", "walking three times a week for four months", tp, HeartFailure.MY_URI);

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

		String recurrence = "FREQ=DAILY;UNTIL="+convertToCalendarToString(endDate); // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";

		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking 2", "walking every day", tp, HeartFailure.MY_URI);

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
	
	public void testCompletenessUnitSet() throws Exception{
		Treatment4Rules treatment =  generateDailyTreatment4Test(30, 0, 10); //en total, 10 sesiones 
		Assert.assertTrue(treatment.getCompletenessUnit() == 0);
		
		ksession.insert(treatment);
		ksession.fireAllRules();
		
		Assert.assertTrue(treatment.getCompletenessUnit()== (1/10));
		
	}

	@Test
	public void testPerformedSessionInRightIntervalOfTime() throws Exception{
		
		Treatment4Rules treatment = generateDailyTreatment4Test(50, 5, 10);
		//empez� hace 50 minutos y como las sesiones son de 60 minutos, aun faltan 10 minutos para acabar (estamos en performing interval)
		
		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();

		startTime.add(Calendar.DAY_OF_YEAR, 0);
		startTime.add(Calendar.MINUTE, -20);
		endTime.add(Calendar.DAY_OF_YEAR, 0); // no importa cuando termina, s�lo necesitamos el momento en el que comienza la ps
		endTime.add(Calendar.MINUTE, 10);
		
		XMLGregorianCalendar psStartTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTime);
		XMLGregorianCalendar psEndTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTime);

		PerformedSession ps = new PerformedSession(treatment,psStartTime,psEndTime);
		Assert.assertTrue(ps.getIsValid() == false);
		
		ksession.insert(treatment);
		ksession.insert(ps);
		
		ksession.fireAllRules();
		
		Assert.assertTrue(ps.getIsValid() == true);

	}
	
	
	@Test
	//de momento la comprobaci�n es s�lo visual
	public void testSessionNotPerformed() throws Exception{
		
		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();

		startTime.add(Calendar.DAY_OF_YEAR, -3);
		startTime.add(Calendar.MINUTE, -155);
		endTime.add(Calendar.DAY_OF_YEAR, -3);
		endTime.add(Calendar.MINUTE, (-95)); // estamos mas alla del intervalo extra

		endDate.add(Calendar.DAY_OF_YEAR, 5);

		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTime);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTime);

		String recurrence = "FREQ=DAILY;UNTIL="+convertToCalendarToString(endDate); // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 60 minutes at a moderate step";

		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking 3", "walking every day", tp, HeartFailure.MY_URI);
		Treatment4Rules t4r2 = new Treatment4Rules(wt);
		
		
		System.out.println("Ahora: " + t4r2.getNow());
		System.out.println("LSEWEI: " + t4r2.getLastSessionEndWithExtraTime());
		
		ksession.insert(t4r2); //no introducimos ps
		ksession.fireAllRules();
	
	}
	
	
	@Test
	public void testAlertMessageInExtraInterval() throws DatatypeConfigurationException{
		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();

		startTime.add(Calendar.DAY_OF_YEAR, -3);
		startTime.add(Calendar.MINUTE, -65);
		endTime.add(Calendar.DAY_OF_YEAR, -3);
		endTime.add(Calendar.MINUTE, (-5)); // estamos en el intervalo extra

		endDate.add(Calendar.DAY_OF_YEAR, 5);

		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTime);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTime);

		String recurrence = "FREQ=DAILY;UNTIL="+convertToCalendarToString(endDate); // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 60 minutes at a moderate step";

		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking 4", "walking every day", tp, HeartFailure.MY_URI);
		Treatment4Rules t4r4 = new Treatment4Rules(wt);
		
		
		ksession.insert(t4r4); //no introducimos ps
		ksession.fireAllRules();
	}
}
