package org.universAAL.ontology.agendaEventSelection;

import org.universAAL.middleware.owl.ManagedIndividual;

/**
 * Time search type
 */
public class TimeSearchType extends ManagedIndividual {

    public static final String MY_URI = AgendaEventSelectionOntology.NAMESPACE
	    + "TimeSearchType";

    public static final int STARTS_BETWEEN = 0;

    public static final int ENDS_BETWEEN = 1;

    public static final int STARTS_AND_ENDS_BETWEEN = 2;

    public static final int START_BEFORE_AND_ENDS_AFTER = 3;

    public static final int ALL_BEFORE = 4;

    public static final int ALL_AFTER = 5;

    public static final int ALL_CASES = 6;

    private static final String[] names = { "Starts_between", "Ends_between",
	    "Starts_and_ends_between", "Starts_before_and_ends_after",
	    "allBefore", "allAfter", "allCases" };

    // when an event's START TIME is BETWEEN START and END TIME of search
    // interval

    public static final TimeSearchType startsBetween = new TimeSearchType(
	    STARTS_BETWEEN);
    // when an event's END TIME is between START and END TIME of search interval

    public static final TimeSearchType endsBetween = new TimeSearchType(
	    ENDS_BETWEEN);
    // when event's START and END TIME is between START and ENT TIME of search
    // interval

    public static final TimeSearchType startsAndEndsBetween = new TimeSearchType(
	    STARTS_AND_ENDS_BETWEEN);
    // when event's START TIME is before START TIME of search interval, and
    // event's END TIME is after END TIME of search interval

    public static final TimeSearchType startsBeforeAndEndsAfter = new TimeSearchType(
	    START_BEFORE_AND_ENDS_AFTER);
    // when event's END TIME is before START TIME of search interval

    public static final TimeSearchType allBefore = new TimeSearchType(
	    ALL_BEFORE);
    // when event's START TIME is after END TIME of search interval

    public static final TimeSearchType allAfter = new TimeSearchType(ALL_AFTER);
    // when one of the previous cases exists

    public static final TimeSearchType allCases = new TimeSearchType(ALL_CASES);

    /**
     * Returns the list of all class members guaranteeing that no other members
     * will be created after a call to this method.
     * 
     * @return
     */
    public static ManagedIndividual[] getEnumerationMembers() {
	return new ManagedIndividual[] { startsBetween, endsBetween,
		startsAndEndsBetween, startsBeforeAndEndsAfter, allBefore,
		allAfter, allCases };
    }

    /**
     * Returns the rating with the given URI.
     * 
     * @param instanceURI
     * @return
     */
    public static ManagedIndividual getIndividualByURI(String instanceURI) {
	return (instanceURI != null && instanceURI
		.startsWith(AgendaEventSelectionOntology.NAMESPACE)) ? valueOf(instanceURI
		.substring(AgendaEventSelectionOntology.NAMESPACE.length()))
		: null;
    }

    /**
     * 
     * 
     * @param name
     * @return
     */
    public static final TimeSearchType valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(AgendaEventSelectionOntology.NAMESPACE))
	    name = name.substring(AgendaEventSelectionOntology.NAMESPACE
		    .length());

	for (int i = STARTS_BETWEEN; i <= ALL_CASES; ++i)
	    if (names[i].equals(name))
		return getTimeSearchTypeByOrder(i);

	return null;
    }

    /**
     * 
     * 
     * @param order
     * @return
     */
    public static TimeSearchType getTimeSearchTypeByOrder(int order) {
	switch (order) {
	case STARTS_BETWEEN:
	    return startsBetween;
	case ENDS_BETWEEN:
	    return endsBetween;
	case STARTS_AND_ENDS_BETWEEN:
	    return startsAndEndsBetween;
	case START_BEFORE_AND_ENDS_AFTER:
	    return startsBeforeAndEndsAfter;
	case ALL_BEFORE:
	    return allBefore;
	case ALL_AFTER:
	    return allAfter;
	case ALL_CASES:
	    return allCases;
	default:
	    return null;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
     * (java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_OPTIONAL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.owl.ManagedIndividual#isWellFormed()
     */
    public boolean isWellFormed() {
	return true;
    }

    private int order;

    /**
     * 
     * 
     * @param order
     */
    private TimeSearchType(int order) {
	super(AgendaEventSelectionOntology.NAMESPACE + names[order]);
	this.order = order;
    }

    /**
     * 
     * 
     * @return name
     */
    public String name() {
	return names[this.order];
    }

    /**
     * 
     * 
     * @return
     */
    public int ord() {
	return this.order;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.owl.ManagedIndividual#setProperty(java.lang
     * .String, java.lang.Object)
     */
    public void setProperty(String propURI, Object o) {
	// do nothing
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
