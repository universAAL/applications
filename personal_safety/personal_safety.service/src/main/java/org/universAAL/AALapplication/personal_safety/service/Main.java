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

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;

/**
 * @author <a href="mailto:alfiva@itaca.upv.es">Alvaro Fides Valero</a>
 *
 */
public class Main {
	public static ModuleContext context=null;
	
    public static SCaller rcaller=null;
	public static OPublisher routput=null;
    public static CSubscriber csubscriber=null;
    public static RiskGUI gui=null;

	public static final String PROPS_FILE="RiskStub.properties";
	public static final String USER="VC.user";
	public static final String CONTACT="VC.contact";
	public static final String VCENABLE="VC.enabled";
	public static final String TEXT="SMS.text";
	public static final String RISKTEXT="SMS.risk";
	public static final String NUMBER="SMS.number";
	public static final String SMSENABLE="SMS.enabled";
	public static final String DELAY="RISK.delay";
	public static final String DEFAULT="RISK.Room@Default";
	public static final String RISKENABLE="RISK.enabled";

	public static final String COMMENTS="This file stores persistence info for the Risk Manager stub. Times in minutes. \n" +
			"To set a risk situation timer for a room at a specific time, use the following: \n" +
			"RISK.Room@<URISuffixOfTheRoom>=00:<TimerMinutes>,<StartingHourOfPeriod>:<TimerMinutes>,... \n" +
			"Example: RISK.Room@Bathroom=00:60,06:150,12:60";
	
	private final static Logger log=LoggerFactory.getLogger(Main.class);

	public void start(ModuleContext context) throws Exception {
		log.info("Starting Risk manager stub bundle");
		Main.context=context;
		rcaller=new SCaller(context);
		gui=new RiskGUI();
		routput=new OPublisher(context);
		csubscriber=new CSubscriber(context);
		log.info("Started Risk manager stub bundle");
	}

	public void stop(ModuleContext context) throws Exception {
		log.info("Stopping riskstub bundle");
		routput.close();
		csubscriber.close();
		log.info("Stopped riskstub Advisor bundle");
	}
	
	public static synchronized void setProperties(Properties prop){
		try {
			FileWriter out;
			out = new FileWriter(PROPS_FILE);
			prop.store(out, COMMENTS);
			out.close();
		} catch (Exception e) {
			log.error("Could not set properties file: {}",e);
		}
	}
	
	public static synchronized Properties getProperties(){
		Properties prop=new Properties();
		try {
			prop=new Properties();
			InputStream in = new FileInputStream(PROPS_FILE);
			prop.load(in);
			in.close();
		} catch (java.io.FileNotFoundException e) {
			log.warn("Properties file does not exist; generating default...");
			prop.setProperty(USER,"Elderly-User");
			prop.setProperty(CONTACT,"John-Doctor");
			prop.setProperty(VCENABLE,"false");
			
			prop.setProperty(TEXT,"Panic button pressed");
			prop.setProperty(RISKTEXT,"Risk situation detected");
			prop.setProperty(NUMBER,"123456789");
			prop.setProperty(SMSENABLE,"false");
			
			prop.setProperty(DELAY,"1");
			prop.setProperty(DEFAULT,"00:0");
			prop.setProperty(RISKENABLE,"false");
			setProperties(prop);
		}catch (Exception e) {
			log.error("Could not access properties file: {}",e);
		}
		return prop;
	}

}
