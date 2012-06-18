package org.persona.service.helpwhenoutside;

import java.io.FileInputStream;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Vector;

//import org.aal_persona.platform.ContextInitializer.ContextInitializer;
//import org.aal_persona.platform.ContextInitializer.ContextInitializerFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
//import org.osgi.util.tracker.ServiceTracker;
//import org.persona.middleware.context.ContextEvent;

//import org.persona.platform.casf.ontology.PhysicalThing;
//import org.persona.platform.profiling.ontology.User;
//import org.persona.platform.servicegateway.GatewayPort;

import org.persona.service.helpwhenoutside.common.Agenda;
import org.persona.service.helpwhenoutside.common.BundleProvider;
import org.persona.service.helpwhenoutside.common.DataStorage;
//import org.persona.service.helpwhenoutside.common.OutdoorLocationContextPublisher;
import org.persona.service.helpwhenoutside.impl.SMSGatewayImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;
import org.universAAL.ri.servicegateway.GatewayPort;
public class Activator implements BundleActivator, BundleProvider, Runnable {

	private static final Logger log = LoggerFactory.getLogger(Activator.class);
	
	public static ModuleContext context = null;
	public static BundleContext osgiContext=null;
	
	private Agenda agenda;
	private Thread thread;
	public static HwoConsumer hwoconsumer;
	public static Walker paseador; // Just for testing purposes.
	public static WanderingDetector wanderingdetector;
	

	public static Properties config;
	public void start(BundleContext bcontext) throws Exception {
		
		Activator.context = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { bcontext });
		// Retrieve all configuration options needed by the service
		// like parameters for AbnormalSituationDetector or the MAM address
		config = new Properties();
		DataStorage dataStorage = DataStorage.getInstance();
		try {
			// the data directory WITH separator in the end
			String dataDir = dataStorage.getDataDirectory();
			FileInputStream propIS = new FileInputStream(dataDir + "config.properties");
			config.load(propIS);
		} catch (IOException e) {
			log.error("Cannot load config.properties");
		}

		
		agenda = new Agenda(context);
		
	
		osgiContext = bcontext;
		osgiContext.registerService(BundleProvider.class.getName(), this, null);  
		
		HelpWhenOutsideDataService dataService = new HelpWhenOutsideXMLDataService(agenda);
		paseador = new Walker(config,dataService,context); // Just for testing purposes
		hwoconsumer=new HwoConsumer(context,dataService); 
		wanderingdetector = new WanderingDetector(5,5); 
		

		HelpWhenOutsideServlet serviceServlet =
		new HelpWhenOutsideServlet(
				dataService, 
				config);
		
		MapEditor mapEditorServlet = new MapEditor(config, dataService);		
		
		osgiContext.registerService(GatewayPort.class.getName(), serviceServlet, null);
		osgiContext.registerService(GatewayPort.class.getName(), mapEditorServlet, null);
		osgiContext.registerService(SMSGateway.class.getName(), new SMSGatewayImpl(null), null);
		thread = new Thread(this, "Help When Outside WB");
		thread.start();
		log.info("Service Help When Outside STARTED");
	}
	
	public void stop(BundleContext arg0) throws Exception {
		log.info("Service Help When Outside STOPPED");
	}

	public Agenda getAgenda() {

		return agenda;
	}

	

	public synchronized void run() {
	

		
	}

	public AbnormalSituationDetector getAbnormalSituationDetector() {
		// TODO Auto-generated method stub
		return null;
	}

	public OutdoorLocationContextPublisher getOutdoorLocationContextPublisher() {
		// TODO Auto-generated method stub
		return null;
	}


}
