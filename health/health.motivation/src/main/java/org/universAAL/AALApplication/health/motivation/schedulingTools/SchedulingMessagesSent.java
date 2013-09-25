package org.universAAL.AALApplication.health.motivation.schedulingTools;

import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;

import org.universAAL.ontology.owl.Message;
import org.universAAL.ontology.owl.MotivationalMessage;

public class SchedulingMessagesSent {

	public static ArrayList <MotivationalMessage> messagesSent = new ArrayList <MotivationalMessage>();
	
	public static void sendMessage(MotivationalMessage mm){
		messagesSent.add(mm);
	}
	
	public static ArrayList <MotivationalMessage> getMessagesSentToday(){
		
		Calendar nowCal = Calendar.getInstance(); //now
		Date today = new Date(nowCal.getTime());
		ArrayList <MotivationalMessage> messagesSentToday = new ArrayList<MotivationalMessage>();
		
		for(int i=0;i<messagesSent.size();i++){
			MotivationalMessage mm = messagesSent.get(i);
			XMLGregorianCalendar date = mm.getSentDate();
			Date sentDate = new Date(date.toGregorianCalendar().getTime());
			if(sentDate.compareTo(today)== 0)
				messagesSentToday.add(messagesSent.get(i));
		}
		return messagesSentToday;
	}
	
}
