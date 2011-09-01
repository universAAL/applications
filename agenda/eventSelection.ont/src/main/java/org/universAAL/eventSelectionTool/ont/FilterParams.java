package org.universAAL.eventSelectionTool.ont;
//tu smo dodali import iz agende.ont
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.agenda.ont.Event;

/**
 * @author kagnadis
 * 
 */
public class FilterParams extends ManagedIndividual {
	public static final String MY_URI;
	public static final String PROP_DT_BEGIN;
	public static final String PROP_DT_END;
	public static final String PROP_CATEGORY;
	public static final String PROP_SPOKEN_LANGUAGE;
	public static final String PROP_HAS_USER_PROFILE;
	public static final String PROP_HAS_LOCATION;
	public static final String PROP_DESCRIPTION;
	public static final String PROP_HAS_SEARCH_TYPE;

	static {
		MY_URI = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "FilterParams";
		PROP_DT_BEGIN = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "DTbegin";
		PROP_DT_END = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "DTend";
		PROP_CATEGORY = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "category";
		PROP_SPOKEN_LANGUAGE = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "spokenLanguage";
		PROP_HAS_USER_PROFILE = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "hasUserProfile";
		PROP_HAS_LOCATION = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "hasLocation";
		PROP_HAS_SEARCH_TYPE = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "hasSearchType";
		PROP_DESCRIPTION = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "hasDescription";

		register(FilterParams.class);
	}

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (PROP_DT_BEGIN.equals(propURI))
			return Restriction
					.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 0);
		if (PROP_DT_END.equals(propURI))
			return Restriction
					.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 0);
		if (PROP_CATEGORY.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String.class), 1, 0);
		if (PROP_SPOKEN_LANGUAGE.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String.class), 1, 0);
		if (PROP_HAS_USER_PROFILE.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, UserProfile.MY_URI, 1, 0);
		if (PROP_HAS_LOCATION.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, Location.MY_URI, 1, 0);
		if (PROP_HAS_SEARCH_TYPE.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TimeSearchType.MY_URI, 1, 1);
		if (PROP_DESCRIPTION.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI, TypeMapper.getDatatypeURI(String.class), 1, 1);
		return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] toReturn = new String[inherited.length+7];
		int i = 0;
		while (i < inherited.length) {
			toReturn[i] = inherited[i];
			i++;
		}
		toReturn[i++] = PROP_DT_BEGIN;
		toReturn[i++] = PROP_DT_END;
		toReturn[i++] = PROP_CATEGORY;
		toReturn[i++] = PROP_SPOKEN_LANGUAGE;
		toReturn[i++] = PROP_HAS_USER_PROFILE;
		toReturn[i++] = PROP_HAS_LOCATION;
		toReturn[i++] = PROP_DESCRIPTION;
		toReturn[i++] = PROP_HAS_SEARCH_TYPE;
		
		
		return toReturn;
	}
	
	public static String getRDFSComment() {
		return "The class of all FilterParams.";
	}

	public static String getRDFSLabel() {
		return "FilterParams";
	}

	public FilterParams() {
		super();
	}

	public FilterParams(String uri) {
		super(uri);
	}

	/*
	 * DTbegin
	 */
	public XMLGregorianCalendar getDTbegin() {
		return (XMLGregorianCalendar) props.get(PROP_DT_BEGIN);
	}

	public void setDTbegin(XMLGregorianCalendar DTbegin) {
		props.put(PROP_DT_BEGIN, DTbegin);
	}

	/*
	 * DTend
	 */
	public XMLGregorianCalendar getDTend() {
		return (XMLGregorianCalendar) props.get(PROP_DT_END);
	}

	public void setDTend(XMLGregorianCalendar DTend) {
		props.put(PROP_DT_END, DTend);
	}

	/*
	 * Category
	 */
	public void setCategory(String category) {
		props.put(PROP_CATEGORY, category);
	}

	public String getCategory() {
		return (String) props.get(PROP_CATEGORY);
	}

	/*
	 * Spoken Language
	 */
	public void setSpokenLanguage(String spokenLang) {
		props.put(PROP_SPOKEN_LANGUAGE, spokenLang);
	}

	public String getSpokenLanguage() {
		return (String) props.get(PROP_SPOKEN_LANGUAGE);
	}
	
	/*
	 * Description
	 */
	public void setDescription(String descriptionPattern) {
		props.put(PROP_DESCRIPTION, descriptionPattern);
	}

	public String getDescription() {
		return (String) props.get(PROP_DESCRIPTION);
	}

	/*
	 * User Profile
	 */
	public void setUserProfile(UserProfile userProfile) {
		props.put(PROP_CATEGORY, userProfile);
	}

	public UserProfile getUserProfile() {
		return (UserProfile) props.get(PROP_HAS_USER_PROFILE);
	}

	/*
	 * Location
	 */
	public void setLocation(Location location) {
		props.put(PROP_HAS_LOCATION, location);
	}

	public Location getLocation() {
		return (Location) props.get(PROP_HAS_LOCATION);
	}
	
	/*
	 * Time SearchType
	 */
	public void setTimeSearchType(TimeSearchType tst) {
		if (tst != null)
			props.put(PROP_HAS_SEARCH_TYPE, tst);
	}

	public TimeSearchType getTimeSearchType() {
		return (TimeSearchType) props.get(PROP_HAS_SEARCH_TYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.persona.ontology.ManagedIndividual#getPropSerializationType(java.
	 * lang.String)
	 */
	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {

		return true;
	}

	// condition::Find if all of the FilterParams values match to
	// Event.EventDetails parameters
	public boolean matches(Event event) {
		if (this.getCategory() != null) {

			if (!this.getCategory().equals(event.getEventDetails().getCategory())) {
				return false;
			}
		}

		if (this.getSpokenLanguage() != null) {
			if (!this.getSpokenLanguage().equals(event.getEventDetails().getSpokenLanguage())) {
				return false;
			}
		}
		
		if (this.getDescription() != null) {
//			replace contains() with indexOf in order to be compliant with java 1.3
//			if (!(event.getEventDetails().getDescription().toLowerCase().contains(this.getDescription().toLowerCase()))) {
//				return false;
//			}
			if (event.getEventDetails().getDescription().toLowerCase().indexOf(this.getDescription().toLowerCase()) == -1) {
				return false;
			}
		}
		
		if (this.getTimeSearchType() != null){
			boolean validTime = this.isDateTimeValid(event.getEventDetails().getTimeInterval().getStartTime(),
											     event.getEventDetails().getTimeInterval().getEndTime());
		if (!validTime)
			return false;
		}

		return true;
	}
	
	private boolean isDateTimeValid(XMLGregorianCalendar startEvent, XMLGregorianCalendar endEvent) {
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
}
