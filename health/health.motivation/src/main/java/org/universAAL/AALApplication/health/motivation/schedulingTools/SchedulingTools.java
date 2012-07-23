package org.universAAL.AALApplication.health.motivation.schedulingTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.datatype.XMLGregorianCalendar;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Summary;

import org.universAAL.AALApplication.health.motivation.testSupportClasses.DetectedTreatments;
import org.universAAL.AALApplication.health.motivation.testSupportClasses.TreatmentTypeClassification;
import org.universaal.ontology.health.owl.PerformedSession;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.TreatmentPlanning;

public class SchedulingTools {

	public static Map<Treatment, DateList> mapOfListDates = new TreeMap<Treatment, DateList>();
	
	
	//-----------------------------------------------
	public static boolean treatmentStartsToday(Treatment t){
		
		Calendar nowCal = Calendar.getInstance(); //now
		
		nowCal.set(Calendar.HOUR, 0);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		
		Date today = new Date(nowCal.getTime());
		TreatmentPlanning tp = t.getTreatmentPlanning();
		Date treatmentStart = new Date(tp.getStartDate().toGregorianCalendar().getTime());
		
		if(treatmentStart.compareTo(today)==0) //es cero cuando la fecha es la misma
			return true;
		else
			return false;
	}
	
	public static boolean treatmentEndsToday(Treatment t) throws Exception{
		
		Calendar nowCal = Calendar.getInstance(); //now
		
		nowCal.set(Calendar.HOUR, 0);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		
		Date today = new Date(nowCal.getTime());
		Date treatmentEndDate = getTreatmentEndDate(t);
		
		if(treatmentEndDate.compareTo(today)==0) //es cero cuando la fecha es la misma
			return true;
		else
			return false;
	}
	public static boolean treatmentEndsAfterToday(Treatment t) throws Exception{
		
		Calendar nowCal = Calendar.getInstance(); //now
		
		nowCal.set(Calendar.HOUR, 0);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		
		Date today = new Date(nowCal.getTime()); // sólo sale la fecha, no el instante
		Date treatmentEndDate = getTreatmentEndDate(t);
		
		if(treatmentEndDate.compareTo(today)>0)
			return true;
		else
			return false;
	}
	
public static boolean treatmentEndedBeforeToday(Treatment t) throws Exception{
		
		Calendar nowCal = Calendar.getInstance(); //now
		
		nowCal.set(Calendar.HOUR, 0);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		
		Date today = new Date(nowCal.getTime()); // sólo sale la fecha, no el instante
		Date treatmentEndDate = getTreatmentEndDate(t);
		
		if(treatmentEndDate.compareTo(today)<0)
			return true;
		else
			return false;
	}

	public static Date getTreatmentEndDate(Treatment t) throws Exception{
		DateList sessions =  getPlannedSessions(t);
		int lastDateIndex = sessions.size() - 1 ;
		Date lastDateAux = (Date)sessions.get(lastDateIndex);
		
		//queremos devolver sólo la fecha, no la hora
		Calendar calFinal = Calendar.getInstance();
		calFinal.setTime(lastDateAux);
		Date lastDate = new Date (calFinal.getTime());
		return lastDate;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	public static DateTime getLastPlannedSession(Treatment t) throws Exception{
		
		DateList allSessions = getPlannedSessions(t);
		
		Calendar nowCal = Calendar.getInstance(); //now
		DateTime now = new DateTime(nowCal.getTime());
		
		for (int i=0;i<allSessions.size();i++){
			DateTime sessionDate = (DateTime)allSessions.get(i);
			DateTime nextDate = (DateTime)allSessions.get(i+1);
			if(now.after(sessionDate)&& now.before(nextDate)){
				return (DateTime)allSessions.get(i);
			}
		}
		return null;
	}


	public static boolean isNowInPerformingInterval(Treatment t) throws Exception{
		//ver la última sesión
		DateTime lastSession = getLastPlannedSession(t);
		
		VEvent session = tpToEvent(t).getOccurrence(lastSession);
		// sacar el momento de finalización
		DtEnd sessionEnd = session.getEndDate();
		// compararla con ahora: si estoy después del rango, false
		Calendar nowCal = Calendar.getInstance(); //now
		DateTime now = new DateTime(nowCal.getTime());
		
		if(now.after(sessionEnd.getDate()))
			return false;
		else
			return true;
		// si estoy dentro, true
	}
	
	public static boolean isNowInExtraInterval(Treatment t) throws Exception{
		//ver la última sesión
		DateTime lastSession = getLastPlannedSession(t);

		VEvent session = tpToEvent(t).getOccurrence(lastSession);
		// sacar el momento de finalización
		DtEnd sessionEnd = session.getEndDate();
		DtStart sessionStart = session.getStartDate();
		
		Calendar nowCal = Calendar.getInstance(); //now
		DateTime now = new DateTime(nowCal.getTime());
		
		Dur actualDistance = new Dur(sessionEnd.getDate(), now);
		Dur sessionDuration = new Dur(sessionStart.getDate(), sessionEnd.getDate());
		
		int actualDistanceInMinutes = actualDistance.getMinutes();
		int sessionDurationInMinutes = sessionDuration.getMinutes();
		int courtesy  = sessionDuration.getMinutes()/2;
		
		if( actualDistanceInMinutes <= (2*sessionDurationInMinutes + courtesy) )
			return true;
		else
			return false;
	}
	
	public boolean isMomentInPerformingInterval(DateTime moment, Treatment t) throws Exception{
		//ver la última sesión
		DateTime lastSession = getLastPlannedSession(t);
		
		VEvent session = tpToEvent(t).getOccurrence(lastSession);
		// sacar el momento de finalización
		DtEnd sessionEnd = session.getEndDate();
		
		if(moment.after(sessionEnd.getDate()))
			return false;
		else
			return true;
		// si estoy dentro, true
	}
	
	public boolean isMomentInExtraInterval(DateTime moment, Treatment t) throws Exception{
		//ver la última sesión
		DateTime lastSession = getLastPlannedSession(t);

		VEvent session = tpToEvent(t).getOccurrence(lastSession);
		// sacar el momento de finalización
		DtEnd sessionEnd = session.getEndDate();
		DtStart sessionStart = session.getStartDate();
		
		Dur actualDistance = new Dur(sessionEnd.getDate(), moment);
		Dur sessionDuration = new Dur(sessionStart.getDate(), sessionEnd.getDate());
		
		int actualDistanceInMinutes = actualDistance.getMinutes();
		int sessionDurationInMinutes = sessionDuration.getMinutes();
		int courtesy  = sessionDuration.getMinutes()/2;
		
		if( actualDistanceInMinutes <= (2*sessionDurationInMinutes + courtesy) )
			return true;
		else
			return false;
	}
	
	public boolean psInPerformingInterval(PerformedSession ps) throws Exception{
		DateTime psStartTime = new DateTime (ps.getSessionStartTime().toGregorianCalendar().getTime());
		return isMomentInPerformingInterval(psStartTime, ps.getAssociatedTreatment());
	}
	
	public boolean psInExtraInterval(PerformedSession ps) throws Exception{
		DateTime psStartTime = new DateTime (ps.getSessionStartTime().toGregorianCalendar().getTime());
		return isMomentInExtraInterval(psStartTime, ps.getAssociatedTreatment());
	}
	
	//public boolean isSessionReminderAlertSent(){
		
	//}
	
	public static boolean isPSInPerformingInterval(PerformedSession ps) throws Exception{
		DateTime psEnd = new DateTime(ps.getSessionEndTime().toGregorianCalendar().getTime());
		Treatment aTreatment = ps.getAssociatedTreatment();
		
		Date lastSession = getLastPlannedSession(aTreatment);
		VEvent session = tpToEvent(aTreatment).getOccurrence(lastSession);
		// compararla con ahora: si estoy después del rango, false
		Calendar nowCal = Calendar.getInstance(); //now
		DateTime now = new DateTime(nowCal.getTime());
		
		if(now.after(psEnd))
			return false;
		else
			return true;
		// si estoy dentro, true
	}
	
	public static void getDuration(){
		
	}
	
	
	//----------------------------------------------------------------------------------------------------
	
	/**
	 * This method converts the treatment planning associated to a treatment
	 * into a ical event
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static VEvent tpToEvent(Treatment t) throws Exception{
		
		// Firstly, the data from the treatment planning is obtained
		TreatmentPlanning tp = t.getTreatmentPlanning();
		XMLGregorianCalendar stDateForFirstEvent = tp.getStartDate();
		XMLGregorianCalendar endDateForFirstEvent = tp.getEndDate();
		String recurrence = tp.getRecurrence();
		
		// Secondly, java util dates are mapped to ical dates-time (date + time) 
		
		DateTime utilToiCalSt = new DateTime((stDateForFirstEvent.toGregorianCalendar()).getTime()); // this step converts from Date (util) to Date (iCal4Java)
		DateTime utilToiCalEnd = new DateTime((endDateForFirstEvent.toGregorianCalendar()).getTime());

		// Next, DateSt y DateEnd are defined, so then the event can be created
		DtStart firstEventSt = new DtStart(utilToiCalSt);
		DtEnd firstEventEnd = new DtEnd(utilToiCalEnd);
		
		// The recurrence rule parameter and the summary are created from the data extracted from the tp:
		RRule recurrenceRule = new RRule(recurrence);
		Summary summ = new Summary(tp.getDescription());
		
		// Finally the event is generated:
		VEvent event = new VEvent();
		event.getProperties().add(recurrenceRule);
		event.getProperties().add(firstEventSt);
		event.getProperties().add(firstEventEnd);
		event.getProperties().add(summ);
		
		return event;
	}
	
	/**
	 * This method gets all the ocurrences of an event, in date-time format
	 * @param event
	 * @return
	 * @throws Exception 
	 */
	public static DateList getPlannedSessions (Treatment t) throws Exception{
		VEvent event = tpToEvent(t);
		String recurrence = event.getProperty(Property.RRULE).getValue();
		Recur recur = new Recur(recurrence);
		DateTime seed = new DateTime (event.getStartDate().getDate()); //seed refers to the first ocurrence of the event
		
		// The list of dates is a date-time list.
		DateList plannedSessions = recur.getDates(seed, recur.getUntil(), Value.DATE_TIME);
		
		return plannedSessions;
	}
	
	
	/**
	 * This method calculates the value of the completeness unit, 
	 * attending to the number of sessions to be performed.
	 * @param list
	 * @return
	 */
	public static float calculateUnitOfCompleteness(DateList list){
		float unit = 1/list.size();
		return unit;
	}

	public static boolean isThereASessionToday(DateList sessions) throws Exception{
		
		Calendar cal = Calendar.getInstance();
		// We set the time to 0:00h
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		
		Date today = new Date(cal.getTime());
		
		if(sessions.contains(today))
			return true;
		else
			return false;
	}
	
	public static DateList getSessionsToday(DateList allSessions) throws Exception{

		DateList sessionsToday = new DateList();
		
		if(isThereASessionToday(allSessions)){
			Date today = new Date();
			for(int i=0;i<allSessions.size();i++){
				if(allSessions.get(i).equals(today))
					sessionsToday.add(allSessions.get(i));
			}
			return sessionsToday;
		}
		else{
			return sessionsToday;
		}
	}

	public static DateList getSessionsForSpecificDate(Date d, Treatment t) throws Exception{
		
		DateList allSessions = getPlannedSessions(t);
		DateList sessions = new DateList();
		
		for(int i=0;i<allSessions.size();i++){
			DateTime dt = (DateTime) allSessions.get(i);
			Date date = new Date(dt.getTime());
			if(date.equals(d))
				sessions.add(allSessions.get(i));
		}
		return sessions;
	}
	
	
	public static int getNumberOfSessionsADay(Treatment t) throws Exception{
		int numberOfSessions = 0;
		int index = 0;
		DateList list = getPlannedSessions(t);
		Date dt = (Date)list.get(index);
		while(dt.equals( (Date)list.get(index) )){
			index++;
		}
		numberOfSessions = index;
		return numberOfSessions;
	}
	
	
	public static int getTreatmentDurationInDays(Treatment t) throws Exception{
		VEvent sessionsOfTreatment = tpToEvent(t);
		int totalDays;
		String recurrence = sessionsOfTreatment.getProperty(Property.RRULE).getValue();
		Recur recur = new Recur(recurrence);
		Date firstDay = new Date (sessionsOfTreatment.getStartDate().getDate());
		Date lastDay = recur.getUntil();
		
		Dur duration = new Dur(firstDay,lastDay);
		totalDays = duration.getDays();
		return totalDays;
	}

	public static int daysUntilNextSession(Date d, DateList sessions ){
		if(sessions.contains(d)){
			int index = sessions.indexOf(d);
			int nextSessionIndex = index ++;
			Dur time = new Dur(d, (Date)sessions.get(nextSessionIndex));
			return time.getDays();
		}
		else{
			int i = 0;
			while(d.before((Date)sessions.get(i))){
				i++;
			}
			int nextSessionIndex = i;
			Dur time = new Dur(d, (Date)sessions.get(nextSessionIndex));
			return time.getDays();
		}
	}
	
	
	public static int hoursUntilNextSession(DateTime d, DateList sessions ){
		if(sessions.contains(d)){
			int index = sessions.indexOf(d);
			int nextSessionIndex = index ++;
			Dur time = new Dur(d, (DateTime)sessions.get(nextSessionIndex));
			return time.getHours();
		}
		else{
			int i = 0;
			while(d.before((Date)sessions.get(i))){
				i++;
			}
			int nextSessionIndex = i;
			Dur time = new Dur(d, (DateTime)sessions.get(nextSessionIndex));
			return time.getHours();
		}
	}
	
	public static boolean isDayDefined(Treatment t) throws Exception{
		String recurrence = t.getTreatmentPlanning().getRecurrence();
		if(recurrence.contains("BYDAY"))
			return true;
		else
			return false;
		
	}
	
	public static boolean isHourDefined(Treatment t) throws Exception{
		
		XMLGregorianCalendar stDateForFirstEvent = t.getTreatmentPlanning().getStartDate();
		XMLGregorianCalendar endDateForFirstEvent = t.getTreatmentPlanning().getEndDate();
		
		GregorianCalendar realSt = stDateForFirstEvent.toGregorianCalendar();
		GregorianCalendar realEnd = endDateForFirstEvent.toGregorianCalendar();
		//DateTime realSt = new DateTime((stDateForFirstEvent.toGregorianCalendar()).getTime()); // this step converts from Date (util) to Date (iCal4Java)
		//DateTime realEnd = new DateTime((endDateForFirstEvent.toGregorianCalendar()).getTime());

		Calendar calIni = Calendar.getInstance();
		calIni.set(Calendar.HOUR, 0);
		calIni.set(Calendar.MINUTE,0);
		calIni.set(Calendar.SECOND,0);
		
		Calendar calFin = Calendar.getInstance();
		calFin.set(Calendar.HOUR, 0);
		calFin.set(Calendar.MINUTE,1);
		calFin.set(Calendar.SECOND,0);
		
		
		if(realSt.HOUR==calIni.HOUR && realSt.MINUTE==calIni.MINUTE && realSt.SECOND == calIni.SECOND &&
		realEnd.HOUR==calFin.HOUR && realEnd.MINUTE==calFin.MINUTE && realEnd.SECOND == calFin.SECOND)
			return false;
		else
			return true;
		
	}
	
	public static boolean isEndDateDefined(Treatment t){
		String recurrence = t.getTreatmentPlanning().getRecurrence();
		
		if (recurrence.contains("UNTIL") || recurrence.contains("COUNT"))
			return true;
		else
			return false;
	}
	
	public static boolean isLessThanAMonth(Treatment t) throws Exception{
		int days = getTreatmentDurationInDays(t);
		if(days <= 30)
			return true;
		else
			return false;
	}
	
	public static boolean isLessThanAYear(Treatment t) throws Exception{
		int days = getTreatmentDurationInDays(t);
		if(days > 180 && days <= 365)
			return true;
		else
			return false;
	}
	
	public static void setListOfSessions(Treatment t) throws Exception{
		mapOfListDates.put(t, getPlannedSessions(t));
	}
	
	public static boolean hasAssociatedListOfSessions(Treatment t){
		if(mapOfListDates.containsKey(t))
			return true;
		else
			return false;
	}
	
	public static boolean treatmentPerformed4To5ADay(Treatment t) throws Exception{
		int number = getNumberOfSessionsADay(t);
		if (number==4 || number==5)
			return true;
		else 
			return false;
	}
	
	public static boolean treatmentPerformed2To3ADay(Treatment t) throws Exception{
		int number = getNumberOfSessionsADay(t);
		if (number==2 || number==3)
			return true;
		else 
			return false;
	}
	
	public static boolean treatmentPerformedOnceADay(Treatment t) throws Exception{
		int number = getNumberOfSessionsADay(t);
		if (number==1)
			return true;
		else 
			return false;
	}
	
	public static boolean sessionPerformedAfter15minutes(Treatment t, PerformedSession ps) throws Exception{
		boolean performed;
		
		XMLGregorianCalendar sStart = ps.getSessionStartTime();
		XMLGregorianCalendar sEnd = ps.getSessionEndTime();
		
		DateTime utilToiCalSessionSt = new DateTime((sStart.toGregorianCalendar()).getTime()); // this step converts from Date (util) to Date (iCal4Java)
		DateTime utilToiCalSessionEnd = new DateTime((sEnd.toGregorianCalendar()).getTime());
		
		DateList sessions = getPlannedSessions(t);
		Date specificDay = new Date((sStart.toGregorianCalendar()).getTime());
		
		DateList sessionsSpecificDay = getSessionsForSpecificDate(specificDay,t);
		VEvent event = tpToEvent(t);
		
		for(int i =0;i<sessionsSpecificDay.size();i++){
			VEvent e = event.getOccurrence( (DateTime)sessionsSpecificDay.get(i) );
			DateTime dt = new DateTime (  (e.getEndDate()).getValue()  );
			Dur dur = new Dur(utilToiCalSessionEnd,dt);
			if(dur.getMinutes()<=15)
				return performed=true;
		}
		return performed=false;
	}
	
	
	public static boolean sessionPerformedAfter30minutes(Treatment t, PerformedSession ps) throws Exception{
		boolean performed;
		
		XMLGregorianCalendar sStart = ps.getSessionStartTime();
		XMLGregorianCalendar sEnd = ps.getSessionEndTime();
		
		DateTime utilToiCalSessionSt = new DateTime((sStart.toGregorianCalendar()).getTime()); // this step converts from Date (util) to Date (iCal4Java)
		DateTime utilToiCalSessionEnd = new DateTime((sEnd.toGregorianCalendar()).getTime());
		
		DateList sessions = getPlannedSessions(t);
		Date specificDay = new Date((sStart.toGregorianCalendar()).getTime());
		
		DateList sessionsSpecificDay = getSessionsForSpecificDate(specificDay,t);
		VEvent event = tpToEvent(t);
		
		for(int i =0;i<sessionsSpecificDay.size();i++){
			VEvent e = event.getOccurrence( (DateTime)sessionsSpecificDay.get(i) );
			DateTime dt = new DateTime (  (e.getEndDate()).getValue()  );
			Dur dur = new Dur(utilToiCalSessionEnd,dt);
			if(dur.getMinutes()<=30)
				return performed=true;
		}
		return performed=false;
	}
	
	public static boolean sessionPerformedAfter1Hour(Treatment t, PerformedSession ps) throws Exception{
		boolean performed;
		
		XMLGregorianCalendar sStart = ps.getSessionStartTime();
		XMLGregorianCalendar sEnd = ps.getSessionEndTime();
		
		DateTime utilToiCalSessionSt = new DateTime((sStart.toGregorianCalendar()).getTime()); // this step converts from Date (util) to Date (iCal4Java)
		DateTime utilToiCalSessionEnd = new DateTime((sEnd.toGregorianCalendar()).getTime());
		
		DateList sessions = getPlannedSessions(t);
		Date specificDay = new Date((sStart.toGregorianCalendar()).getTime());
		
		DateList sessionsSpecificDay = getSessionsForSpecificDate(specificDay,t);
		VEvent event = tpToEvent(t);
		
		for(int i =0;i<sessionsSpecificDay.size();i++){
			VEvent e = event.getOccurrence( (DateTime)sessionsSpecificDay.get(i) );
			DateTime dt = new DateTime (  (e.getEndDate()).getValue()  );
			Dur dur = new Dur(utilToiCalSessionEnd,dt);
			if(dur.getMinutes()<=60)
				return performed=true;
		}
		return performed=false;
	}
	
	public static boolean sessionPerformedAfter2Hours(Treatment t, PerformedSession ps) throws Exception{
		boolean performed;
		
		XMLGregorianCalendar sStart = ps.getSessionStartTime();
		XMLGregorianCalendar sEnd = ps.getSessionEndTime();
		
		DateTime utilToiCalSessionSt = new DateTime((sStart.toGregorianCalendar()).getTime()); // this step converts from Date (util) to Date (iCal4Java)
		DateTime utilToiCalSessionEnd = new DateTime((sEnd.toGregorianCalendar()).getTime());
		
		DateList sessions = getPlannedSessions(t);
		Date specificDay = new Date((sStart.toGregorianCalendar()).getTime());
		
		DateList sessionsSpecificDay = getSessionsForSpecificDate(specificDay,t);
		VEvent event = tpToEvent(t);
		
		for(int i =0;i<sessionsSpecificDay.size();i++){
			VEvent e = event.getOccurrence( (DateTime)sessionsSpecificDay.get(i) );
			DateTime dt = new DateTime (  (e.getEndDate()).getValue()  );
			Dur dur = new Dur(utilToiCalSessionEnd,dt);
			if(dur.getMinutes()<=120)
				return performed=true;
		}
		return performed=false;
	}
	
	// mejor devuelve un array de clases "TreatmentWithPendingSessions" que tenga asociado un treatment
	
	public ArrayList <TreatmentWithPendingSessions> areTherePendingSessionsToday () throws Exception{
		
		ArrayList <Treatment> checkTreatments = DetectedTreatments.getDetectedTreatments();
		ArrayList <TreatmentWithPendingSessions> treatmentsWPS = new ArrayList <TreatmentWithPendingSessions>();
		
		for(int i=0;i<checkTreatments.size() ;i++){
			Treatment t = checkTreatments.get(i);
			DateList psforTreatment = getPlannedSessions(t);
			if( getSessionsToday(psforTreatment).size() != 0){// hay sesiones pendientes hoy
				TreatmentWithPendingSessions twps = new TreatmentWithPendingSessions(t);
				treatmentsWPS.add(twps);
			}
		}
		
		return treatmentsWPS;
	}
}
