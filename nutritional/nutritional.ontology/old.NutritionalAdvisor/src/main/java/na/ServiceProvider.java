/*
	Copyright 2008-2010 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institute of Computer Graphics Research 
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package na;

import na.services.NutriUILauncher;
import na.utils.Utils;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.input.DefaultInputPublisher;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.ontology.nutrition.NutritionService;

/**
 * @author mtazari
 *
 */
public class ServiceProvider extends ServiceCallee {

	public static final String UI_NUTRITIONAL_CLIENT_NAMESPACE = Activator.CLIENT_NUTRITIONAL_UI_NAMESPACE +"ServiceProvider#";
	public static final String START_URI = UI_NUTRITIONAL_CLIENT_NAMESPACE + "MainDialog";

	
	ServiceProvider(BundleContext context) {
		super(context, new ServiceProfile[]{
				InitialServiceDialog.createInitialDialogProfile(
						NutritionService.MY_URI,
						"http://www.tsb.upv.es/",
						"Nutritional UI Client",
						START_URI)
		});
	}

	/**
	 * @see org.persona.middleware.service.ServiceCallee#communicationChannelBroken()
	 */
	public void communicationChannelBroken() {
		Utils.println("communication channel broken :(");
	}

	/**
	 * @see org.persona.middleware.service.ServiceCallee#handleCall(org.persona.middleware.service.ServiceCall)
	 */
	public ServiceResponse handleCall(ServiceCall call) {
		Utils.println("received call: "+call.getProcessURI());
		if (call != null) {
			String operation = call.getProcessURI();
			if (operation != null && operation.startsWith(START_URI)) {
				Utils.println("start service: " + START_URI);
				Utils.println("-- Lighting UI Client Main Menu --");
				return showNutritionalUI(call.getInvolvedUser());
			}
		}
		return null;
	}
	
	private ServiceResponse showNutritionalUI(Resource resource) {
		DefaultInputPublisher ip = new DefaultInputPublisher(Activator.getBundleContext());
		ip.publish(new InputEvent(resource, null, InputEvent.uAAL_MAIN_MENU_REQUEST));
		try {
			NutriUILauncher t = new NutriUILauncher();
			t.launch();
			return new ServiceResponse(CallStatus.succeeded);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(CallStatus.serviceSpecificFailure);
		}
	}
	
//	private ServiceResponse showCalendarUI(Resource resource) {
//		try {
////			new CalendarGUI(Activator.getBundleContext());
//			DefaultInputPublisher ip = new DefaultInputPublisher(Activator.getBundleContext());
//			
//			// test the persona main menu
//			ip.publish(new InputEvent(resource,
//					null, InputEvent.uAAL_MAIN_MENU_REQUEST));
//			return new ServiceResponse(CallStatus.succeeded);
//		} catch (Exception e) {
//			return new ServiceResponse(CallStatus.serviceSpecificFailure);
//		}
////		return null;
//	}
	
}
