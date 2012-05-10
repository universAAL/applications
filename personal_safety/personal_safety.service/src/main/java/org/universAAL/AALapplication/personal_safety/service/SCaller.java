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

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.AALapplication.personal_safety.sms.owl.EnvioSMSService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;

/*
import src.main.java.es.itaca.tsb.persona.riskstub.Activator;
import src.main.java.es.itaca.tsb.persona.riskstub.Calendar;
import src.main.java.es.itaca.tsb.persona.riskstub.EnvioSMSService;
import src.main.java.es.itaca.tsb.persona.riskstub.ServiceRequest;
import src.main.java.es.itaca.tsb.persona.riskstub.ServiceResponse;
import src.main.java.es.itaca.tsb.persona.riskstub.String;
*/

/**
 * @author <a href="mailto:alfiva@itaca.upv.es">Alvaro Fides Valero</a>
 *
 * Attention: 
 *  Skeleton Class, see riskstub.SCaller for previous version.
 */
public class SCaller {
	DefaultServiceCaller caller;
	
	private final static Logger log=LoggerFactory.getLogger(SCaller.class);
	
    protected SCaller(ModuleContext context) {
    	caller=new DefaultServiceCaller(context);
	}
    
    public boolean startVideoCall(){
    	//TODO Adapt Videoconference Service
    	return true;
    }
    private ServiceRequest sendSMS(String txt,String num){
		ServiceRequest sendSMS = new ServiceRequest(new EnvioSMSService(null), null);
		sendSMS.getRequestedService().addInstanceLevelRestriction(MergedRestriction.getFixedValueRestriction(EnvioSMSService.PROP_MANDA_TEXTO,new String(txt)),new String[] { EnvioSMSService.PROP_MANDA_TEXTO });
		sendSMS.getRequestedService().addInstanceLevelRestriction(MergedRestriction.getFixedValueRestriction(EnvioSMSService.PROP_TIENE_NUMERO, new String(num)),new String[] { EnvioSMSService.PROP_TIENE_NUMERO });
		return sendSMS;
    }
    
    public boolean sendPanicButtonSMSText()
	{
    	log.debug("Calling sms service");
    	String txt=Main.getProperties().getProperty(Main.TEXT, "PERSONA SMS Alert. Contact relative.");
		String num=Main.getProperties().getProperty(Main.NUMBER, "123456789");
		Calendar now = Calendar.getInstance();
		ServiceResponse sr = caller.call(sendSMS(txt + "  ("
				+ now.get(Calendar.HOUR_OF_DAY) + ":"
				+ now.get(Calendar.MINUTE) + ")", num));
		return sr.getCallStatus() == CallStatus.succeeded;
	}	
    
    public boolean sendRiskSMSText()
	{
    	log.debug("Calling sms service");
    	String txt=Main.getProperties().getProperty(Main.RISKTEXT, "PERSONA SMS Alert. Contact relative.");
		String num=Main.getProperties().getProperty(Main.NUMBER, "123456789");
		Calendar now = Calendar.getInstance();
		ServiceResponse sr = caller.call(sendSMS(txt + "  ("
				+ now.get(Calendar.HOUR_OF_DAY) + ":"
				+ now.get(Calendar.MINUTE) + ")", num));
		return sr.getCallStatus()==CallStatus.succeeded;
	}	
}
