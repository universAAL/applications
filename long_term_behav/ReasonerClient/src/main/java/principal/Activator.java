package principal;
 
import mainclasses.SCalleeProvidedService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.ontology.drools.service.DroolsService;

public class Activator implements BundleActivator{
	
	private ServiceCaller caller; // to call a service

	public void start(BundleContext context) throws Exception {
		
	
		
		
		// I create a default caller and i pass him the actual context
		caller = new DefaultServiceCaller(context);
		
		System.out.println("caller created.........");
		
		// I create a object service request

		ServiceRequest req = new ServiceRequest(new DroolsService(),null);
		
		
		System.out.println("request created.........");
			

		// I configure the request for the call.
		//req.addTypeFilter(new String[] { ServiceReasoner.PROPERTY_CONTROLS }, Rule.MY_URI);

		// output_temp id of the uri.

		req.addRequiredOutput(SCalleeProvidedService.SERVER_NAMESPACE
				+ "output_temp",
				new String[] { DroolsService.RULE});
		
	
		System.out.println("IM CALLING REASONER-ADDRULE");
		
		// I call the service

		caller.call(req);
		
	
		
		
		
	}

	
	

	public void stop(BundleContext arg0) throws Exception {
		caller.close();
	}

}
