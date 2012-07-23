package org.universAAL.AALapplication.safety_home.service.smokeDetectionProvider;

/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_6#Publishing_context_events */
import org.universAAL.AALapplication.safety_home.service.smokeDetectionSoapClient.SOAPClient;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.safetyDevices.SmokeSensor;

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_SMOKE_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetySmokeProvider.owl#";
	public static final String MY_URI = SAFETY_SMOKE_PROVIDER_NAMESPACE + "Smoke";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_SMOKE_PROVIDER_NAMESPACE + "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;
	
	protected CPublisher(ModuleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
	}
	
	protected CPublisher(ModuleContext context) {
		super(context, getProviderInfo());
		try{
			ContextProvider info = new ContextProvider(SAFETY_SMOKE_PROVIDER_NAMESPACE + "SmokeContextProvider");
			info.setType(ContextProviderType.controller);
			cp = new DefaultContextPublisher(context, info);
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
			Thread.sleep(63000);
			publishSmoke(0);
		}
	}
	
	private void publishSmoke(int deviceID){
		Device device=null;
		if (SOAPClient.getSmoke())
			if(deviceID==0){
				SmokeSensor smoke = new SmokeSensor(CPublisher.DEVICE_URI_PREFIX + deviceID);
				device=(Device)smoke;
				smoke.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "smoke"));
				smoke.setSmoke(SOAPClient.getSmoke());
				
				System.out.println("############### PUBLISHING EVENT ###############");
				cp.publish(new ContextEvent(smoke, SmokeSensor.PROP_SMOKE));
				System.out.println("################################################");
			}
	}

	
	private static ContextProvider getProviderInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
}