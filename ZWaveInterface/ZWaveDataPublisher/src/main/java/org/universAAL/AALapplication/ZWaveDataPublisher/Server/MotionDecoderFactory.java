package org.universAAL.AALapplication.ZWaveDataPublisher.Server;

import org.universAAL.AALapplication.ZWaveDataPublisher.MotionContact.MotionContactSensorPublisher;

public class MotionDecoderFactory implements ISocketServerProtocolDecoderFactory {

	private MotionContactSensorPublisher motionPublisher;
	
	public MotionDecoderFactory(MotionContactSensorPublisher mp){
		motionPublisher = mp;
	}
	
	public ISocketSeverProtocolDecoder getNewProtocolDecoder() {
		// TODO Auto-generated method stub
		return new MotionDecoder(motionPublisher);
	}

}
