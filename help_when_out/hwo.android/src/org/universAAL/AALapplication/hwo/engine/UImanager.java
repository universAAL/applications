package org.universAAL.AALapplication.hwo.engine;

// Class to interact with UI.

/* Fallos de las clases:
 * - Primero, el del POM.
 * - HWO falla por "register" 
 * - Locator falla por "register" y por PhysicalThing.getClassRestrictionsOnProperty(propURI), que tiene que ser (String, string) pero no sé cual es la segunda cadena
 * aunque a lo mejor no conviene meterse en esta porque la voy a sustituir.
 * - SCalle falla porque no reconoce los métodos de uimanager. De hecho, ninguna clase puede verlos. Lo raro es que solo fallan los métodos de formularios.
 * 		Es como si no fueran visibles desde fuera, porque desde dentro de la clase si que se pueden llamar. Y se queja de que no están definidos para UICaller en vez de para Uimanager.
 * - SCalleProvidedServices falla por register 
 * - UIManager falla por el método subscribe y porque no sé implementar como poner la lista  */

import java.util.List;

import org.universAAL.AALapplication.hwo.model.User;

import android.util.Log;

public class UImanager {

	static final String OUTPUT_NAMESPACE = HwoNamespace.NAMESPACE
			+ "OutputProvider#";

	// URIs
	public static final String HWO_INPUT_NAMESPACE = "http://ontology.universAAL.org/Input.owl#";
	public static final String SUBMIT_HOME = HWO_INPUT_NAMESPACE + "home";
	public static final String SUBMIT_MANUAL = HWO_INPUT_NAMESPACE + "manual";
	public static final String SUBMIT_TAKE = HWO_INPUT_NAMESPACE + "take";
	public static final String SUBMIT_ALERT = HWO_INPUT_NAMESPACE + "Alert";
	public static final String SUBMIT_GUIDE = HWO_INPUT_NAMESPACE + "Guide";
	public static final String SUBMIT_CHOOSE = HWO_INPUT_NAMESPACE + "Choose";
	public static final String SUBMIT_ALERT_YES = HWO_INPUT_NAMESPACE
			+ "AlertYes";
	public static final String SUBMIT_ALERT_NO = HWO_INPUT_NAMESPACE
			+ "AlertNo";

	// Messages
//	public static final String SMS_TITLE = Messages.getString("HwOGUI.1"); // src/main/resources/org/universAAL/AALapplication/hwo
//	public static final String HOME_SUBMIT = Messages.getString("HwOGUI.2");
//	public static final String SMS_IMG_LABEL = Messages.getString("HwOGUI.3");
//	public static final String SMS_TEXT = Messages.getString("HwOGUI.4");
//	public static final String SMS_NO_TEXT = Messages.getString("HwOGUI.5");
//	public static final String BUTTON_MANUAL_TITLE = Messages
//			.getString("HwOGUI.6");
//	public static final String BUTTON_MANUAL_LABEL = Messages
//			.getString("HwOGUI.7");
//	public static final String TAKE_HOME_TITLE = Messages.getString("HwOGUI.8");
//	public static final String TAKE_HOME_LABEL = Messages.getString("HwOGUI.9");
//	public static final String TAKE_HOME_TEXT = Messages.getString("HwOGUI.10");
//	public static final String ALERT_CG_TEXT = Messages.getString("HwOGUI.11");
//	public static final String ALERT_CG_TITLE = Messages.getString("HwOGUI.12");
//	public static final String ALERT_CG_LABEL = Messages.getString("HwOGUI.13");
//	public static final String GUIDE_ME_TITLE = Messages.getString("HwOGUI.14");
//	public static final String GUIDE_ME_TEXT = Messages.getString("HwOGUI.15");
//	public static final String GUIDE_ME_CHOOSE_LABEL = Messages
//			.getString("HwOGUI.16");
//	public static final String CHOOSE_POI_TITLE = Messages
//			.getString("HwOGUI.17");
//	public static final String CHOOSE_POI_DESTINATIONS = Messages
//			.getString("HwOGUI.18");
//	public static final String CHOOSE_POI_TEXT = Messages
//			.getString("HwOGUI.19");
//	public static final String ASK_ALERT_TITLE = Messages
//			.getString("HwOGUI.20");
//	public static final String ASK_ALERT_YES = Messages.getString("HwOGUI.21");
//	public static final String ASK_ALERT_NO = Messages.getString("HwOGUI.22");
//	public static final String ASK_ALERT_TEXT_OUT = Messages
//			.getString("HwOGUI.23");
//	public static final String ASK_ALERT_TEXT_STOPPED = Messages
//			.getString("HwOGUI.24");
//	public static final String ASK_ALERT_TEXT_WANDERING = Messages
//			.getString("HwOGUI.25");
//	public static final String ALERT_CG_OUT = Messages.getString("HwOGUI.26");
//	public static final String ALERT_CG_STOPPED = Messages
//			.getString("HwOGUI.27");
//	public static final String ALERT_CG_WANDERING = Messages
//			.getString("HwOGUI.28");
//	public static final String PANIC_BUTTON_PRESSED = Messages
//			.getString("HwOGUI.29");

	static final String PROP_SELECTED_POI_INDEX = OUTPUT_NAMESPACE
			+ "POI_index";
//	static final PropertyPath PROP_PATH_POI_INDEX = new PropertyPath(null,
//			false, new String[] { PROP_SELECTED_POI_INDEX });

	private static final String TAG = "UImanager";

	public UImanager() {

	}

	public void communicationChannelBroken() {

	}

	public void dialogAborted(String dialogID) {
	}

	// UI methods to be called from another HWO classes

	public void showButtonManualForm(User user) { // Panic Button
		Log.d(TAG, "Show manual panic button screen");
//		Form f = getPanicButtonForm();
//		UIRequest oe = new UIRequest(user, f, LevelRating.high, Locale.ENGLISH,
//				PrivacyLevel.insensible);

//		sendUIRequest(oe);

	}

	public void showGuideMeForm(User user) { // Guide Me
		Log.d(TAG, "Show Guide Me screen");
//		Form f = getGuideMeForm();
//		UIRequest oe = new UIRequest(user, f, LevelRating.full, Locale.ENGLISH,
//				PrivacyLevel.insensible);

//		sendUIRequest(oe);
	}

	public void showSMSForm(User user, boolean smsSuccess) { // SMS information
																// screen: sent
																// or not sent
		Log.d(TAG, "Show SMS screen");
//		Form f = getSMSForm(smsSuccess);
//		UIRequest oe = new UIRequest(user, f, LevelRating.full, Locale.ENGLISH,
//				PrivacyLevel.insensible);

//		sendUIRequest(oe);

	}

	public void showgenericMSG(User user, String MSG, String Title) { // For
																		// testing
																		// purposes
		Log.d(TAG, "Show Generic Message");
//		Form f = getGenericMSGForm(MSG, Title);
//		UIRequest oe = new UIRequest(user, f, LevelRating.full, Locale.ENGLISH,
//				PrivacyLevel.insensible);
//		sendUIRequest(oe);

	}

	public void showalertForm(User user, String wd) {
		Log.d(TAG, "Show Alert screen");
//		Form f = getshowalertForm(wd);
//		UIRequest oe = new UIRequest(user, f, LevelRating.full, Locale.ENGLISH,
//				PrivacyLevel.insensible);
//		sendUIRequest(oe);

	}

	public void showAvailablePOIForm(User user, List<POI> POIs) { // POI List
																	// for Guide
																	// Me
																	// Service
		Log.d(TAG, "Show Generic Message");
//		Form f = getShowPOIsForm(POIs);
//		UIRequest oe = new UIRequest(user, f, LevelRating.full, Locale.ENGLISH,
//				PrivacyLevel.insensible);

//		sendUIRequest(oe);

	}

	// ___FORMS________________________
/*
	public Form getPanicButtonForm() {
		Log.d(TAG, "Generating panic button form");
		Form f = Form.newDialog(BUTTON_MANUAL_TITLE, (String) null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		Label labelBoton = new Label(BUTTON_MANUAL_LABEL, null);
		new Submit(controls, labelBoton, SUBMIT_MANUAL); // This URI is the one
															// that is
															// recognized by the
															// listener methods.
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
		return f;
	}

	public Form getTakeHomeForm() {
		Log.d(TAG, "Generating take home form");
		Form f = Form.newDialog(TAKE_HOME_TITLE, (String) null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		new SimpleOutput(controls, null, null, TAKE_HOME_TEXT);
		Label labelBoton = new Label(TAKE_HOME_LABEL, null);
		new Submit(controls, labelBoton, SUBMIT_TAKE);
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
		return f;
	}

	public Form getAlertCareGiversForm() {
		Log.d(TAG, "Generating alert caregivers form");
		Form f = Form.newDialog(ALERT_CG_TITLE, (String) null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		new SimpleOutput(controls, null, null, ALERT_CG_TEXT);
		Label labelBoton = new Label(ALERT_CG_LABEL, null);
		new Submit(controls, labelBoton, SUBMIT_ALERT); // esto habría que
														// cambiarlo, aquí y en
														// el input? para que
														// use el namespace de
														// output.
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME); // volver
																					// al
																					// menú
																					// principal
		return f;
	}

	private Form getshowalertForm(String wd) {
		Log.d(TAG, "Generating alert form");
		Form f = Form.newDialog(ASK_ALERT_TITLE, (String) null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		if (wd == "OUT")
			new SimpleOutput(controls, null, null, ASK_ALERT_TEXT_OUT);
		else if (wd == "STOPPED")
			new SimpleOutput(controls, null, null, ASK_ALERT_TEXT_STOPPED);
		else
			new SimpleOutput(controls, null, null, ASK_ALERT_TEXT_WANDERING);
		Label labelBoton = new Label(ASK_ALERT_YES, null);
		Label labelBoton2 = new Label(ASK_ALERT_NO, null);
		new Submit(controls, labelBoton, SUBMIT_ALERT_YES);
		new Submit(controls, labelBoton2, SUBMIT_ALERT_NO);
		return f;

	}

	public Form getGuideMeForm() {
		Log.d(TAG, "Generating guide me form");
		Form f = Form.newDialog(GUIDE_ME_TITLE, (String) null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		new SimpleOutput(controls, null, null, GUIDE_ME_TEXT);
		Label labelBoton = new Label(TAKE_HOME_LABEL, null);
		new Submit(controls, labelBoton, SUBMIT_TAKE); // Si quiere ir a casa,
														// ejecutamos Take Me
														// home
		Label labelBoton2 = new Label(GUIDE_ME_CHOOSE_LABEL, null);
		new Submit(controls, labelBoton2, SUBMIT_GUIDE);
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);

		return f;
	}

	public Form getShowPOIsForm(List<POI> POIs) {
		Log.d(TAG, "Generating POIs form");
		Form f = Form.newDialog(CHOOSE_POI_TITLE, (String) null);
		Group submits = f.getSubmits();
		Select1 select = new Select1(f.getIOControls(), new Label(
				CHOOSE_POI_DESTINATIONS, null), PROP_PATH_POI_INDEX,
				MergedRestriction.getAllValuesRestrictionWithCardinality(
						PROP_SELECTED_POI_INDEX, // this is where we stored the
													// selected idex POI, global
													// variable that can be
													// accessed from SCallee.
						TypeMapper.getDatatypeURI(Integer.class), 1, 1), // Only
																			// one
																			// selection
																			// is
																			// allowed.
																			// Almacenar
																			// solo
																			// un
																			// entero
																			// en
																			// esa
																			// propiedad
				new Integer(0)); // opcion por defecto

		for (int i = 0; i < POIs.size(); i++)
			select.addChoiceItem(new ChoiceItem(POIs.get(i).getName(), null,
					new Integer(i))); // Choice Item: returns POI index when a
										// POI name is selected
		new Submit(f.getSubmits(), new Label(CHOOSE_POI_TEXT, null),
				SUBMIT_CHOOSE);
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
		return f;
	}

	public Form getSMSForm(boolean sent) {
		Log.d(TAG, "Generating sms form");
		Form f = Form.newDialog(SMS_TITLE, (String) null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
		new MediaObject(controls, new Label(SMS_IMG_LABEL,
				(sent ? "/enviarSMS.jpg" : "/smsNoEnviado.gif")), "image", // in
																			// future
																			// versions
																			// of
																			// UAAL
																			// it
																			// may
																			// be
																			// necessary
																			// to
																			// remove
																			// the
																			// "/"
																			// before
																			// image
																			// name.
				(sent ? "/enviarSMS.jpg" : "/smsNoEnviado.gif"));
		new SimpleOutput(controls, new Label("", (String) null), null,
				sent ? SMS_TEXT : SMS_NO_TEXT);
		return f;
	}

	public Form getGenericMSGForm(String MSG, String Title) {
		Log.d(TAG, "Generating GenericMSG form");
		Form f = Form.newMessage(Title, MSG);
		return f;

	}
*/
	// _INPUT METHODS
/*
	public void handleUIResponse(UIResponse input) {
		User user = (User) input.getUser();
		Log.i(TAG, "Received an Input Event from user {}", user.getURI());
		String submit = input.getSubmissionID();

		try { // Submits inside any Service UI.

			if (submit.equals(SUBMIT_HOME)) { // This is an URI, corresponding
												// to this service.
				Log.d(TAG, "Input received was go Home"); // Home = Aplication
															// main menu. As we
															// do nothing, and
															// every "submit"
															// button close the
															// form, this will
															// lead us to main
															// menu.

			} else if (submit.equals(SUBMIT_MANUAL)) {
				Log.d(TAG, "Calling Panic Button Service in Scallee");
				boolean sent = BackgroundService.scallee.Panic(user);
				if (sent == true) {
					Log.d(TAG, "exito en Send");
				} else
					Log.d(TAG, "fallo en Send");

			} else if (submit.equals(SUBMIT_TAKE)) {
				Log.d(TAG, "Calling Take Me Home Service in Scallee");
				boolean taken = BackgroundService.scallee.TakeHome();
				if (taken == true) {
					Log.d(TAG, "exito en Take");
				} else
					Log.d(TAG, "fallo en Take");

			} else if (submit.equals(SUBMIT_ALERT)) {
				Log.d(TAG, "Calling Alert Caregivers Service in Scallee");

				boolean alerted = BackgroundService.scallee.AlertCareGivers(user, null);
				if (alerted == true) {
					Log.d(TAG, "exito en Alert");
				} else
					Log.d(TAG, "fallo en Alert");

			} else if (submit.equals(SUBMIT_GUIDE)) {
				Log.d(TAG, "Calling Guide Me Service in Scallee");

				boolean guided = BackgroundService.scallee.GuideMe(user);
				if (guided == true) {
					Log.d(TAG, "exito en GuideMe");
				} else
					Log.d(TAG, "fallo en GuideMe");

			} else if (submit.equals(SUBMIT_CHOOSE)) {
				Log.d(TAG, "Starting Guide To function in Guide Me Service");
				int POIindex = -1; // Getting the result of the select form.
				String POIselected = null;
				String POIselectedLocation = null;
				Object obj = input
						.getUserInput(new String[] { PROP_SELECTED_POI_INDEX }); // In
																					// this
																					// property
																					// path
																					// is
																					// stored
																					// the
																					// selected
																					// item's
																					// index.
				if (obj instanceof Integer) {
					POIindex = ((Integer) obj).intValue();
					POIselected = BackgroundService.scallee.GetPOIs().get(POIindex)
							.getName(); // To retrieve the informal name of POI
					POIselectedLocation = BackgroundService.scallee.GetPOIs()
							.get(POIindex).getCoordinate(); // To retrieve the
															// GPS Location of
															// POI
				}
				boolean guidedto = BackgroundService.scallee
						.GuideTo(POIselectedLocation);
				if (guidedto == true) {
					Log.d(TAG, "exito en GuideTo");
				} else
					Log.d(TAG, "fallo en GuideTo");

			} else if (submit.equals(SUBMIT_ALERT_YES)) {
				Log.d(TAG,
						" debug El usuario ha pedido avisar a los cuidadores");

				boolean alerted = BackgroundService.wanderingdetector.alert_yes();
				if (alerted == true) {
					Log.d(TAG, "exito en Alert");
				} else
					Log.d(TAG, "fallo en Alert");

			} else if (submit.equals(SUBMIT_ALERT_NO)) {
				Log.d(TAG,
						"  debug El usuario ha pedido no avisar a los cuidadores ");

				boolean alerted = BackgroundService.wanderingdetector.alert_no();
				if (alerted == true) {
					Log.d(TAG, "exito en Alert");
				} else
					Log.d(TAG, "fallo en Alert");

			}

		} catch (Exception e) {
			Log.e(TAG,"Error while processing the user input: {}", e);
		}
	}
*/
}