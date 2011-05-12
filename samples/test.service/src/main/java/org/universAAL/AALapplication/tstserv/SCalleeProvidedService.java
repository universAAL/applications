package org.universAAL.AALapplication.tstserv;

import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.lighting.ElectricLight;
import org.universAAL.ontology.lighting.LightSource;
import org.universAAL.ontology.lighting.Lighting;

import java.util.Hashtable;

public class SCalleeProvidedService extends Lighting {

    // Needed mandatory constructor
    public SCalleeProvidedService(String uri) {
	super(uri);
    }

    // Namespaces should always look like this
    public static final String MY_LIGHTING_SERVER_NAMESPACE = "http://ontology.universAAL.org/MyOwnLightingServer.owl#";
    // You must include MY URI to identify this server. Must be unique!
    public static final String MY_URI = MY_LIGHTING_SERVER_NAMESPACE
	    + "myOwnServer";
    // Constants used for referencing profiles, inputs and outputs
    public static final String SERVICE_GET_LAMP_INFO = MY_LIGHTING_SERVER_NAMESPACE
	    + "servGetLampInfo";
    public static final String SERVICE_SET_BRIGHT = MY_LIGHTING_SERVER_NAMESPACE
	    + "servSetBright";
    public static final String INPUT_LAMP_URI = MY_LIGHTING_SERVER_NAMESPACE
	    + "inLampURI";
    public static final String INPUT_LAMP_BRIGHT = MY_LIGHTING_SERVER_NAMESPACE
	    + "inLampBright";
    public static final String OUTPUT_LAMP_BRIGHTNESS = MY_LIGHTING_SERVER_NAMESPACE
	    + "outLampBright";
    public static final String OUTPUT_LAMP_TYPE = MY_LIGHTING_SERVER_NAMESPACE
	    + "outLampType";

    // Instantiate the profiles with the exact amount of profiles you have
    // created
    public static ServiceProfile[] profiles = new ServiceProfile[2];
    private static Hashtable serverLevelRestrictions = new Hashtable();

    static {
	register(SCalleeProvidedService.class);
	// 1. Restrictions of our instance
	// We CONTROL LightSources.
	addRestriction((Restriction) Lighting.getClassRestrictionsOnProperty(
		Lighting.PROP_CONTROLS).copy(),
		new String[] { Lighting.PROP_CONTROLS },
		serverLevelRestrictions);

	// The LightSources we control are of TYPE ElectricLight
	addRestriction(Restriction.getAllValuesRestriction(
		LightSource.PROP_HAS_TYPE, ElectricLight.MY_URI), new String[] {
		Lighting.PROP_CONTROLS, LightSource.PROP_HAS_TYPE },
		serverLevelRestrictions);

	// The LightSources we control can have BRIGHTNESS from 1 to 100
	addRestriction(Restriction.getAllValuesRestrictionWithCardinality(
		LightSource.PROP_SOURCE_BRIGHTNESS, new Enumeration(
			new Integer[] { new Integer(0), new Integer(100) }), 1,
		1), new String[] { Lighting.PROP_CONTROLS,
		LightSource.PROP_SOURCE_BRIGHTNESS }, serverLevelRestrictions);

	// 2. Create our first profile - Get information (type and place) of
	// lamp
	SCalleeProvidedService getLampInfo = new SCalleeProvidedService(
		SERVICE_GET_LAMP_INFO);
	// Input - which lamp
	getLampInfo.addFilteringInput(INPUT_LAMP_URI, LightSource.MY_URI, 1, 1,
		new String[] { Lighting.PROP_CONTROLS });
	// Output - Brightness
	getLampInfo.addOutput(OUTPUT_LAMP_BRIGHTNESS, TypeMapper
		.getDatatypeURI(Integer.class), 1, 1, new String[] {
		Lighting.PROP_CONTROLS, LightSource.PROP_SOURCE_BRIGHTNESS });
	// Output - Type
	getLampInfo.addOutput(OUTPUT_LAMP_TYPE, ElectricLight.MY_URI, 1, 1,
		new String[] { Lighting.PROP_CONTROLS,
			LightSource.PROP_HAS_TYPE });

	// 3. Create our second profile - Set brightness of lamp
	SCalleeProvidedService setBright = new SCalleeProvidedService(
		SERVICE_SET_BRIGHT);
	// Input - which lamp
	setBright.addFilteringInput(INPUT_LAMP_URI, LightSource.MY_URI, 1, 1,
		new String[] { Lighting.PROP_CONTROLS });
	// Input+Effect - Brightness to set
	setBright.addInputWithChangeEffect(INPUT_LAMP_BRIGHT, TypeMapper
		.getDatatypeURI(Integer.class), 1, 1, new String[] {
		Lighting.PROP_CONTROLS, LightSource.PROP_SOURCE_BRIGHTNESS });

	// 4. Register our services. Check size again!!!
	profiles[0] = getLampInfo.myProfile;
	profiles[1] = setBright.myProfile;
    }

}
