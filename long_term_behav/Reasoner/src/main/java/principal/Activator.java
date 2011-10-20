package principal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{
	public static BundleContext context=null;
	public static SCallee scallee=null;
	public static SCaller scaller=null;

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		scallee=new SCallee(context);
		scaller=new SCaller(context);
	}

	public void stop(BundleContext arg0) throws Exception {
		scallee.close();
		scaller.close();
	}

}
