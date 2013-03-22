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

package org.universAAL.ontology;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.Shopping.FoodManagement;
import org.universAAL.ontology.Shopping.Refrigerator;
import org.universAAL.ontology.Shopping.ShoppingList;

/**
 * @author dimokas
 * 
 */

public class ShoppingFactory extends ResourceFactoryImpl {

    public ShoppingFactory() {
    }

    public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {
		switch (factoryIndex) {
		case 0:
		    return new FoodItem(instanceURI);
		case 1:
		    return new Refrigerator(instanceURI);
		case 2:
		    return new FoodManagement(instanceURI);
		case 3:
		    return new ShoppingList(instanceURI);
		}

	return null;
    }

    public Resource castAs(Resource r, String classURI) {
    	return null;
    }
}
