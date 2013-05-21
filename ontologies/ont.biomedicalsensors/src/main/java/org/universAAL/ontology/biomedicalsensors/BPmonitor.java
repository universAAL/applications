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

/**
 * Ontological enumeration of possible blood-pressure monitor brandnames.
 * 
 * @author billk, joemoul
 * 
 */
public class BPmonitor extends SensorType {
    public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
	    + "BPmonitor";

    public static final int BRAND1 = 0;
    public static final int BRAND2 = 1;

    private static final String[] brandNames = { "Brand1_bp_monitor",
	    "Brand2_bp_monitor" };

    public static final BPmonitor brand1_bp_monitor = new BPmonitor(BRAND1);
    public static final BPmonitor brand2_bp_monitor = new BPmonitor(BRAND2);

    public String getClassURI() {
	return MY_URI;
    }

    public static BPmonitor getBPMonitorByOrder(int order) {
	switch (order) {
	case BRAND1:
	    return brand1_bp_monitor;
	case BRAND2:
	    return brand2_bp_monitor;
	default:
	    return null;
	}
    }

    public static final BPmonitor valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(BiomedicalSensorsOntology.NAMESPACE))
	    name = name.substring(BiomedicalSensorsOntology.NAMESPACE.length());

	for (int i = BRAND1; i <= BRAND2; i++)
	    if (brandNames[i].equals(name))
		return getBPMonitorByOrder(i);

	return null;
    }

    private int order;

    private BPmonitor(int order) {
	super(BiomedicalSensorsOntology.NAMESPACE + brandNames[order]);
	this.order = order;
    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_OPTIONAL;
    }

    public boolean isWellFormed() {
	return true;
    }

    public String name() {
	return brandNames[order];
    }

    public int ord() {
	return order;
    }
}
