package org.universAAL.ontology.agendaEventSelection;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.agenda.Event;

/**
 * @author kagnadis
 * 
 */
public class FilterParams extends ManagedIndividual {
    
    /**  */
    public static final String MY_URI = AgendaEventSelectionOntology.NAMESPACE
	    + "FilterParams";
    
    /**  */
    public static final String PROP_DT_BEGIN = AgendaEventSelectionOntology.NAMESPACE
	    + "DTbegin";
    
    /**  */
    public static final String PROP_DT_END = AgendaEventSelectionOntology.NAMESPACE
	    + "DTend";
    
    /**  */
    public static final String PROP_CATEGORY = AgendaEventSelectionOntology.NAMESPACE
	    + "category";
    
    /**  */
    public static final String PROP_SPOKEN_LANGUAGE = AgendaEventSelectionOntology.NAMESPACE
	    + "spokenLanguage";
    
    /**  */
    public static final String PROP_HAS_USER_PROFILE = AgendaEventSelectionOntology.NAMESPACE
	    + "hasUserProfile";
    
    /**  */
    public static final String PROP_HAS_LOCATION = AgendaEventSelectionOntology.NAMESPACE
	    + "hasLocation";
    
    /**  */
    public static final String PROP_DESCRIPTION = AgendaEventSelectionOntology.NAMESPACE
	    + "hasSearchType";
    
    /**  */
    public static final String PROP_HAS_SEARCH_TYPE = AgendaEventSelectionOntology.NAMESPACE
	    + "hasDescription";

    /**
     * 
     */
    public FilterParams() {
	super();
    }

    /**
     * 
     *
     * @param uri 
     */
    public FilterParams(String uri) {
	super(uri);
    }

    /*
     * DTbegin
     */
    /**
     * 
     *
     * @return 
     */
    public XMLGregorianCalendar getDTbegin() {
	return (XMLGregorianCalendar) props.get(PROP_DT_BEGIN);
    }

    /**
     * 
     *
     * @param DTbegin 
     */
    public void setDTbegin(XMLGregorianCalendar DTbegin) {
	props.put(PROP_DT_BEGIN, DTbegin);
    }

    /*
     * DTend
     */
    /**
     * 
     *
     * @return 
     */
    public XMLGregorianCalendar getDTend() {
	return (XMLGregorianCalendar) props.get(PROP_DT_END);
    }

    /**
     * 
     *
     * @param DTend 
     */
    public void setDTend(XMLGregorianCalendar DTend) {
	props.put(PROP_DT_END, DTend);
    }

    /*
     * Category
     */
    /**
     * 
     *
     * @param category 
     */
    public void setCategory(String category) {
	props.put(PROP_CATEGORY, category);
    }

    /**
     * 
     *
     * @return 
     */
    public String getCategory() {
	return (String) props.get(PROP_CATEGORY);
    }

    /*
     * Spoken Language
     */
    /**
     * 
     *
     * @param spokenLang 
     */
    public void setSpokenLanguage(String spokenLang) {
	props.put(PROP_SPOKEN_LANGUAGE, spokenLang);
    }

    /**
     * 
     *
     * @return 
     */
    public String getSpokenLanguage() {
	return (String) props.get(PROP_SPOKEN_LANGUAGE);
    }

    /*
     * Description
     */
    /**
     * 
     *
     * @param descriptionPattern 
     */
    public void setDescription(String descriptionPattern) {
	props.put(PROP_DESCRIPTION, descriptionPattern);
    }

    /**
     * 
     *
     * @return 
     */
    public String getDescription() {
	return (String) props.get(PROP_DESCRIPTION);
    }

    /*
     * User Profile
     */
    /**
     * 
     *
     * @param userProfile 
     */
    public void setUserProfile(UserProfile userProfile) {
	props.put(PROP_CATEGORY, userProfile);
    }

    /**
     * 
     *
     * @return 
     */
    public UserProfile getUserProfile() {
	return (UserProfile) props.get(PROP_HAS_USER_PROFILE);
    }

    /*
     * Location
     */
    /**
     * 
     *
     * @param location 
     */
    public void setLocation(Location location) {
	props.put(PROP_HAS_LOCATION, location);
    }

    /**
     * 
     *
     * @return 
     */
    public Location getLocation() {
	return (Location) props.get(PROP_HAS_LOCATION);
    }

    /*
     * Time SearchType
     */
    /**
     * 
     *
     * @param tst 
     */
    public void setTimeSearchType(TimeSearchType tst) {
	if (tst != null)
	    props.put(PROP_HAS_SEARCH_TYPE, tst);
    }

    /**
     * 
     *
     * @return 
     */
    public TimeSearchType getTimeSearchType() {
	return (TimeSearchType) props.get(PROP_HAS_SEARCH_TYPE);
    }


    /* (non-Javadoc)
     * @see org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType(java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
    }

    /* (non-Javadoc)
     * @see org.universAAL.middleware.owl.ManagedIndividual#isWellFormed()
     */
    public boolean isWellFormed() {

	return true;
    }

    // condition::Find if all of the FilterParams values match to
    // Event.EventDetails parameters
    /**
     * 
     *
     * @param event 
     * @return 
     */
    public boolean matches(Event event) {
	if (this.getCategory() != null) {

	    if (!this.getCategory().equals(
		    event.getEventDetails().getCategory())) {
		return false;
	    }
	}

	if (this.getSpokenLanguage() != null) {
	    if (!this.getSpokenLanguage().equals(
		    event.getEventDetails().getSpokenLanguage())) {
		return false;
	    }
	}

	if (this.getDescription() != null) {
	    // replace contains() with indexOf in order to be compliant with
	    // java 1.3
	    // if
	    // (!(event.getEventDetails().getDescription().toLowerCase().contains(this.getDescription().toLowerCase())))
	    // {
	    // return false;
	    // }
	    if (event.getEventDetails().getDescription().toLowerCase().indexOf(
		    this.getDescription().toLowerCase()) == -1) {
		return false;
	    }
	}

	if (this.getTimeSearchType() != null) {
	    boolean validTime = this.isDateTimeValid(event.getEventDetails()
		    .getTimeInterval().getStartTime(), event.getEventDetails()
		    .getTimeInterval().getEndTime());
	    if (!validTime)
		return false;
	}

	return true;
    }

    /**
     * 
     *
     * @param startEvent 
     * @param endEvent 
     * @return 
     */
    private boolean isDateTimeValid(XMLGregorianCalendar startEvent,
	    XMLGregorianCalendar endEvent) {
	XMLGregorianCalendar startPeriod = this.getDTbegin();
	XMLGregorianCalendar endPeriod = this.getDTend();
	if ((startPeriod == null) && (endPeriod != null))
	    startPeriod = endPeriod;
	else if ((startPeriod != null) && (endPeriod == null))
	    endPeriod = startPeriod;
	else if ((startPeriod == null) && (endPeriod == null))
	    return true;

	TimeSearchType tst = this.getTimeSearchType();

	switch (tst.ord()) {
	case TimeSearchType.STARTS_BETWEEN:
	    /* Event starts inside a period */
	    if ((startPeriod.compare(startEvent) == DatatypeConstants.LESSER)
		    && (endPeriod.compare(startEvent) == DatatypeConstants.GREATER))
		return true;
	    break;
	case TimeSearchType.ENDS_BETWEEN:
	    /* Event ends inside a period */
	    if ((startPeriod.compare(endEvent) == DatatypeConstants.LESSER)
		    && (endPeriod.compare(endEvent) == DatatypeConstants.GREATER))
		return true;
	    break;
	case TimeSearchType.STARTS_AND_ENDS_BETWEEN:
	    /* Event starts - ends inside a period */
	    if ((startPeriod.compare(startEvent) == DatatypeConstants.LESSER)
		    && (endPeriod.compare(endEvent) == DatatypeConstants.GREATER))
		return true;
	    break;
	case TimeSearchType.START_BEFORE_AND_ENDS_AFTER:
	    /* Event is running throughout a period */
	    if ((startPeriod.compare(startEvent) == DatatypeConstants.GREATER)
		    && (endPeriod.compare(endEvent) == DatatypeConstants.LESSER))
		return true;
	    break;
	case TimeSearchType.ALL_BEFORE:
	    /* Event starts - ends inside a period */
	    if (startPeriod.compare(endEvent) == DatatypeConstants.GREATER)
		return true;
	    break;
	case TimeSearchType.ALL_AFTER:
	    /* Event is running throughout a period */
	    if (endPeriod.compare(startEvent) == DatatypeConstants.LESSER)
		return true;
	    break;
	case TimeSearchType.ALL_CASES:
	default:
	    /* Check all four previous cases */
	    if (startPeriod.compare(endEvent) == DatatypeConstants.GREATER)
		return true;
	    if (endPeriod.compare(startEvent) == DatatypeConstants.LESSER)
		return true;
	    if ((startPeriod.compare(startEvent) == DatatypeConstants.LESSER)
		    && (endPeriod.compare(startEvent) == DatatypeConstants.GREATER))
		return true;
	    if ((startPeriod.compare(endEvent) == DatatypeConstants.LESSER)
		    && (endPeriod.compare(endEvent) == DatatypeConstants.GREATER))
		return true;
	    if ((startPeriod.compare(startEvent) == DatatypeConstants.LESSER)
		    && (endPeriod.compare(endEvent) == DatatypeConstants.GREATER))
		return true;
	    if ((startPeriod.compare(startEvent) == DatatypeConstants.GREATER)
		    && (endPeriod.compare(endEvent) == DatatypeConstants.LESSER))
		return true;
	    break;
	}

	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI()
     */
    public String getClassURI() {
	return MY_URI;
    }
}
