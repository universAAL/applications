package org.universAAL.AALapplication.hwo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.android.felix.IContextStub;
import org.universAAL.middleware.util.Constants;

import android.content.Context;

public class Activator implements BundleActivator, ServiceListener {
    public static BundleContext context = null;
    public static SCallee scallee = null;
    public static ISubscriber isubscriber = null;
    public static OPublisher opublisher = null;
    public static Context activityHandle=null;

    public static final String PROPS_FILE = "hwo.mobile.properties";
    private static File confHome = new File(new File(Constants
	    .getSpaceConfRoot()), "hwo.mobile");
    public static final String TEXT = "SMS.text";
    public static final String NUMBER = "SMS.number";
    public static final String SMSENABLE = "SMS.enabled";

    public static final String GPSTO = "TAKE.destination";
    protected static Properties properties = new Properties();

    public static final String COMMENTS = "This file stores persistence info for the HwO Mobile Module.";

    private final static Logger log = LoggerFactory.getLogger(Activator.class);

    public void start(BundleContext context) throws Exception {
	log.info("Starting Help when Outside Mobile Module");
	Activator.context = context;
	properties=loadProperties();
	scallee = new SCallee(context);
	isubscriber = new ISubscriber(context);
	opublisher = new OPublisher(context);
	
	String filter = "(objectclass=" + IContextStub.class.getName() + ")";
	context.addServiceListener(this, filter);
	ServiceReference references[] = context.getServiceReferences(null, filter);
	for (int i = 0; references != null && i < references.length; i++)
		this.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED, references[i]));
	log.info("Started Help when Outside Mobile Module");
    }

    public void stop(BundleContext arg0) throws Exception {
	log.info("Stopping Help when Outside Mobile Module");
	scallee.close();
	isubscriber.close();
	opublisher.close();
	log.info("Stopped Help when Outside Mobile Module");
    }

    private static synchronized void setProperties(Properties prop) {
	try {
	    FileOutputStream fileOutputStream = new FileOutputStream(new File(
		    confHome, PROPS_FILE));
	    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
		    fileOutputStream);
	    prop.store(bufferedOutputStream, COMMENTS);
	    bufferedOutputStream.close();
	    fileOutputStream.close();
	} catch (Exception e) {
	    log.error("Could not set properties file: {}", e);
	}
    }

    private static synchronized Properties loadProperties() {
	Properties prop = new Properties();
	try {
	    prop = new Properties();
	    InputStream in = new FileInputStream(new File(confHome, PROPS_FILE));
	    prop.load(in);
	    in.close();
	} catch (java.io.FileNotFoundException e) {
	    log.warn("Properties file does not exist; generating default...");

	    prop.setProperty(TEXT, "Panic button pressed");
	    prop.setProperty(NUMBER, "123456789");
	    prop.setProperty(SMSENABLE, "false");

	    prop.setProperty(GPSTO, "Rue Wiertz 60, 1047 Bruxelles, Belgique");
	    setProperties(prop);
	} catch (Exception e) {
	    log.error("Could not access properties file: {}", e);
	}
	return prop;
    }

    public static synchronized Properties getProperties() {
	return properties;
    }

    public void serviceChanged(ServiceEvent event) {
	switch (event.getType()) {
	case ServiceEvent.REGISTERED:
	case ServiceEvent.MODIFIED:
		IContextStub stub = (IContextStub) context.getService(event.getServiceReference());
		activityHandle=stub.getAndroidContext();
		break;
	case ServiceEvent.UNREGISTERING:
		activityHandle = null;
		break;
	}		
    }

}
