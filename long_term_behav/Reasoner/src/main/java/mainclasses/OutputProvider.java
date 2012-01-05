package mainclasses;



import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.io.owl.PrivacyLevel;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.Submit;
import org.universAAL.middleware.output.OutputEvent;
import org.universAAL.middleware.output.OutputPublisher;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.ElderlyUser;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.input.InputSubscriber;
import org.universAAL.middleware.input.InputSubscriber;
public class OutputProvider extends OutputPublisher {
	
	static final String OUTPUT_NAMESPACE = SCalleeProvidedService.SERVICE_START_UI+"OutputProvider#";
	static final String SUBMISSION_ON = OUTPUT_NAMESPACE+"on";
	static final String SUBMISSION_OFF = OUTPUT_NAMESPACE+"off";
	static final String CONFIGURATION = OUTPUT_NAMESPACE+"Configuration";
	static final ElderlyUser testUser = 
		new ElderlyUser(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
	//static InputConsumer inputConsumer;
	
	
	
	private Form mainDialog = null;
	
	protected OutputProvider(BundleContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	

	
	public static Form initMainDialog(){
		
		Form f = Form.newDialog("UI: LTBA", new Resource());
		
		
		if(mainclasses.Methods.getltbaState()== true){
			new Submit(f.getIOControls(), new Label("Off", null), SUBMISSION_ON);
			new Submit(f.getIOControls(), new Label("Configuration", null), CONFIGURATION);
		
		
		} else {
			
			
			new Submit(f.getIOControls(), new Label("On", null), SUBMISSION_ON);
			new Submit(f.getIOControls(), new Label("Configuration", null), CONFIGURATION);
		}
		
		
		
		
		
		
		
		
		return f;
	}


	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
	

	
	Form startMainDialog() {
		if (mainDialog == null)
			mainDialog = initMainDialog();
		OutputEvent out = new OutputEvent(
				testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH,
				PrivacyLevel.insensible);
		//inputConsumer.subscribe(mainDialog.getDialogID());
		publish(out);
		return mainDialog;
		
	}
	
	
	
}
