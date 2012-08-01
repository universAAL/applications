package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.ontology.BiomedicalSensorsOntology;

public class ConnectionsList extends ConnectionType {

	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "ConnectionsList";

	public static final int BLUETOOTH = 0;
	public static final int WIFI = 1;
	public static final int CABLE = 2;

	private static final String[] connectionMethod = { "Bluetooth", "WiFi", "Cable" };

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
