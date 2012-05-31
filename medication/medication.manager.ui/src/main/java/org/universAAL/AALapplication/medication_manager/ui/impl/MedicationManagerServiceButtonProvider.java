package org.universAAL.AALapplication.medication_manager.ui.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

public class MedicationManagerServiceButtonProvider extends ServiceCallee {

	private static final String NAMESPACE = "http://ontologies.universAAL.com/MedicationManagerService.owl#";
	private static final String MY_URI = NAMESPACE + "MedicationManagerButtonService";
	private static final String START_UI = NAMESPACE + "startUI";

	private ModuleContext ctxt;

	public MedicationManagerServiceButtonProvider(ModuleContext context,
                                                ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		this.ctxt = context;
	}

	public MedicationManagerServiceButtonProvider(ModuleContext context){
		this(context,getProfiles());
	}

	private static ServiceProfile[] getProfiles() {
		ServiceProfile initDP = InitialServiceDialog
				.createInitialDialogProfile(
						MY_URI,
						"http://depot.universAAL.com",
						"Medication Manager Service Main Menu",
						START_UI);
		return new ServiceProfile[] {initDP};
	}

	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		Object inputUser = call.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
		new MainMedicationManagerMenu(this.ctxt).showDialog((Resource) inputUser);
		return new ServiceResponse(CallStatus.succeeded);
	}

}
