package org.universAAL.AALApplication.health.profile.manager;

import org.universAAL.itests.IntegrationTest;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.services.GetProfileService;

public class HealthProfileProviderTest extends IntegrationTest {

	private static final String NAMESPACE = "http://ontology.upm.es/Test.owl#";
	private static final String USER = "saied";
	
	public HealthProfileProviderTest(){
		setIgnoreVersionMismatch(false);
	}
	
    public void testGetProfileService() {
    	ProjectActivator.context.logInfo("MAIN TEST","Testing get Health Profile Service Call", null);
    	ServiceRequest req = new ServiceRequest( new GetProfileService(), new AssistedPerson(NAMESPACE+USER));
    	req.addRequiredOutput(HealthProfile.MY_URI, new String[] {GetProfileService.PROP_MANAGES_PROFILE});
    	DefaultServiceCaller caller = new DefaultServiceCaller(ProjectActivator.context);
    	ServiceResponse sr = caller.call(req);
    	assertTrue(sr.getCallStatus().equals(CallStatus.succeeded));
  
    	HealthProfile hp = (HealthProfile) sr.getOutput(GetProfileService.PROP_MANAGES_PROFILE, true).get(0); 
    	assertNotNull(hp);
    }
    
}
