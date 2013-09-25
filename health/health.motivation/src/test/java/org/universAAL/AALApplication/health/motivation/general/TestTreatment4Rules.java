package org.universAAL.AALApplication.health.motivation.general;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
import org.universAAL.AALApplication.health.motivation.schedulingTools.Treatment4Rules;
import org.universAAL.AALApplication.health.motivation.treatment.TestIface;
import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.Walking;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;

public class TestTreatment4Rules extends TestIface{
	
	private SendMotivationMessageIface iTestTreatment = this;
	private MotivationServiceRequirementsIface iTestStatusForMMVariables = this;
	User ap; 

	@Before
	public void setUp() throws Exception{
		registerClassesNeeded();
		TestIface.resetMessagesSent();
		ap = this.getAssistedPerson(); 
		MessageManager.setLanguage(Locale.ENGLISH);
		MessageManager.setMMSenderIface(iTestTreatment); //para las pruebas, usamos la interfaz de pruebas
		MessageManager.buildMapStructure();
		MessageVariables.setMotivationServiceRequirementsIface(iTestStatusForMMVariables);
		MessageManager.buildInitialMapOfVariables();
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

		String recurrence = "FREQ=WEEKLY;UNTIL="+convertToCalendarToString(endDate); // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
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
	public void testNow() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test(0, 10, 10);
		
		long now1 = t4r.getNow().toGregorianCalendar().getTimeInMillis();
		
		GregorianCalendar gc = new GregorianCalendar();
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar now = dtf.newXMLGregorianCalendar(gc);
		long now2 = now.toGregorianCalendar().getTimeInMillis();
		
		Assert.assertTrue(now1-now2 < 500);
	}

	@Test
	public void testgetLastSessionStart() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test(30, 7, 14);
		XMLGregorianCalendar fecha = t4r.getLastSessionStart();
		
		long now = Calendar.getInstance().getTimeInMillis();
		long lastSession = fecha.toGregorianCalendar().getTimeInMillis();
		
		Assert.assertTrue(now -lastSession <  60*60*1000);
	}
	
	@Test
	public void testgetLastSessionStartFuture() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test(-100, 7, 14);
		XMLGregorianCalendar fecha = t4r.getLastSessionStart();
		
		long now = Calendar.getInstance().getTimeInMillis();
		long lastSession = fecha.toGregorianCalendar().getTimeInMillis();
		
		Assert.assertTrue(now - lastSession >  6*24*60*60*1000);
		Assert.assertTrue(now - lastSession <  8*24*60*60*1000);
	}
	
	@Test
	public void testgetLastSessionStartNo() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test(-100, -3, 30);
		XMLGregorianCalendar fecha = t4r.getLastSessionStart();
		Assert.assertNull(fecha);
	}
	
	@Test
	public void testgetLastSessionEnd() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test(30, 7, 14);
		XMLGregorianCalendar fecha = t4r.getLastSessionEnd();
		
		long now = Calendar.getInstance().getTimeInMillis();
		long lastSessionEnd = fecha.toGregorianCalendar().getTimeInMillis();
		
		Assert.assertTrue(lastSessionEnd - now <  40*60*1000);
	}

	@Test
	public void testgetLastSessionEndNo() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test(-100, -3, 30);
		XMLGregorianCalendar fecha = t4r.getLastSessionEnd();
		Assert.assertNull(fecha);
	}
	
	@Test
	public void testgetLastSessionEndExtraTime() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test(30, 7, 14); 
		XMLGregorianCalendar fecha = t4r.getLastSessionEndWithExtraTime();
		
		long now = Calendar.getInstance().getTimeInMillis();
		long lastSessionEnd = fecha.toGregorianCalendar().getTimeInMillis();
		System.out.println(fecha);
		
		Assert.assertTrue(lastSessionEnd - now >  119*60*1000);
		Assert.assertTrue(lastSessionEnd - now <  121*60*1000);
	}
	
}
