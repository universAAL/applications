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
