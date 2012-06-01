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
public class MealCategory extends ManagedIndividual {
	//
	private static final String MY_NAMESPACE = Activator.MY_NAMESPACE;
	// MY URI is the URI of this concept. It is mandatory for all.
	public static final String MY_URI;
	// In this static block you set the URIs of your enumeration. Since we have
	// already defined a namespace in MyConcept for our domain, we reuse it.
	static {
		// The URI of your enumeration, which is the same name than the class
		MY_URI = MY_NAMESPACE + "MealCategory";
		register(MealCategory.class);
	}

	// These constants identify the possible values in your enumeration
	public static final int VALUE_BREAKFAST 		= 1;
	public static final int VALUE_MIDMORNING_SNACK	= 2;
	public static final int VALUE_LUNCH 			= 3;
	public static final int VALUE_AFTERNOON_SNACK	= 4;
	public static final int VALUE_DINNER 			= 5;
	public static final int VALUE_AFTERDINNER_SNACK	= 6;

	// In instances (individuals) this has the value of one of the numeric
	// constants above
	private int order;

	// You have to give a name to each possible value. These names are used in
	// the URI of each individual (the possible values of the enumeration), so
	// they must follow the format for individual names: initial lower case,
	// without spaces (probably all in lower case, but must be confirmed)
	private static final String[] names = { "Breakfast", "Midmorning snack", "Lunch",
			"Afternoon snack", "Dinner", "Afterdinner snack" };

	// These are all the possible instances of your enumeration: the individuals
	public static final MealCategory BREAKFAST 			= new MealCategory(VALUE_BREAKFAST, 0);
	public static final MealCategory MIDMORNING_SNACK 	= new MealCategory(VALUE_MIDMORNING_SNACK, 1);
	public static final MealCategory LUNCH 				= new MealCategory(VALUE_LUNCH, 2);
	public static final MealCategory AFTERNOON_SNACK 	= new MealCategory(VALUE_AFTERNOON_SNACK, 3);
	public static final MealCategory DINNER 			= new MealCategory(VALUE_DINNER, 4);
	public static final MealCategory AFTERDINNER_SNACK 	= new MealCategory(VALUE_AFTERDINNER_SNACK, 5);

	// This methods returns the list of all class members guaranteeing that no
	// other members will be created after a call to this method.
	public static ManagedIndividual[] getEnumerationMembers() {
		return new ManagedIndividual[] { BREAKFAST, MIDMORNING_SNACK, LUNCH,
				DINNER, AFTERNOON_SNACK, DINNER, AFTERDINNER_SNACK };
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
	public static MealCategory getMealCategoryByOrder(int order) {
		order = order + 1;
		switch (order) {
		case VALUE_BREAKFAST:
			return BREAKFAST;
		case VALUE_MIDMORNING_SNACK:
			return MIDMORNING_SNACK;
		case VALUE_LUNCH:
			return LUNCH;
		case VALUE_AFTERNOON_SNACK:
			return AFTERNOON_SNACK;
		case VALUE_DINNER:
			return DINNER;
		case VALUE_AFTERDINNER_SNACK:
			return AFTERDINNER_SNACK;
		default:
			System.out.println("getMealCategoryByOrder return null para: "+order);
			return null;
		}
	}

	// Returns the individual that matches the given name.
	public static final MealCategory valueOf(String name) {
		if (name == null)
			return null;

		// Remember to change the namespace to your domain...
		if (name.startsWith(MY_NAMESPACE))
			name = name.substring(MY_NAMESPACE.length());

		// Here you must use the first and last numeric constants you declared
		// at the beginning (lowest and highest numbers). Watch out! It is a
		// common mistake to forget to update the max constant in the 'for', for
		// instance if you add or reduce the amount of options...
		for (int i = VALUE_BREAKFAST; i <= VALUE_AFTERDINNER_SNACK; i++)
			if (names[i].equals(name))
				return getMealCategoryByOrder(i);
		return null;
	}

	// This is used privately. Constructs an individual based on the given
	// numeric constant.
	private MealCategory(int value, int pos) {
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
	
	public MealCategory() {
		// Basic constructor. In general it is empty.
	}

	public MealCategory(String uri) {
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
	
	public static MealCategory getMealCategoryByValue(String value) {
		if (value==null) {
			System.out.println("error in getMealCategoryByValue value=null");
			return null;
		}
		for (int i=0; i<MealCategory.names.length; i++) {
			if (MealCategory.names[i].compareToIgnoreCase(value)==0)
				return MealCategory.getMealCategoryByOrder(i);
		}
		return null;
	}
}
