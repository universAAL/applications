package principal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.ServiceRequest;


public class Activator implements BundleActivator{
	public static BundleContext context=null;
	public static SCaller caller=null;

	public void start(BundleContext context) throws Exception {
		
		
		
		Activator.context=context;
		caller=new SCaller(context, null);
		
		SCaller mySCaller = new SCaller(context, null);
		
		// I create a object service request

		ServiceRequest req = new ServiceRequest(ServiceReasoner.MY_URI );
		
		
		
			

		// I configure the request for the call.
		req.addTypeFilter(new String[] { ServiceReasoner.PROPERTY_CONTROLS }, ServiceReasoner.MY_URI);

		// output_temp id of the uri.

		req.addRequiredOutput(SCalleeProvidedService.SERVER_NAMESPACE
				+ "output_temp",
				new String[] { ServiceReasoner.PROPERTY_CONTROLS,
				ServiceReasoner.MY_URI });
		
		mySCaller.call(req);
		
		
		
	}

	public void stop(BundleContext arg0) throws Exception {
		caller.close();
	}

}
