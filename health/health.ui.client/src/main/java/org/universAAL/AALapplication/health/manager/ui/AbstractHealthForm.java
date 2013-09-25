/**
 * 
 */
package org.universAAL.AALapplication.health.manager.ui;

import java.util.Locale;
import java.util.Map;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.services.ProfileManagementService;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public abstract class AbstractHealthForm extends UICaller {

	private static final String REQ_OUTPUT_PROFILE = "http://ontologies.universaal.org/TreatmentManager#profile";
	protected ModuleContext context;
	protected User inputUser;

	static private Map<User, AbstractHealthForm> currentForm;
	
	/**
	 * @param context
	 */
	public AbstractHealthForm(ModuleContext context, User inputUser) {
		super(context);
		this.context = context;
		this.inputUser = inputUser;
	}

	@Override
	public void communicationChannelBroken() {}

	@Override
	public void dialogAborted(String arg0) {}

	protected void sendForm(Form f){
		sendUIRequest(
				new UIRequest(inputUser, f,
						LevelRating.low, Locale.ENGLISH,
						PrivacyLevel.homeMatesOnly));
	}
	
	
	static public AbstractHealthForm getCurrentFor(User u){
		return currentForm.get(u);
	}
	
	/**
	 * @param user
	 * @return
	 */
	protected HealthProfile getHealthProfile() {
		ServiceRequest req = new ServiceRequest(new ProfileManagementService(null), null);
		req.addValueFilter(new String[] {ProfileManagementService.PROP_ASSISTED_USER}, inputUser);
		req.addRequiredOutput(REQ_OUTPUT_PROFILE, new String[] {ProfileManagementService.PROP_ASSISTED_USER_PROFILE});
		
		ServiceResponse sr = new DefaultServiceCaller(this.context).call(req);
		if (sr.getCallStatus() == CallStatus.succeeded) {
			return (HealthProfile) sr.getOutput(REQ_OUTPUT_PROFILE, false).get(0);
		}
		
		return null;
	}

}
