package org.universAAL.AALapplication.ont;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.AALapplication.ont.foodDevices.Refrigerator;
import org.universAAL.AALapplication.ont.foodDevices.FoodManagement;
import org.universAAL.AALapplication.ont.foodDevices.FoodItem;
import org.universAAL.AALapplication.ont.foodDevices.ShoppingList;

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
