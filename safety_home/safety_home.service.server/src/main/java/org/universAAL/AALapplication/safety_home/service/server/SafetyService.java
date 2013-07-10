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

package org.universAAL.AALapplication.safety_home.service.server;

import java.util.Hashtable;

import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.Safety.SafetyManagement;
import org.universAAL.ontology.Safety.Door;
import org.universAAL.ontology.Safety.Window;
import org.universAAL.ontology.Safety.LightSensor;
import org.universAAL.ontology.Safety.TemperatureSensor;
import org.universAAL.ontology.Safety.HumiditySensor;
import org.universAAL.ontology.Safety.MotionSensor;
import org.universAAL.ontology.location.Location;

/**
 * @author dimokas
 *
 */

public class SafetyService extends SafetyManagement {
	
	// All the static Strings are used to unique identify special functions and objects
	public static final String SAFETY_SERVER_NAMESPACE = "http://ontology.universaal.org/SafetyServer.owl#";
	public static final String MY_URI = SAFETY_SERVER_NAMESPACE + "SafetyService";
	
	static final String SERVICE_GET_CONTROLLED_DEVICES = SAFETY_SERVER_NAMESPACE + "getControlledDevices";
	static final String SERVICE_GET_DEVICE_INFO = SAFETY_SERVER_NAMESPACE + "getDeviceInfo";
	static final String SERVICE_LOCK = SAFETY_SERVER_NAMESPACE + "lock";
	static final String SERVICE_UNLOCK = SAFETY_SERVER_NAMESPACE + "unlock";
	static final String SERVICE_OPEN = SAFETY_SERVER_NAMESPACE + "open";
	static final String SERVICE_CLOSE = SAFETY_SERVER_NAMESPACE + "close";
	static final String INPUT_DEVICE_URI = SAFETY_SERVER_NAMESPACE + "DeviceURI";
	static final String OUTPUT_CONTROLLED_DEVICES = SAFETY_SERVER_NAMESPACE + "controlledDevices";	
	static final String OUTPUT_DEVICE_STATUS = SAFETY_SERVER_NAMESPACE + "status";
	static final String OUTPUT_DEVICE_LOCATION = SAFETY_SERVER_NAMESPACE + "location";
	
	static final ServiceProfile[] profiles = new ServiceProfile[6];
	private static Hashtable serverSafetyRestrictions = new Hashtable();
	
	static {
		OntologyManagement.getInstance().register(
				new SimpleOntology(MY_URI, SafetyManagement.MY_URI,
					new ResourceFactoryImpl() {
					    @Override
					    public Resource createInstance(String classURI,
						    String instanceURI, int factoryIndex) {
						return new SafetyService(instanceURI);
					    }
					}));
		
		
		String[] ppControls = new String[] {SafetyManagement.PROP_CONTROLS};
		String[] ppStatus = new String[] {SafetyManagement.PROP_CONTROLS, Door.PROP_DEVICE_STATUS};
		
		addRestriction((MergedRestriction)
				SafetyManagement.getClassRestrictionsOnProperty(SafetyManagement.MY_URI, SafetyManagement.PROP_CONTROLS).copy(),
				ppControls, serverSafetyRestrictions);
		addRestriction(
				MergedRestriction.getAllValuesRestrictionWithCardinality(Door.PROP_DEVICE_STATUS,
						new Enumeration(new Integer[] {new Integer(0), new Integer(100)}), 1, 1),
				ppStatus,serverSafetyRestrictions);

		
		SafetyService getControlledDevices = new SafetyService(SERVICE_GET_CONTROLLED_DEVICES);
		getControlledDevices.addOutput(OUTPUT_CONTROLLED_DEVICES, Door.MY_URI, 0, 0, ppControls);
		profiles[0] = getControlledDevices.myProfile;
		
		SafetyService getDeviceInfo = new SafetyService(SERVICE_GET_DEVICE_INFO);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, Door.MY_URI, 1, 1, ppControls);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, Window.MY_URI, 1, 1, ppControls);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, LightSensor.MY_URI, 1, 1, ppControls);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, TemperatureSensor.MY_URI, 1, 1, ppControls);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, HumiditySensor.MY_URI, 1, 1, ppControls);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, MotionSensor.MY_URI, 1, 1, ppControls);
		getDeviceInfo.addOutput(OUTPUT_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1,
				ppStatus);
		getDeviceInfo.addOutput(OUTPUT_DEVICE_LOCATION,	Location.MY_URI, 1, 1,
				new String[] {SafetyManagement.PROP_CONTROLS, Door.PROP_PHYSICAL_LOCATION});
		profiles[1] = getDeviceInfo.myProfile;
		
		SafetyService lock = new SafetyService(SERVICE_LOCK);
		lock.addFilteringInput(INPUT_DEVICE_URI, Door.MY_URI, 1, 1, ppControls);
		lock.myProfile.addChangeEffect(ppStatus, new Integer(0));
		profiles[2] = lock.myProfile;
		
		SafetyService unlock = new SafetyService(SERVICE_UNLOCK);
		unlock.addFilteringInput(INPUT_DEVICE_URI, Door.MY_URI, 1, 1, ppControls);
		unlock.myProfile.addChangeEffect(ppStatus, new Integer(100));
		profiles[3] = unlock.myProfile;

		SafetyService open = new SafetyService(SERVICE_OPEN);
		open.addFilteringInput(INPUT_DEVICE_URI, Door.MY_URI, 1, 1, ppControls);
		open.myProfile.addChangeEffect(ppStatus, new Integer(101));
		profiles[4] = open.myProfile;

		SafetyService close = new SafetyService(SERVICE_CLOSE);
		close.addFilteringInput(INPUT_DEVICE_URI, Door.MY_URI, 1, 1, ppControls);
		close.myProfile.addChangeEffect(ppStatus, new Integer(-1));
		profiles[5] = close.myProfile;
	}


	private SafetyService(String uri) {
		super(uri);
	}
	
}