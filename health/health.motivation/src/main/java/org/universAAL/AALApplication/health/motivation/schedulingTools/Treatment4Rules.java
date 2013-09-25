package org.universAAL.AALApplication.health.motivation.schedulingTools;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.Walking;

/**
 * An extension of the Treatment that adds methods used for rules.
 * @author mdelafuente
 *
 */
public class Treatment4Rules extends Treatment{

	public Treatment4Rules(String tName, String tDescription, TreatmentPlanning tp, String diseaseURI){
		super(tName,tDescription,tp, diseaseURI);
	}

	public Treatment4Rules(Treatment t){
		super(t.getName(), t.getDescription(), t.getTreatmentPlanning(), t.getAssociatedDiseaseURI());
	}

	/**
	 * returns the current timestamp as {@link XMLGregorianCalendar}
	 * @return
	 * @throws Exception
	 */
	public XMLGregorianCalendar getNow() throws Exception{
		GregorianCalendar gc = new GregorianCalendar();
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar now = dtf.newXMLGregorianCalendar(gc);
		return now;
	}

	//	/**
	//	 * Not implemented.
	//	 */
	//	public void setNow(XMLGregorianCalendar time){
	//		throw new UnsupportedOperationException("Set NOW does not mean anything !");
	//	}

	/**
	 * Gives the start time and date of the last session of this treatment according to the plan.
	 * @return the start time of the last planned session, null if no planned session is available.
	 */
	public XMLGregorianCalendar getLastSessionStart() throws Exception{

		DateTime lastSession = SchedulingTools.getLastPlannedSession(this);
		
		if(lastSession == null)
			return null;
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(lastSession.getTime());
		
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar lss = dtf.newXMLGregorianCalendar(gc);

		return lss;
	}

	/**
	 * Returns the last session end time, null if no last session.
	 * @return
	 * @throws Exception
	 */
	public XMLGregorianCalendar getLastSessionEnd() throws Exception{

		DateTime lastSession = SchedulingTools.getLastPlannedSession(this);
		if(lastSession == null)
			return null;
		
		VEvent session = SchedulingTools.tpToEvent(this);

		// sacar el momento de finalizaci�n
		int duration = (int) (session.getEndDate().getDate().getTime() - session.getStartDate().getDate().getTime());

		Calendar lsEndDate = Calendar.getInstance();
		lsEndDate.setTimeInMillis(lastSession.getTime());
		lsEndDate.add(Calendar.MILLISECOND, duration);

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(lsEndDate.getTimeInMillis());

		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar lse = dtf.newXMLGregorianCalendar(gc);

		return lse;
	}


	public XMLGregorianCalendar getLastSessionEndWithExtraTime() throws Exception{

		DateTime lastSession = SchedulingTools.getLastPlannedSession(this);
		VEvent session = SchedulingTools.tpToEvent(this);
		
		int sessionDuration = (int) (session.getEndDate().getDate().getTime() - session.getStartDate().getDate().getTime());
		int courtesy = sessionDuration/2; 
		int extraTime = sessionDuration + courtesy;

		Calendar lsEndDateWithExtraTime = Calendar.getInstance();
		lsEndDateWithExtraTime.setTimeInMillis(lastSession.getTime());
		lsEndDateWithExtraTime.add(Calendar.MILLISECOND, (sessionDuration + extraTime));

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(lsEndDateWithExtraTime.getTimeInMillis());

		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar lsewei = dtf.newXMLGregorianCalendar(gc);

		return lsewei;
	}
	
}

