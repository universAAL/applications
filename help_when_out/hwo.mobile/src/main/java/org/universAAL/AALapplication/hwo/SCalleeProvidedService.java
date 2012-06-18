package org.universAAL.AALapplication.hwo;



import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


 
public class SCalleeProvidedService extends Service {
	private final static Logger log = LoggerFactory.getLogger(SCalleeProvidedService.class);
	public static ServiceProfile[] profiles = new ServiceProfile[4]; 

	
	public static final String HWO_SERVER_NAMESPACE = "http://ontology.universAAL.org/HwoServer.owl#";
	public static final String MY_URI = HWO_SERVER_NAMESPACE + "HwoService";
	
	static{
				 
		profiles[0] = InitialServiceDialog.createInitialDialogProfile(
						HwoNamespace.NAMESPACE +"Panic",
						"http://www.tsb.upv.es", "Panic Button",
						HwoNamespace.NAMESPACE+"startUIpanic"); //Hwo.NAMESPACE = http://ontology.universAAL.org/hwo.owl#
		
		profiles[1] = InitialServiceDialog.createInitialDialogProfile(
						HwoNamespace.NAMESPACE+"TakeHome", // This URI has to be put in "main_menu_saied_en.txt" so it starts automatically
						"http://www.tsb.upv.es", "Take me Home",
						HwoNamespace.NAMESPACE+"startUIhome"); // This URI is the one that SCallee recognises in its code
		
		profiles[2] = InitialServiceDialog.createInitialDialogProfile(
						HwoNamespace.NAMESPACE+"AlertCaregivers",
						"http://www.tsb.upv.es", "Alert Caregivers",
						HwoNamespace.NAMESPACE+"startUIalertcg"); //al quitarlo, cambiar tambien el tamaño de perfiles.
		
		profiles[3] = InitialServiceDialog.createInitialDialogProfile(
						HwoNamespace.NAMESPACE+"GuideMe",
						"http://www.tsb.upv.es", "Guide Me",
						HwoNamespace.NAMESPACE+"startUIguide");
		log.info("Profiles created");
		
		
		  
	}	   
	
	
	
	private SCalleeProvidedService(String uri) {
		   super(uri);
	}
	
}




