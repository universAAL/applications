package org.universAAL.ontology;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.Shopping.FoodManagement;
import org.universAAL.ontology.Shopping.Refrigerator;
import org.universAAL.ontology.Shopping.ShoppingList;

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
