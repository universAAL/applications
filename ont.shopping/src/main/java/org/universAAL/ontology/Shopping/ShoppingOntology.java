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

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.ShoppingFactory;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.profile.ProfileOntology;

/**
 * @author dimokas
 * 
 */

public final class ShoppingOntology extends
	org.universAAL.middleware.owl.Ontology {

    private static ShoppingFactory factory = new ShoppingFactory();

    public static final String NAMESPACE = Resource.uAAL_NAMESPACE_PREFIX
	    + "FoodManagement.owl#";

    public ShoppingOntology() {
	super(NAMESPACE);
    }

    public ShoppingOntology(String ontURI) {
	super(ontURI);
    }

    public void create() {
	Resource r = getInfo();
	r.setResourceComment("Ontology for food and shopping.");
	r.setResourceLabel("FoodManagement");

	addImport(DataRepOntology.NAMESPACE);
	// addImport(ServiceBusOntology.NAMESPACE);
	// addImport(LocationOntology.NAMESPACE);
	addImport(PhThingOntology.NAMESPACE);
	addImport(ProfileOntology.NAMESPACE);

	OntClassInfoSetup oci;

	// load FoodItem
	oci = createNewOntClassInfo(FoodItem.MY_URI, factory, 0);
	oci.setResourceComment("The class of food items");
	oci.setResourceLabel("Food item");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(FoodItem.PROP_NAME).setFunctional();
	oci.addDatatypeProperty(FoodItem.PROP_QUANTITY).setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(FoodItem.PROP_QUANTITY,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	// .addRestriction(new BoundedValueRestriction(FoodItem.PROP_QUANTITY,
	// new Integer(0), true, new Integer(100), true)));
	oci.addRestriction(MergedRestriction.getCardinalityRestriction(
		FoodItem.PROP_NAME, 1, 1));

	// load Refrigerator
	oci = createNewOntClassInfo(Refrigerator.MY_URI, factory, 1);
	oci.setResourceComment("The class of Refrigerator");
	oci.setResourceLabel("Refrigerator");
	oci.addSuperClass(Device.MY_URI);
	oci.addObjectProperty(Refrigerator.PROP_TEMPERATURE).setFunctional();
	oci.addDatatypeProperty(Refrigerator.PROP_DEVICE_STATUS)
		.setFunctional();
	oci.addObjectProperty(Refrigerator.PROP_HAS_FOODITEMS).setFunctional();
	oci.addRestriction(MergedRestriction.getCardinalityRestriction(
		Refrigerator.PROP_TEMPERATURE, 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Refrigerator.PROP_DEVICE_STATUS, TypeMapper
				.getDatatypeURI(Integer.class), 1, 1));
	// .addRestriction( new
	// BoundedValueRestriction(Refrigerator.PROP_DEVICE_STATUS,new
	// Integer(0), true, new Integer(100), true)));
	oci
		.addRestriction(MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				Refrigerator.PROP_HAS_FOODITEMS,
				FoodItem.MY_URI, 1, -1));

	// load ShoppingList
	oci = createNewOntClassInfo(ShoppingList.MY_URI, factory, 3);
	oci.setResourceComment("The class of shopping list");
	oci.setResourceLabel("Shopping list");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(ShoppingList.PROP_NAME).setFunctional();
	oci.addDatatypeProperty(ShoppingList.PROP_DATE).setFunctional();
	oci.addObjectProperty(ShoppingList.PROP_HAS_FOODITEMS).setFunctional();
	oci.addRestriction(MergedRestriction.getCardinalityRestriction(
		ShoppingList.PROP_NAME, 1, 1));
	oci.addRestriction(MergedRestriction.getCardinalityRestriction(
		ShoppingList.PROP_DATE, 1, 1));
	oci
		.addRestriction(MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				ShoppingList.PROP_HAS_FOODITEMS,
				FoodItem.MY_URI, 1, -1));

	// load FoodManagement
	oci = createNewOntClassInfo(FoodManagement.MY_URI, factory, 2);
	oci.setResourceComment("The class of services controling food items");
	oci.setResourceLabel("FoodManagement");
	oci.addSuperClass(Service.MY_URI);
	oci.addObjectProperty(FoodManagement.PROP_FRIDGE_CONTROLS);
	oci.addObjectProperty(FoodManagement.PROP_SHOPPINGLIST_CONTROLS);
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		FoodManagement.PROP_FRIDGE_CONTROLS, Refrigerator.MY_URI));
	oci
		.addRestriction(MergedRestriction.getAllValuesRestriction(
			FoodManagement.PROP_SHOPPINGLIST_CONTROLS,
			ShoppingList.MY_URI));
	// oci.addObjectProperty(FoodManagement.PROP_CONTROLS);
	// oci.addRestriction(MergedRestriction.getAllValuesRestriction(
	// FoodManagement.PROP_CONTROLS, Refrigerator.MY_URI));

    }

}
