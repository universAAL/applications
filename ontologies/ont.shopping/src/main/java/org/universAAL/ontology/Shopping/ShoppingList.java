/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.ontology.Shopping;

import java.util.ArrayList;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.Shopping.ShoppingOntology;

/**
 * @author dimokas
 *
 */
public class ShoppingList extends ManagedIndividual {
	
	public static final String MY_URI; 
	public static final String PROP_NAME;
	public static final String PROP_DATE;
	public static final String PROP_ID;
	public static final String PROP_HAS_FOODITEMS;

	static{
		MY_URI = ShoppingOntology.NAMESPACE + "ShoppingList";
		PROP_NAME = ShoppingOntology.NAMESPACE + "shoppingListName";
		PROP_DATE = ShoppingOntology.NAMESPACE + "shoppingListDate";
		PROP_ID = ShoppingOntology.NAMESPACE + "shoppingListId";
		PROP_HAS_FOODITEMS = ShoppingOntology.NAMESPACE + "FoodItems";
	}

	public static String[] getStandardPropertyURIs() {
		//return new String[] {PROP_DEVICE_LOCATION, PROP_DEVICE_STATUS};
		return new String[] {PROP_NAME,PROP_DATE};
	}

	public String getClassURI() {
    	return MY_URI;
    }
	
	public ShoppingList(){
		super();
	}
	
	public ShoppingList(String uri) {
		super(uri);				
	}
		
	public ShoppingList(String uri, String name) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_NAME, new String(name));
	}

	public ShoppingList(String uri, String name, String creationDate) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_NAME, new String(name));
		props.put(PROP_DATE, new String(creationDate));
	}

	public ShoppingList(String uri, String name, String creationDate, int id) {
		super(uri);
		if (name == null)
			   throw new IllegalArgumentException();
			
		props.put(PROP_NAME, new String(name));
		props.put(PROP_DATE, new String(creationDate));
		props.put(PROP_ID, new Integer(id));
	}

	public String getName() {
		return (String) props.get(PROP_NAME);
	}
	public void setName(String name) {
		if (name != null)
		    props.put(PROP_NAME, name);
	}

	public String getDate() {
		return (String) props.get(PROP_DATE);
	}
	public void setDate(String date) {
		if (date != null)
		    props.put(PROP_DATE, date);
	}

	public Integer getId() {
		return (Integer) props.get(PROP_ID);
	}
	public void setId(int id) {
	    props.put(PROP_ID, new Integer(id));
	}

	public void setFoodItems(ArrayList fi) {
	    props.put(PROP_HAS_FOODITEMS, fi);
	}
	
	public ArrayList getFoodItems(){
		return (ArrayList)props.get(PROP_HAS_FOODITEMS);
	}
	
	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
		//return (PROP_NAME.equals(propURI)) ? PROP_SERIALIZATION_REDUCED
		//	: PROP_SERIALIZATION_FULL;
	}
		
	public boolean isWellFormed() {
		return true;
		//return props.containsKey(PROP_NAME)
		//&& props.containsKey(PROP_QUANTITY);
	}
	
	
}
