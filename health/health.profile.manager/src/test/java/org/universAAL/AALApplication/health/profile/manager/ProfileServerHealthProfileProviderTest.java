package org.universAAL.AALApplication.health.profile.manager;

import org.universAAL.itests.IntegrationTest;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.AssistedPersonProfile;
import org.universAAL.ontology.profile.service.ProfilingService;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.TakeMeasurementActivity;
import org.universaal.ontology.health.owl.Treatment;

//public class ProfileServerHealthProfileProviderTest extends IntegrationTest {
//
//	
//	private static final String NAMESPACE = "http://ontology.upm.es/Test.owl#";
//	private static final String USER = "saied";
//
//	private static final String MODULE = "health.profile.manager";
//	
//    private ServiceCaller caller;
//    
//    public ProfileServerHealthProfileProviderTest() {
//        setIgnoreVersionMismatch(true);
//    }
//    
//    public void onSetUp() {
//    	logAllBundles();
//    	
//    	caller = new DefaultServiceCaller(ProjectActivator.context);
//    	
//    	AssistedPerson ap = new AssistedPerson(NAMESPACE+USER);
//    	AssistedPersonProfile app = new AssistedPersonProfile(NAMESPACE + USER + "prof");
//    	ap.setProfile(app);
//    	
//    	ServiceRequest req = new ServiceRequest(new ProfilingService(null),	null);
//    	req.addAddEffect(new String[] { ProfilingService.PROP_CONTROLS }, ap);
//
//    	ProjectActivator.context.logInfo(MODULE, "Calling new Assided Person Profile", null);
//    	ServiceResponse r = caller.call(req);
//    	
//    	ProjectActivator.context.logInfo(MODULE, "Adding Assisted Person Profile Result: " + r.getCallStatus().name(), null);
//    	//assertTrue(.equals(CallStatus.succeeded));
//    }
//    
//    public void testGetProfile() {
//    	ProjectActivator.context.logInfo(MODULE, "Testing get Health Profile", null);
//    	HealthProfileProvider psm = new ProfileServerHealthProfileProvider(ProjectActivator.context);
//    	HealthProfile hp = psm.getHealthProfile(NAMESPACE+USER);
//    	assertNotNull(hp);
//    }
//    
//    public void testUpdateProfile() {
//    	ProjectActivator.context.logInfo(MODULE, "Testing update Health Profile", null);
//    	HealthProfileProvider psm = new ProfileServerHealthProfileProvider(ProjectActivator.context);
//    	HealthProfile hp = psm.getHealthProfile(NAMESPACE+USER);
//    	String treatmentName = "Take Weight measurement";
//    	Treatment t = new TakeMeasurementActivity(treatmentName, "", null);
//    	hp.addTreatment(t);
//    	psm.updateHealthProfile(hp);
//    	hp = psm.getHealthProfile(NAMESPACE+USER);
//    	assertTrue(hp.getTreatments().length == 1);
//		assertTrue(hp.getTreatments()[0].getName().equals(treatmentName));
//    }
//
//}
