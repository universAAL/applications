package org.universAAL.AALapplication.tstserv;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.lighting.ElectricLight;

public class SCallee extends ServiceCallee {
    // This is a reference constant ServiceResponse for when we have to
    // communicate failures
    private static final ServiceResponse errorResp = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);

    protected SCallee(BundleContext context, ServiceProfile[] realizedServices) {
	super(context, realizedServices);
	// TODO Auto-generated constructor stub
    }

    protected SCallee(BundleContext context) {
	super(context, SCalleeProvidedService.profiles);
	// TODO Auto-generated constructor stub
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    // When Service bus thinks we can handle a request we are called this method
    public ServiceResponse handleCall(ServiceCall call) {
	if (call == null)
	    return null;

	// The "operation" processURI is one of the references we used in the
	// profiles of ProvidedService
	String operation = call.getProcessURI();
	if (operation == null)
	    return null;

	// If the profile addressed is SERVICE_GET_LAMP_INFO...
	if (operation.startsWith(SCalleeProvidedService.SERVICE_GET_LAMP_INFO)) {
	    // We get the input from the call by using the reference we gave it
	    // in ProvidedService
	    Object inputLamp = call
		    .getInputValue(SCalleeProvidedService.INPUT_LAMP_URI);
	    if (inputLamp == null)
		return errorResp;
	    // inputLamp now has the LightSource value. We can cast it to
	    // (LightSource), but we use .toString to directly get its URI
	    return getLampInfo(inputLamp.toString());
	}

	// If the profile addressed is SERVICE_SET_BRIGHT
	if (operation.startsWith(SCalleeProvidedService.SERVICE_SET_BRIGHT)) {
	    // We get the inputs from the call by using the reference we gave it
	    // in ProvidedService
	    Object inputLamp = call
		    .getInputValue(SCalleeProvidedService.INPUT_LAMP_URI);
	    Object inputBright = call
		    .getInputValue(SCalleeProvidedService.INPUT_LAMP_BRIGHT);
	    if (inputLamp == null || inputBright == null
		    || !(inputBright instanceof Integer))
		return errorResp;
	    // inputLamp now has the LightSource value. We can cast it to
	    // (LightSource), but we use .toString to directly get its URI.
	    // inputBright has the request brightness Integer. We cast it to
	    // (Integer)
	    return setBright(inputLamp.toString(), (Integer) inputBright);
	}

	return errorResp;
    }

    private ServiceResponse setBright(String lampURI, Integer inputBright) {
	try {
	    ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	    // There is no output, we would just set the lamp "lampURI" at the
	    // right level
	    System.out.println("SET LAMP " + lampURI + " AT BRIGHTNESS "
		    + inputBright);
	    return sr;
	} catch (Exception e) {
	    return errorResp;
	}
    }

    private ServiceResponse getLampInfo(String lampURI) {
	try {
	    // lampURI is the light they are asking for. We dont care now.
	    ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	    // Bright Always on :)
	    sr.addOutput(new ProcessOutput(
		    SCalleeProvidedService.OUTPUT_LAMP_BRIGHTNESS, new Integer(
			    100)));
	    // It´s fluorescent
	    sr.addOutput(new ProcessOutput(
		    SCalleeProvidedService.OUTPUT_LAMP_TYPE,
		    ElectricLight.fluorescentLamp));
	    return sr;
	} catch (Exception e) {
	    return errorResp;
	}
    }

}
