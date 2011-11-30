package org.universAAL.AALApplication.health.motivation;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceResponse;

public class SCaller extends ServiceCaller{

	protected SCaller(BundleContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public void handleResponse(String reqID, ServiceResponse response) {
		// TODO Auto-generated method stub
		
	}

}
