package na.services.scheduler.evaluation;

import java.util.Calendar;
import java.util.GregorianCalendar;

import na.miniDao.Advise;
import na.miniDao.AdviseTime;
import na.oasisUtils.profile.ProfileConnector;
import na.services.scheduler.ExtraAdvise;
import na.services.scheduler.Juez;
import na.utils.AdviseRepository;
import na.utils.MiniCalendar;
import na.utils.ServiceInterface;
import na.utils.Utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TimeEvaluator {
	private Log log = LogFactory.getLog(TimeEvaluator.class);	
	private static final int STATE_Actual = 1;
	private static final int STATE_Future = 2;
	private static final int STATE_Old = 3;
//	private static final int REPETITION_MINUTES = 1;
//	private static final int REPETITION_DAYS = 2;
//	private static final int REPETITION_SET_DAYS = 3;
//	
//	private static final int SHOW_TIME_ANY = 1;
//	private static final int SHOW_TIME_SET_TIMES = 2;
	
	private static final int DAY_FREQUENCY_Daily		= 1;
	private static final int DAY_FREQUENCY_Weekly	= 2;
//	private static final int DAY_FREQUENCY_Every2Weeks	= 3;
//	private static final int DAY_FREQUENCY_Every3Weeks	= 4;
//	private static final int DAY_FREQUENCY_Monthly	= 5;
//	
//	private static final int TIME_FREQUENCY_15min 	= 1;
//	private static final int TIME_FREQUENCY_30min 	= 2;
//	private static final int TIME_FREQUENCY_Hourly 	= 3;
	
/**
 * Check temp rules of an advise. Makes sure that the advice is in the right time range.
 * 
 * @param item the item
 * @return the int
 */
public int checkTempRules(ExtraAdvise item) {
		
		//check if it's already in the queue for the publicist
		if (item.isSentToPublicist()==true) {
			log.info("  item: "+item.advise.getTitle() + " is already in the queue for the publicist");
			return Juez.ACTION_IGNORE;
		}
		
		// get Event's state
		int state = checkDateInterval(Utils.Dates.getJavaCalendar(item.advise.getStartDate()),
				Utils.Dates.getJavaCalendar(item.advise.getEndDate()));
//		log.info("J: estado: "+state);
		// old, actual or future? Act wisely
		switch (state) {
		case STATE_Old:
			return Juez.ACTION_DESTROY;
			
		case STATE_Future:
			return Juez.ACTION_IGNORE;
			
		case STATE_Actual:
			if (this.checkFrequencyInterval(item)==true) {
//				log.info("T: intervalo corrrecto");
				return Juez.ACTION_PUBLISH;
			} else {
//				log.info("T: intervalo incorrecto");
				return Juez.ACTION_IGNORE;
			}
//			if (item.advise.getTimeInfo().isIsAnyTime()==true ||
//				item.advise.getTimeInfo().isIsAnyTime()==false && this.checkTimeInterval(item)==true) {
//				if (this.checkFrequencyInterval(item)==true) {
//					log.info("T: intervalo corrrecto");
//					return Juez.ACTION_PUBLISH;
//				} else {
//					log.info("T: intervalo incorrecto");
//					return Juez.ACTION_IGNORE;
//				}
//				
//			} else {
//				log.info("T: Ignorar!");
//				return Juez.ACTION_IGNORE;
//			}
			
		default:
			log.error("T: Unknown state!");
			break;
		}
		
		return Juez.ERROR;
	}
	
	
	/**
	 * Returns the current state of the Event. Old, actual or future. According to the start and end dates.
	 * 
	 * @param start the from
	 * @param end the to
	 * @return the int
	 */
	private int checkDateInterval(Calendar start, Calendar end) {
//		log.info("T: start: "+start);
//		log.info("T: end: "+end);
		Calendar nowCalendar = Utils.Dates.getMyCalendar();
//		log.info("T: Now: "+nowCalendar);
		if (nowCalendar.after(end)) {
//			log.info("T: It's an old advise: "+end.getTime());
			return STATE_Old;
		} else if (nowCalendar.before(start)) {
//			log.info("T: It's a future advise: "+start.getTime());
			return STATE_Future;
		}
//		log.info("T: It's a current advise, starts at: "+start.getTime() + " ends at: "+end.getTime());
		return STATE_Actual;
	}
	
	/**
	 * Given a Advise, it checks all it's times.
	 * if > --- time  NOW time+1h  ------->
	 * 		the event is happening around now
	 * 
	 * @param item the item
	 * @return true, if successful
	 */
	private boolean checkTimeInterval(ExtraAdvise item) {
		// check that it hasn't been shown yet
		MiniCalendar cal = AdviseRepository.exists(item.advise.getID());
		Calendar adviceCal = null;
		Calendar currentCal = Utils.Dates.getMyCalendar();
		if (cal!=null) {
			adviceCal = Utils.Dates.getMyCalendar();
			adviceCal.set(cal.year, cal.month, cal.day, cal.hour, cal.minute);
		} else {
//			adviceCal = Utils.Dates.getMyCalendar();
//			System.out.println("calendar set... revisar");
//			adviceCal.set(cal.year, cal.month, cal.day, cal.hour, cal.minute);
		}
		
		if (item.advise.getTimeInfo()==null) {
			log.error("Advise getTimeInfo is null: "+item.advise.getTitle());
			return false;
		}
		
		// show at any time?
		// si se muestra a cualquier hora y se ha mostrado hoy, no mostrar de nuevo
		if (item.advise.getTimeInfo()!=null && item.advise.getTimeInfo().isIsAnyTime() && adviceCal!=null && Utils.Dates.isSameDay(currentCal, adviceCal)) {
			log.info("  T: Advice for anytime was shown today already: "+ item.advise.getTitle());
			return false;
		}
		// si se muestra a cualquier hora y NO se ha mostrado hoy, SI mostrar de nuevo
		if (item.advise.getTimeInfo()!=null && item.advise.getTimeInfo().isIsAnyTime() && adviceCal==null) {
//			log.info("T: Advice may show at any time: "+ item.advise.getTitle());
			return true;
		}
		
		// check activation times
		AdviseTime[] timesArray = item.advise.getTimeInfo().getTimes();
		//loop selected times
		if (timesArray!=null) {
			for (AdviseTime adviseTime : timesArray) {
				String timme = "";
				// use patient's time?
				if (adviseTime.isUsePatientTime()) {
					switch (adviseTime.getMealType()) {
						case ServiceInterface.MealCategory_BreakFast:
							// get breakfast time from Profile
							log.info("  T: showing advice at patient's breakfast time");
							timme = ProfileConnector.getInstance().getBreakfastTime();
							break;
							
						case ServiceInterface.MealCategory_Lunch:
							// get lunch time from Profile
							log.info("  T: showing advice at patient's lunch time");
							timme = ProfileConnector.getInstance().getLunchTime();
							break;
							
						case ServiceInterface.MealCategory_Dinner:
							// get dinner time from Profile
							log.info("  T: showing advice at patient's dinner time");
							timme = ProfileConnector.getInstance().getDinnerTime();
							break;
							
						default:
							log.error("Wrong meal category!!!");
							continue;
					}
					boolean result = this.checkTime(item.advise, timme, adviceCal);
					if (result)
						return true;
				} else {
					// use custom time
					boolean result = this.checkTime(item.advise, adviseTime.getTime(), adviceCal);
					if (result)
						return true;
				}
			}
		} else {
			log.error("T: Times is null! anytime: "+item.advise.getTimeInfo().isIsAnyTime());
			if (item.advise.getTimeInfo()!=null && item.advise.getTimeInfo().isIsAnyTime()) {
				log.info("  T: show at anytime: show now!");
				return true;
			}
		}
				
		return false;
	}
	
	private boolean checkTime(Advise advise, String timeString, Calendar previousCal) {
		Calendar nowCalendar = Utils.Dates.getMyCalendar();
		String[] items = timeString.split(":");
		Calendar startEvent = Calendar.getInstance();
		int value_hour = new Integer(items[0]).intValue();
		int value_minute = new Integer(items[1]).intValue();
//		log.info("T: HORA DEL DIA::::::: >"+value+"<");
		startEvent.set(Calendar.HOUR_OF_DAY, value_hour);
		startEvent.set(Calendar.MINUTE, value_minute);
		startEvent.add(Calendar.SECOND, -1);
		Calendar hourLater = (Calendar) startEvent.clone();
		hourLater.add(Calendar.HOUR_OF_DAY, 1);
		log.info("  T: Ahora son las: "+nowCalendar.getTime()+" .El advise se debe ejecutar entre las: "+startEvent.getTime()+ " y las: " +hourLater.getTime());
		if (previousCal!=null) {
//			log.info("T: El advise fue mostrado a las: "+previousCal.getTime());
//			log.info("T: startEvent: "+startEvent.getTime());
//			log.info("T: previousCal: "+previousCal.getTime());
//			log.info("T: hourLater: "+hourLater.getTime());
//			if (previousCal.before(startEvent) && previousCal.before(hourLater)) {
			if (startEvent.before(previousCal) && previousCal.before(hourLater)) {
				log.info("  T: El advise : "+ advise.getTitle() +" YA fue mostrado a las: "+previousCal.getTime());
				return false;
			} else {
//				log.info("T: Quiza deba mostrarse de nuevo");
				if (startEvent.before(nowCalendar) && nowCalendar.before(hourLater)) {
					log.info("  T: >> Se muestra de nuevo :D");
					return true;
				} else {
					log.info("  T: >> se mostró anteriormente pero todavia no ha llegado el momento, no mostrar.");
				}
			}
		} else {
			if (startEvent.before(nowCalendar) && nowCalendar.before(hourLater)) {
				log.info("  T: Hora correcta. Mostrar. Ahora son las: "+nowCalendar.getTime());
				return true;
			} else {
				log.info("  T: No es la hora correcta. Ahora son las: "+nowCalendar.getTime());
			}
		}
		return false;
	}
	
	private boolean checkFrequencyInterval(ExtraAdvise item) {
		/*
		 * Daily -> continue
		 * Weekly -
		 */
		switch (item.advise.getFrequency()) {
		case DAY_FREQUENCY_Daily:
//			log.info("T: DAILY ADVICE");
			// don't need to check activation day
			return this.checkTimeInterval(item);
		//////////////////////////////////////////////
		case DAY_FREQUENCY_Weekly:
//			log.info("T: WEEKLY ADVICE");
			// check if current day is in activation days list
			int today = Utils.Dates.getIntToday();
			boolean found = false;
			for (int activeDay : item.advise.getActiveOnDays()) {
				if (activeDay == today) {
					found = true;
					continue;
				}
			}
			if (found) {
				return this.checkTimeInterval(item);
			} else {
				return false;
			}
		////////////////////////////////////////////////
		default:
			log.error("WRONG ADVICE FREQUENCY!!");
			break;
		}
		
//		if (item.getLastTimeShown()==null) {
//			log.info(item.advise.getTitle() + "J: getLastTimeShown is null");
////			noPreviousTime = true;
//			return true;
//		} 
//
//		Calendar cal = Utils.Dates.getMyCalendar();
//		log.info("T: " + item.advise.getTitle() + " hourFrequency: "+item.getHour_frequency());
//		if (item.getHour_frequency() == TIME_FREQUENCY_15min) {
//			long now = cal.getTimeInMillis();
//			long itemTime = item.getLastTimeShown().getTimeInMillis();
//			long dif = now - itemTime;
//			if ((dif / 1000 / 60) < 15) {
//				log.info("T: >>>>>>> todavía no ha transcurrido 15 min");
//				return false;
//			} else {
//				log.info("T: >>>>>>> ya ha transcurrido 15 min");
//				return true;
//			}
//		} else if (item.getHour_frequency() == TIME_FREQUENCY_30min) {
//			long now = cal.getTimeInMillis();
//			long itemTime = item.getLastTimeShown().getTimeInMillis();
//			long dif = now - itemTime;
//			if ((dif / 1000 / 60) < 30) {
//				log.info("T: >>>>>>> todavía no ha transcurrido 30 min");
//				return false;
//			} else {
//				log.info("T: >>>>>>> ya ha transcurrido 30 min");
//				return true;
//			}
//		} else if (item.getHour_frequency() == TIME_FREQUENCY_Hourly) {
//			long now = cal.getTimeInMillis();
//			long itemTime = item.getLastTimeShown().getTimeInMillis();
//			long dif = now - itemTime;
//			if ((dif / 1000 / 60) < 60) {
//				log.info("T: >>>>>>> todavía no ha transcurrido una hora");
//				return false;
//			} else {
//				log.info("T: >>>>>>> ya ha transcurrido una hora");
//				return true;
//			}
//		} else {
//			log.info("T: Unknown HourFrequencyInterval: "+ item.getHour_frequency());
//		}
//		
//		return false;
		return true;
	}
}
