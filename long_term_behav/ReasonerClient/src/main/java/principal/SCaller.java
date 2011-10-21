package principal;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;




public class SCaller extends ServiceCaller{

	private ServiceCaller caller; // to call a service
	
	protected SCaller(BundleContext context,ServiceRequest req) {
		super(context);
		// TODO Auto-generated constructor stub
		
		System.out.println("IM CALLING REASONER-ADDRULE");
		
		ServiceResponse sr;

		

		// I call the service

		sr = caller.call(req);
		
		
		
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public void handleResponse(String reqID, ServiceResponse response) {
		// TODO Auto-generated method stub
		
	}

}
