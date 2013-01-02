package org.universAAL.ontology.ltba;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.LTBAOntology;

/**
 * Enumeration of the Activity Report Type. To be used in the future for avoid
 * specific service call for each type of report.
 * 
 * @author mllorente
 * 
 */
public class ActivityReportType extends ManagedIndividual {

	public static final String MY_URI = LTBAOntology.NAMESPACE;

	// Three types of activity report. Day, Week and Month.
	public static final int DAY = 0;
	public static final int WEEK = 1;
	public static final int MONTH = 2;

	// In instances (individuals) this has the value of one of the numeric
	// constants above
	private int order;

	// String names of the options
	private static final String[] names = { "day", "week", "month" };

	// These are all the possible instances of your enumeration: the individuals
	public static final ActivityReportType day = new ActivityReportType(DAY);
	public static final ActivityReportType week = new ActivityReportType(WEEK);
	public static final ActivityReportType month = new ActivityReportType(MONTH);

	// methods inherited from the tutorial sample
	public static ManagedIndividual[] getEnumerationMembers() {
		return new ManagedIndividual[] { day, week, month };
	}

	// This is used privately. Constructs an individual based on the given
	// numeric constant.
	private ActivityReportType(int order) {
		super(LTBAOntology.NAMESPACE + names[order]);
		this.order = order;
	}

	public ActivityReportType() {
		// Enumerations cannot be instantiated by other than themselves. This
		// constructor must be empty to prevent this.
	}

	// See MyConcept for an explanation of this method. In Enumerations it is
	// not relevant, although it must be present. (have to check if it works if
	// we include properties in enumerations)
	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_OPTIONAL;
	}

	// You don´t really need this for enumerations, but it won´t hurt if it
	// always returns true...
	public boolean isWellFormed() {
		return true;
	}

	// --------------------------------------------------------------------

	// This is a helper method. You may choose not to include it, because it is
	// not used by the system. However it will surely be used by developers, so
	// its inclusion is recommended. It returns the matching individual of the
	// enumeration given its order (in the numeric constants)
	public static ActivityReportType getByOrder(int order) {
		switch (order) {
		case DAY:
			return day;
		case WEEK:
			return week;
		case MONTH:
			return month;
		default:
			return null;
		}
	}

	// Returns the individual that matches the given name.
	public static final ActivityReportType valueOf(String name) {
		if (name == null)
			return null;

		// Remember to change the namespace to your domain...
		if (name.startsWith(LTBAOntology.NAMESPACE))
			name = name.substring(LTBAOntology.NAMESPACE.length());

		// Here you must use the first and last numeric constants you declared
		// at the beginning (lowest and highest numbers). Watch out! It is a
		// common mistake to forget to update the max constant in the 'for', for
		// instance if you add or reduce the amount of options...
		for (int i = DAY; i <= MONTH; i++)
			if (names[i].equals(name))
				return getByOrder(i);

		return null;
	}

	// This is used in instances (individuals) to get their name
	public String name() {
		return names[order];
	}

	// This is used in instances (individuals) to get their numeric constant
	// (their order)
	public int ord() {
		return order;
	}

	// As commented above, currently enumerations do not support properties in
	// our code (I have to check this later). This method prevents anyone from
	// adding properties manually later to individuals.
	public void setProperty(String propURI, Object o) {
		// do nothing
	}

}
