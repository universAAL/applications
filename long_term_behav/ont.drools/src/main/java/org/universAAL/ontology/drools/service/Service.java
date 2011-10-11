package org.universAAL.ontology.drools.service;


import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.drools.Consequence;
import org.universAAL.ontology.drools.Fact;
import org.universAAL.ontology.drools.FactProperty;
import org.universAAL.ontology.drools.Rule;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.phThing.Sensor;

//If you are making a concept that does not inherit from any other you just extend ManagedIndividual
//Otherwise you extend the concept class you inherit from
public class Service extends ManagedIndividual {
  // Make sure you use the same namespace in all your domain ontology
  // You can declare the namespace in your root concept and later reuse it in
  // the rest of classes
  public static final String MY_NAMESPACE;
  // MY URI is the URI of this concept. It is mandatory for all.
  public static final String MY_URI;
  // Now declare ALL properties that this concept defines
  public static final String FACT;
  public static final String RULE;
  public static final String CONSEQUENCE;
  // In this static block you set the URIs of your concept and its properties
  static {
	// Namespaces must follow this format
	MY_NAMESPACE = "http://ontology.universAAL.org/Drools.Service.owl#";
	// The URI of your concept, which is the same name than the class
	MY_URI = FactProperty.MY_NAMESPACE + "Service";
	// Now declare the URIs of the properties. They must start with lower
	// case.
	FACT = Fact.MY_NAMESPACE + "hasFact";
	RULE = Rule.MY_NAMESPACE + "hasRule";
	CONSEQUENCE = Consequence.MY_NAMESPACE + "hasConsequence";
//	CONTEXT_EVENT = 
	
	
	// This line registers the ontology concept in the platform
	register(FactProperty.class);
  }

  // In this method you must return the restrictions on the property you are
  // asked for
  public static Restriction getClassRestrictionsOnProperty(String propURI) {
	// For each property you have declared, return the appropriate
	// restrictions
	
	return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	// In this case we have no parent concept so we use ManagedIndividual.
	// If you inherited from other concept, then use it instead.
  }

  // This method is used by the system to handle the ontologies. It returns
  // the URIs of all properties used in this concept.
  public static String[] getStandardPropertyURIs() {
	// First get property URIs of your parent concept (in this case we have
	// none, so we use ManagedIndividual)
	String[] inherited = ManagedIndividual.getStandardPropertyURIs();
	String[] toReturn = new String[inherited.length + 3];// Make sure you
	// increase the size by the number of properties declared in your
	// concept!
	int i = 0;
	// With this we copy the properties of the parent...
	while (i < inherited.length) {
	    toReturn[i] = inherited[i];
	    i++;
	}
	// ...and with this we add the properties declared in this concept
	toReturn[i++] = FACT;
	toReturn[i++] = RULE;
	toReturn[i] = CONSEQUENCE;
	// Now we have all the property URIs of the concept, both inherited and
	// declared by it.
	return toReturn;
  }

  public Service() {
	// Basic constructor. In general it is empty.
  }

  public Service(String uri) {
	super(uri);
	// This is the commonly used constructor. In general it is like this,
	// just a call to super.
  }

  public static String getRDFSComment() {
	return "A comment describing what this concept is used for";
  }

  public static String getRDFSLabel() {
	return "Human readable ID for the concept. e.g: 'My Concept'";
  }

  // This method is used for serialization purposes, to restrict the amount of
  // information to serialize when forwarding it among nodes.
  // For each property you must return one of PROP_SERIALIZATION_FULL,
  // REDUCED, OPTIONAL or UNDEFINED.
  // Refer to their javadoc to see what they mean.
  public int getPropSerializationType(String propURI) {
	// In this case we serialize everything. It is up to you to define what
	// is important to be serialized and what is expendable in your concept.
	return PROP_SERIALIZATION_FULL;
  }




}
