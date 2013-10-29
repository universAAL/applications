/*
	Copyright 2012 CERTH, http://www.certh.gr
	
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
package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.ontology.BiomedicalSensorsFactory;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.phThing.PhThingOntology;

/**
 * The BioMedicalSensors Ontology.
 * 
 * @author billyk, joemoul
 */

public final class BiomedicalSensorsOntology extends Ontology {

    private static BiomedicalSensorsFactory factory = new BiomedicalSensorsFactory();

    public static final String NAMESPACE = Resource.uAAL_NAMESPACE_PREFIX
	    + "BiomedicalSensors.owl#";

    public BiomedicalSensorsOntology() {
	super(NAMESPACE);
    }




    public void create() {
	Resource r = getInfo();
	r
		.setResourceComment("The upper ontology defining  concepts from the Biomedical Sensors realm. ");
	r.setResourceLabel("Biomedical Sensor");
	addImport(DataRepOntology.NAMESPACE);
	addImport(PhThingOntology.NAMESPACE);


	OntClassInfoSetup oci;

	// load SensorType
	oci = createNewAbstractOntClassInfo(SensorType.MY_URI);
	oci.setResourceComment("The type of a sensor");
	oci.setResourceLabel("Sensor Type");

	// load ConnectionType
	oci = createNewAbstractOntClassInfo(ConnectionType.MY_URI);
	oci.setResourceComment("The type of connection to the sensor");
	oci.setResourceLabel("Connection Type");

	// load measured Entity
	oci = createNewOntClassInfo(MeasuredEntity.MY_URI, factory, 0);
	oci.setResourceComment("The class of all measured entities");
	oci.setResourceLabel("Measured Entity");
	oci.addSuperClass(ManagedIndividual.MY_URI);

	oci.addDatatypeProperty(MeasuredEntity.PROP_MEASUREMENT_NAME)
		.setFunctional();
	oci.addDatatypeProperty(MeasuredEntity.PROP_MEASUREMENT_VALUE)
		.setFunctional();
	oci.addDatatypeProperty(MeasuredEntity.PROP_MEASUREMENT_ERROR)
		.setFunctional();
	oci.addDatatypeProperty(MeasuredEntity.PROP_MEASUREMENT_UNIT)
		.setFunctional();

	oci.addDatatypeProperty(MeasuredEntity.PROP_MEASUREMENT_TIME)
		.setFunctional();
	oci.addDatatypeProperty(MeasuredEntity.PROP_TERMINOLOGY_CODE)
		.setFunctional();
	oci.addDatatypeProperty(MeasuredEntity.PROP_TERMINOLOGY_PURL)
		.setFunctional();

	// load composite biomedical sensor
	oci = createNewOntClassInfo(CompositeBiomedicalSensor.MY_URI, factory,
		0);
	oci.setResourceComment("The class of all biomedical sensors");
	oci.setResourceLabel("Biomedical Sensor");
	oci.addSuperClass(Device.MY_URI);

	oci.addObjectProperty(CompositeBiomedicalSensor.PROP_CONNECTION_TYPE)
		.setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			CompositeBiomedicalSensor.PROP_CONNECTION_TYPE,
			ConnectionType.MY_URI, 1, 1));

	oci.addDatatypeProperty(
		CompositeBiomedicalSensor.PROP_DISCOVERED_BT_SERVICE)
		.setFunctional();
	oci.addDatatypeProperty(CompositeBiomedicalSensor.PROP_SENSOR_NAME)
		.setFunctional();
	oci.addDatatypeProperty(CompositeBiomedicalSensor.PROP_IS_CONNECTED)
		.setFunctional();
	oci.addObjectProperty(CompositeBiomedicalSensor.PROP_SENSOR_TYPE)
		.setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			CompositeBiomedicalSensor.PROP_SENSOR_TYPE,
			SensorType.MY_URI, 1, 1));
	oci.addObjectProperty(CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS)
		.setFunctional();
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS,
			MeasuredEntity.MY_URI, 1, 1000));

	// load ConnectionList
	oci = createNewAbstractOntClassInfo(ConnectionsList.MY_URI);
	oci.setResourceComment("The connection type of sensors");
	oci.setResourceLabel("ConnectionsList");
	oci.addSuperClass(ConnectionType.MY_URI);
	oci.toEnumeration(new ManagedIndividual[] { ConnectionsList.bt,
		ConnectionsList.wifi, ConnectionsList.cable });

	// load Zephyr
	oci = createNewAbstractOntClassInfo(Zephyr.MY_URI);
	oci.setResourceComment("The BioHarness Zephyr sensor");
	oci.setResourceLabel("Zephyr");
	oci.addSuperClass(SensorType.MY_URI);

	// load Scale
	oci = createNewAbstractOntClassInfo(Scale.MY_URI);
	oci.setResourceComment("The Scale sensor");
	oci.setResourceLabel("Scale");
	oci.addSuperClass(SensorType.MY_URI);

	// load BloodPressure Monitor
	oci = createNewAbstractOntClassInfo(BPmonitor.MY_URI);
	oci.setResourceComment("The Blood Pressure monitor sensor");
	oci.setResourceLabel("BPmonitor");
	oci.addSuperClass(SensorType.MY_URI);
	oci.toEnumeration(new ManagedIndividual[] {
		BPmonitor.brand1_bp_monitor, BPmonitor.brand2_bp_monitor });

	// load Service
	oci = createNewOntClassInfo(BiomedicalSensorService.MY_URI, factory, 1);
	oci
		.setResourceComment("The class of services controling biomedical sensors");
	oci.setResourceLabel("Biomedical Sensors Service");
	oci.addSuperClass(Service.MY_URI);
	oci.addObjectProperty(BiomedicalSensorService.PROP_CONTROLS);
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		BiomedicalSensorService.PROP_CONTROLS,
		CompositeBiomedicalSensor.MY_URI));

	// load AlertService
	oci = createNewOntClassInfo(AlertService.MY_URI, factory, 1);
	oci.setResourceComment("The class of services monitoring alerts");
	oci.setResourceLabel("Biomedical Sensors Alerts Service");
	oci.addSuperClass(BiomedicalSensorService.MY_URI);
	oci.addDatatypeProperty(AlertService.PROP_MONITORS).setFunctional();
    }
}
