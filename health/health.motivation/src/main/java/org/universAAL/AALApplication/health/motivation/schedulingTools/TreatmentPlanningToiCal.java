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

import java.util.Calendar;
import javax.xml.datatype.XMLGregorianCalendar;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;

import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;

public class TreatmentPlanningToiCal {


	private static VEvent translateToiCal(Treatment t) throws Exception{

		// First, the data from the treatment planning is obtained
		TreatmentPlanning tp = t.getTreatmentPlanning();
		XMLGregorianCalendar stDateForFirstEvent = tp.getStartDate();
		XMLGregorianCalendar endDateForFirstEvent = tp.getEndDate();
		String recurrence = tp.getRecurrence();

		Date utilToiCalSt = new Date((stDateForFirstEvent.toGregorianCalendar()).getTime()); // this step converts from Date (util) to Date (iCal4Java)
		Date utilToiCalEnd = new Date((endDateForFirstEvent.toGregorianCalendar()).getTime());

		DtStart firstEventSt = new DtStart(utilToiCalSt);
		DtEnd firstEventEnd = new DtEnd(utilToiCalEnd);

		RRule recurrenceRule = new RRule(recurrence);
		//Recur recur = recurrenceRule.getRecur();
		//Date treatmentEndDate = recur.getUntil(); // date de ical4java
		//treatmentDuration = new Dur(stDate, treatmentEndDate);

		Summary summ = new Summary(tp.getDescription());
		
		Uid uid = new Uid("blabla");
		
		// The event is created
		VEvent walkingSession = new VEvent();
		walkingSession.getProperties().add(recurrenceRule);
		walkingSession.getProperties().add(firstEventSt);
		walkingSession.getProperties().add(firstEventEnd);
		walkingSession.getProperties().add(summ);
		walkingSession.getProperties().add(uid);
		
		return walkingSession;

		//return null;	
	}

	public static boolean isToday(Treatment t) throws Exception {
		
		VEvent event = translateToiCal(t);
		System.out.println("EVT  "+event);
		event.validate();
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		
		PeriodList pl = event.calculateRecurrenceSet(new Period(new DateTime(todayStart.getTimeInMillis()), new Dur(1, 0, 0, 0)));
		System.out.println("hey "+pl);
		
		pl = event.getConsumedTime(new Date(todayStart.getTimeInMillis()), new Date(todayEnd.getTimeInMillis()));
		
		System.out.println("hoy "+pl);
		
		if(pl.isEmpty())
			return false;
		else
			return true;
	}

}
