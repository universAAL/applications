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
package org.universAAL.AALApplication.health.motivation.testSupportClasses;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.ontology.health.owl.Treatment;

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

//m�todos para insertar la sesi�n no realizada en el array que corresponda, seg�n el estado motivacional

}
