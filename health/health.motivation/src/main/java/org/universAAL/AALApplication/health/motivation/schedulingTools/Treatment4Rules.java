package org.universAAL.AALApplication.health.motivation.schedulingTools;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

import org.junit.Test;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.TreatmentPlanning;
import org.universaal.ontology.health.owl.Walking;

public class Treatment4Rules extends Treatment{
	
	public Treatment4Rules(String tName, String tDescription, TreatmentPlanning tp, String diseaseURI){
		super(tName,tDescription,tp, diseaseURI);
	}
	
	
	public XMLGregorianCalendar getNow() throws Exception{
		GregorianCalendar gc = new GregorianCalendar();
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar now = dtf.newXMLGregorianCalendar(gc);
		return now;
	}
	
	public void setNow(){
	}
	
	public XMLGregorianCalendar getLastSessionStart(Treatment t) throws Exception{
		
		DateTime lastSession = SchedulingTools.getLastPlannedSession(t);
		
		Calendar lsStartTime = Calendar.getInstance();
		lsStartTime.setTimeInMillis(lastSession.getTime());
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(lsStartTime.getTimeInMillis());
		
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar lss = dtf.newXMLGregorianCalendar(gc);
		
		return lss;
			
		}
	
	public void setLastSessionStart(){
	}
	
	public XMLGregorianCalendar getLastSessionEnd(Treatment t) throws Exception{
		
		DateTime lastSession = SchedulingTools.getLastPlannedSession(t);
		VEvent session = SchedulingTools.tpToEvent(t);
		
		// sacar el momento de finalización
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
	
	public void setLastSessionEnd(){
		
	}
	
	public XMLGregorianCalendar getLastSessionEndWithExtraTime(Treatment t) throws Exception{
		
		DateTime lastSession = SchedulingTools.getLastPlannedSession(t);
		VEvent session = SchedulingTools.tpToEvent(t);
		
		int sessionDuration = (int) (session.getEndDate().getDate().getTime() - session.getStartDate().getDate().getTime());
		int courtesy = sessionDuration/2; 
		int extraTime = sessionDuration + courtesy;
		
		Calendar lsEndDateWithExtraTime = Calendar.getInstance();
		lsEndDateWithExtraTime.setTimeInMillis(lastSession.getTime());
		lsEndDateWithExtraTime.add(Calendar.MILLISECOND, (sessionDuration + extraTime));
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(lsEndDateWithExtraTime.getTimeInMillis());
		
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar lse = dtf.newXMLGregorianCalendar(gc);
		
		return lse;
	}
	
	public void setLastSessionEndWithExtraTime(){
		
	}

}

