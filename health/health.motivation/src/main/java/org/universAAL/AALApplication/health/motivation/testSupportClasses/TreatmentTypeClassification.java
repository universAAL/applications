package org.universAAL.AALApplication.health.motivation.testSupportClasses;

import java.util.HashMap;
import java.util.Map;


import org.universaal.ontology.health.owl.Treatment;

public class TreatmentTypeClassification {

	/**
	 * A treatment will be short_term if it lasts for less than a month.
	 * A treatment will be medium_term if it lasts for less than 6 months.
	 * A treatment will be long_term if it lasts more than 6 months, and less than 18 months.
	 * A treatment will be from_now_on if it lasts more than 18 months.
	 */
	
	public static Map<Treatment, TreatmentTypeClassification> mapOfTreatmentTypes = new HashMap<Treatment, TreatmentTypeClassification>();
	// utiliza put y get
	
	private Treatment associatedTreatment;
	
	public enum ByDuration{
		SHORT_TERM, MEDIUM_TERM, LONG_TERM, FROM_NOW_ON
	}
	
	public enum ByRecurrence{
		FIXED, FLEXIBLE_DEFINED, FLEXIBLE_CUSTOM
		//A treatment is fixed when the day and hour is defined
		//A treatment is flexible_defined when the day is defined but the hour is not
		//A treatment is flexible_custom when neither the day or the hour are defined.
		
		// A treatment is considered to be hourless defined when the start hour is 0:00
		// and the end hour is 0:01
	}
	
	public enum ByMeasurement{
		WITH_MEASUREMENT, WITHOUT_MEASUREMENT
	}
	
	public ByRecurrence trecur;
	public ByDuration tdur;
	public ByMeasurement tmeasure;
	
	// CONSTRUCTORS
	public TreatmentTypeClassification(ByDuration dur, ByRecurrence recur, ByMeasurement measurement){
		//cuando detectemos un treatment, sabremos si necesita o no medidas, y en base al treatment planning
		//conoceremos la duración y la recurrencia
		this.setTreatmentByDuration(dur);
		this.setTreatmentByRecurrence(recur);
		this.setTreatmentByMeasurement(tmeasure);
	
	}
	
	public TreatmentTypeClassification(Treatment t){
		this.setAssociatedTreatment(t);
		mapOfTreatmentTypes.put(t,this);
	}
	
	
	
	public void setAssociatedTreatment(Treatment t){
		associatedTreatment=t;
		
	}
	
	public ByDuration getTreatmentByDuration(){
		return tdur;
	}
	
	public void setTreatmentByDuration(ByDuration ttype){
		tdur=ttype;
	}
	
	
	public ByRecurrence getTreatmentByRecurrence(){
		return trecur;
	}
	
	public void setTreatmentByRecurrence(ByRecurrence ttype){
		trecur=ttype;
	}
	
	
	public ByMeasurement getTreatmentByMeasurement(){
		return tmeasure;
	}
	
	public void setTreatmentByMeasurement(ByMeasurement ttype){
		tmeasure=ttype;
	}
	
	public Treatment getAssociatedTreatment(){
		return associatedTreatment;
	}
	

	//Other methods
	
	
	public void insertTreatmentInTypeMap(Treatment t){
		
	}
	
	public static void insertTypeOfTreatmentInWM(TreatmentTypeClassification ttc){
		
	}
	/*
	public static TreatmentTypeClassification establishTypeOfTreatment(Treatment t){
		
		TreatmentTypeClassification ttc = new TreatmentTypeClassification();
		
		//First the measurement-type is established
		if(t.hasMeasurementRequirements())
			ttc.setTreatmentByMeasurement(ByMeasurement.WITH_MEASUREMENT);
		else
			ttc.setTreatmentByMeasurement(ByMeasurement.WITHOUT_MEASUREMENT);
			
		//Next, the duration-type is established
		
		
		
		return ttc;
	}

	public static void establishAndInsertTypeOfTreatment(Treatment t){
		TreatmentTypeClassification ttc = establishTypeOfTreatment(t);
		insertTypeOfTreatmentInWM(ttc);
	}
*/
	
	public static boolean hasTTC(Treatment t){
		if(mapOfTreatmentTypes.containsKey(t))
			return true;
		else
			return false;
			
	}
	
	public static boolean hasValidTTC(Treatment t){
		
		if( hasTTC(t) ){
			TreatmentTypeClassification ttc = mapOfTreatmentTypes.get(t);
			if(ttc.getTreatmentByDuration()!=null && ttc.getTreatmentByMeasurement()!=null && ttc.getTreatmentByRecurrence()!=null)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	}
