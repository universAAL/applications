package org.universAAL.ontology.Safety;

import org.universAAL.middleware.owl.BoundingValueRestriction;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.SafetyFactory;
import org.universAAL.ontology.profile.ProfileOntology;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.phThing.PhThingOntology;

public final class SafetyOntology extends Ontology {

    private static SafetyFactory factory = new SafetyFactory();

    public static final String NAMESPACE = "http://ontology.universaal.org/Safety.owl#";

    public SafetyOntology() {
    	super(NAMESPACE);
    }

    public SafetyOntology(String ontURI) {
    	super(ontURI);
    }


    public void create() {
	Resource r = getInfo();
	r.setResourceComment("Ontology for safety at home.");
	r.setResourceLabel("SafetyManagement");
	addImport(DataRepOntology.NAMESPACE);
	//addImport(ServiceBusOntology.NAMESPACE);
	//addImport(LocationOntology.NAMESPACE);
	addImport(PhThingOntology.NAMESPACE);
	addImport(ProfileOntology.NAMESPACE);

	OntClassInfoSetup oci;

	// load Door
	oci = createNewOntClassInfo(Door.MY_URI, factory, 0);
	oci.setResourceComment("The class of Door");
	oci.setResourceLabel("Door");
	oci.addSuperClass(Device.MY_URI);
	oci.addDatatypeProperty(Door.PROP_DEVICE_STATUS).setFunctional();
	oci.addObjectProperty(Door.PROP_DEVICE_RFID).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			Door.PROP_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1).addRestriction( new BoundingValueRestriction(Door.PROP_DEVICE_STATUS,
				new Integer(0), true, new Integer(100), true)));
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			Door.PROP_DEVICE_RFID, Door.MY_URI, 1, 1));

	// load Humidity Sensor
	oci = createNewOntClassInfo(HumiditySensor.MY_URI, factory, 1);
	oci.setResourceComment("The class of Humidity Sensor");
	oci.setResourceLabel("Humidity");
	oci.addSuperClass(Device.MY_URI);
	oci.addDatatypeProperty(HumiditySensor.PROP_DEVICE_STATUS).setFunctional();
	oci.addObjectProperty(HumiditySensor.PROP_HUMIDITY).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			HumiditySensor.PROP_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1).addRestriction( new BoundingValueRestriction(HumiditySensor.PROP_DEVICE_STATUS,
				new Integer(0), true, new Integer(100), true)));
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			HumiditySensor.PROP_HUMIDITY, HumiditySensor.MY_URI, 1, 1));
    
	// load Light Sensor
	oci = createNewOntClassInfo(LightSensor.MY_URI, factory, 2);
	oci.setResourceComment("The class of Light Sensor");
	oci.setResourceLabel("Light");
	oci.addSuperClass(Device.MY_URI);
	oci.addDatatypeProperty(LightSensor.PROP_DEVICE_STATUS).setFunctional();
	oci.addObjectProperty(LightSensor.PROP_SENSOR_STATUS).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			LightSensor.PROP_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1).addRestriction( new BoundingValueRestriction(LightSensor.PROP_DEVICE_STATUS,
				new Integer(0), true, new Integer(100), true)));
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			LightSensor.PROP_SENSOR_STATUS, LightSensor.MY_URI, 1, 1));

	// load Motion Sensor
	oci = createNewOntClassInfo(MotionSensor.MY_URI, factory, 3);
	oci.setResourceComment("The class of Motion Sensor");
	oci.setResourceLabel("Motion");
	oci.addSuperClass(Device.MY_URI);
	oci.addDatatypeProperty(MotionSensor.PROP_DEVICE_STATUS).setFunctional();
	oci.addObjectProperty(MotionSensor.PROP_MOTION).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			MotionSensor.PROP_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1).addRestriction( new BoundingValueRestriction(MotionSensor.PROP_DEVICE_STATUS,
				new Integer(0), true, new Integer(100), true)));
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			MotionSensor.PROP_MOTION, MotionSensor.MY_URI, 1, 1));

	// load Smoke Sensor
	oci = createNewOntClassInfo(SmokeSensor.MY_URI, factory, 4);
	oci.setResourceComment("The class of Smoke Sensor");
	oci.setResourceLabel("Smoke");
	oci.addSuperClass(Device.MY_URI);
	oci.addDatatypeProperty(SmokeSensor.PROP_DEVICE_STATUS).setFunctional();
	oci.addObjectProperty(SmokeSensor.PROP_SMOKE).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			SmokeSensor.PROP_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1).addRestriction( new BoundingValueRestriction(SmokeSensor.PROP_DEVICE_STATUS,
				new Integer(0), true, new Integer(100), true)));
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			SmokeSensor.PROP_SMOKE, SmokeSensor.MY_URI, 1, 1));

	// load Temperature Sensor
	oci = createNewOntClassInfo(TemperatureSensor.MY_URI, factory, 5);
	oci.setResourceComment("The class of Temperature Sensor");
	oci.setResourceLabel("Temperature");
	oci.addSuperClass(Device.MY_URI);
	oci.addDatatypeProperty(TemperatureSensor.PROP_DEVICE_STATUS).setFunctional();
	oci.addObjectProperty(TemperatureSensor.PROP_TEMPERATURE).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			TemperatureSensor.PROP_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1).addRestriction( new BoundingValueRestriction(TemperatureSensor.PROP_DEVICE_STATUS,
				new Integer(0), true, new Integer(100), true)));
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			TemperatureSensor.PROP_TEMPERATURE, TemperatureSensor.MY_URI, 1, 1));
	
	// load Window Sensor
	oci = createNewOntClassInfo(Window.MY_URI, factory, 6);
	oci.setResourceComment("The class of Window Sensor");
	oci.setResourceLabel("Window");
	oci.addSuperClass(Device.MY_URI);
	oci.addDatatypeProperty(Window.PROP_DEVICE_STATUS).setFunctional();
	oci.addObjectProperty(Window.PROP_SENSOR_STATUS).setFunctional();
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			Window.PROP_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1).addRestriction( new BoundingValueRestriction(Window.PROP_DEVICE_STATUS,
				new Integer(0), true, new Integer(100), true)));
	oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
			Window.PROP_SENSOR_STATUS, Window.MY_URI, 1, 1));

	// load SafetyManagement
	oci = createNewOntClassInfo(SafetyManagement.MY_URI, factory, 7);
	oci.setResourceComment("The class of services controling safety items");
	oci.setResourceLabel("SafetyManagement");
	oci.addSuperClass(Service.MY_URI);
	oci.addObjectProperty(SafetyManagement.PROP_CONTROLS);
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		SafetyManagement.PROP_CONTROLS, Door.MY_URI));

	
    }

}
