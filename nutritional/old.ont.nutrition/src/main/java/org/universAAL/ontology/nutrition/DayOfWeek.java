package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.Activator;

// Enumerations can be used for giving value to a property that must have one 
// of a specific set of individual values.
// Currently only Enumerations "without meaning" are accepted, that is, 
// they are only represented by their type URI and individual URI. 
// They don´t have properties. Nevertheless, in theory it should be possible 
// to declare properties into enumerations, by merging them with the required 
// code of normal concepts (see MyConcept). However I have not checked it.
// All enumerations extend ManagedIndividual (I have not checked if it is 
// possible to extend other concepts or enumerations, but it seems possible)
public class DayOfWeek extends ManagedIndividual {
	//
	private static final String MY_NAMESPACE = Activator.MY_NAMESPACE;
	// MY URI is the URI of this concept. It is mandatory for all.
	public static final String MY_URI;
	// In this static block you set the URIs of your enumeration. Since we have
	// already defined a namespace in MyConcept for our domain, we reuse it.
	static {
		// The URI of your enumeration, which is the same name than the class
		MY_URI = MY_NAMESPACE + "DayOfWeek";
		register(DayOfWeek.class);
	}

	// These constants identify the possible values in your enumeration
	public static final int VALUE_SUNDAY 	= 1;
	public static final int VALUE_MONDAY 	= 2;
	public static final int VALUE_TUESDAY 	= 3;
	public static final int VALUE_WEDNESDAY	= 4;
	public static final int VALUE_THURSDAY 	= 5;
	public static final int VALUE_FRIDAY 	= 6;
	public static final int VALUE_SATURDAY 	= 7;

	// In instances (individuals) this has the value of one of the numeric
	// constants above
	private int order;

	// You have to give a name to each possible value. These names are used in
	// the URI of each individual (the possible values of the enumeration), so
	// they must follow the format for individual names: initial lower case,
	// without spaces (probably all in lower case, but must be confirmed)
	private static final String[] names = { "sunday", "monday", "tuesday",
			"wednesday", "thursday", "friday", "saturday"};

	// These are all the possible instances of your enumeration: the individuals
	public static final DayOfWeek SUNDAY 		= new DayOfWeek(VALUE_SUNDAY, 0);
	public static final DayOfWeek MONDAY 		= new DayOfWeek(VALUE_MONDAY, 1);
	public static final DayOfWeek TUESDAY 		= new DayOfWeek(VALUE_TUESDAY, 2);
	public static final DayOfWeek WEDNESDAY 	= new DayOfWeek(VALUE_WEDNESDAY, 3);
	public static final DayOfWeek THURSDAY 		= new DayOfWeek(VALUE_THURSDAY, 4);
	public static final DayOfWeek FRIDAY 		= new DayOfWeek(VALUE_FRIDAY, 5);
	public static final DayOfWeek SATURDAY 		= new DayOfWeek(VALUE_SATURDAY, 6);

	// This methods returns the list of all class members guaranteeing that no
	// other members will be created after a call to this method.
	public static ManagedIndividual[] getEnumerationMembers() {
		return new ManagedIndividual[] { SUNDAY, MONDAY, TUESDAY,
				THURSDAY, WEDNESDAY, FRIDAY, SATURDAY};
	}

	// This method returns the individual of this enumeration that has the the
	// given URI.
	// DO NOT CHANGE THIS. Just copy it. It works like this for all
	// enumerations. (you have to change the namespace to your domain, though)
	public static ManagedIndividual getIndividualByURI(String instanceURI) {
		return (instanceURI != null && instanceURI.startsWith(MY_NAMESPACE)) ? valueOf(instanceURI
				.substring(MY_NAMESPACE.length()))
				: null;
	}

	// This is a helper method. You may choose not to include it, because it is
	// not used by the system. However it will surely be used by developers, so
	// its inclusion is recommended. It returns the matching individual of the
	// enumeration given its order (in the numeric constants)
	public static DayOfWeek getDaysOfWeekByOrder(int order) {
		switch (order) {
		case VALUE_SUNDAY:
			return SUNDAY;
		case VALUE_MONDAY:
			return MONDAY;
		case VALUE_TUESDAY:
			return TUESDAY;
		case VALUE_WEDNESDAY:
			return WEDNESDAY;
		case VALUE_THURSDAY:
			return THURSDAY;
		case VALUE_FRIDAY:
			return FRIDAY;
		case VALUE_SATURDAY:
			return SATURDAY;
		default:
			return null;
		}
	}

	// Returns the individual that matches the given name.
	public static final DayOfWeek valueOf(String name) {
		if (name == null)
			return null;

		// Remember to change the namespace to your domain...
		if (name.startsWith(MY_NAMESPACE))
			name = name.substring(MY_NAMESPACE.length());

		// Here you must use the first and last numeric constants you declared
		// at the beginning (lowest and highest numbers). Watch out! It is a
		// common mistake to forget to update the max constant in the 'for', for
		// instance if you add or reduce the amount of options...
		for (int i = VALUE_SUNDAY; i <= VALUE_SATURDAY; i++)
			if (names[i].equals(name))
				return getDaysOfWeekByOrder(i);
		return null;
	}

	// This is used privately. Constructs an individual based on the given
	// numeric constant.
	private DayOfWeek(int value, int pos) {
		super(MY_NAMESPACE + names[pos]);
//		super(getReal(ord));
		this.order = pos;
	}

//	/*
//	 * Val es un valor cualquiera, hay que mapearlo al array
//	 */
//	private static String getReal(int val) {
//		return DishCategory.getDishCategoriesByOrder(val).name();
////		return names[val];
//	}
	
	public DayOfWeek() {
		// Basic constructor. In general it is empty.
	}

	public DayOfWeek(String uri) {
		super(uri);
		// This is the commonly used constructor. In general it is like this,
		// just a call to super. It should not be used by external developers...
	}

	public static String getRDFSComment() {
		return "A comment describing what this enumeration is used for";
	}

	public static String getRDFSLabel() {
		return "Human readable ID for the enumeration. e.g: 'My Enumeration'"; //$NON-NLS-1$
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
