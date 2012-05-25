package org.universAAL.AALApplication.health.profile.manager;

import org.universAAL.itests.IntegrationTest;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.AssistedPersonProfile;
import org.universAAL.ontology.profile.service.ProfilingService;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.services.GetProfileService;

public class ArtifactTest extends IntegrationTest {

	
	private static final String NAMESPACE = "http://ontology.upm.es/Test.owl#";
	private static final String USER = "saied";

    private ServiceCaller caller;
    
    public void onSetUp() {
    	logAllBundles();
    	caller = new DefaultServiceCaller(ProjectActivator.context);
    	
    	AssistedPerson ap = new AssistedPerson(NAMESPACE+USER);
    	AssistedPersonProfile app = new AssistedPersonProfile(NAMESPACE + USER + "prof");
    	ap.setProfile(app);
    	
    	ServiceRequest req = new ServiceRequest(new ProfilingService(null),	null);
    	req.addAddEffect(new String[] { ProfilingService.PROP_CONTROLS }, ap);

    	assertTrue(caller.call(req).equals(CallStatus.succeeded));
    }
    
    public void testGetProfile() {
    	ProfileServerManager psm = new ProfileServerManager(ProjectActivator.context);
    	HealthProfile hp = psm.getHealthProfile(NAMESPACE+USER);
    	assertNotNull(hp);
    }
    
    public void testGetProfileService() {
    	ServiceRequest req = new ServiceRequest( new GetProfileService(), new AssistedPerson(NAMESPACE+USER));
    	req.addRequiredOutput(HealthProfile.MY_URI, new String[] {GetProfileService.PROP_MANAGES_PROFILE});
    	ServiceResponse sr = caller.call(req);
    	assertTrue(sr.getCallStatus().equals(CallStatus.succeeded));
  
    	HealthProfile hp = (HealthProfile) sr.getOutput(GetProfileService.PROP_MANAGES_PROFILE, true).get(0); 
    	assertNotNull(hp);
    }
}
