package org.universAAL.AALapplication.testclient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.lighting.LightSource;
import org.universAAL.ontology.lighting.Lighting;

public class Activator implements BundleActivator {
    // These are reference constants which must also follow the namespace+uri
    // format
    // we use them to keep track of the outputs we want to get
    private static final String OUTPUT_TO_PUT_BRIGHT = "http://ontology.universAAL.org/MyLightingClient.owl#ouroutput1";
    private static final String OUTPUT_TO_PUT_TYPE = "http://ontology.universAAL.org/MyLightingClient.owl#ouroutput2";
    public static BundleContext context = null;

    public void start(BundleContext context) throws Exception {
	Activator.context = context;
	// A DefaultServiceCaller has everything we usually want: .call
	ServiceCaller sc = new DefaultServiceCaller(context);
	// We get a ServiceResponse from a .call(ServiceRequest)
	ServiceResponse sr1 = sc
		.call(getInfoServRequest("http://ontology.universAAL.org/MyLightingServer.owl#lamp1"));
	// If "succeeded"...
	if (sr1.getCallStatus() == CallStatus.succeeded) {
	    // We list all the outputs we got
	    System.out.println("GOT OUTPUTS " + sr1.getOutputs().toString());
	    // We get each single output and print it (the toString method
	    // return the URI, otherwise we should cast...) We use the
	    // constants we declared to reference the received outputs
	    System.out.println("GOT BRIGHT "
		    + sr1.getOutput(OUTPUT_TO_PUT_BRIGHT, true).toString());
	    System.out.println("GOT TYPE "
		    + sr1.getOutput(OUTPUT_TO_PUT_TYPE, true).toString());
	}
	// We get a ServiceResponse from a .call(ServiceRequest)
	ServiceResponse sr2 = sc.call(getSetServRequest(
		"http://ontology.universAAL.org/MyLightingServer.owl#lamp1",
		new Integer(50)));
	if (sr2.getCallStatus() == CallStatus.succeeded) {
	    // There would be no outputs to this call
	    System.out.println("SET SUCCEEDED");
	}
    }

    // Prepare a ServiceRequest that matches the profile to set brightness
    private ServiceRequest getSetServRequest(String uri, Integer brght) {
	// Instantiate a ServiceRequest upon the Service Ontology we used in
	// ProcidedService
	ServiceRequest set = new ServiceRequest(new Lighting(), null);
	// We add an input. It´s a filtering input: The light we want to set the
	// brightness for
	set.addValueFilter(new String[] { Lighting.PROP_CONTROLS },
		new LightSource(uri));
	// We add another input, but not filtering, one that changes the
	// bightness to the value given.
	set.addChangeEffect(new String[] { Lighting.PROP_CONTROLS,
		LightSource.PROP_SOURCE_BRIGHTNESS }, brght);

	return set;
    }

    // Prepare a ServiceRequest that matches the profile to get info
    private ServiceRequest getInfoServRequest(String uri) {
	// Instantiate a ServiceRequest upon the Service Ontology we used in
	// ProcidedService
	ServiceRequest getInfo = new ServiceRequest(new Lighting(), null);
	// We add an input. It´s a filtering input: The light we want to know
	// the info from
	getInfo.addValueFilter(new String[] { Lighting.PROP_CONTROLS },
		new LightSource(uri));
	// We request the output that states the brightness. We will reference
	// it as OUTPUT_TO_PUT_BRIGHT
	getInfo.addRequiredOutput(OUTPUT_TO_PUT_BRIGHT, new String[] {
		Lighting.PROP_CONTROLS, LightSource.PROP_SOURCE_BRIGHTNESS });
	// We request the output that states the type. We will reference it as
	// OUTPUT_TO_PUT_TYPE
	getInfo.addRequiredOutput(OUTPUT_TO_PUT_TYPE, new String[] {
		Lighting.PROP_CONTROLS, LightSource.PROP_HAS_TYPE });

	return getInfo;
    }

    public void stop(BundleContext arg0) throws Exception {
    }

}
