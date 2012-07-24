package org.universAAL.AALApplication.health.motivation.testSupportClasses;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universaal.ontology.health.owl.Treatment;

public class SessionNotPerformed {
	
	XMLGregorianCalendar sessionNPDate;
	Treatment sNotPerformedTreatment;
	
	public SessionNotPerformed(XMLGregorianCalendar lastSessionNPDate, Treatment t){
		setSessionNPDate(lastSessionNPDate);
		setSessionNotPerformedTreatment(t);
	}
	
	public void setSessionNPDate(XMLGregorianCalendar lastSessionNPDate){
		this.sessionNPDate=lastSessionNPDate;
	}
	
	public void setSessionNotPerformedTreatment(Treatment t){
		this.sNotPerformedTreatment = t;
	}
	
	public XMLGregorianCalendar getSessionNPDate(){
		return sessionNPDate;
	}
	
	public Treatment getSNPTtreatment(){
		return sNotPerformedTreatment;
	}

//métodos para insertar la sesión no realizada en el array que corresponda, según el estado motivacional

}
