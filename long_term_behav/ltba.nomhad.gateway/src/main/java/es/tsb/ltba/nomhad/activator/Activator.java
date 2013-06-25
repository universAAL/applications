package es.tsb.ltba.nomhad.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class Activator implements BundleActivator {

	private ModuleContext context;

	public void start(BundleContext context) throws Exception {

		this.context = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });
		NomhadGateway.getInstance().setModuleContext(this.context);
	}

	public void stop(BundleContext context) throws Exception {
	}

	public void setContext(ModuleContext context) {
		this.context = context;
	}

	public ModuleContext getContext() {
		return context;
	}

}
