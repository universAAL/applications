package org.universAAL.AALapplication.hwo;

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
import java.util.Locale;

// import org.universAAL.middleware.rdf.Resource;
//import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.container.ModuleContext;
//import org.universAAL.middleware.io.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.Select1;
//import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
// old import org.universAAL.middleware.output.OutputEvent;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest; 
import org.universAAL.middleware.ui.UIHandler; 
// old import org.universAAL.middleware.output.OutputPublisher;
//import org.universAAL.middleware.rdf.PropertyPath;
//old import org.universAAL.middleware.owl.OrderingRestriction;
//old import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.owl.IntRestriction;
import org.universAAL.middleware.owl.MergedRestriction; 

import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.ui.owl.Modality;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
//import org.universAAL.middleware.owl.OrderingRestriction;
//import org.universAAL.middleware.owl.Restriction;
//import org.universAAL.ontology.phThing.Device;
//import org.universAAL.middleware.io.rdf.InputField;

import org.universAAL.ontology.phThing.PhysicalThing;
import org.universAAL.ontology.profile.User;


// Imports de InputConsumer: 


import org.universAAL.middleware.ui.UIResponse;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UImanager extends UICaller {
	
			
	static final String OUTPUT_NAMESPACE = HwoNamespace.NAMESPACE+"OutputProvider#";

	//URIs
    public static final String HWO_INPUT_NAMESPACE = "http://ontology.universAAL.org/Input.owl#"; 
    public static final String SUBMIT_HOME = HWO_INPUT_NAMESPACE + "home"; 
    public static final String SUBMIT_MANUAL = HWO_INPUT_NAMESPACE+ "manual";
    public static final String SUBMIT_TAKE = HWO_INPUT_NAMESPACE + "take"; 
    public static final String SUBMIT_ALERT = HWO_INPUT_NAMESPACE + "Alert";
    public static final String SUBMIT_GUIDE = HWO_INPUT_NAMESPACE + "Guide";
    public static final String SUBMIT_CHOOSE = HWO_INPUT_NAMESPACE + "Choose";
    public static final String SUBMIT_ALERT_YES = HWO_INPUT_NAMESPACE + "AlertYes";
    public static final String SUBMIT_ALERT_NO = HWO_INPUT_NAMESPACE + "AlertNo";
    
    //Messages
    public static final String SMS_TITLE = Messages.getString("HwOGUI.1"); // src/main/resources/org/universAAL/AALapplication/hwo
    public static final String HOME_SUBMIT = Messages.getString("HwOGUI.2"); 
    public static final String SMS_IMG_LABEL = Messages.getString("HwOGUI.3"); 
    public static final String SMS_TEXT = Messages.getString("HwOGUI.4"); 
    public static final String SMS_NO_TEXT = Messages.getString("HwOGUI.5");
    public static final String BUTTON_MANUAL_TITLE = Messages.getString("HwOGUI.6"); 
    public static final String BUTTON_MANUAL_LABEL = Messages.getString("HwOGUI.7"); 
    public static final String TAKE_HOME_TITLE = Messages.getString("HwOGUI.8"); 
    public static final String TAKE_HOME_LABEL = Messages.getString("HwOGUI.9"); 
    public static final String TAKE_HOME_TEXT = Messages.getString("HwOGUI.10"); 
    public static final String ALERT_CG_TEXT = Messages.getString("HwOGUI.11"); 
    public static final String ALERT_CG_TITLE = Messages.getString("HwOGUI.12");
    public static final String ALERT_CG_LABEL = Messages.getString("HwOGUI.13");
    public static final String GUIDE_ME_TITLE = Messages.getString("HwOGUI.14");
    public static final String GUIDE_ME_TEXT = Messages.getString("HwOGUI.15");
    public static final String GUIDE_ME_CHOOSE_LABEL = Messages.getString("HwOGUI.16");
    public static final String CHOOSE_POI_TITLE = Messages.getString("HwOGUI.17"); 
    public static final String CHOOSE_POI_DESTINATIONS = Messages.getString("HwOGUI.18");
    public static final String CHOOSE_POI_TEXT = Messages.getString("HwOGUI.19");
    public static final String ASK_ALERT_TITLE = Messages.getString("HwOGUI.20");
    public static final String ASK_ALERT_YES = Messages.getString("HwOGUI.21");
    public static final String ASK_ALERT_NO = Messages.getString("HwOGUI.22");
    public static final String ASK_ALERT_TEXT_OUT = Messages.getString("HwOGUI.23");
    public static final String ASK_ALERT_TEXT_STOPPED = Messages.getString("HwOGUI.24");
    public static final String ASK_ALERT_TEXT_WANDERING = Messages.getString("HwOGUI.25");
    public static final String ALERT_CG_OUT = Messages.getString("HwOGUI.26");
    public static final String ALERT_CG_STOPPED = Messages.getString("HwOGUI.27");
    public static final String ALERT_CG_WANDERING = Messages.getString("HwOGUI.28");
    public static final String PANIC_BUTTON_PRESSED = Messages.getString("HwOGUI.29");
    
    
   		
    static final String PROP_SELECTED_POI_INDEX	= OUTPUT_NAMESPACE+"POI_index";
    static final PropertyPath PROP_PATH_POI_INDEX =
    		new PropertyPath(null, false,
    			new String[] {
    				PROP_SELECTED_POI_INDEX
    			});
    
    private final static Logger log = LoggerFactory.getLogger(UImanager.class);

    
    protected UImanager(ModuleContext context) {
    	super(context); 
		
    	}

    public void communicationChannelBroken() {
    	 
    	}
    
 

    public void dialogAborted(String dialogID) {
    }
    
    // UI methods to be called from another HWO classes

    public void showButtonManualForm(User user) { //Panic Button
		log.debug("Show manual panic button screen");
		Form f = getPanicButtonForm();
		UIRequest oe = new UIRequest(user, f, LevelRating.high,Locale.ENGLISH, PrivacyLevel.insensible);
		
		sendUIRequest(oe);

	    }
    
    public void showGuideMeForm(User user) { // Guide Me
    	log.debug("Show Guide Me screen");
    	Form f = getGuideMeForm();
    	UIRequest oe = new UIRequest(user, f, LevelRating.full,
    		Locale.ENGLISH, PrivacyLevel.insensible);
    	
    	sendUIRequest(oe);
	}
    
    public void showSMSForm(User user, boolean smsSuccess) { //SMS information screen: sent or not sent
    	log.debug("Show SMS screen");
    	Form f = getSMSForm(smsSuccess);
    	UIRequest oe = new UIRequest(user, f, LevelRating.full,
		Locale.ENGLISH, PrivacyLevel.insensible);
		
		sendUIRequest(oe);
	
    }
    
        
 
    
    public void showgenericMSG(User user, String MSG,String Title) {    // For testing purposes
    	log.debug("Show Generic Message");
    	Form f = getGenericMSGForm(MSG,Title);
    	UIRequest oe = new UIRequest(user, f, LevelRating.full,
    		Locale.ENGLISH, PrivacyLevel.insensible);
    	sendUIRequest(oe);
    	
     }
    
    public void showalertForm(User user, String wd) {
    	log.debug("Show Alert screen");
    	Form f = getshowalertForm(wd);
    	UIRequest oe = new UIRequest(user, f, LevelRating.full,
    		Locale.ENGLISH, PrivacyLevel.insensible);
    	sendUIRequest(oe);
		
	}
    
   

	public void showAvailablePOIForm(User user, List<POI> POIs) { //POI List for Guide Me Service
    	log.debug("Show Generic Message");
    	Form f = getShowPOIsForm(POIs);
    	UIRequest oe = new UIRequest(user, f, LevelRating.full,
    		Locale.ENGLISH, PrivacyLevel.insensible);
    	
    	sendUIRequest(oe);
    	
     }
    


    // ___FORMS________________________

    public Form getPanicButtonForm() {
		log.debug("Generating panic button form");
		Form f = Form.newDialog(BUTTON_MANUAL_TITLE, (String) null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		Label labelBoton = new Label(BUTTON_MANUAL_LABEL, null);
		new Submit(controls, labelBoton, SUBMIT_MANUAL); // This URI is the one that is recognized by the listener methods.
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
		return f;
	    }

    public Form getTakeHomeForm() {
		log.debug("Generating take home form");
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
    	log.debug("Generating alert caregivers form");
    	Form f = Form.newDialog(ALERT_CG_TITLE, (String) null);
    	Group controls = f.getIOControls();
    	Group submits = f.getSubmits();
    	new SimpleOutput(controls, null, null, ALERT_CG_TEXT);
    	Label labelBoton = new Label( ALERT_CG_LABEL, null);
    	new Submit(controls, labelBoton, SUBMIT_ALERT); //esto habría que cambiarlo, aquí y en el input? para que use el namespace de output.
    	new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME); //volver al menú principal
    	return f;
        }
    
    private Form getshowalertForm(String wd) {
    	log.debug("Generating alert form");
    	Form f = Form.newDialog(ASK_ALERT_TITLE, (String) null);
    	Group controls = f.getIOControls();
    	Group submits = f.getSubmits();
    	if (wd=="OUT") new SimpleOutput(controls, null, null, ASK_ALERT_TEXT_OUT);
    	else if (wd=="STOPPED") new SimpleOutput(controls, null, null, ASK_ALERT_TEXT_STOPPED);
    	else new SimpleOutput(controls, null, null, ASK_ALERT_TEXT_WANDERING);
    	Label labelBoton = new Label( ASK_ALERT_YES, null);
    	Label labelBoton2 = new Label( ASK_ALERT_NO, null);
    	new Submit(controls, labelBoton, SUBMIT_ALERT_YES); 
    	new Submit(controls, labelBoton2, SUBMIT_ALERT_NO); 
    	return f;
		
	}
    
    public Form getGuideMeForm() {
    	log.debug("Generating guide me form");
    	Form f = Form.newDialog(GUIDE_ME_TITLE, (String) null);
    	Group controls = f.getIOControls();
    	Group submits = f.getSubmits();
    	new SimpleOutput(controls, null, null, GUIDE_ME_TEXT);
    	Label labelBoton = new Label(TAKE_HOME_LABEL, null);
    	new Submit(controls, labelBoton,SUBMIT_TAKE); //Si quiere ir a casa, ejecutamos Take Me home
    	Label labelBoton2 = new Label(GUIDE_ME_CHOOSE_LABEL, null);
    	new Submit(controls, labelBoton2, SUBMIT_GUIDE);
    	new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);

    	return f;
        }
    
    public Form getShowPOIsForm(List<POI> POIs) {
    	log.debug("Generating POIs form");
    	Form f = Form.newDialog(CHOOSE_POI_TITLE, (String) null); 
    	Group submits = f.getSubmits();
    	Select1 select = new Select1(f.getIOControls(),
				new Label(CHOOSE_POI_DESTINATIONS, null),
				PROP_PATH_POI_INDEX,
				MergedRestriction.getAllValuesRestrictionWithCardinality(
						PROP_SELECTED_POI_INDEX,       //this is where we stored the selected idex POI, global variable that can be accessed from SCallee.
						TypeMapper.getDatatypeURI(Integer.class),						
						1, 1), //Only one selection is allowed. Almacenar solo un entero en esa propiedad
				new Integer(0)); //opcion por defecto

		for (int i=0; i<POIs.size(); i++)
			select.addChoiceItem(
				new ChoiceItem(POIs.get(i).getName(),null,new Integer(i))); //Choice Item: returns POI index when a POI name is selected
		new Submit(f.getSubmits(), new Label(CHOOSE_POI_TEXT, null), SUBMIT_CHOOSE);
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
		return f;
        }
    

    public Form getSMSForm(boolean sent) {
		log.debug("Generating sms form");
		Form f = Form.newDialog(SMS_TITLE, (String) null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
		new MediaObject(controls, new Label(SMS_IMG_LABEL, (sent ? "/enviarSMS.jpg" : "/smsNoEnviado.gif")), "image", //in future versions of UAAL it may be necessary to remove the "/" before image name.
				(sent ? "/enviarSMS.jpg" : "/smsNoEnviado.gif"));
		new SimpleOutput(controls, new Label("", (String) null), null,
			sent ? SMS_TEXT : SMS_NO_TEXT); 
		return f;
	    }
    
    public Form getGenericMSGForm(String MSG,String Title) {
    	log.debug("Generating GenericMSG form");
    	Form f = Form.newMessage(Title, MSG);
    	return f;
    	
        }

	//_INPUT METHODS 
    
    public void handleUIResponse(UIResponse input)  {
    	User user = (User) input.getUser();
    	log.info("Received an Input Event from user {}", user.getURI());
    	String submit = input.getSubmissionID();
    	

		try { //Submits inside any Service UI.
			
		    if (submit.equals(SUBMIT_HOME)) { //This is an URI, corresponding to this service.
			log.debug("Input received was go Home"); // Home = Aplication main menu. As we do nothing, and every "submit" button close the form, this will lead us to main menu.
		   
		    } else if (submit.equals(SUBMIT_MANUAL)) { 
		    	log.debug("Calling Panic Button Service in Scallee");
		    	boolean sent = Activator.scallee.Panic(user); 
		    	if (sent==true) {log.debug("exito en Send");} else log.debug("fallo en Send");
		    	
		   
		    } else if (submit.equals(SUBMIT_TAKE)) { 
		    	log.debug("Calling Take Me Home Service in Scallee");
		    	boolean taken = Activator.scallee.TakeHome();
		    	if (taken==true) {log.debug("exito en Take");} else log.debug("fallo en Take");
		    	
		   
		    }else if (submit.equals(SUBMIT_ALERT)) { 
			    	log.debug("Calling Alert Caregivers Service in Scallee");
			    	
			    	boolean alerted = Activator.scallee.AlertCareGivers(user,null);
			    	if (alerted==true) {log.debug("exito en Alert");} else log.debug("fallo en Alert");
			    	
		    
		    }else if (submit.equals(SUBMIT_GUIDE)) { 
		    	log.debug("Calling Guide Me Service in Scallee");
		    	
		    	boolean guided = Activator.scallee.GuideMe(user);
		    	if (guided==true) {log.debug("exito en GuideMe");} else log.debug("fallo en GuideMe");
	
		   
		    }  else if (submit.equals(SUBMIT_CHOOSE)) { 
		    	log.debug("Starting Guide To function in Guide Me Service");
		    	int POIindex = -1; //Getting the result of the select form.
				String POIselected = null;
				String POIselectedLocation = null;
		    	Object obj = input.getUserInput(new String[]{PROP_SELECTED_POI_INDEX}); //In this property path is stored the selected item's index.
				if (obj instanceof Integer) {
					POIindex = ((Integer) obj).intValue();
					POIselected = Activator.scallee.GetPOIs().get(POIindex).getName(); // To retrieve the informal name of POI
					POIselectedLocation  = Activator.scallee.GetPOIs().get(POIindex).getCoordinate(); // To retrieve the GPS Location of POI
				}
		    	boolean guidedto = Activator.scallee.GuideTo(POIselectedLocation);
		    	if (guidedto==true) {log.debug("exito en GuideTo");} else log.debug("fallo en GuideTo");
		    	
		    	
		    } else if (submit.equals(SUBMIT_ALERT_YES)) { 
		    	log.debug(" debug El usuario ha pedido avisar a los cuidadores");
		    	
		    	boolean alerted = Activator.wanderingdetector.alert_yes();
		    	if (alerted==true) {log.debug("exito en Alert");} else log.debug("fallo en Alert");
		    	
		    	
		    } 
		    else if (submit.equals(SUBMIT_ALERT_NO)) { 
		    	log.debug("  debug El usuario ha pedido no avisar a los cuidadores ");
		    	
		    	boolean alerted = Activator.wanderingdetector.alert_no();
		    	if (alerted==true) {log.debug("exito en Alert");} else log.debug("fallo en Alert");
		    	
		    	
		    }
		    
		} catch (Exception e) {
		    log.error("Error while processing the user input: {}", e);
		}
    }

	

}