package org.universAAL.AALApplication.health.motivation.testSupportClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;

public class PerformedSessionsSupport {

	public ArrayList <PerformedSession> validPerformedSessions; //sesiones realizadas dentro del intervalo correcto
	public ArrayList <PerformedSession> invalidPerformedSessions; // sesiones realizadas fuera del intervalo
	public ArrayList <PerformedSession> nonPerformedSessions;

	public int numberOfConsecutiveNonPerformedSessionsCont; 
	public int numberOfConsecutiveNonPerformedSessionsAct; 
	public int numberOfConsecutiveNonPerformedSessionsMan;
	
	private Treatment associatedTreatment;
	
	public static Map<Treatment, PerformedSessionsSupport> mapOfPSSupport = new HashMap<Treatment, PerformedSessionsSupport>();
	
	public static final int numberOfNonPerformanceAllowed = 3;
	public static final int numberOfNonPerformanceAllowedMaintenance = 5;
	
	
	public PerformedSessionsSupport(){
		
		this.validPerformedSessions  = new ArrayList <PerformedSession>();
		this.invalidPerformedSessions = new ArrayList <PerformedSession>(); 
		
		this.setNumberOfConsecutiveNonPerformedSessionsAct(0);
		this.setNumberOfConsecutiveNonPerformedSessionsCont(0);
		this.setNumberOfConsecutiveNonPerformedSessionsMan(0);
	}
	
	public PerformedSessionsSupport(Treatment t){
		PerformedSessionsSupport pss = new PerformedSessionsSupport();
		pss.setAssociatedTreatment(t);
		mapOfPSSupport.put(t,pss);
	}
	
	public void setAssociatedTreatment(Treatment t){
		associatedTreatment=t;
		
	}
	
	public Treatment getAssociatedTreatment (){
		return associatedTreatment;
	}
	
	public void setNumberOfConsecutiveNonPerformedSessionsCont (int number){
		this.numberOfConsecutiveNonPerformedSessionsCont = number;
	}
	
	public void setNumberOfConsecutiveNonPerformedSessionsAct (int number){
		this.numberOfConsecutiveNonPerformedSessionsAct = number;
	}
	
	public void setNumberOfConsecutiveNonPerformedSessionsMan (int number){
		this.numberOfConsecutiveNonPerformedSessionsMan = number;
	}
	
	
	public int getNumberOfConsecutiveNonPerformedSessionsCont (){
		 return numberOfConsecutiveNonPerformedSessionsCont;
	}
	
	public int getNumberOfConsecutiveNonPerformedSessionsAct (){
		return numberOfConsecutiveNonPerformedSessionsAct;
	}
	
	public int getNumberOfConsecutiveNonPerformedSessionsMan (){
		return numberOfConsecutiveNonPerformedSessionsMan;
	}
	
	public void insertValidPerformedSession(PerformedSession ps){
		validPerformedSessions.add(ps);
	}
	
	public  ArrayList <PerformedSession> getValidPerformedSessions(){
		return validPerformedSessions;
	}
	
	public  void insertInvalidPerformedSession(PerformedSession ps){
		invalidPerformedSessions.add(ps);
	}
	
	public ArrayList <PerformedSession> getInvalidPerformedSessions(){
		return invalidPerformedSessions;
	}
	
	
	/*
	public  int getNumberOfValidPerformedSessions(){
		return validPerformedSessions.size();
	}
	
	public  int getNumberOfInvalidPerformedSessions(){
		return validPerformedSessions.size();
	}
	
	public  void increaseNumberOfContemplationNonPerformedSessions(){
		numberOfConsecutiveNonPerformedSessionsCont++;
	}
	
	public  void increaseNumberOfActionNonPerformedSessions(){
		numberOfConsecutiveNonPerformedSessionsAct++;
	}

	public  void increaseNumberOfMaintenanceNonPerformedSessions(){
		numberOfConsecutiveNonPerformedSessionsMan++;
	}

	public  int getNumberOfContemplationConsecutiveNonPerformedSessions(){
		return numberOfConsecutiveNonPerformedSessionsCont;
	}
	
	public  int getNumberOfActionConsecutiveNonPerformedSessions(){
		return numberOfConsecutiveNonPerformedSessionsAct;
	}

	public  int getNumberOfMaintenanceConsecutiveNonPerformedSessions(){
		return numberOfConsecutiveNonPerformedSessionsMan;
	}
	*/
}
