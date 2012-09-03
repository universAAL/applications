/*
	Copyright 2008-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer Gesellschaft - Institut fuer Graphische Datenverarbeitung 
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package org.universAAL.ontology.nutrition;

import java.util.Hashtable;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.Activator;

/**
 * Ontological service that handles nutrition. Methods included in this
 * class are the mandatory ones for representing an ontological service in Java
 * classes for uAAL.
 * 
 * @author hecgamar
 * 
 */
public class NutritionService extends Service {
	public static final String NAMESPACE;
	public static final String MY_URI;
	public static final String PROP_GET_RECIPE;
	
	public static final String NUTRITIONAL_SERVER_NAMESPACE = "http://ontology.uaal.org/NutritionalServer.owl#";
	public static final String SERVICE_GET_RECIPE = NUTRITIONAL_SERVER_NAMESPACE + "getRecipe";
	public static final String OUTPUT_GET_RECIPE = NUTRITIONAL_SERVER_NAMESPACE + "recipe";

	private static Hashtable nutritionRestrictions = new Hashtable(1);
	static {
		System.out.println("Nutri: Nutrional Advisor Service available at: "+ NUTRITIONAL_SERVER_NAMESPACE);
		NAMESPACE = Activator.MY_NAMESPACE;
		MY_URI = Activator.MY_NAMESPACE + "NutritionService";
		PROP_GET_RECIPE = Activator.MY_NAMESPACE + "getRecipe";
		register(NutritionService.class);
		
		// GET_RECIPE devuelve Recipe
//		boolean res = addRestriction(Restriction.getAllValuesRestriction(PROP_GET_RECIPE,
//				Recipe.MY_URI), new String[] { PROP_GET_RECIPE },
//				nutritionRestrictions);
		boolean res = addRestriction(Restriction.getAllValuesRestrictionWithCardinality(PROP_GET_RECIPE,
				Recipe.MY_URI, 1,1), new String[] { PROP_GET_RECIPE },
				nutritionRestrictions);
		if (res==false)
			System.out.println("Nutri: addRestriction result: "+res + " for: "+PROP_GET_RECIPE);
	}

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		System.out.println("Nutri: getClassRestrictionsOnProperty: "+propURI);
		if (propURI == null) {
			System.out.println("Nutri: propURI es null!");
			return null;
		}
		Object r = nutritionRestrictions.get(propURI);
		System.out.println("Nutri: r is: "+r + " for: "+propURI);
		if (r!=null && r instanceof Restriction)
			return (Restriction) r;
		return Service.getClassRestrictionsOnProperty(propURI);
	}

	public static String getRDFSComment() {
		return "The class of services nutrition recipes.";
	}

	public static String getRDFSLabel() {
		return "Nutrition";
	}

	public NutritionService() {
		super();
	}

	public NutritionService(String uri) {
		super(uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universAAL.ontology.Service#getClassLevelRestrictions()
	 */
	protected Hashtable getClassLevelRestrictions() {
		return nutritionRestrictions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
	 * (java.lang.String)
	 */
	public int getPropSerializationType(String propURI) {
		return PROP_GET_RECIPE.equals(propURI) ? PROP_SERIALIZATION_FULL : super
				.getPropSerializationType(propURI);
	}

	public boolean isWellFormed() {
		return true;
	}
}
