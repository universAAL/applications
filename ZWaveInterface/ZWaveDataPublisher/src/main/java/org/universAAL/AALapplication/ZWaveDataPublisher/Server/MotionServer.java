package org.universAAL.AALapplication.ZWaveDataPublisher.Server;

import org.universAAL.AALapplication.ZWaveDataPublisher.MotionContact.MotionContactSensorPublisher;

public class MotionServer extends AbstractSocketServerXSocket {
	
	
	public MotionServer(ISocketServerProtocolDecoderFactory protocolFactory,
			int listenPort) {
		super(protocolFactory, listenPort);
		// TODO Auto-generated constructor stub
	}

	public void packetArrived(ISocketServerConnection connection, Object pckt) {
		// TODO Auto-generated method stub

	}

	public void socketDisconnected(ISocketServerConnection connection) {
		// TODO Auto-generated method stub

	}

	public void socketConnected(ISocketServerConnection connection) {
		// TODO Auto-generated method stub

	}

	public void socketException(ISocketServerConnection connection, Exception ex) {
		// TODO Auto-generated method stub

	}

}
