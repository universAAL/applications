package principal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{
	public static BundleContext context=null;
	public static SCaller caller=null;

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		caller=new SCaller(context);
		
		SCaller mySCaller = new SCaller(context);
		
		
		
		
		
	}

	public void stop(BundleContext arg0) throws Exception {
		caller.close();
	}

}
