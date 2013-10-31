/*******************************************************************************
 * Copyright 2013 Universidad Polit�cnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.AALApplication.health.motivation.schedulingTools;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import net.fortuna.ical4j.model.Date;

import org.universAAL.ontology.health.owl.PerformedSession;
import org.universAAL.ontology.health.owl.Treatment;

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
