package org.universAAL.AALapplication.hwo;


// TODO Create a listener to the context bus that receives events of management of POIs, safe Area, Home Location and alerts.


import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent; 
import org.osgi.framework.ServiceListener; 
import org.osgi.framework.ServiceReference; 

import org.universAAL.android.felix.IContextStub;  
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.ontology.profile.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context; 


public class Activator implements BundleActivator,ServiceListener {
	public static BundleContext osgiContext=null; 
	public static ModuleContext context=null;
	public static Context activityHandle=null; //This will allow us to use Android context functions and services.
	
	public static SCallee scallee=null;
	public static UImanager uimanager =null;
	public static WanderingDetector wanderingdetector; // This has to be put in the servlet
	public static HwoConsumer hwoconsumer =null;
	public static DefaultServiceCaller caller; 
	
	public static final String PROPS_FILE = "hwo.mobile.properties";
	public static final String TEXT = "SMS.text";
    public static final String NUMBER = "SMS.number";
    public static final String SMSENABLE = "SMS.enabled";
    public static final String GPSTO = "TAKE.destination";
    protected static Properties properties = new Properties();

    public static final String COMMENTS = "This file stores persistence info for the HwO Mobile Module.";
    
    public boolean  SMSSENT =false;

	
	
	
	
	private final static Logger log = LoggerFactory.getLogger(Activator.class);
	
	public void start(BundleContext bcontext) throws Exception { //When Hwo bundle is started
		
		log.info("Starting Help when Outside Mobile Module");
		// properties=loadProperties();
		Activator.osgiContext=bcontext;
		Activator.context = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { bcontext }); //Conversion from Osgicontext to UAAL context
		uimanager =new UImanager(context);
		
		scallee=new SCallee(context);
		log.debug("ACT: SCallee started");
		hwoconsumer=new HwoConsumer(context);
		log.debug("ACT:  hwoconsumer created");	
		caller=new DefaultServiceCaller(context); //SMS
		log.debug("ACT:  caller created");
		DataStorage dataStorage = DataStorage.getInstance();
		log.debug("ACT: dataStorage created");
		wanderingdetector = new WanderingDetector(5,5); // This has to be put in the servlet
		log.debug("ACT: wandering created");
		
		log.info("ACT: All classes initialized");
		
		String filter = "(objectclass=" + IContextStub.class.getName() + ")"; 
		bcontext.addServiceListener(this, filter);  //this only works if this class implements ServiceListener from Android
		
		
	ServiceReference references[] = osgiContext.getServiceReferences(null, filter);  
	for (int i = 0; references != null && i < references.length; i++)
			this.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED, references[i]));
		log.info("ACT: Started Help when Outside Mobile Module");
	   	scallee.Location(null); //desactivado GPS mientras pruebas con SMS
		log.debug(" ACT: GPS thread launched");
		hwoconsumer.startWanderingThread();
		log.debug ("ACT: check GPS thread launched  ");
		
		
		
	}
	

public void stop(BundleContext arg0) throws Exception {
	log.info("ACT: Stopping Help when Outside Mobile Module");
	scallee.close();
	uimanager.close();
		
	log.info("ACT: Stopped Help when Outside Mobile Module");
    }

public void serviceChanged(ServiceEvent event) {
	switch (event.getType()) {
	case ServiceEvent.REGISTERED:
	case ServiceEvent.MODIFIED:
		IContextStub stub = (IContextStub) osgiContext.getService(event.getServiceReference());
		activityHandle=stub.getAndroidContext(); //getting Android context.
		break;
	case ServiceEvent.UNREGISTERING:
		activityHandle = null;
		break;
	}		
   }  
	

}