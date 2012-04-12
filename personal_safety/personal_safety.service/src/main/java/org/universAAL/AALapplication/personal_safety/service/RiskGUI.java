/*
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
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
package org.universAAL.AALapplication.personal_safety.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.Group;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.MediaObject;
import org.universAAL.middleware.io.rdf.SimpleOutput;
import org.universAAL.middleware.io.rdf.Submit;

/**
 * @author <a href="mailto:alfiva@itaca.upv.es">Alvaro Fides Valero</a>
 *
 */
public class RiskGUI{
	public static final String PERSONA_INPUT_NAMESPACE = org.universAAL.middleware.io.rdf.Input.RDF_NAMESPACE;//"http://ontology.aal-persona.org/Input.owl#"; //$NON-NLS-1$
	public static final String SUBMIT_HOME = PERSONA_INPUT_NAMESPACE + "home"; //$NON-NLS-1$
	public static final String SUBMIT_CANCEL = PERSONA_INPUT_NAMESPACE + "cancel"; //$NON-NLS-1$
	public static final String BUTTON_TITLE = Messages.getString("RiskGUI.1"); //$NON-NLS-1$
	public static final String SMS_TITLE = Messages.getString("RiskGUI.2"); //$NON-NLS-1$
	public static final String HOME_SUBMIT = Messages.getString("RiskGUI.3"); //$NON-NLS-1$
	public static final String SMS_IMG_LABEL = Messages.getString("RiskGUI.4"); //$NON-NLS-1$
	public static final String SMS_TEXT = Messages.getString("RiskGUI.5"); //$NON-NLS-1$
	public static final String SMS_LABEL = Messages.getString("RiskGUI.6"); //$NON-NLS-1$
	public static final String BUTTON_LABEL = Messages.getString("RiskGUI.7"); //$NON-NLS-1$
	public static final String SMS_NO_TEXT = Messages.getString("RiskGUI.8"); //$NON-NLS-1$
	public static final String VC_TITLE = Messages.getString("RiskGUI.9"); //$NON-NLS-1$
	public static final String VC_NO_TEXT = Messages.getString("RiskGUI.10"); //$NON-NLS-1$
	public static final String BATTERY_TEXT = Messages.getString("RiskGUI.11"); //$NON-NLS-1$
	public static final String BATTERY_TITLE = Messages.getString("RiskGUI.12"); //$NON-NLS-1$

	private static final String imgroot="/img/risk/"; //$NON-NLS-1$ //$NON-NLS-2$
	
	private final static Logger log=LoggerFactory.getLogger(RiskGUI.class);
	
	public Form getUserStateForm(){
		log.debug("Generating abort button form");
		Form f = Form.newDialog(BUTTON_TITLE, (String)null);
		Group controls = f.getIOControls();

		Label labelBoton = new Label(BUTTON_LABEL,imgroot + "ShinyButtonGreen5.jpg");
		new Submit(controls,labelBoton,SUBMIT_CANCEL);
		
		return f;
	}
	
	public Form getSMSForm(boolean sent){
		log.debug("Generating sms form");
		Form f = Form.newDialog(SMS_TITLE, (String)null);
		Group controls = f.getIOControls();
		Group submits = f.getSubmits();
		
		new Submit(submits, new Label(HOME_SUBMIT,(String)null), SUBMIT_HOME);
		
		new MediaObject(controls,new Label(SMS_IMG_LABEL,imgroot + (sent?"enviarSMS.jpg":"smsNoEnviado.gif")),"image",imgroot + (sent?"enviarSMS.jpg":"smsNoEnviado.gif"));
		new SimpleOutput(controls,new Label("",(String)null), null, sent?SMS_TEXT:SMS_NO_TEXT);
		
		return f;
	}

	public Form getNoVCForm() {
		log.debug("Generating vc failed form");
		Form f=Form.newMessage(VC_TITLE, VC_NO_TEXT);
		return f;
//		Form f = Form.newDialog(VC_TITLE, (String)null);
//		Group controls = f.getIOControls();
//		Group submits = f.getSubmits();
//		
//		new Submit(submits, new org.persona.middleware.dialog.Label(HOME_SUBMIT,(String)null), SUBMIT_HOME);
//		new SimpleOutput(controls,new org.persona.middleware.dialog.Label("",(String)null), null, VC_NO_TEXT);
//		
//		return f;
	}
	
	public Form getBatteryForm() {
		log.debug("Generating battery form");
		Form f=Form.newMessage(BATTERY_TITLE, BATTERY_TEXT);
		return f;
	}
}