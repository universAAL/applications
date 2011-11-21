package principal;
 
import mainclasses.SCalleeProvidedService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.ontology.drools.Rule;
import org.universAAL.ontology.drools.service.DroolsService;
import org.universAAL.samples.service.utils.SimpleAdd;
import org.universAAL.samples.service.utils.SimpleRequest;

public class Activator implements BundleActivator{
	
	private ServiceCaller caller; // to call a service

	public void start(BundleContext context) throws Exception {
		
	
		
//		String my_rule ="Rule";
//		
//		// I create a default caller and i pass him the actual context
//		caller = new DefaultServiceCaller(context);
//		
//		System.out.println("caller created.........");
//		
//		// I create a object service request
//
//		ServiceRequest req = new ServiceRequest(new DroolsService(),null);
//		
//		
//		System.out.println("request created.........");
//			
//        SimpleRequest set = new SimpleRequest(new DroolsService());
//		
//		
//		
//		
//		set.putArgument(new String[] { DroolsService.RULE }, new SimpleAdd(my_rule));
//		
//		
//		req.addRequiredOutput(SCalleeProvidedService.SERVER_NAMESPACE
//				+ "output_temp",
//				new String[] { DroolsService.RULE});
//		
//	
//		System.out.println("IM CALLING REASONER-ADDRULE");
//		
//		// I call the service
//
//		caller.call(req);
		
	
		caller = new DefaultServiceCaller(context);
		SimpleRequest add = new SimpleRequest(new DroolsService());
		add.putArgument(new String[] { DroolsService.RULE }, new SimpleAdd(new Rule("http://ontology.universAAL.org/MyServer.owl#myRule")));
		caller.call(add);
	}

	
	

	public void stop(BundleContext arg0) throws Exception {
		caller.close();
	}

}
