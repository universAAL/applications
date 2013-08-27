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

public class ConnectionsList extends ConnectionType {

    public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
	    + "ConnectionsList";

    public static final int BLUETOOTH = 0;
    public static final int WIFI = 1;
    public static final int CABLE = 2;

<<<<<<< .mine
	private static final String[] connectionMethod = { "Bluetooth", "WiFi",
			"Cable" };
=======
    private static final String[] connectionMethod = { "Bluetooth", "WiFi",
	    "Cable" };
>>>>>>> .r1661

    public static final ConnectionsList bt = new ConnectionsList(BLUETOOTH);
    public static final ConnectionsList wifi = new ConnectionsList(WIFI);
    public static final ConnectionsList cable = new ConnectionsList(CABLE);

    public String getClassURI() {
	return MY_URI;
    }

    public static ConnectionsList getConnectionTypeByOrder(int order) {
	switch (order) {
	case BLUETOOTH:
	    return bt;
	case WIFI:
	    return wifi;
	case CABLE:
	    return cable;
	default:
	    return null;
	}
    }

    public static final ConnectionsList valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(BiomedicalSensorsOntology.NAMESPACE))
	    name = name.substring(BiomedicalSensorsOntology.NAMESPACE.length());

	for (int i = BLUETOOTH; i <= CABLE; i++)
	    if (connectionMethod[i].equals(name))
		return getConnectionTypeByOrder(i);

	return null;
    }

    private int order;

    private ConnectionsList(int order) {
	super(BiomedicalSensorsOntology.NAMESPACE + connectionMethod[order]);
	this.order = order;
    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_OPTIONAL;

    }

    public boolean isWellFormed() {
	return true;
    }

    public String name() {
	return connectionMethod[order];
    }

    public int ord() {
	return order;
    }
}
