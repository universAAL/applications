/**
 * 
 */
package org.universAAL.agenda.ont;

import java.util.List;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;

import org.universAAL.ontology.location.address.Address;

//import org.universAAL.ontology.*;
/**
 * @author kagnantis
 * 
 */
public class EventDetails extends ManagedIndividual {
    public static final String MY_URI;
    public static final String PROP_CATEGORY;
    // public static final String PROP_ID;
    public static final String PROP_HAS_ADDRESS;
    public static final String PROP_DESCRIPTION;
    public static final String PROP_PLACE_NAME;
    public static final String PROP_SPOKEN_LANGUAGE;
    public static final String PROP_PARTICIPANTS;
    public static final String PROP_HAS_VALID_PERIOD;

    static {
	MY_URI = Calendar.CALENDAR_NAMESPACE + "EventDetails";
	PROP_CATEGORY = Calendar.CALENDAR_NAMESPACE + "category";
	PROP_HAS_ADDRESS = Calendar.CALENDAR_NAMESPACE + "hasAddress";
	PROP_DESCRIPTION = Calendar.CALENDAR_NAMESPACE + "description";
	PROP_PLACE_NAME = Calendar.CALENDAR_NAMESPACE + "placeName";
	PROP_SPOKEN_LANGUAGE = Calendar.CALENDAR_NAMESPACE + "spokenLanguage";
	PROP_PARTICIPANTS = Calendar.CALENDAR_NAMESPACE + "participants";
	PROP_HAS_VALID_PERIOD = Calendar.CALENDAR_NAMESPACE + "hasValidPeriod";
	register(EventDetails.class);
    }

    public static Restriction getClassRestrictionsOnProperty(String propURI) {
	if (PROP_CATEGORY.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    TypeMapper.getDatatypeURI(String.class), 1, 0);
	if (PROP_HAS_ADDRESS.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    Address.MY_URI, 1, 1);
	if (PROP_DESCRIPTION.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    TypeMapper.getDatatypeURI(String.class), 1, 0);
	if (PROP_PLACE_NAME.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    TypeMapper.getDatatypeURI(String.class), 1, 0);
	if (PROP_SPOKEN_LANGUAGE.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    TypeMapper.getDatatypeURI(String.class), 1, 0);
	if (PROP_PARTICIPANTS.equals(propURI))
	    return Restriction.getAllValuesRestriction(propURI, TypeMapper
		    .getDatatypeURI(String.class));
	if (PROP_HAS_VALID_PERIOD.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    TimeInterval.MY_URI, 1, 0);

	return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
    }

    public static String[] getStandardPropertyURIs() {
	String[] inherited = ManagedIndividual.getStandardPropertyURIs();
	String[] toReturn = new String[inherited.length + 7];
	int i = 0;
	while (i < inherited.length) {
	    toReturn[i] = inherited[i];
	    i++;
	}
	toReturn[i++] = PROP_CATEGORY;
	toReturn[i++] = PROP_HAS_ADDRESS;
	toReturn[i++] = PROP_DESCRIPTION;
	toReturn[i++] = PROP_PLACE_NAME;
	toReturn[i++] = PROP_SPOKEN_LANGUAGE;
	toReturn[i++] = PROP_PARTICIPANTS;
	toReturn[i++] = PROP_HAS_VALID_PERIOD;

	return toReturn;
    }

    public static String getRDFSComment() {
	return "The class of event details.";
    }

    public static String getRDFSLabel() {
	return "Event details";
    }

    public EventDetails() {
	super();
    }

    public EventDetails(String uri) {
	super(uri);
    }

    public EventDetails(String uri, Address address) {
	super(uri);
	if (address == null)
	    throw new IllegalArgumentException();

	props.put(PROP_HAS_ADDRESS, address);
    }

    /*
     * Address
     */
    public Address getAddress() {
	return (Address) props.get(PROP_HAS_ADDRESS);
    }

    public void setAddress(Address address) {
	if (address == null)
	    return;

	props.put(PROP_HAS_ADDRESS, address);
    }

    /*
     * Category
     */
    public String getCategory() {
	return (String) props.get(PROP_CATEGORY);
    }

    public void setCategory(String category) {
	if (category == null)
	    return;

	props.put(PROP_CATEGORY, category);
    }

    /*
     * Description
     */
    public String getDescription() {
	return (String) props.get(PROP_DESCRIPTION);
    }

    public void setDescription(String description) {
	if (description == null)
	    return;

	props.put(PROP_DESCRIPTION, description);
    }

    /*
     * PlaceName
     */
    public String getPlaceName() {
	return (String) props.get(PROP_PLACE_NAME);
    }

    public void setPlaceName(String placeName) {
	if (placeName == null)
	    return;

	props.put(PROP_PLACE_NAME, placeName);
    }

    /*
     * spokenLanguage
     */
    public String getSpokenLanguage() {
	return (String) props.get(PROP_SPOKEN_LANGUAGE);
    }

    public void setSpokenLanguage(String spokenLanguage) {
	if (spokenLanguage == null)
	    return;

	props.put(PROP_SPOKEN_LANGUAGE, spokenLanguage);
    }

    /*
     * event participants IS NEVER USED...
     */
    public List getParticipantList() {
	return (List) props.get(PROP_PARTICIPANTS);
    }

    public void setParticipantList(List participants) {
	if (participants == null)
	    return;

	props.put(PROP_PARTICIPANTS, participants);
    }

    /*
     * TimeInterval - When the event starts and when it is finished
     */
    public TimeInterval getTimeInterval() {
	return (TimeInterval) props.get(PROP_HAS_VALID_PERIOD);
    }

    public void setTimeInterval(TimeInterval validPeriod) {
	if (validPeriod == null)
	    return;

	props.put(PROP_HAS_VALID_PERIOD, validPeriod);
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

    /*
     * (non-Javadoc)
     * 
     * @see org.persona.ontology.ManagedIndividual#isWellFormed()
     */
    public boolean isWellFormed() {
	return props.containsKey(PROP_HAS_ADDRESS);
    }

    public String toString(String fieldSeperator) {
	StringBuffer s = new StringBuffer(100);

	s.append("EventDetails:" + fieldSeperator);
	s.append("\tCategory    :\t" + this.getCategory() + fieldSeperator);
	s.append("\tDescription :\t" + this.getDescription() + fieldSeperator);
	s.append("\tPlace Name  :\t" + this.getPlaceName() + fieldSeperator);
	s.append("\tSp. Language:\t" + this.getSpokenLanguage()
		+ fieldSeperator);
	if (this.getParticipantList() != null)
	    s.append("\tParticipants:\t" + this.getParticipantList().size()
		    + fieldSeperator);
	// PhysicalAddress smo svuda zamjenili sa Address
	Address ad = this.getAddress();

	if (ad == null) {
	    s.append("\tAddress:\t Not specified" + fieldSeperator);
	} else {
	    s.append("\tAddress:\t" + fieldSeperator);
	    if (ad.getCountry() != null)
		s.append("\t\tCountry  :\t" + ad.getCountry() + fieldSeperator);
	    s.append("\t\tExtended Address :\t" + ad.getExtAddress()
		    + fieldSeperator);
	    s.append("\t\tLocality     :\t" + ad.getCityQuarter()
		    + fieldSeperator);
	    s.append("\t\tPostal Code :\t" + ad.getPostalCode()
		    + fieldSeperator);
	    s.append("\t\tRegion   :\t" + ad.getRegion() + fieldSeperator);
	    if (ad.getCityPlace() != null) {
		s.append("\t\tStreet Name  :\t" + ad.getCityPlace()
			+ fieldSeperator);
		s.append("\t\tBuilding  :\t" + "1");
	    }
	}

	// if (ad == null) {
	// s.append("\tAddress:\t Not specified" + fieldSeperator);
	// } else {
	// s.append("\tAddress:\t" + fieldSeperator);
	// if (ad.getCountry() != null)
	// s.append("\t\tCountry  :\t" + ad.getCountry() + fieldSeperator);
	// s.append("\t\tExtended Address :\t" + ad.getExtAddress() +
	// fieldSeperator);
	// s.append("\t\tLocality     :\t" + ad.getLocalName() +
	// fieldSeperator);
	// s.append("\t\tPostal Code :\t" + ad.getPostalCode() +
	// fieldSeperator);
	// s.append("\t\tRegion   :\t" + ad.getState() + fieldSeperator);
	// if (ad.getStreet() != null) {
	// s.append("\t\tStreet Name  :\t" + ad.getStreet() + fieldSeperator);
	// s.append("\t\tBuilding  :\t" + "1");
	// }
	// }

	TimeInterval timeInterval = this.getTimeInterval();

	s.append(fieldSeperator + '\t');
	if (timeInterval == null) {
	    s.append("\tTime Interval:\t Not specified" + fieldSeperator);
	} else {
	    s.append(timeInterval.toString(fieldSeperator + '\t'));
	}

	return s.toString();
    }

    public String toString() {
	return this.toString("\n");
    }

}
