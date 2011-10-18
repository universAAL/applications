package principal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.ontology.drools.Rule;

public class Activator implements BundleActivator{
	public static BundleContext context=null;
	public static SCallee scallee=null;
	public static SCaller scaller=null;

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		scallee=new SCallee(context);
		scaller=new SCaller(context);
		
		Rule my_rule = new Rule();
		
		my_rule.setBODY("This is the body of the rule");
		
		principal.Methods.addRule(my_rule);
		
		
		//Methods my_method= new Methods(String uri);
		
		
		
		
		
	}

	public void stop(BundleContext arg0) throws Exception {
		scallee.close();
		scaller.close();
	}

}
