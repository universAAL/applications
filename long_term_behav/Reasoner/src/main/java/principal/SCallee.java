package principal;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

public class SCallee extends ServiceCallee{

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

	public ServiceResponse handleCall(ServiceCall call) {
		// TODO Auto-generated method stub
		return null;
	}

}
