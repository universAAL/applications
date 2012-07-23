package org.universAAL.AALApplication.health.motivation.general;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;

import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.schedulingTools.SchedulingTools;
import org.universAAL.AALApplication.health.motivation.treatment.TestIface;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.TreatmentPlanning;
import org.universaal.ontology.health.owl.Walking;

public class TestSchedulingTools {
	
	@Before
	
	public void setUp(){
		TestIface.registerClassesNeeded();
	}

	public Treatment generateTreatment4Test() throws Exception{
		
		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();
		
		startDate.add(Calendar.DAY_OF_YEAR, -10); //comienzo hace 10 días
		endDate.add(Calendar.DAY_OF_YEAR, -10); // terminará en 10 días
		
		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);
		
		firstEventStartDate.setTime(20, 0, 0);
		firstEventEndDate.setTime(20, 30, 0);
		
		String recurrence = "FREQ=WEEKLY;BYDAY=MO,WE,FR;UNTIL=20121231T235959"; // las sesiones serán los lunes, miércoles y viernes hasta fin de año, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";
		
		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);
		
		return wt;
	}
	
public Treatment generateTreatmentEndsToday4Test() throws Exception{
		
		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();
		
		startDate.add(Calendar.DAY_OF_YEAR, -1); //comenzó hace 10 días
		endDate.add(Calendar.DAY_OF_YEAR, -1); // terminó en 10 días
		
		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);
		
		firstEventStartDate.setTime(20, 0, 0);
		firstEventEndDate.setTime(20, 30, 0);
		
		String recurrence = "FREQ=WEEKLY;BYDAY=SU;UNTIL=20120722T235959"; // cambiar el día manualmente
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";
		
		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);
		
		return wt;
	}
	
	@Test
	public void testLastSession() throws Exception{
		
		Treatment t =  generateTreatment4Test();
		VEvent event = SchedulingTools.tpToEvent(t);
		DateList sessionDatesAndTimes = SchedulingTools.getPlannedSessions(t);
		
		for(int i=0;i<sessionDatesAndTimes.size();i++){
			DateTime fecha = (DateTime) sessionDatesAndTimes.get(i);
			
			System.out.println("Fecha sesión: " + fecha + "\n" );
			System.out.println("Hora fin próxima sesión: " + event.getEndDate().getValue() + "\n");
		}
		
		System.out.println("Fecha de la última sesión: " + SchedulingTools.getLastPlannedSession(t));
		
		boolean inPerformingInterval = SchedulingTools.isNowInPerformingInterval(t);
		
		Assert.assertEquals(false, inPerformingInterval);
	}
	/*
	@Test
	
	public void testVEvent() throws Exception{
		Treatment t =  generateTreatment4Test();
		VEvent event = SchedulingTools.tpToEvent(t);
		DtStart start = event.getStartDate();
		DtEnd end = event.getEndDate();
		Dur duration = new Dur(start.getDate(), end.getDate());
		System.out.println("Duración: " + duration.getMinutes());
		
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
