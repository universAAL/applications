package org.universAAL.AALapplication.safety_home.service.server;

import java.util.Hashtable;

import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.safetyDevices.Safety;
import org.universAAL.ontology.safetyDevices.Door;
import org.universAAL.ontology.location.Location;

/**
 * @author dimokas
 *
 */

public class SafetyService extends Safety {
	
	// All the static Strings are used to unique identify special functions and objects
	public static final String SAFETY_SERVER_NAMESPACE = "http://ontology.universaal.org/SafetyServer.owl#";
	public static final String MY_URI = SAFETY_SERVER_NAMESPACE + "SafetyService";
	
	static final String SERVICE_GET_CONTROLLED_DEVICES = SAFETY_SERVER_NAMESPACE + "getControlledDevices";
	static final String SERVICE_GET_DEVICE_INFO = SAFETY_SERVER_NAMESPACE + "getDeviceInfo";
	static final String SERVICE_LOCK = SAFETY_SERVER_NAMESPACE + "lock";
	static final String SERVICE_UNLOCK = SAFETY_SERVER_NAMESPACE + "unlock";
	static final String INPUT_DEVICE_URI = SAFETY_SERVER_NAMESPACE + "DeviceURI";
	static final String OUTPUT_CONTROLLED_DEVICES = SAFETY_SERVER_NAMESPACE + "controlledDevices";	
	static final String OUTPUT_DEVICE_STATUS = SAFETY_SERVER_NAMESPACE + "status";
	static final String OUTPUT_DEVICE_LOCATION = SAFETY_SERVER_NAMESPACE + "location";
	
	static final ServiceProfile[] profiles = new ServiceProfile[4];
	private static Hashtable serverSafetyRestrictions = new Hashtable();
	
	static {
		register(SafetyService.class);
		String[] ppControls = new String[] {Safety.PROP_CONTROLS};
		String[] ppStatus = new String[] {Safety.PROP_CONTROLS, Door.PROP_DEVICE_STATUS};
		
		addRestriction((Restriction)
				Safety.getClassRestrictionsOnProperty(Safety.PROP_CONTROLS).copy(),
				ppControls, serverSafetyRestrictions);
		addRestriction(
				Restriction.getAllValuesRestrictionWithCardinality(Door.PROP_DEVICE_STATUS,
						new Enumeration(new Integer[] {new Integer(0), new Integer(100)}), 1, 1),
				ppStatus,serverSafetyRestrictions);

		
		SafetyService getControlledDevices = new SafetyService(SERVICE_GET_CONTROLLED_DEVICES);
		getControlledDevices.addOutput(OUTPUT_CONTROLLED_DEVICES, Door.MY_URI, 0, 0, ppControls);
		profiles[0] = getControlledDevices.myProfile;
		
		SafetyService getDeviceInfo = new SafetyService(SERVICE_GET_DEVICE_INFO);
		getDeviceInfo.addFilteringInput(INPUT_DEVICE_URI, Door.MY_URI, 1, 1, ppControls);
		getDeviceInfo.addOutput(OUTPUT_DEVICE_STATUS, TypeMapper.getDatatypeURI(Integer.class), 1, 1,
				ppStatus);
		getDeviceInfo.addOutput(OUTPUT_DEVICE_LOCATION,	Location.MY_URI, 1, 1,
				new String[] {Safety.PROP_CONTROLS, Door.PROP_DEVICE_LOCATION});
		profiles[1] = getDeviceInfo.myProfile;
		
		SafetyService lock = new SafetyService(SERVICE_LOCK);
		lock.addFilteringInput(INPUT_DEVICE_URI, Door.MY_URI, 1, 1, ppControls);
		lock.myProfile.addChangeEffect(ppStatus, new Integer(0));
		profiles[2] = lock.myProfile;
		
		SafetyService unlock = new SafetyService(SERVICE_UNLOCK);
		unlock.addFilteringInput(INPUT_DEVICE_URI, Door.MY_URI, 1, 1, ppControls);
		unlock.myProfile.addChangeEffect(ppStatus, new Integer(100));
		profiles[3] = unlock.myProfile;
}


	private SafetyService(String uri) {
		super(uri);
	}
	
}
