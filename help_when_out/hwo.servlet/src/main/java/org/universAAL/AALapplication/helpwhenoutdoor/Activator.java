/**
 * @author Arturo Domingo
 * @author <a href="mailto:geibsan@upvnet.upv.es">Gema Ibanez&064;UPVLC</a> 
 * @version %I%
 * @since 1.0
 * 
 */

package org.universAAL.AALapplication.helpwhenoutdoor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.AALapplication.helpwhenoutdoor.common.Agenda;
import org.universAAL.AALapplication.helpwhenoutdoor.common.BundleProvider;
import org.universAAL.AALapplication.helpwhenoutdoor.common.DataStorage;
import org.universAAL.AALapplication.helpwhenoutdoor.impl.SMSGatewayImpl;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializerEx;
import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ri.gateway.communicator.service.RemoteSpacesManager;
import org.universAAL.ri.servicegateway.GatewayPort;

public class Activator implements BundleActivator, BundleProvider, Runnable {

    private static final Logger log = LoggerFactory.getLogger(Activator.class);
    public static ModuleContext context = null;
    public static BundleContext osgiContext = null;
    private Agenda agenda;
    private Thread thread;
    public static HwoConsumer hwoconsumer;
    public static WanderingDetector wanderingdetector;
    public static Properties config;

    private static RemoteSpacesManager remoteManager;

    public static Walker paseador; // Just for testing purposes.

    /**
     * Activator method
     * 
     * @param bcontext
     * @exception Exception
     */
    public void start(BundleContext bcontext) throws Exception {

	Activator.context = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { bcontext });
	// Retrieve all configuration options needed by the service
	// like parameters for AbnormalSituationDetector or the MAM address
	config = new Properties();
	DataStorage dataStorage = DataStorage.getInstance();
	try {
	    // the data directory WITH separator at the end
	    String dataDir = dataStorage.getDataDirectory();
	    FileInputStream propIS = new FileInputStream(dataDir
		    + "help_when_outdoor.properties");
	    config.load(propIS);
	} catch (IOException e) {
	    log.error("Cannot load helpwhenoutdoor.properties");
	}

	agenda = new Agenda(context);

	osgiContext = bcontext;
	osgiContext.registerService(BundleProvider.class.getName(), this, null);

	HelpWhenOutdoorDataService dataService = new HelpWhenOutdoorXMLDataService(
		agenda);
	paseador = new Walker(config, dataService, context); // Just for testing
							     // purposes
	hwoconsumer = new HwoConsumer(context, dataService); // ContextSubscriber
	wanderingdetector = new WanderingDetector(5, 5); // Checking user is out
							 // of safe area or is
							 // wandering

	// Service servlet for serving websites for consulting user data
	HelpWhenOutdoorServlet serviceServlet = new HelpWhenOutdoorServlet(
		dataService, config);

	// Service servlet for serving websites for modifying user data
	MapEditor mapEditorServlet = new MapEditor(config, dataService);

	// Register the servlets in the OSGi context
	osgiContext.registerService(GatewayPort.class.getName(),
		serviceServlet, null);
	osgiContext.registerService(GatewayPort.class.getName(),
		mapEditorServlet, null);
	osgiContext.registerService(SMSGateway.class.getName(),
		new SMSGatewayImpl(null), null);
	thread = new Thread(this, "Help When Outdoor WB");
	thread.start();
	log.info("Service Help When Outdoor STARTED");

	remoteManager = (RemoteSpacesManager) context.getContainer()
		.fetchSharedObject(context,
			new Object[] { RemoteSpacesManager.class.getName() });
	log.info("Sending remote context events request ...");
	new Thread() {
	    public void run() {
		boolean result = false;
		while (!result) {
		    try{
			result = remoteManager.importRemoteContextEvents(hwoconsumer,
				    HwoConsumer.getContextSubscriptionParams());
			
		    }catch (Exception e) {
			e.printStackTrace();
		    }finally{
			try {
			    Thread.sleep(2000);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }
		    if (result){
			log.info("Import succeded!!!");
		    }else{
			log.info("Retrying import request ...");
		    }
		}
		
	    }
	}.start();

    }

    public void stop(BundleContext arg0) throws Exception {
	log.info("Service Help When Outdoor STOPPED");
    }

    public Agenda getAgenda() {

	return agenda;
    }

    public synchronized void run() {

    }

    public AbnormalSituationDetector getAbnormalSituationDetector() {
	return null;
    }

    public OutdoorLocationContextPublisher getOutdoorLocationContextPublisher() {
	return null;
    }

}
