/**
 * 
 */
package org.universAAL.AALapplication.health.manager.ui2;

import java.util.Locale;
import java.util.Map;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public abstract class AbstractHealthForm extends UICaller {

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

}
