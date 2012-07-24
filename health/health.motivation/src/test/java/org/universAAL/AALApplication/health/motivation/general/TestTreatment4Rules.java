package org.universAAL.AALApplication.health.motivation.general;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.schedulingTools.Treatment4Rules;
import org.universAAL.AALApplication.health.motivation.treatment.TestIface;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.TreatmentPlanning;
import org.universaal.ontology.health.owl.Walking;

public class TestTreatment4Rules extends TestIface{
	
	private SendMotivationMessageIface iTestTreatment = this;
	private MotivationServiceRequirementsIface iTestStatusForMMVariables = this;
	

	@Before
	public void setUp(){
		registerClassesNeeded();
	}
	
	public Treatment4Rules generateTreatment4Test() throws Exception{

		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();

		startDate.add(Calendar.DAY_OF_YEAR, -1); //comenzó hace 10 días
		endDate.add(Calendar.DAY_OF_YEAR, -1); // terminó en 10 días

		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);

		firstEventStartDate.setTime(20, 0, 0);
		firstEventEndDate.setTime(20, 30, 0);

		String recurrence = "FREQ=WEEKLY;BYDAY=SU;UNTIL=20121122T235959"; // cambiar el día manualmente
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";

		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Treatment4Rules wt = new Treatment4Rules("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);

		return wt;
	}

	@Test
	public void testNow() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test();
		System.out.println("Now" + t4r.getNow());
	}

	@Test
	public void testgetLastSessionStart() throws Exception{
		Treatment4Rules t4r = generateTreatment4Test();
		XMLGregorianCalendar fecha = t4r.getLastSessionStart(t4r);
		System.out.println("Last session start:" + t4r.getLastSessionStart(t4r));
	}

}
