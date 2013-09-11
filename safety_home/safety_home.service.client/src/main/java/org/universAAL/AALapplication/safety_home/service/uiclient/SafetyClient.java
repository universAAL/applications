/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.AALapplication.safety_home.service.uiclient;

import org.universAAL.ontology.lighting.LightSource;
import org.universAAL.ontology.lighting.Lighting;
import org.universAAL.ontology.phThing.Device;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.universAAL.AALapplication.safety_home.service.uiclient.dialogs.door.FrontDoorControl;
import org.universAAL.AALapplication.safety_home.service.uiclient.dialogs.environmental.EnvironmentalControl;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.Safety.DoorBell;
import org.universAAL.ontology.Safety.FanHeater;
import org.universAAL.ontology.Safety.SafetyManagement;
import org.universAAL.ontology.Safety.Door;
import org.universAAL.ontology.Safety.Window;
import org.universAAL.ontology.Safety.LightSensor;
import org.universAAL.ontology.Safety.TemperatureSensor;
import org.universAAL.ontology.Safety.HumiditySensor;
import org.universAAL.ontology.Safety.SmokeSensor;
import org.universAAL.ontology.Safety.MotionSensor;

/**
 * @author dimokas
 * 
 */

public class SafetyClient extends ContextSubscriber {

	public static FrontDoorControl fdc;
	public static EnvironmentalControl ec;
	private static ServiceCaller caller;
	private static final String SAFETY_CONSUMER_NAMESPACE = "http://ontology.universaal.org/SafetyClient.owl#";
	private static final String OUTPUT_LIST_OF_DEVICES = SAFETY_CONSUMER_NAMESPACE + "controlledDevices";
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

		ContextEventPattern cep8 = new ContextEventPattern();
		cep7.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, DoorBell.MY_URI));

		return new ContextEventPattern[] { cep1, cep2, cep3, cep4, cep5, cep6, cep7, cep8 };
	}

	SafetyClient(ModuleContext context) {
		super(context, getContextSubscriptionParams());
		caller = new DefaultServiceCaller(context);
		fdc = new FrontDoorControl(context);
		ec = new EnvironmentalControl(context);
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
		getAllDevices.addRequiredOutput(OUTPUT_LIST_OF_DEVICES,	new String[] { SafetyManagement.PROP_CONTROLS });

		return getAllDevices;
	}

	public static Device[] getControlledDevices() {

		ServiceResponse sr = caller.call(getAllDevicesRequest());
		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				List deviceList = sr.getOutput(OUTPUT_LIST_OF_DEVICES, true);
				if (deviceList == null || deviceList.size() == 0) {
					LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"getControlledDevices",
							new Object[] { "there are no devices" }, null);
					return null;
				}
				Device[] devices = (Device[]) deviceList.toArray(new Device[deviceList.size()]);

				return devices;
			} 
			catch (Exception e) {
				e.printStackTrace();
				LogUtils.logError(SharedResources.moduleContext, SafetyClient.class, "getControlledDevices",
						new Object[] { "got exception",	e.getMessage() }, e);
				return null;
			}
		} 
		else {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"getControlledDevices",
					new Object[] { "callstatus is not succeeded" }, null);
			return null;
		}
	}

    public static boolean getDeviceInfo(String deviceURI) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"getDeviceInfo",
					new Object[] { "wrong device URI" }, null);
			return false;
		}
		
		ServiceResponse sr = caller.call(getDeviceInfoRequest(deviceURI));
		LogUtils.logDebug(SharedResources.moduleContext, SafetyClient.class, "getDeviceInfo",
				new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		if (sr.getCallStatus() == CallStatus.succeeded){
		    System.out.println("GOT OUTPUTS " + sr.getOutputs().toString());
		    System.out.println("GOT LOCATION " + sr.getOutput(OUTPUT_DEVICE_LOCATION, true).toString());
		    System.out.println("GOT STATUS " + sr.getOutput(OUTPUT_DEVICE_STATUS, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_LOCATION, true).toString());
		    values.add(sr.getOutput(OUTPUT_DEVICE_STATUS, true).toString());
		    
			return true;
		}
		else {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"getDeviceInfo",
					new Object[] { "the device information request failed" }, null);
			return false;
		}    
    }
    
	public static boolean lock(String deviceURI) {
		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"lock",
					new Object[] { "wrong device URI" }, null);
			
			return false;
		}

		ServiceResponse sr = caller.call(lockRequest(deviceURI));
		LogUtils.logDebug(SharedResources.moduleContext, SafetyClient.class, "lock",
				new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		System.out.println("----------------------- End of LOCK operation -----------------------");
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"lock",
					new Object[] { "the device couldn't be locked" }, null);

			return false;
		}
	}

	public static boolean unlock(String deviceURI) {

		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"unlock",
					new Object[] { "wrong device URI" }, null);

			return false;
		}
		
		ServiceResponse sr = caller.call(unlockRequest(deviceURI));
		LogUtils.logDebug(SharedResources.moduleContext, SafetyClient.class, "unlock",
				new Object[] { "Call status: ", sr.getCallStatus().name() }, null);
		
		System.out.println("----------------------- End of UNLOCK operation -----------------------");
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"unlock",
					new Object[] { "the device couldn't be unlocked" }, null);

			return false;
		}
	}

    public static boolean turnOn(String lampURI) {

		if ((lampURI == null) || !(lampURI instanceof String))
		    return false;
	
		ServiceResponse sr = caller.call(turnOnLampRequest(lampURI));
	
		if (sr.getCallStatus() == CallStatus.succeeded) {
		    System.out.println("SafetyClient: SUCCESS: the lamp was turned on: " + lampURI);
		    return true;
		} else {
		    System.out.println("SafetyClient: the lamp couldn't turned on in turnOn(String lampURI)");
		    return false;
		}
    }

    private static ServiceRequest turnOnLampRequest(String lampURI) {
		ServiceRequest turnOnLamp = new ServiceRequest(new SafetyManagement(), null);
		System.out.println("TURN ON "+lampURI);
		turnOnLamp.addValueFilter(new String[] { SafetyManagement.PROP_LAMP_CONTROLS }, new LightSource(lampURI));
		turnOnLamp.addChangeEffect(new String[] { SafetyManagement.PROP_LAMP_CONTROLS, LightSource.PROP_SOURCE_BRIGHTNESS }, new Integer(102));
		
		return turnOnLamp;
    }
    
    public static boolean turnOff(String lampURI) {
		if ((lampURI == null) || !(lampURI instanceof String))
		    return false;

		ServiceResponse sr = caller.call(turnOffLampRequest(lampURI));
		if (sr.getCallStatus() == CallStatus.succeeded) {
		    System.out.println("SafetyClient: SUCCESS: the lamp was turned off!");
		    return true;
		} else {
		    System.out.println("SafetyClient: the lamp couldn't turned off!");
		    return false;
		}
    }

    private static ServiceRequest turnOffLampRequest(String lampURI) {
		ServiceRequest turnOffLamp = new ServiceRequest(new SafetyManagement(), null);
		System.out.println("TURN OFF "+lampURI);

		turnOffLamp.addValueFilter(new String[] { SafetyManagement.PROP_LAMP_CONTROLS }, new LightSource(lampURI));
		turnOffLamp.addChangeEffect(new String[] { SafetyManagement.PROP_LAMP_CONTROLS, LightSource.PROP_SOURCE_BRIGHTNESS }, new Integer(-2));
		
		return turnOffLamp;
    }

    public static boolean turnOnHeating(String heatingURI) {

		if ((heatingURI == null) || !(heatingURI instanceof String))
		    return false;
	
		ServiceResponse sr = caller.call(turnOnHeatingRequest(heatingURI));
		if (sr.getCallStatus() == CallStatus.succeeded) {
		    System.out.println("SafetyClient: SUCCESS: the heating was turned on!");
		    return true;
		} else {
		    System.out.println("SafetyClient: the heating couldn't turned on!");
		    return false;
		}
    }

    private static ServiceRequest turnOnHeatingRequest(String heatingURI) {
		ServiceRequest turnOnHeating = new ServiceRequest(new SafetyManagement(), null);
		System.out.println("TURN ON "+heatingURI);
		turnOnHeating.addValueFilter(new String[] { SafetyManagement.PROP_HEATING_CONTROLS }, new FanHeater(heatingURI));
		turnOnHeating.addChangeEffect(new String[] { SafetyManagement.PROP_HEATING_CONTROLS, FanHeater.PROP_IS_ENABLED }, new Boolean(true));
		
		return turnOnHeating;
    }

    public static boolean turnOffHeating(String heatingURI) {
		if ((heatingURI == null) || !(heatingURI instanceof String))
		    return false;

		ServiceResponse sr = caller.call(turnOffHeatingRequest(heatingURI));
		if (sr.getCallStatus() == CallStatus.succeeded) {
		    System.out.println("SafetyClient: SUCCESS: the heating was turned off!");
		    return true;
		} else {
		    System.out.println("SafetyClient: the heating couldn't turned off!");
		    return false;
		}
    }

    private static ServiceRequest turnOffHeatingRequest(String heatingURI) {
		ServiceRequest turnOffHeating = new ServiceRequest(new SafetyManagement(), null);
		System.out.println("TURN OFF "+heatingURI);
		turnOffHeating.addValueFilter(new String[] { SafetyManagement.PROP_HEATING_CONTROLS }, new FanHeater(heatingURI));
		turnOffHeating.addChangeEffect(new String[] { SafetyManagement.PROP_HEATING_CONTROLS, FanHeater.PROP_IS_ENABLED }, new Boolean(false));

		return turnOffHeating;
    }

    
    public static boolean open(String deviceURI) {

		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"open",
					new Object[] { "wrong device URI" }, null);
			
			return false;
		}
		
		ServiceResponse sr = caller.call(openRequest(deviceURI));
		LogUtils.logDebug(SharedResources.moduleContext, SafetyClient.class, "open",
				new Object[] { "Call status: ", sr.getCallStatus().name() }, null);
		
		System.out.println("----------------------- End of OPEN operation -----------------------");
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"open",
					new Object[] { "the device couldn't be opened" }, null);

			return false;
		}
	}

	public static boolean close(String deviceURI) {

		if ((deviceURI == null) || !(deviceURI instanceof String)) {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"close",
					new Object[] { "wrong device URI" }, null);

			return false;
		}
		
		ServiceResponse sr = caller.call(closeRequest(deviceURI));
		LogUtils.logDebug(SharedResources.moduleContext, SafetyClient.class, "close",
				new Object[] { "Call status: ", sr.getCallStatus().name() }, null);

		System.out.println("----------------------- End of CLOSE operation -----------------------");
		if (sr.getCallStatus() == CallStatus.succeeded)
			return true;
		else {
			LogUtils.logWarn(SharedResources.moduleContext,	SafetyClient.class,	"close",
					new Object[] { "the device couldn't be closed" }, null);

			return false;
		}
	}

    public void whoIsKnocking(String person) {
    	//String tmp = person;
    	//SafetyUIClient.setKnockingPerson(person);
    	fdc.setKnockingPerson(person);
    	fdc.startVisitorDialog(1);
    }
	
    public void windowStatus(int status) {
    	//SafetyUIClient.setWindowStatus(status);
    	ec.startWindowDialog(status);
    }

    public void lightStatus(int status) {
    	//SafetyUIClient.setLightStatus(status);
    	ec.startLightsDialog(status);
    }

    public void temperatureValue(float temperature) {
    	//SafetyUIClient.setTemperatureValue(temperature);
    	ec.setTemperature(temperature);
    }

    public void humidityValue(float humidity) {
    	//SafetyUIClient.setHumidityValue(humidity);
    	ec.setHumidity(humidity);
    }

    public void motionValue(double motion) {
    	//SafetyUIClient.setMotionValue(motion);
    	ec.setMotion(motion);
    	ec.startMotionDialog(ec.getMotionWarnings());
    }

    public void smokeValue(boolean smoke) {
    	//SafetyUIClient.setSmokeValue(smoke);
    	ec.startSmokeDialog(smoke);
    }

    public void doorBell(boolean isEnabled) {
    	fdc.startDoorBellDialog(isEnabled);
    }

    public void handleContextEvent(ContextEvent event) {

/*		System.out.println("############### EVENT RECEIVED ###############");
		System.out.println("Received context event:\n"+"    Subject     = "+
				event.getSubjectURI()+"\n"+"    Subject type= "+
				event.getSubjectTypeURI()+ "\n"+"    Predicate   = "+
				event.getRDFPredicate()+"\n"+"    Object      = "+
				event.getRDFObject()		
		);
		System.out.println("################################################");*/
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
		if (((String)event.getSubjectTypeURI()).indexOf("DoorBell")!=-1)
			doorBell(((Boolean)event.getRDFObject()).booleanValue());
	}

	public void communicationChannelBroken() {
	}

	public static Vector getValues(){
		return values;
	}
}
