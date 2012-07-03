package org.universAAL.AALapplication.safety_home.service.client;

import org.universAAL.ontology.phThing.Device;

import java.util.List;
import java.util.Vector;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.safetyDevices.SafetyManagement;
import org.universAAL.ontology.safetyDevices.Door;
import org.universAAL.ontology.safetyDevices.Window;
import org.universAAL.ontology.safetyDevices.LightSensor;
import org.universAAL.ontology.safetyDevices.TemperatureSensor;
import org.universAAL.ontology.safetyDevices.HumiditySensor;
import org.universAAL.ontology.safetyDevices.SmokeSensor;
import org.universAAL.ontology.safetyDevices.MotionSensor;


/**
 * @author dimokas
 * 
 */
class SafetyClient extends ContextSubscriber {

	private static ServiceCaller caller;

	private static final String SAFETY_CONSUMER_NAMESPACE = "http://ontology.universaal.org/SafetyClient.owl#";

	private static final String OUTPUT_LIST_OF_DEVICES = SAFETY_CONSUMER_NAMESPACE
			+ "controlledDevices";
	
    private static final String OUTPUT_DEVICE_LOCATION = SAFETY_CONSUMER_NAMESPACE + "location";
    private static final String OUTPUT_DEVICE_STATUS = SAFETY_CONSUMER_NAMESPACE + "status";

    private static Vector values = new Vector(); 
    
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern cep1 = new ContextEventPattern();
		cep1.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, Door.MY_URI));

		ContextEventPattern cep2 = new ContextEventPattern();
		cep2.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, Window.MY_URI));

		ContextEventPattern cep3 = new ContextEventPattern();
		cep3.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, LightSensor.MY_URI));

		ContextEventPattern cep4 = new ContextEventPattern();
		cep4.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, TemperatureSensor.MY_URI));

		ContextEventPattern cep5 = new ContextEventPattern();
		cep5.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, HumiditySensor.MY_URI));

		ContextEventPattern cep6 = new ContextEventPattern();
		cep6.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, MotionSensor.MY_URI));

		ContextEventPattern cep7 = new ContextEventPattern();
		cep7.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, SmokeSensor.MY_URI));

		return new ContextEventPattern[] { cep1, cep2, cep3, cep4, cep5, cep6, cep7 };
	}

	SafetyClient(ModuleContext context) {
		// the constructor register us to the bus
		super(context, getContextSubscriptionParams());
		caller = new DefaultServiceCaller(context);
		new SafetyUIClient(getControlledDevices());
	}

	/*****************************************************************/
	/* 	Services Requests											 */
	/*****************************************************************/

	private static ServiceRequest lockRequest(String deviceURI) {
		ServiceRequest turnOff = new ServiceRequest(new SafetyManagement(), null);

		turnOff.addValueFilter(new String[] { SafetyManagement.PROP_CONTROLS }, new Door(deviceURI));
		turnOff.addChangeEffect(new String[] { SafetyManagement.PROP_CONTROLS,
				Door.PROP_DEVICE_STATUS }, new Integer(0));
		return turnOff;
	}

	private static ServiceRequest unlockRequest(String deviceURI) {
		ServiceRequest turnOn = new ServiceRequest(new SafetyManagement(), null);

		turnOn.addValueFilter(new String[] { SafetyManagement.PROP_CONTROLS }, new Door(deviceURI));
		turnOn.addChangeEffect(new String[] { SafetyManagement.PROP_CONTROLS,Door.PROP_DEVICE_STATUS },
				new Integer(100));
		return turnOn;
	}
	
	private static ServiceRequest openRequest(String deviceURI) {
		ServiceRequest open = new ServiceRequest(new SafetyManagement(), null);

		open.addValueFilter(new String[] { SafetyManagement.PROP_CONTROLS }, new Door(deviceURI));
		open.addChangeEffect(new String[] { SafetyManagement.PROP_CONTROLS,Door.PROP_DEVICE_STATUS },	new Integer(101));
		return open;
	}

	private static ServiceRequest closeRequest(String deviceURI) {
		ServiceRequest close = new ServiceRequest(new SafetyManagement(), null);

		close.addValueFilter(new String[] { SafetyManagement.PROP_CONTROLS }, new Door(deviceURI));
		close.addChangeEffect(new String[] { SafetyManagement.PROP_CONTROLS,Door.PROP_DEVICE_STATUS }, new Integer(-1));
		return close;
	}

    private static ServiceRequest getDeviceInfoRequest(String deviceURI) {
		ServiceRequest getInfo = new ServiceRequest(new SafetyManagement(), null);
		getInfo.addValueFilter(new String[] { SafetyManagement.PROP_CONTROLS },new Door(deviceURI));
		getInfo.addRequiredOutput(OUTPUT_DEVICE_STATUS, new String[] {
				SafetyManagement.PROP_CONTROLS, Door.PROP_DEVICE_STATUS });
		getInfo.addRequiredOutput(OUTPUT_DEVICE_LOCATION, new String[] {
				SafetyManagement.PROP_CONTROLS, Door.PROP_PHYSICAL_LOCATION });
	
		return getInfo;
    }
	
	public static ServiceRequest getAllDevicesRequest() {
		ServiceRequest getAllDevices = new ServiceRequest(new SafetyManagement(),	null);
		// In this case, we do not intend to change anything but only retrieve some info
		getAllDevices.addRequiredOutput(OUTPUT_LIST_OF_DEVICES,	new String[] { SafetyManagement.PROP_CONTROLS });

		return getAllDevices;
	}

	// Get a list of all available devices in the system
	public static Device[] getControlledDevices() {

		// Make a call for the devices and get the request
		ServiceResponse sr = caller.call(getAllDevicesRequest());
		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				List deviceList = sr.getOutput(OUTPUT_LIST_OF_DEVICES, true);
				if (deviceList == null || deviceList.size() == 0) {
					//LogUtils.logInfo(Activator.logger,
					//		"SafetyConsumer", "getControlledDevices",
					//		new Object[] { "there are no devices" }, null);
					return null;
				}
				// simple create an array out of the oven-array and give it back
				// --> finished
				Device[] devices = (Device[]) deviceList.toArray(new Device[deviceList.size()]);

				return devices;
			} 
			catch (Exception e) {
				e.printStackTrace();
				//LogUtils.logError(Activator.logger, "SafetyConsumer",
				//		"getControlledDevices", new Object[] { "got exception",
				//				e.getMessage() }, e);
				return null;
			}
		} 
		else {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer",
			//		"getControlledDevices",
			//		new Object[] { "callstatus is not succeeded" }, null);
			return null;
		}
	}

    public static boolean getDeviceInfo(String deviceURI) {
		// check if input is valid
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer",
			//		"getDeviceInfo", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(getDeviceInfoRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "SafetyConsumer",
		//		"getDeviceInfo", new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		// check the call status and return true if succeeded
		if (sr.getCallStatus() == CallStatus.succeeded){
		    System.out.println("GOT OUTPUTS " + sr.getOutputs().toString());
		    System.out.println("GOT LOCATION " + sr.getOutput(OUTPUT_DEVICE_LOCATION, true).toString());
		    System.out.println("GOT STATUS " + sr.getOutput(OUTPUT_DEVICE_STATUS, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_LOCATION, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_STATUS, true).toString());
		    
			return true;
		}
		else {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer",
			//		"getDeviceInfo", new Object[] { "The device information request failed" }, null);
			return false;
		}    
    }
    
	// this method turn off the device at deviceURI and give back if the
	// operation was a success
	public static boolean lock(String deviceURI) {
		// check if input is valid
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer",
			//		"lock", new Object[] { "wrong deviceURI" }, null);
			return false;
		}

		// make a call with the appropriate request
		ServiceResponse sr = caller.call(lockRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "SafetyConsumer",
		//		"lock", new Object[] { "Call status: ",
		//				sr.getCallStatus().name() }, null);

		// check the call status and return true if succeeded
		System.out.println("----------------------- End of LOCK operation -----------------------");
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer",
			//		"lock",
			//		new Object[] { "the device couldn't be locked" }, null);
			return false;
		}
	}

	public static boolean unlock(String deviceURI) {

		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer", 
			//		"unlock", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(unlockRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "SafetyConsumer", "turnOn",
		//		new Object[] { "Call device status: ", sr.getCallStatus().name() }, null);
		System.out.println("----------------------- End of UNLOCK operation -----------------------");
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer",
			//		"unlock",
			//		new Object[] { "the device couldn't be unlocked" }, null);
			return false;
		}
	}

	public static boolean open(String deviceURI) {

		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer", 
			//		"open", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(openRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "SafetyConsumer", "open",
		//		new Object[] { "Call device status: ", sr.getCallStatus().name() }, null);
		System.out.println("----------------------- End of OPEN operation -----------------------");
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer",
			//		"open",
			//		new Object[] { "the device couldn't be opened" }, null);
			return false;
		}
	}

	public static boolean close(String deviceURI) {

		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer", 
			//		"close", new Object[] { "wrong deviceURI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(closeRequest(deviceURI));
		//LogUtils.logDebug(Activator.logger, "SafetyConsumer", "close",
		//		new Object[] { "Call device status: ", sr.getCallStatus().name() }, null);
		System.out.println("----------------------- End of CLOSE operation -----------------------");
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			//LogUtils.logWarning(Activator.logger, "SafetyConsumer",
			//		"unlock",
			//		new Object[] { "the device couldn't be closed" }, null);
			return false;
		}
	}

    public void whoIsKnocking(String person) {
    	String tmp = person;
    	SafetyUIClient.setKnockingPerson(person);
    }
	
    public void windowStatus(int status) {
    	SafetyUIClient.setWindowStatus(status);
    }

    public void lightStatus(int status) {
    	SafetyUIClient.setLightStatus(status);
    }

    public void temperatureValue(float temperature) {
    	SafetyUIClient.setTemperatureValue(temperature);
    }

    public void humidityValue(float humidity) {
    	SafetyUIClient.setHumidityValue(humidity);
    }

    public void motionValue(double motion) {
    	SafetyUIClient.setMotionValue(motion);
    }

    public void smokeValue(boolean smoke) {
    	SafetyUIClient.setSmokeValue(smoke);
    }

    public void handleContextEvent(ContextEvent event) {

		System.out.println("############### EVENT RECEIVED ###############");
		System.out.println("Received context event:\n"+"    Subject     = "+
				event.getSubjectURI()+"\n"+"    Subject type= "+
				event.getSubjectTypeURI()+ "\n"+"    Predicate   = "+
				event.getRDFPredicate()+"\n"+"    Object      = "+
				event.getRDFObject()		
		);
		System.out.println("################################################");
		if (((String)event.getSubjectTypeURI()).indexOf("Door")!=-1){
			if (event.getRDFPredicate().indexOf("deviceRfid")!=-1){
				whoIsKnocking((String)event.getRDFObject());
			}
		}
		if (((String)event.getSubjectTypeURI()).indexOf("Window")!=-1)
			windowStatus(((Integer)event.getRDFObject()).intValue());
		if (((String)event.getSubjectTypeURI()).indexOf("Light")!=-1)
			lightStatus(((Integer)event.getRDFObject()).intValue());
		if (((String)event.getSubjectTypeURI()).indexOf("Temperature")!=-1)
			temperatureValue(((Float)event.getRDFObject()).floatValue());
		if (((String)event.getSubjectTypeURI()).indexOf("Humidity")!=-1)
			humidityValue(((Float)event.getRDFObject()).floatValue());
		if (((String)event.getSubjectTypeURI()).indexOf("Motion")!=-1)
			motionValue(((Double)event.getRDFObject()).doubleValue());
		if (((String)event.getSubjectTypeURI()).indexOf("Smoke")!=-1)
			smokeValue(((Boolean)event.getRDFObject()).booleanValue());
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	public static Vector getValues(){
		return values;
	}
}
