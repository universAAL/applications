package org.universAAL.AALApplication.health.motivation.schedulingTools;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import net.fortuna.ical4j.model.Date;

import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;

public class SessionPerformance {

	public  ArrayList <PerformedSession> outOfDatePerformedSessions = new ArrayList <PerformedSession>();
	public  ArrayList <PerformedSession> performedSessions = new ArrayList <PerformedSession>();
	public 	ArrayList <Date> nonPerformedSessions = new ArrayList <Date>();
	
	public Treatment associatedTreatment;
	
	static Map<Treatment, SessionPerformance> mapOfPerformances = new TreeMap<Treatment, SessionPerformance>();
	
	public SessionPerformance (Treatment t){
		this.setAssociatedTreatment(t);
		mapOfPerformances.put(t,this);
	}
	
	public void setAssociatedTreatment(Treatment t){
		associatedTreatment = t;
	}
	
	public static SessionPerformance getPerformances (Treatment t){
		SessionPerformance sp = mapOfPerformances.get(t);
		return sp;
	}
	public Treatment getAssociatedTreatment (){
		return associatedTreatment;
	}
	public void addOutOfDateSession(PerformedSession ps){
		outOfDatePerformedSessions.add(ps);
	}
	
	public ArrayList <PerformedSession> getOutOfDateSessions(){
		return outOfDatePerformedSessions;
	}
	
	public void addPerformedSession(PerformedSession ps){
		performedSessions.add(ps);
	}
	
	public ArrayList <PerformedSession> getPerformedSessions(){
		return performedSessions;
	}
	
	public void addNotPerformedSession(Date date){
		nonPerformedSessions.add(date);
	}
	
	public ArrayList <Date> getNotPerformedSessions(){
		return nonPerformedSessions;
	}
	
}
