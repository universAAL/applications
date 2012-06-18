package org.persona.service.helpwhenoutside.common;

import javax.xml.datatype.XMLGregorianCalendar;

public class AgendaEvent {
	public static final double UNKNOWN_POSITION=200;
	
	public AgendaEvent(){
		
		
	}
	
	/**
	 * The time when the event has started, 
	 * expressed as an XMLGregorianCalendar object
	 */
	public XMLGregorianCalendar beginTime = null;
	
	/**
	 * The time when the event will finish, 
	 * expressed as an XMLGregorianCalendar object 
	 */
	public XMLGregorianCalendar endTime = null;
	
	/**
	 * The address where the event will take place 
	 */
	public String address = "";
	
	/**
	 * The latitude coordinate of the event
	 */
	public double latitude = 0.0;
	/**
	 * The longitude coordinate of the event
	 */
	public double longitude = 0.0;
	
	/**
	 * The agenda event univocal ID (retrieved from agenda service)
	 */
	public int eventID =0;
	
	/**
	 * The agenda event description (this value can be null)
	 */
	public String description;
}
