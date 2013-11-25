package org.universAAL.ontology.activity;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.supply.AbsLocation;


public abstract class Activity extends ManagedIndividual {
  public static final String MY_URI = ActivityOntology.NAMESPACE
    + "Activity";
  public static final String PROP_STOP_DATE = ActivityOntology.NAMESPACE
    + "stopDate";
  public static final String PROP_START_DATE = ActivityOntology.NAMESPACE
    + "startDate";
  public static final String PROP_NAME = ActivityOntology.NAMESPACE
    + "name";
public static final String PROP_LOCATION = ActivityOntology.NAMESPACE;


  public Activity () {
    super();
  }
  
  public Activity (String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	// TODO Implement or if for Device subclasses: remove 
	return 0;
  }

  public boolean isWellFormed() {
	return true 
      && hasProperty(PROP_STOP_DATE)
      && hasProperty(PROP_START_DATE)
      && hasProperty(PROP_NAME);
  }

  public String getName() {
    return (String)getProperty(PROP_NAME);
  }		

  public void setName(String newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP_NAME, newPropValue);
  }		

  public XMLGregorianCalendar getStartDate() {
    return (XMLGregorianCalendar)getProperty(PROP_START_DATE);
  }		

  public void setStartDate(XMLGregorianCalendar newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP_START_DATE, newPropValue);
  }		

  public XMLGregorianCalendar getStopDate() {
    return (XMLGregorianCalendar)getProperty(PROP_STOP_DATE);
  }		

  public void setStopDate(XMLGregorianCalendar newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP_STOP_DATE, newPropValue);
  }
  
  public AbsLocation getLocation(){
	  return (AbsLocation) getProperty(PROP_LOCATION);
  }
  
  public void setLocation(AbsLocation loc){
	  if (loc != null){
		 changeProperty(PROP_LOCATION, loc); 	
	  }
  }
}
