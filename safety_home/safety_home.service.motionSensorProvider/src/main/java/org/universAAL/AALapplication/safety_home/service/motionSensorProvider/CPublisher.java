package org.universAAL.AALapplication.safety_home.service.motionSensorProvider;

import org.universAAL.AALapplication.safety_home.service.motionSensorSoapClient.SOAPClient;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.safetyDevices.HumiditySensor;
import org.universAAL.ontology.safetyDevices.MotionSensor;

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_MOTION_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetyMotionProvider.owl#";
	public static final String MY_URI = SAFETY_MOTION_PROVIDER_NAMESPACE + "Motion";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_MOTION_PROVIDER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;
	
	protected CPublisher(ModuleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
	}
	
	protected CPublisher(ModuleContext context) {
		super(context, getProviderInfo());
		try{
			cp = new DefaultContextPublisher(context, getProviderInfo());
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	public CPublisher(ModuleContext context, ContextProvider providerInfo, ContextPublisher cp) {
		super(context, providerInfo);
		try{
			this.cp = cp;
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void invoke() throws InterruptedException{
		//getUsers();
		while (true){
			Thread.sleep(20000);
			publishMotionDetection(0);
		}
	}
/*	
	private void publishMotionDetection(int deviceID){
		Device device=null;
		if(deviceID==0){
			MotionSensor motion = new MotionSensor(CPublisher.DEVICE_URI_PREFIX + deviceID);
			device=(Device)motion;
			motion.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "humidity"));
			motion.setMotion(SOAPClient.getMotionDetection());
			
			System.out.println("############### PUBLISHING EVENT ###############");
			cp.publish(new ContextEvent(motion, MotionSensor.PROP_MOTION));
			System.out.println("################################################");
		}
	}
*/

	private void publishMotionDetection(int deviceID){
		Device device=null;		
		MotionSensor motion = new MotionSensor(CPublisher.DEVICE_URI_PREFIX + deviceID);
		device=(Device)motion;
		motion.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "humidity"));
		motion.setMotion(SOAPClient.getMotionDetection());
		cp.publish(new ContextEvent(motion, MotionSensor.PROP_MOTION));
	}

	
	private static ContextProvider getProviderInfo() {
		ContextEventPattern cep6 = new ContextEventPattern();
		cep6.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT, MotionSensor.MY_URI));
		ContextProvider info = new ContextProvider(SAFETY_MOTION_PROVIDER_NAMESPACE + "MotionContextProvider");
		info.setType(ContextProviderType.controller);
		info.setProvidedEvents(new ContextEventPattern[]{cep6});

		return info;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
}
