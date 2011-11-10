package org.universAAL.AALapplication.safety_home.service.client;

import org.universAAL.ontology.phThing.Device;

import java.util.List;
import java.util.Vector;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.util.LogUtils;
import org.universAAL.ontology.safetyDevices.Safety;
import org.universAAL.ontology.safetyDevices.Door;


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
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(Restriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, Door.MY_URI));
		
		return new ContextEventPattern[] { cep };
	}

	SafetyClient(BundleContext context) {
		// the constructor register us to the bus
		super(context, getContextSubscriptionParams());

		caller = new DefaultServiceCaller(context);
		new SafetyUIClient(getControlledDevices());
	}

	/*****************************************************************/
	/* 	Services Requests											 */
	/*****************************************************************/

	private static ServiceRequest lockRequest(String deviceURI) {
		ServiceRequest turnOff = new ServiceRequest(new Safety(), null);

		turnOff.addValueFilter(new String[] { Safety.PROP_CONTROLS }, new Door(deviceURI));
		turnOff.addChangeEffect(new String[] { Safety.PROP_CONTROLS,
				Door.PROP_DEVICE_STATUS }, new Integer(0));
		return turnOff;
	}

	private static ServiceRequest unlockRequest(String deviceURI) {
		ServiceRequest turnOn = new ServiceRequest(new Safety(), null);

		turnOn.addValueFilter(new String[] { Safety.PROP_CONTROLS }, new Door(deviceURI));
		turnOn.addChangeEffect(new String[] { Safety.PROP_CONTROLS,Door.PROP_DEVICE_STATUS },
				new Integer(100));
		return turnOn;
	}
	
	private static ServiceRequest openRequest(String deviceURI) {
		ServiceRequest open = new ServiceRequest(new Safety(), null);

		open.addValueFilter(new String[] { Safety.PROP_CONTROLS }, new Door(deviceURI));
		open.addChangeEffect(new String[] { Safety.PROP_CONTROLS,Door.PROP_DEVICE_STATUS },	new Integer(101));
		return open;
	}

	private static ServiceRequest closeRequest(String deviceURI) {
		ServiceRequest close = new ServiceRequest(new Safety(), null);

		close.addValueFilter(new String[] { Safety.PROP_CONTROLS }, new Door(deviceURI));
		close.addChangeEffect(new String[] { Safety.PROP_CONTROLS,Door.PROP_DEVICE_STATUS }, new Integer(-1));
		return close;
	}

    private static ServiceRequest getDeviceInfoRequest(String deviceURI) {
    	// Instantiate a ServiceRequest upon the Service Ontology we used
		ServiceRequest getInfo = new ServiceRequest(new Safety(), null);
		// We add an input. It´s a filtering input: The device we want to know the info from
		getInfo.addValueFilter(new String[] { Safety.PROP_CONTROLS },new Door(deviceURI));
		// We request the output that states the type. We will reference it as
		// OUTPUT_TO_PUT_TYPE
		getInfo.addRequiredOutput(OUTPUT_DEVICE_STATUS, new String[] {
				Safety.PROP_CONTROLS, Door.PROP_DEVICE_STATUS });

		getInfo.addRequiredOutput(OUTPUT_DEVICE_LOCATION, new String[] {
				Safety.PROP_CONTROLS, Door.PROP_DEVICE_LOCATION });
	
		return getInfo;
    }
	
	public static ServiceRequest getAllDevicesRequest() {
		ServiceRequest getAllDevices = new ServiceRequest(new Safety(),	null);
		// In this case, we do not intend to change anything but only retrieve some info
		getAllDevices.addRequiredOutput(OUTPUT_LIST_OF_DEVICES,	new String[] { Safety.PROP_CONTROLS });

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

	public void handleContextEvent(ContextEvent event) {
/*
		LogUtils.logInfo(Activator.logger, "SafetyConsumer",
				"handleContextEvent", new Object[] {
						"Received context event:\n", "    Subject     = ",
						event.getSubjectURI(), "\n", "    Subject type= ",
						event.getSubjectTypeURI(), "\n", "    Predicate   = ",
						event.getRDFPredicate(), "\n", "    Object      = ",
						event.getRDFObject() }, null);
*/
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	public static Vector getValues(){
		return values;
	}
}
