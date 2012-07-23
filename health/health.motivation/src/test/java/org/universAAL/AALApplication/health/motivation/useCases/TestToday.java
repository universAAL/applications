package org.universAAL.AALApplication.health.motivation.useCases;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.schedulingTools.TreatmentPlanningToiCal;
import org.universAAL.AALApplication.health.motivation.treatment.TestIface;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.TreatmentPlanning;
import org.universaal.ontology.health.owl.Walking;

public class TestToday {

	@Before
	public void setup(){
		TestIface.registerClassesNeeded();
	}


	@Test
	public void testIsToday() throws Exception{
		//A walking treatment that starts at 6 and ends at 7
		GregorianCalendar startFirstEvt = new GregorianCalendar();
		startFirstEvt.add(Calendar.DAY_OF_YEAR, -7);
		GregorianCalendar endFirstEvt = (GregorianCalendar) startFirstEvt.clone();
		endFirstEvt.add(Calendar.HOUR_OF_DAY, 2);
		
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.add(Calendar.DAY_OF_YEAR, +21);
		
		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startFirstEvt);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endFirstEvt);

		String recurrence = "FREQ=WEEKLY;" +
		"BYDAY="+TestIface.convertDayOfWeekToICALString(startFirstEvt)+";" +
		"UNTIL="+TestIface.convertToCalendarToString(endDate)+"Z";
		
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";
		
		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);
		
		Assert.assertTrue(TreatmentPlanningToiCal.isToday(wt));
	}


	@Test
	public void testNotToday() throws Exception{
		GregorianCalendar startFirstEvt = new GregorianCalendar();
		startFirstEvt.add(Calendar.DAY_OF_WEEK, -5);
		GregorianCalendar endFirstEvt = (GregorianCalendar) startFirstEvt.clone();
		endFirstEvt.add(Calendar.HOUR_OF_DAY, 2);

		GregorianCalendar endDate = new GregorianCalendar();
		endDate.add(Calendar.DAY_OF_YEAR, +21);
		
		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startFirstEvt);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endFirstEvt);

		String recurrence = "FREQ=WEEKLY;" +
		"BYDAY="+TestIface.convertDayOfWeekToICALString(startFirstEvt)+";" +
		"UNTIL="+TestIface.convertToCalendarToString(endDate);
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";
		
		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);
		
		Assert.assertFalse(TreatmentPlanningToiCal.isToday(wt));
	}
}
