package health.test.treatment.generator;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.services.TreatmentManagementService;

public class TreatmentGenerator extends ServiceCaller {

	protected TreatmentGenerator(ModuleContext context) {
		super(context);
	}

	@Override
	public void communicationChannelBroken() {

	}

	@Override
	public void handleResponse(String reqID, ServiceResponse response) {
		// TODO Auto-generated method stub

	}
	
	public void addTreatment(Treatment treatment, User usr) {
		ServiceRequest sr = new ServiceRequest(new TreatmentManagementService(), usr);
		sr.addAddEffect(new String[] {TreatmentManagementService.PROP_MANAGES_TREATMENT}, treatment);
		
	}

}
