package org.universAAL.AALapplication.safety_home.service.windowProvider;

import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.safety_home.service.soapClient.SOAPClient;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.safetyDevices.Window;

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_WINDOW_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetyWindowProvider.owl#";
	public static final String MY_URI = SAFETY_WINDOW_PROVIDER_NAMESPACE + "Window";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_WINDOW_PROVIDER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;
	
	protected CPublisher(BundleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
	}
	
	protected CPublisher(BundleContext context) {
		super(context, getProviderInfo());
		try{
			ContextProvider info = new ContextProvider(SAFETY_WINDOW_PROVIDER_NAMESPACE + "WindowContextProvider");
			info.setType(ContextProviderType.controller);
			cp = new DefaultContextPublisher(context, info);
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	public void invoke() throws InterruptedException{
		//getUsers();
		while (true){
			Thread.sleep(30000);
			publishWindowStatus(0);
		}
	}
	
	private void publishWindowStatus(int deviceID){
		Device device=null;
		if(deviceID==0){
			Window window = new Window(CPublisher.DEVICE_URI_PREFIX + deviceID);
			device=(Device)window;
			window.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "window"));
			if (SOAPClient.isWindowClosed())
				window.setSensorStatus(new Integer(0));
			else
				window.setSensorStatus(new Integer(100));
			
			System.out.println("############### PUBLISHING EVENT ###############");
			cp.publish(new ContextEvent(window, Window.PROP_SENSOR_STATUS));
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
