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
		
	
	
	
		caller = new DefaultServiceCaller(context);
		SimpleRequest add = new SimpleRequest(new DroolsService());
		add.putArgument(new String[] { DroolsService.RULE }, new SimpleAdd(new Rule("http://ontology.universAAL.org/MyServer.owl#myRule")));
		System.out.println("I'm calling");
		
		
		
		caller.call(add);
	}

	
	

	public void stop(BundleContext arg0) throws Exception {
		caller.close();
	}

}
